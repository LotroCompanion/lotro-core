package delta.games.lotro.lore.collections.mounts.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.io.xml.SkillDescriptionXMLConstants;
import delta.games.lotro.lore.collections.mounts.MountDescription;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for mounts stored in XML.
 * @author DAM
 */
public class MountXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public MountXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("skills");
  }

  /**
   * Parse mounts from an XML file.
   * @param source Source file.
   * @return List of parsed mounts.
   */
  public List<MountDescription> parseMountsFile(File source)
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
  private MountDescription parseMount(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    MountDescription ret=new MountDescription();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,MountXMLConstants.MOUNT_IDENTIFIER_ATTR,0);
    ret.setIdentifier(id);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    ret.setName(name);
    // Initial Name
    String initialName=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_INITIAL_NAME_ATTR,"");
    ret.setInitialName(initialName);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_CATEGORY_ATTR,"");
    ret.setMountCategory(category);
    // Mount type
    String mountType=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_MOUNT_TYPE_ATTR,"");
    ret.setMountType(mountType);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,SkillDescriptionXMLConstants.SKILL_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
    ret.setDescription(description);
    // Source description
    String sourceDescription=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_SOURCE_DESCRIPTION_ATTR,"");
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
    // Tall
    boolean tall=DOMParsingTools.getBooleanAttribute(attrs,MountXMLConstants.MOUNT_TALL_ATTR,false);
    ret.setTall(tall);
    // Peer Mount ID
    int peerMountId=DOMParsingTools.getIntAttribute(attrs,MountXMLConstants.MOUNT_PEER_ID_ATTR,0);
    ret.setPeerMountId(peerMountId);
    return ret;
  }
}
