package delta.games.lotro.lore.quests.objectives.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.Interactable;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.dialogs.QuestCompletionComment;
import delta.games.lotro.lore.quests.io.xml.QuestXMLConstants;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

/**
 * Writes dialogs to XML documents.
 * @author DAM
 */
public class DialogsXMLWriter
{
  /**
   * Write a dialog element.
   * @param hd Output transformer.
   * @param tag Tag to use.
   * @param dialog Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeDialogElement(TransformerHandler hd, String tag, DialogElement dialog) throws SAXException
  {
    AttributesImpl dialogAttrs=new AttributesImpl();
    // NPC
    Interactable npc=dialog.getWho();
    SharedXMLUtils.writeInteractable(npc,dialogAttrs);
    // Text
    String text=dialog.getWhat();
    if (!text.isEmpty())
    {
      dialogAttrs.addAttribute("","",QuestXMLConstants.TEXT_ATTR,XmlWriter.CDATA,text);
    }
    hd.startElement("","",tag,dialogAttrs);
    hd.endElement("","",tag);
  }

  /**
   * Write a quest completion comment.
   * @param hd Output transformer.
   * @param comment Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeCompletionComment(TransformerHandler hd, QuestCompletionComment comment) throws SAXException
  {
    hd.startElement("","",QuestXMLConstants.QUEST_COMPLETION_COMMENT_TAG,new AttributesImpl());
    // NPCs
    for(Interactable npc : comment.getWhos())
    {
      AttributesImpl npcAttrs=new AttributesImpl();
      SharedXMLUtils.writeInteractable(npc,npcAttrs);
      hd.startElement("","",QuestXMLConstants.NPC_TAG,npcAttrs);
      hd.endElement("","",QuestXMLConstants.NPC_TAG);
    }
    // Text
    for(String text : comment.getWhats())
    {
      if (!text.isEmpty())
      {
        AttributesImpl textAttrs=new AttributesImpl();
        textAttrs.addAttribute("","",QuestXMLConstants.TEXT_ATTR,XmlWriter.CDATA,text);
        hd.startElement("","",QuestXMLConstants.TEXT_TAG,textAttrs);
        hd.endElement("","",QuestXMLConstants.TEXT_TAG);
      }
    }
    hd.endElement("","",QuestXMLConstants.QUEST_COMPLETION_COMMENT_TAG);
  }
}
