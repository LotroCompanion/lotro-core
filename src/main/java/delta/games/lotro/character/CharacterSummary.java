package delta.games.lotro.character;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.common.Race;

/**
 * Storage class for a LOTRO character summary.
 * @author DAM
 */
public class CharacterSummary
{
  private String _characterName;
  private String _server;
  private CharacterSex _sex;
  private CharacterClass _class;
  private Race _race;
  private String _region;
  private int _level;

  /**
   * Constructor.
   */
  public CharacterSummary()
  {
    _characterName="";
    _server="";
    _sex=null;
    _class=null;
    _race=null;
    _region="";
    _level=0;
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public CharacterSummary(CharacterSummary source)
  {
    _characterName=source._characterName;
    _server=source._server;
    _sex=source._sex;
    _class=source._class;
    _race=source._race;
    _region=source._region;
    _level=source._level;
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
    return _server;
  }

  /**
   * Set the character's server.
   * @param server the server to set. 
   */
  public void setServer(String server)
  {
    if (server==null)
    {
      server="";
    }
    _server=server;
  }

  /**
   * Get the character's sex.
   * @return the character's sex.
   */
  public CharacterSex getCharacterSex()
  {
    return _sex;
  }

  /**
   * Set the character's sex.
   * @param characterSex the sex to set.
   */
  public void setCharacterSex(CharacterSex characterSex)
  {
    _sex=characterSex;
  }

  /**
   * Get the character's class.
   * @return the character's class.
   */
  public CharacterClass getCharacterClass()
  {
    return _class;
  }

  /**
   * Set the character's class.
   * @param characterClass the class to set.
   */
  public void setCharacterClass(CharacterClass characterClass)
  {
    _class=characterClass;
  }

  /**
   * Get the character's race.
   * @return the character's race.
   */
  public Race getRace()
  {
    return _race;
  }

  /**
   * Set the character's race.
   * @param race the race to set.
   */
  public void setRace(Race race)
  {
    _race=race;
  }

  /**
   * Set the character's region.
   * @return the character's region.
   */
  public String getRegion()
  {
    return _region;
  }

  /**
   * Set the character's region.
   * @param region the region to set.
   */
  public void setRegion(String region)
  {
    if (region==null)
    {
      region="";
    }
    _region=region;
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
    sb.append("Server [").append(_server).append("], ");
    sb.append("Race [").append(_race).append("], ");
    sb.append("Region [").append(_region).append("], ");
    sb.append("Sex [").append(_sex).append("], ");
    sb.append("Class [").append(_class).append("], ");
    sb.append("Race [").append(_race).append("], ");
    sb.append("Level [").append(_level).append(']');
    return sb.toString();
  }
}
