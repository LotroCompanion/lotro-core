package delta.games.lotro.lore.pvp.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.pvp.Rank;
import delta.games.lotro.lore.pvp.RankScale;
import delta.games.lotro.lore.pvp.RankScaleEntry;
import delta.games.lotro.lore.pvp.RanksManager;

/**
 * Writes PVP data to an XML file.
 * @author DAM
 */
public class PVPDataXMLWriter
{
  /**
   * Write a PVP data to an XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final RanksManager data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeData(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeData(TransformerHandler hd, RanksManager data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",PVPDataXMLConstants.PVP_TAG,attrs);
    for(String key : data.getKeys())
    {
      RankScale rankScale=data.getRankScale(key);
      writeRankScale(hd,rankScale);
    }
    hd.endElement("","",PVPDataXMLConstants.PVP_TAG);
  }

  private void writeRankScale(TransformerHandler hd, RankScale rankScale) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Key
    String key=rankScale.getKey();
    attrs.addAttribute("","",PVPDataXMLConstants.KEY_ATTR,XmlWriter.CDATA,key);
    hd.startElement("","",PVPDataXMLConstants.RANK_SCALE_TAG,attrs);
    for(RankScaleEntry entry : rankScale.getEntries())
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      // Rank
      Rank rank=entry.getRank();
      // - code
      int code=rank.getCode();
      entryAttrs.addAttribute("","",PVPDataXMLConstants.CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
      // - name
      String name=rank.getName();
      entryAttrs.addAttribute("","",PVPDataXMLConstants.LABEL_ATTR,XmlWriter.CDATA,name);
      // Min value
      int minValue=entry.getValue();
      entryAttrs.addAttribute("","",PVPDataXMLConstants.MIN_ATTR,XmlWriter.CDATA,String.valueOf(minValue));
      hd.startElement("","",PVPDataXMLConstants.RANK_SCALE_ENTRY_TAG,entryAttrs);
      hd.endElement("","",PVPDataXMLConstants.RANK_SCALE_ENTRY_TAG);
    }
    hd.endElement("","",PVPDataXMLConstants.RANK_SCALE_TAG);
  }
}
