package delta.games.lotro.lore.items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.NumericTools;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.Interactable;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.enums.EquipmentCategory;
import delta.games.lotro.common.enums.ItemClass;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.utils.valueTables.QualityBasedValuesTable;
import delta.games.lotro.lore.items.details.ItemDetail;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.effects.ItemEffectsManager;
import delta.games.lotro.lore.items.essences.EssencesSlotsSetup;
import delta.games.lotro.lore.items.scaling.ItemSpellcraft;
import delta.games.lotro.lore.items.scaling.Munging;
import delta.games.lotro.lore.items.scaling.ScalingData;
import delta.games.lotro.lore.items.sets.ItemsSet;

/**
 * Item description.
 * @author DAM
 */
public class Item implements Interactable,ItemProvider
{
  private static final Logger LOGGER=LoggerFactory.getLogger(Item.class);

  // Item identifier
  private int _identifier;
  // Icon name: iconID-backgroundIconID
  private String _icon;
  // Associated set (may be null)
  private ItemsSet _set;
  // Slots
  private EquipmentLocation _equipmentLocation;
  private EquipmentLocation _precludedSlots;
  // Item name. Ex: "Jacket of the Impossible Shot"
  private String _name;
  // Plural name
  private String _pluralName;
  // Item class. Yields the category name.
  private ItemClass _itemClass;
  // Tier
  private Integer _tier;
  // Equipment category
  private EquipmentCategory _equipmentCategory;
  // Item binding: "Bind on Acquire", ...
  private ItemBinding _binding;
  // Is item unique or not?
  private boolean _unique;
  // Stats
  private BasicStatsSet _stats;
  private StatsProvider _statsProvider;
  // Essences
  private EssencesSlotsSetup _essenceSlots;
  // Durability
  private Integer _durability;
  // Sturdiness (may be null)
  private ItemSturdiness _sturdiness;
  // Item level (may be null)
  private Integer _itemLevel;
  // Item level offset (may be null)
  private Integer _itemLevelOffset;

  // Requirements:
  private UsageRequirement _requirements;
  // Full description (may be empty but not <code>null</code>)
  private String _description;
  // Value
  private QualityBasedValuesTable _value;
  // Stacking information
  private Integer _stackMax;
  // Quality
  private ItemQuality _quality;
  // Scaling
  private ScalingData _scaling;
  // Spellcraft
  private ItemSpellcraft _spellcraft;
  // Other details
  private ItemDetailsManager _details;
  // Effects
  private ItemEffectsManager _effects;

  /**
   * Constructor.
   */
  public Item()
  {
    super();
    _identifier=0;
    _icon=null;
    _equipmentLocation=EquipmentLocations.NONE;
    _name="";
    _itemClass=null;
    _binding=null;
    _unique=false;
    _stats=new BasicStatsSet();
    _statsProvider=null;
    _essenceSlots=null;
    _durability=null;
    _sturdiness=null;
    _requirements=new UsageRequirement();
    _itemLevel=null;
    _itemLevelOffset=null;
    _description="";
    _value=null;
    _stackMax=null;
    _quality=null;
    _scaling=null;
    _spellcraft=null;
    _details=null;
    _effects=null;
  }

  @Override
  public Item getItem()
  {
    return this;
  }

  /**
   * Get the identifier of this item.
   * @return an item identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this item.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the icon for this item.
   * @return an icon name.
   */
  public String getIcon()
  {
    return _icon;
  }

  /**
   * Set the icon for this item.
   * @param icon Icon name to set.
   */
  public void setIcon(String icon)
  {
    _icon=icon;
  }

  /**
   * Get the icon ID for this item.
   * @return an icon ID.
   */
  public int getIconId()
  {
    int ret=0;
    if (_icon!=null)
    {
      int index=_icon.indexOf('-');
      String iconStr=(index!=-1)?_icon.substring(0,index):_icon;
      ret=NumericTools.parseInt(iconStr,0);
    }
    return ret;
  }

