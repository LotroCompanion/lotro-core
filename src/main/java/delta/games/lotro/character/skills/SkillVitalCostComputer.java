package delta.games.lotro.character.skills;

import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.common.enums.VitalType;
import delta.games.lotro.common.enums.VitalTypes;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.utils.maths.Progression;

/**
 * Vital cost computer.
 * @author DAM
 */
public class SkillVitalCostComputer
{
  private CharacterDataForSkills _character;

  /**
   * Constructor.
   * @param character Character to use.
   */
  public SkillVitalCostComputer(CharacterDataForSkills character)
  {
    _character=character;
  }

  private float getStat(VitalType type)
  {
    if (type==VitalTypes.MORALE)
    {
      return _character.getStat(WellKnownStat.MORALE);
    }
    if (type==VitalTypes.POWER)
    {
      return _character.getStat(WellKnownStat.POWER);
    }
    return 0;
  }

  private Float getProgressionCost(SkillVitalCost costDefinition)
  {
    Progression progression=costDefinition.getPointsProgression();
    if (progression==null)
    {
      return null;
    }
    Float progValue=progression.getValue(_character.getLevel());
    return progValue;
  }

  /**
   * Get the vital cost from the given definition.
   * @param costDefinition Input.
   * @return A cost value or <code>null</code> if no cost.
   */
  public Float getVitalCost(SkillVitalCost costDefinition)
  {
    if (costDefinition==null)
    {
      return null;
    }
    // Consumes all?
    if (costDefinition.isConsumesAll())
    {
      float cost=getStat(costDefinition.getVitalType());
      return Float.valueOf(cost);
    }
    float totalCost=0;
    // Points
    Float pointsCost=costDefinition.getPoints();
    if (pointsCost!=null)
    {
      totalCost+=pointsCost.floatValue();
    }
    // Progression
    Float progressionCost=getProgressionCost(costDefinition);
    if (progressionCost!=null)
    {
      totalCost+=progressionCost.floatValue();
    }
    // Percentage
    Float percentage=costDefinition.getPercentage();
    if (percentage!=null)
    {
      float stat=getStat(costDefinition.getVitalType());
      float percentageCost=stat*percentage.floatValue();
      totalCost+=percentageCost;
    }
    // Modifiers
    ModPropertyList mods=costDefinition.getVitalMods();
    float costMultiplier=_character.computeMultiplicativeModifiers(mods);
    float cost=totalCost*costMultiplier;
    return Float.valueOf(cost);
  }
}
