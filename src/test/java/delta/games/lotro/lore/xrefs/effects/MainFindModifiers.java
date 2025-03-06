package delta.games.lotro.lore.xrefs.effects;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.effects.AreaEffect;
import delta.games.lotro.common.effects.BubbleEffect;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.effects.InduceCombatStateEffect;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegaciesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacy;
import delta.games.lotro.lore.items.sets.ItemsSet;
import delta.games.lotro.lore.items.sets.ItemsSetsManager;
import delta.games.lotro.lore.items.sets.SetBonus;

/**
 * @author dmorcellet
 */
public class MainFindModifiers
{
  private void doIt()
  {
    for(Effect effect : EffectsManager.getInstance().getEffects())
    {
      System.out.println("Effect: "+effect);
      inspectEffect(effect);
    }
  }

  private void inspectEffect(Effect effect)
  {
    inspectShared(effect);
    if (effect instanceof AreaEffect)
    {
      inspectAreaEffect((AreaEffect)effect);
    }
    else if (effect instanceof BubbleEffect)
    {
      inspectBubbleEffect((BubbleEffect)effect);
    }
    else if (effect instanceof InduceCombatStateEffect)
    {
      inspectInduceCombatStateEffect((InduceCombatStateEffect)effect);
    }
  }

  private void inspectShared(Effect effect)
  {
    // Duration
    EffectDuration duration=effect.getEffectDuration();
    if (duration!=null)
    {
      ModPropertyList durationMods=duration.getDurationModifiers();
      useModifiers("DURATION",durationMods);
      ModPropertyList pulseCountMods=duration.getPulseCountModifiers();
      useModifiers("PULSE_COUNT",pulseCountMods);
    }
  }

  private void inspectAreaEffect(AreaEffect areaEffect)
  {
    ModPropertyList maxTargetsMods=areaEffect.getMaxTargetsModifiers();
    useModifiers("MAX_TARGETS",maxTargetsMods);
  }

  private void inspectBubbleEffect(BubbleEffect bubbleEffect)
  {
    ModPropertyList mods=bubbleEffect.getModifiers();
    useModifiers("BUBBLE_SIZE",mods);
  }

  private void inspectInduceCombatStateEffect(InduceCombatStateEffect induceCombatStateEffect)
  {
    ModPropertyList durationMods=induceCombatStateEffect.getDurationModifiers();
    useModifiers("STATE_DURATION",durationMods);
    ModPropertyList breakProbabilityMods=induceCombatStateEffect.getBreakOnVitalLossProbabilityModifiers();
    useModifiers("BREAK_PROBABILITY",breakProbabilityMods);
    ModPropertyList gracePeriodMods=induceCombatStateEffect.getGracePeriodModifiers();
    useModifiers("GRACE_PERIOD",gracePeriodMods);
  }

  private void useModifiers(String semantics, ModPropertyList modifiers)
  {
    if (modifiers==null)
    {
      return;
    }
    for(Integer propertyID : modifiers.getIDs())
    {
      StatsRegistry statsRegistry=StatsRegistry.getInstance();
      StatDescription stat=statsRegistry.getById(propertyID.intValue());
      if (stat!=null)
      {
        System.out.println("\t"+semantics+" => "+stat.getKey()+":"+stat.getInternalName());
        findPropertySources(stat);
      }
    }
  }

  private void findPropertySources(StatDescription stat)
  {
    // Non imbued legacies
    for(TieredNonImbuedLegacy legacy : NonImbuedLegaciesManager.getInstance().getTieredLegacies())
    {
      if (stat==legacy.getStat())
      {
        System.out.println("\t\tFound stat "+stat.getKey()+" in non imbued legacy: "+legacy);
      }
    }
    // Sets
    for(ItemsSet set : ItemsSetsManager.getInstance().getAll())
    {
      for(SetBonus bonus : set.getBonuses())
      {
        if (hasProperty(bonus.getStatsProvider(),stat))
        {
          System.out.println("\t\tFound stat "+stat.getKey()+" in bonus of set: "+set);
        }
      }
    }
    // Traits
    for(TraitDescription trait : TraitsManager.getInstance().getAll())
    {
      if (hasProperty(trait.getStatsProvider(),stat))
      {
        System.out.println("\t\tFound stat "+stat.getKey()+" in trait: "+trait);
      }
    }
    // Effects
    for(Effect effect : EffectsManager.getInstance().getEffects())
    {
      if (effect instanceof PropertyModificationEffect)
      {
        PropertyModificationEffect propModEffect=(PropertyModificationEffect)effect;
        if (hasProperty(propModEffect.getStatsProvider(),stat))
        {
          System.out.println("\t\tFound stat "+stat.getKey()+" in effect: "+effect);
        }
      }
    }
    // Items
    /*
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      if (hasProperty(item.getStatsProvider(),stat))
      {
        System.out.println("\t\tFound stat "+stat.getKey()+" in item: "+item);
      }
    }
    */
  }

  private boolean hasProperty(StatsProvider statsProvider, StatDescription stat)
  {
    if (statsProvider==null)
    {
      return false;
    }
    for(StatProvider statProvider : statsProvider.getStatProviders())
    {
      if (statProvider.getStat()==stat)
      {
        return true;
      }
    }
    return false;
  }

    // PIP effect:
    // - amount
    // Revive effect:
    // - percentage of vital at revive
    // Reactive vital effect: not managed in renderer?
    // Instant vital effect: amount
    // Vital over time effect: amount

    // Shared
    // - Stats providers: tons, including Property Modification effects

    // Skills:
    // - Geometry: max range
    // - PIP data:
    //     - value
    //     - required min
    //     - required max
    //     - change per interval
    //     - seconds per pip
    // - Attacks:
    //     - DPS
    //     - max damage
    //     - damage modifier
    // - Cooldown
    // - Max targets
    // - effects manager (8 types)
    //    - additive
    //    - override (single property ID)
    // - vital cost: morale, morale/s, power, power/s

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    new MainFindModifiers().doIt();
  }
}
