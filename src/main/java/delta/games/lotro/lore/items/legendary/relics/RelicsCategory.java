package delta.games.lotro.lore.items.legendary.relics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.RunicTier;

/**
 * Manages all relics for a given category.
 * @author DAM
 */
public class RelicsCategory
{
  private RunicTier _tier;
  private Map<Integer,Relic> _relicById;
  private List<Relic> _relics;

  /**
   * Constructor.
   * @param tier Managed relic tier.
   */
  public RelicsCategory(RunicTier tier)
  {
    _tier=tier;
    _relicById=new HashMap<Integer,Relic>();
    _relics=new ArrayList<Relic>();
  }

  /**
   * Get the relic tier.
   * @return the relic tier.
   */
  public RunicTier getTier()
  {
    return _tier;
  }

  /**
   * Get a relic by its name.
   * @param name Name of the relic to get.
   * @return A relic or <code>null</code> if not found.
   */
  public Relic getByName(String name)
  {
    for(Relic relic : _relics)
    {
      if (relic.getName().equals(name))
      {
        return relic;
      }
    }
    return null;
  }

  /**
   * Get a relic using its identifier.
   * @param id Identifier.
   * @return A relic or <code>null</code> if not found.
   */
  public Relic getById(int id)
  {
    return _relicById.get(Integer.valueOf(id));
  }

  /**
   * Add a relic.
   * @param relic Relic to add.
   */
  public void addRelic(Relic relic)
  {
    _relics.add(relic);
    Integer id=Integer.valueOf(relic.getIdentifier());
    _relicById.put(id,relic);
  }

  /**
   * Get a list of all relics included in this category.
   * @return a list of all managed relics.
   */
  public List<Relic> getAllRelics()
  {
    return new ArrayList<Relic>(_relics);
  }

  @Override
  public String toString()
  {
    return _tier.getLabel();
  }
}
