package delta.games.lotro.common.stats;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.l10n.L10n;
import delta.common.utils.text.TextTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;

/**
 * Utility methods for stats.
 * @author DAM
 */
public class StatUtils
{
  private static final String PERCENTVALUE="${PERCENTVALUE}";
  /**
   * Special value for "no description".
   */
  public static final String NO_DESCRIPTION="-";

  /**
   * Get a displayable string for a stat value.
   * @param value Value to display.
   * @param stat Stat to use.
   * @return A displayable string.
   */
  public static String getStatDisplay(Number value, StatDescription stat)
  {
    if (value==null)
    {
      return "-";
    }
    value=fixValueForDisplay(value,stat);
    return getStatDisplayRegular(value,stat);
  }

  private static Number fixValueForDisplay(Number value, StatDescription stat)
  {
    if (isRegenStat(stat))
    {
      value=Float.valueOf(value.floatValue()/60);
    }
    return value;
  }

  private static boolean isRegenStat(StatDescription stat)
  {
    if (stat==WellKnownStat.ICMR) return true;
    if (stat==WellKnownStat.ICPR) return true;
    if (stat==WellKnownStat.OCMR) return true;
    if (stat==WellKnownStat.OCPR) return true;
    return false;
  }

  private static String getStatDisplayRegular(Number value, StatDescription stat)
  {
    boolean percentage=stat.isPercentage();
    String valueStr;
    float valueToUse=value.floatValue();
    int digitsBelow1=0;
    int digitsAbove1=0;
    if (stat instanceof FloatStatDescription)
    {
      FloatStatDescription floatStat=(FloatStatDescription)stat;
      digitsBelow1=floatStat.getMaxDigitsBelow1();
      digitsAbove1=floatStat.getMaxDigitsAbove1();
    }
    if ((percentage) && (digitsAbove1<2))
    {
      digitsAbove1=2;
    }
    if (Math.abs(valueToUse)<1.0)
    {
      valueStr=L10n.getString(valueToUse,digitsBelow1);
    }
    else
    {
      if (digitsAbove1==0)
      {
        valueStr=L10n.getString(Math.round(valueToUse));
      }
      else
      {
        valueStr=L10n.getString(valueToUse,digitsAbove1);
      }
    }
    if (percentage)
    {
      valueStr=valueStr+"%";
    }
    return valueStr;
  }

  /**
   * Get the display lines for some stats.
   * @param stats Stats to display.
   * @return A possibly empty but not <code>null</code> array of stat lines.
   */
  public static List<String> getStatsForDisplay(BasicStatsSet stats)
  {
    List<String> lines=unsafeGetStatsForDisplay(stats);
    lines=TextTools.handleNewLines(lines);
    return lines;
  }

  /**
   * Get the display lines for some stats.
   * @param stats Stats to display.
   * @return A possibly empty but not <code>null</code> array of stat lines.
   */
  private static List<String> unsafeGetStatsForDisplay(BasicStatsSet stats)
  {
    List<String> lines=new ArrayList<String>();
    for(StatsSetElement element : stats.getStatElements())
    {
      if (!shallShow(element))
      {
        continue;
      }
      String line=getStatDisplay(element);
      if ((line==null) || (line.isEmpty()))
      {
        continue;
      }
      lines.add(line);
    }
    return lines;
  }

  private static boolean shallShow(StatsSetElement element)
  {
    String descriptionOverride=element.getDescriptionOverride();
    if ((descriptionOverride!=null) && (!NO_DESCRIPTION.equals(descriptionOverride)))
    {
      return true;
    }
    StatDescription stat=element.getStat();
    return stat.isVisible();
  }

  /**
   * Get special effects.
   * @param provider Stats provider.
   * @return A list of special effect lines.
   */
  private static List<String> getSpecialEffects(StatsProvider provider)
  {
    List<String> lines=new ArrayList<String>();
    for(SpecialEffect specialEffect : provider.getSpecialEffects())
    {
      String label=specialEffect.getLabel();
      if ((label!=null) && (!label.isEmpty()))
      {
        lines.add(label);
      }
    }
    return lines;
  }

  /**
   * Get a full stats display (including special effects).
   * @param statsProvider Stats provider (for special effects).
   * @param level Level to use for computations.
   * @return A possibly empty but not <code>null</code> list of stat/effect lines.
   */
  public static List<String> getFullStatsForDisplay(StatsProvider statsProvider, int level)
  {
    return getFullStatsForDisplay(statsProvider,new SimpleStatComputerContext(1,level));
  }

