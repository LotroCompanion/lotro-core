package delta.games.lotro.character.races;

/**
 * Gender of a race.
 * @author DAM
 */
public class RaceGender
{
  private String _name;
  private int _largeIconId;
  private int _iconId;
  private int _smallIconId;

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
}
