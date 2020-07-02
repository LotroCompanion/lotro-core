package delta.games.lotro.character.classes.traitTree;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;

/**
 * Trait tree.
 * @author DAM
 */
public class TraitTree
{
  private CharacterClass _class;
  private List<TraitTreeBranch> _branches;

  /**
   * Constructor.
   * @param characterClass Associated class.
   */
  public TraitTree(CharacterClass characterClass)
  {
    _class=characterClass;
    _branches=new ArrayList<TraitTreeBranch>();
  }

  /**
   * Get the associated class.
   * @return a class.
   */
  public CharacterClass getCharacterClass()
  {
    return _class;
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
   * Get a branch using its code.
   * @param branchCode A branch code.
   * @return A branch or <code>null</code> if not found.
   */
  public TraitTreeBranch getBranchByCode(int branchCode)
  {
    for(TraitTreeBranch branch : _branches)
    {
      if (branch.getCode()==branchCode)
      {
        return branch;
      }
    }
    return null;
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
