package delta.games.lotro.character.gear;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.BasicCharacterAttributes;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Equipment of a character.
 * @author DAM
 */
public class CharacterGear
{
  private HashMap<GearSlot,GearSlotContents> _contents; 

  /**
   * Constructor.
   */
  public CharacterGear()
  {
    _contents=new HashMap<GearSlot,GearSlotContents>();
  }

  /**
   * Get a slot contents.
   * @param slot Slot to get.
   * @param createIfNeeded <code>true</code> to create the slot if it does not exist,
   * <code>false</code> otherwise.
   * @return A slot contents or <code>null</code> if not found and <code>createIfNeeded</code> is <code>false</code>.
   */
  public GearSlotContents getSlotContents(GearSlot slot, boolean createIfNeeded)
  {
    GearSlotContents contents=null;
    if (slot!=null)
    {
      contents=_contents.get(slot);
      if ((contents==null) && (createIfNeeded))
      {
        contents=new GearSlotContents(slot);
        _contents.put(slot,contents);
      }
    }
    return contents;
  }

  /**
   * Set the contents of this object from a given source.
   * @param source Source to copy.
   */
  public void copyFrom(CharacterGear source)
  {
    _contents.clear();
    for(Map.Entry<GearSlot,GearSlotContents> entry : source._contents.entrySet())
    {
      GearSlotContents newSlot=new GearSlotContents(entry.getValue());
      _contents.put(entry.getKey(),newSlot);
    }
  }

  /**
   * Get the item for a given slot.
   * @param slot Targeted slot.
   * @return An item or <code>null</code> if not found.
   */
  public ItemInstance<? extends Item> getItemForSlot(GearSlot slot)
  {
    GearSlotContents contents=getSlotContents(slot,false);
    ItemInstance<? extends Item> itemInstance=null;
    if (contents!=null)
    {
      itemInstance=contents.getItem();
    }
    return itemInstance;
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _contents.clear();
  }

  /**
   * Set the wearer of this equipment.
   * @param wearer Wearer to use.
   */
  public void setWearer(BasicCharacterAttributes wearer)
  {
    for(GearSlotContents contents : _contents.values())
    {
      ItemInstance<? extends Item> item=contents.getItem();
      if (item!=null)
      {
        item.setWearer(wearer);
      }
    }
  }

  /**
   * Get the equipped slots.
   * @return A set of slots.
   */
  public Set<GearSlot> getEquippedSlots()
  {
    Set<GearSlot> ret=new HashSet<GearSlot>();
    for(GearSlot slot : GearSlot.getAll())
    {
      GearSlotContents slotContents=getSlotContents(slot,false);
      if (slotContents==null)
      {
        continue;
      }
      ItemInstance<?> itemInstance=slotContents.getItem();
      if (itemInstance==null)
      {
        continue;
      }
      ret.add(slot);
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    GearSlot[] slots=GearSlot.getAll();
    for(GearSlot slot : slots)
    {
      GearSlotContents contents=getSlotContents(slot,false);
      if (contents!=null)
      {
        sb.append(contents);
        sb.append(EndOfLine.NATIVE_EOL);
      }
    }
    return sb.toString().trim();
  }
}
