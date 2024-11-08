package delta.games.lotro.common.effects.display;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.character.skills.attack.SkillAttackComputer;
import delta.games.lotro.common.effects.AbstractVitalChange;
import delta.games.lotro.common.effects.BaseVitalEffect;
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.effects.VitalOverTimeEffect;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatModifiersComputer;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.WeaponInstance;
import delta.games.lotro.utils.maths.Progression;

/**
 * Utility methods to display vital effects.
 * @author DAM
 */
public class VitalEffectsUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(VitalEffectsUtils.class); 

  private SkillAttackComputer _attackComputer;
  private StatModifiersComputer _statModsComputer;
  private EffectRenderingContext _context;

  /**
   * Constructor.
   * @param context Context to use.
   */
  public VitalEffectsUtils(EffectRenderingContext context)
  {
    _context=context;
    CharacterDataForSkills character=context.getCharacter();
    _attackComputer=new SkillAttackComputer(character);
    _statModsComputer=context.getStatModifiersComputer();
  }

  /**
   * Get a value for the given change.
   * @param change Change to use.
   * @return A value or <code>null</code>.
   */
  public Float getValue(AbstractVitalChange change)
  {
    Float value=change.getConstant();
    if (value!=null)
    {
      return value;
    }
    Progression progression=change.getProgression();
    if (progression!=null)
    {
      int level=_context.getLevel();
      value=progression.getValue(level);
    }
    return value;
  }

  /**
   * Get the min/max value for a vital change.
   * @param description Vital change description.
   * @return An array of min and max values.
   */
  public int[] getMinMaxValue(VitalChangeDescription description)
  {
    // TODO Use this
    Float minValueFloat=description.getMinValue();
    Float maxValueFloat=description.getMaxValue();
    if ((minValueFloat!=null) && (maxValueFloat!=null))
    {
      int minValue=(int)minValueFloat.floatValue();
      int maxValue=(int)maxValueFloat.floatValue();
      return new int[] {minValue, maxValue};
    }
    return new int[] {0,0};
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
   * Get min/max values for a vital effect change.
   * @param stat Involved stat.
   * @param effect Effect.
   * @param description Change description.
   * @param initial Initial change or not.
   * @return An array of 2 values (min and max).
   */
  public float[] getMinMaxValue(StatDescription stat, BaseVitalEffect effect, VitalChangeDescription description, boolean initial)
  {
    float maxChange=getMaxVitalChange(stat,effect,description,initial);
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
   * @param stat Stat to use.
   * @param effect Vital effect.
   * @param description Vital change to use.
   * @param initial Initial/instant change or not.
   * @return A vital change value.
   */
  private float getMaxVitalChange(StatDescription stat, BaseVitalEffect effect, VitalChangeDescription description, boolean initial)
  {
    float change=0;
    ImplementUsageType implementUsage=_context.getImplementUsage();
    DamageQualifier damageQualifier=_context.getDamageQualifier();
    float qualifierValue=getQualifierValue(implementUsage,stat,damageQualifier);
    float modifiers=0;
    if (_statModsComputer!=null)
    {
      modifiers=_statModsComputer.computeAdditiveModifiers(description.getModifiers());
    }
    float qualifierFactor=getQualifierFactor(modifiers,implementUsage,qualifierValue);
    LOGGER.debug("Qualifier factor: {}", Float.valueOf(qualifierFactor));

    // Progression
    Float value=getValue(description);
    if (value!=null)
    {
      change=value.floatValue();
      LOGGER.debug("Constant contribution: {}", value);
    }

    // Implement
    ItemInstance<?> item=_context.getImplement(implementUsage);
    if (implementUsage!=null)
    {
      float vps=implementContrib(implementUsage,item);
      LOGGER.debug("VPS implement contribution: {}", Float.valueOf(vps));

      Float vpsMultiplierValue=description.getVPSMultiplier();
      float vpsMultiplier=(vpsMultiplierValue!=null)?vpsMultiplierValue.floatValue():0;
      LOGGER.debug("VPS multiplier: {}", Float.valueOf(vpsMultiplier));

      if (!initial)
      {
        float duration=EffectDisplayUtils.getDuration(effect,_statModsComputer);
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
    else if (implementUsage==ImplementUsageTypes.PRIMARY)
    {
      if (damageQualifier!=null)
      {
        qualifierValue=_attackComputer.getDamageQualifier(damageQualifier);
        LOGGER.debug("Primary qualifier value: {}",Float.valueOf(qualifierValue));
      }
    }
    else if (implementUsage==ImplementUsageTypes.RANGED)
    {
      if (damageQualifier!=null)
      {
        qualifierValue=_attackComputer.getDamageQualifier(damageQualifier);
        LOGGER.debug("Ranged qualifier value: {}",Float.valueOf(qualifierValue));
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

  private DamageType getDamageType(BaseVitalEffect effect)
  {
    DamageType damageType=effect.getDamageType();
    if (damageType!=null)
    {
      return damageType;
    }
    ImplementUsageType implementUsage=_context.getImplementUsage();
    damageType=_attackComputer.getDamageType(implementUsage);
    return damageType;
  }

  /**
   * Get a string to display a vital over-time effect.
   * @param effect Effect.
   * @param storage Storage for generated output.
   */
  public void getVitalOverTimeEffectDisplay(VitalOverTimeEffect effect, List<String> storage)
  {
    StatDescription stat=effect.getStat();
    DamageType damageType=getDamageType(effect);
    String initialLine=null;
    VitalChangeDescription initialChange=effect.getInitialChangeDescription();
    if (initialChange!=null)
    {
      float[] initialMinMax=getMinMaxValue(stat,effect,initialChange,true);
      int initialMinInt=Math.round(initialMinMax[0]);
      int initialMaxInt=Math.round(initialMinMax[1]);
      initialLine=VitalChangeUtils.buildFullChange(initialMinInt,initialMaxInt,stat,damageType);
    }
    String overTimeLine=null;
    VitalChangeDescription overTimeChange=effect.getOverTimeChangeDescription();
    if (overTimeChange!=null)
    {
      float[] intervalMinMax=getMinMaxValue(stat,effect,overTimeChange,false);
      int intervalMinInt=Math.round(intervalMinMax[0]);
      int intervalMaxInt=Math.round(intervalMinMax[1]);

      float interval=EffectDisplayUtils.getDuration(effect,_statModsComputer);
      float totalDuration=EffectDisplayUtils.getTotalDuration(effect,_statModsComputer);
      overTimeLine=VitalChangeUtils.buildFullChange(intervalMinInt,intervalMaxInt,stat,damageType);
      overTimeLine=overTimeLine+" every "+L10n.getString(interval,1,1)+" seconds";
      int pulseCount=EffectDisplayUtils.getPulseCount(effect,_statModsComputer);
      if (pulseCount>1)
      {
        String totalDurationStr=L10n.getString(Math.round(totalDuration));
        overTimeLine=overTimeLine+" for "+totalDurationStr+" seconds";
      }
    }
    if (initialLine!=null)
    {
      if (overTimeLine!=null)
      {
        storage.add(initialLine+" initially.");
      }
      else
      {
        storage.add(initialLine+".");
      }
    }
    if (overTimeLine!=null)
    {
      storage.add(overTimeLine+".");
    }
  }
}
