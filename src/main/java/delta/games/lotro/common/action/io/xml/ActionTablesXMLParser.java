package delta.games.lotro.common.action.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.common.action.ActionEntry;
import delta.games.lotro.common.action.ActionTable;
import delta.games.lotro.common.action.ActionTableEntry;
import delta.games.lotro.common.action.ActionTables;
import delta.games.lotro.common.action.ActionTablesEntry;
import delta.games.lotro.common.action.ActionTablesManager;
import delta.games.lotro.common.enums.AICooldownChannel;
import delta.games.lotro.common.enums.AIHint;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.utils.enums.EnumXMLUtils;

/**
 * Parser for action tables stored in XML.
 * @author DAM
 */
public class ActionTablesXMLParser
{
  private LotroEnum<AICooldownChannel> _cooldownChannelEnum;

  /**
   * Constructor.
   */
  public ActionTablesXMLParser()
  {
    _cooldownChannelEnum=LotroEnumsRegistry.getInstance().get(AICooldownChannel.class);
  }

  /**
   * Parse an action tables usage.
   * @param root Parent tag.
   * @return Action tables or <code>null</code>.
   */
  public ActionTables parseTablesUsage(Element root)
  {
    List<Element> actionTableTags=DOMParsingTools.getChildTagsByName(root,ActionTablesXMLConstants.ACTION_TABLE_USE_TAG);
    if (actionTableTags.isEmpty())
    {
      return null;
    }
    ActionTables ret=new ActionTables();
    for(Element actionTableTag : actionTableTags)
    {
      NamedNodeMap attrs=actionTableTag.getAttributes();
      // Table ID
      int tableID=DOMParsingTools.getIntAttribute(attrs,ActionTablesXMLConstants.ACTION_TABLE_USE_ID_ATTR,0);
      ActionTablesManager mgr=ActionTablesManager.getInstance();
      ActionTable actionTable=mgr.getActionTable(tableID);
      if (actionTable==null)
      {
        continue;
      }
      ActionTablesEntry entry=new ActionTablesEntry(actionTable);
      // Min level
      Integer minLevel=DOMParsingTools.getIntegerAttribute(attrs,ActionTablesXMLConstants.MIN_LEVEL_ATTR,null);
      entry.setMinLevel(minLevel);
      // Max level
      Integer maxLevel=DOMParsingTools.getIntegerAttribute(attrs,ActionTablesXMLConstants.MAX_LEVEL_ATTR,null);
      entry.setMaxLevel(maxLevel);
      ret.addActionTablesEntry(entry);
    }
    return ret;
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed colors.
   */
  public List<ActionTable> parseXML(File source)
  {
    List<ActionTable> ret=new ArrayList<ActionTable>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> actionTableTags=DOMParsingTools.getChildTagsByName(root,ActionTablesXMLConstants.ACTION_TABLE_TAG);
      for(Element actionTableTag : actionTableTags)
      {
        ActionTable actionTable=parseActionTable(actionTableTag);
        ret.add(actionTable);
      }
    }
    return ret;
  }

  private ActionTable parseActionTable(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,ActionTablesXMLConstants.ACTION_TABLE_ID_ATTR,0);
    ActionTable ret=new ActionTable(id);
    // Entries
    List<Element> tableEntryTags=DOMParsingTools.getChildTagsByName(root,ActionTablesXMLConstants.ACTION_TABLE_ENTRY_TAG);
    for(Element tableEntryTag : tableEntryTags)
    {
      ActionTableEntry entry=new ActionTableEntry();
      NamedNodeMap entryAttrs=tableEntryTag.getAttributes();
      // Probability
      float probability=DOMParsingTools.getFloatAttribute(entryAttrs,ActionTablesXMLConstants.ACTION_TABLE_ENTRY_PROBABILITY_ATTR,0);
      entry.setProbability(probability);
      // Self data/constraints
      // - cooldown
      Float cooldown=DOMParsingTools.getFloatAttribute(entryAttrs,ActionTablesXMLConstants.ACTION_TABLE_ENTRY_COOLDOWN_ATTR,null);
      entry.setCooldown(cooldown);
      // - required hints
      String requiredHints=DOMParsingTools.getStringAttribute(entryAttrs,ActionTablesXMLConstants.ACTION_TABLE_ENTRY_REQUIRED_HINTS_ATTR,null);
      entry.setRequiredHints(parseHints(requiredHints));
      // - disallowed hints
      String disallowedHints=DOMParsingTools.getStringAttribute(entryAttrs,ActionTablesXMLConstants.ACTION_TABLE_ENTRY_DISALLOWED_HINTS_ATTR,null);
      entry.setDisallowedHints(parseHints(disallowedHints));
      // - cooldown channel
      Integer cooldownChannelCode=DOMParsingTools.getIntegerAttribute(entryAttrs,ActionTablesXMLConstants.ACTION_TABLE_ENTRY_COOLDOWN_CHANNEL_ATTR,null);
      if (cooldownChannelCode!=null)
      {
        AICooldownChannel cooldownChannel=_cooldownChannelEnum.getEntry(cooldownChannelCode.intValue());
        entry.setCooldownChannel(cooldownChannel);
      }
      // Target data/constraints
      // - target cooldown
      Float targetCooldown=DOMParsingTools.getFloatAttribute(entryAttrs,ActionTablesXMLConstants.ACTION_TABLE_ENTRY_TARGET_COOLDOWN_ATTR,null);
      entry.setTargetCooldown(targetCooldown);
      // - target required hints
      String targetRequiredHints=DOMParsingTools.getStringAttribute(entryAttrs,ActionTablesXMLConstants.ACTION_TABLE_ENTRY_TARGET_REQUIRED_HINTS_ATTR,null);
      entry.setTargetRequiredHints(parseHints(targetRequiredHints));
      // - target disallowed hints
      String targetDisallowedHints=DOMParsingTools.getStringAttribute(entryAttrs,ActionTablesXMLConstants.ACTION_TABLE_ENTRY_TARGET_DISALLOWED_HINTS_ATTR,null);
      entry.setTargetDisallowedHints(parseHints(targetDisallowedHints));
      // Actions chain
      List<Element> entryTags=DOMParsingTools.getChildTagsByName(tableEntryTag,ActionTablesXMLConstants.ACTION_TAG);
      for(Element entryTag : entryTags)
      {
        ActionEntry action=loadEntry(entryTag);
        if (action!=null)
        {
          entry.addAction(action);
        }
      }
      ret.addEntry(entry);
    }
    return ret;
  }

  private ActionEntry loadEntry(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Skill ID
    int skillID=DOMParsingTools.getIntAttribute(attrs,ActionTablesXMLConstants.ACTION_SKILL_ID_ATTR,0);
    SkillDescription skill=SkillsManager.getInstance().getSkill(skillID);
    if (skill==null)
    {
      return null;
    }
    ActionEntry ret=new ActionEntry(skill);
    Float recovery=DOMParsingTools.getFloatAttribute(attrs,ActionTablesXMLConstants.ACTION_RECOVERY_ATTR,null);
    ret.setRecovery(recovery);
    return ret;
  }

  private List<AIHint> parseHints(String value)
  {
    return EnumXMLUtils.readEnumEntriesList(value,AIHint.class);
  }
}
