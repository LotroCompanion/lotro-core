package delta.games.lotro.lore.items.legendary.relics;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.common.treasure.RelicsList;
import delta.games.lotro.lore.items.Container;

/**
 * Container-specific data (relics).
 * @author DAM
 */
public class RelicsContainer extends Container
{
  private RelicsList _relicsList;
  private Relic _relic;

  /**
   * Constructor.
   * @param identifier Container identifier.
   */
  public RelicsContainer(int identifier)
  {
    super(identifier);
  }

  /**
   * Get the relics list for this container, if any.
   * @return a relics list or <code>null</code>.
   */
  public RelicsList getRelicsList()
  {
    return _relicsList;
  }

  /**
   * Set the relics list for this container.
   * @param relicsList the relics list to set.
   */
  public void setRelicsList(RelicsList relicsList)
  {
    _relicsList=relicsList;
  }

  /**
   * Get the relic for this container.
   * @return a relic or <code>null</code>.
   */
  public Relic getRelic()
  {
    return _relic;
  }

  /**
   * Set the relic for this container.
   * @param relic the relic to set.
   */
  public void setRelic(Relic relic)
  {
    _relic=relic;
  }

  /**
   * Indicates if this container may contain the given relic.
   * @param relicId Identifier of the relic to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(int relicId)
  {
    if ((_relic!=null) && (_relic.getIdentifier()==relicId))
    {
      return true;
    }
    if ((_relicsList!=null) && (_relicsList.contains(relicId)))
    {
      return true;
    }
    return false;
  }


  /**
   * Get the identifiers of the reachable relics.
   * @return A set of relic identifiers.
   */
  public Set<Integer> getRelicds()
  {
    Set<Integer> ret=new HashSet<Integer>();
    if (_relic!=null)
    {
      ret.add(Integer.valueOf(_relic.getIdentifier()));
    }
    if (_relicsList!=null)
    {
      ret.addAll(_relicsList.getRelicIds());
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return "Relic container: id="+getIdentifier();
  }
}
