package delta.games.lotro.lore.quests.objectives;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.agents.mobs.MobDescription;

/**
 * Monster died condition.
 * @author DAM
 */
public class MonsterDiedCondition extends ObjectiveCondition
{
  private MobDescription _mob;
  private List<MobSelection> _mobSelections;

  /**
   * Constructor.
   */
  public MonsterDiedCondition()
  {
    super();
    _mobSelections=new ArrayList<MobSelection>();
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.MONSTER_DIED;
  }

  /**
   * Get the mob.
   * @return a mob or <code>null</code>.
   */
  public MobDescription getMob()
  {
    return _mob;
  }

  /**
   * Set the mob.
   * @param mob the mob to set. May be <code>null</code>.
   */
  public void setMob(MobDescription mob)
  {
    _mob=mob;
  }

  /**
   * Get the mob name.
   * @return a mob name or <code>null</code> if mob identifier is not set.
   */
  public String getMobName()
  {
    return _mob!=null?_mob.getName():null;
  }

  /**
   * Get the mob selections.
   * @return A possibly empty but not <code>null</code> list of mob selections.
   */
  public List<MobSelection> getMobSelections()
  {
    return _mobSelections;
  }

  /**
   * Mob selection.
   * @author DAM
   */
  public static class MobSelection
  {
    private String _where;
    private EntityClassification _what;

    /**
     * Get the location of mobs.
     * @return a location string (internal game code).
     */
    public String getWhere()
    {
      return _where;
    }

    /**
     * Set the location of mob.
     * @param where the location to set.
     */
    public void setWhere(String where)
    {
      _where=where;
    }

    /**
     * Get the mob kind.
     * @return a mob kind description (genus/species/subspecies).
     */
    public EntityClassification getWhat()
    {
      return _what;
    }

    /**
     * Set the mob kind.
     * @param what the mob kind to set.
     */
    public void setWhat(EntityClassification what)
    {
      _what=what;
    }

    @Override
    public String toString()
    {
      return _what + " in " + _where;
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("#").append(getIndex());
    sb.append(": ").append("MonsterDiedCondition: mob="+_mob+", selections="+_mobSelections);
    return sb.toString();
  }
}
