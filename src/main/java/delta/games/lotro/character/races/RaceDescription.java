package delta.games.lotro.character.races;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Race description.
 * @author DAM
 */
public class RaceDescription
{
  private Race _race;
  private String _description;
  private RaceGender _male;
  private RaceGender _female;
  private List<CharacterClass> _classes;
  private List<RaceTrait> _traits;
  private List<TraitDescription> _earnableTraits;

  /**
   * Constructor.
   * @param race Character race.
   */
  public RaceDescription(Race race)
  {
    _race=race;
    _description="";
    _male=null;
    _female=null;
    _classes=new ArrayList<CharacterClass>();
    _traits=new ArrayList<RaceTrait>();
    _earnableTraits=new ArrayList<TraitDescription>();
  }

  /**
   * Get the described race.
   * @return a character race.
   */
  public Race getRace()
  {
    return _race;
  }

  /**
   * Get the race description.
   * @return a description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the race description.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    if (description==null) description="";
    _description=description;
  }

  /**
   * Get the male gender.
   * @return the male gender.
   */
  public RaceGender getMaleGender()
  {
    return _male;
  }

  /**
   * Set the male gender.
   * @param male Gender to set.
   */
  public void setMaleGender(RaceGender male)
  {
    _male=male;
  }

  /**
   * Get the female gender.
   * @return the female gender.
   */
  public RaceGender getFemaleGender()
  {
    return _female;
  }

  /**
   * Set the female gender.
   * @param female Gender to set.
   */
  public void setFemaleGender(RaceGender female)
  {
    _female=female;
  }

  /**
   * Add an allowed class.
   * @param characterClass Class to add.
   */
  public void addAllowedClass(CharacterClass characterClass)
  {
    _classes.add(characterClass);
  }

  /**
   * Get all the allowed classes of this race.
   * @return A list of character classes.
   */
  public List<CharacterClass> getAllowedClasses()
  {
    return _classes;
  }

  /**
   * Add a race trait.
   * @param trait Trait to add.
   */
  public void addTrait(RaceTrait trait)
  {
    _traits.add(trait);
  }

  /**
   * Get all the traits of this race.
   * @return A list of race traits.
   */
  public List<RaceTrait> getTraits()
  {
    return _traits;
  }

  /**
   * Add an earnable race trait.
   * @param trait Trait to add.
   */
  public void addEarnableTrait(TraitDescription trait)
  {
    _earnableTraits.add(trait);
  }

  /**
   * Get all the earnable traits of this race.
   * @return A list of traits.
   */
  public List<TraitDescription> getEarnableTraits()
  {
    return _earnableTraits;
  }

  /**
   * Get the race traits for a given character level.
   * @param level Character level.
   * @return A possibly empty but not <code>null</code> list of traits.
   */
  public List<TraitDescription> getTraitsForLevel(int level)
  {
    List<TraitDescription> traits=new ArrayList<TraitDescription>();
    for(RaceTrait classTrait : _traits)
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
