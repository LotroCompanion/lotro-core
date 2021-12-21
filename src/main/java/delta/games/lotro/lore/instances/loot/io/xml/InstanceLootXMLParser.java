package delta.games.lotro.lore.instances.loot.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.math.Range;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.PrivateEncountersManager;
import delta.games.lotro.lore.instances.loot.InstanceLootEntry;
import delta.games.lotro.lore.instances.loot.InstanceLootParameters;
import delta.games.lotro.lore.instances.loot.InstanceLootTablesManager;
import delta.games.lotro.lore.instances.loot.InstanceLoots;
import delta.games.lotro.lore.instances.loot.InstanceLootsTable;

/**
 * Parser for the instance loots stored in XML.
 * @author DAM
 */
public class InstanceLootXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public InstanceLootTablesManager parseXML(File source)
  {
    InstanceLootTablesManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseInstanceLoots(root);
    }
    return ret;
  }

  /**
   * Build an instance loots manager from an XML tag.
   * @param rootTag Root tag.
   * @return An instance loots manager.
   */
  private InstanceLootTablesManager parseInstanceLoots(Element rootTag)
  {
    InstanceLootTablesManager mgr=new InstanceLootTablesManager();
    List<Element> tableTags=DOMParsingTools.getChildTagsByName(rootTag,InstanceLootXMLConstants.TABLE_TAG);
    for(Element tableTag : tableTags)
    {
      InstanceLootsTable instance=parseTable(tableTag);
      mgr.addTable(instance);
    }
    return mgr;
  }

  /**
   * Build a table from an XML tag.
   * @param tableTag Root tag.
   * @return A loots table.
   */
  private InstanceLootsTable parseTable(Element tableTag)
  {
    int tableId=DOMParsingTools.getIntAttribute(tableTag.getAttributes(),InstanceLootXMLConstants.TABLE_ID_ATTR,0);
    InstanceLootsTable table=new InstanceLootsTable(tableId);
    List<Element> instanceTags=DOMParsingTools.getChildTagsByName(tableTag,InstanceLootXMLConstants.INSTANCE_TAG);
    for(Element instanceTag : instanceTags)
    {
      InstanceLoots instance=parseInstance(instanceTag);
      table.addInstanceLoots(instance);
    }
    return table;
  }

  private InstanceLoots parseInstance(Element instanceTag)
  {
    NamedNodeMap attrs=instanceTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,InstanceLootXMLConstants.INSTANCE_ID_ATTR,0);

    PrivateEncounter instance=PrivateEncountersManager.getInstance().getPrivateEncounterById(id);
    InstanceLoots ret=new InstanceLoots(instance);
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(instanceTag,InstanceLootXMLConstants.LOOT_ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      int minDifficulty=DOMParsingTools.getIntAttribute(entryAttrs,InstanceLootXMLConstants.MIN_DIFFICULTY_ATTR,0);
      int maxDifficulty=DOMParsingTools.getIntAttribute(entryAttrs,InstanceLootXMLConstants.MAX_DIFFICULTY_ATTR,0);
      Range difficultyRange=new Range(minDifficulty,maxDifficulty);
      int minGroupSize=DOMParsingTools.getIntAttribute(entryAttrs,InstanceLootXMLConstants.MIN_GROUP_SIZE_ATTR,0);
      int maxGroupSize=DOMParsingTools.getIntAttribute(entryAttrs,InstanceLootXMLConstants.MAX_GROUP_SIZE_ATTR,0);
      Range groupSizeRange=new Range(minGroupSize,maxGroupSize);
      int minLevel=DOMParsingTools.getIntAttribute(entryAttrs,InstanceLootXMLConstants.MIN_LEVEL_ATTR,0);
      int maxLevel=DOMParsingTools.getIntAttribute(entryAttrs,InstanceLootXMLConstants.MAX_LEVEL_ATTR,0);
      Range levelRange=new Range(minLevel,maxLevel);
      int trophyListId=DOMParsingTools.getIntAttribute(entryAttrs,InstanceLootXMLConstants.TROPHY_LIST_ID_ATTR,0);
      InstanceLootParameters parameters=new InstanceLootParameters(difficultyRange,groupSizeRange,levelRange);
      InstanceLootEntry entry=new InstanceLootEntry(parameters);

      LootsManager lootsMgr=LootsManager.getInstance();
      if (trophyListId!=0)
      {
        TrophyList trophyList=(TrophyList)lootsMgr.getTables().getItem(trophyListId);
        entry.setTrophyList(trophyList);
      }
      ret.addEntry(entry);
    }
    return ret;
  }
}
