package delta.games.lotro.lore.items;

import org.apache.log4j.Logger;

import delta.games.lotro.lore.items.legendary.LegaciesManager;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.LegendaryInstance;
import delta.games.lotro.lore.items.legendary.LegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.LegendaryItem;
import delta.games.lotro.lore.items.legendary.LegendaryItemInstance;
import delta.games.lotro.lore.items.legendary.LegendaryWeapon;
import delta.games.lotro.lore.items.legendary.LegendaryWeaponInstance;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegaciesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryInstanceAttrs;

/**
 * Item builder.
 * @author DAM
 */
public class ItemFactory
{
  private static final Logger LOGGER=Logger.getLogger(ItemFactory.class);

  /**
   * Build a new item of the right class.
   * @param category Item category.
   * @return An item. 
   */
  public static Item buildItem(ItemCategory category)
  {
    Item ret;
    if (category==ItemCategory.ARMOUR)
    {
      ret=new Armour();
    }
    else if (category==ItemCategory.WEAPON)
    {
      ret=new Weapon();
    }
    else if (category==ItemCategory.LEGENDARY_WEAPON)
    {
      ret=new LegendaryWeapon();
    }
    else if (category==ItemCategory.LEGENDARY_ITEM)
    {
      ret=new LegendaryItem();
    }
    else
    {
      ret=new Item();
    }
    return ret;
  }

  /**
   * Clone an item.
   * @param item Source item.
   * @return Cloned item.
   */
  public static Item clone(Item item)
  {
    Item ret;
    ItemCategory category=item.getCategory();
    if (category==ItemCategory.ARMOUR)
    {
      ret=new Armour((Armour)item);
    }
    else if (category==ItemCategory.WEAPON)
    {
      ret=new Weapon((Weapon)item);
    }
    else if (category==ItemCategory.LEGENDARY_WEAPON)
    {
      ret=new LegendaryWeapon((LegendaryWeapon)item);
    }
    else if (category==ItemCategory.LEGENDARY_ITEM)
    {
      ret=new LegendaryItem((LegendaryItem)item);
    }
    else
    {
      ret=new Item(item);
    }
    return ret;
  }

  /**
   * Build an item instance for an item.
   * @param item Item to use.
   * @return A new item instance.
   */
  public static ItemInstance<? extends Item> buildInstance(Item item)
  {
    ItemInstance<? extends Item> ret;
    if (item instanceof Armour)
    {
      Armour reference=(Armour)item;
      ArmourInstance instance=new ArmourInstance();
      instance.setReference(reference);
      ret=instance;
    }
    else if (item instanceof LegendaryWeapon)
    {
      LegendaryWeapon reference=(LegendaryWeapon)item;
      LegendaryWeaponInstance instance=new LegendaryWeaponInstance();
      instance.setReference(reference);
      ret=instance;
    }
    else if (item instanceof Weapon)
    {
      Weapon reference=(Weapon)item;
      WeaponInstance<Weapon> instance=new WeaponInstance<Weapon>();
      instance.setReference(reference);
      ret=instance;
    }
    else if (item instanceof LegendaryItem)
    {
      LegendaryItem reference=(LegendaryItem)item;
      LegendaryItemInstance instance=new LegendaryItemInstance();
      instance.setReference(reference);
      ret=instance;
    }
    else
    {
      ItemInstance<Item> instance=new ItemInstance<Item>();
      instance.setReference(item);
      ret=instance;
    }
    return ret;
  }

  /**
   * Clone an item instance.
   * @param item Item instance to use.
   * @return A new item instance.
   */
  public static ItemInstance<? extends Item> cloneInstance(ItemInstance<? extends Item> item)
  {
    ItemInstance<? extends Item> clone=buildInstance(item.getReference());
    clone.copyFrom(item);
    return clone;
  }

  /**
   * Initialize the contents of an item instance.
   * @param itemInstance Instance to initialize.
   */
  public static void initInstance(ItemInstance<? extends Item> itemInstance)
  {
    if (itemInstance instanceof LegendaryInstance)
    {
      Item item=itemInstance.getReference();
      Legendary legendary=(Legendary)item;
      LegendaryAttrs legendaryAttrs=legendary.getLegendaryAttrs();
      Integer mainLegacyId=legendaryAttrs.getMainLegacyId();
      if (mainLegacyId!=null)
      {
        DefaultNonImbuedLegacy defaultLegacy=NonImbuedLegaciesManager.getInstance().getDefaultLegacy(mainLegacyId.intValue());
        if (defaultLegacy!=null)
        {
          setupDefaultLegacy(itemInstance,defaultLegacy);
        }
        else
        {
          LOGGER.warn("Could not find default legacy: "+mainLegacyId);
        }
      }
      else
      {
        LOGGER.warn("No main legacy for: "+item);
      }
    }
    else
    {
      LOGGER.warn("Not a legendary instance: "+itemInstance);
    }
  }

  private static void setupDefaultLegacy(ItemInstance<? extends Item> itemInstance, DefaultNonImbuedLegacy defaultLegacy)
  {
    LegendaryInstance legendaryInstance=(LegendaryInstance)itemInstance;
    LegendaryInstanceAttrs legendaryInstanceAttrs=legendaryInstance.getLegendaryAttributes();
    NonImbuedLegendaryInstanceAttrs nonImbuedAttrs=legendaryInstanceAttrs.getNonImbuedAttrs();
    DefaultNonImbuedLegacyInstance defaultLegacyInstance=nonImbuedAttrs.getDefaultLegacy();
    defaultLegacyInstance.setLegacy(defaultLegacy);
    // TODO For some items, we may have a different default legacy depending on the item level
    // (seen on 3rd age Champion's Rune). Then the model gives the legacy for the default item level (e.g. 75)

    int mainImbuedLegacy=defaultLegacy.getImbuedLegacyId();
    if (mainImbuedLegacy!=0)
    {
      ImbuedLegacy mainLegacy=LegaciesManager.getInstance().getLegacy(mainImbuedLegacy);
      ImbuedLegendaryInstanceAttrs imbuedAttrs=legendaryInstanceAttrs.getImbuedAttrs();
      ImbuedLegacyInstance mainLegacyInstance=imbuedAttrs.getLegacy(0);
      mainLegacyInstance.setLegacy(mainLegacy);
    }
    else
    {
      LOGGER.warn("No imbued legacy associated to default legacy: "+defaultLegacy);
    }
    
  }
}
