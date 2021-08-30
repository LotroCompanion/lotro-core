package delta.games.lotro.lore.relics.melding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.comparators.RelicNameComparator;

/**
 * Output of a relic melding.
 * @author DAM
 */
public class MeldingOutput
{
  private List<RelicMeldingOutputEntry> _relics;

  /**
   * Constructor.
   */
  public MeldingOutput()
  {
    _relics=new ArrayList<RelicMeldingOutputEntry>();
  }

  /**
   * Add an output.
   * @param relic Relic to add.
   * @param weight Weight.
   */
  public void addOutput(Relic relic, int weight)
  {
    RelicMeldingOutputEntry newRelic=new RelicMeldingOutputEntry(weight,relic);
    _relics.add(newRelic);
  }

  /**
   * Add an output.
   * @param item Item to add.
   * @param weight Weight.
   */
  public void addOutput(Item item, int weight)
  {
    RelicMeldingOutputEntry newItem=new RelicMeldingOutputEntry(weight,item);
    _relics.add(newItem);
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
    for(RelicMeldingOutputEntry relic : _relics)
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
  public List<RelicMeldingOutputEntry> getPossibleOutputs()
  {
    return new ArrayList<RelicMeldingOutputEntry>(_relics);
  }
}
