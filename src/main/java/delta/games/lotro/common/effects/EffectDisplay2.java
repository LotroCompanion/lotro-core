package delta.games.lotro.common.effects;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.character.skills.attack.SkillAttackComputer;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.DamageQualifiers;
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
 * @author dm
 */
public class EffectDisplay2
{
  private CharacterDataForSkills _character;
  private SkillAttackComputer _attackComputer;

  /**
   * Constructor.
   * @param character Character to use.
   * @param details Parent skill details.
   */
  public EffectDisplay2(CharacterDataForSkills character, SkillDetails details)
  {
    _character=character;
    _attackComputer=new SkillAttackComputer(_character,details);
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
        System.out.println("Weapon Tactical DPS: "+vps);
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
      }
    }
    return vps;
  }

  /**
   * Compute a vital change.
   * @param generator Parent effect generator.
   * @param effect Vital effect.
   * @param description Vital change to use.
   * @param damageQualifier Damage qualifier.
   * @param initial Initial/instant change or not.
   * @param minimum Compute the minimum change or maximum change.
   * @return A vital change value.
   */
  private float getVitalChange(SkillEffectGenerator generator, BaseVitalEffect effect, VitalChangeDescription description, DamageQualifier damageQualifier, boolean initial, boolean minimum)
  {
    float change=0;
    float qualifierValue=0;
    ImplementUsageType implementUsage=generator.getImplementUsage();
    if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
    {
      qualifierValue=_attackComputer.getHealingQualifier();
    }
    else if (implementUsage==ImplementUsageTypes.TACTICAL_DPS)
    {
      qualifierValue=_attackComputer.getDamageQualifier(DamageQualifiers.TACTICAL);
      System.out.println("Tactical DPS qualifier value: "+qualifierValue);
    }
    else
    {
      System.out.println("Unmanaged!");
    }

    // TODO Use damageQualifier. If not set, qualifierValue shall be 1?

    float modifiers=_character.computeAdditiveModifiers(description.getModifiers());
    Progression prog=description.getProgression();
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
    System.out.println("Change: "+change);
    Float vpsMultiplierValue=description.getVPSMultiplier();
    float vpsMultiplier=(vpsMultiplierValue!=null)?vpsMultiplierValue.floatValue():0;
    System.out.println("VPS multiplier: "+vpsMultiplier);

    ItemInstance<?> item=_character.getImplement(implementUsage);
    if (implementUsage!=null)
    {
      float vps=implementContrib(implementUsage,item);
      System.out.println("Implement contrib: "+vps);
      if (!initial)
      {
        Float duration=effect.getEffectDuration().getDuration();
        if (duration!=null)
        {
          vps*=duration.floatValue();
        }
      }
      if (implementUsage==ImplementUsageTypes.TACTICAL_HPS)
      {
        change+=(qualifierValue+modifiers)*vpsMultiplier*vps;
      }
      else
      {
        System.out.println("qualifierValue*(1+modifiers)*vpsMultiplier*vps");
        System.out.println(qualifierValue+"*"+(1+modifiers)+"*"+vpsMultiplier+"*"+vps);
        change+=qualifierValue*(1+modifiers)*vpsMultiplier*vps;
      }
    }

    Float variance=description.getVariance();
    if ((variance!=null) && (minimum))
    {
      change*=(1-variance.floatValue());
    }
    return change;
  }

  public String getVitalEffectDisplay(SkillEffectGenerator generator, BaseVitalEffect effect, DamageQualifier damageQualifier)
  {
    if (effect instanceof InstantVitalEffect)
    {
      InstantVitalEffect instantVitalEffect=(InstantVitalEffect)effect;
      return getInstantVitalEffectDisplay(generator,instantVitalEffect,damageQualifier);
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      VitalOverTimeEffect vitalOverTimeEffect=(VitalOverTimeEffect)effect;
      return getVitalOverTimeEffectDisplay(generator,vitalOverTimeEffect,damageQualifier);
    }
    return "";
  }

  private String getInstantVitalEffectDisplay(SkillEffectGenerator generator, InstantVitalEffect effect, DamageQualifier damageQualifier)
  {
    VitalChangeDescription change=effect.getInstantChangeDescription();
    float min=getVitalChange(generator,effect,change,damageQualifier,true,true);
    int minInt=Math.round(min);
    float max=getVitalChange(generator,effect,change,damageQualifier,true,false);
    int maxInt=Math.round(max);

    StatDescription stat=effect.getStat();
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
        fullChange="Heals "+changeStr;
      }
    }
    else if (stat==WellKnownStat.POWER)
    {
      fullChange=(negative?"Drains ":"Restores ")+changeStr;
    }
    return fullChange;
  }

  private static String buildChangeStr(int min, int max)
  {
    String ret="";
    if (min==max)
    {
      ret=String.valueOf(min);
    }
    else
    {
      ret=min+"-"+max;
    }
    return ret;
  }

  private String getVitalOverTimeEffectDisplay(SkillEffectGenerator generator, VitalOverTimeEffect effect, DamageQualifier damageQualifier)
  {
    StatDescription stat=effect.getStat();
    DamageType damageType=effect.getDamageType();
    String initialLine=null;
    VitalChangeDescription initialChange=effect.getInitialChangeDescription();
    if (initialChange!=null)
    {
      float initialMin=getVitalChange(generator,effect,initialChange,damageQualifier,true,true);
      int initialMinInt=Math.round(initialMin);
      float initialMax=getVitalChange(generator,effect,initialChange,damageQualifier,true,false);
      int initialMaxInt=Math.round(initialMax);
      initialLine=buildFullChange(initialMinInt,initialMaxInt,stat,damageType);
    }
    String overTimeLine=null;
    VitalChangeDescription overTimeChange=effect.getOverTimeChangeDescription();
    if (overTimeChange!=null)
    {
      float intervalMin=getVitalChange(generator,effect,overTimeChange,damageQualifier,false,true);
      int intervalMinInt=Math.round(intervalMin);
      float intervalMax=getVitalChange(generator,effect,overTimeChange,damageQualifier,false,false);
      int intervalMaxInt=Math.round(intervalMax);

      EffectDuration duration=effect.getEffectDuration();
      int pulseCount=duration.getPulseCount();
      pulseCount+=_character.computeAdditiveModifiers(duration.getPulseCountModifiers());
      Float interval=duration.getDuration();
      float totalDuration=interval.floatValue()*pulseCount;

      overTimeLine=buildFullChange(intervalMinInt,intervalMaxInt,stat,damageType);
      overTimeLine=overTimeLine+" every "+L10n.getString(interval.floatValue(),1)+" seconds for "+L10n.getString(Math.round(totalDuration))+" seconds";
    }
    if (initialLine!=null)
    {
      if (overTimeLine==null)
      {
        return initialLine;
      }
      return initialLine+" initially\n"+overTimeLine;
    }
    return (overTimeLine!=null)?overTimeLine:"";
  }
}
