package delta.games.lotro.character.status.hobbies;

import delta.games.lotro.lore.hobbies.HobbyDescription;

/**
 * Hobby status.
 * @author DAM
 */
public class HobbyStatus
{
  private HobbyDescription _hobby;
  private int _value;

  /**
   * Constructor.
   * @param hobby Associated hobby.
   */
  public HobbyStatus(HobbyDescription hobby)
  {
    if (hobby==null)
    {
      throw new IllegalArgumentException("hobby is null");
    }
    _hobby=hobby;
    _value=0;
  }

  /**
   * Get the associated hobby.
   * @return the associated hobby.
   */
  public HobbyDescription getHobby()
  {
    return _hobby;
  }

  /**
   * Get the status value for the managed hobby.
   * @return A status value.
   */
  public int getValue()
  {
    return _value;
  }

  /**
   * Set the status value.
   * @param value Value to set.
   */
  public void setValue(int value)
  {
    _value=value;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int hobbyID=_hobby.getIdentifier();
    sb.append("Hobby ").append(_hobby.getName()).append(" (").append(hobbyID).append("): ");
    sb.append(_value);
    return sb.toString();
  }
}
