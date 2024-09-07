package delta.games.lotro.character.status.skills.io.xml;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.status.skills.SkillStatus;
import delta.games.lotro.character.status.skills.SkillsStatusManager;

/**
 * Parser for the skills status stored in XML.
 * @author DAM
 */
public class SkillsStatusXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(SkillsStatusXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public SkillsStatusManager parseXML(File source)
  {
    SkillsStatusManager status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseStatus(root);
    }
    return status;
  }

  private SkillsStatusManager parseStatus(Element root)
  {
    SkillsStatusManager status=new SkillsStatusManager();
    // Status of skills
    List<Element> statusTags=DOMParsingTools.getChildTagsByName(root,SkillsStatusXMLConstants.SKILL,false);
    for(Element statusTag : statusTags)
    {
      parseSkillStatus(status,statusTag);
    }
    return status;
  }

  private void parseSkillStatus(SkillsStatusManager status, Element statusTag)
  {
    NamedNodeMap attrs=statusTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,SkillsStatusXMLConstants.SKILL_ID_ATTR,0);
    // Create status
    SkillsManager mgr=SkillsManager.getInstance();
    SkillDescription skill=mgr.getSkill(id);
    if (skill==null)
    {
      // Unknown skill!
      LOGGER.warn("Unknown skill: "+id);
      return;
    }
    SkillStatus newStatus=status.get(skill,true);
    // Available
    boolean available=DOMParsingTools.getBooleanAttribute(attrs,SkillsStatusXMLConstants.SKILL_AVAILABLE_ATTR,false);
    newStatus.setAvailable(available);
  }
}
