package delta.games.lotro.lore.mobs.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.mobs.MobDescription;
import delta.games.lotro.lore.mobs.MobsManager;

/**
 * Parser for the mobs stored in XML.
 * @author DAM
 */
public class MobsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public MobsManager parseXML(File source)
  {
    MobsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseMobs(root);
    }
    return ret;
  }

  /**
   * Build a mobs manager from an XML tag.
   * @param rootTag Root tag.
   * @return A mobs manager.
   */
  private MobsManager parseMobs(Element rootTag)
  {
    MobsManager mgr=new MobsManager();

    List<Element> mobTags=DOMParsingTools.getChildTags(rootTag);
    for(Element mobTag : mobTags)
    {
      MobDescription mob=parseMobTag(mobTag);
      mgr.addMob(mob);
    }
    return mgr;
  }

  private MobDescription parseMobTag(Element mobTag)
  {
    NamedNodeMap attrs=mobTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,MobsXMLConstants.ID_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,MobsXMLConstants.NAME_ATTR,"");
    MobDescription ret=new MobDescription(id,name);
    return ret;
  }
}
