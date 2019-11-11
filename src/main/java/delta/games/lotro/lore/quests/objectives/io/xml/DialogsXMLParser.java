package delta.games.lotro.lore.quests.objectives.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.npc.NpcDescription;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.dialogs.QuestCompletionComment;
import delta.games.lotro.lore.quests.io.xml.QuestXMLConstants;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

/**
 * Parser for dialogs stored in XML.
 * @author DAM
 */
public class DialogsXMLParser
{
  /**
   * Build a dialog element from a tag.
   * @param dialogTag Input tag.
   * @return the new dialog.
   */
  public static DialogElement parseDialog(Element dialogTag)
  {
    DialogElement ret=new DialogElement();
    NamedNodeMap bestowerAttrs=dialogTag.getAttributes();
    // NPC
    Proxy<NpcDescription> npc=SharedXMLUtils.parseNpcProxy(bestowerAttrs);
    ret.setWho(npc);
    // Text
    String text=DOMParsingTools.getStringAttribute(bestowerAttrs,QuestXMLConstants.TEXT_ATTR,"");
    ret.setWhat(text);
    return ret;
  }

  /**
   * Build a quest completion comment from a tag.
   * @param commentTag Input tag.
   * @return the new quest completion comment.
   */
  public static QuestCompletionComment parseQuestCompletionComment(Element commentTag)
  {
    QuestCompletionComment ret=new QuestCompletionComment();
    // NPCs
    List<Element> npcTags=DOMParsingTools.getChildTagsByName(commentTag,QuestXMLConstants.NPC_TAG);
    for(Element npcTag : npcTags)
    {
      Proxy<NpcDescription> npcProxy=SharedXMLUtils.parseNpcProxy(npcTag.getAttributes());
      ret.addWho(npcProxy);
    }
    // Text
    List<Element> textTags=DOMParsingTools.getChildTagsByName(commentTag,QuestXMLConstants.TEXT_TAG);
    for(Element textTag : textTags)
    {
      String text=DOMParsingTools.getStringAttribute(textTag.getAttributes(),QuestXMLConstants.TEXT_ATTR,null);
      ret.addWhat(text);
    }
    return ret;
  }
}