  /**
   * Get a displayable view of stats.
   * @param statsProvider Stats provider.
   * @param context Stat computing context.
   * @return A list of displayable strings.
   */
  public static List<String> getFullStatsForDisplay(StatsProvider statsProvider, StatComputerContext context)
  {
    List<String> lines=new ArrayList<String>();
    int nbEntries=statsProvider.getEntriesCount();
    for(int i=0;i<nbEntries;i++)
    {
      StatsProviderEntry entry=statsProvider.getEntry(i);
      if (entry instanceof StatProvider)
      {
        StatProvider provider=(StatProvider)entry;
        StatsSetElement element=statsProvider.getStat(provider,context);
        if (element!=null)
        {
          String statDisplay=getStatDisplay(element);
          if ((statDisplay!=null) && (!statDisplay.isEmpty()))
          {
            lines.add(statDisplay);
          }
        }
      }
      else if (entry instanceof SpecialEffect)
      {
        SpecialEffect specialEffect=(SpecialEffect)entry;
        String line=specialEffect.getLabel();
        if ((line!=null) && (!line.isEmpty()))
        {
          lines.add(line);
        }
      }
    }
    lines=TextTools.handleNewLines(lines);
    return lines;
  }

  /**
   * Get a full stats display (including special effects).
   * @param stats Stats to use.
   * @param provider Stats provider (for special effects).
   * @return A possibly empty but not <code>null</code> list of stat/effect lines.
   */
  public static List<String> getFullStatsForDisplay(BasicStatsSet stats, StatsProvider provider)
  {
    List<String> lines=unsafeGetStatsForDisplay(stats);
    if (provider!=null)
    {
      List<String> specialEffects=getSpecialEffects(provider);
      lines.addAll(specialEffects);
    }
    lines=TextTools.handleNewLines(lines);
    return lines;
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
    if (element.getOperator()==StatOperator.MULTIPLY)
    {
      StatDescription newStat=new StatDescription();
      newStat.setIdentifier(stat.getIdentifier());
      stat=newStat;
      stat.setPercentage(true);
      value=convertMultiplyToPercentage(value);
      if (value.floatValue()>0.0)
      {
        prefix="+";
      }
    }
    if (element.getOperator()==StatOperator.SUBSTRACT)
    {
      prefix="-";
    }
    String valueStr=getStatDisplay(value,stat);
    String absPercentValue=valueStr;
    if (absPercentValue.startsWith("-"))
    {
      absPercentValue=absPercentValue.substring(1);
    }
    String descriptionOverride=element.getDescriptionOverride();
    if (descriptionOverride!=null)
    {
      if (!NO_DESCRIPTION.equals(descriptionOverride))
      {
        line=descriptionOverride;
        line=line.replace("{***}",valueStr);
        line=line.replace("${VALUE}",valueStr);
        line=line.replace("+${PERCENTVALUE}","+"+absPercentValue);
        line=line.replace("-${PERCENTVALUE}","-"+absPercentValue);
        int index=line.indexOf(PERCENTVALUE);
        if (index==0)
        {
          line=line.replace(PERCENTVALUE,valueStr);
        }
        else if (index>0)
        {
          line=line.replace(PERCENTVALUE,absPercentValue);
        }
        line=line.replace("${PROPERTY}",statName);
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
    if ((stat==WellKnownStat.ICMR) || (stat==WellKnownStat.ICPR) || (stat==WellKnownStat.OCMR) || (stat==WellKnownStat.OCPR))
    {
      statValue=statValue*60;
    }
    return statValue;
  }

  /**
   * Get the value of a stat.
   * @param level Level to use.
   * @param tier Tier to use.
   * @param tiersCount Tiers count.
   * @param provider Stat provider.
   * @return A value or <code>null</code>.
   */
  public static Float getStatValue(int level, int tier, int tiersCount, StatProvider provider)
  {
    Float value=null;
    if (provider instanceof TieredScalableStatProvider)
    {
      value=provider.getStatValue(tier,level);
    }
    else if (provider instanceof ScalableStatProvider)
    {
      ScalableStatProvider scalableStatProvider=(ScalableStatProvider)provider;
      if (tiersCount>1)
      {
        value=scalableStatProvider.getStatValue(1,tier);
      }
      else
      {
        value=scalableStatProvider.getStatValue(1,level);
      }
    }
    else
    {
      value=provider.getStatValue(1,level);
    }
    return value;
  }
}
