package delta.games.lotro.utils.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Interactable;
import delta.games.lotro.lore.agents.AgentDescription;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobsManager;
import delta.games.lotro.lore.agents.npcs.NPCsManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLConstants;
import delta.games.lotro.utils.Proxy;

/**
 * Shared XML utilities.
 * @author DAM
 */
public class SharedXMLUtils
{
  private static final Logger LOGGER=Logger.getLogger(SharedXMLUtils.class);

  /**
   * Parse an item proxy.
   * @param itemTag Source tag.
   * @return a proxy or <code>null</code> if not valid.
   */
  public static Proxy<Item> parseItemProxy(Element itemTag)
  {
    Proxy<Item> proxy=null;
    NamedNodeMap attrs=itemTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,SharedXMLConstants.PROXY_ID_ATTR,0);
    if (id!=0)
    {
      Item item=ItemsManager.getInstance().getItem(id);
      if (item!=null)
      {
        proxy=new Proxy<Item>();
        proxy.setId(id);
        proxy.setName(item.getName());
        proxy.setObject(item);
      }
    }
    else
    {
      LOGGER.warn("Could not find item with ID: "+id);
    }
    return proxy;
  }


  /**
   * Write a proxy.
   * @param hd Output.
   * @param tagName Tag to use.
   * @param proxy Proxy data.
   * @throws SAXException If an error occurs.
   */
  public static void writeProxy(TransformerHandler hd, String tagName, Proxy<? extends Identifiable> proxy) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    int id=proxy.getId();
    if (id!=0)
    {
      attrs.addAttribute("","",SharedXMLConstants.PROXY_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    String name=proxy.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",SharedXMLConstants.PROXY_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }

  /**
   * Load NPC proxy from XML attributes.
   * @param attrs Input.
   * @return A NPC proxy or <code>null</code> if none.
   */
  public static Interactable parseInteractable(Attributes attrs)
  {
    Interactable ret=null;
    // NPC
    // - id
    int npcId=SAXParsingTools.getIntAttribute(attrs,SharedXMLConstants.NPC_ID_ATTR,0);
    if (npcId!=0)
    {
      // - NPC?
      ret=NPCsManager.getInstance().getNPCById(npcId);
      if (ret!=null)
      {
        return ret;
      }
      // - Item?
      ret=ItemsManager.getInstance().getItem(npcId);
      if (ret!=null)
      {
        return ret;
      }
      // - Mob?
      ret=MobsManager.getInstance().getMobById(npcId);
      if (ret!=null)
      {
        return ret;
      }
    }
    int mobId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.MOB_ID_ATTR,0);
    if (mobId!=0)
    {
      ret=MobsManager.getInstance().getMobById(mobId);
    }
    return ret;
  }


  /**
   * Load an agent from XML attributes.
   * @param attrs Input.
   * @return An agent or <code>null</code> if none.
   */
  public static AgentDescription parseAgent(Attributes attrs)
  {
    AgentDescription ret=null;
    // NPC
    // - id
    int npcId=SAXParsingTools.getIntAttribute(attrs,SharedXMLConstants.NPC_ID_ATTR,0);
    if (npcId!=0)
    {
      // - NPC?
      ret=NPCsManager.getInstance().getNPCById(npcId);
      if (ret!=null)
      {
        return ret;
      }
    }
    int mobId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.MOB_ID_ATTR,0);
    if (mobId!=0)
    {
      ret=MobsManager.getInstance().getMobById(mobId);
    }
    return ret;
  }

  /**
   * Write an interactable into the given attributes.
   * @param interactable Interactable to write.
   * @param attrs Storage.
   */
  public static void writeInteractable(Interactable interactable, AttributesImpl attrs)
  {
    if (interactable!=null)
    {
      boolean isMob=(interactable instanceof MobDescription);
      String idTag=(isMob?ObjectivesXMLConstants.MOB_ID_ATTR:SharedXMLConstants.NPC_ID_ATTR);
      String nameTag=(isMob?ObjectivesXMLConstants.MOB_NAME_ATTR:SharedXMLConstants.NPC_NAME_ATTR);
      // ID
      int id=interactable.getIdentifier();
      attrs.addAttribute("","",idTag,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=interactable.getName();
      if (name!=null)
      {
        attrs.addAttribute("","",nameTag,XmlWriter.CDATA,name);
      }
    }
  }
}
