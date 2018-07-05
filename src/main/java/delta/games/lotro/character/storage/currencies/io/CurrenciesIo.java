package delta.games.lotro.character.storage.currencies.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.account.Account;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.currencies.CurrenciesSummary;
import delta.games.lotro.character.storage.currencies.Currency;
import delta.games.lotro.character.storage.currencies.CurrencyHistory;
import delta.games.lotro.character.storage.currencies.io.xml.CurrenciesXMLParser;
import delta.games.lotro.character.storage.currencies.io.xml.CurrenciesXMLWriter;

/**
 * I/O methods for currencies.
 * @author DAM
 */
public class CurrenciesIo
{
  /**
   * Load the currencies summary for a character.
   * @param character Targeted character.
   * @return A currencies summary.
   */
  public static CurrenciesSummary load(CharacterFile character)
  {
    File fromFile=getCurrenciesSummaryFile(character);
    return load(fromFile);
  }

  /**
   * Load the currencies summary for a character.
   * @param account Targeted account.
   * @param server Optional server name (may be <code>null</code>).
   * @return A currencies summary.
   */
  public static CurrenciesSummary load(Account account, String server)
  {
    File fromFile=getCurrenciesSummaryFile(account,server);
    return load(fromFile);
  }

  /**
   * Load the currencies summary from a file.
   * @param fromFile Source file.
   * @return A currencies summary.
   */
  private static CurrenciesSummary load(File fromFile)
  {
    CurrenciesSummary status=null;
    if (fromFile.exists())
    {
      CurrenciesXMLParser parser=new CurrenciesXMLParser();
      status=parser.parseCurrenciesSummary(fromFile);
    }
    if (status==null)
    {
      // Initialize from scratch
      status=new CurrenciesSummary();
    }
    return status;
  }

  /**
   * Save the currencies summary for a character.
   * @param character Targeted character.
   * @param summary Summary to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, CurrenciesSummary summary)
  {
    File toFile=getCurrenciesSummaryFile(character);
    return save(toFile,summary);
  }

  /**
   * Save the currencies summary for a character.
   * @param account Targeted account.
   * @param server Optional server name (may be <code>null</code>).
   * @param summary Summary to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(Account account, String server, CurrenciesSummary summary)
  {
    File toFile=getCurrenciesSummaryFile(account,server);
    return save(toFile,summary);
  }

  /**
   * Save the currencies summary to a file.
   * @param toFile File to write to.
   * @param summary Summary to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  private static boolean save(File toFile, CurrenciesSummary summary)
  {
    CurrenciesXMLWriter writer=new CurrenciesXMLWriter();
    boolean ok=writer.write(toFile,summary,EncodingNames.UTF_8);
    return ok;
  }

  private static File getCurrenciesSummaryFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File currenciesDir=new File(rootDir,"currencies");
    File summaryFile=new File(currenciesDir,"summary.xml");
    return summaryFile;
  }

  private static File getCurrenciesSummaryFile(Account account, String server)
  {
    File rootDir=account.getRootDir();
    if (server!=null)
    {
      rootDir=new File(rootDir,server);
    }
    File currenciesDir=new File(rootDir,"currencies");
    File summaryFile=new File(currenciesDir,"summary.xml");
    return summaryFile;
  }

  /**
   * Load a currency history for a character.
   * @param character Targeted character.
   * @param currency Currency to use.
   * @return A currency history.
   */
  public static CurrencyHistory load(CharacterFile character, Currency currency)
  {
    File fromFile=getCurrencyHistoryFile(character,currency);
    return load(fromFile,currency);
  }

  /**
   * Load a currency history for a character.
   * @param account Targeted account.
   * @param server Optional server name (may be <code>null</code>).
   * @param currency Currency to use.
   * @return A currency history.
   */
  public static CurrencyHistory load(Account account, String server, Currency currency)
  {
    File fromFile=getCurrencyHistoryFile(account,server,currency);
    return load(fromFile,currency);
  }

  /**
   * Load a currency history from a file.
   * @param fromFile Source file.
   * @param currency Currency to use.
   * @return A currency history.
   */
  public static CurrencyHistory load(File fromFile, Currency currency)
  {
    CurrencyHistory history=new CurrencyHistory(currency);
    if (fromFile.exists())
    {
      CurrenciesXMLParser parser=new CurrenciesXMLParser();
      parser.parseCurrencyHistory(fromFile,history.getStorage());
    }
    return history;
  }

  /**
   * Save a currency history for a character.
   * @param character Targeted character.
   * @param history History to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, CurrencyHistory history)
  {
    Currency currency=history.getCurrency();
    File toFile=getCurrencyHistoryFile(character,currency);
    return save(toFile,history);
  }

  /**
   * Save a currency history for a character.
   * @param account Targeted account.
   * @param server Optional server name (may be <code>null</code>).
   * @param history History to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(Account account, String server, CurrencyHistory history)
  {
    Currency currency=history.getCurrency();
    File toFile=getCurrencyHistoryFile(account,server,currency);
    return save(toFile,history);
  }

  /**
   * Save a currency history to a file.
   * @param toFile File to write to.
   * @param history History to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(File toFile, CurrencyHistory history)
  {
    CurrenciesXMLWriter writer=new CurrenciesXMLWriter();
    boolean ok=writer.writeCurrencyHistory(toFile,history,EncodingNames.UTF_8);
    return ok;
  }

  private static File getCurrencyHistoryFile(CharacterFile character, Currency currency)
  {
    File rootDir=character.getRootDir();
    File currenciesDir=new File(rootDir,"currencies");
    String key=currency.getKey();
    File historyFile=new File(currenciesDir,key+".xml");
    return historyFile;
  }

  private static File getCurrencyHistoryFile(Account account, String server, Currency currency)
  {
    File rootDir=account.getRootDir();
    if (server!=null)
    {
      rootDir=new File(rootDir,server);
    }
    File currenciesDir=new File(rootDir,"currencies");
    String key=currency.getKey();
    File historyFile=new File(currenciesDir,key+".xml");
    return historyFile;
  }
}
