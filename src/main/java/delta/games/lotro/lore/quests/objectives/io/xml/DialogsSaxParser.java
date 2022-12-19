package delta.games.lotro.lore.quests.objectives.io.xml;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.dialogs.QuestCompletionComment;
import delta.games.lotro.lore.quests.io.xml.QuestXMLConstants;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

/**
 * Parser for dialogs stored in XML.
 * @author DAM
 */
public class DialogsSaxParser
{
  private QuestCompletionComment _comment;
  private QuestDescription _quest;

  public void setQuest(QuestDescription quest)
  {
    _quest=quest;
  }

  public void startElement(String qualifiedName, Attributes attrs)
  {
    if (QuestXMLConstants.QUEST_COMPLETION_COMMENT_TAG.equals(attrs))
    {
      _comment=new QuestCompletionComment();
      _quest.addCompletionComment(_comment);
    }
    else if (QuestXMLConstants.NPC_TAG.equals(attrs))
    {
      Proxy<NpcDescription> npcProxy=SharedXMLUtils.parseNpcProxy(attrs);
      _comment.addWho(npcProxy);
    }
    else if (QuestXMLConstants.TEXT_TAG.equals(attrs))
    {
      String text=SAXParsingTools.getStringAttribute(attrs,QuestXMLConstants.TEXT_ATTR,null);
      _comment.addWhat(text);
    }
  }

  public void endElement(String qualifiedName)
  {
    _comment=null;
  }

  /**
   * Build a dialog element from SAX attributes.
   * @param attrs Input tag.
   * @return the new dialog.
   */
  public static DialogElement parseDialog(Attributes attrs)
  {
    DialogElement ret=new DialogElement();
    // NPC
    Proxy<NpcDescription> npc=SharedXMLUtils.parseNpcProxy(attrs);
    ret.setWho(npc);
    // Text
    String text=SAXParsingTools.getStringAttribute(attrs,QuestXMLConstants.TEXT_ATTR,"");
    ret.setWhat(text);
    return ret;
  }
}
