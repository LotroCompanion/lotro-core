package delta.games.lotro.character.stats.io.xml;

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
import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StarterStatsManager;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.utils.FixedDecimalsInteger;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Writes starter stats to XML files.
 * @author DAM
 */
public class StarterStatsWriter
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static final String CDATA="CDATA";
  
  /**
   * Write a starter stats to a XML file.
   * @param outFile Output file.
   * @param stats Stats to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, StarterStatsManager stats, String encoding)
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
      write(hd,stats);
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
  
  private void write(TransformerHandler hd, StarterStatsManager stats) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",StarterStatsXMLConstants.STARTER_STATS_TAG,attrs);
    for(Race race : Race.ALL_RACES)
    {
      AttributesImpl raceAttrs=new AttributesImpl();
      raceAttrs.addAttribute("","",StarterStatsXMLConstants.RACE_NAME_ATTR,CDATA,race.getKey());
      hd.startElement("","",StarterStatsXMLConstants.RACE_TAG,raceAttrs);
      for(CharacterClass cClass : CharacterClass.ALL_CLASSES)
      {
        BasicStatsSet statsSet=stats.getStartingStats(race, cClass);
        if (statsSet!=null)
        {
          AttributesImpl classAttrs=new AttributesImpl();
          classAttrs.addAttribute("","",StarterStatsXMLConstants.CLASS_NAME_ATTR,CDATA,cClass.getKey());
          hd.startElement("","",StarterStatsXMLConstants.CLASS_TAG,classAttrs);
          for(STAT stat : STAT.values())
          {
            AttributesImpl statAttrs=new AttributesImpl();
            FixedDecimalsInteger value=statsSet.getStat(stat);
            if (value!=null)
            {
              String key=stat.getKey();
              statAttrs.addAttribute("","",StarterStatsXMLConstants.STAT_NAME_ATTR,CDATA,key);
              String valueStr=String.valueOf(value.getInternalValue());
              statAttrs.addAttribute("","",StarterStatsXMLConstants.STAT_VALUE_ATTR,CDATA,valueStr);
              hd.startElement("","",StarterStatsXMLConstants.STAT_TAG,statAttrs);
              hd.endElement("","",StarterStatsXMLConstants.STAT_TAG);
            }
          }
          hd.endElement("","",StarterStatsXMLConstants.CLASS_TAG);
        }
      }
      hd.endElement("","",StarterStatsXMLConstants.RACE_TAG);
    }
    hd.endElement("","",StarterStatsXMLConstants.STARTER_STATS_TAG);
  }
}
