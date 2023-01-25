package delta.games.lotro.character.classes.initialGear.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.classes.initialGear.InitialGearDefinition;
import delta.games.lotro.character.classes.initialGear.InitialGearElement;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.lore.items.Item;

/**
 * Writes initial gear to XML files.
 * @author DAM
 */
public class InitialGearXMLWriter
{
  /**
   * Write initial gear to a XML file.
   * @param toFile File to write to.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<InitialGearDefinition> data)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeInitialGear(hd,data);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeInitialGear(TransformerHandler hd, List<InitialGearDefinition> data) throws SAXException
  {
    hd.startElement("","",InitialGearXMLConstants.INITIAL_GEAR_TAG,new AttributesImpl());
    for(InitialGearDefinition gearDefinition : data)
    {
      writeInitialGear(hd,gearDefinition);
    }
    hd.endElement("","",InitialGearXMLConstants.INITIAL_GEAR_TAG);
  }

  private static void writeInitialGear(TransformerHandler hd, InitialGearDefinition data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Key
    String key=data.getClassKey();
    attrs.addAttribute("","",InitialGearXMLConstants.CLASS_KEY_ATTR,XmlWriter.CDATA,key);
    hd.startElement("","",InitialGearXMLConstants.CLASS_TAG,attrs);
    // Items
    List<InitialGearElement> elements=data.getElements();
    for(InitialGearElement element : elements)
    {
      AttributesImpl itemAttrs=new AttributesImpl();
      // Item
      Item item=element.getItem();
      // -ID
      int itemId=item.getIdentifier();
      itemAttrs.addAttribute("","",InitialGearXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
      // Item name
      String itemName=item.getName();
      itemAttrs.addAttribute("","",InitialGearXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,itemName);
      // Race
      RaceDescription requiredRace=element.getRequiredRace();
      if (requiredRace!=null)
      {
        itemAttrs.addAttribute("","",InitialGearXMLConstants.ITEM_RACE_ATTR,XmlWriter.CDATA,requiredRace.getKey());
      }
      hd.startElement("","",InitialGearXMLConstants.ITEM_TAG,itemAttrs);
      hd.endElement("","",InitialGearXMLConstants.ITEM_TAG);
    }
    hd.endElement("","",InitialGearXMLConstants.CLASS_TAG);
  }
}
