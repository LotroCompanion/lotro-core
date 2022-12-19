package delta.games.lotro.lore.quests.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.common.LockType;
import delta.games.lotro.common.Repeatability;
import delta.games.lotro.common.Size;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.lore.maps.MapDescription;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLConstants;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLParser;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.objectives.io.xml.DialogsSaxParser;
import delta.games.lotro.lore.webStore.WebStoreItem;
import delta.games.lotro.lore.webStore.WebStoreItemsManager;
import delta.games.lotro.utils.Proxy;

/**
 * SAX parser for quests.
 * @author DAM
 */
public final class QuestsSaxParser extends DefaultHandler
{
  private static final Logger LOGGER=Logger.getLogger(QuestsSaxParser.class);

  private List<QuestDescription> _parsedQuests;
  private QuestDescription _currentItem;

  private QuestsSaxParser()
  {
    _parsedQuests=new ArrayList<QuestDescription>();
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return List of parsed quests.
   */
  public static List<QuestDescription> parseQuestsFile(File source)
  {
    try
    {
      QuestsSaxParser handler=new QuestsSaxParser();

      // Use the default (non-validating) parser
      SAXParserFactory factory=SAXParserFactory.newInstance();
      SAXParser saxParser=factory.newSAXParser();
      saxParser.parse(source,handler);
      saxParser.reset();
      return handler._parsedQuests;
    }
    catch (Exception e)
    {
      LOGGER.error("Error when loading items file "+source,e);
    }
    return null;
  }

  protected void parseAchievableAttributes(Attributes attrs, Achievable achievable)
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
    achievable.setDescription(description);
  }

  @Override
  public void startElement(String uri, String localName, String qualifiedName, Attributes attrs) throws SAXException
  {
    if (QuestXMLConstants.QUEST_TAG.equals(qualifiedName))
    {
      QuestDescription q=new QuestDescription();

      // Shared attributes
      parseAchievableAttributes(attrs,_currentItem);
      // Scope
      String scope=SAXParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_SCOPE_ATTR,"");
      q.setQuestScope(scope);
      // Quest arc
      String arc=SAXParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_ARC_ATTR,"");
      q.setQuestArc(arc);
      // Size
      String sizeStr=SAXParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_SIZE_ATTR,null);
      Size size=Size.SOLO;
      if (sizeStr!=null)
      {
        size=Size.valueOf(sizeStr);
      }
      q.setSize(size);
      // Repeatable
      byte repeatable=(byte)SAXParsingTools.getIntAttribute(attrs,QuestXMLConstants.QUEST_REPEATABLE_ATTR,0);
      q.setRepeatability(Repeatability.getByCode(repeatable));
      // Lock type
      String lockTypeStr=SAXParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_LOCK_TYPE_ATTR,null);
      if (lockTypeStr!=null)
      {
        LockType lockType=LockType.valueOf(lockTypeStr);
        q.setLockType(lockType);
      }
      // Instanced
      boolean instanced=SAXParsingTools.getBooleanAttribute(attrs,QuestXMLConstants.QUEST_INSTANCED_ATTR,false);
      q.setInstanced(instanced);
      // Shareable
      boolean shareable=SAXParsingTools.getBooleanAttribute(attrs,QuestXMLConstants.QUEST_SHAREABLE_ATTR,true);
      q.setShareable(shareable);
      // Session play
      boolean sessionPlay=SAXParsingTools.getBooleanAttribute(attrs,QuestXMLConstants.QUEST_SESSION_PLAY_ATTR,false);
      q.setSessionPlay(sessionPlay);
      // Auto-bestowed
      boolean autoBestowed=SAXParsingTools.getBooleanAttribute(attrs,QuestXMLConstants.QUEST_AUTO_BESTOWED_ATTR,false);
      q.setAutoBestowed(autoBestowed);
      // Web store item
      int webStoreItemID=SAXParsingTools.getIntAttribute(attrs,AchievableXMLConstants.WEB_STORE_ITEM_ID_ATTR,0);
      if (webStoreItemID>0)
      {
        WebStoreItem webStoreItem=WebStoreItemsManager.getInstance().getWebStoreItem(webStoreItemID);
        _currentItem.setWebStoreItem(webStoreItem);
      }

      _currentItem=q;
    }
    // Bestowers
    else if (QuestXMLConstants.BESTOWER_TAG.equals(qualifiedName))
    {
      DialogElement dialog=DialogsSaxParser.parseDialog(attrs);
      _currentItem.addBestower(dialog);
    }
    // End dialogs
    else if (QuestXMLConstants.END_DIALOG_TAG.equals(qualifiedName))
    {
      DialogElement dialog=DialogsSaxParser.parseDialog(attrs);
      _currentItem.addEndDialog(dialog);
    }
    // Objectives
    // TODO ObjectivesSaxXMLParser
    // Maps
    else if (MapDescriptionXMLConstants.MAP_TAG.equals(qualifiedName))
    {
      MapDescription map=MapDescriptionXMLParser.parseMapDescription(attrs);
      _currentItem.addMap(map);
    }
    else if (QuestXMLConstants.NEXT_QUEST_TAG.equals(qualifiedName))
    {
      // Next quest
      _currentItem.setNextQuest(buildProxy(attrs));
    }
    // Completion comments
    // TODO DialogsSaxParser()
    // Requirements
    UsageRequirementsXMLParser.parseRequirements(_currentItem.getUsageRequirement(),attrs);
    // TODO parseAchievablesRequirements(root,q);
    // TODO parseWorldEventsRequirements(root,q);
    // Rewards
    // TODO RewardsXMLParser.loadRewards(root,q.getRewards());
  }

  /**
   * Identify end of element.
   */

  @Override
  public void endElement(String uri, String localName, String qualifiedName)
  {
  }

  protected Proxy<Achievable> buildProxy(Attributes attrs)
  {
    Proxy<Achievable> ret=null;
    int id=SAXParsingTools.getIntAttribute(attrs,AchievableXMLConstants.PROXY_ID_ATTR,0);
    String name=SAXParsingTools.getStringAttribute(attrs,AchievableXMLConstants.PROXY_NAME_ATTR,null);
    if (id!=0)
    {
      ret=new Proxy<Achievable>();
      ret.setId(id);
      ret.setName(name);
    }
    return ret;
  }
}
