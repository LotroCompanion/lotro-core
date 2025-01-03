package delta.games.lotro.lore.xrefs.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.effects.ParentEffect;
import delta.games.lotro.lore.xrefs.Reference;

/**
 * Finds references to effects.
 * @author DAM
 */
public class EffectReferencesBuilder
{
  private List<Reference<?,EffectRole>> _storage;

  /**
   * Constructor.
   */
  public EffectReferencesBuilder()
  {
    _storage=new ArrayList<Reference<?,EffectRole>>();
  }

  /**
   * Search for an effect.
   * @param effectID Effect identifier.
   * @return the found references.
   */
  public List<Reference<?,EffectRole>> inspectEffect(int effectID)
  {
    _storage.clear();
    findInEffects(effectID);
    //findInSkills(effectID);
    //findInTraits(effectID);
    List<Reference<?,EffectRole>> ret=new ArrayList<Reference<?,EffectRole>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInEffects(int effectID)
  {
    EffectsManager mgr=EffectsManager.getInstance();
    Effect mainEffect=mgr.getEffectById(effectID);
    for(Effect e : mgr.getEffects())
    {
      if (e instanceof ParentEffect)
      {
        ParentEffect pe=(ParentEffect)e;
        if (pe.getChildEffects().contains(mainEffect))
        {
          _storage.add(new Reference<Effect,EffectRole>(e,EffectRole.PARENT_EFFECT));
        }
      }
    }
  }
}
