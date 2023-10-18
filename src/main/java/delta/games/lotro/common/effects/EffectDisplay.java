package delta.games.lotro.common.effects;

import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.text.EndOfLine;
import delta.common.utils.variables.VariableValueProvider;
import delta.common.utils.variables.VariablesResolver;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillType;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.maths.Progression;

/**
 * Display effects.
 * @author DAM
 */
public class EffectDisplay
{
  private static final Logger LOGGER=Logger.getLogger(EffectDisplay.class);

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
   * @param sb Output stream.
   * @param effect Effect to show.
   */
  public void displayEffect(StringBuilder sb, Effect2 effect)
  {
    String descriptionOverride=effect.getDescriptionOverride();
    if (!descriptionOverride.isEmpty())
    {
      String text=resolveVariables(effect,descriptionOverride);
      sb.append(text).append(EndOfLine.NATIVE_EOL);
    }
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      sb.append(description).append(EndOfLine.NATIVE_EOL);
    }
    displaySpecifics(sb,effect);
    if (!_durationDisplayed)
    {
      EffectDuration effectDuration=effect.getEffectDuration();
      if (effectDuration!=null)
      {
        Float duration=effectDuration.getDuration();
        if (duration!=null)
        {
          String durationStr=Duration.getDurationString(duration.intValue());
          sb.append("Duration: ").append(durationStr).append(EndOfLine.NATIVE_EOL);
          _durationDisplayed=true;
        }
      }
    }
  }

  private String resolveVariables(Effect2 effect, String input)
  {
    VariableValueProvider provider=new VariableValueProvider()
    {
      @Override
      public String getVariable(String variableName)
      {
        return resolveVariable(effect,variableName);
      }
    };
    VariablesResolver resolver=new VariablesResolver(provider);
    return resolver.render(input);
  }

  private String resolveVariable(Effect2 effect, String variableName)
  {
    String ret=effect.resolveVariable(variableName);
    if (ret!=null)
    {
      return ret;
    }
    return variableName;
  }

  private void displaySpecifics(StringBuilder sb, Effect2 effect)
  {
    if (effect instanceof InstantVitalEffect)
    {
      showInstantVitalEffect(sb,(InstantVitalEffect)effect);
    }
    else if (effect instanceof ProcEffect)
    {
      showProcEffect(sb,(ProcEffect)effect);
    }
    else if (effect instanceof ReactiveVitalEffect)
    {
      showReactiveVitalEffect(sb,(ReactiveVitalEffect)effect);
    }
    else if (effect instanceof PropertyModificationEffect)
    {
      showPropertyModificationEffect(sb,(PropertyModificationEffect)effect);
    }
    else if (effect instanceof InstantFellowshipEffect)
    {
      showInstantFellowshipEffect(sb,(InstantFellowshipEffect)effect);
    }
    else if (effect instanceof GenesisEffect)
    {
      showGenesisEffect(sb,(GenesisEffect)effect);
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      showVitalOverTimeEffect(sb,(VitalOverTimeEffect)effect);
    }
    else if (effect instanceof InduceCombatStateEffect)
    {
      showInduceCombatStateEffect(sb,(InduceCombatStateEffect)effect);
    }
    else if (effect instanceof DispelByResistEffect)
    {
      showDispelByResistEffect(sb,(DispelByResistEffect)effect);
    }
  }

  private void showInstantVitalEffect(StringBuilder sb, InstantVitalEffect effect)
  {
    StatDescription stat=effect.getStat();
    VitalChangeDescription description=effect.getInstantChangeDescription();
    if (description==null)
    {
      return;
    }
    boolean isMorale=(stat==WellKnownStat.MORALE);
    boolean multiplicative=effect.isMultiplicative();
    if (!multiplicative)
    {
      int[] minMax=getMinMaxValue(description);
      if (minMax[0]==minMax[1])
      {
        if ((minMax[0]<0) && isMorale)
        {
          //sb.append("Apply to the target(s)").append(EndOfLine.NATIVE_EOL);
          sb.append(-minMax[0]).append(" Damage").append(EndOfLine.NATIVE_EOL);
        }
        else
        {
          sb.append(minMax[0]).append(' ');
          sb.append(stat.getName()).append(EndOfLine.NATIVE_EOL);
        }
      }
      else
      {
        if ((minMax[0]<0) && (minMax[1]<0) && isMorale)
        {
          //sb.append("Apply to the target(s)").append(EndOfLine.NATIVE_EOL);
          sb.append(-minMax[0]).append(" - ").append(-minMax[1]).append(" Damage").append(EndOfLine.NATIVE_EOL);
        }
        else
        {
          sb.append(minMax[0]).append(" - ").append(minMax[1]).append(' ');
          sb.append(stat.getName()).append(EndOfLine.NATIVE_EOL);
        }
      }
    }
    else
    {
      Float value=getValue(description);
      if (value!=null)
      {
        float maxPercentageFloat=value.floatValue()*100;
        int maxPercentage=(int)maxPercentageFloat;
        Float variance=description.getVariance();
        sb.append("Restores ");
        if (variance!=null)
        {
          float minPercentageFloat=maxPercentageFloat*(1-variance.floatValue());
          int minPercentage=(int)minPercentageFloat;
          sb.append(minPercentage).append("% - ").append(maxPercentage).append('%');
        }
        else
        {
          sb.append(maxPercentage).append('%');
        }
        sb.append(" of maximum ").append(stat.getName()).append(EndOfLine.NATIVE_EOL);
      }
    }
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
      if (value==null)
      {
        value=Float.valueOf(0);
      }
    }
    return value;
  }

  private int[] getMinMaxValue(VitalChangeDescription description)
  {
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
        return new int[] {minValue, maxValue};
      }
      return new int[] {maxValue, maxValue};
    }
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

  private void showPropertyModificationEffect(StringBuilder sb, PropertyModificationEffect effect)
  {
    StatsProvider provider=effect.getStatsProvider();
    if (provider==null)
    {
      return;
    }
    BasicStatsSet stats=provider.getStats(1,_level);
    String[] lines=StatUtils.getStatsDisplayLines(stats);
    for(String line : lines)
    {
      sb.append(line).append(EndOfLine.NATIVE_EOL);
    }
  }

  private void showReactiveVitalEffect(StringBuilder sb, ReactiveVitalEffect effect)
  {
    showPropertyModificationEffect(sb,effect);
    // Defender
    ReactiveChange defender=effect.getDefenderReactiveChange();
    if (defender!=null)
    {
      List<DamageType> damageTypes=effect.getDamageTypes();
      sb.append("On any ").append(damageTypes).append(" damage:").append(EndOfLine.NATIVE_EOL);
      ReactiveVitalChange change=defender.getVitalChange();
      if (change!=null)
      {
        Float value=getValue(change);
        float probability=change.getProbability();
        boolean multiplicative=change.isMultiplicative();
        int percentage=(int)(probability*100);
        if (!multiplicative)
        {
          int damage=(int)value.floatValue();
          if (percentage!=100)
          {
            sb.append(percentage).append("% chance to Negate ").append(damage).append(" damage").append(EndOfLine.NATIVE_EOL);
          }
          else
          {
            // Never?
            sb.append("Negate ").append(damage).append(" damage").append(EndOfLine.NATIVE_EOL);
          }
        }
        else
        {
          int percentageDamage=(int)(value.floatValue()*100);
          if (percentage!=100)
          {
            // Never?
            sb.append(percentage).append("% chance to Negate ").append(percentageDamage).append("% damage").append(EndOfLine.NATIVE_EOL);
          }
          else
          {
            // Negate X% damage
            sb.append("Negate ").append(percentageDamage).append("% damage").append(EndOfLine.NATIVE_EOL);
          }
        }
      }
      EffectAndProbability defenderEffect=defender.getEffect();
      if (defenderEffect!=null)
      {
        float effectProbability=defenderEffect.getProbability();
        int effectPercentage=(int)(effectProbability*100);
        sb.append(effectPercentage).append("% chance to Receive effect:").append(EndOfLine.NATIVE_EOL);
        displayEffect(sb,defenderEffect.getEffect());
      }
    }
    // Attacker
    ReactiveChange attacker=effect.getAttackerReactiveChange();
    if (attacker!=null)
    {
      List<DamageType> damageTypes=effect.getDamageTypes();
      sb.append("On any ").append(damageTypes).append(" damage:").append(EndOfLine.NATIVE_EOL);
      ReactiveVitalChange change=attacker.getVitalChange();
      if (change!=null)
      {
        Float value=getValue(change);
        // TODO Handle case where damageTypes is [ALL]
        float probability=change.getProbability();
        int percentage=(int)(probability*100);
        boolean multiplicative=change.isMultiplicative();
        if (!multiplicative)
        {
          int damage=Math.round(Math.abs(value.floatValue()));
          if (percentage!=100)
          {
            sb.append(percentage).append("% chance to Reflect ").append(damage).append(" damage").append(EndOfLine.NATIVE_EOL);
          }
          else
          {
            sb.append("Reflect ").append(damage).append(" damage").append(EndOfLine.NATIVE_EOL);
          }
        }
        else
        {
          // Never multiplicative?
          int percentageDamage=(int)(value.floatValue()*100);
          if (percentage!=100)
          {
            sb.append(percentage).append("% chance to Reflect ").append(percentageDamage).append("% damage").append(EndOfLine.NATIVE_EOL);
          }
          else
          {
            sb.append("Reflect ").append(percentageDamage).append("% damage").append(EndOfLine.NATIVE_EOL);
          }
        }
      }
      EffectAndProbability attackerEffect=attacker.getEffect();
      if (attackerEffect!=null)
      {
        float effectProbability=attackerEffect.getProbability();
        int effectPercentage=(int)(effectProbability*100);
        sb.append(effectPercentage).append("% chance to Reflect effect:").append(EndOfLine.NATIVE_EOL);
        displayEffect(sb,attackerEffect.getEffect());
      }
    }
  }

  private void showInstantFellowshipEffect(StringBuilder sb, InstantFellowshipEffect effect)
  {
    Float range=effect.getRange();
    List<EffectGenerator> effects=effect.getEffects();
    //boolean appliesToTarget=effect.appliesToTarget();
    int rangeInt=(int)range.floatValue();
    sb.append("Effects applied to the Fellowship within ").append(rangeInt).append(" metres:").append(EndOfLine.NATIVE_EOL);
    showEffectGenerators(sb,effects);
  }

  private void showEffectGenerators(StringBuilder sb, List<EffectGenerator> effects)
  {
    for(EffectGenerator effectGenerator : effects)
    {
      showEffectGenerator(sb,effectGenerator);
    }
  }

  private void showEffectGenerator(StringBuilder sb, EffectGenerator effectGenerator)
  {
    Effect2 childEffect=effectGenerator.getEffect();
    Float spellcraft=effectGenerator.getSpellcraft();
    int levelBackup=_level;
    if (spellcraft!=null)
    {
      _level=spellcraft.intValue();
    }
    displayEffect(sb,childEffect);
    _level=levelBackup;
  }

  private void showGenesisEffect(StringBuilder sb, GenesisEffect effect)
  {
    boolean permanent=effect.isPermanent();
    if (permanent)
    {
      sb.append("Permanent").append(EndOfLine.NATIVE_EOL);
    }
    else
    {
      float duration=effect.getSummonDuration();
      String durationStr=Duration.getDurationString((int)duration);
      sb.append("Duration: ").append(durationStr).append(EndOfLine.NATIVE_EOL);
    }
    _durationDisplayed=true;
    Hotspot hotspot=effect.getHotspot();
    if (hotspot!=null)
    {
      showEffectGenerators(sb,hotspot.getEffects());
    }
    // TODO Interactable
  }

  private void showVitalOverTimeEffect(StringBuilder sb, VitalOverTimeEffect effect)
  {
    VitalChangeDescription initialChange=effect.getInitialChangeDescription();
    Float initialValue=getValue(initialChange);
    VitalChangeDescription overTimeChange=effect.getOverTimeChangeDescription();
    Float overTimeValue=getValue(overTimeChange);
    StatDescription stat=effect.getStat();
    EffectDuration duration=effect.getEffectDuration();
    int pulseCount=duration.getPulseCount();
    Float interval=duration.getDuration();
    float totalDuration=interval.floatValue()*pulseCount;
    String overtime=" every "+interval+" seconds for "+(int)totalDuration+" seconds.";
    DamageType damageType=effect.getDamageType();
    if (damageType!=null)
    {
      // Damage computation is wrong now (values do depend on character stats=>tactical mastery % ?)
      if (initialValue!=null)
      {
        sb.append(initialValue).append(' ').append(damageType.getLabel());
        sb.append(" Damage initially.").append(EndOfLine.NATIVE_EOL);
      }
      sb.append(overTimeValue).append(' ').append(damageType.getLabel());
      sb.append(" Damage").append(overtime).append(EndOfLine.NATIVE_EOL);
    }
    else
    {
      // Heal values do not seem to be changed by Incoming Healing or Outgoing Healing!
      if (initialValue!=null)
      {
        sb.append("Restores ").append(initialValue).append(' ').append(stat.getName());
        sb.append(" initially.").append(EndOfLine.NATIVE_EOL);
      }
      sb.append("Restores ").append(overTimeValue).append(' ').append(stat.getName());
      sb.append(overtime).append(EndOfLine.NATIVE_EOL);
    }
    _durationDisplayed=true;
  }

  private void showInduceCombatStateEffect(StringBuilder sb, InduceCombatStateEffect effect)
  {
    float duration=effect.getDuration();
    LinearFunction durationFunction=effect.getDurationFunction();
    if (durationFunction!=null)
    {
      Float computedDuration=durationFunction.getValue(_level);
      if (computedDuration!=null)
      {
        duration=computedDuration.floatValue();
      }
    }
    CombatState state=effect.getCombatState();
    String stateStr="?";
    if (state!=null)
    {
      stateStr=getStateLabel(state);
    }
    sb.append(duration).append("s ").append(stateStr).append(EndOfLine.NATIVE_EOL);
  }

  private String getStateLabel(CombatState state)
  {
    int code=state.getCode();
    if (code==2) return "Fear";
    if (code==3) return "Daze";
    if ((code==9) || (code==15)) return "Stun";
    if (code==10) return "Root";
    LOGGER.warn("Unmanaged state: "+state.getLabel());
    return state.getLabel();
  }

  private void showDispelByResistEffect(StringBuilder sb, DispelByResistEffect effect)
  {
    int count=effect.getMaxDispelCount();
    List<ResistCategory> categories=effect.getResistCategories();
    boolean useStrengthRestriction=effect.useStrengthRestriction();
    String effects=((count<0) || (count>1))?"effects":"effect";
    // TODO Format categories list
    String label="Removes "+((count<0)?"all":"up to "+count)+" "+categories+" "+effects;
    if (useStrengthRestriction)
    {
      // TODO Use raw spellcraft if any
      Integer strengthOffset=effect.getStrengthOffset();
      int delta=(strengthOffset!=null)?strengthOffset.intValue():4;
      int strength=_level+delta;
      String complement=" with a maximum strength of "+strength;
      label=label+complement;
    }
    sb.append(label).append(EndOfLine.NATIVE_EOL);
  }

  private void showProcEffect(StringBuilder sb, ProcEffect effect)
  {
    showPropertyModificationEffect(sb,effect);
    //Float cooldown=effect.getCooldown();
    Float probability=effect.getProcProbability();
    List<SkillType> skillTypes=effect.getSkillTypes();
    List<EffectGenerator> procedEffects=effect.getProcedEffects();
    StringBuilder childSb=new StringBuilder();
    showEffectGenerators(childSb,procedEffects);
    String descriptionOverride=effect.getDescriptionOverride();
    if (descriptionOverride.isEmpty())
    {
      if (childSb.length()>0)
      {
        sb.append("On every ").append(skillTypes).append(" skill");
        if (probability!=null)
        {
          int percent=(int)(probability.floatValue()*100);
          sb.append(", ").append(percent).append("% chance to");
        }
        sb.append(EndOfLine.NATIVE_EOL);
        sb.append(childSb);
      }
    }
    else
    {
      sb.append(childSb);
    }
  }
}
