package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.enums.DamageQualifier;

/**
 * Facilities to display skill effects.
 * @author DAM
 */
public class SkillEffectsDisplay
{
  private CharacterDataForSkills _character;

  /**
   * Constructor.
   * @param data Access to character data related to skills.
   */
  public SkillEffectsDisplay(CharacterDataForSkills data)
  {
    _character=data;
  }

  /**
   * Handle a skill effect.
   * @param damageQualifier Damage qualifier.
   * @param generator Effect generator.
   * @param effect Effect.
   * @param channeled Channeled skill or not.
   * @param storage Storage for generated text.
   */
  public void handleEffect(DamageQualifier damageQualifier, SkillEffectGenerator generator, Effect effect, boolean channeled, List<String> storage)
  {
    int level=_character.getLevel();
    EffectRenderingEngine engine=new EffectRenderingEngine(level);
    EffectRenderingContext context=engine.getContext();
    EffectRenderingState state=engine.getState();
    if (channeled)
    {
      state.setDurationDisplayed();
    }
    context.setCharacter(_character);
    context.setStatValueProvider(_character);
    context.setDamageQualifier(damageQualifier);
    context.setImplementUsage(generator.getImplementUsage());
    engine.displayEffect(storage,effect);
  }
}
