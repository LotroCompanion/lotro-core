package delta.games.lotro.lore.quests.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.Size;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLParser;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestDescription.FACTION;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLParser;
import delta.games.lotro.utils.Proxy;

/**
 * Parser for quest descriptions stored in XML.
 * @author DAM
 */
public class QuestXMLParser
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
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,QuestXMLConstants.QUEST_ID_ATTR,0);
    q.setIdentifier(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_NAME_ATTR,"");
    q.setName(name);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_CATEGORY_ATTR,null);
    q.setCategory(category);
    // Scope
    String scope=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_SCOPE_ATTR,null);
    q.setQuestScope(scope);
    // Quest arc
    String arc=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_ARC_ATTR,null);
    q.setQuestArc(arc);
    // Size
    String sizeStr=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_SIZE_ATTR,null);
    Size size=Size.valueOf(sizeStr);
    q.setSize(size);
    // Faction
    String factionStr=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_FACTION_ATTR,null);
    FACTION faction=FACTION.valueOf(factionStr);
    q.setFaction(faction);
    // Repeatable
    boolean repeatable=DOMParsingTools.getBooleanAttribute(attrs,QuestXMLConstants.QUEST_REPEATABLE_ATTR,false);
    q.setRepeatable(repeatable);
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
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_DESCRIPTION_ATTR,null);
    q.setDescription(description);
    // Bestower
    String bestower=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_BESTOWER_ATTR,null);
    q.setBestower(bestower);
    // Bestower text
    String bestowerText=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_BESTOWER_TEXT_ATTR,null);
    q.setBestowerText(bestowerText);
    // Objectives
    ObjectivesXMLParser.loadObjectives(root,q.getObjectives());
    // Requirements
    UsageRequirementsXMLParser.parseRequirements(q.getUsageRequirement(),root);

    // Prerequisite quests
    List<Element> prerequisiteTags=DOMParsingTools.getChildTagsByName(root,QuestXMLConstants.PREREQUISITE_TAG);
    for(Element prerequisiteTag : prerequisiteTags)
    {
      Proxy<QuestDescription> proxy=buildProxy(prerequisiteTag);
      if (proxy!=null)
      {
        q.addPrerequisiteQuest(proxy);
      }
    }
    // Next quest
    Element nextQuestTag=DOMParsingTools.getChildTagByName(root,QuestXMLConstants.NEXT_QUEST_TAG);
    q.setNextQuest(buildProxy(nextQuestTag));

    RewardsXMLParser.loadRewards(root,q.getQuestRewards());
    return q;
  }

  private Proxy<QuestDescription> buildProxy(Element tag)
  {
    Proxy<QuestDescription> ret=null;
    if (tag!=null)
    {
      NamedNodeMap attrs=tag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(attrs,QuestXMLConstants.QUEST_PROXY_ID_ATTR,0);
      String name=DOMParsingTools.getStringAttribute(attrs,QuestXMLConstants.QUEST_PROXY_NAME_ATTR,null);
      if (id!=0)
      {
        ret=new Proxy<QuestDescription>();
        ret.setId(id);
        ret.setName(name);
      }
    }
    return ret;
  }
}
