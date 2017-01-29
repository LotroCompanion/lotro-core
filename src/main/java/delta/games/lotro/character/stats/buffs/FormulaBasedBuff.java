package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Buff based on a formula.
 * @author DAM
 */
public class FormulaBasedBuff extends AbstractBuffImpl
{
  private Formula _formula;
  private STAT _stat;
  private float[] _sliceCounts;

  /**
   * Constructor.
   * @param sliceCounts Slice count values.
   * @param stat Targeted stat.
   * @param formula Computing formula.
   */
  public FormulaBasedBuff(float[] sliceCounts, STAT stat, Formula formula)
  {
    _sliceCounts=sliceCounts;
    _stat=stat;
    _formula=formula;
  }

  @Override
  public BasicStatsSet getStats(CharacterData character, BuffInstance buff)
  {
    BasicStatsSet stats=new BasicStatsSet();
    Integer tier=buff.getTier();
    if ((tier!=null) && (tier.intValue()>0))
    {
      float sliceCount=_sliceCounts[tier.intValue()-1];
      int level=character.getLevel();
      float value=_formula.compute(level,sliceCount);
      stats.addStat(_stat,new FixedDecimalsInteger(value));
    }
    return stats;
  }

  @Override
  public List<Integer> getTiers()
  {
    List<Integer> ret=new ArrayList<Integer>();
    for(int tier=1;tier<=_sliceCounts.length;tier++)
    {
      ret.add(Integer.valueOf(tier));
    }
    return ret;
  }
}
