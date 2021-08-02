package delta.games.lotro.character.status.titles.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.titles.TitleState;
import delta.games.lotro.character.status.titles.TitleStatus;
import delta.games.lotro.character.status.titles.TitlesStatusManager;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Writes a titles status to an XML file.
 * @author DAM
 */
public class TitlesStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final TitlesStatusManager status, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeStatus(hd,status);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a status to the given XML stream.
   * @param hd XML output stream.
   * @param statusMgr Status to write.
   * @throws Exception If an error occurs.
   */
  private void writeStatus(TransformerHandler hd, TitlesStatusManager statusMgr) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",TitlesStatusXMLConstants.TITLES_STATUS_TAG,attrs);

    List<TitleStatus> titleStatuses=statusMgr.getAll();

    for(TitleStatus titleStatus : titleStatuses)
    {
      TitleState state=titleStatus.getState();
      if (state!=TitleState.ACQUIRED)
      {
        continue;
      }
      AttributesImpl statusAttrs=new AttributesImpl();
      // ID
      int titleID=titleStatus.getTitleId();
      statusAttrs.addAttribute("","",TitlesStatusXMLConstants.TITLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(titleID));
      // Name
      TitleDescription title=titleStatus.getTitle();
      String name=title.getName();
      statusAttrs.addAttribute("","",TitlesStatusXMLConstants.TITLE_NAME_ATTR,XmlWriter.CDATA,name);
      // Acquisition date
      Long acquisitionDate=titleStatus.getAcquisitionDate();
      if (acquisitionDate!=null)
      {
        statusAttrs.addAttribute("","",TitlesStatusXMLConstants.ACQUISITION_DATE_ATTR,XmlWriter.CDATA,acquisitionDate.toString());
      }
      // Acquisition timestamp
      Double acquisitionTimestamp=titleStatus.getAcquisitionTimeStamp();
      if (acquisitionTimestamp!=null)
      {
        statusAttrs.addAttribute("","",TitlesStatusXMLConstants.ACQUISITION_TIMESTAMP_ATTR,XmlWriter.CDATA,acquisitionTimestamp.toString());
      }
      hd.startElement("","",TitlesStatusXMLConstants.TITLE,statusAttrs);
      hd.endElement("","",TitlesStatusXMLConstants.TITLE);
    }
    hd.endElement("","",TitlesStatusXMLConstants.TITLES_STATUS_TAG);
  }
}
