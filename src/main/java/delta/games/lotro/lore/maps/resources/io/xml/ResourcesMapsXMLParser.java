package delta.games.lotro.lore.maps.resources.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.crafting.CraftingData;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.CraftingSystem;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.maps.resources.ResourcesMapDescriptor;
import delta.games.lotro.lore.maps.resources.ResourcesMapsManager;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

/**
 * Parser for the resources maps stored in XML.
 * @author DAM
 */
public class ResourcesMapsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public ResourcesMapsManager parseXML(File source)
  {
    ResourcesMapsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseResourcesMaps(root);
    }
    return ret;
  }

  /**
   * Build a resources maps manager from an XML tag.
   * @param rootTag Root tag.
   * @return A resources maps manager.
   */
  private ResourcesMapsManager parseResourcesMaps(Element rootTag)
  {
    ResourcesMapsManager mgr=new ResourcesMapsManager();

    // Maps
    List<Element> resourcesMapTags=DOMParsingTools.getChildTagsByName(rootTag,ResourcesMapsXMLConstants.RESOURCES_MAP_TAG);
    for(Element resourcesMapTag : resourcesMapTags)
    {
      ResourcesMapDescriptor resourcesMap=parseResourcesMap(resourcesMapTag);
      mgr.addResourcesMap(resourcesMap);
    }
    return mgr;
  }

  private ResourcesMapDescriptor parseResourcesMap(Element resourcesMapTag)
  {
    NamedNodeMap attrs=resourcesMapTag.getAttributes();
    // Profession key
    String professionKey=DOMParsingTools.getStringAttribute(attrs,ResourcesMapsXMLConstants.PROFESSION_KEY_ATTR,null);
    CraftingData craftingData=CraftingSystem.getInstance().getData();
    Profession profession=craftingData.getProfessionsRegistry().getProfessionByKey(professionKey);
    if (profession==null)
    {
      return null;
    }
    // Tier
    int tier=DOMParsingTools.getIntAttribute(attrs,ResourcesMapsXMLConstants.PROFESSION_TIER_ATTR,-1);
    CraftingLevel level=profession.getByTier(tier);
    if (level==null)
    {
      return null;
    }
    ResourcesMapDescriptor ret=new ResourcesMapDescriptor(level);
    // Items
    List<Element> itemTags=DOMParsingTools.getChildTagsByName(resourcesMapTag,ResourcesMapsXMLConstants.ITEM_TAG);
    for(Element itemTag : itemTags)
    {
      Proxy<Item> item=SharedXMLUtils.parseItemProxy(itemTag);
      ret.addItem(item);
    }
    // Maps
    List<Element> mapTags=DOMParsingTools.getChildTagsByName(resourcesMapTag,ResourcesMapsXMLConstants.MAP_TAG);
    for(Element mapTag : mapTags)
    {
      NamedNodeMap mapAttrs=mapTag.getAttributes();
      // ID
      int mapId=DOMParsingTools.getIntAttribute(mapAttrs,ResourcesMapsXMLConstants.MAP_ID_ATTR,0);
      ret.addMapId(mapId);
    }
    return ret;
  }
}
