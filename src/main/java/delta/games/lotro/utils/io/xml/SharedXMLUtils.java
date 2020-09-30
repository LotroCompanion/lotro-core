package delta.games.lotro.utils.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
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
  public static Proxy<NpcDescription> parseNpcProxy(NamedNodeMap attrs)
  {
    Proxy<NpcDescription> proxy=null;
    // NPC proxy
    // - id
    int npcId=DOMParsingTools.getIntAttribute(attrs,SharedXMLConstants.NPC_ID_ATTR,0);
    if (npcId!=0)
    {
      // - name
      String npcName=DOMParsingTools.getStringAttribute(attrs,SharedXMLConstants.NPC_NAME_ATTR,"?");
      proxy=new Proxy<NpcDescription>();
      proxy.setId(npcId);
      proxy.setName(npcName);
    }
    return proxy;
  }

  /**
   * Write a NPC proxy into the given attributes.
   * @param proxy Proxy to write.
   * @param attrs Storage.
   */
  public static void writeNpcProxy(Proxy<NpcDescription> proxy, AttributesImpl attrs)
  {
    if (proxy!=null)
    {
      // ID
      int id=proxy.getId();
      attrs.addAttribute("","",SharedXMLConstants.NPC_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=proxy.getName();
      if (name!=null)
      {
        attrs.addAttribute("","",SharedXMLConstants.NPC_NAME_ATTR,XmlWriter.CDATA,name);
      }
    }
  }
}
