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

        // Register
        summary.addCurrencyStatus(status);
      }
    }
    return summary;
  }
}
