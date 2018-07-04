package delta.games.lotro.character.storage.currencies.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
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

  /**
   * Load a currency history for a character.
   * @param character Targeted character.
   * @param currency Currency to use.
   * @return A currency history.
   */
  public static CurrencyHistory load(CharacterFile character, Currency currency)
  {
    File fromFile=getCurrencyHistoryFile(character,currency);
    CurrencyHistory history=new CurrencyHistory(currency);
    if (fromFile.exists())
    {
      CurrenciesXMLParser parser=new CurrenciesXMLParser();
      parser.parseCurrencyHistory(fromFile,history.getStorage());
    }
    return history;
  }

  /**
   * Save a currency history summary for a character.
   * @param character Targeted character.
   * @param history History to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, CurrencyHistory history)
  {
    Currency currency=history.getCurrency();
    File toFile=getCurrencyHistoryFile(character,currency);
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
}
