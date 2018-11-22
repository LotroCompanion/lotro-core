package delta.games.lotro.character.classes;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;

/**
 * Class description.
 * @author DAM
 */
public class ClassDescription
{
  private CharacterClass _class;
  private int _iconId;
  private int _smallIconId;
  private List<ClassTrait> _traits;

  /**
   * Constructor.
   * @param characterClass Character class.
   */
  public ClassDescription(CharacterClass characterClass)
  {
    _class=characterClass;
    _iconId=0;
    _smallIconId=0;
    _traits=new ArrayList<ClassTrait>();
  }

  /**
   * Get the described character class.
   * @return a character class.
   */
  public CharacterClass getCharacterClass()
  {
    return _class;
  }

  /**
   * Get the ID of the icon for this class.
   * @return an icon ID.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the ID of the icon for this class.
   * @param iconId the icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the ID of the small icon for this class.
   * @return an icon ID.
   */
  public int getSmallIconId()
  {
    return _smallIconId;
  }

  /**
   * Set the ID of the small icon for this class.
   * @param smallIconId the icon ID to set.
   */
  public void setSmallIconId(int smallIconId)
  {
    _smallIconId=smallIconId;
  }

  /**
   * Add a class trait.
   * @param trait Trait to add.
   */
  public void addTrait(ClassTrait trait)
  {
    _traits.add(trait);
  }

  /**
   * Get all the traits of this class.
   * @return A list of class traits.
   */
  public List<ClassTrait> getTraits()
  {
    return _traits;
  }

  /**
   * Get the class trait for a given character level.
   * @param level Character level.
   * @return A possibly empty but not <code>null</code> list of traits.
   */
  public List<TraitDescription> getTraitsForLevel(int level)
  {
    List<TraitDescription> traits=new ArrayList<TraitDescription>();
    for(ClassTrait classTrait : _traits)
    {
      int requiredLevel=classTrait.getRequiredLevel();
      if (level>=requiredLevel)
      {
        traits.add(classTrait.getTrait());
      }
    }
    return traits;
  }
}
