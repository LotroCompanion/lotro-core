package delta.games.lotro.utils.maths.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.utils.maths.ArrayProgression;
import delta.games.lotro.utils.maths.LinearInterpolatingProgression;
import delta.games.lotro.utils.maths.Progression;

/**
 * Writes progressions to XML documents.
 * @author DAM
 */
public class ProgressionsXMLWriter
{
  /**
   * Write a progression to a XML document.
   * @param hd Transformer handler.
   * @param progression Data to save.
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, final Progression progression) throws SAXException
  {
    if (progression instanceof ArrayProgression)
    {
      writeArrayProgression(hd,(ArrayProgression)progression);
    }
    if (progression instanceof LinearInterpolatingProgression)
    {
      writeLinearInterpolatingProgression(hd,(LinearInterpolatingProgression)progression);
    }
  }

  /**
   * Write an array progression to a XML document.
   * @param hd Transformer handler.
   * @param progression Data to save.
   * @throws SAXException If an error occurs.
   */
  private static void writeArrayProgression(TransformerHandler hd, final ArrayProgression progression) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    int identifier=progression.getIdentifier();
    attrs.addAttribute("","",ProgressionsXMLConstants.IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(identifier));
    int nbPoints=progression.getNumberOfPoints();
    attrs.addAttribute("","",ProgressionsXMLConstants.NB_POINTS_ATTR,XmlWriter.CDATA,String.valueOf(nbPoints));
    hd.startElement("","",ProgressionsXMLConstants.ARRAY_PROGRESSION_TAG,attrs);
    for(int i=0;i<nbPoints;i++)
    {
      AttributesImpl pointAttrs=new AttributesImpl();
      int x=progression.getX(i);
      pointAttrs.addAttribute("","",ProgressionsXMLConstants.X_ATTR,XmlWriter.CDATA,String.valueOf(x));
      float y=progression.getY(i);
      pointAttrs.addAttribute("","",ProgressionsXMLConstants.Y_ATTR,XmlWriter.CDATA,String.valueOf(y));
      hd.startElement("","",ProgressionsXMLConstants.POINT_TAG,pointAttrs);
      hd.endElement("","",ProgressionsXMLConstants.POINT_TAG);
    }
    hd.endElement("","",ProgressionsXMLConstants.ARRAY_PROGRESSION_TAG);
  }

  /**
   * Write an linear interpolating progression to a XML document.
   * @param hd Transformer handler.
   * @param progression Data to save.
   * @throws SAXException If an error occurs.
   */
  private static void writeLinearInterpolatingProgression(TransformerHandler hd, final LinearInterpolatingProgression progression) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    int identifier=progression.getIdentifier();
    attrs.addAttribute("","",ProgressionsXMLConstants.IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(identifier));
    int nbPoints=progression.getNumberOfPoints();
    attrs.addAttribute("","",ProgressionsXMLConstants.NB_POINTS_ATTR,XmlWriter.CDATA,String.valueOf(nbPoints));
    hd.startElement("","",ProgressionsXMLConstants.LINEAR_INTERPOLATION_PROGRESSION_TAG,attrs);
    for(int i=0;i<nbPoints;i++)
    {
      AttributesImpl pointAttrs=new AttributesImpl();
      int x=progression.getX(i);
      pointAttrs.addAttribute("","",ProgressionsXMLConstants.X_ATTR,XmlWriter.CDATA,String.valueOf(x));
      float y=progression.getY(i);
      pointAttrs.addAttribute("","",ProgressionsXMLConstants.Y_ATTR,XmlWriter.CDATA,String.valueOf(y));
      hd.startElement("","",ProgressionsXMLConstants.POINT_TAG,pointAttrs);
      hd.endElement("","",ProgressionsXMLConstants.POINT_TAG);
    }
    hd.endElement("","",ProgressionsXMLConstants.LINEAR_INTERPOLATION_PROGRESSION_TAG);
  }
}
