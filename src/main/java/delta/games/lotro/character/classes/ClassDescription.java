package delta.games.lotro.character.classes;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.classes.proficiencies.ClassProficiencies;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.stats.buffs.BuffSpecification;

/**
 * Character class description.
 * @author DAM
 */
public class ClassDescription extends AbstractClassDescription
{
  private String _tacticalDpsStatName;
  private TraitTree _traitTree;
  private List<BuffSpecification> _defaultBuffs;
  private ClassProficiencies _proficiencies;

  /**
   * Constructor.
   * @param id Identifier.
   * @param code Internal LOTRO code.
   * @param key Key Internal LC string identifier.
   */
  public ClassDescription(int id, int code, String key)
  {
    super(id,code,key);
    _defaultBuffs=new ArrayList<BuffSpecification>();
    _proficiencies=new ClassProficiencies();
  }

  /**
   * Get the name of the tactical DPS stat.
   * @return a stat name
   */
  public String getTacticalDpsStatName()
  {
    return _tacticalDpsStatName;
  }

  /**
   * Set the name of the tactical DPS stat.
   * @param tacticalDpsStatName name to use.
   */
  public void setTacticalDpsStatName(String tacticalDpsStatName)
  {
    _tacticalDpsStatName=tacticalDpsStatName;
  }

  /**
   * Get the trait tree for this class.
   * @return a trait tree.
   */
  public TraitTree getTraitTree()
  {
    return _traitTree;
  }

  /**
   * Set the trait tree for this class.
   * @param traitTree Trait tree to set.
   */
  public void setTraitTree(TraitTree traitTree)
  {
    _traitTree=traitTree;
  }

  /**
   * Add default buff.
   * @param buff Buff to add.
   */
  public void addDefaultBuff(BuffSpecification buff)
  {
    _defaultBuffs.add(buff);
  }

  /**
   * Get the default buffs for this class.
   * @return A possibly empty but never <code>null</code> list of buffs.
   */
  public List<BuffSpecification> getDefaultBuffs()
  {
    return _defaultBuffs;
  }

  /**
   * Get the class proficiencies.
   * @return the class proficiencies.
   */
  public ClassProficiencies getProficiencies()
  {
    return _proficiencies;
  }
}
