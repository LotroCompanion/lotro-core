package delta.games.lotro.character.storage.currencies.io.xml;

import java.io.File;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserEngine;
import delta.common.utils.xml.sax.SAXParserValve;
import delta.games.lotro.character.storage.currencies.CurrencyStorage;

/**
 * Parser for the currencies data stored in XML.
 * @author DAM
 */
public class CurrencyHistoryXMLParser extends SAXParserValve<Void>
{
  private CurrencyStorage _storage;

  /**
   * Parse the XML file.
   * @param source Source file.
   * @param storage Storage for loaded data.
   */
  public void parseCurrencyHistory(File source, CurrencyStorage storage)
  {
    _storage=storage;
    SAXParserEngine<Void> engine=new SAXParserEngine<>(this);
    SAXParsingTools.parseFile(source,engine);
    _storage=null;
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (CurrenciesXMLConstants.CURRENCY_SAMPLE_TAG.equals(tagName))
    {
      // Date
      long date=SAXParsingTools.getLongAttribute(attrs,CurrenciesXMLConstants.CURRENCY_SAMPLE_DATE_ATTR,0);
      // Value
      int value=SAXParsingTools.getIntAttribute(attrs,CurrenciesXMLConstants.CURRENCY_SAMPLE_VALUE_ATTR,0);
      _storage.setValueAt(date,value);
    }
    return this;
  }
}
