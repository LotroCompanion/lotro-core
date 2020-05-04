package delta.games.lotro.character;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.common.Race;

/**
 * Character data summary.
 * @author DAM
 */
public class CharacterDataSummary implements BasicCharacterAttributes
{
  private String _characterName;
  private int _level;
  private CharacterSummary _summary;

  /**
   * Constructor.
   */
  public CharacterDataSummary()
  {
    _characterName="";
    _level=0;
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public CharacterDataSummary(CharacterDataSummary source)
  {
    _characterName=source._characterName;
    _level=source._level;
    _summary=source._summary; // No deep copy
  }

  /**
   * Get the character's name.
   * @return the character's name.
   */
  public String getName()
  {
    return _characterName;
  }

  /**
   * Set the character's name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _characterName=name;
  }

  /**
   * Get the character's server.
   * @return the character's server.
   */
  public String getServer()
  {
    return _summary!=null?_summary.getServer():"";
  }

  /**
   * Get the parent summary.
   * @return the parent summary.
   */
  public CharacterSummary getSummary()
  {
    return _summary;
  }

  /**
   * Set the parent summary.
   * @param summary Summary to set.
   */
  public void setSummary(CharacterSummary summary)
  {
    _summary=summary;
  }

  /**
   * Get the character's sex.
   * @return the character's sex.
   */
  public CharacterSex getCharacterSex()
  {
    return _summary!=null?_summary.getCharacterSex():null;
  }

  /**
   * Get the character's class.
   * @return the character's class.
   */
  public CharacterClass getCharacterClass()
  {
    return _summary!=null?_summary.getCharacterClass():null;
  }

  /**
   * Get the character's race.
   * @return the character's race.
   */
  public Race getRace()
  {
    return _summary!=null?_summary.getRace():null;
  }

  /**
   * Get the character's level.
   * @return the character's level.
   */
  public int getLevel()
  {
    return _level;
  }

  /**
   * Set the character's level.
   * @param level the level to set.
   */
  public void setLevel(int level)
  {
    _level=level;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Name [").append(_characterName).append("], ");
    sb.append("Level [").append(_level).append(']');
    if (_summary!=null)
    {
      sb.append(", ").append(_summary.toString());
    }
    return sb.toString();
  }
}
