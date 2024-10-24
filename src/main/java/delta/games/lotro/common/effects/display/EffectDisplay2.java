package delta.games.lotro.common.effects.display;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.l10n.L10n;
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

  private float[] getVitalChange(ImplementUsageType implementUsage, StatDescription stat, BaseVitalEffect effect, VitalChangeDescription description, DamageQualifier damageQualifier, boolean initial)
  {
    float maxChange=getMaxVitalChange(implementUsage,stat,effect,description,damageQualifier,initial);
    float minChange=maxChange;
    Float variance=description.getVariance();
    if (variance!=null)
    {
      minChange*=(1-variance.floatValue());
    }
    return new float[] {minChange,maxChange};
  }

  /**
   * Compute a vital change.
   * @param implementUsage Implement usage.
   * @param stat Stat to use.
   * @param effect Vital effect.
   * @param description Vital change to use.
   * @param damageQualifier Damage qualifier.
   * @param initial Initial/instant change or not.
   * @return A vital change value.
   */
  private float getMaxVitalChange(ImplementUsageType implementUsage, StatDescription stat, BaseVitalEffect effect, VitalChangeDescription description, DamageQualifier damageQualifier, boolean initial)
  {
    float change=0;
    float qualifierValue=getQualifierValue(implementUsage,stat,damageQualifier);
    float modifiers=_character.computeAdditiveModifiers(description.getModifiers());
    float qualifierFactor=getQualifierFactor(modifiers,implementUsage,qualifierValue);
    LOGGER.debug("Qualifier factor: {}", Float.valueOf(qualifierFactor));

    // Progression
    Progression prog=description.getProgression();
    if (prog!=null)
    {
      Float progValueF=prog.getValue(_character.getLevel());
      float progValue=(progValueF!=null)?progValueF.floatValue():0;
      change=progValue;
      LOGGER.debug("Progression contribution: {}", Float.valueOf(progValue));
    }

    // Implement
    ItemInstance<?> item=_character.getImplement(implementUsage);
    if (implementUsage!=null)
    {
      float vps=implementContrib(implementUsage,item);
      LOGGER.debug("VPS implement contribution: {}", Float.valueOf(vps));

      Float vpsMultiplierValue=description.getVPSMultiplier();
      float vpsMultiplier=(vpsMultiplierValue!=null)?vpsMultiplierValue.floatValue():0;
      LOGGER.debug("VPS multiplier: {}", Float.valueOf(vpsMultiplier));

      if (!initial)
      {
        float duration=EffectDisplayUtils.getDuration(effect,_character);
        vps*=duration;
        LOGGER.debug("Interval implement contribution: {}", Float.valueOf(vps));
      }
      float implementContrib=vpsMultiplier*vps;
      LOGGER.debug("implementContrib=vpsMultiplier*vps");
      LOGGER.debug("{}={}*{}",Float.valueOf(implementContrib),Float.valueOf(vpsMultiplier),Float.valueOf(vps));
      LOGGER.debug("Implement contribution: {}", Float.valueOf(implementContrib));
      change+=implementContrib;
    }
    LOGGER.debug("Change: {}", Float.valueOf(change));
    change*=qualifierFactor;
    LOGGER.debug("Total change: {}", Float.valueOf(change));
    return change;
  }

  private float getQualifierValue(ImplementUsageType implementUsage, StatDescription stat, DamageQualifier damageQualifier)
  {
    float qualifierValue=1.0f;
    if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
    {
      if (stat==WellKnownStat.MORALE)
      {
        qualifierValue=_attackComputer.getHealingQualifier();
        LOGGER.debug("Tactical HPS qualifier value: {}",Float.valueOf(qualifierValue));
      }
    }
    else if (implementUsage==ImplementUsageTypes.TACTICAL_DPS)
    {
      if (damageQualifier!=null)
      {
        qualifierValue=_attackComputer.getDamageQualifier(damageQualifier);
        LOGGER.debug("Tactical DPS qualifier value: {}",Float.valueOf(qualifierValue));
      }
    }
    return qualifierValue;
  }

  private float getQualifierFactor(float modifiers, ImplementUsageType implementUsage, float qualifierValue)
  {
    float factor;
    if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
    {
      factor=(qualifierValue+modifiers);
    }
    else
    {
      factor=qualifierValue*(1+modifiers);
    }
    return factor;
  }

  /**
   * Get a string to display a vital effect.
   * @param implementUsage Implement usage.
   * @param effect Effect.
   * @param damageQualifier Damage qualifier.
   * @param storage Storage for generated output.
   */
  public void getVitalEffectDisplay(ImplementUsageType implementUsage, BaseVitalEffect effect, DamageQualifier damageQualifier, List<String> storage)
  {
    if (effect instanceof InstantVitalEffect)
    {
      InstantVitalEffect instantVitalEffect=(InstantVitalEffect)effect;
      String line=getInstantVitalEffectDisplay(implementUsage,instantVitalEffect,damageQualifier);
      storage.add(line);
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      VitalOverTimeEffect vitalOverTimeEffect=(VitalOverTimeEffect)effect;
      getVitalOverTimeEffectDisplay(implementUsage,vitalOverTimeEffect,damageQualifier,storage);
    }
  }

  private String getInstantVitalEffectDisplay(ImplementUsageType implementUsage, InstantVitalEffect effect, DamageQualifier damageQualifier)
  {
    VitalChangeDescription change=effect.getInstantChangeDescription();
    StatDescription stat=effect.getStat();
    float[] minMaxChange=getVitalChange(implementUsage,stat,effect,change,damageQualifier,true);
    int minInt=Math.round(minMaxChange[0]);
    int maxInt=Math.round(minMaxChange[1]);

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

  private void getVitalOverTimeEffectDisplay(ImplementUsageType implementUsage, VitalOverTimeEffect effect, DamageQualifier damageQualifier, List<String> storage)
  {
    StatDescription stat=effect.getStat();
    DamageType damageType=effect.getDamageType();
    String initialLine=null;
    VitalChangeDescription initialChange=effect.getInitialChangeDescription();
    if (initialChange!=null)
    {
      float[] initialMinMax=getVitalChange(implementUsage,stat,effect,initialChange,damageQualifier,true);
      int initialMinInt=Math.round(initialMinMax[0]);
      int initialMaxInt=Math.round(initialMinMax[1]);
      initialLine=buildFullChange(initialMinInt,initialMaxInt,stat,damageType);
    }
    String overTimeLine=null;
    VitalChangeDescription overTimeChange=effect.getOverTimeChangeDescription();
    if (overTimeChange!=null)
    {
      float[] intervalMinMax=getVitalChange(implementUsage,stat,effect,overTimeChange,damageQualifier,false);
      int intervalMinInt=Math.round(intervalMinMax[0]);
      int intervalMaxInt=Math.round(intervalMinMax[1]);

      EffectDuration duration=effect.getEffectDuration();
      int pulseCount=duration.getPulseCount();
      pulseCount+=_character.computeAdditiveModifiers(duration.getPulseCountModifiers());
      float interval=EffectDisplayUtils.getDuration(effect,_character);
      float totalDuration=interval*pulseCount;
      overTimeLine=buildFullChange(intervalMinInt,intervalMaxInt,stat,damageType);
      overTimeLine=overTimeLine+" every "+L10n.getString(interval,1,1)+" seconds";
      if (totalDuration>0)
      {
        overTimeLine=overTimeLine+" for "+L10n.getString(Math.round(totalDuration))+" seconds";
      }
    }
    if (initialLine!=null)
    {
      if (overTimeLine!=null)
      {
        storage.add(initialLine+" initially");
      }
      else
      {
        storage.add(initialLine);
      }
    }
    if (overTimeLine!=null)
    {
      storage.add(overTimeLine);
    }
  }
}
