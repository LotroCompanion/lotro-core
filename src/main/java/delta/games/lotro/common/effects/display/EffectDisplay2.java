package delta.games.lotro.common.effects.display;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.l10n.L10n;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.character.skills.attack.SkillAttackComputer;
import delta.games.lotro.common.effects.BaseVitalEffect;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.effects.InstantVitalEffect;
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.effects.VitalOverTimeEffect;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.WeaponInstance;
import delta.games.lotro.utils.maths.Progression;

/**
 * Utility methods to display effects.
 * @author DAM
 */
public class EffectDisplay2
{
  private static final Logger LOGGER=LoggerFactory.getLogger(EffectDisplay2.class); 

  private CharacterDataForSkills _character;
  private SkillAttackComputer _attackComputer;

  /**
   * Constructor.
   * @param character Character to use.
   */
  public EffectDisplay2(CharacterDataForSkills character)
  {
    _character=character;
    _attackComputer=new SkillAttackComputer(_character);
  }

  private float implementContrib(ImplementUsageType implementUsage, ItemInstance<?> item)
  {
    float vps=0;
    boolean noTHRClass=false; // TODO
    if (implementUsage==ImplementUsageTypes.TACTICAL_DPS)
    {
      if (item!=null)
      {
        StatDescription tacticalDPS=StatsRegistry.getInstance().getByKey("Combat_TacticalDPS_Modifier");
        Number dpsN=item.getStats().getStat(tacticalDPS);
        vps=(dpsN!=null)?-dpsN.floatValue():0;
        LOGGER.debug("Item Tactical DPS: {}",Float.valueOf(vps));
      }
      vps-=20; // Combat_Class_TacticalDPS
    }
    else if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
    {
      if (noTHRClass)
      {
        //nVPS = csm.CalcStat("CombatBaseTacHPSNoClass",aChar[CHAR_LEVEL]);
        vps=100; // TODO
      }
      else
      {
        if (item!=null)
        {
          StatDescription tacticalDPS=StatsRegistry.getInstance().getByKey("Combat_TacticalHPS_Modifier");
          Number hpsN=item.getStats().getStat(tacticalDPS);
          vps=(hpsN!=null)?hpsN.floatValue():0;
          LOGGER.debug("Item Tactical HPS: {}",Float.valueOf(vps));
        }
        vps+=5.659143f; // Combat_Class_TacticalHPS
      }
    }
    else
    {
      boolean isWeapon=(item instanceof WeaponInstance);
      if (isWeapon)
      {
        WeaponInstance<?> weapon=(WeaponInstance<?>)item;
        vps=-weapon.getEffectiveDPS();
        LOGGER.debug("Weapon DPS: {}",Float.valueOf(vps));
      }
    }
    return vps;
  }

  /**
   * Compute a vital change.
   * @param implementUsage Implement usage.
   * @param stat Stat to use.
   * @param effect Vital effect.
   * @param description Vital change to use.
   * @param damageQualifier Damage qualifier.
   * @param initial Initial/instant change or not.
   * @param minimum Compute the minimum change or maximum change.
   * @return A vital change value.
   */
  private float getVitalChange(ImplementUsageType implementUsage, StatDescription stat, BaseVitalEffect effect, VitalChangeDescription description, DamageQualifier damageQualifier, boolean initial, boolean minimum)
  {
    float qualifierValue=0;
    if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
    {
      if (stat==WellKnownStat.MORALE)
      {
        qualifierValue=_attackComputer.getHealingQualifier();
      }
      else
      {
        qualifierValue=1.0f;
      }
      LOGGER.debug("Tactical HPS qualifier value: {}",Float.valueOf(qualifierValue));
    }
    else if (implementUsage==ImplementUsageTypes.TACTICAL_DPS)
    {
      if (damageQualifier==null)
      {
        qualifierValue=1.0f;
      }
      else
      {
        qualifierValue=_attackComputer.getDamageQualifier(damageQualifier);
        LOGGER.debug("Tactical DPS qualifier value: {}",Float.valueOf(qualifierValue));
      }
    }
    else
    {
      qualifierValue=1.0f;
    }

    float change=0;
    float modifiers=_character.computeAdditiveModifiers(description.getModifiers());
    Progression prog=description.getProgression();
    if (prog!=null)
    {
      Float progValueF=prog.getValue(_character.getLevel());
      float progValue=(progValueF!=null)?progValueF.floatValue():0;
      if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
      {
        change=(qualifierValue+modifiers)*progValue;
      }
      else
      {
        change=qualifierValue*(1+modifiers)*progValue;
      }
    }
    LOGGER.debug("Base vital change: {}", Float.valueOf(change));
    Float vpsMultiplierValue=description.getVPSMultiplier();
    float vpsMultiplier=(vpsMultiplierValue!=null)?vpsMultiplierValue.floatValue():0;
    LOGGER.debug("VPS multiplier: {}", Float.valueOf(vpsMultiplier));

    ItemInstance<?> item=_character.getImplement(implementUsage);
    if (implementUsage!=null)
    {
      float vps=implementContrib(implementUsage,item);
      LOGGER.debug("VPS implement contribution: {}", Float.valueOf(vps));
      if (!initial)
      {
        Float duration=effect.getEffectDuration().getDuration();
        if (duration!=null)
        {
          vps*=duration.floatValue();
          LOGGER.debug("Interval implement contribution: {}", Float.valueOf(vps));
        }
      }
      if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
      {
        LOGGER.debug("Tactical HPS: change+=(qualifierValue+modifiers)*vpsMultiplier*vps");
        float changeContrib=(qualifierValue+modifiers)*vpsMultiplier*vps;
        LOGGER.debug("({}+{})*{}*{}={}",Float.valueOf(qualifierValue),Float.valueOf(modifiers),Float.valueOf(vpsMultiplier),Float.valueOf(vps),Float.valueOf(changeContrib));
        change+=changeContrib;
      }
      else
      {
        LOGGER.debug("change+=qualifierValue*(1+modifiers)*vpsMultiplier*vps");
        float changeContrib=qualifierValue*(1+modifiers)*vpsMultiplier*vps;
        LOGGER.debug("{}*(1+{})*{}*{}={}",Float.valueOf(qualifierValue),Float.valueOf(modifiers),Float.valueOf(vpsMultiplier),Float.valueOf(vps),Float.valueOf(changeContrib));
        change+=changeContrib;
      }
    }

    Float variance=description.getVariance();
    if ((variance!=null) && (minimum))
    {
      change*=(1-variance.floatValue());
    }
    return change;
  }

