package delta.games.lotro.account;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import delta.common.utils.files.FilesDeleter;
import delta.common.utils.files.comparator.FileNameComparator;
import delta.common.utils.files.filter.FileTypePredicate;
import delta.games.lotro.data.UserDataManager;

/**
 * Storage manager for all accounts.
 * @author DAM
 */
public class AccountsStorageManager
{
  /**
   * Seed for account directory names.
   */
  private static final String ACCOUNT_SEED="account-";
  private File _accountsDir;

  /**
   * Constructor.
   */
  public AccountsStorageManager()
  {
    UserDataManager dataMgr=UserDataManager.getInstance();
    _accountsDir=dataMgr.getAccountsDir();
    _accountsDir.mkdirs();
  }

  /**
   * Create a new account.
   * @param accountID Account ID.
   * @return A new account or <code>null</code> if a problem occurred.
   */
  public Account newAccount(AccountReference accountID)
  {
    File newDir=getNewAccountDirectory();
    newDir.mkdirs();
    Account account=new Account(newDir);
    AccountSummary summary=account.getSummary();
    summary.setAccountID(accountID);
    boolean ok=account.saveSummary(summary);
    if (!ok)
    {
      return null;
    }
    return account;
  }


  /**
   * Remove an account.
   * It removes all data for this account.
   * @param account Targeted account.
   */
  public void removeAccount(Account account)
  {
    File accountDir=account.getRootDir();
    if (accountDir.exists())
    {
      FilesDeleter deleter=new FilesDeleter(accountDir,null,true);
      deleter.doIt();
    }
  }

  private File getNewAccountDirectory()
  {
    File ret=null;
    int index=0;
    while (true)
    {
      String indexStr=String.format("%1$05d",Integer.valueOf(index));
      File dataFile=new File(_accountsDir,ACCOUNT_SEED+indexStr);
      if (!dataFile.exists())
      {
        ret=dataFile;
        break;
      }
      index++;
    }
    return ret;
  }

  /**
   * Get a list of all managed accounts, sorted by name.
   * @return a list of accounts.
   */
  public List<Account> getAllAccounts()
  {
    List<Account> accounts=new ArrayList<Account>();
    FileFilter fileFilter=new FileTypePredicate(FileTypePredicate.DIRECTORY);
    File[] accountDirs=_accountsDir.listFiles(fileFilter);
    if (accountDirs!=null)
    {
      // Sort files by name to always get accounts in the same order.
      Arrays.sort(accountDirs,new FileNameComparator());
      for(File accountDir : accountDirs)
      {
        String dirName=accountDir.getName();
        if (dirName.startsWith(ACCOUNT_SEED))
        {
          Account account=new Account(accountDir);
          AccountSummary summary=account.getSummary();
          if (summary!=null)
          {
            accounts.add(account);
          }
        }
      }
    }
    return accounts;
  }
}
