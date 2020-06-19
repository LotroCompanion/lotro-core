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
  private int _code;
  private String _name;
  private TraitTreeProgression _progression;
  private Map<String,TraitTreeCell> _cells;

  /**
   * Constructor.
   * @param code Code.
   * @param name Branch name.
   */
  public TraitTreeBranch(int code, String name)
  {
    _code=code;
    _name=name;
    _cells=new HashMap<String,TraitTreeCell>();
    _progression=new TraitTreeProgression();
  }

  /**
   * Get the internal code for this branch.
   * @return A code.
   */
  public int getCode()
  {
    return _code;
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
    List<String> cells=new ArrayList<String>(_cells.keySet());
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
    TraitTreeCell cell=getCell(cellId);
    return (cell!=null)?cell.getTrait():null;
  }

  /**
   * Get the targeted cell.
   * @param cellId Identifier of the targeted cell.
   * @return A cell or <code>null</code>.
   */
  public TraitTreeCell getCell(String cellId)
  {
    return _cells.get(cellId);
  }

  /**
   * Set a cell.
   * @param cellId Identifier of the targeted cell.
   * @param cell Cell to set.
   */
  public void setCell(String cellId, TraitTreeCell cell)
  {
    _cells.put(cellId,cell);
  }

  /**
   * Get a list of all the referenced traits.
   * @return A list of traits.
   */
  public List<TraitDescription> getTraits()
  {
    List<TraitDescription> ret=new ArrayList<TraitDescription>();
    for(TraitTreeCell cell : _cells.values())
    {
      ret.add(cell.getTrait());
    }
    return ret;
  }
}
