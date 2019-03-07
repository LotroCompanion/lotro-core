package delta.games.lotro.character.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.common.Race;

/**
 * Writes LOTRO character summaries to XML files.
 * @author DAM
 */
public class CharacterSummaryXMLWriter
{
  /**
   * Write a character to a XML file.
   * @param outFile Output file.
   * @param character Character to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CharacterSummary character, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl characterAttrs=new AttributesImpl();
        write(characterAttrs,character);
        hd.startElement("","",CharacterXMLConstants.CHARACTER_TAG,characterAttrs);
        hd.endElement("","",CharacterXMLConstants.CHARACTER_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write character summary attributes.
   * @param characterAttrs Attributes to write to.
   * @param character Source data.
   * @throws Exception If an error occurs.
   */
  public static void write(AttributesImpl characterAttrs, CharacterSummary character) throws Exception
  {
    // Name
    String name=character.getName();
    if ((name!=null) && (name.length()>0))
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Server
    String server=character.getServer();
    if ((server!=null) && (server.length()>0))
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_SERVER_ATTR,XmlWriter.CDATA,server);
    }
    // Account
    String accountName=character.getAccountName();
    if ((accountName!=null) && (accountName.length()>0))
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_ACCOUNT_ATTR,XmlWriter.CDATA,accountName);
    }
    // Character class
    CharacterClass characterClass=character.getCharacterClass();
    if (characterClass!=null)
    {
      String cClass=characterClass.getKey();
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_CLASS_ATTR,XmlWriter.CDATA,cClass);
    }
    // Race
    Race race=character.getRace();
    if (race!=null)
    {
      String cRace=race.getLabel();
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_RACE_ATTR,XmlWriter.CDATA,cRace);
    }
    // Sex
    CharacterSex sex=character.getCharacterSex();
    if (sex!=null)
    {
      String sexKey=sex.getKey();
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_SEX_ATTR,XmlWriter.CDATA,sexKey);
    }
    // Region
    String region=character.getRegion();
    if ((region!=null) && (region.length()>0))
    {
      characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_REGION_ATTR,XmlWriter.CDATA,region);
    }
    // Level
    int level=character.getLevel();
    characterAttrs.addAttribute("","",CharacterXMLConstants.CHARACTER_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level));
  }
}
