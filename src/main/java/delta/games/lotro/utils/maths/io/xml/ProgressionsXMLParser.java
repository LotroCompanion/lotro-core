package delta.games.lotro.utils.maths.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.utils.maths.ArrayProgression;
import delta.games.lotro.utils.maths.LinearInterpolatingProgression;
import delta.games.lotro.utils.maths.Progression;

/**
 * Parser for progressions stored in XML.
 * @author DAM
 */
public class ProgressionsXMLParser
{
  /**
   * Read a progression from an XML tag.
   * @param root Tag to use.
   * @return Parsed data or <code>null</code>.
   */
  public Progression parseXML(Element root)
  {
    String tagName=root.getTagName();
    if (ProgressionsXMLConstants.ARRAY_PROGRESSION_TAG.equals(tagName))
    {
      return parseArrayProgression(root);
    }
    if (ProgressionsXMLConstants.LINEAR_INTERPOLATION_PROGRESSION_TAG.equals(tagName))
    {
      return parseLinearInterpolatingProgression(root);
    }
    return null;
  }

  private ArrayProgression parseArrayProgression(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    int identifier=DOMParsingTools.getIntAttribute(attrs,ProgressionsXMLConstants.IDENTIFIER_ATTR,0);
    int nbPoints=DOMParsingTools.getIntAttribute(attrs,ProgressionsXMLConstants.NB_POINTS_ATTR,0);
    ArrayProgression progression=new ArrayProgression(identifier,nbPoints);

    int index=0;
    List<Element> pointTags=DOMParsingTools.getChildTagsByName(root,ProgressionsXMLConstants.POINT_TAG);
    for(Element pointTag : pointTags)
    {
      NamedNodeMap pointAttrs=pointTag.getAttributes();
      int x=DOMParsingTools.getIntAttribute(pointAttrs,ProgressionsXMLConstants.X_ATTR,0);
      int y=DOMParsingTools.getIntAttribute(pointAttrs,ProgressionsXMLConstants.Y_ATTR,0);
      progression.set(index,x,y);
      index++;
    }
    return progression;
  }

  private LinearInterpolatingProgression parseLinearInterpolatingProgression(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    int identifier=DOMParsingTools.getIntAttribute(attrs,ProgressionsXMLConstants.IDENTIFIER_ATTR,0);
    int nbPoints=DOMParsingTools.getIntAttribute(attrs,ProgressionsXMLConstants.NB_POINTS_ATTR,0);
    LinearInterpolatingProgression progression=new LinearInterpolatingProgression(identifier,nbPoints);

    int index=0;
    List<Element> pointTags=DOMParsingTools.getChildTagsByName(root,ProgressionsXMLConstants.POINT_TAG);
    for(Element pointTag : pointTags)
    {
      NamedNodeMap pointAttrs=pointTag.getAttributes();
      int x=DOMParsingTools.getIntAttribute(pointAttrs,ProgressionsXMLConstants.X_ATTR,0);
      int y=DOMParsingTools.getIntAttribute(pointAttrs,ProgressionsXMLConstants.Y_ATTR,0);
      progression.set(index,x,y);
      index++;
    }
    return progression;
  }
}
