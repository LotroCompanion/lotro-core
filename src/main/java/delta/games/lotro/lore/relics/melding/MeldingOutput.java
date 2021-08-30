package delta.games.lotro.lore.relics.melding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.comparators.RelicNameComparator;
import delta.games.lotro.lore.relics.RelicAndWeight;

/**
 * Output of a relic melding.
 * @author DAM
 */
public class MeldingOutput
{
  private List<RelicAndWeight> _relics;

  /**
   * Constructor.
   */
  public MeldingOutput()
  {
    _relics=new ArrayList<RelicAndWeight>();
  }

  /**
   * Add an output.
   * @param relic Relic to add.
   * @param weight Weight.
   */
  public void addOutput(Relic relic, int weight)
  {
    RelicAndWeight newRelic=new RelicAndWeight(weight,relic);
    _relics.add(newRelic);
  }

  /**
   * Get the first relic output.
   * @return A relic or <code>null</code> if no output defined.
   */
  public Relic getFirstRelic()
  {
    if (_relics.size()>0)
    {
      return _relics.get(0).getRelic();
    }
    return null;
  }

  /**
   * Get the list of possible relics.
   * @return A list of relics, sorted by name.
   */
  public List<Relic> getPossibleRelics()
  {
    List<Relic> ret=new ArrayList<Relic>();
    for(RelicAndWeight relic : _relics)
    {
      ret.add(relic.getRelic());
    }
    Collections.sort(ret,new RelicNameComparator());
    return ret;
  }

  /**
   * Get the possible outputs.
   * @return A list of relic/weight pairs.
   */
  public List<RelicAndWeight> getPossibleOutputs()
  {
    return new ArrayList<RelicAndWeight>(_relics);
  }
}
