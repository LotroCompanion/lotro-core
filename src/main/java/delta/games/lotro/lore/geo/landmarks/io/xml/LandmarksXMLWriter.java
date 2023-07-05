package delta.games.lotro.lore.geo.landmarks.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.geo.landmarks.LandmarkDescription;

/**
 * Writes landmarks to XML files.
 * @author DAM
 */
public class LandmarksXMLWriter
{
  /**
   * Write a file with landmarks.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeLandmarksFile(File toFile, List<LandmarkDescription> data)
  {
    LandmarksXMLWriter writer=new LandmarksXMLWriter();
    boolean ok=writer.writeLandmarks(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write landmarks to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeLandmarks(File outFile, final List<LandmarkDescription> data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeLandmarks(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeLandmarks(TransformerHandler hd, List<LandmarkDescription> data) throws SAXException
  {
    hd.startElement("","",LandmarksXMLConstants.LANDMARKS_TAG,new AttributesImpl());
    for(LandmarkDescription landmark : data)
    {
      writeLandmark(hd,landmark);
    }
    hd.endElement("","",LandmarksXMLConstants.LANDMARKS_TAG);
  }

  private void writeLandmark(TransformerHandler hd, LandmarkDescription landmark) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=landmark.getIdentifier();
    attrs.addAttribute("","",LandmarksXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=landmark.getName();
    attrs.addAttribute("","",LandmarksXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);

    hd.startElement("","",LandmarksXMLConstants.LANDMARK_TAG,attrs);
    hd.endElement("","",LandmarksXMLConstants.LANDMARK_TAG);
  }
}
