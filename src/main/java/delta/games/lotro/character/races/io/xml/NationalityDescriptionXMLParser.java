package delta.games.lotro.character.races.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.races.NationalityDescription;

/**
 * Parser for nationality descriptions stored in XML.
 * @author DAM
 */
public class NationalityDescriptionXMLParser
{
  /**
   * Parse a nationality descriptions XML file.
   * @param source Source file.
   * @return List of parsed nationality descriptions.
   */
  public static List<NationalityDescription> parseNationalitiesFile(File source)
  {
    List<NationalityDescription> descriptions=new ArrayList<NationalityDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> nationalityTags=DOMParsingTools.getChildTagsByName(root,NationalityDescriptionXMLConstants.NATIONALITY_TAG);
      for(Element nationalityTag : nationalityTags)
      {
        NationalityDescription description=parseNationalityDescription(nationalityTag);
        descriptions.add(description);
      }
    }
    return descriptions;
  }

  /**
   * Build a nationality description from an XML tag.
   * @param root Root XML tag.
   * @return A nationality description.
   */
  private static NationalityDescription parseNationalityDescription(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,NationalityDescriptionXMLConstants.NATIONALITY_CODE_ATTR,0);
    NationalityDescription ret=new NationalityDescription(code);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,NationalityDescriptionXMLConstants.NATIONALITY_NAME_ATTR,"");
    ret.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,NationalityDescriptionXMLConstants.NATIONALITY_DESCRIPTION_ATTR,"");
    ret.setDescription(description);
    // Icon ID
    int iconID=DOMParsingTools.getIntAttribute(attrs,NationalityDescriptionXMLConstants.NATIONALITY_ICON_ID_ATTR,0);
    ret.setIconID(iconID);
    // Male guideline
    String maleGuideline=DOMParsingTools.getStringAttribute(attrs,NationalityDescriptionXMLConstants.NATIONALITY_MALE_GUIDELINE_ATTR,"");
    ret.setNamingGuidelineMale(maleGuideline);
    // Female guideline
    String femaleGuideline=DOMParsingTools.getStringAttribute(attrs,NationalityDescriptionXMLConstants.NATIONALITY_FEMALE_GUIDELINE_ATTR,"");
    ret.setNamingGuidelineFemale(femaleGuideline);
    // Title ID
    int titleID=DOMParsingTools.getIntAttribute(attrs,NationalityDescriptionXMLConstants.NATIONALITY_TITLE_ID_ATTR,0);
    if (titleID!=0)
    {
      ret.setTitleID(Integer.valueOf(titleID));
    }
    // Aliases
    List<Element> aliasTags=DOMParsingTools.getChildTagsByName(root,NationalityDescriptionXMLConstants.ALIAS_TAG);
    for(Element aliasTag : aliasTags)
    {
      String alias=DOMParsingTools.getStringAttribute(aliasTag.getAttributes(),NationalityDescriptionXMLConstants.ALIAS_NAME_ATTR,null);
      if (alias!=null)
      {
        ret.addAlias(alias);
      }
    }
    return ret;
  }
}
