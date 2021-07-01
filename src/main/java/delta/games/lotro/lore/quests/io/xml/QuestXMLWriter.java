package delta.games.lotro.lore.quests.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.LockType;
import delta.games.lotro.common.Repeatability;
import delta.games.lotro.common.Size;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLWriter;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLWriter;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestDescription.FACTION;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.dialogs.QuestCompletionComment;
import delta.games.lotro.lore.quests.objectives.io.xml.DialogsXMLWriter;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLWriter;
import delta.games.lotro.utils.Proxy;

/**
 * Writes LOTRO quests to XML files.
 * @author DAM
 */
public class QuestXMLWriter extends AchievableXMLWriter
{
  /**
   * Write a file with quests.
   * @param toFile Output file.
   * @param quests Quests to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeQuestsFile(File toFile, List<QuestDescription> quests)
  {
    QuestXMLWriter writer=new QuestXMLWriter();
    Collections.sort(quests,new IdentifiableComparator<QuestDescription>());
    boolean ok=writer.writeQuests(toFile,quests,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write quests to a XML file.
   * @param outFile Output file.
   * @param quests Quests to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeQuests(File outFile, final List<QuestDescription> quests, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",QuestXMLConstants.QUESTS_TAG,new AttributesImpl());
        for(QuestDescription quest : quests)
        {
          write(hd,quest);
        }
        hd.endElement("","",QuestXMLConstants.QUESTS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, QuestDescription quest) throws Exception
  {
    AttributesImpl questAttrs=new AttributesImpl();

    // Shared achievable attributes
    writeAttributes(hd,questAttrs,quest);
    // Scope
    String scope=quest.getQuestScope();
    if (scope.length()>0)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_SCOPE_ATTR,XmlWriter.CDATA,scope);
    }
    // Quest arc
    String arc=quest.getQuestArc();
    if (arc.length()>0)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_ARC_ATTR,XmlWriter.CDATA,arc);
    }
    // Size
    Size size=quest.getSize();
    if (size!=Size.SOLO)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_SIZE_ATTR,XmlWriter.CDATA,size.name());
    }
    // Faction
    FACTION faction=quest.getFaction();
    if (faction==FACTION.MONSTER_PLAY)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_FACTION_ATTR,XmlWriter.CDATA,faction.name());
    }
    // Repeatable
    Repeatability repeatability=quest.getRepeatability();
    if (repeatability!=Repeatability.NOT_REPEATABLE)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_REPEATABLE_ATTR,XmlWriter.CDATA,String.valueOf(repeatability.getCode()));
    }
    // Lock type
    LockType lockType=quest.getLockType();
    if (lockType!=null)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_LOCK_TYPE_ATTR,XmlWriter.CDATA,lockType.name());
    }
    // Instanced?
    boolean instanced=quest.isInstanced();
    if (instanced)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_INSTANCED_ATTR,XmlWriter.CDATA,String.valueOf(instanced));
    }
    // Shareable?
    boolean shareable=quest.isShareable();
    if (!shareable)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_SHAREABLE_ATTR,XmlWriter.CDATA,String.valueOf(shareable));
    }
    // Session play?
    boolean sessionPlay=quest.isSessionPlay();
    if (sessionPlay)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_SESSION_PLAY_ATTR,XmlWriter.CDATA,String.valueOf(sessionPlay));
    }
    // Auto-bestowed?
    boolean autoBestowed=quest.isAutoBestowed();
    if (autoBestowed)
    {
      questAttrs.addAttribute("","",QuestXMLConstants.QUEST_AUTO_BESTOWED_ATTR,XmlWriter.CDATA,String.valueOf(autoBestowed));
    }
    // Obsolete?
    boolean obsolete=quest.isObsolete();
    if (obsolete)
    {
      questAttrs.addAttribute("","",AchievableXMLConstants.OBSOLETE_ATTR,XmlWriter.CDATA,String.valueOf(obsolete));
    }
    // Requirements
    UsageRequirementsXMLWriter.write(questAttrs,quest.getUsageRequirement());
    // Description
    String description=quest.getDescription();
    if (description.length()>0)
    {
      questAttrs.addAttribute("","",AchievableXMLConstants.DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",QuestXMLConstants.QUEST_TAG,questAttrs);

    // Bestowers
    for(DialogElement bestower : quest.getBestowers())
    {
      DialogsXMLWriter.writeDialogElement(hd,QuestXMLConstants.BESTOWER_TAG,bestower);
    }
    // Objectives
    ObjectivesXMLWriter.write(hd,quest.getObjectives());
    // End dialogs
    for(DialogElement endDialog : quest.getEndDialogs())
    {
      DialogsXMLWriter.writeDialogElement(hd,QuestXMLConstants.END_DIALOG_TAG,endDialog);
    }
    // Completion comments
    for(QuestCompletionComment comment : quest.getCompletionComments())
    {
      DialogsXMLWriter.writeCompletionComment(hd,comment);
    }
    // Pre-requisites
    writePrerequisites(hd,quest);
    // Maps
    writeMaps(hd,quest);
    // Next quest
    Proxy<Achievable> nextQuest=quest.getNextQuest();
    writeAchievableProxy(hd,nextQuest,QuestXMLConstants.NEXT_QUEST_TAG);
    RewardsXMLWriter.write(hd,quest.getRewards());
    hd.endElement("","",QuestXMLConstants.QUEST_TAG);
  }
}
