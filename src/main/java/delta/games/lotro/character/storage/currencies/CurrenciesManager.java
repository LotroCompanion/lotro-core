package delta.games.lotro.character.storage.currencies;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.account.Account;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.currencies.io.CurrenciesIo;

/**
 * Currencies manager.
 * <p>
 * Manages currencies for a single character or for an account/server:
 * <ul>
 * <li>load from disk,
 * <li>update,
 * <li>save.
 * </ul>
 * @author DAM
 */
public class CurrenciesManager
{
  private File _rootDir;
  private CurrenciesSummary _summary;
  private Map<String,CurrencyHistory> _histories;

  /**
   * Constructor for a character.
   * @param characterFile Character file.
   */
  public CurrenciesManager(CharacterFile characterFile)
  {
    _rootDir=getRootDir(characterFile);
    init();
  }

  /**
   * Constructor for an account/server.
   * @param account Account.
   * @param serverName Server name.
   */
  public CurrenciesManager(Account account, String serverName)
  {
    _rootDir=getRootDir(account,serverName);
    init();
  }

  /**
   * Constructor for an account.
   * @param account Account.
   */
  public CurrenciesManager(Account account)
  {
    _rootDir=getRootDir(account);
    init();
  }

  private void init()
  {
    _histories=new HashMap<String,CurrencyHistory>();
    _summary=CurrenciesIo.load(getSummaryFile());
  }

  /**
   * Update a currency.
   * @param currencyId Currency identifier.
   * @param value Value to set.
   * @param withHistory Maintain history or not.
   */
  public void updateCurrency(String currencyId, int value, boolean withHistory)
  {
    long now=System.currentTimeMillis();
    CurrencyStatus status=_summary.getCurrency(currencyId,true);
    if (status==null)
    {
      return;
    }
    status.setDate(now);
    status.setValue(value);
    if (withHistory)
    {
      CurrencyHistory history=getHistory(currencyId);
      history.getStorage().setValueAt(now,value);
    }
  }

  /**
   * Get the history of a currency.
   * @param currency Currency to use.
   * @return An history.
   */
  public CurrencyHistory getHistory(Currency currency)
  {
    if (currency==null)
    {
      return null;
    }
    String currencyId=currency.getKey();
    return getHistory(currencyId);
  }

  private CurrencyHistory getHistory(String currencyId)
  {
    CurrencyHistory ret=_histories.get(currencyId);
    if (ret==null)
    {
      Currency currency=Currencies.get().getByKey(currencyId);
      if (currency!=null)
      {
        ret=loadCurrencyHistory(currency);
        if (ret==null)
        {
          ret=new CurrencyHistory(currency);
        }
        _histories.put(currencyId,ret);
      }
    }
    return ret;
  }

  private CurrencyHistory loadCurrencyHistory(Currency currency)
  {
    CurrencyHistory ret=null;
    String legacyKey=currency.getLegacyKey();
    if (legacyKey!=null)
    {
      File legacyFile=new File(_rootDir,legacyKey+".xml");
      ret=CurrenciesIo.load(legacyFile,currency);
    }
    else
    {
      File regularFile=new File(_rootDir,currency.getKey()+".xml");
      ret=CurrenciesIo.load(regularFile,currency);
    }
    return ret;
  }

  private boolean saveCurrencyHistory(CurrencyHistory history)
  {
    boolean ret;
    Currency currency=history.getCurrency();
    String legacyKey=currency.getLegacyKey();
    if (legacyKey!=null)
    {
      File legacyFile=new File(_rootDir,legacyKey+".xml");
      ret=CurrenciesIo.save(legacyFile,history);
    }
    else
    {
      File regularFile=new File(_rootDir,currency.getKey()+".xml");
      ret=CurrenciesIo.save(regularFile,history);
    }
    return ret;
  }

  /**
   * Save the current managed data to disk.
   */
  public void save()
  {
    CurrenciesIo.save(getSummaryFile(),_summary);
    for(CurrencyHistory history : _histories.values())
    {
      saveCurrencyHistory(history);
    }
  }

  private File getSummaryFile()
  {
    return new File(_rootDir,"summary.xml");
  }

  private File getRootDir(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File currenciesDir=new File(rootDir,"currencies");
    return currenciesDir;
  }

  private File getRootDir(Account account, String server)
  {
    File rootDir=account.getRootDir();
    if (server!=null)
    {
      rootDir=new File(rootDir,server);
    }
    File currenciesDir=new File(rootDir,"currencies");
    return currenciesDir;
  }

  private File getRootDir(Account account)
  {
    File rootDir=account.getRootDir();
    File currenciesDir=new File(rootDir,"currencies");
    return currenciesDir;
  }
}
