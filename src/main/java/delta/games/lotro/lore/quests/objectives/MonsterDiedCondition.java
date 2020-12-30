package delta.games.lotro.lore.quests.objectives;

import java.util.ArrayList;
import java.util.List;

/**
 * Monster died condition.
 * @author DAM
 */
public class MonsterDiedCondition extends ObjectiveCondition
{
  private Integer _mobId;
  private String _mobName;
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
   * Get the mob identifier.
   * @return a mob identifier or <code>null</code>.
   */
  public Integer getMobId()
  {
    return _mobId;
  }

  /**
   * Set the mob identifier.
   * @param mobId the identifier to set. May be <code>null</code>.
   */
  public void setMobId(Integer mobId)
  {
    _mobId=mobId;
  }

  /**
   * Get the mob name.
   * @return a mob name or <code>null</code> if mob identifier is not set.
   */
  public String getMobName()
  {
    return _mobName;
  }

  /**
   * Set the mob name.
   * @param mobName Name to set. May be <code>null</code>.
   */
  public void setMobName(String mobName)
  {
    _mobName=mobName;
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
    private String _what;

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
    public String getWhat()
    {
      return _what;
    }

    /**
     * Set the mob kind.
     * @param what the mob kind to set.
     */
    public void setWhat(String what)
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
    sb.append(": ").append("MonsterDiedCondition: "+_mobId+", name="+_mobName+", selections="+_mobSelections);
    return sb.toString();
  }
}
