package delta.games.lotro.lore.items;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import delta.common.utils.NumericTools;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.common.id.EntityId;
import delta.games.lotro.common.id.ItemInstanceId;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.stats.StatsManager;
import delta.games.lotro.common.stats.StatsProvider;
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
  // Instance ID
  private ItemInstanceId _id;
  // Validity date
  private Long _time;
  // Item birth name (given by the crafter) (may be <code>null</code>)
  private String _birthName;
  // Crafter name (may be <code>null</code>)
  private String _crafterName;
  // Stats manager
  private StatsManager _statsManager;
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
  private ColorDescription _color;
  // Bound to
  private EntityId _boundTo;
  // Properties
  private HashMap<String,String> _properties;

  /**
   * Constructor.
   */
  public ItemInstance()
  {
    _reference=null;
    _id=null;
    _time=null;
    _birthName=null;
    _crafterName=null;
    _statsManager=new StatsManager();
    _essences=null;
    _durability=null;
    _itemLevel=null;
    _minLevel=null;
    _value=null;
    _color=null;
    _boundTo=null;
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
   * Get the item instance identifier.
   * @return An item instance identifier or <code>null</code> if not set.
   */
  public ItemInstanceId getInstanceId()
  {
    return _id;
  }

  /**
   * Set the item instance identifier.
   * @param id Identifier to set (may be <code>null</code>).
   */
  public void setInstanceId(ItemInstanceId id)
  {
    _id=id;
  }

  /**
   * Get the validity time for this instance.
   * @return a time or <code>null</code> if not set.
   */
  public Long getTime()
  {
    return _time;
  }

  /**
   * Set the validity time for this instance.
   * @param time Time to set (may be <code>null</code>).
   */
  public void setTime(Long time)
  {
    _time=time;
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
   * Get the stats manager.
   * @return the stats manager.
   */
  public StatsManager getStatsManager()
  {
    return _statsManager;
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
   * Get the effective durability.
   * @return a durability value or <code>null</code>.
   */
  public Integer getEffectiveDurability()
  {
    return (_durability!=null?_durability:(_reference!=null)?_reference.getDurability():null);
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
   * Get the effective item level.
   * @return an item level or <code>null</code>.
   */
  public Integer getEffectiveItemLevel()
  {
    return (_itemLevel!=null?_itemLevel:(_reference!=null)?_reference.getItemLevel():null);
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
   * Get the effective min level.
   * @return a minimum level or <code>null</code>.
   */
  public Integer getEffectiveMinLevel()
  {
    return (_minLevel!=null?_minLevel:(_reference!=null)?_reference.getMinLevel():null);
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
   * Get the effective value.
   * @return a value or <code>null</code>.
   */
  public Money getEffectiveValue()
  {
    return (_value!=null?_value:(_reference!=null)?_reference.getValue():null);
  }

  /**
   * Get the color of this item.
   * @return a color or <code>null</code>.
   */
  public ColorDescription getColor()
  {
    return _color;
  }

  /**
   * Set the color of this item.
   * @param color the value to set.
   */
  public void setColor(ColorDescription color)
  {
    _color=color;
  }

  /**
   * Get the 'bound to' identifier.
   * @return An entity identifier or <code>null</code> if not set.
   */
  public EntityId getBoundTo()
  {
    return _boundTo;
  }

  /**
   * Set the 'bound to' identifier.
   * @param boundTo Identifier to set (may be <code>null</code>).
   */
  public void setBoundTo(EntityId boundTo)
  {
    _boundTo=boundTo;
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
   * Get the value of a property (using instance properties, then reference properties if not found).
   * @param key Property name.
   * @return A value or <code>null</code> if not found.
   */
  public String getEffectiveProperty(String key)
  {
    String ret=getProperty(key);
    if ((ret==null) && (_reference!=null))
    {
      ret=_reference.getProperty(key);
    }
    return ret;
  }

  /**
   * Copy item instance data from a source.
   * @param itemInstance Source item instance.
   */
  @SuppressWarnings("unchecked")
  public void copyFrom(ItemInstance<?> itemInstance)
  {
    _reference=(T)itemInstance._reference;
    _id=itemInstance._id;
    _time=itemInstance._time;
    _birthName=itemInstance._birthName;
    _crafterName=itemInstance._crafterName;
    _statsManager=new StatsManager(itemInstance._statsManager);
    if (itemInstance._essences!=null)
    {
      _essences=new EssencesSet(itemInstance._essences);
    }
    else
    {
      _essences=null;
    }
    _durability=itemInstance._durability;
    _itemLevel=itemInstance._itemLevel;
    _minLevel=itemInstance._minLevel;
    if (itemInstance._value!=null)
    {
      _value=new Money(itemInstance._value);
    }
    else
    {
      _value=null;
    }
    _color=itemInstance._color;
    _boundTo=itemInstance._boundTo;
    _properties=new HashMap<String,String>(itemInstance._properties);
  }

  /**
   * Get the stats of this item.
   * This includes:
   * <ul>
   * <li>its own stats,
   * <li>essences stats,
   * <li>stats of legendary stuff if applicable.
   * </ul>
   * @return some stats.
   */
  public BasicStatsSet getStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    BasicStatsSet ownStats=_statsManager.getResult();
    ret.addStats(ownStats);
    BasicStatsSet essencesStats=getEssencesStats();
    ret.addStats(essencesStats);
    return ret;
  }

  /**
   * Update automatic stats
   */
  public void updateAutoStats()
  {
    if (_reference!=null)
    {
      StatsProvider provider=_reference.getStatsProvider();
      if (provider!=null)
      {
        Integer effectiveLevel=getEffectiveItemLevel();
        if (effectiveLevel!=null)
        {
          BasicStatsSet stats=provider.getStats(1,effectiveLevel.intValue(),true);
          _statsManager.getDefault().setStats(stats);
          _statsManager.apply();
        }
      }
    }
  }

  /**
   * Get the stats from essences.
   * @return some stats.
   */
  public BasicStatsSet getEssencesStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    if (_essences!=null)
    {
      int nbSlots=_essences.getSize();
      for(int i=0;i<nbSlots;i++)
      {
        Item essence=_essences.getEssence(i);
        if (essence!=null)
        {
          BasicStatsSet essenceStats=essence.getStats();
          ret.addStats(essenceStats);
        }
      }
    }
    return ret;
  }

  /**
   * Dump the contents of this item instance as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    if (_reference!=null)
    {
      sb.append(_reference.dump());
      sb.append(EndOfLine.NATIVE_EOL);
    }
    sb.append("Instance:");
    if (_id!=null)
    {
      sb.append(" (instance ID=");
      sb.append(_id);
      sb.append(')');
    }
    if (_time!=null)
    {
      sb.append(" (validity time=");
      sb.append(_time);
      sb.append(": ");
      sb.append(new Date(_time.longValue()));
      sb.append(')');
    }
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
    if (_color!=null)
    {
      sb.append(" (Color=");
      sb.append(_color.getName());
      sb.append(')');
    }
    if (_boundTo!=null)
    {
      sb.append(" (bound to=");
      sb.append(_boundTo);
      sb.append(')');
    }
    sb.append(EndOfLine.NATIVE_EOL);
    // Stats
    sb.append("Stats manager: ").append(_statsManager);
    // Essences
    if ((_essences!=null) && (_essences.getSize()>0))
    {
      sb.append(_essences);
    }
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return getRefToString()+" ("+_itemLevel+")";
  }
}
