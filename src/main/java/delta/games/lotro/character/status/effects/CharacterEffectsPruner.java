package delta.games.lotro.character.status.effects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.stats.tomes.StatTome;
import delta.games.lotro.character.stats.tomes.StatTomesManager;
import delta.games.lotro.character.status.traits.raw.RawTraitsStatus;
import delta.games.lotro.character.traits.EffectAtRank;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.stats.StatDescription;

/**
 * Utility methods to filter out effects to avoid them being used twice in stats computations.
 * @author DAM
 */
public class CharacterEffectsPruner
{
  private Set<Effect> _effectsToIgnore;

  /**
   * Constructor.
   */
  public CharacterEffectsPruner()
  {
    _effectsToIgnore=new HashSet<Effect>();
  }

  /**
   * Set the traits.
   * @param traits Traits to use.
   */
  public void setTraits(RawTraitsStatus traits)
  {
    _effectsToIgnore.clear();
    Set<Effect> effects=getEffectsFromTraits(traits);
    _effectsToIgnore.addAll(effects);
  }

  /**
   * Use the given effect or not.
   * @param effectInstance Effect instance to test.
   * @return <code>true</code> to use it, <code>false</code> otherwise.
   */
  public boolean useEffect(EffectInstance effectInstance)
  {
    Effect effect=effectInstance.getEffect();
    int effectID=effect.getIdentifier();
    if (isVirtueEffect(effectID))
    {
      return false;
    }
    if (isStatTomeEffect(effectID))
    {
      return false;
    }
    // Traits
    if (_effectsToIgnore.contains(effect))
    {
      return false;
    }
    return true;
  }

  private boolean isVirtueEffect(int effectId)
  {
    for(VirtueDescription virtue : VirtuesManager.getInstance().getAll())
    {
      PropertyModificationEffect virtueEffect=virtue.getPassivesEffect();
      if (virtueEffect!=null)
      {
        if (virtueEffect.getIdentifier()==effectId)
        {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isStatTomeEffect(int effectId)
  {
    TraitsManager traitsMgr=TraitsManager.getInstance();
    StatTomesManager mgr=StatTomesManager.getInstance();
    for(StatDescription stat : mgr.getStats())
    {
      int nbRanks=mgr.getNbOfRanks(stat);
      for(int i=1;i<=nbRanks;i++)
      {
        StatTome tome=mgr.getStatTome(stat,i);
        int traitID=tome.getTraitId();
        TraitDescription trait=traitsMgr.getTrait(traitID);
        if (trait==null)
        {
          continue;
        }
        for(EffectGenerator generator : trait.getEffectGenerators())
        {
          int tomeEffect=generator.getEffect().getIdentifier();
          if (tomeEffect==effectId)
          {
            return true;
          }
        }
      }
    }
    return false;
  }

  private Set<Effect> getEffectsFromTraits(RawTraitsStatus status)
  {
    Set<Effect> ret=new HashSet<Effect>();
    TraitsManager traitsMgr=TraitsManager.getInstance();
    for(Integer traitID : status.getKnownTraits())
    {
      TraitDescription trait=traitsMgr.getTrait(traitID.intValue());
      int rank=status.getTraitRank(traitID.intValue());
      getEffectsFromTrait(trait,rank,ret);
    }
    return ret;
  }

  private void getEffectsFromTrait(TraitDescription trait, int rank, Set<Effect> effects)
  {
    for(EffectAtRank effectAtRank : trait.getEffects())
    {
      int traitRank=effectAtRank.getRank();
      if (traitRank==rank)
      {
        Effect effect=effectAtRank.getEffect();
        effects.add(effect);
      }
    }
    List<EffectGenerator> generators=trait.getEffectGenerators();
    if (!generators.isEmpty())
    {
      for(EffectGenerator generator : generators)
      {
        Effect effect=generator.getEffect();
        effects.add(effect);
      }
    }
  }
}
