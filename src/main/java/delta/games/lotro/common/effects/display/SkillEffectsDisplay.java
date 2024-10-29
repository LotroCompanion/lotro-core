package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.common.effects.ApplicationProbability;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.stats.StatModifiersComputer;

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
    context.setCharacter(_character);
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
    engine.displayEffect(storage,effect);
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
