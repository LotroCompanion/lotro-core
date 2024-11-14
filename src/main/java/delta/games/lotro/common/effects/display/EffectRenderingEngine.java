package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.Effect;

/**
 * Effect rendering engine.
 * @author DAM
 */
public class EffectRenderingEngine
{
  private EffectRenderingState _state;
  private EffectRenderingContext _context;

  /**
   * Constructor.
   * @param level Level to use for computations.
   */
  public EffectRenderingEngine(int level)
  {
    _context=new EffectRenderingContext(level);
    _state=new EffectRenderingState();
  }

  /**
   * Constructor.
   * @param state Initial state.
   * @param context Initial context.
   */
  public EffectRenderingEngine(EffectRenderingState state, EffectRenderingContext context)
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
   * Get the rendering context.
   * @return the rendering context.
   */
  public EffectRenderingContext getContext()
  {
    return _context;
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
    SingleEffectRenderersFactory f=new SingleEffectRenderersFactory();
    @SuppressWarnings("unchecked")
    AbstractSingleEffectRenderer<Effect> renderer=(AbstractSingleEffectRenderer<Effect>)f.buildRenderer(effect);
    renderer.render(this,storage,effect);
  }
}
