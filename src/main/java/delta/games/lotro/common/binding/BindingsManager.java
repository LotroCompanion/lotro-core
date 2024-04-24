package delta.games.lotro.common.binding;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.account.AccountReference;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Manager for known bindings.
 * @author DAM
 */
public class BindingsManager
{
  private static final Logger LOGGER=Logger.getLogger(BindingsManager.class);

  private static BindingsManager _instance=new BindingsManager();

  /**
   * Private constructor.
   */
  private Map<InternalGameId,BindingInfo> _knownBindings;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static BindingsManager getInstance()
  {
    return _instance;
  }

  /**
   * Constructor.
   */
  public BindingsManager()
  {
    _knownBindings=new HashMap<InternalGameId,BindingInfo>();
  }

  /**
   * Add a character.
   * @param character Character to add.
   */
  public void addCharacter(CharacterFile character)
  {
    InternalGameId id=character.getSummary().getId();
    if (id!=null)
    {
      BindingInfo info=new BindingInfo(id,character);
      registerBinding(info);
    }
  }

  /**
   * Add an account.
   * @param account Account to add.
   */
  public void addAccount(Account account)
  {
    for(String serverName : account.getKnownServers())
    {
      AccountOnServer accountOnServer=account.getServer(serverName);
      InternalGameId id=accountOnServer.getAccountID();
      BindingInfo info=new BindingInfo(id,accountOnServer);
      registerBinding(info);
    }
  }

  private void registerBinding(BindingInfo info)
  {
    InternalGameId id=info.getId();
    _knownBindings.put(id,info);
    LOGGER.debug("Registered: "+info);
  }

  /**
   * Remove a character.
   * @param character Character to remove.
   */
  public void removeCharacter(CharacterFile character)
  {
    InternalGameId id=character.getSummary().getId();
    _knownBindings.remove(id);
  }

  /**
   * Get the displayable binding info for a given id.
   * @param id ID to render.
   * @param currentCharacter Current character (for context adaptation).
   * @return A displayable string.
   */
  public String getDisplayableBindingInfo(InternalGameId id, CharacterFile currentCharacter)
  {
    BindingInfo info=_knownBindings.get(id);
    if (info==null)
    {
      return "Bound to ?";
    }
    CharacterFile boundToCharacter=info.getCharacter();
    if (boundToCharacter!=null)
    {
      // Bound to a character
      String name=boundToCharacter.getName();
      return "Bound to "+name;
    }
    AccountOnServer account=info.getAccount();
    if (account!=null)
    {
      // Bound to an account.
      // If this is the same account as the one of the given character (if any), then
      // use "character's account" instead of "account XXX".
      String accountName=account.getAccount().getDisplayName();
      String bindingStr="account "+accountName;
      if (currentCharacter!=null)
      {
        AccountReference currentAccountRef=currentCharacter.getAccountID();
        if (currentAccountRef!=null)
        {
          AccountReference boundAccountRef=account.getAccount().getID();
          if (boundAccountRef.getDisplayName().equals(currentAccountRef.getDisplayName()))
          {
            String currentCharacterName=currentCharacter.getName();
            bindingStr=currentCharacterName+"'s account";
          }
        }
      }
      return "Bound to "+bindingStr;
    }
    return "?";
  }
}
