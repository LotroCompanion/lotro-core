package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.common.effects.ApplicationProbability;
import delta.games.lotro.common.effects.BaseVitalEffect;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectFlags;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.stats.StatModifiersComputer;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Facilities to display skill effects.
 * @author DAM
 */
public class SkillEffectsDisplay
{
  private CharacterDataForSkills _character;
  private StatModifiersComputer _statModsComputer;

  /**
   * Constructor.
   * @param data Access to character data related to skills.
   * @param skill Skill.
   */
  public SkillEffectsDisplay(CharacterDataForSkills data, SkillDescription skill)
  {
    _character=data;
    _statModsComputer=new StatModifiersComputer(data);
  }

  /**
   * Handle a skill effect.
   * @param damageQualifier Damage qualifier.
   * @param generator Effect generator.
   * @param effect Effect.
   * @param storage Storage for generated text.
   */
  public void handleEffect(DamageQualifier damageQualifier, SkillEffectGenerator generator, Effect effect, List<String> storage)
  {
    int level=_character.getLevel();
    EffectRenderingEngine engine=new EffectRenderingEngine(level);
    engine.setDoDescription(false);
    EffectRenderingContext context=engine.getContext();
    context.setStatValueProvider(_character);
    context.setDamageQualifier(damageQualifier);
    context.setImplementUsage(generator.getImplementUsage());
    handleEffect(engine,effect,storage);
  }

  /**
   * Handle a skill effect.
   * @param engine Engine.
   * @param effect Effect.
   * @param storage Storage for generated text.
   */
  public void handleEffect(EffectRenderingEngine engine, Effect effect, List<String> storage)
  {
    // Check probability
    float probabilityValue=getEffectApplicationProbability(effect);
    boolean applicable=(probabilityValue>0);
    if (!applicable)
    {
      return;
    }
    /*
    if (probabilityValue<1.0f)
    {
      int percentage=Math.round(probabilityValue*100);
      String probabilityLine=percentage+"% chance to apply";
      storage.add(probabilityLine);
    }
    */
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      storage.add(description);
    }
    if (effect instanceof BaseVitalEffect)
    {
      BaseVitalEffect vitalEffect=(BaseVitalEffect)effect;
      EffectDisplay2 d2=new EffectDisplay2(_character);
      EffectRenderingContext context=engine.getContext(); 
      ImplementUsageType implementUsage=context.getImplementUsage();
      DamageQualifier damageQualifier=context.getDamageQualifier();
      d2.getVitalEffectDisplay(implementUsage,vitalEffect,damageQualifier,storage);
    }
    else if (effect instanceof PropertyModificationEffect)
    {
      PropertyModificationEffect propModEffect=(PropertyModificationEffect)effect;
      StatsProvider provider=propModEffect.getStatsProvider();
      if (provider!=null)
      {
        EffectRenderingContext context=engine.getContext(); 
        List<String> lines=StatUtils.getFullStatsForDisplay(provider,context);
        storage.addAll(lines);
      }
      boolean expiresOutOfCombat=effect.getBaseFlag(EffectFlags.DURATION_COMBAT_ONLY);
      if (expiresOutOfCombat)
      {
        // TODO Sometimes "Expires if out of combat for a short amount of time."
        storage.add("Expires if out of combat for 9 seconds.");
      }
      float duration=EffectDisplayUtils.getDuration(propModEffect,_statModsComputer);
      if (duration>0)
      {
        String line="Duration: "+L10n.getString(duration,1)+"s";
        storage.add(line);
      }
    }
    else
    {
      engine.displayEffect(storage,effect);
    }
  }

  private float getEffectApplicationProbability(Effect effect)
  {
    ApplicationProbability probability=effect.getApplicationProbability();
    if (probability==ApplicationProbability.ALWAYS)
    {
      return 1.0f;
    }
    float probabilityValue=probability.getProbability();
    Integer modifier=probability.getModProperty();
    probabilityValue+=_statModsComputer.computeAdditiveModifier(modifier);
    return probabilityValue;
  }
}
