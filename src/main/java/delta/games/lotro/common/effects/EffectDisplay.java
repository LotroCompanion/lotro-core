package delta.games.lotro.common.effects;

import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.maths.Progression;

/**
 * Display effects.
 * @author DAM
 */
public class EffectDisplay
{
  private int _level;
  private boolean _durationDisplayed;

  /**
   * Constructor.
   * @param level Level to use for computations.
   */
  public EffectDisplay(int level)
  {
    _level=level;
    _durationDisplayed=false;
  }

  /**
   * Display an effect.
   * @param effect Effect to show.
   */
  public void displayEffect(Effect2 effect)
  {
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      System.out.println(description);
    }
    for(EffectAspect aspect : effect.getAspects())
    {
      displayAspect(effect,aspect);
    }
    if (!_durationDisplayed)
    {
      EffectDuration effectDuration=effect.getDuration();
      if (effectDuration!=null)
      {
        Float duration=effectDuration.getDuration();
        if (duration!=null)
        {
          String durationStr=Duration.getDurationString(duration.intValue());
          System.out.println("Duration: "+durationStr);
          _durationDisplayed=true;
        }
      }
    }
  }

  private void displayAspect(Effect2 effect, EffectAspect aspect)
  {
    if (aspect instanceof InstantVitalEffect)
    {
      showInstantVitalEffect((InstantVitalEffect)aspect);
    }
    else if (aspect instanceof StatsEffect)
    {
      showStatsEffect((StatsEffect)aspect);
    }
    else if (aspect instanceof ReactiveVitalEffect)
    {
      showReactiveVitalEffect((ReactiveVitalEffect)aspect);
    }
    else if (aspect instanceof InstantFellowshipEffect)
    {
      showInstantFellowshipEffect((InstantFellowshipEffect)aspect);
    }
    else if (aspect instanceof GenesisEffect)
    {
      showGenesisEffect((GenesisEffect)aspect);
    }
    else if (aspect instanceof VitalOverTimeEffect)
    {
      showVitalOverTimeEffect(effect,(VitalOverTimeEffect)aspect);
    }
    else if (aspect instanceof InduceCombatStateEffect)
    {
      showInduceCombatStateEffect((InduceCombatStateEffect)aspect);
    }
    else if (aspect instanceof DispelByResistEffect)
    {
      showDispelByResistEffect((DispelByResistEffect)aspect);
    }
  }

  private void showInstantVitalEffect(InstantVitalEffect effect)
  {
    StatDescription stat=effect.getStat();
    VitalChangeDescription description=effect.getInstantChangeDescription();
    if (description==null)
    {
      return;
    }
    Float value=getValue(description);
    if (value!=null)
    {
      float maxValueFloat=value.floatValue();
      int maxValue=(int)maxValueFloat;
      Float variance=description.getVariance();
      if (variance!=null)
      {
        float minValueFloat=maxValueFloat*(1-variance.floatValue());
        int minValue=(int)minValueFloat;
        System.out.print(minValue+" - "+maxValue);
      }
      else
      {
        System.out.print(maxValue);
      }
    }
    else
    {
      Float minValue=description.getMinValue();
      Float maxValue=description.getMaxValue();
      System.out.println((int)minValue.floatValue()+" - "+(int)maxValue.floatValue());
    }
    System.out.println(" "+stat.getName());
  }

  private Float getValue(AbstractVitalChange change)
  {
    if (change==null)
    {
      return null;
    }
    Float value=change.getConstant();
    if (value!=null)
    {
      return value;
    }
    Progression progression=change.getProgression();
    if (progression!=null)
    {
      value=progression.getValue(_level);
    }
    return value;
  }

  private void showStatsEffect(StatsEffect effect)
  {
    StatsProvider provider=effect.getStatsProvider();
    BasicStatsSet stats=provider.getStats(1,_level);
    String[] lines=StatUtils.getStatsDisplayLines(stats);
    for(String line : lines)
    {
      System.out.println(line);
    }
  }

  private void showReactiveVitalEffect(ReactiveVitalEffect aspect)
  {
    // Defender
    ReactiveVitalChange defender=aspect.getDefenderVitalChange();
    if (defender!=null)
    {
      Float value=getValue(defender);
      List<DamageType> damageTypes=aspect.getDamageTypes();
      float probability=defender.getProbability();
      boolean multiplicative=defender.isMultiplicative();
      int percentage=(int)(probability*100);
      System.out.println("On any "+damageTypes+" damage:");
      if (!multiplicative)
      {
        int damage=(int)value.floatValue();
        if (percentage!=100)
        {
          System.out.println(percentage+"% chance to Negate "+damage+" damage");
        }
        else
        {
          // Never?
          System.out.println("Negate "+damage+" damage");
        }
      }
      else
      {
        int percentageDamage=(int)(value.floatValue()*100);
        if (percentage!=100)
        {
          // Never?
          System.out.println(percentage+"% chance to Negate "+percentageDamage+"% damage");
        }
        else
        {
          // Negate 100% damage
          System.out.println("Negate "+percentageDamage+"% damage");
        }
      }
    }
    EffectAndProbability defenderEffect=aspect.getDefenderEffect();
    if (defenderEffect!=null)
    {
      List<DamageType> damageTypes=aspect.getDamageTypes();
      // TODO Handle case where damageTypes is [ALL]
      System.out.println("On any "+damageTypes+" damage:");
      float probability=defenderEffect.getProbability();
      int percentage=(int)(probability*100);
      Effect2 effect=defenderEffect.getEffect();
      System.out.println(percentage+"% chance to Receive effect:");
      displayEffect(effect);
    }
    // Attacker
    ReactiveVitalChange attacker=aspect.getAttackerVitalChange();
    if (attacker!=null)
    {
      Float value=getValue(attacker);
      List<DamageType> damageTypes=aspect.getDamageTypes();
      // TODO Handle case where damageTypes is [ALL]
      System.out.println("On any "+damageTypes+" damage:");
      float probability=attacker.getProbability();
      int percentage=(int)(probability*100);
      boolean multiplicative=attacker.isMultiplicative();
      if (!multiplicative)
      {
        int damage=Math.round(Math.abs(value.floatValue()));
        if (percentage!=100)
        {
          System.out.println(percentage+"% chance to Reflect "+damage+" damage");
        }
        else
        {
          System.out.println("Reflect "+damage+" damage");
        }
      }
      else
      {
        // Never multiplicative?
        int percentageDamage=(int)(value.floatValue()*100);
        if (percentage!=100)
        {
          System.out.println(percentage+"% chance to Reflect "+percentageDamage+"% damage");
        }
        else
        {
          System.out.println("Reflect "+percentageDamage+"% damage");
        }
      }
    }
    EffectAndProbability attackerEffect=aspect.getAttackerEffect();
    if (attackerEffect!=null)
    {
      List<DamageType> damageTypes=aspect.getDamageTypes();
      // TODO Handle case where damageTypes is [ALL]
      System.out.println("On any "+damageTypes+" damage:");
      float probability=attackerEffect.getProbability();
      int percentage=(int)(probability*100);
      Effect2 effect=attackerEffect.getEffect();
      System.out.println(percentage+"% chance to Reflect effect:");
      displayEffect(effect);
    }
  }

  private void showInstantFellowshipEffect(InstantFellowshipEffect aspect)
  {
    Float range=aspect.getRange();
    List<EffectGenerator> effects=aspect.getEffects();
    //boolean appliesToTarget=aspect.appliesToTarget();
    int rangeInt=(int)range.floatValue();
    System.out.println("Effects applied to the Fellowship within "+rangeInt+" metres:");
    showEffectGenerators(effects);
  }

  private void showEffectGenerators(List<EffectGenerator> effects)
  {
    for(EffectGenerator effectGenerator : effects)
    {
      showEffectGenerator(effectGenerator);
    }
  }

  private void showEffectGenerator(EffectGenerator effectGenerator)
  {
    Effect2 childEffect=effectGenerator.getEffect();
    Float spellcraft=effectGenerator.getSpellcraft();
    int levelBackup=_level;
    if (spellcraft!=null)
    {
      _level=spellcraft.intValue();
    }
    displayEffect(childEffect);
    _level=levelBackup;
  }

  private void showGenesisEffect(GenesisEffect aspect)
  {
    boolean permanent=aspect.isPermanent();
    if (permanent)
    {
      System.out.println("Permanent");
    }
    else
    {
      float duration=aspect.getDuration();
      String durationStr=Duration.getDurationString((int)duration);
      System.out.println("Duration: "+durationStr);
    }
    _durationDisplayed=true;
    Hotspot hotspot=aspect.getHotspot();
    if (hotspot!=null)
    {
      showEffectGenerators(hotspot.getEffects());
    }
    // TODO Interactable
  }

  private void showVitalOverTimeEffect(Effect2 effect, VitalOverTimeEffect aspect)
  {
    VitalChangeDescription initialChange=aspect.getInitialChangeDescription();
    Float initialValue=getValue(initialChange);
    VitalChangeDescription overTimeChange=aspect.getOverTimeChangeDescription();
    Float overTimeValue=getValue(overTimeChange);
    StatDescription stat=aspect.getStat();
    EffectDuration duration=effect.getDuration();
    int pulseCount=duration.getPulseCount();
    Float interval=duration.getDuration();
    float totalDuration=interval.floatValue()*pulseCount;
    String overtime=" every "+interval+" seconds for "+(int)totalDuration+" seconds.";
    DamageType damageType=aspect.getDamageType();
    if (damageType!=null)
    {
      // Damage computation is wrong now (values do depend on character stats=>tactical mastery % ?)
      if (initialValue!=null)
      {
        System.out.println(initialValue+" "+damageType.getLabel()+" Damage initially.");
      }
      System.out.println(overTimeValue+" "+damageType.getLabel()+" Damage"+overtime);
    }
    else
    {
      // Heal values do not seem to be changed by Incoming Healing or Outgoing Healing!
      if (initialValue!=null)
      {
        System.out.println("Restores "+initialValue+" "+stat.getName()+" initially.");
      }
      System.out.println("Restores "+overTimeValue+" "+stat.getName()+overtime);
    }
    _durationDisplayed=true;
  }

  private void showInduceCombatStateEffect(InduceCombatStateEffect aspect)
  {
    float duration=aspect.getDuration();
    LinearFunction durationFunction=aspect.getDurationFunction();
    if (durationFunction!=null)
    {
      Float computedDuration=durationFunction.getValue(_level);
      if (computedDuration!=null)
      {
        duration=computedDuration.floatValue();
      }
    }
    CombatState state=aspect.getCombatState();
    String stateStr=getStateLabel(state);
    System.out.println(duration+"s "+stateStr);
  }

  private String getStateLabel(CombatState state)
  {
    int code=state.getCode();
    if (code==2) return "Fear";
    if (code==3) return "Daze";
    if ((code==9) || (code==15)) return "Stun";
    if (code==10) return "Root";
    System.out.println("Unmanaged state: "+state.getLabel());
    return state.getLabel();
  }

  private void showDispelByResistEffect(DispelByResistEffect aspect)
  {
    int count=aspect.getMaxDispelCount();
    List<ResistCategory> categories=aspect.getResistCategories();
    boolean useStrengthRestriction=aspect.useStrengthRestriction();
    String effects=((count<0) || (count>1))?"effects":"effect"; 
    String label="Removes "+((count<0)?"all":"up to "+count)+" "+categories+" "+effects;
    if (useStrengthRestriction)
    {
      // TODO Use raw spellcraft if any
      Integer strengthOffset=aspect.getStrengthOffset();
      int delta=(strengthOffset!=null)?strengthOffset.intValue():4;
      int strength=_level+delta;
      String complement=" with a maximum strength of "+strength;
      label=label+complement;
    }
    System.out.println(label);
  }
}
