package delta.games.lotro.common.owner;

import java.util.Objects;

/**
 * Owner of type 'character' (in a server and an account).
 * @author DAM
 */
public class CharacterOwner extends Owner
{
  private AccountServerOwner _server;
  private String _characterName;

  /**
   * Constructor.
   * @param server Parent account and server.
   * @param character Character name.
   */
  public CharacterOwner(AccountServerOwner server, String character)
  {
    _server=server;
    _characterName=character;
  }

  /**
   * Get the parent owner.
   * @return the parent owner.
   */
  public AccountServerOwner getServer()
  {
    return _server;
  }

  /**
   * Get the account name.
   * @return the account name.
   */
  public String getAccountName()
  {
    return _server.getAccountName();
  }

  /**
   * Get the server name.
   * @return the server name.
   */
  public String getServerName()
  {
    return _server.getServerName();
  }

  /**
   * Get the character name.
   * @return the character name.
   */
  public String getCharacterName()
  {
    return _characterName;
  }

  @Override
  public String getLabel()
  {
    return _characterName+"@"+getServerName();
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    if (!(object instanceof AccountServerOwner))
    {
      return false;
    }
    CharacterOwner other=(CharacterOwner)object;
    if (!_characterName.equals(other._characterName))
    {
      return false;
    }
    return Objects.equals(_server,other._server);
  }
}
