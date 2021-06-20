package delta.games.lotro.character.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.CharacterDataSummary;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Writes LOTRO character summaries to XML files.
 * @author DAM
 */
public class CharacterSummaryXMLWriter
{
  /**
   * Write a character to a XML file.
   * @param outFile Output file.
   * @param summary Character to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeCharacterSummaryFile(File outFile, final CharacterSummary summary, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl characterAttrs=new AttributesImpl();
        writeCharacterSummary(characterAttrs,summary);
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
   * @param attrs Attributes to write to.
   * @param summary Source data.
   * @throws Exception If an error occurs.
   */
  public static void writeCharacterSummary(AttributesImpl attrs, CharacterSummary summary) throws Exception
  {
    writeBaseCharacterSummary(attrs,summary);
    // Region
    String region=summary.getRegion();
    if (region.length()>0)
    {
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_REGION_ATTR,XmlWriter.CDATA,region);
    }
    // Kinship ID
    InternalGameId kinshipID=summary.getKinshipID();
    if (kinshipID!=null)
    {
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_KINSHIP_ID_ATTR,XmlWriter.CDATA,kinshipID.asPersistedString());
    }
    // Import date
    Long importDate=summary.getImportDate();
    if (importDate!=null)
    {
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_IMPORT_DATE_ATTR,XmlWriter.CDATA,importDate.toString());
    }
  }

  /**
   * Write base character summary attributes.
   * @param attrs Attributes to write to.
   * @param summary Source data.
   * @throws Exception If an error occurs.
   */
  public static void writeBaseCharacterSummary(AttributesImpl attrs, BaseCharacterSummary summary) throws Exception
  {
    // ID
    InternalGameId id=summary.getId();
    if (id!=null)
    {
      String idStr=id.asPersistedString();
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_ID_ATTR,XmlWriter.CDATA,idStr);
    }
    // Name
    String name=summary.getName();
    if (name.length()>0)
    {
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Server
    String server=summary.getServer();
    if (server.length()>0)
    {
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_SERVER_ATTR,XmlWriter.CDATA,server);
    }
    // Account
    String accountName=summary.getAccountName();
    if (accountName.length()>0)
    {
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_ACCOUNT_ATTR,XmlWriter.CDATA,accountName);
    }
    // Character class
    CharacterClass characterClass=summary.getCharacterClass();
    if (characterClass!=null)
    {
      String cClass=characterClass.getKey();
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_CLASS_ATTR,XmlWriter.CDATA,cClass);
    }
    // Race
    Race race=summary.getRace();
    if (race!=null)
    {
      String cRace=race.getLabel();
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_RACE_ATTR,XmlWriter.CDATA,cRace);
    }
    // Sex
    CharacterSex sex=summary.getCharacterSex();
    if (sex!=null)
    {
      String sexKey=sex.getKey();
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_SEX_ATTR,XmlWriter.CDATA,sexKey);
    }
    // Level
    int level=summary.getLevel();
    attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level));
  }

  /**
   * Write character data summary attributes.
   * @param attrs Attributes to write to.
   * @param dataSummary Source data.
   * @throws Exception If an error occurs.
   */
  public static void writeDataSummary(AttributesImpl attrs, CharacterDataSummary dataSummary) throws Exception
  {
    // Name
    String name=dataSummary.getName();
    if (name.length()>0)
    {
      attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Level
    int level=dataSummary.getLevel();
    attrs.addAttribute("","",CharacterXMLConstants.CHARACTER_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level));
  }
}
