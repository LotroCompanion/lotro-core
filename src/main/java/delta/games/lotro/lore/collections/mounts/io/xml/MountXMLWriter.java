package delta.games.lotro.lore.collections.mounts.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.collections.mounts.MountDescription;

/**
 * Writes mounts to XML files.
 * @author DAM
 */
public class MountXMLWriter
{
  /**
   * Write some mounts to a XML file.
   * @param toFile File to write to.
   * @param mounts Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<MountDescription> mounts)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",MountXMLConstants.MOUNTS_TAG,new AttributesImpl());
        for(MountDescription mount : mounts)
        {
          writeMount(hd,mount);
        }
        hd.endElement("","",MountXMLConstants.MOUNTS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeMount(TransformerHandler hd, MountDescription mount) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=mount.getIdentifier();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=mount.getName();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_NAME_ATTR,XmlWriter.CDATA,name);
    // Initial name
    String initialName=mount.getInitialName();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_INITIAL_NAME_ATTR,XmlWriter.CDATA,initialName);
    // Category
    String category=mount.getCategory();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(category));
    // Mount type
    String mountType=mount.getMountType();
    if (mountType.length()>0)
    {
      attrs.addAttribute("","",MountXMLConstants.MOUNT_MOUNT_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(mountType));
    }
    // Description
    String description=mount.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",MountXMLConstants.MOUNT_DESCRIPTION_ATTR,XmlWriter.CDATA,String.valueOf(description));
    }
    // Source Description
    String sourceDescription=mount.getSourceDescription();
    if (sourceDescription.length()>0)
    {
      attrs.addAttribute("","",MountXMLConstants.MOUNT_SOURCE_DESCRIPTION_ATTR,XmlWriter.CDATA,String.valueOf(sourceDescription));
    }
    // Icon ID
    int iconId=mount.getIconId();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    // Morale
    int morale=mount.getMorale();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_MORALE_ATTR,XmlWriter.CDATA,String.valueOf(morale));
    // Speed
    float speed=mount.getSpeed();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_SPEED_ATTR,XmlWriter.CDATA,String.valueOf(speed));
    // Tall
    boolean tall=mount.isTall();
    if (tall)
    {
      attrs.addAttribute("","",MountXMLConstants.MOUNT_TALL_ATTR,XmlWriter.CDATA,String.valueOf(tall));
    }
    // Peer Mount ID
    int peerMountId=mount.getPeerMountId();
    if (peerMountId!=0)
    {
      attrs.addAttribute("","",MountXMLConstants.MOUNT_PEER_ID_ATTR,XmlWriter.CDATA,String.valueOf(peerMountId));
    }
    hd.startElement("","",MountXMLConstants.MOUNT_TAG,attrs);
    hd.endElement("","",MountXMLConstants.MOUNT_TAG);
  }
}