  /**
   * Get a string to display a vital effect.
   * @param implementUsage Implement usage.
   * @param effect Effect.
   * @param damageQualifier Damage qualifier.
   * @return A displayable string.
   */
  public String getVitalEffectDisplay(ImplementUsageType implementUsage, BaseVitalEffect effect, DamageQualifier damageQualifier)
  {
    if (effect instanceof InstantVitalEffect)
    {
      InstantVitalEffect instantVitalEffect=(InstantVitalEffect)effect;
      return getInstantVitalEffectDisplay(implementUsage,instantVitalEffect,damageQualifier);
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      VitalOverTimeEffect vitalOverTimeEffect=(VitalOverTimeEffect)effect;
      return getVitalOverTimeEffectDisplay(implementUsage,vitalOverTimeEffect,damageQualifier);
    }
    return "";
  }

  private String getInstantVitalEffectDisplay(ImplementUsageType implementUsage, InstantVitalEffect effect, DamageQualifier damageQualifier)
  {
    VitalChangeDescription change=effect.getInstantChangeDescription();
    StatDescription stat=effect.getStat();
    float min=getVitalChange(implementUsage,stat,effect,change,damageQualifier,true,true);
    int minInt=Math.round(min);
    float max=getVitalChange(implementUsage,stat,effect,change,damageQualifier,true,false);
    int maxInt=Math.round(max);

    DamageType damageType=effect.getDamageType();
    String ret=buildFullChange(minInt,maxInt,stat,damageType);
    return ret;
  }

  private static String buildFullChange(int min, int max, StatDescription stat, DamageType damageType)
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
        fullChange="Heals "+changeStr+" Morale";
      }
    }
    else if (stat==WellKnownStat.POWER)
    {
      fullChange=(negative?"Drains ":"Restores ")+changeStr+" Power";
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

  private String getVitalOverTimeEffectDisplay(ImplementUsageType implementUsage, VitalOverTimeEffect effect, DamageQualifier damageQualifier)
  {
    StatDescription stat=effect.getStat();
    DamageType damageType=effect.getDamageType();
    String initialLine=null;
    VitalChangeDescription initialChange=effect.getInitialChangeDescription();
    if (initialChange!=null)
    {
      float initialMin=getVitalChange(implementUsage,stat,effect,initialChange,damageQualifier,true,true);
      int initialMinInt=Math.round(initialMin);
      float initialMax=getVitalChange(implementUsage,stat,effect,initialChange,damageQualifier,true,false);
      int initialMaxInt=Math.round(initialMax);
      initialLine=buildFullChange(initialMinInt,initialMaxInt,stat,damageType);
    }
    String overTimeLine=null;
    VitalChangeDescription overTimeChange=effect.getOverTimeChangeDescription();
    if (overTimeChange!=null)
    {
      float intervalMin=getVitalChange(implementUsage,stat,effect,overTimeChange,damageQualifier,false,true);
      int intervalMinInt=Math.round(intervalMin);
      float intervalMax=getVitalChange(implementUsage,stat,effect,overTimeChange,damageQualifier,false,false);
      int intervalMaxInt=Math.round(intervalMax);

      EffectDuration duration=effect.getEffectDuration();
      int pulseCount=duration.getPulseCount();
      pulseCount+=_character.computeAdditiveModifiers(duration.getPulseCountModifiers());
      Float interval=duration.getDuration();
      float totalDuration=interval.floatValue()*pulseCount;

      overTimeLine=buildFullChange(intervalMinInt,intervalMaxInt,stat,damageType);
      overTimeLine=overTimeLine+" every "+L10n.getString(interval.floatValue(),1)+" seconds";
      if (totalDuration>0)
      {
        overTimeLine=overTimeLine+" for "+L10n.getString(Math.round(totalDuration))+" seconds";
      }
    }
    if (initialLine!=null)
    {
      if (overTimeLine==null)
      {
        return initialLine;
      }
      return initialLine+" initially"+EndOfLine.NATIVE_EOL+overTimeLine;
    }
    return (overTimeLine!=null)?overTimeLine:"";
  }
}
