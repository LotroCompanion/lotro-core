package delta.games.lotro.character;

import java.util.HashMap;
import java.util.Map;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Equipment of a character.
 * @author DAM
 */
public class CharacterEquipment
{
  private static HashMap<Integer,EQUIMENT_SLOT> _positionToSlot=new HashMap<Integer,EQUIMENT_SLOT>();

  /**
   * Equipment slot designators.
   * @author DAM
   */
  public enum EQUIMENT_SLOT
  {
    /**
     * Left ear.
     */
    LEFT_EAR(14,EquipmentLocation.EAR),
    /**
     * Right ear.
     */
    RIGHT_EAR(15,EquipmentLocation.EAR),
    /**
     * Neck.
     */
    NECK(11,EquipmentLocation.NECK),
    /**
     * Pocket.
     */
    POCKET(16,EquipmentLocation.POCKET),
    /**
     * Left wrist.
     */
    LEFT_WRIST(9,EquipmentLocation.WRIST),
    /**
     * Right wrist.
     */
    RIGHT_WRIST(10,EquipmentLocation.WRIST),
    /**
     * Left finger.
     */
    LEFT_FINGER(12,EquipmentLocation.FINGER),
    /**
     * Right finger.
     */
    RIGHT_FINGER(13,EquipmentLocation.FINGER),
    /**
     * Head.
     */
    HEAD(2,EquipmentLocation.HEAD),
    /**
     * Shoulder.
     */
    SHOULDER(7,EquipmentLocation.SHOULDER),
    /**
     * Breast.
     */
    BREAST(3,EquipmentLocation.CHEST),
    /**
     * Back.
     */
    BACK(8,EquipmentLocation.BACK),
    /**
     * Hands.
     */
    HANDS(5,EquipmentLocation.HAND),
    /**
     * Legs.
     */
    LEGS(4,EquipmentLocation.LEGS),
    /**
     * Feet.
     */
    FEET(6,EquipmentLocation.FEET),
    /**
     * Main melee object.
     */
    MAIN_MELEE(17,EquipmentLocation.MAIN_HAND),
    /**
     * Other melee object.
     */
    OTHER_MELEE(18,EquipmentLocation.OFF_HAND),
    /**
     * Ranged object.
     */
    RANGED(19,EquipmentLocation.RANGED_ITEM),
    /**
     * Tool object.
     */
    TOOL(20,EquipmentLocation.TOOL),
    /**
     * Class object item.
     */
    CLASS_ITEM(21,EquipmentLocation.CLASS_SLOT);

    private int _position;
    private EquipmentLocation _location;

    private EQUIMENT_SLOT(int position, EquipmentLocation location)
    {
      _position=position;
      _location=location;
      _positionToSlot.put(Integer.valueOf(position),this);
    }

    /**
     * Get the integer position associated with this slot.
     * @return an integer value.
     */
    public int getPosition()
    {
      return _position;
    }

    /**
     * Get the associated location.
     * @return the associated location.
     */
    public EquipmentLocation getLocation()
    {
      return _location;
    }
  }

  /**
   * Contents of a single slot.
   * @author DAM
   */
  public static class SlotContents
  {
    private EQUIMENT_SLOT _slot;
    private Integer _itemId;
    private Item _item;

    /**
     * Constructor.
     * @param slot Targeted equipment slot.
     */
    public SlotContents(EQUIMENT_SLOT slot)
    {
      _slot=slot;
      _itemId=null;
      _item=null;
    }

    /**
     * Copy constructor.
     * @param source Source data.
     */
    public SlotContents(SlotContents source)
    {
      _slot=source._slot;
      _itemId=source._itemId;
      if (source._item!=null)
      {
        _item=ItemFactory.clone(source._item);
      }
    }

    /**
     * Get the managed slot.
     * @return the managed slot.
     */
    public EQUIMENT_SLOT getSlot()
    {
      return _slot;
    }

