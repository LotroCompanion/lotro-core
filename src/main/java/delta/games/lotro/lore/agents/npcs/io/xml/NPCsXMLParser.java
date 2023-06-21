package delta.games.lotro.lore.agents.npcs.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.agents.npcs.NPCsManager;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for the NPCs stored in XML.
 * @author DAM
 */
public class NPCsXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public NPCsXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("npc");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public NPCsManager parseXML(File source)
  {
    NPCsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseNPCs(root);
    }
    return ret;
  }

  /**
   * Build a NPCs manager from an XML tag.
   * @param rootTag Root tag.
   * @return A NPCs manager.
   */
  private NPCsManager parseNPCs(Element rootTag)
  {
    NPCsManager mgr=new NPCsManager();

    List<Element> npcTags=DOMParsingTools.getChildTags(rootTag);
    for(Element npcTag : npcTags)
    {
      NpcDescription npc=parseNPCTag(npcTag);
      mgr.addNPC(npc);
    }
    return mgr;
  }

  private NpcDescription parseNPCTag(Element npcTag)
  {
    NamedNodeMap attrs=npcTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,NPCsXMLConstants.ID_ATTR,0);
    // Name
    String name=I18nRuntimeUtils.getLabel(_i18n,id);
    NpcDescription ret=new NpcDescription(id,name);
    // Title
    String title=DOMParsingTools.getStringAttribute(attrs,NPCsXMLConstants.TITLE_ATTR,"");
    title=I18nRuntimeUtils.getLabel(_i18n,title);
    ret.setTitle(title);
    return ret;
  }
}
