package delta.games.lotro.common.binding;

import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Binding info for a single ID (character ID or account/server ID).
 * @author DAM
 */
public class BindingInfo
{
  private InternalGameId _id;
  private AccountOnServer _account;
  private CharacterFile _character;

  /**
   * Constructor.
   * @param id Identifier.
   * @param account Associated account.
   */
  public BindingInfo(InternalGameId id, AccountOnServer account)
  {
    _id=id;
    _account=account;
  }

  /**
   * Constructor.
   * @param id Identifier.
   * @param character Associated character.
   */
  public BindingInfo(InternalGameId id, CharacterFile character)
  {
    _id=id;
    _character=character;
  }

  /**
   * Get the managed ID.
   * @return the managed ID.
   */
  public InternalGameId getId()
  {
    return _id;
  }

  /**
   * Get the managed account.
   * @return An account or <code>null</code> if this info is for a character.
   */
  public AccountOnServer getAccount()
  {
    return _account;
  }

  /**
   * Get the managed character.
   * @return A character or <code>null</code> if this info is for an account.
   */
  public CharacterFile getCharacter()
  {
    return _character;
  }

  @Override
  public String toString()
  {
    String target="?";
    if (_character!=null)
    {
      target=_character.getName();
    }
    else if (_account!=null)
    {
      target=_account.toString();
    }
    return _id.asDisplayableString()+" => "+target;
  }
}
