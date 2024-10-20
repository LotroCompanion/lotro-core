package delta.games.lotro.common.effects.display;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.variables.VariableValueProvider;
import delta.common.utils.variables.VariablesResolver;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.effects.AbstractVitalChange;
import delta.games.lotro.common.effects.ComboEffect;
import delta.games.lotro.common.effects.DispelByResistEffect;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectAndProbability;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.GenesisEffect;
import delta.games.lotro.common.effects.Hotspot;
import delta.games.lotro.common.effects.InduceCombatStateEffect;
import delta.games.lotro.common.effects.InstantFellowshipEffect;
import delta.games.lotro.common.effects.InstantVitalEffect;
import delta.games.lotro.common.effects.ProcEffect;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.effects.ReactiveChange;
import delta.games.lotro.common.effects.ReactiveVitalChange;
import delta.games.lotro.common.effects.ReactiveVitalEffect;
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.effects.VitalOverTimeEffect;
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
import delta.games.lotro.utils.strings.TextSanitizer;

/**
 * Display effects.
 * @author DAM
 */
public class EffectDisplay
{
  private static final Logger LOGGER=LoggerFactory.getLogger(EffectDisplay.class);

  private EffectRenderingState _state;
  private EffectRenderingContext _context;

  /**
   * Constructor.
   * @param level Level to use for computations.
   */
  public EffectDisplay(int level)
  {
    _context=new EffectRenderingContext(level);
    _state=new EffectRenderingState();
  }

  /**
   * Constructor.
   * @param state Initial state.
   * @param context Initial context.
   */
  public EffectDisplay(EffectRenderingState state, EffectRenderingContext context)
  {
    _state=state;
    _context=context;
  }

  /**
   * Get the rendering state.
   * @return the rendering state.
   */
  public EffectRenderingState getState()
  {
    return _state;
  }

  /**
   * Get the level to use.
   * @return A level.
   */
  public int getLevel()
  {
    return _context.getLevel();
  }

