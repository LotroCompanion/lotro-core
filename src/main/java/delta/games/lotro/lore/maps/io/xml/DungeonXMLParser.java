package delta.games.lotro.lore.maps.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.maps.Dungeon;
import delta.games.lotro.lore.maps.DungeonsManager;

/**
 * Parser for the dungeons stored in XML.
 * @author DAM
 */
public class DungeonXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public DungeonsManager parseXML(File source)
  {
    DungeonsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseDungeons(root);
    }
    return ret;
  }

  /**
   * Build a dungeons manager from an XML tag.
   * @param rootTag Root tag.
   * @return A dungeons manager.
   */
  private DungeonsManager parseDungeons(Element rootTag)
  {
    DungeonsManager mgr=new DungeonsManager();

    // Dungeons
    List<Element> dungeonTags=DOMParsingTools.getChildTagsByName(rootTag,DungeonXMLConstants.DUNGEON_TAG);
    for(Element dungeonTag : dungeonTags)
    {
      Dungeon dungeon=parseDungeon(dungeonTag);
      mgr.addDungeon(dungeon);
    }
    return mgr;
  }

  private Dungeon parseDungeon(Element dungeonTag)
  {
    NamedNodeMap attrs=dungeonTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,DungeonXMLConstants.ID_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,DungeonXMLConstants.NAME_ATTR,"");
    // Basemap identifier
    int basemapId=DOMParsingTools.getIntAttribute(attrs,DungeonXMLConstants.BASEMAP_ID_ATTR,0);

    Dungeon ret=new Dungeon(id,name,basemapId);
    return ret;
  }
}
