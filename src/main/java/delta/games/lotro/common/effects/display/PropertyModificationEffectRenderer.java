package delta.games.lotro.common.effects.display;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.effects.StatsProviderStructValuesVisitor;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.effects.EffectFlags;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatModifiersComputer;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.effects.DisplayEffectsUtils;
import delta.games.lotro.lore.utils.EffectUtils;
import delta.games.lotro.values.StructValue;

/**
 * Renderer for 'property modification' effects.
 * @author DAM
 * @param <T> Actual type of effect.
 */
public class PropertyModificationEffectRenderer<T extends PropertyModificationEffect> extends AbstractSingleEffectRenderer<T>
{
  private static final String EFFECTS_LIST_KEY="${EFFECTLIST}";

  @Override
  public void render(List<String> storage, T effect)
  {
    List<String> childStorage=new ArrayList<String>();
    renderSpecifics(childStorage,effect);
    if (!childStorage.isEmpty())
    {
      renderDuration(childStorage,effect);
    }
    renderAfterDuration(childStorage,effect);
    storage.addAll(childStorage);
  }

  protected void renderStats(List<String> storage, T effect)
  {
    EffectRenderingState state=getState();
    if (state.hidePropertyModificationStats())
    {
      return;
    }
    StatsProvider provider=effect.getStatsProvider();
    if (provider!=null)
    {
      EffectRenderingContext context=getContext(); 
      List<String> lines=StatUtils.getFullStatsForDisplay(provider,context);
      lines=handleEffectsList(lines,provider);
      storage.addAll(lines);
    }
    boolean expiresOutOfCombat=effect.getBaseFlag(EffectFlags.DURATION_COMBAT_ONLY);
    if (expiresOutOfCombat)
    {
      // TODO Sometimes "Expires if out of combat for a short amount of time."
      storage.add("Expires if out of combat for 9 seconds.");
    }
  }

  private List<String> handleEffectsList(List<String> lines, StatsProvider provider)
  {
    boolean hasMarker=hasEffectsListMarker(lines);
    if (!hasMarker)
    {
      return lines;
    }
    List<String> ret=new ArrayList<String>();
    List<String> effectsList=getEffectsList(provider);
    for(String line : lines)
    {
      if (line.contains(EFFECTS_LIST_KEY))
      {
        String updatedInputLine=line.replace(EFFECTS_LIST_KEY,"").trim();
        if (!updatedInputLine.isEmpty())
        {
          ret.add(updatedInputLine);
        }
        if (!effectsList.isEmpty())
        {
          ret.addAll(effectsList);
        }
      }
      else
      {
        ret.add(line);
      }
    }
    return ret;
  }

  private boolean hasEffectsListMarker(List<String> lines)
  {
    for(String line : lines)
    {
      if (line.contains(EFFECTS_LIST_KEY))
      {
        return true;
      }
    }
    return false;
  }

  private static class EffectsListBuilder
  {
    private List<SkillEffectGenerator> generators=new ArrayList<SkillEffectGenerator>();

    private Void handleSkillEffect(StatDescription stat, StructValue structValue)
    {
      SkillEffectGenerator generator=EffectUtils.decodeEffect(structValue);
      if (generator!=null)
      {
        generators.add(generator);
      }
      return null;
    }

    public List<SkillEffectGenerator> build(StatsProvider provider)
    {
      StatsProviderStructValuesVisitor visitor=new StatsProviderStructValuesVisitor(this::handleSkillEffect);
      visitor.inspectStatsProvider(provider);
      return generators;
    }
  }

  private List<String> getEffectsList(StatsProvider provider)
  {
    List<String> ret=new ArrayList<String>();
    List<SkillEffectGenerator> generators=new EffectsListBuilder().build(provider);
    for(SkillEffectGenerator generator : generators)
    {
      int level=getLevel();
      DisplayEffectsUtils.showEffectGenerator(ret,generator,false,level);
    }
    return ret;
  }

  protected void renderSpecifics(List<String> storage, T effect)
  {
    renderStats(storage,effect);
  }

  private void renderDuration(List<String> storage, T effect)
  {
    StatModifiersComputer statModsComputer=getContext().getStatModifiersComputer();
    float duration=EffectDisplayUtils.getDuration(effect,statModsComputer);
    if (duration>0)
    {
      String line="Duration: "+Duration.getShortDurationString(duration);
      storage.add(line);
      getState().setDurationDisplayed();
    }
  }

  protected void renderAfterDuration(List<String> storage, T effect)
  {
    // Nothing!
  }
}
