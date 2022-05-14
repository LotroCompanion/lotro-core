package delta.games.lotro.lore.items;

import org.apache.log4j.Logger;

import delta.games.lotro.lore.items.carryalls.CarryAll;
import delta.games.lotro.lore.items.essences.EssencesSet;
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
import delta.games.lotro.lore.items.legendary2.LegendaryItem2;
import delta.games.lotro.lore.items.legendary2.LegendaryItemInstance2;
import delta.games.lotro.lore.items.legendary2.LegendaryWeapon2;
import delta.games.lotro.lore.items.legendary2.LegendaryWeaponInstance2;

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
    else if (category==ItemCategory.LEGENDARY_WEAPON2)
    {
      ret=new LegendaryWeapon2();
    }
    else if (category==ItemCategory.LEGENDARY_ITEM2)
    {
      ret=new LegendaryItem2();
    }
    else if (category==ItemCategory.CARRY_ALL)
    {
      ret=new CarryAll();
    }
    else
    {
      ret=new Item();
    }
    return ret;
  }

  /**
   * Build an item instance for an item.
   * @param item Item to use.
   * @return A new, initialized item instance.
   */
  public static ItemInstance<? extends Item> buildInstance(Item item)
  {
    ItemInstance<? extends Item> itemInstance=createItemInstance(item);
    initInstance(itemInstance);
    return itemInstance;
  }

  /**
   * Create an item instance for an item.
   * @param item Item to use.
   * @return A new item instance.
   */
  public static ItemInstance<? extends Item> createItemInstance(Item item)
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
    else if (item instanceof LegendaryWeapon2)
    {
      LegendaryWeapon2 reference=(LegendaryWeapon2)item;
      LegendaryWeaponInstance2 instance=new LegendaryWeaponInstance2(reference.getLegendaryAttrs());
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
    else if (item instanceof LegendaryItem2)
    {
      LegendaryItem2 reference=(LegendaryItem2)item;
      LegendaryItemInstance2 instance=new LegendaryItemInstance2(reference.getLegendaryAttrs());
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
   * @param itemInstance Item instance to use.
   * @return A new item instance.
   */
  public static ItemInstance<? extends Item> cloneInstance(ItemInstance<? extends Item> itemInstance)
  {
    ItemInstance<? extends Item> clone=createItemInstance(itemInstance.getReference());
    clone.copyFrom(itemInstance);
    return clone;
  }

  /**
   * Initialize the contents of an item instance.
   * @param itemInstance Instance to initialize.
   */
  private static void initInstance(ItemInstance<? extends Item> itemInstance)
  {
    Item item=itemInstance.getReference();
    if (itemInstance instanceof LegendaryInstance)
    {
      Legendary legendary=(Legendary)item;
      LegendaryAttrs legendaryAttrs=legendary.getLegendaryAttrs();
      setupDefaultLegacy(itemInstance,legendaryAttrs);
    }
    // Essences
    int nbEssenceSlots=item.getEssenceSlots();
    if (nbEssenceSlots>0)
    {
      EssencesSet essences=new EssencesSet(nbEssenceSlots);
      itemInstance.setEssences(essences);
    }
    // Compute automatic stats
    itemInstance.updateAutoStats();
  }

  private static void setupDefaultLegacy(ItemInstance<? extends Item> itemInstance, LegendaryAttrs legendaryAttrs)
  {
    // Main legacy
    int mainLegacyId=legendaryAttrs.getMainLegacyId();
    DefaultNonImbuedLegacy defaultLegacy=NonImbuedLegaciesManager.getInstance().getDefaultLegacy(mainLegacyId);
    if (defaultLegacy==null)
    {
      LOGGER.warn("Could not find default legacy: "+mainLegacyId);
      return;
    }
    LegendaryInstance legendaryInstance=(LegendaryInstance)itemInstance;
    LegendaryInstanceAttrs legendaryInstanceAttrs=legendaryInstance.getLegendaryAttributes();
    NonImbuedLegendaryInstanceAttrs nonImbuedAttrs=legendaryInstanceAttrs.getNonImbuedAttrs();
    DefaultNonImbuedLegacyInstance defaultLegacyInstance=nonImbuedAttrs.getDefaultLegacy();
    defaultLegacyInstance.setLegacy(defaultLegacy);
    // - rank 
    int baseRank=legendaryAttrs.getMainLegacyBaseRank();
    defaultLegacyInstance.setRank(baseRank);
    // TODO For some items, we may have a different default legacy depending on the item level
    // (seen on 3rd age Champion's Rune). Then the model gives the legacy for the default item level (e.g. 75)

    // Imbued data
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
