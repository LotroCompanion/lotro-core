package delta.games.lotro.common.effects.display;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.ProcEffect;
import delta.games.lotro.common.enums.SkillType;

/**
 * Renderer for 'ProcEffect' effects.
 * @author DAM
 */
public class ProcEffectRenderer extends PropertyModificationEffectRenderer<ProcEffect>
{
  @Override
  protected void renderSpecifics(List<String> storage, ProcEffect effect)
  {
    super.renderStats(storage,effect);
    renderProc(storage,effect);
  }

  private void renderProc(List<String> storage, ProcEffect effect)
  {
    Float probability=effect.getProcProbability();
    List<SkillType> skillTypes=effect.getSkillTypes();
    List<EffectGenerator> procedEffects=effect.getProcedEffects();
    List<String> childStorage=new ArrayList<String>();
    showEffectGenerators(childStorage,procedEffects);
    String descriptionOverride=effect.getDescriptionOverride();
    if (descriptionOverride.isEmpty())
    {
      if (!childStorage.isEmpty())
      {
        String skillTypesStr=EffectDisplayUtils.formatSkillTypes(skillTypes);
        String text="On every "+skillTypesStr+" skill";
        if (probability!=null)
        {
          int percent=(int)(probability.floatValue()*100);
          String suffix=", "+percent+"% chance to";
          text=text+suffix;
        }
        storage.add(text);
        storage.addAll(childStorage);
      }
    }
    else
    {
      storage.addAll(childStorage);
    }
  }
}
