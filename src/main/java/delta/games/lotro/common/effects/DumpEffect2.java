package delta.games.lotro.common.effects;

import java.util.List;

import delta.common.utils.io.streams.IndentableStream;
import delta.games.lotro.common.enums.SkillType;
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
   * @param is Output stram.
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
    EffectDuration duration=effect.getDuration();
    _is.println("Duration: "+duration);
    ApplicationProbability probability=effect.getApplicationProbability();
    _is.println("Probability: "+probability);
    for(EffectAspect aspect : effect.getAspects())
    {
      showAspect(aspect);
    }
    _is.decrementIndentationLevel();
  }

  private void showAspect(EffectAspect aspect)
  {
    if (aspect instanceof ProcEffect)
    {
      showProcEffect((ProcEffect)aspect);
    }
    else if (aspect instanceof StatsEffect)
    {
      showStatEffect((StatsEffect)aspect);
    }
    else if (aspect instanceof InstantVitalEffect)
    {
      showInstantVitalEffect((InstantVitalEffect)aspect);
    }
    else if (aspect instanceof VitalOverTimeEffect)
    {
      showVitalOverTimeEffect((VitalOverTimeEffect)aspect);
    }
    else if (aspect instanceof InstantFellowshipEffect)
    {
      showInstantFellowshipEffect((InstantFellowshipEffect)aspect);
    }
    else if (aspect instanceof ReactiveVitalEffect)
    {
      showReactiveVitalEffect((ReactiveVitalEffect)aspect);
    }
  }

  private void showStatEffect(StatsEffect statsEffect)
  {
    StatsProvider statsProvider=statsEffect.getStatsProvider();
    _is.println("Stats: "+statsProvider);
  }

  private void showProcEffect(ProcEffect procEffect)
  {
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
    showGenerators(procedEffects);
  }

  private void showInstantVitalEffect(InstantVitalEffect aspect)
  {
    StatDescription stat=aspect.getStat();
    _is.println("Vital instant change: "+stat.getName());
    if (aspect.isMultiplicative())
    {
      _is.println("Multiplicative");
    }
    // Instant change
    _is.println("Instant change:");
    dumpVitalChangeDescription(aspect.getInstantChangeDescription());
  }

  private void showVitalOverTimeEffect(VitalOverTimeEffect aspect)
  {
    StatDescription stat=aspect.getStat();
    _is.println("Vital over-time change: "+stat.getName());
    // Initial change:
    _is.println("Initial change:");
    dumpVitalChangeDescription(aspect.getInitialChangeDescription());
    _is.println("Over-time change:");
    dumpVitalChangeDescription(aspect.getOverTimeChangeDescription());
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
    _is.decrementIndentationLevel();
  }

  private void showInstantFellowshipEffect(InstantFellowshipEffect aspect)
  {
    _is.println("Fellowship effect:");
    showGenerators(aspect.getEffects());
    boolean applyToRaidGroups=aspect.appliesToRaidGroups();
    if (applyToRaidGroups)
    {
      _is.println("Apply to raid groups");
    }
    boolean applyToPets=aspect.appliesToPets();
    if (applyToPets)
    {
      _is.println("Apply to pets");
    }
    boolean applyToTarget=aspect.appliesToTarget();
    if (applyToTarget)
    {
      _is.println("Apply to target");
    }
    Float range=aspect.getRange();
    if (range!=null)
    {
      _is.println("Range: "+range+"m");
    }
  }

  private void showReactiveVitalEffect(ReactiveVitalEffect aspect)
  {
    _is.println("Reactive vital effect:");
    List<DamageType> damageTypes=aspect.getDamageTypes();
    _is.println("Damage types: "+damageTypes);
    DamageType override=aspect.getAttackerDamageTypeOverride();
    if (override!=null)
    {
      _is.println("Damage override: "+override);
    }
    dumpReactiveVitalChange("Attacker vital change:",aspect.getAttackerVitalChange());
    dumpEffectAndProbability("Attacker effect: ",aspect.getAttackerEffect());
    dumpReactiveVitalChange("Defender vital change:",aspect.getDefenderVitalChange());
    dumpEffectAndProbability("Defender effect: ",aspect.getDefenderEffect());
    boolean removeOnProc=aspect.isRemoveOnProc();
    if (removeOnProc)
    {
      _is.println("Removed on proc");
    }
  }

  private void dumpReactiveVitalChange(String label, ReactiveVitalChange change)
  {
    if (change==null)
    {
      return;
    }
    _is.println(label);
    _is.incrementIndendationLevel();
    Float constant=change.getConstant();
    if (constant!=null)
    {
      _is.println("Constant: "+constant);
    }
    Progression progression=change.getProgression();
    if (progression!=null)
    {
      _is.println("Progression: "+progression);
    }
    float probability=change.getProbability();
    _is.println("Probability: "+probability);
    if (change.isMultiplicative())
    {
      _is.println("Multiplicative");
    }
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

  private void showGenerators(List<EffectGenerator> generators)
  {
    if (!generators.isEmpty())
    {
      _is.println("Proc'ed effects:");
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
