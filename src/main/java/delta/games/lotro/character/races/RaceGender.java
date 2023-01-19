package delta.games.lotro.character.races;

import delta.games.lotro.common.CharacterSex;

/**
 * Gender of a race.
 * @author DAM
 */
public class RaceGender
{
  private String _name;
  private RaceDescription _race;
  private CharacterSex _gender;
  private int _largeIconId;
  private int _iconId;
  private int _smallIconId;
  private int _avatarId;

  /**
   * Constructor.
   */
  public RaceGender()
  {
    _name="";
  }

  /**
   * Get the name of this gender.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name for this gender.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the associated race.
   * @return the associated race.
   */
  public RaceDescription getRace()
  {
    return _race;
  }

  /**
   * Get the associated gender.
   * @return the associated gender.
   */
  public CharacterSex getGender()
  {
    return _gender;
  }

  /**
   * Set race and gender.
   * @param race Race.
   * @param gender Gender.
   */
  public void setRaceAndGender(RaceDescription race, CharacterSex gender)
  {
    _race=race;
    _gender=gender;
  }

  /**
   * Get the ID of the large icon for this gender.
   * @return an icon ID.
   */
  public int getLargeIconId()
  {
    return _largeIconId;
  }

  /**
   * Set the ID of the large icon for this gender.
   * @param largeIconId the icon ID to set.
   */
  public void setLargeIconId(int largeIconId)
  {
    _largeIconId=largeIconId;
  }

  /**
   * Get the ID of the icon for this gender.
   * @return an icon ID.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the ID of the icon for this gender.
   * @param iconId the icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the ID of the small icon for this gender.
   * @return an icon ID.
   */
  public int getSmallIconId()
  {
    return _smallIconId;
  }

  /**
   * Set the ID of the small icon for this gender.
   * @param smallIconId the icon ID to set.
   */
  public void setSmallIconId(int smallIconId)
  {
    _smallIconId=smallIconId;
  }

  /**
   * Get the avatar ID for this gender.
   * @return an avatar ID.
   */
  public int getAvatarId()
  {
    return _avatarId;
  }

  /**
   * Set the avatar ID for this gender.
   * @param avatarId the avatar ID to set.
   */
  public void setAvatarId(int avatarId)
  {
    _avatarId=avatarId;
  }
}