    /**
     * Get the identifier of the item in this slot.
     * @return An item identifier or <code>null</code> if none.
     */
    public Integer getItemId()
    {
      return _itemId;
    }

    /**
     * Set the identifier of the item in this slot.
     * @param itemId An item identifier or <code>null</code>.
     */
    public void setItemId(Integer itemId)
    {
      _itemId=itemId;
    }

    /**
     * Get the item in this slot.
     * @return an item or <code>null</code>.
     */
    public Item getItem()
    {
      return _item;
    }

    /**
     * Set the item in this slot.
     * @param item Item to set.
     */
    public void setItem(Item item)
    {
      _item=item;
    }

    @Override
    public String toString()
    {
      StringBuilder sb=new StringBuilder();
      sb.append("Slot ").append(_slot).append(": ");
      sb.append("itemId=[").append(_itemId).append(']');
      if (_item!=null)
      {
        sb.append(", item=[").append(_item).append(']');
      }
      return sb.toString().trim();
    }
  }

  private HashMap<Integer,SlotContents> _contents; 

  /**
   * Constructor.
   */
  public CharacterEquipment()
  {
    _contents=new HashMap<Integer,SlotContents>();
    // Force EQUIPMENT_SLOT class initialization
    EQUIMENT_SLOT.values();
  }

  /**
   * Get a slot contents.
   * @param slot Slot to get.
   * @param createIfNeeded <code>true</code> to create the slot if it does not exist,
   * <code>false</code> otherwise.
   * @return A slot contents or <code>null</code> if not found and <code>createIfNeeded</code> is <code>false</code>.
   */
  public SlotContents getSlotContents(EQUIMENT_SLOT slot, boolean createIfNeeded)
  {
    SlotContents contents=null;
    if (slot!=null)
    {
      int index=slot.getPosition();
      contents=_contents.get(Integer.valueOf(index));
      if ((contents==null) && (createIfNeeded))
      {
        contents=new SlotContents(slot);
        _contents.put(Integer.valueOf(index),contents);
      }
    }
    return contents;
  }

  /**
   * Get an equipment slot designator using its position index. 
   * @param index Index to search.
   * @return An equipment slot or <code>null</code> if not found.
   */
  public static EQUIMENT_SLOT getSlotByIndex(int index)
  {
    EQUIMENT_SLOT slot=_positionToSlot.get(Integer.valueOf(index));
    return slot;
  }

  /**
   * Set the contents of this object from a given source.
   * @param source Source to copy.
   */
  public void copyFrom(CharacterEquipment source)
  {
    _contents.clear();
    for(Map.Entry<Integer,SlotContents> entry : source._contents.entrySet())
    {
      SlotContents newSlot=new SlotContents(entry.getValue());
      _contents.put(entry.getKey(),newSlot);
    }
  }

  /**
   * Get the item for a given slot.
   * @param slot Targeted slot.
   * @return An item or <code>null</code> if not found.
   */
  public Item getItemForSlot(EQUIMENT_SLOT slot)
  {
    SlotContents contents=getSlotContents(slot,false);
    Item item=null;
    if (contents!=null)
    {
      item=contents.getItem();
      if (item==null)
      {
        Integer id=contents.getItemId();
        if (id!=null)
        {
          ItemsManager itemsManager=ItemsManager.getInstance();
          item=itemsManager.getItem(id);
          if (item!=null)
          {
            item=ItemFactory.clone(item);
          }
          contents.setItem(item);
        }
      }
    }
    return item;
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _contents.clear();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    EQUIMENT_SLOT[] slots=EQUIMENT_SLOT.values();
    for(EQUIMENT_SLOT slot : slots)
    {
      SlotContents contents=getSlotContents(slot,false);
      if (contents!=null)
      {
        sb.append(contents);
        sb.append(EndOfLine.NATIVE_EOL);
      }
    }
    return sb.toString().trim();
  }
}
