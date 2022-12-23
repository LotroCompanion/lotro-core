package delta.games.lotro.lore.quests.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserEngine;
import delta.common.utils.xml.sax.SAXParserValve;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.common.LockType;
import delta.games.lotro.common.Repeatability;
import delta.games.lotro.common.Size;
import delta.games.lotro.common.requirements.io.xml.QuestsRequirementsSaxParser;
import delta.games.lotro.common.requirements.io.xml.QuestsRequirementsXMLConstants;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.common.rewards.io.xml.RewardsSaxXMLParser;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLConstants;
import delta.games.lotro.lore.maps.MapDescription;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLConstants;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLParser;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.objectives.io.xml.DialogsSaxParser;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesSaxXMLParser;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLConstants;
import delta.games.lotro.lore.webStore.WebStoreItem;
import delta.games.lotro.lore.webStore.WebStoreItemsManager;
import delta.games.lotro.lore.worldEvents.io.xml.WorldEventConditionsSaxParser;
import delta.games.lotro.lore.worldEvents.io.xml.WorldEventConditionsXMLConstants;
import delta.games.lotro.utils.Proxy;

/**
 * SAX parser for quests.
 * @author DAM
 */
public final class QuestsSaxParser extends SAXParserValve<List<QuestDescription>>
{
  private QuestDescription _currentItem;
  private ObjectivesSaxXMLParser _objectives;
  private RewardsSaxXMLParser _rewards;
  private QuestsRequirementsSaxParser _requirements;
  private DialogsSaxParser _dialogs;
  private WorldEventConditionsSaxParser _worldEventConditions;

  /**
   * Constructor.
   */
  private QuestsSaxParser()
  {
    super();
    setResult(new ArrayList<QuestDescription>());
    _objectives=new ObjectivesSaxXMLParser();
    _objectives.setParent(this);
    _rewards=new RewardsSaxXMLParser();
    _rewards.setParent(this);
    _requirements=new QuestsRequirementsSaxParser();
    _requirements.setParent(this);
    _dialogs=new DialogsSaxParser();
    _dialogs.setParent(this);
    _worldEventConditions=new WorldEventConditionsSaxParser();
    _worldEventConditions.setParent(this);
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return List of parsed quests.
   */
  public static List<QuestDescription> parseQuestsFile(File source)
  {
    SAXParserValve<List<QuestDescription>> initial=new QuestsSaxParser();
    SAXParserEngine<List<QuestDescription>> engine=new SAXParserEngine<List<QuestDescription>>(initial);
    List<QuestDescription> result=SAXParsingTools.parseFile(source,engine);
    return result;
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
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (QuestXMLConstants.QUEST_TAG.equals(tagName))
    {
      QuestDescription q=new QuestDescription();
      getResult().add(q);

      // Shared attributes
      parseAchievableAttributes(attrs,q);
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
        q.setWebStoreItem(webStoreItem);
      }
      // Requirements
      UsageRequirementsXMLParser.parseRequirements(q.getUsageRequirement(),attrs);
      _currentItem=q;
    }
    // Bestowers
    else if (QuestXMLConstants.BESTOWER_TAG.equals(tagName))
    {
      DialogElement dialog=DialogsSaxParser.parseDialog(attrs);
      _currentItem.addBestower(dialog);
    }
    // End dialogs
    else if (QuestXMLConstants.END_DIALOG_TAG.equals(tagName))
    {
      DialogElement dialog=DialogsSaxParser.parseDialog(attrs);
      _currentItem.addEndDialog(dialog);
    }
    // Maps
    else if (MapDescriptionXMLConstants.MAP_TAG.equals(tagName))
    {
      MapDescription map=MapDescriptionXMLParser.parseMapDescription(attrs);
      _currentItem.addMap(map);
    }
    else if (QuestXMLConstants.NEXT_QUEST_TAG.equals(tagName))
    {
      // Next quest
      _currentItem.setNextQuest(buildProxy(attrs));
    }
    // Objectives
    else if (ObjectivesXMLConstants.OBJECTIVE_TAG.equals(tagName))
    {
      _objectives.setObjectives(_currentItem.getObjectives());
      return _objectives;
    }
    // Rewards
    else if (RewardsXMLConstants.REWARDS_TAG.equals(tagName))
    {
      _rewards.setRewards(_currentItem.getRewards());
      return _rewards;
    }
    // Requirements
    else if ((QuestsRequirementsXMLConstants.PREREQUISITE_TAG.equals(tagName))
        ||(QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG.equals(tagName)))
    {
      return _requirements;
    }
    // Completion comments
    else if (QuestXMLConstants.QUEST_COMPLETION_COMMENT_TAG.equals(tagName))
    {
      _dialogs.setQuest(_currentItem);
      return _dialogs;
    }
    else if ((WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_TAG.equals(tagName))
        ||(WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_TAG.equals(tagName)))
    {
      return _worldEventConditions;
    }

    return this;
  }

  /**
   * Identify end of element.
   */

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if ((QuestsRequirementsXMLConstants.PREREQUISITE_TAG.equals(tagName))
        ||(QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG.equals(tagName)))
    {
      _currentItem.setQuestRequirements(_requirements.getResult());
    }
    else if ((WorldEventConditionsXMLConstants.WORLD_EVENT_CONDITION_TAG.equals(tagName))
        ||(WorldEventConditionsXMLConstants.COMPOUND_WORLD_EVENT_CONDITION_TAG.equals(tagName)))
    {
      _currentItem.setWorldEventsRequirement(_worldEventConditions.getResult());
    }
    return this;
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
