package delta.games.lotro.lore.quests.io.xml;

import org.xml.sax.Attributes;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.lore.quests.Achievable;

/**
 * Utility methods for achievable parsers.
 * @author DAM
 */
public class AchievableSaxParser
{
  /**
   * Parse achievable attributes (SAX mode).
   * @param attrs Input data.
   * @param achievable Storage.
   * @param i18n Localization support.
   */
  public static void parseAchievableAttributes(Attributes attrs, Achievable achievable, SingleLocaleLabelsManager i18n)
  {
    // Identifier
    int id=SAXParsingTools.getIntAttribute(attrs,AchievableXMLConstants.ID_ATTR,0);
    achievable.setIdentifier(id);
    // Name
    String name=SAXParsingTools.getStringAttribute(attrs,AchievableXMLConstants.NAME_ATTR,"");
    achievable.setName(name);
    // Category
    String category=SAXParsingTools.getStringAttribute(attrs,AchievableXMLConstants.CATEGORY_ATTR,"");
    achievable.setCategory(category);
    // Challenge level
    int challengeLevel=SAXParsingTools.getIntAttribute(attrs,AchievableXMLConstants.LEVEL_ATTR,0);
    achievable.setChallengeLevel(ChallengeLevel.getByCode(challengeLevel));
    // Hidden
    boolean hidden=SAXParsingTools.getBooleanAttribute(attrs,AchievableXMLConstants.HIDDEN_ATTR,false);
    achievable.setHidden(hidden);
    // Monster-play
    boolean monsterPlay=SAXParsingTools.getBooleanAttribute(attrs,AchievableXMLConstants.MONSTER_PLAY_ATTR,false);
    achievable.setMonsterPlay(monsterPlay);
    // Description
    String description=SAXParsingTools.getStringAttribute(attrs,AchievableXMLConstants.DESCRIPTION_ATTR,"");
    description=i18n.getLabel(description);
    achievable.setDescription(description);
  }
}
