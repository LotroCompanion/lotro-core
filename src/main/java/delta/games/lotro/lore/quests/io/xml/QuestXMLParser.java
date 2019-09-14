package delta.games.lotro.lore.quests.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.Repeatability;
import delta.games.lotro.common.Size;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLParser;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestDescription.FACTION;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLParser;

/**
 * Parser for quest descriptions stored in XML.
 * @author DAM
 */
public class QuestXMLParser extends AchievableXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed quests.
   */
  public List<QuestDescription> parseXML(File source)
  {
    List<QuestDescription> ret=new ArrayList<QuestDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> questTags=DOMParsingTools.getChildTagsByName(root,QuestXMLConstants.QUEST_TAG);
      for(Element questTag : questTags)
      {
        QuestDescription quest=parseQuest(questTag);
        ret.add(quest);
      }
    }
    return ret;
  }

  private QuestDescription parseQuest(Element root)
  {
    QuestDescription q=new QuestDescription();

    NamedNodeMap attrs=root.getAttributes();

    // Shared attributes
    parseAchievableAttributes(attrs,q);
    // Scope
    String scope=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_SCOPE_ATTR,"");
    q.setQuestScope(scope);
    // Quest arc
    String arc=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_ARC_ATTR,"");
    q.setQuestArc(arc);
    // Size
    String sizeStr=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_SIZE_ATTR,null);
    Size size=Size.SOLO;
    if (sizeStr!=null)
    {
      size=Size.valueOf(sizeStr);
    }
    q.setSize(size);
    // Faction
    FACTION faction=FACTION.FREE_PEOPLES;
    String factionStr=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_FACTION_ATTR,null);
    if (factionStr!=null)
    {
      faction=FACTION.valueOf(factionStr);
    }
    q.setFaction(faction);
    // Repeatable
    byte repeatable=(byte)DOMParsingTools.getIntAttribute(attrs,QuestXMLConstants.QUEST_REPEATABLE_ATTR,0);
    q.setRepeatability(Repeatability.getByCode(repeatable));
    // Instanced
    boolean instanced=DOMParsingTools.getBooleanAttribute(attrs,QuestXMLConstants.QUEST_INSTANCED_ATTR,false);
    q.setInstanced(instanced);
    // Shareable
    boolean shareable=DOMParsingTools.getBooleanAttribute(attrs,QuestXMLConstants.QUEST_SHAREABLE_ATTR,true);
    q.setShareable(shareable);
    // Session play
    boolean sessionPlay=DOMParsingTools.getBooleanAttribute(attrs,QuestXMLConstants.QUEST_SESSION_PLAY_ATTR,false);
    q.setSessionPlay(sessionPlay);
    // Auto-bestowed
    boolean autoBestowed=DOMParsingTools.getBooleanAttribute(attrs,QuestXMLConstants.QUEST_AUTO_BESTOWED_ATTR,false);
    q.setAutoBestowed(autoBestowed);
    // Bestowers
    List<Element> bestowerTags=DOMParsingTools.getChildTagsByName(root,QuestXMLConstants.BESTOWER_TAG);
    for(Element bestowerTag : bestowerTags)
    {
      DialogElement bestower=ObjectivesXMLParser.parseDialog(bestowerTag);
      q.addBestower(bestower);
    }
    // Objectives
    ObjectivesXMLParser.loadObjectives(root,q.getObjectives());
    // Requirements
    UsageRequirementsXMLParser.parseRequirements(q.getUsageRequirement(),root);
    // Prerequisites
    parsePrerequisites(root,q);
    // Next quest
    Element nextQuestTag=DOMParsingTools.getChildTagByName(root,QuestXMLConstants.NEXT_QUEST_TAG);
    q.setNextQuest(buildProxy(nextQuestTag));

    RewardsXMLParser.loadRewards(root,q.getRewards());
    return q;
  }
}
