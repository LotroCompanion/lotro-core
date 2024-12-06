package delta.games.lotro.account;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.io.streams.IndentableStream;
import delta.games.lotro.character.CharacterFile;

/**
 * Tool to compute the association between characters and accounts.
 * @author DAM
 */
public class CharacterAccountMatcher
{
  private static final Logger LOGGER=LoggerFactory.getLogger(CharacterAccountMatcher.class);

  private class Entry
  {
    private String _key;
    private List<CharacterFile> _characters;

    public Entry(String key)
    {
      _key=key;
      _characters=new ArrayList<CharacterFile>();
    }

    /**
     * Get the account key for this entry.
     * @return a key.
     */
    public String getKey()
    {
      return _key;
    }

    public void addCharacter(CharacterFile character)
    {
      _characters.add(character);
    }

    public List<CharacterFile> getCharacters()
    {
      return _characters;
    }
  }

  /**
   * Compute a key for the given account.
   * @param account Account to use.
   * @return A key.
   */
  public String getKey(Account account)
  {
    if (account==null)
    {
      return "";
    }
    return account.getID().getExternalID();
  }

  private Map<String,Entry> _entries;
  private Map<String,List<Account>> _accountsByName;
  private Map<String,Account> _accountsBySubscription;

  /**
   * Constructor.
   */
  public CharacterAccountMatcher()
  {
    _entries=new HashMap<String,Entry>();
    _accountsByName=new HashMap<String,List<Account>>();
    _accountsBySubscription=new HashMap<String,Account>();
  }

  /**
   * Set the date for the matcher.
   * @param accounts Accounts.
   * @param characters Characters.
   */
  public void setData(List<Account> accounts, List<CharacterFile> characters)
  {
    _entries.clear();
    _accountsByName.clear();
    _accountsBySubscription.clear();
    loadAccounts(accounts);
    for(CharacterFile characterFile : characters)
    {
      Account account=handleCharacter(characterFile);
      setLink(characterFile,account);
    }
  }

  private void loadAccounts(List<Account> accounts)
  {
    for(Account account : accounts)
    {
      String name=account.getAccountName();
      List<Account> accountsWithName=_accountsByName.get(name);
      if (accountsWithName==null)
      {
        accountsWithName=new ArrayList<Account>();
        _accountsByName.put(name,accountsWithName);
      }
      accountsWithName.add(account);
      String subscription=account.getSubscriptionKey();
      if (!subscription.isEmpty())
      {
        Account oldAccount=_accountsBySubscription.put(subscription,account);
        if (oldAccount!=null)
        {
          LOGGER.warn("Accounts with same subscription: {}",subscription);
        }
      }
    }
  }

  private void setLink(CharacterFile character, Account account)
  {
    String key=getKey(account);
    Entry entry=_entries.get(key);
    if (entry==null)
    {
      entry=new Entry(key);
      _entries.put(key,entry);
    }
    entry.addCharacter(character);
  }

  private Account handleCharacter(CharacterFile character)
  {
    AccountReference characterAccountID=character.getAccountID();
    if (characterAccountID==null)
    {
      return null;
    }
    // Use subscription
    String characterSubscription=characterAccountID.getSubscriptionKey();
    if (!characterSubscription.isEmpty())
    {
      Account account=_accountsBySubscription.get(characterSubscription);
      if (account!=null)
      {
        return account;
      }
    }
    String characterAccountName=characterAccountID.getAccountName();
    if (!characterAccountName.isEmpty())
    {
      List<Account> accounts=_accountsByName.get(characterAccountName);
      if (accounts!=null)
      {
        return accounts.get(0);
      }
    }
    return null;
  }

  /**
   * Get all the characters for a given account.
   * @param id Account identifier.
   * @return A list of character (possibly empty but never <code>null</code>).
   */
  public List<CharacterFile> getCharacters(AccountReference id)
  {
    String key=id.getExternalID();
    List<CharacterFile> ret=null;
    Entry entry=_entries.get(key);
    if (entry!=null)
    {
      ret=entry.getCharacters();
    }
    else
    {
      ret=new ArrayList<CharacterFile>();
    }
    return ret;
  }

  /**
   * Dump the contents of this matcher to the given stream.
   * @param ps Output stream.
   */
  public void dump(PrintStream ps)
  {
    IndentableStream s=new IndentableStream(ps);
    List<String> keys=new ArrayList<String>(_entries.keySet());
    Collections.sort(keys);
    for(String key : keys)
    {
      Entry entry=_entries.get(key);
      s.println("Key: "+entry.getKey());
      s.incrementIndendationLevel();
      for(CharacterFile character : entry.getCharacters())
      {
        s.println(character.getIdentifier());
      }
      s.decrementIndentationLevel();
    }
  }
}
