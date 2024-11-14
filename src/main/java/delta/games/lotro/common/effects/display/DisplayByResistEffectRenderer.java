package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.DispelByResistEffect;
import delta.games.lotro.common.enums.ResistCategory;

/**
 * Renderer for 'DispelByResistEffect' effects.
 * @author DAM
 */
public class DisplayByResistEffectRenderer extends AbstractSingleEffectRenderer<DispelByResistEffect>
{
  @Override
  public void render(List<String> storage, DispelByResistEffect effect)
  {
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      return;
    }
    int count=effect.getMaxDispelCount();
    List<ResistCategory> categories=effect.getResistCategories();
    boolean useStrengthRestriction=effect.useStrengthRestriction();
    String effects=((count<0) || (count>1))?"effects":"effect";
    String categoriesStr=EffectDisplayUtils.formatResistCategories(categories);
    String label="Removes "+((count<0)?"all":"up to "+count)+" "+categoriesStr+" "+effects;
    if (useStrengthRestriction)
    {
      // TODO Use raw spellcraft if any
      Integer strengthOffset=effect.getStrengthOffset();
      int delta=(strengthOffset!=null)?strengthOffset.intValue():4;
      int strength=getLevel()+delta;
      String complement=" with maximum strength of "+strength;
      label=label+complement;
    }
    label+=" from the target.";
    storage.add(label);
  }
}

