package delta.games.lotro.character.storage.carryalls.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.storage.carryalls.CarryAllDefinition;
import delta.games.lotro.character.storage.carryalls.CarryAllsDefinitionsManager;
import delta.games.lotro.common.IdentifiableComparator;

/**
 * Writes LOTRO carry-all definitions to XML files.
 * @author DAM
 */
public class CarryAllDefinitionXMLWriter
{
  /**
   * Write a file with carry-all definitions.
   * @param toFile Output file.
   * @param mgr Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeCarryAllsFile(File toFile, final CarryAllsDefinitionsManager mgr)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeCarryAlls(hd,mgr);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeCarryAlls(TransformerHandler hd, CarryAllsDefinitionsManager data) throws Exception
  {
    hd.startElement("","",CarryAllDefinitionXMLConstants.CARRY_ALLS_TAG,new AttributesImpl());
    List<CarryAllDefinition> carryAlls=data.getAll();
    Collections.sort(carryAlls,new IdentifiableComparator<CarryAllDefinition>());
    for(CarryAllDefinition carryAll : carryAlls)
    {
      writeCarryAll(hd,carryAll);
    }
    hd.endElement("","",CarryAllDefinitionXMLConstants.CARRY_ALLS_TAG);
  }

  private static void writeCarryAll(TransformerHandler hd, CarryAllDefinition carryAll) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=carryAll.getIdentifier();
    attrs.addAttribute("","",CarryAllDefinitionXMLConstants.CARRY_ALL_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=carryAll.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",CarryAllDefinitionXMLConstants.CARRY_ALL_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Max items
    int maxItems=carryAll.getMaxItems();
    attrs.addAttribute("","",CarryAllDefinitionXMLConstants.CARRY_ALL_MAX_ITEMS_ATTR,XmlWriter.CDATA,String.valueOf(maxItems));
    // Stack max
    int stackMax=carryAll.getStackMax();
    attrs.addAttribute("","",CarryAllDefinitionXMLConstants.CARRY_ALL_STACK_MAX_ATTR,XmlWriter.CDATA,String.valueOf(stackMax));
    hd.startElement("","",CarryAllDefinitionXMLConstants.CARRY_ALL_TAG,attrs);
    hd.endElement("","",CarryAllDefinitionXMLConstants.CARRY_ALL_TAG);
  }
}
