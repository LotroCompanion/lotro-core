package delta.games.lotro.character.storage.currencies.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.storage.currencies.CurrenciesSummary;
import delta.games.lotro.character.storage.currencies.Currency;
import delta.games.lotro.character.storage.currencies.CurrencyHistory;
import delta.games.lotro.character.storage.currencies.io.xml.CurrenciesXMLParser;
import delta.games.lotro.character.storage.currencies.io.xml.CurrenciesXMLWriter;
import delta.games.lotro.character.storage.currencies.io.xml.CurrencyHistoryXMLParser;

/**
 * I/O methods for currencies.
 * @author DAM
 */
public class CurrenciesIo
{
  /**
   * Load the currencies summary from a file.
   * @param fromFile Source file.
   * @return A currencies summary.
   */
  public static CurrenciesSummary load(File fromFile)
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
   * Save the currencies summary to a file.
   * @param toFile File to write to.
   * @param summary Summary to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(File toFile, CurrenciesSummary summary)
  {
    CurrenciesXMLWriter writer=new CurrenciesXMLWriter();
    boolean ok=writer.write(toFile,summary,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Load a currency history from a file.
   * @param fromFile Source file.
   * @param currency Currency to use.
   * @return A currency history.
   */
  public static CurrencyHistory load(File fromFile, Currency currency)
  {
    CurrencyHistory history=null;
    if (fromFile.exists())
    {
      history=new CurrencyHistory(currency);
      CurrencyHistoryXMLParser parser=new CurrencyHistoryXMLParser();
      parser.parseCurrencyHistory(fromFile,history.getStorage());
    }
    return history;
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
}
