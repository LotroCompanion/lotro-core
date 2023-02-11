package delta.games.lotro.lore.deeds.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserEngine;
import delta.common.utils.xml.sax.SAXParserValve;
import delta.games.lotro.common.enums.DeedCategory;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.requirements.io.xml.QuestsRequirementsSaxParser;
import delta.games.lotro.common.requirements.io.xml.QuestsRequirementsXMLConstants;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.common.rewards.io.xml.RewardsSaxXMLParser;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLConstants;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedType;
import delta.games.lotro.lore.maps.MapDescription;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLConstants;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLParser;
import delta.games.lotro.lore.quests.io.xml.AchievableSaxParser;
import delta.games.lotro.lore.quests.io.xml.AchievableXMLConstants;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesSaxXMLParser;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLConstants;
import delta.games.lotro.lore.webStore.WebStoreItem;
import delta.games.lotro.lore.webStore.WebStoreItemsManager;
import delta.games.lotro.lore.worldEvents.io.xml.WorldEventConditionsSaxParser;
import delta.games.lotro.lore.worldEvents.io.xml.WorldEventConditionsXMLConstants;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * SAX parser for deeds.
 * @author DAM
 */
public final class DeedsSaxParser extends SAXParserValve<List<DeedDescription>>
{
  private DeedDescription _currentItem;
  private ObjectivesSaxXMLParser _objectives;
  private RewardsSaxXMLParser _rewards;
  private QuestsRequirementsSaxParser _requirements;
  private WorldEventConditionsSaxParser _worldEventConditions;
  private SingleLocaleLabelsManager _i18n;
  private LotroEnum<DeedCategory> _categoryEnum;

  /**
   * Constructor.
   */
  private DeedsSaxParser()
  {
    super();
    setResult(new ArrayList<DeedDescription>());
    _i18n=I18nFacade.getLabelsMgr("quests");
    _objectives=new ObjectivesSaxXMLParser(_i18n);
    _objectives.setParent(this);
    _rewards=new RewardsSaxXMLParser();
    _rewards.setParent(this);
    _requirements=new QuestsRequirementsSaxParser();
    _requirements.setParent(this);
    _worldEventConditions=new WorldEventConditionsSaxParser();
    _worldEventConditions.setParent(this);
    _categoryEnum=LotroEnumsRegistry.getInstance().get(DeedCategory.class);
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return List of parsed deeds.
   */
  public static List<DeedDescription> parseDeedsFile(File source)
  {
    SAXParserValve<List<DeedDescription>> initial=new DeedsSaxParser();
    SAXParserEngine<List<DeedDescription>> engine=new SAXParserEngine<List<DeedDescription>>(initial);
    List<DeedDescription> result=SAXParsingTools.parseFile(source,engine);
    return result;
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (DeedXMLConstants.DEED_TAG.equals(tagName))
    {
      DeedDescription deed=new DeedDescription();
      getResult().add(deed);

      // Shared attributes
      AchievableSaxParser.parseAchievableAttributes(attrs,deed,_i18n);
      // Category
      int categoryCode=SAXParsingTools.getIntAttribute(attrs,DeedXMLConstants.CATEGORY_ATTR,0);
      DeedCategory category=null;
      if (categoryCode>0)
      {
        category=_categoryEnum.getEntry(categoryCode);
      }
      deed.setCategory(category);
      // Key
      String key=SAXParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_KEY_ATTR,null);
      deed.setKey(key);
      // Type
      DeedType type=null;
      String typeStr=SAXParsingTools.getStringAttribute(attrs,DeedXMLConstants.DEED_TYPE_ATTR,null);
      if (typeStr!=null)
      {
        try
        {
          type=DeedType.valueOf(typeStr);
        }
        catch(Exception e)
        {
          // Ignored
        }
      }
      deed.setType(type);
      // Web store item
      int webStoreItemID=SAXParsingTools.getIntAttribute(attrs,AchievableXMLConstants.WEB_STORE_ITEM_ID_ATTR,0);
      if (webStoreItemID>0)
      {
        WebStoreItem webStoreItem=WebStoreItemsManager.getInstance().getWebStoreItem(webStoreItemID);
        deed.setWebStoreItem(webStoreItem);
      }
      // Requirements
      UsageRequirementsXMLParser.parseRequirements(deed.getUsageRequirement(),attrs);
      _currentItem=deed;
    }
    // Maps
    else if (MapDescriptionXMLConstants.MAP_TAG.equals(tagName))
    {
      MapDescription map=MapDescriptionXMLParser.parseMapDescription(attrs);
      _currentItem.addMap(map);
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
}
