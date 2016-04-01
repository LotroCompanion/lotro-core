package delta.games.lotro.character.stats.virtues;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.NumericTools;
import delta.common.utils.files.TextFileReader;
import delta.common.utils.text.EncodingNames;
import delta.common.utils.text.TextUtils;
import delta.common.utils.url.URLTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Manager for contributions of virtues to player stats.
 * @author DAM
 */
public class VirtuesContributionsMgr
{
  private HashMap<VirtueId,VirtueContributionTable> _contribs;

  /**
   * Constructor.
   */
  public VirtuesContributionsMgr()
  {
    _contribs=new HashMap<VirtueId,VirtueContributionTable>();
    init2();
  }

  /**
   * Get the contribution for a given virtue and rank.
   * @param virtueId Virtue identifier.
   * @param rank Rank (starting at 1).
   * @return A stats set or <code>null</code> if not found.
   */
  public BasicStatsSet getContribution(VirtueId virtueId, int rank)
  {
    BasicStatsSet stats=null;
    VirtueContributionTable table=_contribs.get(virtueId);
    if (table!=null)
    {
      stats=table.getContrib(rank);
    }
    return stats;
  }

  private HashMap<String,FixedDecimalsInteger[][]> loadStats()
  {
    HashMap<String,FixedDecimalsInteger[][]> values=null;
    URL url=URLTools.getFromClassPath("virtues-values.txt",VirtuesContributionsMgr.class.getPackage());
    if (url!=null)
    {
      TextFileReader r=new TextFileReader(url, EncodingNames.ISO8859_1);
      List<String> lines=TextUtils.readAsLines(r);
      values=new HashMap<String,FixedDecimalsInteger[][]>();
      int index=0;
      for(int i=0;i<12;i++) {
        String statName=lines.get(index).trim();
        index++;
        FixedDecimalsInteger[][] valuesTable=new FixedDecimalsInteger[3][];
        for(int j=0;j<3;j++) {
          String line=lines.get(index);
          String[] items=line.split("\t");
          FixedDecimalsInteger[] valuesLines=new FixedDecimalsInteger[items.length];
          for(int k=0;k<items.length;k++) {
            Float value=NumericTools.parseFloat(items[k]);
            valuesLines[k]=new FixedDecimalsInteger(value.floatValue());
          }
          valuesTable[j]=valuesLines;
          index++;
        }
        values.put(statName,valuesTable);
      }
    }
    return values;
  }

  private void init2()
  {
    HashMap<String,FixedDecimalsInteger[][]> values=loadStats();
    URL url=URLTools.getFromClassPath("virtues-stats.txt",VirtuesContributionsMgr.class.getPackage());
    if (url!=null)
    {
      TextFileReader r=new TextFileReader(url, EncodingNames.ISO8859_1);
      List<String> lines=TextUtils.readAsLines(r);
      for(String line : lines)
      {
        VirtueContributionTable table=new VirtueContributionTable();
        String[] items=line.split("\t");
        VirtueId id=VirtueId.valueOf(items[0].toUpperCase());
        for(int i=1;i<items.length;i++)
        {
          String item=items[i];
          int index=item.indexOf('/');
          String statName,tableName;
          if (index!=-1)
          {
            tableName=item.substring(0,index);
            statName=item.substring(index+1);
          }
          else
          {
            tableName=item;
            statName=item;
          }
          FixedDecimalsInteger[][] valuesTable=values.get(tableName);
          FixedDecimalsInteger[] valuesLine=valuesTable[i-1];
          STAT stat=STAT.getByName(statName);
          table.addContrib(stat,valuesLine);
          _contribs.put(id,table);
        }
      }
    }
  }
}
