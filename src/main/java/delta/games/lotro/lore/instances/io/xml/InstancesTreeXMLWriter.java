package delta.games.lotro.lore.instances.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.instances.InstanceCategory;
import delta.games.lotro.lore.instances.InstancesTree;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Writes the instances tree to an XML file.
 * @author DAM
 */
public class InstancesTreeXMLWriter
{
  /**
   * Write a file with the instances tree.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeInstancesTreeFile(File toFile, InstancesTree data)
  {
    InstancesTreeXMLWriter writer=new InstancesTreeXMLWriter();
    boolean ok=writer.writeInstancesTree(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write the instances tree to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeInstancesTree(File outFile, final InstancesTree data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeInstancesTree(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeInstancesTree(TransformerHandler hd, InstancesTree data) throws SAXException
  {
    hd.startElement("","",InstancesTreeXMLConstants.INSTANCES_TREE_TAG,new AttributesImpl());
    for(String categoryName : data.getCategoryNames())
    {
      InstanceCategory category=data.getCategory(categoryName);
      writeCategory(hd,category);
    }
    hd.endElement("","",InstancesTreeXMLConstants.INSTANCES_TREE_TAG);
  }

  private void writeCategory(TransformerHandler hd, InstanceCategory category) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Name
    String name=category.getName();
    attrs.addAttribute("","",InstancesTreeXMLConstants.CATEGORY_NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",InstancesTreeXMLConstants.CATEGORY_TAG,attrs);
    // Instances
    for(SkirmishPrivateEncounter pe : category.getPrivateEncounters())
    {
      AttributesImpl instanceAttrs=new AttributesImpl();
      // Instance ID
      int id=pe.getIdentifier();
      instanceAttrs.addAttribute("","",InstancesTreeXMLConstants.INSTANCE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Instance name
      String instanceName=pe.getName();
      instanceAttrs.addAttribute("","",InstancesTreeXMLConstants.INSTANCE_NAME_ATTR,XmlWriter.CDATA,instanceName);
      hd.startElement("","",InstancesTreeXMLConstants.INSTANCE_TAG,instanceAttrs);
      hd.endElement("","",InstancesTreeXMLConstants.INSTANCE_TAG);
    }
    hd.endElement("","",InstancesTreeXMLConstants.CATEGORY_TAG);
  }
}
