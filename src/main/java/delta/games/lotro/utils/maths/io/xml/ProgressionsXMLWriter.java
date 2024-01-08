package delta.games.lotro.utils.maths.io.xml;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.utils.maths.ArrayProgression;
import delta.games.lotro.utils.maths.ArrayProgressionConstants;
import delta.games.lotro.utils.maths.LinearInterpolatingProgression;
import delta.games.lotro.utils.maths.Progression;

/**
 * Writes progressions to XML documents.
 * @author DAM
 */
public class ProgressionsXMLWriter
{
  /**
   * Write some progressions to a XML file.
   * @param toFile File to write to.
   * @param progressions Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<Progression> progressions)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",ProgressionsXMLConstants.PROGRESSIONS_TAG,new AttributesImpl());
        for(Progression progression : progressions)
        {
          write(hd,progression);
        }
        hd.endElement("","",ProgressionsXMLConstants.PROGRESSIONS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

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
    else if (progression instanceof LinearInterpolatingProgression)
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
    // Min X
    int minX=progression.getMinX();
    if (minX!=1)
    {
      attrs.addAttribute("","",ProgressionsXMLConstants.MIN_X_ATTR,XmlWriter.CDATA,String.valueOf(minX));
    }
    // Number of points
    int nbPoints=progression.getNumberOfPoints();
    attrs.addAttribute("","",ProgressionsXMLConstants.NB_POINTS_ATTR,XmlWriter.CDATA,String.valueOf(nbPoints));
    // Type
    String type=progression.getValueType();
    if (!ArrayProgressionConstants.FLOAT.equals(type))
    {
      attrs.addAttribute("","",ProgressionsXMLConstants.TYPE_ATTR,XmlWriter.CDATA,type);
    }
    hd.startElement("","",ProgressionsXMLConstants.ARRAY_PROGRESSION_TAG,attrs);
    Number previousValue=progression.getY(0);
    int startX=minX;
    int endX=startX;
    for(int i=1;i<nbPoints;i++)
    {
      int x=minX+i;
      Number y=progression.getY(i);
      if (!Objects.equals(previousValue,y))
      {
        writeArrayProgressionItem(hd,endX-startX,previousValue);
        startX=x;
        previousValue=y;
      }
      endX=x;
    }
    writeArrayProgressionItem(hd,endX-startX,previousValue);
    hd.endElement("","",ProgressionsXMLConstants.ARRAY_PROGRESSION_TAG);
  }

  private static void writeArrayProgressionItem(TransformerHandler hd, int count, Number value) throws SAXException
  {
    AttributesImpl pointAttrs=new AttributesImpl();
    if (count>0)
    {
      pointAttrs.addAttribute("","",ProgressionsXMLConstants.COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count+1));
    }
    pointAttrs.addAttribute("","",ProgressionsXMLConstants.Y_ATTR,XmlWriter.CDATA,String.valueOf(value));
    hd.startElement("","",ProgressionsXMLConstants.POINT_TAG,pointAttrs);
    hd.endElement("","",ProgressionsXMLConstants.POINT_TAG);
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
