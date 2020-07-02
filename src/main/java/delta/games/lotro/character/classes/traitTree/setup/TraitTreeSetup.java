package delta.games.lotro.character.classes.traitTree.setup;

import java.io.File;

import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.classes.traitTree.TraitTreeStatus;
import delta.games.lotro.common.CharacterClass;

/**
 * Trait tree setup.
 * @author DAM
 */
public class TraitTreeSetup
{
  private File _file;
  private String _name;
  private String _description;
  private TraitTreeStatus _status;
  private int _cost;

  /**
   * Constructor.
   * @param traitTree Trait tree to use.
   */
  public TraitTreeSetup(TraitTree traitTree)
  {
    _file=null;
    _name="";
    _description="";
    _status=new TraitTreeStatus(traitTree);
    _cost=0;
  }

  /**
   * Get the file to use to persist this setup.
   * @return A file.
   */
  public File getFile()
  {
    return _file;
  }

  /**
   * Set the file to use to persist this setup.
   * @param file File to set.
   */
  public void setFile(File file)
  {
    _file=file;
  }

  /**
   * Get the name of this setup.
   * @return the name of this setup.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this setup.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Get the description of this setup.
   * @return the description of this setup.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this setup.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Get the character class for this setup.
   * @return A character class.
   */
  public CharacterClass getCharacterClass()
  {
    return _status.getTraitTree().getCharacterClass();
  }

  /**
   * Get the trait tree for this setup.
   * @return A trait tree.
   */
  public TraitTree getTraitTree()
  {
    return _status.getTraitTree();
  }

  /**
   * Get the selected branch for this setup.
   * @return A trait tree branch.
   */
  public TraitTreeBranch getSelectedBranch()
  {
    return _status.getSelectedBranch();
  }

  /**
   * Get the traits status for this setup.
   * @return the trait tree status.
   */
  public TraitTreeStatus getStatus()
  {
    return _status;
  }

  /**
   * Get the cost of this setup.
   * @return a cost in trait points.
   */
  public int getCost()
  {
    return _cost;
  }

  /**
   * Set the cost of this setup.
   * @param cost Cost to set.
   */
  public void setCost(int cost)
  {
    _cost=cost;
  }

  /**
   * Update internal data after status edition.
   */
  public void update()
  {
    _cost=_status.getCost();
  }
}
