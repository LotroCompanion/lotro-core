package delta.games.lotro.common.effects;

import java.util.List;

import delta.common.utils.io.streams.IndentableStream;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.Interactable;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillType;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.maths.Progression;

/**
 * DUmp effects data.
 * @author DAM
 */
public class DumpEffect2
{
  private IndentableStream _is;

  /**
   * Constructor.
   * @param is Output stream.
   */
  public DumpEffect2(IndentableStream is)
  {
    _is=is;
  }

  /**
   * Dump an effect.
   * @param effect Effect to show.
   */
  public void dumpEffect(Effect2 effect)
  {
    _is.println("Effect ID="+effect.getIdentifier()+", name="+effect.getName());
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      _is.println("Description: "+description);
    }
    String appliedDescription=effect.getAppliedDescription();
    if (!appliedDescription.isEmpty())
    {
      _is.println("Applied description: "+appliedDescription);
    }
    String descriptionOverride=effect.getDescriptionOverride();
    if (!descriptionOverride.isEmpty())
    {
      _is.println("Description override: "+descriptionOverride);
    }
    _is.incrementIndendationLevel();
    EffectDuration duration=effect.getEffectDuration();
    String durationStr=duration.toString();
    if (durationStr.length()>0)
    {
      _is.println("Duration: "+duration);
    }
    ApplicationProbability probability=effect.getApplicationProbability();
    _is.println("Probability: "+probability);
    showSpecifics(effect);
    _is.decrementIndentationLevel();
  }

  private void showSpecifics(Effect2 effect)
  {
    if (effect.getClass()!=Effect2.class)
    {
      String className=effect.getClass().getSimpleName();
      boolean doIt=true;
      if ("PropertyModificationEffect".equals(className))
      {
        className="StatsEffect";
        if (((PropertyModificationEffect)effect).getStatsProvider()==null)
        {
          doIt=false;
        }
      }
      if (doIt)
      {
        System.out.println("Aspect class "+className);
      }
    }
    if (effect instanceof ProcEffect)
    {
      showProcEffect((ProcEffect)effect);
    }
    else if (effect instanceof ReactiveVitalEffect)
    {
      showReactiveVitalEffect((ReactiveVitalEffect)effect);
    }
    else if (effect instanceof PropertyModificationEffect)
    {
      showPropertyModificationEffect((PropertyModificationEffect)effect);
    }
    else if (effect instanceof InstantVitalEffect)
    {
      showInstantVitalEffect((InstantVitalEffect)effect);
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      showVitalOverTimeEffect((VitalOverTimeEffect)effect);
    }
    else if (effect instanceof InstantFellowshipEffect)
    {
      showInstantFellowshipEffect((InstantFellowshipEffect)effect);
    }
    else if (effect instanceof GenesisEffect)
    {
      showGenesisEffect((GenesisEffect)effect);
    }
    else if (effect instanceof InduceCombatStateEffect)
    {
      showInduceCombatStateEffect((InduceCombatStateEffect)effect);
    }
    else if (effect instanceof DispelByResistEffect)
    {
      showDispelByResistEffect((DispelByResistEffect)effect);
    }
  }

  private void showPropertyModificationEffect(PropertyModificationEffect statsEffect)
  {
    StatsProvider statsProvider=statsEffect.getStatsProvider();
    if (statsProvider!=null)
    {
      _is.println("Stats: "+statsProvider);
    }
  }

  private void showProcEffect(ProcEffect procEffect)
  {
    showPropertyModificationEffect(procEffect);
    List<SkillType> skillTypes=procEffect.getSkillTypes();
    _is.println("Proc: skillTypes="+skillTypes);
    Float probability=procEffect.getProcProbability();
    if (probability!=null)
    {
      _is.println("Probability: "+probability);
    }
    Float cooldown=procEffect.getCooldown();
    if (cooldown!=null)
    {
      _is.println("Cooldown: "+cooldown);
    }
    List<EffectGenerator> procedEffects=procEffect.getProcedEffects();
    showGenerators("Proc'ed effects:", procedEffects);
  }

  private void showInstantVitalEffect(InstantVitalEffect effect)
  {
    StatDescription stat=effect.getStat();
    _is.println("Vital instant change: "+stat.getName());
    if (effect.isMultiplicative())
    {
      _is.println("Multiplicative");
    }
    // Instant change
    _is.println("Instant change:");
    dumpVitalChangeDescription(effect.getInstantChangeDescription());
  }

  private void showVitalOverTimeEffect(VitalOverTimeEffect effect)
  {
    StatDescription stat=effect.getStat();
    _is.println("Vital over-time change: "+stat.getName());
    // Damage type
    DamageType damageType=effect.getDamageType();
    if (damageType!=null)
    {
      _is.println("Damage type: "+damageType.getLabel());
    }
    // Initial change:
    _is.println("Initial change:");
    dumpVitalChangeDescription(effect.getInitialChangeDescription());
    // Over-time change:
    _is.println("Over-time change:");
    dumpVitalChangeDescription(effect.getOverTimeChangeDescription());
  }

  private void dumpVitalChangeDescription(VitalChangeDescription description)
  {
    if (description==null)
    {
      return;
    }
    _is.incrementIndendationLevel();
    Float constant=description.getConstant();
    if (constant!=null)
    {
      _is.println("Constant: "+constant);
    }
    Progression progression=description.getProgression();
    if (progression!=null)
    {
      _is.println("Progression: "+progression);
    }
    Float min=description.getMinValue();
    Float max=description.getMaxValue();
    if ((min!=null) && (max!=null))
    {
      _is.println("Range: "+min+"-"+max);
    }
    Float variance=description.getVariance();
    if (variance!=null)
    {
      _is.println("Variance: "+(variance.floatValue()*100)+"%");
    }
    _is.decrementIndentationLevel();
  }

  private void showInstantFellowshipEffect(InstantFellowshipEffect effect)
  {
    _is.println("Fellowship effect:");
    showGenerators("Proc'ed effects:", effect.getEffects());
    boolean applyToRaidGroups=effect.appliesToRaidGroups();
    if (applyToRaidGroups)
    {
      _is.println("Apply to raid groups");
    }
    boolean applyToPets=effect.appliesToPets();
    if (applyToPets)
    {
      _is.println("Apply to pets");
    }
    boolean applyToTarget=effect.appliesToTarget();
    if (applyToTarget)
    {
      _is.println("Apply to target");
    }
    Float range=effect.getRange();
    if (range!=null)
    {
      _is.println("Range: "+range+"m");
    }
  }

  private void showReactiveVitalEffect(ReactiveVitalEffect effect)
  {
    showPropertyModificationEffect(effect);
    _is.println("Reactive vital effect:");
    List<DamageType> damageTypes=effect.getDamageTypes();
    _is.println("Damage types: "+damageTypes);
    DamageType override=effect.getAttackerDamageTypeOverride();
    if (override!=null)
    {
      _is.println("Damage override: "+override);
    }
    dumpReactiveChange("Attacker",effect.getAttackerReactiveChange());
    dumpReactiveChange("Defender",effect.getDefenderReactiveChange());
    boolean removeOnProc=effect.isRemoveOnProc();
    if (removeOnProc)
    {
      _is.println("Removed on proc");
    }
  }

  private void dumpReactiveChange(String label, ReactiveChange change)
  {
    if (change==null)
    {
      return;
    }
    ReactiveVitalChange vitalChange=change.getVitalChange();
    if (vitalChange!=null)
    {
      _is.println(label+" vital change:");
      _is.incrementIndendationLevel();
      Float constant=vitalChange.getConstant();
      if (constant!=null) 
      {
        _is.println("Constant: "+constant);
      }
      Progression progression=vitalChange.getProgression();
      if (progression!=null)
      {
        _is.println("Progression: "+progression);
      }
      Float variance=vitalChange.getVariance();
      if (variance!=null)
      {
        _is.println("Variance: "+(variance.floatValue()*100)+"%");
      }
      float probability=vitalChange.getProbability();
      _is.println("Probability: "+probability);
      if (vitalChange.isMultiplicative())
      {
        _is.println("Multiplicative");
      }
    }
    dumpEffectAndProbability(label+" effect: ",change.getEffect());
    _is.decrementIndentationLevel();
  }

  private void dumpEffectAndProbability(String label, EffectAndProbability effectProb)
  {
    if (effectProb==null)
    {
      return;
    }
    _is.println(label);
    _is.incrementIndendationLevel();
    _is.println("Probability: "+effectProb.getProbability());
    Effect2 effect=effectProb.getEffect();
    _is.println("Effect: "+effect.getIdentifier()+" - "+effect.getName());
    _is.decrementIndentationLevel();
  }

  private void showGenesisEffect(GenesisEffect effect)
  {
    boolean permanent=effect.isPermanent();
    if (permanent)
    {
      _is.println("Permanent");
    }
    else
    {
      _is.println("Duration: "+Duration.getDurationString((int)effect.getSummonDuration()));
    }
    Hotspot hotspot=effect.getHotspot();
    if (hotspot!=null)
    {
      int hotspotID=hotspot.getIdentifier();
      String name=hotspot.getName();
      _is.println("Summoned hotspot ID="+hotspotID+": "+name);
      showGenerators("Hotspot effects:", hotspot.getEffects());
    }
    Interactable interactable=effect.getInteractable();
    if (interactable!=null)
    {
      int id=interactable.getIdentifier();
      String name=interactable.getName();
      _is.println("Summoned element ID="+id+": "+name);
    }
  }

  private void showInduceCombatStateEffect(InduceCombatStateEffect effect)
  {
    CombatState state=effect.getCombatState();
    _is.println("Combat state:" +state);
    LinearFunction function=effect.getDurationFunction();
    if (function!=null)
    {
      _is.println("Duration: "+function);
    }
    else
    {
      float duration=effect.getDuration();
      _is.println("Duration: "+duration);
    }
  }

  private void showDispelByResistEffect(DispelByResistEffect effect)
  {
    int dispelCount=effect.getMaxDispelCount();
    _is.println("Max dispel count: "+((dispelCount>0)?String.valueOf(dispelCount):"all"));
    List<ResistCategory> categories=effect.getResistCategories();
    _is.println("Resist categories: "+categories);
    if (effect.useStrengthRestriction())
    {
      _is.println("Use strength restriction.");
      Integer offset=effect.getStrengthOffset();
      if (offset!=null)
      {
        _is.println("Strength offset: "+offset);
      }
    }
  }

  private void showGenerators(String label, List<EffectGenerator> generators)
  {
    if (!generators.isEmpty())
    {
      _is.println(label);
      _is.incrementIndendationLevel();
      for(EffectGenerator generator : generators)
      {
        Effect2 effect=generator.getEffect();
        Float spellcraft=generator.getSpellcraft();
        String msg="ID="+effect.getIdentifier()+", name="+effect.getName();
        if (spellcraft!=null)
        {
          msg=msg+", spellcraft="+spellcraft;
        }
        _is.println(msg);
      }
      _is.decrementIndentationLevel();
    }
  }
}
