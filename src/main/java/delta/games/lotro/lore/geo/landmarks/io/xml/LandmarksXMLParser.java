package delta.games.lotro.lore.geo.landmarks.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.geo.landmarks.LandmarkDescription;
import delta.games.lotro.lore.geo.landmarks.LandmarksManager;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for the landmarks stored in XML.
 * @author DAM
 */
public class LandmarksXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public LandmarksXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("landmarks");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public LandmarksManager parseXML(File source)
  {
    LandmarksManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseLandmarks(root);
    }
    return ret;
  }

  /**
   * Build a landmarks manager from an XML tag.
   * @param rootTag Root tag.
   * @return A landmarks manager.
   */
  private LandmarksManager parseLandmarks(Element rootTag)
  {
    LandmarksManager mgr=new LandmarksManager();
    List<Element> landmarkTags=DOMParsingTools.getChildTagsByName(rootTag,LandmarksXMLConstants.LANDMARK_TAG);
    for(Element landmarkTag : landmarkTags)
    {
      LandmarkDescription landmark=parseLandmark(landmarkTag);
      mgr.addLandmark(landmark);
    }
    return mgr;
  }

  private LandmarkDescription parseLandmark(Element landmarkTag)
  {
    NamedNodeMap attrs=landmarkTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,LandmarksXMLConstants.ID_ATTR,0);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    LandmarkDescription ret=new LandmarkDescription(id,name);
    return ret;
  }
}
