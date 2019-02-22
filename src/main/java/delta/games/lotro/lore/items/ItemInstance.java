package delta.games.lotro.lore.items;

import java.util.HashMap;
import java.util.Map;

import delta.common.utils.NumericTools;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.lore.items.essences.EssencesSet;

/**
 * Base class for item instances.
 * @param <T> Type of the reference item.
 * @author DAM
 */
public class ItemInstance<T extends Item>
{
  // Reference item
  private T _reference;
  // Instance ID (2x32 bits)
  // TODO
  // Item birth name (given by the crafter) (may be <code>null</code>)
  private String _birthName;
  // Crafter name (may be <code>null</code>)
  private String _crafterName;
  // Essences
  private EssencesSet _essences;
  // Durability
  private Integer _durability;
  // Item level (may be null)
  private Integer _itemLevel;
  // Usage minimum level (may be null)
  private Integer _minLevel;
  // Value
  private Money _value;
  // Color
  // TODO
  // Bound to (2x32 bits)
  // TODO
  // Properties
  private HashMap<String,String> _properties;

  /**
   * Constructor.
   */
  public ItemInstance()
  {
    super();
    _birthName=null;
    _crafterName=null;
    _essences=null;
    _durability=null;
    _itemLevel=null;
    _minLevel=null;
    _value=null;
    _properties=new HashMap<String,String>();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public ItemInstance(ItemInstance<T> source)
  {
    this();
    copyFrom(source);
  }

  /**
   * Get the reference item.
   * @return the reference item.
   */
  public T getReference()
  {
    return _reference;
  }

  /**
   * Set the reference item.
   * @param reference Reference to set.
   */
  public void setReference(T reference)
  {
    _reference=reference;
  }

  /**
   * Get a string description of the reference item.
   * @return a displayable string.
   */
  public String getRefToString()
  {
    return (_reference!=null)?_reference.toString():"?";
  }

  /**
   * Get the item identifier of this item instance.
   * @return an item identifier.
   */
  public int getIdentifier()
  {
    return _reference!=null?_reference.getIdentifier():0;
  }

  /**
   * Get the item name of this item instance.
   * @return an item name.
   */
  public String getName()
  {
    return _reference!=null?_reference.getName():null;
  }

  /**
   * Get the item birth name (the one given by the crafter, if any).
   * @return a birth name or <code>null</code>.
   */
  public String getBirthName()
  {
    return _birthName;
  }

  /**
   * Set the item birth name.
   * @param birthName A birth name or <code>null</code> if none.
   */
  public void setBirthName(String birthName)
  {
    _birthName=birthName;
  }

  /**
   * Get the name of the crafter of this item, if any.
   * @return a crafter name or <code>null</code>.
   */
  public String getCrafterName()
  {
    return _crafterName;
  }

  /**
   * Set the name of the crafter of this item, if any.
   * @param crafterName a character name or <code>null</code>.
   */
  public void setCrafterName(String crafterName)
  {
    _crafterName=crafterName;
  }

  /**
   * Set the essences for this item.
   * @param essences Essences to set.
   */
  public void setEssences(EssencesSet essences)
  {
    _essences=essences;
  }

  /**
   * Get the essence for this item.
   * @return A set of essences, or <code>null</code> if not set.
   */
  public EssencesSet getEssences()
  {
    return _essences;
  }

  /**
   * Get essences count.
   * @return a count.
   */
  public int getEssencesCount()
  {
    return (_essences!=null)?_essences.getSize():0;
  }

  /**
   * Get an essence.
   * @param index Index of the targeted essence, starting at 0.
   * @return An essence or <code>null</code>.
   */
  public Item getEssenceAt(int index)
  {
    Item essence=null;
    if (_essences!=null)
    {
      if (index<_essences.getSize())
      {
        essence=_essences.getEssence(index);
      }
    }
    return essence;
  }

  /**
   * Get the durability of this item.
   * @return a durability value.
   */
  public Integer getDurability()
  {
    return _durability;
  }

  /**
   * Set the durability of this item.
   * @param durability the durability to set.
   */
  public void setDurability(Integer durability)
  {
    _durability=durability;
  }

  /**
   * Get the item level.
   * @return a level value or <code>null</code>.
   */
  public Integer getItemLevel()
  {
    return _itemLevel;
  }

  /**
   * Set the item level.
   * @param itemLevel the item level as an integer value, or <code>null</code>.
   */
  public void setItemLevel(Integer itemLevel)
  {
    _itemLevel=itemLevel;
  }

  /**
   * Get the minimum level required to use this item.
   * @return a minimum level value or <code>null</code>.
   */
  public Integer getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Set the minimum level required to use this item.
   * @param minLevel the minimum level as an integer value,
   * or <code>null</code> for no restriction.
   */
  public void setMinLevel(Integer minLevel)
  {
    _minLevel=minLevel;
  }

  /**
   * Get the value of this item.
   * @return an amount of money.
   */
  public Money getValue()
  {
    return _value;
  }

  /**
   * Set the value of this item.
   * @param value the value to set.
   */
  public void setValue(Money value)
  {
    _value=value;
  }

  /**
   * Get the stash identifier of this item.
   * @return an stash item identifier.
   */
  public Integer getStashIdentifier()
  {
    String idStr=getProperty(ItemPropertyNames.STASH_ID);
    Integer ret=null;
    if (idStr!=null)
    {
      ret=NumericTools.parseInteger(idStr);
    }
    return ret;
  }

  /**
   * Set the stash identifier of this item.
   * @param stashIdentifier the stash identifier to set.
   */
  public void setStashIdentifier(Integer stashIdentifier)
  {
    if (stashIdentifier==null)
    {
      removeProperty(ItemPropertyNames.STASH_ID);
    }
    else
    {
      setProperty(ItemPropertyNames.STASH_ID,stashIdentifier.toString());
    }
  }

  /**
   * Set a property for this item.
   * @param key Property key.
   * @param value Property value.
   */
  public void setProperty(String key, String value)
  {
    _properties.put(key,value);
  }

  /**
   * Remove a property.
   * @param key Property key.
   */
  public void removeProperty(String key)
  {
    _properties.remove(key);
  }

  /**
   * Get the value of a property.
   * @param key Property name.
   * @return A value or <code>null</code> if not set.
   */
  public String getProperty(String key)
  {
    return _properties.get(key);
  }

  /**
   * Get all properties for this item.
   * @return A properties map or <code>null</code>.
   */
  public Map<String,String> getProperties()
  {
    return _properties;
  }

  /**
   * Copy item instance data from a source.
   * @param itemInstance Source item instance.
   */
  @SuppressWarnings("unchecked")
  public void copyFrom(ItemInstance<?> itemInstance)
  {
    _reference=(T)itemInstance._reference;
    _birthName=itemInstance._birthName;
    _crafterName=itemInstance._crafterName;
    _essences=new EssencesSet(itemInstance._essences);
    _durability=itemInstance._durability;
    _itemLevel=itemInstance._itemLevel;
    _minLevel=itemInstance._minLevel;
    _value=new Money(itemInstance._value);
    _properties=new HashMap<String,String>(itemInstance._properties);
  }

  /**
   * Dump the contents of this item instance as a string.
   * @return A readable string.
   */
  public String dumpInstanceData()
  {
    StringBuilder sb=new StringBuilder();
    if (_birthName!=null)
    {
      sb.append(" (Given name=");
      sb.append(_birthName);
      sb.append(')');
    }
    if (_crafterName!=null)
    {
      sb.append(" (Crafter=");
      sb.append(_crafterName);
      sb.append(')');
    }
    // Essences
    if ((_essences!=null) && (_essences.getSize()>0))
    {
      sb.append("Essences: ").append(_essences);
    }
    if (_durability!=null)
    {
      sb.append(" (Durability=");
      sb.append(_durability);
      sb.append(')');
    }
    if (_minLevel!=null)
    {
      sb.append(" (Min level=");
      sb.append(_minLevel);
      sb.append(')');
    }
    if (_itemLevel!=null)
    {
      sb.append(" (Item level=");
      sb.append(_itemLevel);
      sb.append(')');
    }
    if (_value!=null)
    {
      sb.append(" (Value=");
      sb.append(_value);
      sb.append(')');
    }
    sb.append(EndOfLine.NATIVE_EOL);
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return getRefToString()+" ("+_itemLevel+")";
  }
}
