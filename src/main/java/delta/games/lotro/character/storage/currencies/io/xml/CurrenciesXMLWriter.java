package delta.games.lotro.character.storage.currencies.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.storage.currencies.CurrenciesSummary;
import delta.games.lotro.character.storage.currencies.Currency;
import delta.games.lotro.character.storage.currencies.CurrencyHistory;
import delta.games.lotro.character.storage.currencies.CurrencyStatus;
import delta.games.lotro.character.storage.currencies.CurrencyStorage;

/**
 * Writes a currencies data to XML files.
 * @author DAM
 */
public class CurrenciesXMLWriter
{
  /**
   * Write a currencies summary to an XML file.
   * @param outFile Output file.
   * @param currencies Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CurrenciesSummary currencies, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeCurrencies(hd,currencies);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a currencies summary to the given XML stream.
   * @param hd XML output stream.
   * @param currencies Data to write.
   * @throws SAXException If an error occurs.
   */
  private void writeCurrencies(TransformerHandler hd, CurrenciesSummary currencies) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Date
    long summaryDate=currencies.getDate();
    attrs.addAttribute("","",CurrenciesXMLConstants.CURRENCIES_SUMMARY_DATE_ATTR,XmlWriter.CDATA,String.valueOf(summaryDate));
    hd.startElement("","",CurrenciesXMLConstants.CURRENCIES_SUMMARY_TAG,attrs);
    List<CurrencyStatus> statuses=currencies.getCurrencies();
    for(CurrencyStatus status : statuses)
    {
      AttributesImpl statusAttrs=new AttributesImpl();
      Currency currency=status.getCurrency();
      // Key
      String key=currency.getPersistenceKey();
      statusAttrs.addAttribute("","",CurrenciesXMLConstants.CURRENCY_SUMMARY_KEY_ATTR,XmlWriter.CDATA,key);
      // Date
      long date=status.getDate();
      statusAttrs.addAttribute("","",CurrenciesXMLConstants.CURRENCY_SUMMARY_DATE_ATTR,XmlWriter.CDATA,String.valueOf(date));
      // Value
      int value=status.getValue();
      statusAttrs.addAttribute("","",CurrenciesXMLConstants.CURRENCY_SUMMARY_VALUE_ATTR,XmlWriter.CDATA,String.valueOf(value));
      // Keep history?
      boolean keepHistory=status.isKeepHistory();
      statusAttrs.addAttribute("","",CurrenciesXMLConstants.CURRENCY_SUMMARY_KEEP_HISTORY_ATTR,XmlWriter.CDATA,String.valueOf(keepHistory));

      hd.startElement("","",CurrenciesXMLConstants.CURRENCY_SUMMARY_TAG,statusAttrs);
      hd.endElement("","",CurrenciesXMLConstants.CURRENCY_SUMMARY_TAG);
    }
    hd.endElement("","",CurrenciesXMLConstants.CURRENCIES_SUMMARY_TAG);
  }

  /**
   * Write a currency history to an XML file.
   * @param outFile Output file.
   * @param history Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeCurrencyHistory(File outFile, final CurrencyHistory history, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeHistory(hd,history);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a currency history to the given XML stream.
   * @param hd XML output stream.
   * @param history Data to write.
   * @throws SAXException If an error occurs.
   */
  private void writeHistory(TransformerHandler hd, CurrencyHistory history) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",CurrenciesXMLConstants.CURRENCY_HISTORY_TAG,attrs);
    CurrencyStorage storage=history.getStorage();
    int nbPoints=storage.getPoints();
    for(int i=0;i<nbPoints;i++)
    {
      AttributesImpl statusAttrs=new AttributesImpl();
      // Date
      long date=storage.getTimeAtIndex(i).longValue();
      statusAttrs.addAttribute("","",CurrenciesXMLConstants.CURRENCY_SAMPLE_DATE_ATTR,XmlWriter.CDATA,String.valueOf(date));
      // Value
      int value=storage.getValueAtIndex(i).intValue();
      statusAttrs.addAttribute("","",CurrenciesXMLConstants.CURRENCY_SAMPLE_VALUE_ATTR,XmlWriter.CDATA,String.valueOf(value));
      hd.startElement("","",CurrenciesXMLConstants.CURRENCY_SAMPLE_TAG,statusAttrs);
      hd.endElement("","",CurrenciesXMLConstants.CURRENCY_SAMPLE_TAG);
    }
    hd.endElement("","",CurrenciesXMLConstants.CURRENCY_HISTORY_TAG);
  }
}
