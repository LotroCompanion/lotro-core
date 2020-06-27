package delta.games.lotro.character.classes;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;

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

  /**
   * Get a list of all the referenced traits.
   * @return A list of traits.
   */
  public List<TraitDescription> getAllTraits()
  {
    List<TraitDescription> ret=new ArrayList<TraitDescription>();
    for(TraitTreeBranch branch : _branches)
    {
      ret.addAll(branch.getTraits());
      ret.addAll(branch.getProgression().getTraits());
    }
    return ret;
  }
}
