package delta.games.lotro.lore.collections.baubles.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;

/**
 * Parser for the baubles directory stored in XML.
 * @author DAM
 */
public class BaublesXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(BaublesXMLParser.class);

  /**
   * Parse the baubles directory from an XML file.
   * @param source Source file.
   * @return List of baubles.
   */
  public List<SkillDescription> parseBaublesFile(File source)
  {
    List<SkillDescription> skills=new ArrayList<SkillDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> baubleTags=DOMParsingTools.getChildTagsByName(root,BaublesXMLConstants.BAUBLE_TAG);
      for(Element baubleTag : baubleTags)
      {
        SkillDescription bauble=parseBauble(baubleTag);
        if (bauble!=null)
        {
          skills.add(bauble);
        }
      }
    }
    return skills;
  }

  /**
   * Get a bauble from an XML tag.
   * @param root Root XML tag.
   * @return A bauble or <code>null</code>.
   */
  private SkillDescription parseBauble(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,BaublesXMLConstants.IDENTIFIER_ATTR,0);
    SkillDescription skill=SkillsManager.getInstance().getSkill(id);
    if (skill==null)
    {
      LOGGER.warn("Skill not found: {}",Integer.valueOf(id));
    }
    return skill;
  }
}
