package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.framework.objects.data.Identifiable;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Money;
import delta.games.lotro.lore.items.bonus.BonusManager;
import delta.games.lotro.lore.items.bonus.RawBonusParser;
import delta.games.lotro.lore.items.essences.EssencesSet;

/**
 * Item description.
 * @author DAM
 */
public class Item implements Identifiable<Long>
{
  // Database primary key
  private Long _primaryKey;
  // Item private identifier
  private int _identifier;
  // Item key: "Jacket_of_the_Impossible_Shot", ...
  private String _key;
  // Items set identifier (may be null)
  private String _setKey;
  // Associated set (may be null)
  private ItemsSet _set;
  // Slot
  private EquipmentLocation _equipmentLocation;
  // Item name "Jacket of the Impossible Shot"
  private String _name;
  // Item birth name (given by the crafter) (may be <code>null</code>)
  private String _birthName;
  // Crafter name (may be <code>null</code>)
  private String _crafterName;
  // Icon URL
  private String _iconURL;
  // Item category: Armour, Tool, ...
  private ItemCategory _category;
  // Item sub-category: "Medium Armour", "Craft Tool", "Light Armour"
  // Weapon: Two-handed Sword, Staff, Halberd, Two-handed Hammer, Bow, Javelin,
  // Two-handed Club, One-handed Hammer, Spear, One-handed Club, One-handed Mace,
  // Crossbow, Dagger, One-handed Axe, One-handed Sword, Two-handed Axe
  // ???: Heavy, Warden, Light 
  // TODO "type" in data.lotro?
  private String _subCategory;
  // Item binding: "Bind on Acquire", ...
  // TODO data.lotro uses flags: bindOnAcquire="1" bindOnEquip="0"
  private ItemBinding _binding;
  // Is item unique or not?
  private boolean _unique;
  // Bonuses
  private List<String> _bonus;
  private BonusManager _bonusMgr;
  // Stats
  private BasicStatsSet _stats;
  // Essences
  private EssencesSet _essences;
  // Durability
  private Integer _durability;
  // Sturdiness (may be null)
  private ItemSturdiness _sturdiness;
  // Item level (may be null)
  private Integer _itemLevel;

  // Requirements:
  // Minimum level (may be null)
  private Integer _minLevel;
  // Class (may be null)
  // TODO list of classes
  private CharacterClass _class;
  // TODO list of races
  // TODO list of factions
  // TODO <gloryRank/>
  // TODO <traits/>
  // Full description
  private String _description;
  // Value
  private Money _value;
  // Stacking information
  private Integer _stackMax;

  // TODO Missing attrs: quality="Incomparable" isItemAdvancement="0" consumedOnUse="0" cooldown="" decoration="" instrument=""
  private ItemQuality _quality;

  private HashMap<String,String> _properties;

  /**
   * Constructor.
   */
  public Item()
  {
    super();
    _primaryKey=null;
    _identifier=0;
    _key=null;
    _setKey=null;
    _equipmentLocation=null;
    _name="";
    _iconURL=null;
    _category=ItemCategory.ITEM;
    _subCategory=null;
    _binding=null;
    _unique=false;
    _bonus=new ArrayList<String>();
    _bonusMgr=new BonusManager();
    _stats=new BasicStatsSet();
    _durability=null;
    _sturdiness=null;
    _minLevel=null;
    _itemLevel=null;
    _class=null;
    _description=null;
    _value=new Money();
    _stackMax=null;
    _quality=ItemQuality.COMMON;
    _properties=null;
  }

  /**
   * Get the primary key of this object.
   * @return the primary key of this object.
   */
  public Long getPrimaryKey()
  {
    return _primaryKey;
  }

