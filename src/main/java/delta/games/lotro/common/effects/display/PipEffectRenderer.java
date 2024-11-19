package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.EffectFlags;
import delta.games.lotro.common.effects.PipEffect;
import delta.games.lotro.common.enums.PipAdjustmentType;
import delta.games.lotro.common.enums.PipType;

/**
 * Renderer for 'PipEffect' effects.
 * @author DAM
 */
public class PipEffectRenderer extends AbstractSingleEffectRenderer<PipEffect>
{
  @Override
  public void render(List<String> storage, PipEffect effect)
  {
    boolean autoExamination=effect.getBaseFlag(EffectFlags.AUTO_EXAMINATION);
    if (!autoExamination)
    {
      return;
    }
    boolean reset=effect.isReset();
    if (reset)
    {
      // TODO
    }
    else
    {
      PipType type=effect.getType();
      int amount=effect.getAmount();
      amount+=computeAdditiveModifiers(effect.getAmountModifiers());
      PipAdjustmentType natural=effect.getAdjustmentType();
      if (natural.getCode()==2) // Natural
      {
        String label=((amount>0)?"+":"")+amount+" "+type.getLabel();
        storage.add(label);
      }
      else
      {
        // TODO
      }
    }
  }
}
