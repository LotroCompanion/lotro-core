package delta.games.lotro.character.stats.computer;

import java.util.List;

import delta.games.lotro.character.stats.StatsSetElement;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.utils.NumericUtils;

/**
 * Update values for 'substract' contribs.
 * @author DAM
 */
public class SubstractContribsComputer
{
  /**
   * Update substract contribs to use 'add' contribs.
   * @param contribs Contribs to update.
   */
  public void handleSubstractContribs(List<StatsContribution> contribs)
  {
    for(StatsContribution contrib : contribs)
    {
      for(StatsSetElement element : contrib.getStats().getStatElements())
      {
        StatOperator operator=element.getOperator();
        if (operator==StatOperator.SUBSTRACT)
        {
          Number newValue=NumericUtils.negate(element.getValue());
          element.setValue(newValue);
          element.setOperator(StatOperator.ADD);
        }
      }
    }
  }
}
