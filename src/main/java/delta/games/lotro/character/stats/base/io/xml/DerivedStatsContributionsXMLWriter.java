package delta.games.lotro.character.stats.base.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr;
import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr.ClassDerivedStats;
import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr.DerivedStatContribution;
import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr.StatContributions;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Writes derived stats contributions to XML files.
 * @author DAM
 */
public class DerivedStatsContributionsXMLWriter
{
  /**
   * Write a derived stats contributions manager to a XML file.
   * @param toFile File to write to.
   * @param statsManager Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final DerivedStatsContributionsMgr statsManager)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",DerivedStatsContributionsXMLConstants.DERIVATED_STATS_CONTRIBUTIONS_TAG,new AttributesImpl());
        for(CharacterClass characterClass : CharacterClass.ALL_CLASSES)
        {
          AttributesImpl classAttrs=new AttributesImpl();
          classAttrs.addAttribute("","",DerivedStatsContributionsXMLConstants.CLASS_CONTRIBS_CLASS_ATTR,CDATA,characterClass.getKey());
          hd.startElement("","",DerivedStatsContributionsXMLConstants.CLASS_CONTRIBS_TAG,classAttrs);
          ClassDerivedStats derivatedStats=statsManager.getDerivatedStats(characterClass);
          List<StatDescription> sourceStats=derivatedStats.getSourceStats();
          for(StatDescription sourceStat : sourceStats)
          {
            AttributesImpl sourceStatAttrs=new AttributesImpl();
            sourceStatAttrs.addAttribute("","",DerivedStatsContributionsXMLConstants.STAT_CONTRIBS_SOURCE_STAT_ATTR,CDATA,sourceStat.getPersistenceKey());
            hd.startElement("","",DerivedStatsContributionsXMLConstants.STAT_CONTRIBS_TAG,sourceStatAttrs);
            StatContributions contribs=derivatedStats.getContribsForStat(sourceStat);
            List<DerivedStatContribution> contributionFactors=contribs.getFactors();
            for(DerivedStatContribution contributionFactor : contributionFactors)
            {
              FixedDecimalsInteger factor=contributionFactor.getFactor();
              StatDescription targetStat=contributionFactor.getTargetStat();
              AttributesImpl contributionFactorAttrs=new AttributesImpl();
              contributionFactorAttrs.addAttribute("","",DerivedStatsContributionsXMLConstants.STAT_CONTRIB_TARGET_STAT_ATTR,CDATA,targetStat.getPersistenceKey());
              contributionFactorAttrs.addAttribute("","",DerivedStatsContributionsXMLConstants.STAT_CONTRIB_FACTOR_ATTR,CDATA,String.valueOf(factor.floatValue()));
              hd.startElement("","",DerivedStatsContributionsXMLConstants.STAT_CONTRIB_TAG,contributionFactorAttrs);
              hd.endElement("","",DerivedStatsContributionsXMLConstants.STAT_CONTRIB_TAG);
            }
            hd.endElement("","",DerivedStatsContributionsXMLConstants.STAT_CONTRIBS_TAG);
          }
          hd.endElement("","",DerivedStatsContributionsXMLConstants.CLASS_CONTRIBS_TAG);
        }
        hd.endElement("","",DerivedStatsContributionsXMLConstants.DERIVATED_STATS_CONTRIBUTIONS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }
}
