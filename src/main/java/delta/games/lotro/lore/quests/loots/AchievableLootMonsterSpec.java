package delta.games.lotro.lore.quests.loots;

import delta.games.lotro.lore.agents.AgentClassification;
import delta.games.lotro.lore.agents.mobs.MobLocation;

/**
 * Monster specification for achievable loot data.
 * @author DAM
 */
public class AchievableLootMonsterSpec
{
  private MobLocation _location;
  private AgentClassification _classification;

  /**
   * Constructor.
   * @param location Mob location.
   * @param classification Mob classification.
   */
  public AchievableLootMonsterSpec(MobLocation location, AgentClassification classification)
  {
    _location=location;
    _classification=classification;
  }

  /**
   * Get the mob location.
   * @return the mob location.
   */
  public MobLocation getLocation()
  {
    return _location;
  }

  /**
   * Get the mob classification.
   * @return the mob classification.
   */
  public AgentClassification getClassification()
  {
    return _classification;
  }

  @Override
  public String toString()
  {
    return "Location: "+_location+", classification: "+_classification;
  }
}
