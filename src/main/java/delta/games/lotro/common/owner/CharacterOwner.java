package delta.games.lotro.common.owner;

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
}
