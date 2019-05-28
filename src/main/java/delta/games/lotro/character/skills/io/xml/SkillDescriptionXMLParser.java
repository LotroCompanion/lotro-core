package delta.games.lotro.character.skills.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;

/**
 * Parser for skill descriptions stored in XML.
 * @author DAM
 */
public class SkillDescriptionXMLParser
{
  /**
   * Parse a skills XML file.
   * @param source Source file.
   * @return List of parsed skills.
   */
  public static List<SkillDescription> parseSkillsFile(File source)
  {
    List<SkillDescription> skills=new ArrayList<SkillDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> skillTags=DOMParsingTools.getChildTagsByName(root,SkillDescriptionXMLConstants.SKILL_TAG);
      for(Element skillTag : skillTags)
      {
        SkillDescription skill=parseSkill(skillTag);
        skills.add(skill);
      }
    }
    return skills;
  }

  /**
   * Build a skill from an XML tag.
   * @param root Root XML tag.
   * @return A skill.
   */
  private static SkillDescription parseSkill(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    SkillDescription skill=new SkillDescription();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,SkillDescriptionXMLConstants.SKILL_IDENTIFIER_ATTR,0);
    skill.setIdentifier(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,SkillDescriptionXMLConstants.SKILL_NAME_ATTR,null);
    skill.setName(name);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,SkillDescriptionXMLConstants.SKILL_CATEGORY_ATTR,"");
    skill.setCategory(category);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,SkillDescriptionXMLConstants.SKILL_ICON_ID_ATTR,0);
    skill.setIconId(iconId);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,SkillDescriptionXMLConstants.SKILL_DESCRIPTION_ATTR,"");
    skill.setDescription(description);

    return skill;
  }
}
