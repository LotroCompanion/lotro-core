package delta.games.lotro.lore.items.legendary.passives.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.items.legendary.passives.PassivesGroup;

/**
 * Writes passives usage to XML documents.
 * @author DAM
 */
public class PassivesGroupsXMLWriter
{
  /**
   * Write some passives groups to a XML file.
   * @param toFile File to write to.
   * @param groups Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<PassivesGroup> groups)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",PassivesGroupsXMLConstants.PASSIVES_USAGE_TAG,new AttributesImpl());
        for(PassivesGroup group : groups)
        {
          writeGroup(hd,group);
        }
        hd.endElement("","",PassivesGroupsXMLConstants.PASSIVES_USAGE_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write a passives group to the given XML stream.
   * @param hd XML output stream.
   * @param group Group to write.
   * @throws Exception If an error occurs.
   */
  private static void writeGroup(TransformerHandler hd, PassivesGroup group) throws Exception
  {
    AttributesImpl groupAttrs=new AttributesImpl();
    hd.startElement("","",PassivesGroupsXMLConstants.PASSIVES_GROUP_TAG,groupAttrs);
    // Passives
    List<Integer> passiveIds=new ArrayList<Integer>(group.getPassiveIds());
    Collections.sort(passiveIds);
    for(Integer passiveId : passiveIds)
    {
      AttributesImpl passiveAttrs=new AttributesImpl();
      passiveAttrs.addAttribute("","",PassivesGroupsXMLConstants.PASSIVE_ID_ATTR,XmlWriter.CDATA,passiveId.toString());
      hd.startElement("","",PassivesGroupsXMLConstants.PASSIVE_TAG,passiveAttrs);
      hd.endElement("","",PassivesGroupsXMLConstants.PASSIVE_TAG);
    }
    // Items
    List<Integer> itemIds=new ArrayList<Integer>(group.getItemIds());
    Collections.sort(itemIds);
    for(Integer itemId : itemIds)
    {
      AttributesImpl itemAttrs=new AttributesImpl();
      itemAttrs.addAttribute("","",PassivesGroupsXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,itemId.toString());
      hd.startElement("","",PassivesGroupsXMLConstants.ITEM_TAG,itemAttrs);
      hd.endElement("","",PassivesGroupsXMLConstants.ITEM_TAG);
    }
    hd.endElement("","",PassivesGroupsXMLConstants.PASSIVES_GROUP_TAG);
  }
}
