package delta.games.lotro.lore.quests.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.utils.Proxy;

/**
 * Base class for achievable XML parsers.
 * @author DAM
 */
public class AchievableXMLParser
{
  protected void parseAchievableAttributes(NamedNodeMap attrs, Achievable achievable)
  {
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,AchievableXMLConstants.ID_ATTR,0);
    achievable.setIdentifier(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,AchievableXMLConstants.NAME_ATTR,"");
    achievable.setName(name);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,AchievableXMLConstants.CATEGORY_ATTR,"");
    achievable.setCategory(category);
    // Challenge level
    byte challengeLevel=(byte)DOMParsingTools.getIntAttribute(attrs,AchievableXMLConstants.LEVEL_ATTR,0);
    achievable.setChallengeLevel(ChallengeLevel.getByCode(challengeLevel));
    // Obsolete
    boolean obsolete=DOMParsingTools.getBooleanAttribute(attrs,AchievableXMLConstants.OBSOLETE_ATTR,false);
    achievable.setObsolete(obsolete);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,AchievableXMLConstants.DESCRIPTION_ATTR,"");
    achievable.setDescription(description);
  }

  protected void parsePrerequisites(Element root, Achievable achievable)
  {
    List<Element> prerequisiteTags=DOMParsingTools.getChildTagsByName(root,AchievableXMLConstants.PREREQUISITE_TAG);
    for(Element prerequisiteTag : prerequisiteTags)
    {
      Proxy<Achievable> proxy=buildProxy(prerequisiteTag);
      if (proxy!=null)
      {
        achievable.addPrerequisite(proxy);
      }
    }
  }

  protected Proxy<Achievable> buildProxy(Element tag)
  {
    Proxy<Achievable> ret=null;
    if (tag!=null)
    {
      NamedNodeMap attrs=tag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(attrs,AchievableXMLConstants.PROXY_ID_ATTR,0);
      String name=DOMParsingTools.getStringAttribute(attrs,AchievableXMLConstants.PROXY_NAME_ATTR,null);
      if (id!=0)
      {
        ret=new Proxy<Achievable>();
        ret.setId(id);
        ret.setName(name);
      }
    }
    return ret;
  }

}
