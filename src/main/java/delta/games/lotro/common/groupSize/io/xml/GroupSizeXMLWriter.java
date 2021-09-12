package delta.games.lotro.common.groupSize.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.groupSize.GroupSize;

/**
 * Writes group sizes to XML files.
 * @author DAM
 */
public class GroupSizeXMLWriter
{
  /**
   * Write a file with group sizes.
   * @param toFile Output file.
   * @param groupSizes Group sizes to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeGroupSizesFile(File toFile, List<GroupSize> groupSizes)
  {
    GroupSizeXMLWriter writer=new GroupSizeXMLWriter();
    boolean ok=writer.writeGroupSizes(toFile,groupSizes,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write group sizes to a XML file.
   * @param outFile Output file.
   * @param groupSizes Group sizes to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeGroupSizes(File outFile, final List<GroupSize> groupSizes, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeGroupSizes(hd,groupSizes);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeGroupSizes(TransformerHandler hd, List<GroupSize> groupSizes) throws Exception
  {
    hd.startElement("","",GroupSizeXMLConstants.GROUP_SIZES_TAG,new AttributesImpl());
    for(GroupSize groupSize : groupSizes)
    {
      writeGroupSize(hd,groupSize);
    }
    hd.endElement("","",GroupSizeXMLConstants.GROUP_SIZES_TAG);
  }

  private void writeGroupSize(TransformerHandler hd, GroupSize groupSize) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Code
    int code=groupSize.getCode();
    attrs.addAttribute("","",GroupSizeXMLConstants.GROUP_SIZE_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Legacy key
    String legacyKey=groupSize.getLegacyKey();
    attrs.addAttribute("","",GroupSizeXMLConstants.GROUP_SIZE_KEY_ATTR,XmlWriter.CDATA,legacyKey);
    // Name
    String name=groupSize.getLabel();
    if (name!=null)
    {
      attrs.addAttribute("","",GroupSizeXMLConstants.GROUP_SIZE_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",GroupSizeXMLConstants.GROUP_SIZE_TAG,attrs);
    hd.endElement("","",GroupSizeXMLConstants.GROUP_SIZE_TAG);
  }
}
