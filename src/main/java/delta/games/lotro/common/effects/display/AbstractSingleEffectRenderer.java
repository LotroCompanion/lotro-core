package delta.games.lotro.common.effects.display;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.variables.VariableValueProvider;
import delta.common.utils.variables.VariablesResolver;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.effects.ApplicationProbability;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatModifiersComputer;
import delta.games.lotro.utils.strings.TextSanitizer;

/**
 * Base class for effect renderers.
 * @author DAM
 * @param <T> Type of managed effects.
 */
public abstract class AbstractSingleEffectRenderer<T extends Effect>
{
  private static final Logger LOGGER=LoggerFactory.getLogger(AbstractSingleEffectRenderer.class);

  private EffectRenderingEngine _engine;

  /**
   * Render a single effect.
   * @param engine Engine to use.
   * @param storage Storage.
   * @param effect Effect.
   */
  public void render(EffectRenderingEngine engine, List<String> storage, T effect)
  {
    _engine=engine;
    doRender(storage,effect);
  }

  private void doRender(List<String> storage, T effect)
  {
    LOGGER.debug("Display effect: {}", effect);
    // Check probability
    float probabilityValue=getEffectApplicationProbability(effect);
    boolean applicable=(probabilityValue>0);
    if (!applicable)
    {
      LOGGER.debug("Not applicable!");
      return;
    }
    String probabilityLine=null;
    if (probabilityValue<1.0f)
    {
      int percentage=Math.round(probabilityValue*100);
      probabilityLine=percentage+"% chance to apply:";
      LOGGER.debug("Probability: {}", probabilityLine);
    }

    List<String> childStorage=new ArrayList<String>();
    // Description override
    String descriptionOverride=effect.getDescriptionOverride();
    if (!descriptionOverride.isEmpty())
    {
      String text=resolveVariables(effect,descriptionOverride);
      text=TextSanitizer.sanitize(text);
      childStorage.add(text);
      LOGGER.debug("Description override: [{}]", text);
    }
    // Description
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      description=TextSanitizer.sanitize(description);
      childStorage.add(description);
      // Assume that this description will remove the need for a probability line
      probabilityLine=null;
      LOGGER.debug("Description: [{}]", description);
    }
    // Effect specifics
    render(childStorage,effect);
    // Duration
    EffectRenderingState state=_engine.getState();
    if (!state.isDurationDisplayed())
    {
      EffectDuration effectDuration=effect.getEffectDuration();
      if (effectDuration!=null)
      {
        if (!childStorage.isEmpty())
        {
          EffectRenderingContext context=getContext();
          float totalDuration=EffectDisplayUtils.getTotalDuration(effect,context.getStatModifiersComputer());
          String durationStr=Duration.getDurationString(Math.round(totalDuration));
          childStorage.add("Duration: "+durationStr);
          LOGGER.debug("Duration: {}", durationStr);
          state.setDurationDisplayed();
        }
      }
    }
    // Fill storage
    if (!childStorage.isEmpty())
    {
      if (probabilityLine!=null)
      {
        storage.add(probabilityLine);
      }
      storage.addAll(childStorage);
      if (LOGGER.isDebugEnabled())
      {
        for(String line : childStorage)
        {
          LOGGER.debug("\tLine: {}", line);
        }
      }
    }
    LOGGER.debug("END Display effect: {}", effect);
  }

  /**
   * Render a single effect.
   * @param storage Storage.
   * @param effect Effect.
   */
  public abstract void render(List<String> storage, T effect);

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

  protected void showEffectGenerators(List<String> storage, List<? extends EffectGenerator> effects)
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
      EffectRenderingContext newContext=getContext().copy();
      newContext.setLevel(childLevel);
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
  protected void displayEffect(List<String> storage, Effect effect)
  {
    _engine.displayEffect(storage,effect);
  }

  /**
   * Compute some additive modifiers.
   * @param modifiers Modifiers to use.
   * @return the computed value.
   */
  protected float computeAdditiveModifiers(ModPropertyList modifiers)
  {
    StatModifiersComputer statModifiers=_engine.getContext().getStatModifiersComputer();
    if (statModifiers!=null)
    {
      return statModifiers.computeAdditiveModifiers(modifiers);
    }
    return 0;
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
    StatModifiersComputer statModsComputer=getContext().getStatModifiersComputer();
    if (statModsComputer!=null)
    {
      probabilityValue+=statModsComputer.computeAdditiveModifier(modifier);
    }
    return probabilityValue;
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
