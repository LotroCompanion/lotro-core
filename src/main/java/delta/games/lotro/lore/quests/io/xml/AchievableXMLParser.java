package delta.games.lotro.lore.quests.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.io.xml.QuestsRequirementsXMLParser;
import delta.games.lotro.lore.maps.MapDescription;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLConstants;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLParser;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.io.xml.WorldEventConditionsXMLParser;
import delta.games.lotro.utils.Proxy;

/**
 * Base class for achievable XML parsers.
 * @author DAM
 */
public class AchievableXMLParser
{
  private WorldEventConditionsXMLParser _worldEventConditionsParser=new WorldEventConditionsXMLParser();

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
    int challengeLevel=DOMParsingTools.getIntAttribute(attrs,AchievableXMLConstants.LEVEL_ATTR,0);
    achievable.setChallengeLevel(ChallengeLevel.getByCode(challengeLevel));
    // Hidden
    boolean hidden=DOMParsingTools.getBooleanAttribute(attrs,AchievableXMLConstants.HIDDEN_ATTR,false);
    achievable.setHidden(hidden);
    // Monster-play
    boolean monsterPlay=DOMParsingTools.getBooleanAttribute(attrs,AchievableXMLConstants.MONSTER_PLAY_ATTR,false);
    achievable.setMonsterPlay(monsterPlay);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,AchievableXMLConstants.DESCRIPTION_ATTR,"");
    achievable.setDescription(description);
  }

  protected void parseAchievablesRequirements(Element root, Achievable achievable)
  {
    AbstractAchievableRequirement requirement=QuestsRequirementsXMLParser.loadRequirement(root);
    achievable.setQuestRequirements(requirement);
  }

  protected void parseWorldEventsRequirements(Element root, Achievable achievable)
  {
    AbstractWorldEventCondition requirement=_worldEventConditionsParser.loadRequirement(root);
    achievable.setWorldEventsRequirement(requirement);
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

  protected void parseMaps(Element root, Achievable achievable)
  {
    List<Element> mapTags=DOMParsingTools.getChildTagsByName(root,MapDescriptionXMLConstants.MAP_TAG);
    for(Element mapTag : mapTags)
    {
      MapDescription map=MapDescriptionXMLParser.parseMapDescription(mapTag);
      achievable.addMap(map);
    }
  }
}
