package delta.games.lotro.common.binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.account.AccountReference;
import delta.games.lotro.account.AccountUtils;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.account.events.AccountEvent;
import delta.games.lotro.account.events.AccountEventType;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.events.CharacterEvent;
import delta.games.lotro.character.events.CharacterEventType;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.utils.events.EventsManager;
import delta.games.lotro.utils.events.GenericEventsListener;

/**
 * Manager for known bindings.
 * @author DAM
 */
public class BindingsManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(BindingsManager.class);

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
    init();
  }

  /**
   * Add a character.
   * @param character Character to add.
   */
  private void addCharacter(CharacterFile character)
  {
    // Character
    InternalGameId id=character.getSummary().getId();
    if (id!=null)
    {
      BindingInfo info=new BindingInfo(id,character);
      registerBinding(info);
    }
    character.getAccountID();
    // Account/server
    AccountReference accountRef=character.getAccountID();
    if (accountRef!=null)
    {
      Account account=AccountsManager.getInstance().getAccount(accountRef.getAccountName(),accountRef.getSubscriptionKey());
      if (account!=null)
      {
        String serverName=character.getServerName();
        AccountOnServer accountOnServer=account.getServer(serverName);
        handleAccountOnServer(accountOnServer);
      }
    }
  }

  /**
   * Add an account.
   * @param account Account to add.
   */
  private void addAccount(Account account)
  {
    List<String> servers=AccountUtils.getServers(account.getSummary());
    for(String serverName : servers)
    {
      AccountOnServer accountOnServer=account.getServer(serverName);
      handleAccountOnServer(accountOnServer);
    }
  }

  private void handleAccountOnServer(AccountOnServer accountOnServer)
  {
    InternalGameId id=accountOnServer.getAccountID();
    if (id!=null)
    {
      BindingInfo info=new BindingInfo(id,accountOnServer);
      registerBinding(info);
    }
  }

  private void registerBinding(BindingInfo info)
  {
    InternalGameId id=info.getId();
    _knownBindings.put(id,info);
    LOGGER.debug("Registered/updated: "+info);
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
   * Remove an account.
   * @param account Account to remove.
   */
  public void removeAccount(Account account)
  {
    List<InternalGameId> toRemove=new ArrayList<InternalGameId>();
    for(BindingInfo info : _knownBindings.values())
    {
      AccountOnServer accountOnServer=info.getAccount();
      if (accountOnServer!=null)
      {
        if (account==accountOnServer.getAccount())
        {
          toRemove.add(accountOnServer.getAccountID());
        }
      }
    }
    for(InternalGameId id : toRemove)
    {
      _knownBindings.remove(id);
    }
  }

  private void init()
  {
    List<CharacterFile> toons=CharactersManager.getInstance().getAllToons();
    for(CharacterFile toon : toons)
    {
      addCharacter(toon);
    }
    List<Account> accounts=AccountsManager.getInstance().getAllAccounts();
    for(Account account : accounts)
    {
      addAccount(account);
    }
    initListeners();
  }

  private void initListeners()
  {
    // Character events
    GenericEventsListener<CharacterEvent> charEventListener=new GenericEventsListener<CharacterEvent>()
    {
      @Override
      public void eventOccurred(CharacterEvent event)
      {
        handleCharacterEvent(event);
      }
    };
    EventsManager.addListener(CharacterEvent.class,charEventListener);
    // Account events
    GenericEventsListener<AccountEvent> accountEventListener=new GenericEventsListener<AccountEvent>()
    {
      @Override
      public void eventOccurred(AccountEvent event)
      {
        handleAccountEvent(event);
      }
    };
    EventsManager.addListener(AccountEvent.class,accountEventListener);
  }

  private void handleCharacterEvent(CharacterEvent event)
  {
    CharacterEventType type=event.getType();
    if (type==CharacterEventType.CHARACTER_ADDED)
    {
      CharacterFile toon=event.getToonFile();
      addCharacter(toon);
    }
    else if (type==CharacterEventType.CHARACTER_REMOVED)
    {
      CharacterFile toon=event.getToonFile();
      removeCharacter(toon);
    }
  }

  private void handleAccountEvent(AccountEvent event)
  {
    AccountEventType type=event.getType();
    if (type==AccountEventType.ACCOUNT_ADDED)
    {
      Account account=event.getAccount();
      addAccount(account);
    }
    else if (type==AccountEventType.ACCOUNT_REMOVED)
    {
      Account account=event.getAccount();
      removeAccount(account);
    }
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
