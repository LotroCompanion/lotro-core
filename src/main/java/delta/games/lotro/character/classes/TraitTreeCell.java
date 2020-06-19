package delta.games.lotro.character.classes;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;

/**
 * Cell of a trait tree.
 * @author DAM
 */
public class TraitTreeCell
{
  private String _cellId;
  private TraitDescription _trait;
  private List<TraitTreeCellDependency> _dependencies;

  /**
   * Constructor.
   * @param cellId Cell identifier.
   * @param trait Trait.
   */
  public TraitTreeCell(String cellId, TraitDescription trait)
  {
    _cellId=cellId;
    _trait=trait;
    _dependencies=new ArrayList<TraitTreeCellDependency>();
  }

  /**
   * Get the cell identifier.
   * @return the cell identifier.
   */
  public String getCellId()
  {
    return _cellId;
  }

  /**
   * Get the trait of this cell.
   * @return a trait.
   */
  public TraitDescription getTrait()
  {
    return _trait;
  }

  /**
   * Get the dependencies for this cell.
   * @return a possibly empty but never <code>null</code> list of dependencies.
   */
  public List<TraitTreeCellDependency> getDependencies()
  {
    return _dependencies;
  }

  /**
   * Add a dependency.
   * @param dependency Dependency to add.
   */
  public void addDependency(TraitTreeCellDependency dependency)
  {
    _dependencies.add(dependency);
  }
}
