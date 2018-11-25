package delta.games.lotro.character.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.traits.TraitDescription;

/**
 * Trait tree branch.
 * @author DAM
 */
public class TraitTreeBranch
{
  private String _name;
  private TraitTreeProgression _progression;
  private Map<String,TraitDescription> _traitsByCell;

  /**
   * Constructor.
   * @param name Branch name.
   */
  public TraitTreeBranch(String name)
  {
    _name=name;
    _traitsByCell=new HashMap<String,TraitDescription>();
    _progression=new TraitTreeProgression();
  }

  /**
   * Get the name of this branch.
   * @return A name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the progression for this branch.
   * @return A trait tree progression.
   */
  public TraitTreeProgression getProgression()
  {
    return _progression;
  }

  /**
   * Get the cell IDs for this branch.
   * @return A sorted list of cell IDs.
   */
  public List<String> getCells()
  {
    List<String> cells=new ArrayList<String>(_traitsByCell.keySet());
    Collections.sort(cells);
    return cells;
  }

  /**
   * Get the trait in the targeted cell.
   * @param cellId Identifier of the targeted cell.
   * @return A trait or <code>null</code>.
   */
  public TraitDescription getTraitForCell(String cellId)
  {
    return _traitsByCell.get(cellId);
  }

  /**
   * Set the trait for a cell.
   * @param cellId Identifier of the targeted cell.
   * @param trait Trait to set.
   */
  public void setCell(String cellId, TraitDescription trait)
  {
    _traitsByCell.put(cellId,trait);
  }

  /**
   * Get a list of all the referenced traits.
   * @return A list of traits.
   */
  public List<TraitDescription> getTraits()
  {
    return new ArrayList<TraitDescription>(_traitsByCell.values());
  }
}