  /**
   * Get the background icon ID for this item.
   * @return an icon ID.
   */
  public int getBackgroundIconId()
  {
    int ret=0;
    if (_icon!=null)
    {
      String iconStr=_icon.substring(_icon.indexOf('-')+1);
      int nextIndex=iconStr.indexOf('-');
      if (nextIndex!=-1)
      {
        iconStr=iconStr.substring(0,nextIndex);
      }
      ret=NumericTools.parseInt(iconStr,0);
    }
    return ret;
  }

  /**
   * Get the associated items set.
   * @return an items set or <code>null</code>.
   */
  public ItemsSet getSet()
  {
    return _set;
  }

  /**
   * Set the associated items set.
   * @param set Items set.
   */
  public void setItemsSet(ItemsSet set)
  {
    _set=set;
  }

  /**
   * Get equipment location.
   * @return an equipment location (never <code>null</code>).
   */
  public EquipmentLocation getEquipmentLocation()
  {
    return _equipmentLocation; 
  }

  /**
   * Set the equipment location.
   * @param equipmentLocation Location to set.
   */
  public void setEquipmentLocation(EquipmentLocation equipmentLocation)
  {
    if (equipmentLocation!=null)
    {
      _equipmentLocation=equipmentLocation;
    }
  }

  /**
   * Get the precluded slots.
   * @return an equipment location (or <code>null</code>).
   */
  public EquipmentLocation getPrecludedSlots()
  {
    return _precludedSlots; 
  }

  /**
   * Set the precluded slots.
   * @param precludedSlots Slots to set.
   */
  public void setPrecludedSlots(EquipmentLocation precludedSlots)
  {
    _precludedSlots=precludedSlots;
  }

  /**
   * Get the name of this item.
   * @return an item name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this item.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the plural name of this item.
   * @return a plural name or <code>null</code>.
   */
  public String getPluralName()
  {
    return _pluralName;
  }

  /**
   * Set the plural name of this item.
   * @param pluralName the plural name to set (may be <code>null</code>).
   */
  public void setPluralName(String pluralName)
  {
    _pluralName=pluralName;
  }

  /**
   * Get the category of this item.
   * @return an item category.
   */
  public ItemCategory getCategory()
  {
    return ItemCategory.ITEM;
  }

  /**
   * Get the item class.
   * @return An item class.
   */
  public ItemClass getItemClass()
  {
    return _itemClass;
  }

  /**
   * Set the item class.
   * @param itemClass Item class to set.
   */
  public void setItemClass(ItemClass itemClass)
  {
    _itemClass=itemClass;
  }

  /**
   * Get the tier for this item.
   * @return A tier or <code>null</code> if not found.
   */
  public Integer getTier()
  {
    return _tier;
  }

  /**
   * Set the item tier.
   * @param tier Tier to set.
   */
  public void setTier(Integer tier)
  {
    _tier=tier;
  }

  /**
   * Get the category of this item.
   * @return a sub-category.
   */
  public String getSubCategory()
  {
    return _itemClass!=null?_itemClass.getLabel():null;
  }

  /**
   * Get the equipment category.
   * @return An equipment category or <code>null</code>.
   */
  public EquipmentCategory getEquipmentCategory()
  {
    return _equipmentCategory;
  }

  /**
   * Set the equipment category.
   * @param equipmentCategory Equipment category to set.
   */
  public void setEquipmentCategory(EquipmentCategory equipmentCategory)
  {
    _equipmentCategory=equipmentCategory;
  }

  /**
   * Get the item binding.
   * @return an item binding.
   */
  public ItemBinding getBinding()
  {
    return _binding;
  }

  /**
   * Set the binding of this item.
   * @param binding the binding to set.
   */
  public void setBinding(ItemBinding binding)
  {
    _binding=binding;
  }

