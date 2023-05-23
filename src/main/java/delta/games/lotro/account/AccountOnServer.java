package delta.games.lotro.account;

import java.io.File;

import delta.common.utils.misc.Preferences;
import delta.games.lotro.account.io.xml.AccountOnServerXMLParser;
import delta.games.lotro.account.io.xml.AccountOnServerXMLWriter;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Provides access to data related to an account on a server.
 * @author DAM
 */
public class AccountOnServer
{
  private Account _account;
  private InternalGameId _accountID;
  private String _serverName;
  private File _rootDir;
  private Preferences _preferences;

  /**
   * Constructor.
   * @param account Parent account.
   * @param serverName Server name.
   */
  public AccountOnServer(Account account, String serverName)
  {
    _account=account;
    _accountID=null;
    _serverName=serverName;
    if (_serverName==null)
    {
      _serverName="";
    }
    _rootDir=new File(account.getRootDir(),_serverName);
    File preferencesDir=new File(_rootDir,"preferences");
    _preferences=new Preferences(preferencesDir);
  }

  /**
   * Load data from file.
   */
  public void load()
  {
    File summaryFile=getSummaryFile();
    if (summaryFile.exists())
    {
      AccountOnServerXMLParser.parseXML(_rootDir,this);
    }
  }

  /**
   * Save data to file.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public boolean save()
  {
    File summaryFile=getSummaryFile();
    boolean ok=AccountOnServerXMLWriter.write(summaryFile,this);
    return ok;
  }

  private File getSummaryFile()
  {
    File summaryFile=new File(_rootDir,"summary.xml");
    return summaryFile;
  }

  /**
   * Get the parent account.
   * @return an account.
   */
  public Account getAccount()
  {
    return _account;
  }

  /**
   * Get the name of the managed server.
   * @return a server name.
   */
  public String getServerName()
  {
    return _serverName;
  }

  /**
   * Get the account identifier.
   * @return an account identifier.
   */
  public InternalGameId getAccountID()
  {
    return _accountID;
  }

  /**
   * Set the account identifier.
   * @param accountID Account identifier to set.
   */
  public void setAccountID(InternalGameId accountID)
  {
    _accountID=accountID;
  }

  /**
   * Get the root directory of the account/server file storage. 
   * @return a root directory.
   */
  public File getRootDir()
  {
    return _rootDir;
  }

  /**
   * Get the preferences for this account/server.
   * @return the preferences for this account/server.
   */
  public Preferences getPreferences()
  {
    return _preferences;
  }

  @Override
  public String toString()
  {
    String accountName=(_account!=null)?_account.getDisplayName():"?";
    return accountName+"@"+_serverName;
  }
}