  /**
   * Set the primary key of this object.
   * @param primaryKey Primary key to set.
   */
  public void setPrimaryKey(Long primaryKey)
  {
    _primaryKey=primaryKey;
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
   * Get the key of this item.
   * @return an item key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the key of the item.
   * @param key the key to set.
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * Set the key of the set this item belongs to.
   * @param setKey the set key to set (<code>null</code> if item belongs to no set).
   */
  public void setSetKey(String setKey)
  {
    _setKey=setKey;
  }

  /**
   * Get the identifier of the set this item belongs to.
   * @return a items set identifier or <code>null</code>.
   */
  public String getSetKey()
  {
    return _setKey;
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
   * Get equipment location.
   * @return an equimment location. 
   */
  public EquipmentLocation getEquipmentLocation()
  {
    return _equipmentLocation; 
  }

  /**
   * Set the equipment location.
   * @param equipmentLocation Location to set.
   */
  public void setEquipmentLocation(EquipmentLocation equipmentLocation) {
    _equipmentLocation=equipmentLocation;
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
   * Get the URL of the icon for this item.
   * @return an URL or <code>null</code>.
   */
  public String getIconURL()
  {
    return _iconURL;
  }

  /**
   * Set the URL for the icon of this item.
   * @param iconURL the URL to set.
   */
  public void setIconURL(String iconURL)
  {
    _iconURL=iconURL;
  }

  /**
   * Get the category of this item.
   * @return an item category.
   */
  public ItemCategory getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this item.
   * @param category the category to set.
   */
  public void setCategory(ItemCategory category)
  {
    _category=category;
  }

  /**
   * Get the sub-category of this item.
   * @return a sub-category.
   */
  public String getSubCategory()
  {
    return _subCategory;
  }

  /**
   * Set the sub-category of this item.
   * @param subCategory the sub-category to set.
   */
  public void setSubCategory(String subCategory)
  {
    _subCategory=subCategory;
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
   * Get the list of bonus for this item.
   * @return a list of bonus.
   */
  public List<String> getBonus()
  {
    // TODO encapsulation
    return _bonus;
  }

  /**
   * Set the list of bonus for this item.
   * @param bonuses the bonus to set.
   */
  public void setBonus(List<String> bonuses)
  {
    _bonus.clear();
    if (bonuses!=null)
    {
      _bonus.addAll(bonuses);
    }
    RawBonusParser parser=new RawBonusParser();
    _bonusMgr=parser.build(bonuses);
    if (parser.hasWarn())
    {
      System.out.println("Item: "+_identifier+", name="+_name);
      for(String bonus : bonuses)
      {
        System.out.println("\t"+bonus);
      }
    }
  }

  /**
   * Get the bonus manager.
   * @return the bonus manager.
   */
  public BonusManager getBonusManager()
  {
    return _bonusMgr;
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
   * Get the total stats for this item.
   * @return a set of stats.
   */
  public BasicStatsSet getTotalStats()
  {
    return _stats;
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
   * Get the required class to use this item.
   * @return a character class or <code>null</code>.
   */
  public CharacterClass getRequiredClass()
  {
    return _class;
  }

  /**
   * Set the required class for this item.
   * @param toonClass a character class or <code>null</code> for no restriction.
   */
  public void setRequiredClass(CharacterClass toonClass)
  {
    _class=toonClass;
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
    _description=description;
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
   * Set a property for this item.
   * @param key Property key.
   * @param value Property value.
   */
  public void setProperty(String key, String value)
  {
    if (_properties==null)
    {
      _properties=new HashMap<String,String>();
    }
    _properties.put(key,value);
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
    if (_key!=null)
    {
      sb.append(" (key=");
      sb.append(_key);
      sb.append(')');
    }
    if (_equipmentLocation!=null)
    {
      sb.append(" (");
      sb.append(_equipmentLocation);
      sb.append(')');
    }
    if (_category!=null)
    {
      sb.append(" (");
      sb.append(_category);
      sb.append(')');
    }
    if (_subCategory!=null)
    {
      sb.append(" (");
      sb.append(_subCategory);
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
    if (_stackMax!=null)
    {
      sb.append(" (Stacks=");
      sb.append(_stackMax);
      sb.append(')');
    }
    if (_class!=null)
    {
      sb.append(" (Required class=");
      sb.append(_class);
      sb.append(')');
    }
    sb.append(EndOfLine.NATIVE_EOL);
    if (_iconURL!=null)
    {
      sb.append(_iconURL);
      sb.append(EndOfLine.NATIVE_EOL);
    }
    if ((_bonus!=null) && (_bonus.size()>0))
    {
      for(String bonus : _bonus)
      {
        sb.append(bonus).append(EndOfLine.NATIVE_EOL);
      }
    }
    if (_description!=null)
    {
      sb.append(_description);
    }
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
