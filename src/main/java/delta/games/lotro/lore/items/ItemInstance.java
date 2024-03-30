package delta.games.lotro.lore.items;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import delta.common.utils.NumericTools;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.BasicCharacterAttributes;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.stats.StatsManager;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.essences.EssencesSet;

/**
 * Base class for item instances.
 * @param <T> Type of the reference item.
 * @author DAM
 */
public class ItemInstance<T extends Item> implements ItemProvider
{
  // Wearer
  private BasicCharacterAttributes _wearer;
  // Reference item
  private T _reference;
  // Instance ID
  private InternalGameId _id;
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
  private InternalGameId _boundTo;
  // Properties
  private HashMap<String,String> _properties;

  /**
   * Constructor.
   */
  public ItemInstance()
  {
    _wearer=null;
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
   * Get the reference item.
   * @return the reference item.
   */
  public T getItem()
  {
    return getReference();
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
  public InternalGameId getInstanceId()
  {
    return _id;
  }

  /**
   * Set the item instance identifier.
   * @param id Identifier to set (may be <code>null</code>).
   */
  public void setInstanceId(InternalGameId id)
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
   * Get the applicable item level.
   * @return An item level value.
   */
  public int getApplicableItemLevel()
  {
    Integer itemLevel=getEffectiveItemLevel();
    int ret=(itemLevel!=null)?itemLevel.intValue():0;
    return ret;
  }

  /**
   * Get the item level to use for stats.
   * @return the item level to use for stats.
   */
  public Integer getItemLevelForStats()
  {
    Integer itemLevel=getEffectiveItemLevel();
    Integer itemLevelOffset=_reference.getItemLevelOffset();
    if (itemLevelOffset!=null)
    {
      if (itemLevel!=null)
      {
        return Integer.valueOf(itemLevel.intValue()+itemLevelOffset.intValue());
      }
      return itemLevelOffset;
    }
    return _itemLevel;
  }

  
  /**
   * Get the level of the owner.
   * @return A character level.
   */
  protected int getWearerLevel()
  {
    return (_wearer!=null)?_wearer.getLevel():1;
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
    return (_value!=null?_value:(_reference!=null)?_reference.getValueAsMoney():null);
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
  public InternalGameId getBoundTo()
  {
    return _boundTo;
  }

  /**
   * Set the 'bound to' identifier.
   * @param boundTo Identifier to set (may be <code>null</code>).
   */
  public void setBoundTo(InternalGameId boundTo)
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
   * Copy item instance data from a source.
   * @param itemInstance Source item instance.
   */
  @SuppressWarnings("unchecked")
  public void copyFrom(ItemInstance<?> itemInstance)
  {
    _wearer=itemInstance._wearer;
    _reference=(T)itemInstance._reference;
    if (itemInstance._id!=null)
    {
      _id=new InternalGameId(itemInstance._id);
    }
    else
    {
      _id=null;
    }
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
    if (itemInstance._boundTo!=null)
    {
      _boundTo=new InternalGameId(itemInstance._boundTo);
    }
    else
    {
      _boundTo=null;
    }
    _properties=new HashMap<String,String>(itemInstance._properties);
  }

  /**
   * Get the wearer of this item.
   * @return A wearer or <code>null</code> if none.
   */
  public BasicCharacterAttributes getWearer()
  {
    return _wearer;
  }

  /**
   * Set the wearer of this item.
   * @param wearer Wearer to set.
   */
  public void setWearer(BasicCharacterAttributes wearer)
  {
    _wearer=wearer;
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
    Integer effectiveLevel=getEffectiveItemLevel();
    if (effectiveLevel!=null)
    {
      BasicStatsSet stats=getStatsForItemLevel(effectiveLevel.intValue());
      if (stats!=null)
      {
        _statsManager.getDefault().setStats(stats);
        _statsManager.apply();
      }
    }
  }

  /**
   * Get the "auto" stats for the given effective level.
   * This takes into account the potential "item level offset"
   * (for instance on Captain standards).
   * @param effectiveItemLevel Item level to use.
   * @return Some stats or <code>null</code> if computation is not possible.
   */
  public BasicStatsSet getStatsForItemLevel(int effectiveItemLevel)
  {
    BasicStatsSet ret=null;
    if (_reference!=null)
    {
      int levelForStats=effectiveItemLevel;
      Integer offset=_reference.getItemLevelOffset();
      if (offset!=null)
      {
        levelForStats+=offset.intValue();
      }
      StatsProvider provider=_reference.getStatsProvider();
      if (provider!=null)
      {
        ret=provider.getStats(1,levelForStats);
      }
    }
    return ret;
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
    sb.append(EndOfLine.NATIVE_EOL);
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
