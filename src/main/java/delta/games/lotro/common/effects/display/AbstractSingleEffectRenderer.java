package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatModifiersComputer;

/**
 * Base class for effect renderers.
 * @author DAM
 */
public class AbstractSingleEffectRenderer
{
  private EffectRenderingEngine _engine;

  /**
   * Set the engine to use.
   * @param engine Engine to use.
   */
  public void setEngine(EffectRenderingEngine engine)
  {
    _engine=engine;
  }

  /**
   * Get the level to use.
   * @return A level.
   */
  public int getLevel()
  {
    return _engine.getLevel();
  }

  /**
   * Get the rendering state. 
   * @return A rendering state.
   */
  public EffectRenderingState getState()
  {
    return _engine.getState();
  }

  /**
   * Get the rendering context.
   * @return A rendering context.
   */
  public EffectRenderingContext getContext()
  {
    return _engine.getContext();
  }

  protected void showEffectGenerators(List<String> storage, List<EffectGenerator> effects)
  {
    EffectRenderingState state=getState();
    boolean isRootEffectBackup=state.isRootEffect();
    state.setRootEffect(false);
    for(EffectGenerator effectGenerator : effects)
    {
      int childLevel=getLevel();
      Float spellcraft=effectGenerator.getSpellcraft();
      if (spellcraft!=null)
      {
        childLevel=spellcraft.intValue();
      }
      EffectRenderingContext newContext=new EffectRenderingContext(childLevel);
      EffectRenderingEngine engine=new EffectRenderingEngine(state,newContext);
      engine.displayEffect(storage,effectGenerator.getEffect());
    }
    state.setRootEffect(isRootEffectBackup);
  }

  /**
   * Display an effect.
   * @param storage Storage for lines to display.
   * @param effect Effect to show.
   */
  public void displayEffect(List<String> storage, Effect effect)
  {
    _engine.displayEffect(storage,effect);
  }

  /**
   * Compute some additive modifiers.
   * @param modifiers Modifiers to use.
   * @return the computed value.
   */
  public float computeAdditiveModifiers(ModPropertyList modifiers)
  {
    StatModifiersComputer statModifiers=_engine.getContext().getStatModifiersComputer();
    if (statModifiers!=null)
    {
      return statModifiers.computeAdditiveModifiers(modifiers);
    }
    return 0;
  }
}
