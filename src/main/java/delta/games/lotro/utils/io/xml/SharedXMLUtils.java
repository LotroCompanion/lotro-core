package delta.games.lotro.utils.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.npc.NpcDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Shared XML utilities.
 * @author DAM
 */
public class SharedXMLUtils
{
  /**
   * Write a proxy
   * @param hd
   * @param tagName
   * @param proxy
   * @throws SAXException
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
