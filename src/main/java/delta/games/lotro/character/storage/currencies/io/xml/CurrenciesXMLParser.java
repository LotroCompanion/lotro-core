package delta.games.lotro.character.storage.currencies.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.currencies.Currencies;
import delta.games.lotro.character.storage.currencies.CurrenciesSummary;
import delta.games.lotro.character.storage.currencies.Currency;
import delta.games.lotro.character.storage.currencies.CurrencyStatus;
import delta.games.lotro.character.storage.currencies.CurrencyStorage;

/**
 * Parser for the currencies data stored in XML.
 * @author DAM
 */
public class CurrenciesXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed summary or <code>null</code>.
   */
  public CurrenciesSummary parseCurrenciesSummary(File source)
  {
    CurrenciesSummary status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseCurrenciesSummary(root);
    }
    return status;
  }

  private CurrenciesSummary parseCurrenciesSummary(Element root)
  {
    CurrenciesSummary summary=new CurrenciesSummary();
    List<Element> currencyTags=DOMParsingTools.getChildTagsByName(root,CurrenciesXMLConstants.CURRENCY_SUMMARY_TAG,false);
    for(Element currencyTag : currencyTags)
    {
      NamedNodeMap attrs=currencyTag.getAttributes();
      String key=DOMParsingTools.getStringAttribute(attrs,CurrenciesXMLConstants.CURRENCY_SUMMARY_KEY_ATTR,null);
      Currency currency=Currencies.get().getByKey(key);
      if (currency!=null)
      {
        CurrencyStatus status=new CurrencyStatus(currency);
        // Date
        long date=DOMParsingTools.getLongAttribute(attrs,CurrenciesXMLConstants.CURRENCY_SUMMARY_DATE_ATTR,0);
        status.setDate(date);
        // Value
        int value=DOMParsingTools.getIntAttribute(attrs,CurrenciesXMLConstants.CURRENCY_SUMMARY_VALUE_ATTR,0);
        status.setValue(value);
        // Keep history
        boolean keepHistory=DOMParsingTools.getBooleanAttribute(attrs,CurrenciesXMLConstants.CURRENCY_SUMMARY_KEEP_HISTORY_ATTR,false);
        status.setKeepHistory(keepHistory);

        // Register
        summary.addCurrencyStatus(status);
      }
    }
    return summary;
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @param storage Storage for loaded data.
   */
  public void parseCurrencyHistory(File source, CurrencyStorage storage)
  {
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      parseCurrencyHistory(root,storage);
    }
  }

  private void parseCurrencyHistory(Element root, CurrencyStorage storage)
  {
    List<Element> sampleTags=DOMParsingTools.getChildTagsByName(root,CurrenciesXMLConstants.CURRENCY_SAMPLE_TAG,false);
    for(Element sampleTag : sampleTags)
    {
      NamedNodeMap attrs=sampleTag.getAttributes();
      // Date
      long date=DOMParsingTools.getLongAttribute(attrs,CurrenciesXMLConstants.CURRENCY_SAMPLE_DATE_ATTR,0);
      // Value
      int value=DOMParsingTools.getIntAttribute(attrs,CurrenciesXMLConstants.CURRENCY_SAMPLE_VALUE_ATTR,0);
      storage.setValueAt(date,value);
    }
  }
}
