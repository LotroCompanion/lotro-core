package delta.games.lotro.lore.quests.objectives.io.xml;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserValve;
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
public class DialogsSaxParser extends SAXParserValve<Void>
{
  private QuestCompletionComment _comment;
  private QuestDescription _quest;

  /**
   * Set the parent quest.
   * @param quest Parent quest to set.
   */
  public void setQuest(QuestDescription quest)
  {
    _quest=quest;
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (QuestXMLConstants.QUEST_COMPLETION_COMMENT_TAG.equals(tagName))
    {
      _comment=new QuestCompletionComment();
      _quest.addCompletionComment(_comment);
    }
    else if (QuestXMLConstants.NPC_TAG.equals(tagName))
    {
      Proxy<NpcDescription> npcProxy=SharedXMLUtils.parseNpcProxy(attrs);
      _comment.addWho(npcProxy);
    }
    else if (QuestXMLConstants.TEXT_TAG.equals(tagName))
    {
      String text=SAXParsingTools.getStringAttribute(attrs,QuestXMLConstants.TEXT_ATTR,null);
      _comment.addWhat(text);
    }
    return this;
  }

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if (QuestXMLConstants.QUEST_COMPLETION_COMMENT_TAG.equals(tagName))
    {
      _comment=null;
      return getParent();
    }
    return this;
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
