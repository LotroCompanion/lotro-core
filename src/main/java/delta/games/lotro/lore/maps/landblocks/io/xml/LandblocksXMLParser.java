package delta.games.lotro.lore.maps.landblocks.io.xml;

import java.io.File;

import org.xml.sax.Attributes;

import delta.common.utils.math.geometry.Vector3D;
import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserEngine;
import delta.common.utils.xml.sax.SAXParserValve;
import delta.games.lotro.lore.geo.BlockReference;
import delta.games.lotro.lore.maps.landblocks.Cell;
import delta.games.lotro.lore.maps.landblocks.Landblock;
import delta.games.lotro.lore.maps.landblocks.LandblocksManager;

/**
 * Parser for the landblocks stored in XML.
 * @author DAM
 */
public class LandblocksXMLParser extends SAXParserValve<LandblocksManager>
{
  private Landblock _landblock;
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public LandblocksManager parseXML(File source)
  {
    SAXParserEngine<LandblocksManager> engine=new SAXParserEngine<>(this);
    LandblocksManager ret=SAXParsingTools.parseFile(source,engine);
    _landblock=null;
    return ret;
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (LandblocksXMLConstants.LANDBLOCK_TAG.equals(tagName))
    {
      // Block ID
      int region=SAXParsingTools.getIntAttribute(attrs,LandblocksXMLConstants.REGION_ATTR,1);
      int blockX=SAXParsingTools.getIntAttribute(attrs,LandblocksXMLConstants.BLOCK_X_ATTR,0);
      int blockY=SAXParsingTools.getIntAttribute(attrs,LandblocksXMLConstants.BLOCK_Y_ATTR,0);
      BlockReference blockId=new BlockReference(region,blockX,blockY);
      _landblock=new Landblock(blockId);
      getResult().addLandblock(_landblock);
      // Area ID
      int areaId=SAXParsingTools.getIntAttribute(attrs,LandblocksXMLConstants.AREA_ID_ATTR,0);
      if (areaId!=0)
      {
        _landblock.setParentArea(areaId);
      }
      // Dungeon ID
      int dungeonId=SAXParsingTools.getIntAttribute(attrs,LandblocksXMLConstants.DUNGEON_ID_ATTR,0);
      if (dungeonId!=0)
      {
        _landblock.setParentDungeon(dungeonId);
      }
      // Height
      float height=SAXParsingTools.getFloatAttribute(attrs,LandblocksXMLConstants.HEIGHT_ATTR,0);
      _landblock.setCenterHeight(height);
    }
    else if (LandblocksXMLConstants.CELL_TAG.equals(tagName))
    {
      int cellIndex=SAXParsingTools.getIntAttribute(attrs,LandblocksXMLConstants.CELL_INDEX_ATTR,0);
      int dungeonIdForCellValue=SAXParsingTools.getIntAttribute(attrs,LandblocksXMLConstants.DUNGEON_ID_ATTR,-1);
      Integer dungeonIdForCell=(dungeonIdForCellValue!=-1)?Integer.valueOf(dungeonIdForCellValue):null;
      float x=SAXParsingTools.getFloatAttribute(attrs,LandblocksXMLConstants.CELL_X_ATTR,0);
      float y=SAXParsingTools.getFloatAttribute(attrs,LandblocksXMLConstants.CELL_Y_ATTR,0);
      float z=SAXParsingTools.getFloatAttribute(attrs,LandblocksXMLConstants.CELL_Z_ATTR,0);
      Vector3D position=new Vector3D();
      position.set(x,y,z);
      Cell cell=new Cell(cellIndex,dungeonIdForCell);
      cell.setPosition(position);
      _landblock.addCell(cell);
    }
    else if (LandblocksXMLConstants.LANDBLOCKS_TAG.equals(tagName))
    {
      setResult(new LandblocksManager());
    }
    return this;
  }
}
