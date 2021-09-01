package delta.games.lotro.lore.relics.melding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.Named;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.comparators.RelicNameComparator;

/**
 * Output of a relic melding.
 * @author DAM
 */
public class MeldingOutput
{
  private List<RelicMeldingOutputEntry> _entries;

  /**
   * Constructor.
   */
  public MeldingOutput()
  {
    _entries=new ArrayList<RelicMeldingOutputEntry>();
  }

  /**
   * Add an output.
   * @param relic Relic to add.
   * @param weight Weight.
   */
  public void addOutput(Relic relic, int weight)
  {
    RelicMeldingOutputEntry newRelic=new RelicMeldingOutputEntry(weight,relic);
    _entries.add(newRelic);
  }

  /**
   * Add an output.
   * @param item Item to add.
   * @param weight Weight.
   */
  public void addOutput(Item item, int weight)
  {
    RelicMeldingOutputEntry newItem=new RelicMeldingOutputEntry(weight,item);
    _entries.add(newItem);
  }

  /**
   * Get the first output.
   * @return A relic/item or <code>null</code> if no output defined.
   */
  public Named getFirstResult()
  {
    if (_entries.size()>0)
    {
      RelicMeldingOutputEntry entry=_entries.get(0);
      return entry.getResult();
    }
    return null;
  }

  /**
   * Get the first relic output.
   * @return A relic or <code>null</code> if no output defined.
   */
  public Item getItemRelic()
  {
    if (_entries.size()>0)
    {
      return _entries.get(0).getItem();
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
    for(RelicMeldingOutputEntry relic : _entries)
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
    return new ArrayList<RelicMeldingOutputEntry>(_entries);
  }

  /**
   * Indicates if the given relic is a possible result or not.
   * @param relicId Identifier of the relic to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isResultRelic(int relicId)
  {
    for(RelicMeldingOutputEntry entry : _entries)
    {
      Relic relic=entry.getRelic();
      if ((relic!=null) && (relic.getIdentifier()==relicId))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Indicates if the given item is a possible result or not.
   * @param itemId Identifier of the item to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isResultItem(int itemId)
  {
    for(RelicMeldingOutputEntry entry : _entries)
    {
      Item item=entry.getItem();
      if ((item!=null) && (item.getIdentifier()==itemId))
      {
        return true;
      }
    }
    return false;
  }
}
