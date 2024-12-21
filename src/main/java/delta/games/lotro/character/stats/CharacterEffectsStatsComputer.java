package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.status.effects.CharacterEffectsManager;
import delta.games.lotro.character.status.effects.CharacterEffectsPruner;
import delta.games.lotro.character.status.effects.EffectInstance;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.stats.SimpleStatComputerContext;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Character effects stats computer.
 * @author DAM
 */
public class CharacterEffectsStatsComputer
{
  /**
   * Compute stats contributions for character effects.
   * @param characterEffects Character effects to use.
   * @param level Character level.
   * @param referenceStats Stats to use for computations.
   * @return a possibly empty but never <code>null</code> list of stats contributions.
   */
  public static List<StatsContribution> getContributions(CharacterEffectsManager characterEffects, int level, BasicStatsSet referenceStats)
  {
    SimpleStatComputerContext context=new SimpleStatComputerContext(1,level);
    context.setStatValueProvider(new BasicStatsSetStatValueProvider(referenceStats));

    CharacterEffectsPruner pruner=new CharacterEffectsPruner();
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    for(EffectInstance effectInstance : characterEffects.getEffects())
    {
      Effect effect=effectInstance.getEffect();
      if (!(effect instanceof PropertyModificationEffect))
      {
        continue;
      }
      PropertyModificationEffect propertyModificationEffect=(PropertyModificationEffect)effect;
      boolean useEffect=pruner.useEffect(effectInstance);
      if (!useEffect)
      {
        continue;
      }
      StatsProvider statsProvider=propertyModificationEffect.getStatsProvider();
      BasicStatsSet stats=statsProvider.getStats(context);
      if (stats.getStatsCount()>0)
      {
        StatsContribution statsContrib=StatsContribution.getEffectContrib(effectInstance,stats);
        ret.add(statsContrib);
      }
    }
    return ret;
  }
}
