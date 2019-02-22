package delta.games.lotro.lore.items;

import delta.games.lotro.lore.items.legendary.LegendaryItem;
import delta.games.lotro.lore.items.legendary.LegendaryItemInstance;
import delta.games.lotro.lore.items.legendary.LegendaryWeapon;
import delta.games.lotro.lore.items.legendary.LegendaryWeaponInstance;

/**
 * Item builder.
 * @author DAM
 */
public class ItemFactory
{
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
    // TODO
    return item;
  }
}
