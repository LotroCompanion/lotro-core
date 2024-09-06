package delta.games.lotro.character.skills.attack;

/**
 * @author dmorcellet
 */
public class SkillDetails
{
  public DamageQualifier getDamagerQualifier()
  {
    return DamageQualifier.MELEE;
  }

  public Float getActionDurationContribution()
  {
    // From Skill_ActionDurationContribution
    return null;
  }

  public Integer getInductionActionID()
  {
    return null;
  }
}
