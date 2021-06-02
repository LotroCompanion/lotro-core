package delta.games.lotro.kinship;

import delta.games.lotro.character.BasicCharacterAttributes;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Storage class for a LOTRO kinship character summary.
 * @author DAM
 */
public class KinshipCharacterSummary implements BasicCharacterAttributes
{
  private InternalGameId _id;
  private String _characterName;
  private String _server;
  private String _accountName;
  private CharacterSex _sex;
  private CharacterClass _class;
  private Race _race;
  private int _level;
  // TODO Add Vocation ID
  // TODO Add last logout
  // TODO Add Area ID

  /**
   * Constructor.
   */
  public KinshipCharacterSummary()
  {
    _id=null;
    _characterName="";
    _server="";
    _accountName="";
    _sex=null;
    _class=null;
    _race=null;
    _level=0;
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public KinshipCharacterSummary(KinshipCharacterSummary source)
  {
    if (source._id!=null)
    {
      _id=new InternalGameId(source._id);
    }
    _characterName=source._characterName;
    _server=source._server;
    _accountName=source._accountName;
    _sex=source._sex;
    _class=source._class;
    _race=source._race;
    _level=source._level;
  }

  /**
   * Get the identififer for this character.
   * @return an identifier or <code>null</code> if not set.
   */
  public InternalGameId getId()
  {
    return _id;
  }

  /**
   * Set the identifier for this character.
   * @param id Identifier to set.
   */
  public void setId(InternalGameId id)
  {
    _id=id;
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
   * Get the character's account.
   * @return the character's account.
   */
  public String getAccountName()
  {
    return _accountName;
  }

  /**
   * Set the character's account.
   * @param accountName the account name to set. 
   */
  public void setAccountName(String accountName)
  {
    if (accountName==null)
    {
      accountName="";
    }
    _accountName=accountName;
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
    sb.append("ID [").append(_id).append("], ");
    sb.append("Name [").append(_characterName).append("], ");
    sb.append("Server [").append(_server).append("], ");
    sb.append("Account [").append(_accountName).append("], ");
    sb.append("Race [").append(_race).append("], ");
    sb.append("Sex [").append(_sex).append("], ");
    sb.append("Class [").append(_class).append("], ");
    sb.append("Race [").append(_race).append("], ");
    sb.append("Level [").append(_level).append(']');
    return sb.toString();
  }
}
