package delta.games.lotro.common.stats;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Utility methods for stats.
 * @author DAM
 */
public class StatUtils
{
  /**
   * Special value for "no description".
   */
  public static final String NO_DESCRIPTION="-";

  /**
   * Get a displayable string for a stat value.
   * @param value Value to display.
   * @param percentage Indicates if the stat is a percentage or not.
   * @return A displayable string.
   */
  public static String getStatDisplay(Number value, boolean percentage)
  {
    String valueStr;
    if (value!=null)
    {
      if (percentage)
      {
        valueStr=L10n.getString(value.doubleValue(),2)+"%";
      }
      else
      {
        float valueToUse=value.floatValue();
        if (Math.abs(valueToUse)<1.0)
        {
          valueStr=L10n.getString(valueToUse,1);
        }
        else
        {
          valueStr=L10n.getString(Math.round(valueToUse));
        }
      }
    }
    else
    {
      valueStr="-";
    }
    return valueStr;
  }

  /**
   * Get the display lines for some stats.
   * @param stats Stats to display.
   * @return A possibly empty but not <code>null</code> array of stat lines.
   */
  public static String[] getStatsDisplayLines(BasicStatsSet stats)
  {
    List<String> lines=getStatsDisplayLinesAsList(stats);
    lines=handleNewLines(lines);
    String[] ret=lines.toArray(new String[lines.size()]);
    return ret;
  }

  /**
   * Get the display lines for some stats.
   * @param stats Stats to display.
   * @return A possibly empty but not <code>null</code> array of stat lines.
   */
  public static List<String> getStatsDisplayLinesAsList(BasicStatsSet stats)
  {
    List<String> lines=new ArrayList<String>();
    for(StatsSetElement element : stats.getStatElements())
    {
      StatDescription stat=element.getStat();
      if (!stat.isVisible())
      {
        continue;
      }
      String line=getStatDisplay(element);
      if (line==null)
      {
        continue;
      }
      lines.add(line);
    }
    return lines;
  }

  /**
   * Gracefully handle new lines.
   * @param lines Input lines.
   * @return a list of lines, with no new line.
   */
  public static List<String> handleNewLines(List<String> lines)
  {
    List<String> ret=new ArrayList<String>();
    for(String line : lines)
    {
      while(true)
      {
        int hasLF=line.indexOf("\n");
        if (hasLF!=-1)
        {
          ret.add(line.substring(0,hasLF));
          line=line.substring(hasLF+1);
        }
        else
        {
          ret.add(line);
          break;
        }
      }
    }
    return ret;
  }

  /**
   * Get special effects.
   * @param provider Stats provider.
   * @return A list of special effect lines.
   */
  public static List<String> getSpecialEffects(StatsProvider provider)
  {
    List<String> lines=new ArrayList<String>();
    for(SpecialEffect specialEffect : provider.getSpecialEffects())
    {
      String label=specialEffect.getLabel();
      lines.add(label);
    }
    return lines;
  }

  /**
   * Get a full stats display (including special effects).
   * @param stats Stats to use.
   * @param provider Stats provider (for special effects).
   * @return A possibly empty but not <code>null</code> array of stat/effect lines.
   */
  public static String[] getFullStatsDisplay(BasicStatsSet stats, StatsProvider provider)
  {
    List<String> lines=getStatsDisplayLinesAsList(stats);
    if (provider!=null)
    {
      List<String> specialEffectsLines=getSpecialEffects(provider);
      lines.addAll(specialEffectsLines);
    }
    lines=handleNewLines(lines);
    String[] ret=lines.toArray(new String[lines.size()]);
    return ret;
  }

  /**
   * Get the display line for a single stat element.
   * @param element Stat element to display.
   * @return A displayable line or <code>null</code> if display is to be avoided.
   */
  public static String getStatDisplay(StatsSetElement element)
  {
    String line=null;
    StatDescription stat=element.getStat();
    String statName=stat.getName();
    Number value=element.getValue();
    String prefix="";
    boolean isPercentage=stat.isPercentage();
    if (element.getOperator()==StatOperator.MULTIPLY)
    {
      value=convertMultiplyToPercentage(value);
      if (value.floatValue()>0.0)
      {
        prefix="+";
      }
      isPercentage=true;
    }
    String valueStr=getStatDisplay(value,isPercentage);
    String descriptionOverride=element.getDescriptionOverride();
    if (descriptionOverride!=null)
    {
      if (!NO_DESCRIPTION.equals(descriptionOverride))
      {
        line=descriptionOverride;
        line=line.replace("{***}",valueStr);
        line=line.replace("${VALUE}",valueStr);
        line=line.replace("${PERCENTVALUE}",valueStr);
        line=line.replace("${PROPERTY}",stat.getName());
      }
    }
    else
    {
      line=prefix+valueStr+" "+statName;
    }
    if (line!=null)
    {
      line=line.replace(". ",".\n");
    }
    return line;
  }

  private static Number convertMultiplyToPercentage(Number input)
  {
    float value=input.floatValue();
    float percentage=(value-1)*100;
    if (Math.abs(percentage-1)<0.001)
    {
      return Integer.valueOf(0);
    }
    return Float.valueOf(percentage);
  }

  /**
   * Fix the value of a stat:
   * <ul>
   * <li>percentage stats are given in % values (i.e 1 becomes 100%).
   * <li>regen ?C?R stats are given in units/minutes (i.e x60).
   * </ul>
   * @param stat Targeted stat.
   * @param statValue Raw value.
   * @return the fixed value.
   */
  public static float fixStatValue(StatDescription stat, float statValue)
  {
    if (stat.isPercentage())
    {
      statValue=statValue*100;
    }
    if (LotroCoreConfig.isLive())
    {
      if ((stat==WellKnownStat.ICMR) || (stat==WellKnownStat.ICPR) || (stat==WellKnownStat.OCMR) || (stat==WellKnownStat.OCPR))
      {
        statValue=statValue*60;
      }
    }
    return statValue;
  }
}
