package delta.games.lotro.character.classes.traitTree;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.Identifiable;

/**
 * Trait tree.
 * @author DAM
 */
public class TraitTree implements Identifiable
{
  private int _id;
  private int _code;
  private String _key;
  private List<TraitTreeBranch> _branches;

  /**
   * Constructor.
   * @param id Trait id.
   */
  public TraitTree(int id)
  {
    _id=id;
    _code=0;
    _key=null;
    _branches=new ArrayList<TraitTreeBranch>();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the enum code for this tree.
   * @return An enum code.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Set the enum code for this tree.
   * @param code Code to set.
   */
  public void setCode(int code)
  {
    _code=code;
  }

  /**
   * Get the identifying key.
   * @return a key or <code>null</code>.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the identifying key.
   * @param key
   */
  public void setKey(String key)
  {
    _key=key;
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
      ret.add(branch.getMainTrait());
    }
    return ret;
  }
}
