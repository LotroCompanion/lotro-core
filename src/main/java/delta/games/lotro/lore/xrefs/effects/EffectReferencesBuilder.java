package delta.games.lotro.lore.xrefs.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectsUtils;
import delta.games.lotro.character.skills.SkillsManager;
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
    EffectsManager mgr=EffectsManager.getInstance();
    Effect mainEffect=mgr.getEffectById(effectID);
    findInEffects(mainEffect);
    findInSkills(mainEffect);
    //findInTraits(effectID);
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
}