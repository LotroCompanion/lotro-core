package delta.games.lotro.lore.hobbies;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.lore.hobbies.rewards.HobbyRewards;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Description of a hobby.
 * @author DAM
 */
public class HobbyDescription implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private int _hobbyType;
  private String _description;
  private String _trainerDisplayInfo;
  private int _iconId;
  private int _dailyProficiencyGainLimit;
  private List<Item> _items;
  private int _minLevel;
  private String _proficiencyPropertyName;
  private String _proficiencyModifierPropertyName;
  private String _treasureProfileOverridePropertyName;
  private List<HobbyTitleEntry>_titles;
  // Rewards
  private HobbyRewards _rewards;

  /**
   * Constructor.
   */
  public HobbyDescription()
  {
    super();
    _identifier=0;
    _name="";
    _hobbyType=1; // Fishing
    _description="";
    _trainerDisplayInfo="";
    _iconId=0;
    _dailyProficiencyGainLimit=0;
    _items=new ArrayList<Item>();
    _minLevel=1;
    _proficiencyPropertyName="";
    _proficiencyModifierPropertyName="";
    _treasureProfileOverridePropertyName="";
    _titles=new ArrayList<HobbyTitleEntry>();
    _rewards=new HobbyRewards();
  }

  /**
   * Get the identifier of this hobby.
   * @return a hobby identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this hobby.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the name of this hobby.
   * @return a hobby name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this hobby.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Get the hobby type.
   * @return the hobby type.
   */
  public int getHobbyType()
  {
    return _hobbyType;
  }

  /**
   * Set the hobby type.
   * @param hobbyType the hobby type to set.
   */
  public void setHobbyType(int hobbyType)
  {
    _hobbyType=hobbyType;
  }

  /**
   * Get the description of this hobby.
   * @return a hobby description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this hobby.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Get the trainer display info.
   * @return a displayable message.
   */
  public String getTrainerDisplayInfo()
  {
    return _trainerDisplayInfo;
  }

  /**
   * Set the trainer display info.
   * @param trainerDisplayInfo the message to set.
   */
  public void setTrainerDisplayInfo(String trainerDisplayInfo)
  {
    _trainerDisplayInfo=trainerDisplayInfo;
  }

  /**
   * Get the hobby icon ID.
   * @return an icon ID.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the hobby icon ID.
   * @param iconId the icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the daily proficiency gain limit.
   * @return the daily proficiency gain limit.
   */
  public int getDailyProficiencyGainLimit()
  {
    return _dailyProficiencyGainLimit;
  }

  /**
   * Set the daily proficiency gain limit.
   * @param dailyProficiencyGainLimit the daily proficiency gain limit to set
   */
  public void setDailyProficiencyGainLimit(int dailyProficiencyGainLimit)
  {
    _dailyProficiencyGainLimit=dailyProficiencyGainLimit;
  }

  /**
   * Add an item.
   * @param item Item to add.
   */
  public void addItem(Item item)
  {
    _items.add(item);
  }

  /**
   * Get the items.
   * @return the items.
   */
  public List<Item> getItems()
  {
    return _items;
  }

  /**
   * Get the minimum level for this hobby.
   * @return the minimum level for this hobby.
   */
  public int getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Set the minimum level for this hobby.
   * @param minLevel the minimum level to set.
   */
  public void setMinLevel(int minLevel)
  {
    _minLevel=minLevel;
  }

  /**
   * Get the property name for the proficiency value.
   * @return a property name.
   */
  public String getProficiencyPropertyName()
  {
    return _proficiencyPropertyName;
  }

  /**
   * Set the property name for the proficiency value.
   * @param proficiencyPropertyName the property name to set.
   */
  public void setProficiencyPropertyName(String proficiencyPropertyName)
  {
    _proficiencyPropertyName=proficiencyPropertyName;
  }

  /**
   * Get the property name for the proficiency modifier.
   * @return a property name.
   */
  public String getProficiencyModifierPropertyName()
  {
    return _proficiencyModifierPropertyName;
  }

  /**
   * Set the property name for the proficiency modifier.
   * @param proficiencyModifierPropertyName the property name to set.
   */
  public void setProficiencyModifierPropertyName(String proficiencyModifierPropertyName)
  {
    _proficiencyModifierPropertyName=proficiencyModifierPropertyName;
  }

  /**
   * Get the property name for the treasure profile override.
   * @return a property name.
   */
  public String getTreasureProfileOverridePropertyName()
  {
    return _treasureProfileOverridePropertyName;
  }

  /**
   * Set the property name for the treasure profile override.
   * @param treasureProfileOverridePropertyName the property name to set.
   */
  public void setTreasureProfileOverridePropertyName(String treasureProfileOverridePropertyName)
  {
    _treasureProfileOverridePropertyName=treasureProfileOverridePropertyName;
  }

  /**
   * Add a title.
   * @param entry Entry to add.
   */
  public void addTitle(HobbyTitleEntry entry)
  {
    _titles.add(entry);
  }

  /**
   * Get the titles.
   * @return the titles.
   */
  public List<HobbyTitleEntry> getTitles()
  {
    return _titles;
  }

  /**
   * Get the rewards for this hobby.
   * @return the rewards for this hobby.
   */
  public HobbyRewards getRewards()
  {
    return _rewards;
  }

  /**
   * Get the title associated with the given proficiency.
   * @param proficiency Proficiency to use.
   * @return A title or <code>null</code>.
   */
  public TitleDescription getTitleForProficiency(int proficiency)
  {
    TitleDescription ret=null;
    for(HobbyTitleEntry entry : _titles)
    {
      int minProficiency=entry.getProficiency();
      if (proficiency>=minProficiency)
      {
        ret=entry.getTitle();
      }
    }
    return ret;
  }
}
