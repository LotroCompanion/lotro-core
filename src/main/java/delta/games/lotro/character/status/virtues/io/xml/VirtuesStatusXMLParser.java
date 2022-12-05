package delta.games.lotro.character.status.virtues.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.virtues.SingleVirtueStatus;
import delta.games.lotro.character.status.virtues.VirtuesStatus;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;

/**
 * Parser for virtues status stored in XML.
 * @author DAM
 */
public class VirtuesStatusXMLParser
{
  /**
   * Parse a virtues status XML file.
   * @param source Source file.
   * @return Parsed virtues status.
   */
  public static VirtuesStatus parseVirtuesStatusFile(File source)
  {
    VirtuesStatus virtuesStatus=new VirtuesStatus();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> virtueStatusTags=DOMParsingTools.getChildTagsByName(root,VirtuesStatusXMLConstants.VIRTUE_STATUS_TAG);
      for(Element virtueStatusTag : virtueStatusTags)
      {
        parseVirtueStatus(virtueStatusTag,virtuesStatus);
      }
    }
    return virtuesStatus;
  }

  /**
   * Build a virtue status from an XML tag.
   * @param root Root XML tag.
   * @param virtuesStatus Storage.
   */
  private static void parseVirtueStatus(Element root, VirtuesStatus virtuesStatus)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,VirtuesStatusXMLConstants.VIRTUE_STATUS_IDENTIFIER_ATTR,0);
    VirtueDescription virtue=VirtuesManager.getInstance().getVirtue(id);
    SingleVirtueStatus status=virtuesStatus.getVirtueStatus(virtue);
    // XP
    int xp=DOMParsingTools.getIntAttribute(attrs,VirtuesStatusXMLConstants.VIRTUE_STATUS_XP_ATTR,0);
    status.setXp(xp);
    // Tier
    int tier=DOMParsingTools.getIntAttribute(attrs,VirtuesStatusXMLConstants.VIRTUE_STATUS_TIER_ATTR,0);
    status.setTier(tier);
  }
}
