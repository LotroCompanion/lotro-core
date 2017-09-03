package delta.games.lotro.character.reputation.io.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.StreamTools;
import delta.games.lotro.character.reputation.FactionData;
import delta.games.lotro.character.reputation.ReputationData;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Writes reputation of LOTRO characters to XML files.
 * @author DAM
 */
public class ReputationXMLWriter
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static final String CDATA="CDATA";

  /**
   * Write the reputation of a character to a XML file.
   * @param outFile Output file.
   * @param reputation Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, ReputationData reputation, String encoding)
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
      write(hd,reputation);
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

  private void write(TransformerHandler hd, ReputationData reputation) throws Exception
  {
    AttributesImpl reputationAttrs=new AttributesImpl();
    hd.startElement("","",ReputationXMLConstants.REPUTATION_TAG,reputationAttrs);
    List<Faction> factions=FactionsRegistry.getInstance().getAll();
    for(Faction faction : factions)
    {
      FactionData factionData=reputation.getFactionStat(faction);
      if (factionData!=null)
      {
        FactionLevel currentLevel=factionData.getFactionLevel();
        AttributesImpl factionAttrs=new AttributesImpl();
        factionAttrs.addAttribute("","",ReputationXMLConstants.FACTION_KEY_ATTR,CDATA,faction.getKey());
        factionAttrs.addAttribute("","",ReputationXMLConstants.FACTION_CURRENT_ATTR,CDATA,currentLevel.getKey());
        hd.startElement("","",ReputationXMLConstants.FACTION_TAG,factionAttrs);
        FactionLevel[] levels=faction.getLevels();
        for(FactionLevel level : levels)
        {
          Long date=factionData.getDateForLevel(level);
          if (date!=null)
          {
            AttributesImpl factionLevelAttrs=new AttributesImpl();
            factionLevelAttrs.addAttribute("","",ReputationXMLConstants.FACTION_LEVEL_KEY_ATTR,CDATA,level.getKey());
            factionLevelAttrs.addAttribute("","",ReputationXMLConstants.FACTION_LEVEL_DATE_ATTR,CDATA,date.toString());
            hd.startElement("","",ReputationXMLConstants.FACTION_LEVEL_TAG,factionLevelAttrs);
            hd.endElement("","",ReputationXMLConstants.FACTION_LEVEL_TAG);
          }
        }
        hd.endElement("","",ReputationXMLConstants.FACTION_TAG);
      }
    }
    hd.endElement("","",ReputationXMLConstants.REPUTATION_TAG);
  }
}
