package delta.games.lotro.character.classes.traitTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.enums.TraitTreeBranchType;

/**
 * Trait tree branch.
 * @author DAM
 */
public class TraitTreeBranch
{
  private TraitTreeBranchType _type;
  private String _description;
  private TraitDescription _trait;
  private TraitTreeProgression _progression;
  private Map<String,TraitTreeCell> _cells;
  private boolean _enabled;

  /**
   * Constructor.
   * @param type Type.
   */
  public TraitTreeBranch(TraitTreeBranchType type)
  {
    _type=type;
    _description="";
    _trait=null;
    _cells=new HashMap<String,TraitTreeCell>();
    _progression=new TraitTreeProgression();
    _enabled=true;
  }

  /**
   * Get the branch type.
   * @return the branch type.
   */
  public TraitTreeBranchType getType()
  {
    return _type;
  }

  /**
   * Get the branch description.
   * @return the branch description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the branch description.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * Get the internal code for this branch.
   * @return A code.
   */
  public int getCode()
  {
    return _type.getCode();
  }

  /**
   * Get the name of this branch.
   * @return A name.
   */
  public String getName()
  {
    return _type.getLabel();
  }

  /**
   * Get the main trait for this branch.
   * @return a trait.
   */
  public TraitDescription getMainTrait()
  {
    return _trait;
  }

  /**
   * Set the main trait for this branch.
   * @param trait Trait to set.
   */
  public void setMainTrait(TraitDescription trait)
  {
    _trait=trait;
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
   * Indicates if this branch is eligible as a main specialization.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEnabled()
  {
    return _enabled;
  }

  /**
   * Set the 'enabled' flag.
   * @param enabled Value to set.
   */
  public void setEnabled(boolean enabled)
  {
    _enabled=enabled;
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
    Collections.sort(ret,new IdentifiableComparator<TraitDescription>());
    return ret;
  }
}
