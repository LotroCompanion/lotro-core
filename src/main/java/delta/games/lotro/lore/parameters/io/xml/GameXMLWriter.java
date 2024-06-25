package delta.games.lotro.lore.parameters.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.parameters.GameParameters;

/**
 * Writes game data to XML files.
 * @author DAM
 */
public class GameXMLWriter
{
  /**
   * Write game data to a XML file.
   * @param toFile File to write to.
   * @param gameParameters Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final GameParameters gameParameters)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeGameParameters(hd,gameParameters);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeGameParameters(TransformerHandler hd, GameParameters gameParameters) throws SAXException
  {
    hd.startElement("","",GameXMLConstants.GAME_TAG,new AttributesImpl());
    AttributesImpl attrs=new AttributesImpl();
    // Max character level
    int maxCharacterlevel=gameParameters.getMaxCharacterLevel();
    attrs.addAttribute("","",GameXMLConstants.PARAM_MAX_CHARACTER_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(maxCharacterlevel));
    // Max legendary item level
    int maxLegendaryItemLevel=gameParameters.getMaxLegendaryItemLevel();
    attrs.addAttribute("","",GameXMLConstants.MAX_LEGENDARY_ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(maxLegendaryItemLevel));
    // Max virtue rank
    int maxVirtueRank=gameParameters.getMaxVirtueRank();
    attrs.addAttribute("","",GameXMLConstants.MAX_VIRTUE_RANK_ATTR,XmlWriter.CDATA,String.valueOf(maxVirtueRank));
    hd.startElement("","",GameXMLConstants.GAME_PARAMETERS_TAG,attrs);
    hd.endElement("","",GameXMLConstants.GAME_PARAMETERS_TAG);
    hd.endElement("","",GameXMLConstants.GAME_TAG);
  }
}
