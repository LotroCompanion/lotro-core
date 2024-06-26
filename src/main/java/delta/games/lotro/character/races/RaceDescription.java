package delta.games.lotro.character.races;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.utils.TraitAndLevel;
import delta.games.lotro.common.Genders;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Race description.
 * @author DAM
 */
public class RaceDescription implements Identifiable,Named
{
  private int _id;
  private int _code;
  private String _key;
  private String _legacyLabel;
  private String _name;
  private String _tag;
  private boolean _isTall;
  private String _description;
  private RaceGender _male;
  private RaceGender _female;
  private List<NationalityDescription> _nationalities;
  private List<String> _classes;
  private List<TraitAndLevel> _traits;
  private List<TraitDescription> _earnableTraits;

  /**
   * Constructor.
   * @param id Identifier.
   * @param code Internal lotro code.
   * @param key Key Internal LC string identifier.
   * @param legacyLabel Legacy label used in XML persistence.
   */
  public RaceDescription(int id, int code, String key, String legacyLabel)
  {
    _id=id;
    _code=code;
    _key=key;
    _legacyLabel=legacyLabel;
    _name="";
    _tag="";
    _isTall=true;
    _description="";
    _male=null;
    _female=null;
    _nationalities=new ArrayList<NationalityDescription>();
    _classes=new ArrayList<String>();
    _traits=new ArrayList<TraitAndLevel>();
    _earnableTraits=new ArrayList<TraitDescription>();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the internal LOTRO code.
   * @return a code.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the internal key.
   * @return An internal key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the legacy label.
   * @return the legacy label.
   */
  public String getLegacyLabel()
  {
    return _legacyLabel;
  }

  /**
   * Get the race name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the race name.
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
   * Get the race tag.
   * @return a tag.
   */
  public String getTag()
  {
    return _tag;
  }

  /**
   * Set the race tag.
   * @param tag Tag to set.
   */
  public void setTag(String tag)
  {
    if (tag==null)
    {
      tag="";
    }
    _tag=tag;
  }

  /**
   * Indicates if this race is 'tall' or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isTall()
  {
    return _isTall;
  }

  /**
   * Set the 'tall' flag.
   * @param tall Value to set.
   */
  public void setTall(boolean tall)
  {
    _isTall=tall;
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
    if (description==null)
    {
      description="";
    }
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
    male.setRaceAndGender(this,Genders.MALE);
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
    female.setRaceAndGender(this,Genders.FEMALE);
  }

  /**
   * Add a nationality.
   * @param nationality Nationality to add.
   */
  public void addNationality(NationalityDescription nationality)
  {
    _nationalities.add(nationality);
  }

  /**
   * Get all the nationalities of this race.
   * @return A list of nationalities.
   */
  public List<NationalityDescription> getNationalities()
  {
    return _nationalities;
  }

  /**
   * Add an allowed class.
   * @param classKey Key of the class to add.
   */
  public void addAllowedClass(String classKey)
  {
    _classes.add(classKey);
  }

  /**
   * Get the keys of the allowed classes of this race.
   * @return A list of character class keys.
   */
  public List<String> getAllowedClasses()
  {
    return _classes;
  }

  /**
   * Add a race trait.
   * @param trait Trait to add.
   */
  public void addTrait(TraitAndLevel trait)
  {
    _traits.add(trait);
  }

  /**
   * Get all the traits of this race.
   * @return A list of race traits.
   */
  public List<TraitAndLevel> getTraits()
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
    for(TraitAndLevel classTrait : _traits)
    {
      TraitDescription trait=classTrait.getTrait();
      if (_earnableTraits.contains(trait))
      {
        // Skip earnable traits
        continue;
      }
      int requiredLevel=classTrait.getRequiredLevel();
      if (level>=requiredLevel)
      {
        traits.add(trait);
      }
    }
    return traits;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
