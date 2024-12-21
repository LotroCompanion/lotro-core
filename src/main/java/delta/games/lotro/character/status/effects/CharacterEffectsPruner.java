package delta.games.lotro.character.status.effects;

import delta.games.lotro.character.stats.tomes.StatTome;
import delta.games.lotro.character.stats.tomes.StatTomesManager;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.stats.StatDescription;

/**
 * Utility methods to filter out effects to avoid them being used twice in stats computations.
 * @author DAM
 */
public class CharacterEffectsPruner
{
  /**
   * Use the given effect or not.
   * @param effectInstance Effect instance to test.
   * @return <code>true</code> to use it, <code>false</code> otherwise.
   */
  public boolean useEffect(EffectInstance effectInstance)
  {
    int effectID=effectInstance.getEffect().getIdentifier();
    if (isVirtueEffect(effectID))
    {
      return false;
    }
    if (isStatTomeEffect(effectID))
    {
      return false;
    }
    // ...
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
}
