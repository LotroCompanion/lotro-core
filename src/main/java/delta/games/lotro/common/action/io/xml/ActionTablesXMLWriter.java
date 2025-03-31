package delta.games.lotro.common.action.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.action.ActionEntry;
import delta.games.lotro.common.action.ActionTable;
import delta.games.lotro.common.action.ActionTableEntry;
import delta.games.lotro.common.action.ActionTables;
import delta.games.lotro.common.action.ActionTablesEntry;
import delta.games.lotro.common.enums.AICooldownChannel;
import delta.games.lotro.common.enums.AIHint;

/**
 * Writes action tables to XML files.
 * @author DAM
 */
public class ActionTablesXMLWriter
{
  /**
   * Write a file with action tables.
   * @param toFile Output file.
   * @param actionTables Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeActionTablesFile(File toFile, List<ActionTable> actionTables)
  {
    ActionTablesXMLWriter writer=new ActionTablesXMLWriter();
    boolean ok=writer.writeActionTables(toFile,actionTables,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write action tables to a XML file.
   * @param outFile Output file.
   * @param actionTables Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeActionTables(File outFile, final List<ActionTable> actionTables, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeActionTables(hd,actionTables);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeActionTables(TransformerHandler hd, List<ActionTable> actionTables) throws SAXException
  {
    hd.startElement("","",ActionTablesXMLConstants.ACTION_TABLES_TAG,new AttributesImpl());
    for(ActionTable actionTable : actionTables)
    {
      writeActionTable(hd,actionTable);
    }
    hd.endElement("","",ActionTablesXMLConstants.ACTION_TABLES_TAG);
  }

  private void writeActionTable(TransformerHandler hd, ActionTable actionTable) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=actionTable.getIdentifier();
    attrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",ActionTablesXMLConstants.ACTION_TABLE_TAG,attrs);
    // Entries
    for(ActionTableEntry entry : actionTable.getEntries())
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      // Probability
      float probability=entry.getProbability();
      entryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_PROBABILITY_ATTR,XmlWriter.CDATA,String.valueOf(probability));
      // Cooldown
      Float cooldown=entry.getCooldown();
      if (cooldown!=null)
      {
        entryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_COOLDOWN_ATTR,XmlWriter.CDATA,cooldown.toString());
      }
      // Required hints
      String requiredHints=persistAIHints(entry.getRequiredHints());
      if (!requiredHints.isEmpty())
      {
        entryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_REQUIRED_HINTS_ATTR,XmlWriter.CDATA,requiredHints);
      }
      // Disallowed hints
      String disallowedHints=persistAIHints(entry.getDisallowedHints());
      if (!disallowedHints.isEmpty())
      {
        entryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_DISALLOWED_HINTS_ATTR,XmlWriter.CDATA,disallowedHints);
      }
      AICooldownChannel cooldownChannel=entry.getCooldownChannel();
      if (cooldownChannel!=null)
      {
        entryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_COOLDOWN_CHANNEL_ATTR,XmlWriter.CDATA,String.valueOf(cooldownChannel.getCode()));
      }
      // Target cooldown
      Float targetCooldown=entry.getTargetCooldown();
      if (targetCooldown!=null)
      {
        entryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_TARGET_COOLDOWN_ATTR,XmlWriter.CDATA,targetCooldown.toString());
      }
      // Target required hints
      String targetRequiredHints=persistAIHints(entry.getTargetRequiredHints());
      if (!targetRequiredHints.isEmpty())
      {
        entryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_TARGET_REQUIRED_HINTS_ATTR,XmlWriter.CDATA,targetRequiredHints);
      }
      // Target disallowed hints
      String targetDisallowedHints=persistAIHints(entry.getTargetDisallowedHints());
      if (!targetDisallowedHints.isEmpty())
      {
        entryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_TARGET_DISALLOWED_HINTS_ATTR,XmlWriter.CDATA,targetDisallowedHints);
      }
      // Actions chain
      hd.startElement("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_TAG,entryAttrs);
      List<ActionEntry> actions=entry.getActionsChain();
      for(ActionEntry actionEntry : actions)
      {
        AttributesImpl actionEntryAttrs=new AttributesImpl();
        // Skill
        SkillDescription skill=actionEntry.getSkill();
        int skillID=skill.getIdentifier();
        actionEntryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(skillID));
        String skillName=skill.getName();
        actionEntryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_SKILL_NAME_ATTR,XmlWriter.CDATA,skillName);
        // Recovery
        Float recovery=actionEntry.getRecovery();
        if (recovery!=null)
        {
          actionEntryAttrs.addAttribute("","",ActionTablesXMLConstants.ACTION_RECOVERY_ATTR,XmlWriter.CDATA,recovery.toString());
        }
        hd.startElement("","",ActionTablesXMLConstants.ACTION_TAG,actionEntryAttrs);
        hd.endElement("","",ActionTablesXMLConstants.ACTION_TAG);
      }
      hd.endElement("","",ActionTablesXMLConstants.ACTION_TABLE_ENTRY_TAG);
    }
    hd.endElement("","",ActionTablesXMLConstants.ACTION_TABLE_TAG);
  }

  private String persistAIHints(List<AIHint> hints)
  {
    StringBuilder sb=new StringBuilder();
    for(AIHint hint : hints)
    {
      if (sb.length()>0)
      {
        sb.append(',');
      }
      sb.append(hint.getCode());
    }
    return sb.toString();
  }

  /**
   * Write an action tables.
   * @param hd Output stream.
   * @param actionTables Data to write.
   * @throws SAXException If an error occurs.
   */
  public void writeActionTablesUsage(TransformerHandler hd, ActionTables actionTables) throws SAXException
  {
    if (actionTables==null)
    {
      return;
    }
    for(ActionTablesEntry actionTableEntry : actionTables.getEntries())
    {
      AttributesImpl attrs=new AttributesImpl();

      ActionTable table=actionTableEntry.getTable();
      // Identifier
      int id=table.getIdentifier();
      attrs.addAttribute("","",ActionTablesXMLConstants.ACTION_TABLE_USE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Min Level
      Integer minLevel=actionTableEntry.getMinLevel();
      if (minLevel!=null)
      {
        attrs.addAttribute("","",ActionTablesXMLConstants.MIN_LEVEL_ATTR,XmlWriter.CDATA,minLevel.toString());
      }
      // Max level
      Integer maxLevel=actionTableEntry.getMaxLevel();
      if (maxLevel!=null)
      {
        attrs.addAttribute("","",ActionTablesXMLConstants.MAX_LEVEL_ATTR,XmlWriter.CDATA,maxLevel.toString());
      }
      hd.startElement("","",ActionTablesXMLConstants.ACTION_TABLE_USE_TAG,attrs);
      hd.endElement("","",ActionTablesXMLConstants.ACTION_TABLE_USE_TAG);
    }
  }
}
