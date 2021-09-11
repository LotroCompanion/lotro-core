package delta.games.lotro.common.difficulty.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.difficulty.Difficulty;

/**
 * Writes difficulties to XML files.
 * @author DAM
 */
public class DifficultyXMLWriter
{
  /**
   * Write a file with difficulties.
   * @param toFile Output file.
   * @param difficulties Difficulties to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeDifficultiesFile(File toFile, List<Difficulty> difficulties)
  {
    DifficultyXMLWriter writer=new DifficultyXMLWriter();
    boolean ok=writer.writeDifficulties(toFile,difficulties,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write difficulties to a XML file.
   * @param outFile Output file.
   * @param difficulties Difficulties to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeDifficulties(File outFile, final List<Difficulty> difficulties, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeDifficulties(hd,difficulties);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeDifficulties(TransformerHandler hd, List<Difficulty> difficulties) throws Exception
  {
    hd.startElement("","",DifficultyXMLConstants.DIFFICULTIES_TAG,new AttributesImpl());
    for(Difficulty difficulty : difficulties)
    {
      writeDifficulty(hd,difficulty);
    }
    hd.endElement("","",DifficultyXMLConstants.DIFFICULTIES_TAG);
  }

  private void writeDifficulty(TransformerHandler hd, Difficulty difficulty) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Code
    int code=difficulty.getCode();
    attrs.addAttribute("","",DifficultyXMLConstants.DIFFICULTY_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Name
    String name=difficulty.getLabel();
    if (name!=null)
    {
      attrs.addAttribute("","",DifficultyXMLConstants.DIFFICULTY_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",DifficultyXMLConstants.DIFFICULTY_TAG,attrs);
    hd.endElement("","",DifficultyXMLConstants.DIFFICULTY_TAG);
  }
}
