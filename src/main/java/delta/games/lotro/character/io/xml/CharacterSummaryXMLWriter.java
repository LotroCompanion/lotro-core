package delta.games.lotro.character.io.xml;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.StreamTools;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.common.Race;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Writes LOTRO character summaries to XML files.
 * @author DAM
 */
public class CharacterSummaryXMLWriter
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static final String CDATA="CDATA";

  /**
   * Write a character to a XML file.
   * @param outFile Output file.
   * @param character Character to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, CharacterSummary character, String encoding)
  {
    boolean ret;
    FileOutputStream fos=null;
    try
    {
      fos=new FileOutputStream(outFile);
      SAXTransformerFactory tf=(SAXTransformerFactory)TransformerFactory.newInstance();
      TransformerHandler hd=tf.newTransformerHandler();
      Transformer serializer=hd.getTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");

      StreamResult streamResult=new StreamResult(fos);
      hd.setResult(streamResult);
      hd.startDocument();
      AttributesImpl characterAttrs=new AttributesImpl();
      write(characterAttrs,character);
      hd.startElement("","",CharacterXMLConstants.CHARACTER_TAG,characterAttrs);
      hd.endElement("","",CharacterXMLConstants.CHARACTER_TAG);
      hd.endDocument();
      ret=true;
    }
    catch (Exception exception)
    {
      _logger.error("",exception);
      ret=false;
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ret;
  }

  /**
   * Write character summary attributes.
   * @param characterAttrs Attributes to write to.
   * @param character Source data.
   * @throws Exception
   */
  public static void write(AttributesImpl characterAttrs, CharacterSummary character) throws Exception
  {
    // Name
    String name=character.getName();
    if ((name!=null) && (name.length()>0))
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_NAME_ATTR,CDATA,name);
    }
    // Server
    String server=character.getServer();
    if ((server!=null) && (server.length()>0))
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_SERVER_ATTR,CDATA,server);
    }
    // Character class
    CharacterClass characterClass=character.getCharacterClass();
    if (characterClass!=null)
    {
      String cClass=characterClass.getKey();
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_CLASS_ATTR,CDATA,cClass);
    }
    // Race
    Race race=character.getRace();
    if (race!=null)
    {
      String cRace=race.getLabel();
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_RACE_ATTR,CDATA,cRace);
    }
    // Sex
    CharacterSex sex=character.getCharacterSex();
    if (sex!=null)
    {
      String sexKey=sex.getKey();
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_SEX_ATTR,CDATA,sexKey);
    }
    // Region
    String region=character.getRegion();
    if ((region!=null) && (region.length()>0))
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_REGION_ATTR,CDATA,region);
    }
    // Level
    int level=character.getLevel();
    characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_LEVEL_ATTR,CDATA,String.valueOf(level));
  }
}
