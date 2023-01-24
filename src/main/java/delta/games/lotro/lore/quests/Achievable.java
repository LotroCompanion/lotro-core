package delta.games.lotro.lore.quests;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.lore.maps.MapDescription;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.webStore.WebStoreItem;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;

/**
 * Base class for quests and deeds.
 * @author DAM
 */
public abstract class Achievable implements Identifiable
{
  /**
   * Identifier.
   */
  private int _identifier;
  /**
   * Name. Can be empty be not <code>null</code>.
   */
  private String _name;
  /**
   * Category. Can be empty be not <code>null</code>.
   */
  private String _category;
  /**
   * Requirements.
   */
  private UsageRequirement _requirement;
  /**
   * Challenge level (never null).
   */
  private ChallengeLevel _challengeLevel;

  // Flags
  /**
   * Hidden?
   */
  private boolean _hidden;
  /**
   * Monster Play?
   */
  private boolean _monsterPlay;
  /**
   * Description.
   */
  private String _description;
  /**
   * Objectives.
   */
  private ObjectivesManager _objectives;
  /**
   * Rewards.
   */
  private Rewards _rewards;
  /**
   * Quest requirements.
   */
  private AbstractAchievableRequirement _questRequirement;
  /**
   * World events requirements.
   */
  private AbstractWorldEventCondition _worldEventsRequirement;
  /**
   * Maps.
   */
  private List<MapDescription> _maps;
  /**
   * Web store item.
   */
  private WebStoreItem _webStoreItem;

  /**
   * Constructor.
   */
  protected Achievable()
  {
    _identifier=0;
    _name="";
    _category="";
    _requirement=new UsageRequirement();
    _challengeLevel=ChallengeLevel.ONE;
    _hidden=false;
    _monsterPlay=false;
    _description="";
    _objectives=new ObjectivesManager();
    _rewards=new Rewards();
    _questRequirement=null;
    _maps=new ArrayList<MapDescription>();
    _webStoreItem=null;
  }

  /**
   * Get the identifier of this achievable.
   * @return the identifier of this achievable.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this achievable.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the name of this achievable.
   * @return the name of this achievable.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this achievable.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null) name="";
    _name=name;
  }

  /**
   * Get the category of this achievable.
   * @return the category of this achievable.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this achievable. 
   * @param category the category to set.
   */
  public void setCategory(String category)
  {
    if (category==null) category="";
    _category=category;
  }

  /**
   * Get the minimum level for this achievable.
   * @return the minimumLevel for this achievable.
   */
  public Integer getMinimumLevel()
  {
    return _requirement.getMinLevel();
  }

  /**
   * Set the minimum level for this achievable.
   * @param minimumLevel the minimum level to set.
   */
  public void setMinimumLevel(Integer minimumLevel)
  {
    _requirement.setMinLevel(minimumLevel);
  }

  /**
   * Set the required race for this achievable.
   * @param race the race to set (or <code>null</code>).
   */
  public void setRequiredRace(RaceDescription race)
  {
    _requirement.addAllowedRace(race);
  }

  /**
   * Set the required class for this achievable.
   * @param characterClass the class to set (or <code>null</code>).
   */
  public void setRequiredClass(ClassDescription characterClass)
  {
    _requirement.removeClassRequirement();
    _requirement.addAllowedClass(characterClass);
  }

  /**
   * Get the usage requirement.
   * @return the usage requirement.
   */
  public UsageRequirement getUsageRequirement()
  {
    return _requirement;
  }

  /**
   * Get the challenge level.
   * @return A challenge level.
   */
  public ChallengeLevel getChallengeLevel()
  {
    return _challengeLevel;
  }

  /**
   * Set the challenge level.
   * @param challengeLevel value to set.
   */
  public void setChallengeLevel(ChallengeLevel challengeLevel)
  {
    _challengeLevel=challengeLevel;
  }

  /**
   * Indicates if this quest is obsolete/hidden or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isHidden()
  {
    return _hidden;
  }

  /**
   * Set the 'hidden' flag.
   * @param hidden value to set.
   */
  public void setHidden(boolean hidden)
  {
    _hidden=hidden;
  }