  /**
   * Indicates if this item is unique or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isUnique()
  {
    return _unique;
  }

  /**
   * Set the unicity of this item.
   * @param unique <code>true</code> to make this item unique, <code>false</code> otherwise.
   */
  public void setUnique(boolean unique)
  {
    _unique=unique;
  }

  /**
   * Get the item stats.
   * @return a set of stats.
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }

  /**
   * Set the item stats using the stats provider.
   */
  public void setStatsFromStatsProvider()
  {
    Integer statsLevel=getItemLevelForStats();
    if (statsLevel!=null)
    {
      if (_statsProvider!=null)
      {
        _stats=_statsProvider.getStats(1,statsLevel.intValue());
      }
    }
  }

  /**
   * Get the stats provider.
   * @return the stats provider (may be <code>null</code>).
   */
  public StatsProvider getStatsProvider()
  {
    return _statsProvider;
  }

  /**
   * Set the stats provider.
   * @param statsProvider Provider to set.
   */
  public void setStatsProvider(StatsProvider statsProvider)
  {
    _statsProvider=statsProvider;
  }

  /**
   * Get the number of essence slots.
   * @return the number of essence slots.
   */
  public int getEssenceSlots()
  {
    return (_essenceSlots!=null)?_essenceSlots.getSocketsCount():0;
  }

  /**
   * Get the essences slots setup.
   * @return A setup or <code>null</code> if no essence slots.
   */
  public EssencesSlotsSetup getEssenceSlotsSetup()
  {
    return _essenceSlots;
  }

