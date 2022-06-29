package delta.games.lotro.lore.collections.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.enums.CollectionCategory;
import delta.games.lotro.common.requirements.RaceRequirement;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementXMLConstants;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLWriter;
import delta.games.lotro.lore.collections.Collectable;
import delta.games.lotro.lore.collections.CollectionDescription;

/**
 * Writes collections to XML files.
 * @author DAM
 */
public class CollectionsXMLWriter
{
  /**
   * Write some collections to a XML file.
   * @param toFile File to write to.
   * @param collections Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<CollectionDescription> collections)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",CollectionsXMLConstants.COLLECTIONS_TAG,new AttributesImpl());
        for(CollectionDescription collection : collections)
        {
          writeCollection(hd,collection);
        }
        hd.endElement("","",CollectionsXMLConstants.COLLECTIONS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeCollection(TransformerHandler hd, CollectionDescription collection) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=collection.getIdentifier();
    attrs.addAttribute("","",CollectionsXMLConstants.COLLECTION_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=collection.getName();
    attrs.addAttribute("","",CollectionsXMLConstants.COLLECTION_NAME_ATTR,XmlWriter.CDATA,name);
    // Category
    CollectionCategory category=collection.getCategory();
    attrs.addAttribute("","",CollectionsXMLConstants.COLLECTION_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(category.getCode()));
    // Race requirement
    RaceRequirement raceRequirement=collection.getRaceRequirement();
    if (raceRequirement!=null)
    {
      String raceRequirementStr=raceRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_RACE_ATTR,XmlWriter.CDATA,raceRequirementStr);
    }
    hd.startElement("","",CollectionsXMLConstants.COLLECTION_TAG,attrs);
    // Elements
    for(Collectable element : collection.getElements())
    {
      AttributesImpl elementAttrs=new AttributesImpl();
      // Identifier
      int elementID=element.getIdentifier();
      elementAttrs.addAttribute("","",CollectionsXMLConstants.COLLECTION_ELEMENT_ID_ATTR,XmlWriter.CDATA,String.valueOf(elementID));
      // Name
      String elementName=element.getName();
      elementAttrs.addAttribute("","",CollectionsXMLConstants.COLLECTION_ELEMENT_NAME_ATTR,XmlWriter.CDATA,elementName);
      hd.startElement("","",CollectionsXMLConstants.ELEMENT_TAG,elementAttrs);
      hd.endElement("","",CollectionsXMLConstants.ELEMENT_TAG);
    }
    // Rewards
    RewardsXMLWriter.write(hd,collection.getRewards());
    hd.endElement("","",CollectionsXMLConstants.COLLECTION_TAG);
  }
}