  /**
   * Display an effect.
   * @param storage Storage for lines to display.
   * @param effect Effect to show.
   */
  public void displayEffect(List<String> storage, Effect effect)
  {
    String descriptionOverride=effect.getDescriptionOverride();
    if (!descriptionOverride.isEmpty())
    {
      String text=resolveVariables(effect,descriptionOverride);
      text=TextSanitizer.sanitize(text);
      storage.add(text);
    }
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      description=TextSanitizer.sanitize(description);
      storage.add(description);
    }
    displaySpecifics(storage,effect);
    if (!_state.isDurationDisplayed())
    {
      EffectDuration effectDuration=effect.getEffectDuration();
      if (effectDuration!=null)
      {
        Float duration=effectDuration.getDuration();
        if (duration!=null)
        {
          if (!storage.isEmpty())
          {
            String durationStr=Duration.getDurationString(duration.intValue());
            storage.add("Duration: "+durationStr);
            _state.setDurationDisplayed();
          }
        }
      }
    }
  }

  private String resolveVariables(Effect effect, String input)
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

  private String resolveVariable(Effect effect, String variableName)
  {
    String ret=effect.resolveVariable(variableName);
    if (ret!=null)
    {
      return ret;
    }
    return variableName;
  }

  private void displaySpecifics(List<String> storage, Effect effect)
  {
    if (effect instanceof InstantVitalEffect)
    {
      showInstantVitalEffect(storage,(InstantVitalEffect)effect);
    }
    else if (effect instanceof ProcEffect)
    {
      showProcEffect(storage,(ProcEffect)effect);
    }
    else if (effect instanceof ReactiveVitalEffect)
    {
      showReactiveVitalEffect(storage,(ReactiveVitalEffect)effect);
    }
    else if (effect instanceof PropertyModificationEffect)
    {
      showPropertyModificationEffect(storage,(PropertyModificationEffect)effect);
    }
    else if (effect instanceof InstantFellowshipEffect)
    {
      showInstantFellowshipEffect(storage,(InstantFellowshipEffect)effect);
    }
    else if (effect instanceof GenesisEffect)
    {
      showGenesisEffect(storage,(GenesisEffect)effect);
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      showVitalOverTimeEffect(storage,(VitalOverTimeEffect)effect);
    }
    else if (effect instanceof InduceCombatStateEffect)
    {
      showInduceCombatStateEffect(storage,(InduceCombatStateEffect)effect);
    }
    else if (effect instanceof DispelByResistEffect)
    {
      showDispelByResistEffect(storage,(DispelByResistEffect)effect);
    }
    else if (effect instanceof ComboEffect)
    {
      // Nothing?
    }
  }

  private void showInstantVitalEffect(List<String> storage, InstantVitalEffect effect)
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
          String text=(-minMax[0])+" Damage";
          storage.add(text);
        }
        else
        {
          String text=(minMax[0])+" "+stat.getName();
          storage.add(text);
        }
      }
      else
      {
        if ((minMax[0]<0) && (minMax[1]<0) && isMorale)
        {
          String text=(-minMax[0])+" - "+(-minMax[1])+" Damage";
          storage.add(text);
        }
        else
        {
          String text=(minMax[0])+" - "+(minMax[1])+" "+stat.getName();
          storage.add(text);
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
        StringBuilder sb=new StringBuilder();
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
        sb.append(" of maximum ").append(stat.getName());
        storage.add(sb.toString());
      }
    }
  }

  private Float getValue(AbstractVitalChange change)
  {
    Float value=change.getConstant();
    if (value!=null)
    {
      return value;
    }
    Progression progression=change.getProgression();
    if (progression!=null)
    {
      value=progression.getValue(getLevel());
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

  private void showPropertyModificationEffect(List<String> storage, PropertyModificationEffect effect)
  {
    if (_state.hidePropertyModificationStats())
    {
      return;
    }
    StatsProvider provider=effect.getStatsProvider();
    if (provider==null)
    {
      return;
    }
    List<String> lines=StatUtils.getFullStatsForDisplay(provider,getLevel());
    storage.addAll(lines);
  }

  private void showReactiveVitalEffect(List<String> storage, ReactiveVitalEffect effect)
  {
    showPropertyModificationEffect(storage,effect);
    // Defender
    ReactiveChange defender=effect.getDefenderReactiveChange();
    if (defender!=null)
    {
      List<DamageType> damageTypes=effect.getDamageTypes();
      if (!damageTypes.isEmpty())
      {
        String damageTypesStr=EffectDisplayUtils.formatDamageType(damageTypes);
        String text="On any "+damageTypesStr+"damage:";
        storage.add(text);
      }
      ReactiveVitalChange change=defender.getVitalChange();
      if (change!=null)
      {
        Float value=getValue(change);
        float probability=change.getProbability();
        boolean multiplicative=change.isMultiplicative();
        int percentage=(int)(probability*100);
        if (!multiplicative)
        {
          int damage=(value!=null)?((int)value.floatValue()):0;
          if (percentage!=100)
          {
            String text=percentage+"% chance to Negate "+damage+" damage";
            storage.add(text);
          }
          else
          {
            // Never?
            String text="Negate "+damage+" damage";
            storage.add(text);
          }
        }
        else
        {
          int percentageDamage=(value!=null)?((int)(value.floatValue()*100)):0;
          if (percentage!=100)
          {
            // Never?
            String text=percentage+"% chance to Negate "+percentageDamage+"% damage";
            storage.add(text);
          }
          else
          {
            // Negate X% damage
            String text="Negate "+percentageDamage+"% damage";
            storage.add(text);
          }
        }
      }
      EffectAndProbability defenderEffect=defender.getEffect();
      if (defenderEffect!=null)
      {
        float effectProbability=defenderEffect.getProbability();
        int effectPercentage=(int)(effectProbability*100);
        String text=effectPercentage+"% chance to Receive effect:";
        storage.add(text);
        displayEffect(storage,defenderEffect.getEffect());
      }
    }
    // Attacker
    ReactiveChange attacker=effect.getAttackerReactiveChange();
    if (attacker!=null)
    {
      List<DamageType> damageTypes=effect.getDamageTypes();
      if (!damageTypes.isEmpty())
      {
        String damageTypesStr=EffectDisplayUtils.formatDamageType(damageTypes);
        String text="On any "+damageTypesStr+"damage:";
        storage.add(text);
      }
      ReactiveVitalChange change=attacker.getVitalChange();
      if (change!=null)
      {
        Float value=getValue(change);
        float probability=change.getProbability();
        int percentage=(int)(probability*100);
        float safeValue=(value!=null)?value.floatValue():0;
        boolean multiplicative=change.isMultiplicative();
        if (!multiplicative)
        {
          int damage=Math.round(Math.abs(safeValue));
          if (percentage!=100)
          {
            String text=percentage+"% chance to Reflect "+damage+" damage";
            storage.add(text);
          }
          else
          {
            String text="Reflect "+damage+" damage";
            storage.add(text);
          }
        }
        else
        {
          // Never multiplicative?
          int percentageDamage=(int)(safeValue*100);
          if (percentage!=100)
          {
            String text=percentage+"% chance to Reflect "+percentageDamage+"% damage";
            storage.add(text);
          }
          else
          {
            String text="Reflect "+percentageDamage+"% damage";
            storage.add(text);
          }
        }
      }
      EffectAndProbability attackerEffect=attacker.getEffect();
      if (attackerEffect!=null)
      {
        float effectProbability=attackerEffect.getProbability();
        int effectPercentage=(int)(effectProbability*100);
        String text=effectPercentage+"% chance to Reflect effect:";
        storage.add(text);
        displayEffect(storage,attackerEffect.getEffect());
      }
    }
  }

  private void showInstantFellowshipEffect(List<String> storage, InstantFellowshipEffect effect)
  {
    Float range=effect.getRange();
    List<EffectGenerator> effects=effect.getEffects();
    //boolean appliesToTarget=effect.appliesToTarget();
    int rangeInt=(int)range.floatValue();
    String text="Effects applied to the Fellowship within "+rangeInt+" metres:";
    storage.add(text);
    showEffectGenerators(storage,effects);
  }

  private void showEffectGenerators(List<String> storage, List<EffectGenerator> effects)
  {
    EffectRenderingState newState=_state.buildChildState();
    for(EffectGenerator effectGenerator : effects)
    {
      int childLevel=getLevel();
      Float spellcraft=effectGenerator.getSpellcraft();
      if (spellcraft!=null)
      {
        childLevel=spellcraft.intValue();
      }
      EffectRenderingContext newContext=new EffectRenderingContext(childLevel);
      EffectDisplay childDisplay=new EffectDisplay(newState,newContext);
      childDisplay.displayEffect(storage,effectGenerator.getEffect());
    }
  }

  private void showGenesisEffect(List<String> storage, GenesisEffect effect)
  {
    boolean permanent=effect.isPermanent();
    if (permanent)
    {
      storage.add("Permanent");
    }
    else
    {
      float duration=effect.getSummonDuration();
      String durationStr=Duration.getDurationString((int)duration);
      storage.add("Duration: "+durationStr);
    }
    _state.setDurationDisplayed();
    Hotspot hotspot=effect.getHotspot();
    if (hotspot!=null)
    {
      showEffectGenerators(storage,hotspot.getEffects());
    }
  }

  private void showVitalOverTimeEffect(List<String> storage, VitalOverTimeEffect effect)
  {
    VitalChangeDescription initialChange=effect.getInitialChangeDescription();
    VitalChangeDescription overTimeChange=effect.getOverTimeChangeDescription();
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
      if (initialChange!=null)
      {
        Float initialValue=getValue(initialChange);
        String text=initialValue+" "+damageType.getLabel()+" Damage initially.";
        storage.add(text);
      }
      if (overTimeChange!=null)
      {
        Float overTimeValue=getValue(overTimeChange);
        String text=overTimeValue+" "+damageType.getLabel()+" Damage"+overtime;
        storage.add(text);
      }
    }
    else
    {
      // Heal values do not seem to be changed by Incoming Healing or Outgoing Healing!
      // TODO Use "Heals" if Morale, "Restores" if Power!
      // TODO Handle variance
      if (initialChange!=null)
      {
        Float initialValue=getValue(initialChange);
        String text="Restores "+initialValue+" "+stat.getName()+" initially.";
        storage.add(text);
      }
      if (overTimeChange!=null)
      {
        Float overTimeValue=getValue(overTimeChange);
        String text="Restores "+overTimeValue+" "+stat.getName()+overtime;
        storage.add(text);
      }
    }
    _state.setDurationDisplayed();
  }

  private void showInduceCombatStateEffect(List<String> storage, InduceCombatStateEffect effect)
  {
    float duration=effect.getDuration();
    LinearFunction durationFunction=effect.getDurationFunction();
    if (durationFunction!=null)
    {
      Float computedDuration=durationFunction.getValue(getLevel());
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
    String text=duration+"s "+stateStr;
    storage.add(text);
  }

  /**
   * Get the label for the given state.
   * @param state State to use.
   * @return A label.
   */
  public static String getStateLabel(CombatState state)
  {
    int code=state.getCode();
    if (code==2) return "Fear";
    if (code==3) return "Daze";
    if ((code==9) || (code==15)) return "Stun";
    if (code==10) return "Root";
    LOGGER.warn("Unmanaged state: {}",state);
    return state.getLabel();
  }

  private void showDispelByResistEffect(List<String> storage, DispelByResistEffect effect)
  {
    int count=effect.getMaxDispelCount();
    List<ResistCategory> categories=effect.getResistCategories();
    boolean useStrengthRestriction=effect.useStrengthRestriction();
    String effects=((count<0) || (count>1))?"effects":"effect";
    String categoriesStr=EffectDisplayUtils.formatResistCategories(categories);
    String label="Removes "+((count<0)?"all":"up to "+count)+" "+categoriesStr+" "+effects;
    if (useStrengthRestriction)
    {
      // TODO Use raw spellcraft if any
      Integer strengthOffset=effect.getStrengthOffset();
      int delta=(strengthOffset!=null)?strengthOffset.intValue():4;
      int strength=getLevel()+delta;
      String complement=" with a maximum strength of "+strength;
      label=label+complement;
    }
    storage.add(label);
  }

  private void showProcEffect(List<String> storage, ProcEffect effect)
  {
    showPropertyModificationEffect(storage,effect);
    Float probability=effect.getProcProbability();
    List<SkillType> skillTypes=effect.getSkillTypes();
    List<EffectGenerator> procedEffects=effect.getProcedEffects();
    List<String> childStorage=new ArrayList<String>();
    showEffectGenerators(childStorage,procedEffects);
    String descriptionOverride=effect.getDescriptionOverride();
    if (descriptionOverride.isEmpty())
    {
      if (!childStorage.isEmpty())
      {
        String skillTypesStr=EffectDisplayUtils.formatSkillTypes(skillTypes);
        String text="On every "+skillTypesStr+" skill";
        if (probability!=null)
        {
          int percent=(int)(probability.floatValue()*100);
          String suffix=", "+percent+"% chance to";
          text=text+suffix;
        }
        storage.add(text);
        storage.addAll(childStorage);
      }
    }
    else
    {
      storage.addAll(childStorage);
    }
  }

}
