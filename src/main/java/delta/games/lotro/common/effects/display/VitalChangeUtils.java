package delta.games.lotro.common.effects.display;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;

/**
 * Utility methods related to vital changes.
 * @author DAM
 */
public class VitalChangeUtils
{
  /**
   * Build a full change line.
   * @param min Minimum value.
   * @param max Maximum value.
   * @param stat Involved stat.
   * @param damageType Damage type (may be <code>null</code>).
   * @return A displayable line.
   */
  public static String buildFullChange(boolean overtime, int min, int max, StatDescription stat, DamageType damageType)
  {
    boolean negative=false;
    if ((min<0) || (max<0))
    {
      negative=true;
      min=Math.abs(min);
      max=Math.abs(max);
    }
    String changeStr=buildChangeStr(min,max);
    String fullChange="";
    if (stat==WellKnownStat.MORALE)
    {
      if (negative)
      {
        String damageTypeStr="";
        if (damageType!=null)
        {
          damageTypeStr=" "+damageType.getLabel();
        }
        fullChange=changeStr+damageTypeStr+" Damage";
      }
      else
      {
        String prefix=(overtime?"Heals ":"+");
        fullChange=prefix+changeStr+" Morale";
      }
    }
    else if (stat==WellKnownStat.POWER)
    {
      if (negative)
      {
        fullChange="Drains "+changeStr+" Power";
      }
      else
      {
        String prefix=(overtime?"Restores ":"+");
        fullChange=prefix+changeStr+" Power";
      }
    }
    return fullChange;
  }

  private static String buildChangeStr(int min, int max)
  {
    String ret="";
    if (min==max)
    {
      ret=L10n.getString(min);
    }
    else
    {
      ret=L10n.getString(min)+" - "+L10n.getString(max);
    }
    return ret;
  }
}
