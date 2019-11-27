package delta.games.lotro.lore.collections.mounts.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.collections.mounts.MountDescription;

/**
 * Parser for mounts stored in XML.
 * @author DAM
 */
public class MountXMLParser
{
  /**
   * Parse mounts from an XML file.
   * @param source Source file.
   * @return List of parsed mounts.
   */
  public static List<MountDescription> parseMountsFile(File source)
  {
    List<MountDescription> mounts=new ArrayList<MountDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> mountTags=DOMParsingTools.getChildTagsByName(root,MountXMLConstants.MOUNT_TAG);
      for(Element mountTag : mountTags)
      {
        MountDescription mount=parseMount(mountTag);
        mounts.add(mount);
      }
    }
    return mounts;
  }

  /**
   * Build a mount from an XML tag.
   * @param root Root XML tag.
   * @return A mount.
   */
  private static MountDescription parseMount(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,MountXMLConstants.MOUNT_IDENTIFIER_ATTR,0);
    MountDescription ret=new MountDescription(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_NAME_ATTR,null);
    ret.setName(name);
    // Initial Name
    String initialName=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_INITIAL_NAME_ATTR,null);
    ret.setInitialName(initialName);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_CATEGORY_ATTR,null);
    ret.setCategory(category);
    // Mount type
    String mountType=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_MOUNT_TYPE_ATTR,null);
    ret.setMountType(mountType);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_DESCRIPTION_ATTR,null);
    ret.setDescription(description);
    // Source description
    String sourceDescription=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_SOURCE_DESCRIPTION_ATTR,null);
    ret.setSourceDescription(sourceDescription);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,MountXMLConstants.MOUNT_ICON_ID_ATTR,0);
    ret.setIconId(iconId);
    // Morale
    int morale=DOMParsingTools.getIntAttribute(attrs,MountXMLConstants.MOUNT_MORALE_ATTR,0);
    ret.setMorale(morale);
    // Speed
    float speed=DOMParsingTools.getFloatAttribute(attrs,MountXMLConstants.MOUNT_SPEED_ATTR,0);
    ret.setSpeed(speed);

    return ret;
  }
}
