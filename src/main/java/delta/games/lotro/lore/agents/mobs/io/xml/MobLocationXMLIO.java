package delta.games.lotro.lore.agents.mobs.io.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.MobDivision;
import delta.games.lotro.lore.agents.mobs.MobLocation;
import delta.games.lotro.lore.geo.landmarks.LandmarkDescription;
import delta.games.lotro.lore.geo.landmarks.LandmarksManager;
import delta.games.lotro.lore.maps.GeoAreasManager;
import delta.games.lotro.lore.maps.LandDivision;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLConstants;

/**
 * XML I/O for mob locations.
 * @author DAM
 */
public class MobLocationXMLIO
{
  /**
   * Write mob location attributes into the given attributes storage.
   * @param selectionAttrs Storage.
   * @param where Classification data.
   */
  public static void writeMobLocation(AttributesImpl selectionAttrs, MobLocation where)
  {
    if (where==null)
    {
      return;
    }
    // Mob division
    MobDivision mobDivision=where.getMobDivision();
    if (mobDivision!=null)
    {
      int mobDivisionCode=mobDivision.getCode();
      selectionAttrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_SELECTION_MOB_DIVISION_ATTR,XmlWriter.CDATA,String.valueOf(mobDivisionCode));
    }
    // Land division
    LandDivision landDivision=where.getLandDivision();
    if (landDivision!=null)
    {
      // ID
      int id=landDivision.getIdentifier();
      selectionAttrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_SELECTION_LAND_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=landDivision.getName();
      if (name!=null)
      {
        selectionAttrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_SELECTION_LAND_NAME_ATTR,XmlWriter.CDATA,name);
      }
    }
    // Landmark
    LandmarkDescription landmark=where.getLandmark();
    if (landmark!=null)
    {
      int landmarkId=landmark.getIdentifier();
      selectionAttrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_SELECTION_LANDMARK_ID_ATTR,XmlWriter.CDATA,String.valueOf(landmarkId));
      String landmarkName=landmark.getName();
      selectionAttrs.addAttribute("","",ObjectivesXMLConstants.MONSTER_SELECTION_LANDMARK_NAME_ATTR,XmlWriter.CDATA,landmarkName);
    }
  }

  /**
   * Parse a mob location from the given SAX attributes.
   * @param attrs Input attributes.
   * @return A mob location or <code>null</code> if no data.
   */
  public static MobLocation parseMobLocation(Attributes attrs)
  {
    // - mob division
    MobDivision mobDivision=null;
    int mobDivisionCode=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.MONSTER_SELECTION_MOB_DIVISION_ATTR,-1);
    if (mobDivisionCode>0)
    {
      LotroEnum<MobDivision> mobDivisionEnum=LotroEnumsRegistry.getInstance().get(MobDivision.class);
      mobDivision=mobDivisionEnum.getEntry(mobDivisionCode);
    }
    // - land division
    LandDivision land=null;
    int whereId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.MONSTER_SELECTION_LAND_ID_ATTR,0);
    if (whereId!=0)
    {
      land=GeoAreasManager.getInstance().getLandById(whereId);
    }
    // - landmark
    LandmarkDescription landmark=null;
    int landmarkId=SAXParsingTools.getIntAttribute(attrs,ObjectivesXMLConstants.MONSTER_SELECTION_LANDMARK_ID_ATTR,0);
    if (landmarkId!=0)
    {
      landmark=LandmarksManager.getInstance().getLandmarkById(landmarkId);
    }
    MobLocation location=null;
    if ((mobDivision!=null) || (land!=null) || (landmark!=null))
    {
      location=new MobLocation(mobDivision,land,landmark);
    }
    return location;
  }
}
