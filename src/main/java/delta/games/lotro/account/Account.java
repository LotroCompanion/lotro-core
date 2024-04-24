package delta.games.lotro.account;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.misc.Preferences;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.account.io.xml.AccountSummaryXMLParser;
import delta.games.lotro.account.io.xml.AccountSummaryXMLWriter;

/**
 * Account description.
 * @author DAM
 */
public class Account
{
  private File _rootDir;
  private AccountSummary _summary;
  private Preferences _preferences;
  private Map<String,AccountOnServer> _servers;

  /**
   * Constructor.
   * @param rootDir Root directory for all files for this account.
   */
  public Account(File rootDir)
  {
    _rootDir=rootDir;
    File preferencesDir=new File(_rootDir,"preferences");
    _preferences=new Preferences(preferencesDir);
    _servers=new HashMap<String,AccountOnServer>();
  }

  /**
   * Get the account ID.
   * @return an account ID.
   */
  public AccountReference getID()
  {
    return getSummary().getAccountID();
  }
  
  /**
   * Get a display name for this account.
   * @return A display name.
   */
  public String getDisplayName()
  {
    return getSummary().getDisplayName();
  }

  /**
   * Get the account data for a server.
   * @param serverName Server name.
   * @return the manager for data on the given server for this account.
   */
  public AccountOnServer getServer(String serverName)
  {
    AccountOnServer ret=_servers.get(serverName);
    if (ret==null)
    {
      ret=new AccountOnServer(this,serverName);
      ret.load();
      _servers.put(serverName,ret);
    }
    return ret;
  }

  /**
   * Get the known servers.
   * @return A sorted list of server names.
   */
  public List<String> getKnownServers()
  {
    List<String> ret=new ArrayList<String>();
    ret.addAll(_servers.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the summary for this account.
   * @return a summary or <code>null</code> if it could not be built!
   */
  public AccountSummary getSummary()
  {
    if (_summary==null)
    {
      _summary=loadSummary();
    }
    if (_summary==null)
    {
      _summary=new AccountSummary();
    }
    return _summary;
  }

  private AccountSummary loadSummary()
  {
    AccountSummary summary=null;
    File summaryFile=getSummaryFile();
    if (summaryFile.exists())
    {
      AccountSummaryXMLParser parser=new AccountSummaryXMLParser();
      summary=parser.parseXML(summaryFile);
    }
    return summary;
  }

  /**
   * Save summary to file.
   * @param summary Summary to write.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public boolean saveSummary(AccountSummary summary)
  {
    AccountSummaryXMLWriter writer=new AccountSummaryXMLWriter();
    File summaryFile=getSummaryFile();
    boolean ok=writer.write(summaryFile,summary,EncodingNames.UTF_8);
    return ok;
  }

  private File getSummaryFile()
  {
    File summaryFile=new File(_rootDir,"summary.xml");
    return summaryFile;
  }

  /**
   * Get the name of this account.
   * @return an account name.
   */
  public String getAccountName()
  {
    return getSummary().getName();
  }

  /**
   * Get the subscription key of this account.
   * @return a subscription key.
   */
  public String getSubscriptionKey()
  {
    return getSummary().getSubscriptionKey();
  }

  /**
   * Get the root directory of the account's file storage. 
   * @return a root directory.
   */
  public File getRootDir()
  {
    return _rootDir;
  }

  /**
   * Get the preferences for this account.
   * @return the preferences for this account.
   */
  public Preferences getPreferences()
  {
    return _preferences;
  }

  @Override
  public String toString()
  {
    return getDisplayName();
  }
}
