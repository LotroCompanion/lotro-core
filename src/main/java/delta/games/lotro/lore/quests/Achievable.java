package delta.games.lotro.lore.quests;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.utils.Proxy;

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
  private boolean _obsolete;
  /**
   * Description.
   */
  private String _description;
  /**
   * Objectives.
   */
  private ObjectivesManager _objectives;
  /**
   * Reward.
   */
  private Rewards _rewards;
  /**
   * Pre-requisites.
   */
  private List<Proxy<Achievable>> _prerequisiteAchievables;

  /**
   * Constructor.
   */
  public Achievable()
  {
    _identifier=0;
    _name="";
    _category="";
    _requirement=new UsageRequirement();
    _challengeLevel=ChallengeLevel.ONE;
    _obsolete=false;
    _description="";
    _objectives=new ObjectivesManager();
    _rewards=new Rewards();
    _prerequisiteAchievables=new ArrayList<Proxy<Achievable>>();
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
   * Get the maximum level for this achievable.
   * @return the maximumLevel for this achievable.
   */
  public Integer getMaximumLevel()
  {
    return _requirement.getMaxLevel();
  }

  /**
   * Set the maximum level for this achievable.
   * @param maximumLevel the maxiimum level to set.
   */
  public void setMaximumLevel(Integer maximumLevel)
  {
    _requirement.setMaxLevel(maximumLevel);
  }

  /**
   * Set the required race for this achievable.
   * @param race the race to set (or <code>null</code>).
   */
  public void setRequiredRace(Race race)
  {
    _requirement.addAllowedRace(race);
  }

  /**
   * Set the required class for this achievable.
   * @param characterClass the class to set (or <code>null</code>).
   */
  public void setRequiredClass(CharacterClass characterClass)
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
  public boolean isObsolete()
  {
    return _obsolete;
  }

  /**
   * Set the 'obsolete' flag.
   * @param obsolete value to set.
   */
  public void setObsolete(boolean obsolete)
  {
    _obsolete=obsolete;
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
   * Get the list of the 'pre-requisite' achievables for this achievable. 
   * @return a possibly empty list of achievables proxies.
   */
  public List<Proxy<Achievable>> getPrerequisites()
  {
    return _prerequisiteAchievables;
  }

  /**
   * Add a 'pre-requisite' achievable.
   * @param prerequisite proxy to add as a 'pre-requisite'.
   */
  public void addPrerequisite(Proxy<Achievable> prerequisite)
  {
    _prerequisiteAchievables.add(prerequisite);
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
    if (_obsolete)
    {
      sb.append(" (obsolete)");
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
    if (_prerequisiteAchievables.size()>0)
    {
      sb.append("Prerequisites: ").append(_prerequisiteAchievables).append(EndOfLine.NATIVE_EOL);
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
