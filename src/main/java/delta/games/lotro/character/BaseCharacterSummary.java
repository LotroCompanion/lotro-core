package delta.games.lotro.character;

import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.common.CharacterSex;

/**
 * Base class for LOTRO character summaries.
 * @author DAM
 */
public class BaseCharacterSummary extends CharacterReference implements BasicCharacterAttributes
{
  private String _server;
  private String _accountName;
  private CharacterSex _sex;
  private RaceDescription _race;

  /**
   * Constructor.
   */
  public BaseCharacterSummary()
  {
    super();
    _server="";
    _accountName="";
    _race=null;
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public BaseCharacterSummary(BaseCharacterSummary source)
  {
    super(source);
    _server=source._server;
    _accountName=source._accountName;
    _sex=source._sex;
    _race=source._race;
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
   * Get the character's race.
   * @return the character's race.
   */
  public RaceDescription getRace()
  {
    return _race;
  }

  /**
   * Set the character's race.
   * @param race the race to set.
   */
  public void setRace(RaceDescription race)
  {
    _race=race;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(super.toString());
    sb.append("Server [").append(_server).append("], ");
    sb.append("Account [").append(_accountName).append("], ");
    sb.append("Race [").append(_race).append("], ");
    sb.append("Sex [").append(_sex).append("], ");
    sb.append("Race [").append(_race).append("], ");
    return sb.toString();
  }
}