  /**
   * Set the essence slots setup.
   * @param essenceSlots Setup to set.
   */
  public void setEssenceSlots(EssencesSlotsSetup essenceSlots)
  {
    _essenceSlots=essenceSlots;
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
   * Get the sturdiness of this item.
   * @return a sturdiness value.
   */
  public ItemSturdiness getSturdiness()
  {
    return _sturdiness;
  }

  /**
   * Set the sturdiness of this item.
   * @param sturdiness the sturdiness to set.
   */
  public void setSturdiness(ItemSturdiness sturdiness)
  {
    _sturdiness=sturdiness;
  }

  /**
   * Get the minimum level required to use this item.
   * @return a minimum level value or <code>null</code>.
   */
  public Integer getMinLevel()
  {
    return _requirements.getMinLevel();
  }

  /**
   * Set the minimum level required to use this item.
   * @param minLevel the minimum level as an integer value,
   * or <code>null</code> for no restriction.
   */
  public void setMinLevel(Integer minLevel)
  {
    _requirements.setLevelRange(minLevel,getMaxLevel());
  }

  /**
   * Set the level range requirement.
   * @param minLevel Minimum level (may be <code>null</code>).
   * @param maxLevel Maximum level (may be <code>null</code>).
   */
  public void setLevelRange(Integer minLevel, Integer maxLevel)
  {
    _requirements.setLevelRange(minLevel,maxLevel);
  }

  /**
   * Get the maximum level to use this item.
   * @return a maximum level value or <code>null</code>.
   */
  public Integer getMaxLevel()
  {
    return _requirements.getMaxLevel();
  }

  /**
   * Get the item level.
   * @return a level value or <code>null</code>.
   */
  public final Integer getItemLevel()
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
   * Get the item level offset.
   * @return a level offset value or <code>null</code>.
   */
  public Integer getItemLevelOffset()
  {
    return _itemLevelOffset;
  }

  /**
   * Get the item level to use for stats.
   * @return the item level to use for stats.
   */
  public Integer getItemLevelForStats()
  {
    if (_itemLevelOffset!=null)
    {
      if (_itemLevel!=null)
      {
        return Integer.valueOf(_itemLevel.intValue()+_itemLevelOffset.intValue());
      }
      return _itemLevelOffset;
    }
    return _itemLevel;
  }

  /**
   * Set the item level offset.
   * @param itemLevelOffset the item level offset as an integer value, or <code>null</code>.
   */
  public void setItemLevelOffset(Integer itemLevelOffset)
  {
    _itemLevelOffset=itemLevelOffset;
  }

  /**
   * Get the required class to use this item.
   * @return a character class or <code>null</code>.
   */
  public AbstractClassDescription getRequiredClass()
  {
    return _requirements.getRequiredClass();
  }

  /**
   * Set the required class for this item.
   * @param characterClass a character class or <code>null</code> for no restriction.
   */
  public void setRequiredClass(ClassDescription characterClass)
  {
    _requirements.setRequiredClass(characterClass);
  }

  /**
   * Get the usage requirements for this item.
   * @return usage requirements.
   */
  public UsageRequirement getUsageRequirements()
  {
    return _requirements;
  }

  /**
   * Get description of this item.
   * @return an item description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this item.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null) description="";
    _description=description;
  }

  /**
   * Get the value table of this item.
   * @return a value table.
   */
  public QualityBasedValuesTable getValueTable()
  {
    return _value;
  }

  /**
   * Get the value of this item.
   * @return a value or <code>null</code> if none.
   */
  public Money getValueAsMoney()
  {
    if (_itemLevel!=null)
    {
      return getValue(_itemLevel.intValue());
    }
    return null;
  }

  /**
   * Get the value of this item at the given item level.
   * @param itemLevel Item level to use.
   * @return A value or <code>null</code> if none.
   */
  public Money getValue(int itemLevel)
  {
    Money ret=null;
    if ((_value!=null) && (_quality!=null))
    {
      Float valueFromTable=_value.getValue(_quality,itemLevel);
      if (valueFromTable!=null)
      {
        ret=new Money();
        ret.setRawValue(Math.round(valueFromTable.floatValue()));
      }
      else
      {
        LOGGER.warn("Could not build item value from table!");
      }
    }
    return ret;
  }

  /**
   * Set the value table of this item.
   * @param value the value to set.
   */
  public void setValueTable(QualityBasedValuesTable value)
  {
    _value=value;
  }

  /**
   * Get the maximum stackability of this item.
   * @return an integer value or <code>null</code> if it is not stackable.
   */
  public Integer getStackMax()
  {
    return _stackMax;
  }

  /**
   * Set the maximum stackability of this item.
   * @param stackMax the maximum size of stacks as an integer,
   * or <code>null</code> if it is not stackable.
   */
  public void setStackMax(Integer stackMax)
  {
    _stackMax=stackMax;
  }

  /**
   * Get the quality of this item.
   * @return a quality value.
   */
  public ItemQuality getQuality()
  {
    return _quality;
  }

  /**
   * Set the quality of this item.
   * @param quality the quality to set.
   */
  public void setQuality(ItemQuality quality)
  {
    _quality=quality;
  }

  /**
   * Indicates if this item is scalable or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isScalable()
  {
    return (_scaling!=null);
  }

  /**
   * Get munging data.
   * @return some munging data or <code>null</code> if none.
   */
  public Munging getMunging()
  {
    return (_scaling!=null)?_scaling.getMunging():null;
  }

  /**
   * Get scaling data.
   * @return scaling data or <code>null</code>.
   */
  public ScalingData getScaling()
  {
    return _scaling;
  }

  /**
   * Set scaling data.
   * @param scaling Scaling data.
   */
  public void setScaling(ScalingData scaling)
  {
    _scaling=scaling;
  }

  /**
   * Get spellcraft data.
   * @return some spellcraft data or <code>null</code> if none.
   */
  public ItemSpellcraft getSpellcraft()
  {
    return _spellcraft;
  }

  /**
   * Set spellcraft data.
   * @param spellcraft Spellcraft data.
   */
  public void setSpellcraft(ItemSpellcraft spellcraft)
  {
    _spellcraft=spellcraft;
  }

  /**
   * Get the details manager.
   * @return a details manager or <code>null</code> if no details.
   */
  public ItemDetailsManager getDetails()
  {
    return _details;
  }

  /**
   * Get the effects manager.
   * @return an effects manager or <code>null</code> if no effects.
   */
  public ItemEffectsManager getEffects()
  {
    return _effects;
  }

  /**
   * Dump the contents of this item as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Name: ").append(_name);
    if (_identifier!=0)
    {
      sb.append(" (id=");
      sb.append(_identifier);
      sb.append(')');
    }
    if (_icon!=null)
    {
      sb.append(" (icon=");
      sb.append(_icon);
      sb.append(')');
    }
    if (_equipmentLocation!=null)
    {
      sb.append(" (");
      sb.append(_equipmentLocation);
      sb.append(')');
    }
    if (_essenceSlots!=null)
    {
      sb.append(" (slot(s): ");
      sb.append(_essenceSlots.toPersistenceString());
      sb.append(')');
    }
    {
      sb.append(" (");
      sb.append(getCategory());
      sb.append(')');
    }
    if (_itemClass!=null)
    {
      sb.append(" (");
      sb.append(_itemClass.getLabel());
      sb.append(')');
    }
    if (_durability!=null)
    {
      sb.append(" (Durability=");
      sb.append(_durability);
      sb.append(')');
    }
    if (_sturdiness!=null)
    {
      sb.append(" (");
      sb.append(_sturdiness);
      sb.append(')');
    }
    if (_quality!=null)
    {
      sb.append(" (Quality=");
      sb.append(_quality);
      sb.append(')');
    }
    if (_unique)
    {
      sb.append(" (unique)");
    }
    if (_binding!=null)
    {
      sb.append(" (");
      sb.append(_binding);
      sb.append(')');
    }
    if (!_requirements.isEmpty())
    {
      sb.append(" (Requirements=");
      sb.append(_requirements);
      sb.append(')');
    }
    if (_itemLevel!=null)
    {
      sb.append(" (Item level=");
      sb.append(_itemLevel);
      sb.append(')');
    }
    if (_itemLevelOffset!=null)
    {
      sb.append(" (Item level offset=");
      sb.append(_itemLevelOffset);
      sb.append(')');
    }
    if (_value!=null)
    {
      sb.append(" (Value=");
      sb.append(_value);
      sb.append(')');
    }
    if (_stackMax!=null)
    {
      sb.append(" (Stacks=");
      sb.append(_stackMax);
      sb.append(')');
    }
    if (_scaling!=null)
    {
      sb.append(" (Scaling=");
      sb.append(_scaling);
      sb.append(')');
    }
    if (_spellcraft!=null)
    {
      sb.append(" (Spellcraft=");
      sb.append(_spellcraft);
      sb.append(')');
    }
    sb.append(EndOfLine.NATIVE_EOL);
    // Description
    if ((_description!=null) && (!_description.isEmpty()))
    {
      sb.append(_description).append(EndOfLine.NATIVE_EOL);
    }
    // Stats
    sb.append("Stats: ").append(_stats).append(EndOfLine.NATIVE_EOL);
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return _identifier+": "+_name+" ("+_itemLevel+")";
  }

  /**
   * Add a detail to the given item.
   * @param item Item to use.
   * @param detail Detail to add.
   */
  public static void addDetail(Item item, ItemDetail detail)
  {
    ItemDetailsManager mgr=item.getDetails();
    if (mgr==null)
    {
      mgr=new ItemDetailsManager();
      item._details=mgr;
    }
    mgr.addItemDetail(detail);
  }

  /**
   * Add an effect to the given item.
   * @param item Item to use.
   * @param type Use case.
   * @param effect Effect to add.
   */
  public static void addEffect(Item item, ItemEffectsManager.Type type, EffectGenerator effect)
  {
    ItemEffectsManager mgr=item.getEffects();
    if (mgr==null)
    {
      mgr=new ItemEffectsManager();
      item._effects=mgr;
    }
    mgr.addEffect(type,effect);
  }
}
