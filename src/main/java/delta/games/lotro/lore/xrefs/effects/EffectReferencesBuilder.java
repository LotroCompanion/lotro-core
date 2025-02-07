package delta.games.lotro.lore.xrefs.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectsUtils;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.traits.EffectAtRank;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
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
    EffectsManager mgr=EffectsManager.getInstance();
    Effect mainEffect=mgr.getEffectById(effectID);
    findInEffects(mainEffect);
    findInSkills(mainEffect);
    findInTraits(mainEffect);
    List<Reference<?,EffectRole>> ret=new ArrayList<Reference<?,EffectRole>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInEffects(Effect mainEffect)
  {
    EffectsManager mgr=EffectsManager.getInstance();
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

  private void findInSkills(Effect mainEffect)
  {
    SkillsManager skillsMgr=SkillsManager.getInstance();
    for(SkillDescription skill : skillsMgr.getAll())
    {
      inspectSkill(skill,mainEffect);
    }
  }

  private void inspectSkill(SkillDescription skill, Effect effect)
  {
    List<SkillEffectGenerator> generators=SkillEffectsUtils.getAllEffects(skill);
    if (!generators.isEmpty())
    {
      for(SkillEffectGenerator generator : generators)
      {
        if (effect==generator.getEffect())
        {
          _storage.add(new Reference<SkillDescription,EffectRole>(skill,EffectRole.SKILL_USED_BY));
        }
      }
    }
  }

  private void findInTraits(Effect mainEffect)
  {
    TraitsManager traitsMgr=TraitsManager.getInstance();
    for(TraitDescription trait : traitsMgr.getAll())
    {
      inspectTrait(trait,mainEffect);
    }
  }

  private void inspectTrait(TraitDescription trait, Effect effect)
  {
    boolean gotIt=false;
    List<EffectGenerator> generators=trait.getEffectGenerators();
    if (!generators.isEmpty())
    {
      for(EffectGenerator generator : generators)
      {
        if (effect==generator.getEffect())
        {
          gotIt=true;
          break;
        }
      }
    }
    if (!gotIt)
    {
      for(EffectAtRank effectAtRank : trait.getEffects())
      {
        if (effect==effectAtRank.getEffect())
        {
          gotIt=true;
          break;
        }
      }
    }
    if (gotIt)
    {
      _storage.add(new Reference<TraitDescription,EffectRole>(trait,EffectRole.TRAIT_USED_BY));
    }
  }
}
