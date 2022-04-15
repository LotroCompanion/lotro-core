package delta.games.lotro.common.colors.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.colors.ColorDescription;

/**
 * Writes LOTRO colors to XML files.
 * @author DAM
 */
public class ColorXMLWriter
{
  /**
   * Write a file with colors.
   * @param toFile Output file.
   * @param colors Colors to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeColorsFile(File toFile, List<ColorDescription> colors)
  {
    ColorXMLWriter writer=new ColorXMLWriter();
    boolean ok=writer.writeColors(toFile,colors,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write colors to a XML file.
   * @param outFile Output file.
   * @param colors Colors to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeColors(File outFile, final List<ColorDescription> colors, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeColors(hd,colors);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeColors(TransformerHandler hd, List<ColorDescription> colors) throws Exception
  {
    hd.startElement("","",ColorXMLConstants.COLORS_TAG,new AttributesImpl());
    for(ColorDescription color : colors)
    {
      writeColor(hd,color);
    }
    hd.endElement("","",ColorXMLConstants.COLORS_TAG);
  }

  private void writeColor(TransformerHandler hd, ColorDescription color) throws Exception
  {
    AttributesImpl colorAttrs=new AttributesImpl();

    // Integer code
    int code=color.getIntCode();
    colorAttrs.addAttribute("","",ColorXMLConstants.COLOR_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Code
    float floatCode=color.getCode();
    colorAttrs.addAttribute("","",ColorXMLConstants.COLOR_FLOAT_CODE_ATTR,XmlWriter.CDATA,String.valueOf(floatCode));
    // Name
    String name=color.getName();
    if (name!=null)
    {
      colorAttrs.addAttribute("","",ColorXMLConstants.COLOR_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",ColorXMLConstants.COLOR_TAG,colorAttrs);
    hd.endElement("","",ColorXMLConstants.COLOR_TAG);
  }
}
