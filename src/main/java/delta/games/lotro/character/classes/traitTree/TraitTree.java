package delta.games.lotro.character.classes.traitTree;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.enums.TraitTreeType;

/**
 * Trait tree.
 * @author DAM
 */
public class TraitTree implements Identifiable
{
  private int _id;
  private TraitTreeType _type;
  private List<TraitTreeBranch> _branches;

  /**
   * Constructor.
   * @param id Trait id.
   * @param type Type.
   */
  public TraitTree(int id, TraitTreeType type)
  {
    _id=id;
    _type=type;
    _branches=new ArrayList<TraitTreeBranch>();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the trait tree type.
   * @return the trait tree type.
   */
  public TraitTreeType getType()
  {
    return _type;
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
      TraitDescription trait=branch.getMainTrait();
      if (trait!=null)
      {
        ret.add(trait);
      }
    }
    return ret;
  }
}
