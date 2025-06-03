package delta.games.lotro.character.details.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.details.CharacterDetails;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.geo.io.xml.PositionXMLWriter;
import delta.games.lotro.common.money.Money;

/**
 * Writes LOTRO character details to XML files.
 * @author DAM
 */
public class CharacterDetailsXMLWriter
{
  /**
   * Write character details to a XML file.
   * @param outFile Output file.
   * @param details Details to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CharacterDetails details, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,details);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, CharacterDetails details) throws SAXException
  {
    AttributesImpl detailsAttrs=new AttributesImpl();

    // In-game time
    int inGameTime=details.getIngameTime();
    detailsAttrs.addAttribute("","",CharacterDetailsXMLConstants.IN_GAME_TIME_ATTR,XmlWriter.CDATA,String.valueOf(inGameTime));
    // Money
    Money money=details.getMoney();
    detailsAttrs.addAttribute("","",CharacterDetailsXMLConstants.MONEY_ATTR,XmlWriter.CDATA,String.valueOf(money.getInternalValue()));
    // Last logout date
    Long lastLogoutDate=details.getLastLogoutDate();
    if (lastLogoutDate!=null)
    {
      detailsAttrs.addAttribute("","",CharacterDetailsXMLConstants.LAST_LOGOUT_DATE_ATTR,XmlWriter.CDATA,lastLogoutDate.toString());
    }
    // No purchase required
    Boolean noPurchaseRequired=details.getNoPurchaseRequired();
    if (noPurchaseRequired!=null)
    {
      detailsAttrs.addAttribute("","",CharacterDetailsXMLConstants.NO_PURCHASE_REQUIRED_ATTR,XmlWriter.CDATA,noPurchaseRequired.toString());
    }
    hd.startElement("","",CharacterDetailsXMLConstants.CHARACTER_DETAILS_TAG,detailsAttrs);
    // XP
    AttributesImpl xpAttrs=new AttributesImpl();
    long xp=details.getXp();
    xpAttrs.addAttribute("","",CharacterDetailsXMLConstants.CURRENT_XP_ATTR,XmlWriter.CDATA,String.valueOf(xp));
    hd.startElement("","",CharacterDetailsXMLConstants.XP_TAG,xpAttrs);
    hd.endElement("","",CharacterDetailsXMLConstants.XP_TAG);
    // Current title
    Integer currentTitleId=details.getCurrentTitleId();
    if (currentTitleId!=null)
    {
      AttributesImpl titleAttrs=new AttributesImpl();
      titleAttrs.addAttribute("","",CharacterDetailsXMLConstants.CURRENT_TITLE_ID_ATTR,XmlWriter.CDATA,currentTitleId.toString());
      hd.startElement("","",CharacterDetailsXMLConstants.CURRENT_TITLE_TAG,titleAttrs);
      hd.endElement("","",CharacterDetailsXMLConstants.CURRENT_TITLE_TAG);
    }
    // Area
    Integer areaID=details.getAreaID();
    if (areaID!=null)
    {
      AttributesImpl areaAttrs=new AttributesImpl();
      areaAttrs.addAttribute("","",CharacterDetailsXMLConstants.CURRENT_AREA_ID_ATTR,XmlWriter.CDATA,areaID.toString());
      hd.startElement("","",CharacterDetailsXMLConstants.CURRENT_AREA_TAG,areaAttrs);
      hd.endElement("","",CharacterDetailsXMLConstants.CURRENT_AREA_TAG);
    }
    // Dungeon
    Integer dungeonID=details.getDungeonID();
    if (dungeonID!=null)
    {
      AttributesImpl dungeonAttrs=new AttributesImpl();
      dungeonAttrs.addAttribute("","",CharacterDetailsXMLConstants.CURRENT_DUNGEON_ID_ATTR,XmlWriter.CDATA,dungeonID.toString());
      hd.startElement("","",CharacterDetailsXMLConstants.CURRENT_DUNGEON_TAG,dungeonAttrs);
      hd.endElement("","",CharacterDetailsXMLConstants.CURRENT_DUNGEON_TAG);
    }
    // Position
    Position position=details.getPosition();
    PositionXMLWriter.writePosition(hd,position);
    // Current vocation
    Integer currentVocationId=details.getCurrentVocationId();
    if (currentVocationId!=null)
    {
      AttributesImpl vocationAttrs=new AttributesImpl();
      vocationAttrs.addAttribute("","",CharacterDetailsXMLConstants.CURRENT_VOCATION_ID_ATTR,XmlWriter.CDATA,currentVocationId.toString());
      hd.startElement("","",CharacterDetailsXMLConstants.CURRENT_VOCATION_TAG,vocationAttrs);
      hd.endElement("","",CharacterDetailsXMLConstants.CURRENT_VOCATION_TAG);
    }
    hd.endElement("","",CharacterDetailsXMLConstants.CHARACTER_DETAILS_TAG);
  }
}
