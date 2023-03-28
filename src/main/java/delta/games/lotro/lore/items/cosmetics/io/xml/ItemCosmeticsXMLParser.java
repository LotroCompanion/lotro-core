package delta.games.lotro.lore.items.cosmetics.io.xml;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import delta.common.utils.NumericTools;
import delta.games.lotro.lore.items.cosmetics.ItemCosmetics;

/**
 * SAX parser for item cosmetics.
 * @author DAM
 */
public final class ItemCosmeticsXMLParser extends DefaultHandler
{
  private static final Logger LOGGER=Logger.getLogger(ItemCosmeticsXMLParser.class);

  private ItemCosmetics _result;

  private ItemCosmeticsXMLParser()
  {
    _result=new ItemCosmetics();
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return List of parsed items.
   */
  public static ItemCosmetics parseFile(File source)
  {
    try
    {
      ItemCosmeticsXMLParser handler=new ItemCosmeticsXMLParser();
      // Use the default (non-validating) parser
      SAXParserFactory factory=SAXParserFactory.newInstance();
      SAXParser saxParser=factory.newSAXParser();
      saxParser.parse(source,handler);
      saxParser.reset();
      return handler._result;
    }
    catch (Exception e)
    {
      LOGGER.error("Error when loading items file "+source,e);
    }
    return null;
  }

  @Override
  public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
  {
    if (ItemCosmeticsXMLConstants.COSMETIC_TAG.equals(qualifiedName))
    {
      // ItemIDs
      String itemIDsStr=attributes.getValue(ItemCosmeticsXMLConstants.COSMETIC_ITEM_IDS_ATTR);
      String[] itemIDStrs=itemIDsStr.split(",");
      int[] itemIDs=new int[itemIDStrs.length];
      for(int i=0;i<itemIDs.length;i++)
      {
        itemIDs[i]=NumericTools.parseInt(itemIDStrs[i],0);
      }
      _result.addEntry(itemIDs);
    }
  }
}