  /**
   * Indicates if this quest is 'monster play' or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isMonsterPlay()
  {
    return _monsterPlay;
  }

  /**
   * Set the 'monster play' flag.
   * @param monsterPlay value to set.
   */
  public void setMonsterPlay(boolean monsterPlay)
  {
    _monsterPlay=monsterPlay;
  }

  /**
   * Get the description of this achievable.
   * @return the description of this achievable.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this achievable.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null) description="";
    _description=description;
  }

  /**
   * Get the objectives manager for this achievable.
   * @return the objectives manager for this achievable.
   */
  public ObjectivesManager getObjectives()
  {
    return _objectives;
  }

  /**
   * Get the rewards for this achievable.
   * @return the rewards.
   */
  public Rewards getRewards()
  {
    return _rewards;
  }

  /**
   * Get the quest requirements for this achievable. 
   * @return a quest requirement or <code>null</code>.
   */
  public AbstractAchievableRequirement getQuestRequirements()
  {
    return _questRequirement;
  }

  /**
   * Set the quest requirement.
   * @param questRequirement Requirement to set.
   */
  public void setQuestRequirements(AbstractAchievableRequirement questRequirement)
  {
    _questRequirement=questRequirement;
  }

  /**
   * Get the world events requirement.
   * @return A world events requirement or <code>null</code>.
   */
  public AbstractWorldEventCondition getWorldEventsRequirement()
  {
    return _worldEventsRequirement;
  }

  /**
   * Set the world events requirement.
   * @param worldEventsRequirement Requirement to set, may be <code>null</code>.
   */
  public void setWorldEventsRequirement(AbstractWorldEventCondition worldEventsRequirement)
  {
    _worldEventsRequirement=worldEventsRequirement;
  }

  /**
   * Add a map.
   * @param map Map to add.
   */
  public void addMap(MapDescription map)
  {
    _maps.add(map);
  }

  /**
   * Get the associated maps.
   * @return a possibly empty but never <code>null</code> list of maps.
   */
  public List<MapDescription> getMaps()
  {
    return _maps;
  }

  /**
   * Get the associated web store item, if any.
   * @return A web store item or <code>null</code>.
   */
  public WebStoreItem getWebStoreItem()
  {
    return _webStoreItem;
  }

  /**
   * Set the associated web store item.
   * @param webStoreItem Web store item to set.
   */
  public void setWebStoreItem(WebStoreItem webStoreItem)
  {
    _webStoreItem=webStoreItem;
  }

  
  /**
   * Dump the contents of this achievable as a string.
   * @return A readable string.
   */
  public abstract String dump();

  protected void dumpFirstLine(StringBuilder sb)
  {
    sb.append("Name: ").append(_name);
    if (_identifier!=0)
    {
      sb.append(" (");
      sb.append(_identifier);
      sb.append(')');
    }
    sb.append(" (challenge level=").append(_challengeLevel).append(")");
    if (_hidden)
    {
      sb.append(" (obsolete/hidden)");
    }
    if (_monsterPlay)
    {
      sb.append(" (monster play)");
    }
  }

  protected void dumpOtherLines(StringBuilder sb)
  {
    if (_category.length()>0)
    {
      sb.append("Category: ").append(_category).append(EndOfLine.NATIVE_EOL);
    }
    if (!_requirement.isEmpty())
    {
      sb.append("Requirements: ").append(_requirement).append(EndOfLine.NATIVE_EOL);
    }
    sb.append("Rewards: ").append(_rewards).append(EndOfLine.NATIVE_EOL);
    sb.append("Description: ").append(_description).append(EndOfLine.NATIVE_EOL);
    sb.append("Objectives: ").append(_objectives).append(EndOfLine.NATIVE_EOL);
    if (_questRequirement!=null)
    {
      sb.append("Prerequisites: ").append(_questRequirement).append(EndOfLine.NATIVE_EOL);
    }
    if (_maps.size()>0)
    {
      sb.append("Maps: ").append(_maps).append(EndOfLine.NATIVE_EOL);
    }
  }

  /**
   * Indicates if this achievable has geo data.
   * @return <code>true</code> if it has geo data.
   */
  public boolean hasGeoData()
  {
    for(Objective objective : _objectives.getObjectives())
    {
      if (objective.hasGeoData())
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
