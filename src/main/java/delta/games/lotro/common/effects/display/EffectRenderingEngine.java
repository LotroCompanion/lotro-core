package delta.games.lotro.common.effects.display;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.variables.VariableValueProvider;
import delta.common.utils.variables.VariablesResolver;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.utils.strings.TextSanitizer;

/**
 * Effect rendering engine.
 * @author DAM
 */
public class EffectRenderingEngine
{
  private EffectRenderingState _state;
  private EffectRenderingContext _context;
  private boolean _doDescription;

  /**
   * Constructor.
   * @param level Level to use for computations.
   */
  public EffectRenderingEngine(int level)
  {
    _context=new EffectRenderingContext(level);
    _state=new EffectRenderingState();
    _doDescription=true;
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
    _doDescription=true;
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
   * Set the 'do description' flag.
   * @param doDescription Flag to set.
   */
  public void setDoDescription(boolean doDescription)
  {
    _doDescription=doDescription;
  }

  private void displaySpecifics(List<String> storage, Effect effect)
  {
    SingleEffectRenderersFactory f=new SingleEffectRenderersFactory();
    @SuppressWarnings("unchecked")
    SingleEffectRenderer<Effect> renderer=(SingleEffectRenderer<Effect>)f.buildRenderer(effect);
    if (renderer instanceof AbstractSingleEffectRenderer)
    {
      ((AbstractSingleEffectRenderer)renderer).setEngine(this);
    }
    renderer.render(storage,effect);
  }

  /**
   * Display an effect.
   * @param storage Storage for lines to display.
   * @param effect Effect to show.
   */
  public void displayEffect(List<String> storage, Effect effect)
  {
    List<String> childStorage=new ArrayList<String>();
    String descriptionOverride=effect.getDescriptionOverride();
    if (!descriptionOverride.isEmpty())
    {
      String text=resolveVariables(effect,descriptionOverride);
      text=TextSanitizer.sanitize(text);
      childStorage.add(text);
    }
    if (_doDescription)
    {
      String description=effect.getDescription();
      if (!description.isEmpty())
      {
        description=TextSanitizer.sanitize(description);
        childStorage.add(description);
      }
    }
    displaySpecifics(childStorage,effect);
    if (!_state.isDurationDisplayed())
    {
      EffectDuration effectDuration=effect.getEffectDuration();
      if (effectDuration!=null)
      {
        Float duration=effectDuration.getDuration();
        if (duration!=null)
        {
          if (!childStorage.isEmpty())
          {
            String durationStr=Duration.getDurationString(duration.intValue());
            childStorage.add("Duration: "+durationStr);
            _state.setDurationDisplayed();
          }
        }
      }
    }
    storage.addAll(childStorage);
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
}