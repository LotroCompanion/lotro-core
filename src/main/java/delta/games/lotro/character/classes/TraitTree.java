package delta.games.lotro.character.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Trait tree.
 * @author DAM
 */
public class TraitTree
{
  private List<TraitTreeBranch> _branches;

  /**
   * Constructor.
   */
  public TraitTree()
  {
    _branches=new ArrayList<TraitTreeBranch>();
  }

  /**
   * Add a branch.
   * @param branch Branch to add.
   */
  public void addBranch(TraitTreeBranch branch)
  {
    _branches.add(branch);
  }

  /**
   * Get all branches.
   * @return a list of branches.
   */
  public List<TraitTreeBranch> getBranches()
  {
    return _branches;
  }
}
