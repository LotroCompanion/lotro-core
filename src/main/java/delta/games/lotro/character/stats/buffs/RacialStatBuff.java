package delta.games.lotro.character.stats.buffs;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.NumericTools;
import delta.common.utils.files.TextFileReader;
import delta.common.utils.text.EncodingNames;
import delta.common.utils.text.TextUtils;
import delta.common.utils.url.URLTools;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Racial stat buff that gives a raw stat value that depends on the character level.
 * @author DAM
 */
public class RacialStatBuff extends AbstractBuffImpl
{
  private STAT _stat;
  private HashMap<Integer,Integer> _levelToStatValue;

  /**
   * Constructor.
   * @param stat Stat to use.
   */
  public RacialStatBuff(STAT stat)
  {
    _stat=stat;
    _levelToStatValue=new HashMap<Integer,Integer>();
    init();
  }

  private void init()
  {
    URL url=URLTools.getFromClassPath("racialStatsBuffs.txt",RacialStatBuff.class.getPackage());
    if (url!=null)
    {
      TextFileReader r=new TextFileReader(url, EncodingNames.ISO8859_1);
      List<String> lines=TextUtils.readAsLines(r);
      for(String line : lines)
      {
        String[] items=line.split("\t");
        if ((items!=null) && (items.length==2))
        {
          Integer level=NumericTools.parseInteger(items[0]);
          Integer value=NumericTools.parseInteger(items[1]);
          if ((level!=null) && (value!=null))
          {
            _levelToStatValue.put(level,value);
          }
        }
      }
    }
  }

  @Override
  public BasicStatsSet getStats(CharacterData character, BuffInstance buff)
  {
    BasicStatsSet ret=new BasicStatsSet();
    int level=character.getLevel();
    Integer value=_levelToStatValue.get(Integer.valueOf(level));
    if (value!=null)
    {
      ret.addStat(_stat,new FixedDecimalsInteger(value.intValue()));
    }
    return ret;
  }
}
