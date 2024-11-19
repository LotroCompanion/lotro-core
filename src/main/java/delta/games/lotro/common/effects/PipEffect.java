package delta.games.lotro.common.effects;

import delta.games.lotro.common.enums.PipAdjustmentType;
import delta.games.lotro.common.enums.PipType;
import delta.games.lotro.common.properties.ModPropertyList;

/**
 * PIP effect.
 * @author DAM
 */
public class PipEffect extends InstantEffect
{
  private PipType _type; // From Effect_Pip_Type
  private PipAdjustmentType _adjustmentType; // From Effect_Pip_AdjustmentType
  private int _amount; // From Effect_Pip_AdjustmentAmount
  private ModPropertyList _amountModifiers; // From Effect_Pip_AdjustmentAmount_AdditiveModifiers

  /**
   * Constructor.
   */
  public PipEffect()
  {
    super();
  }

  /**
   * Get the PIP type.
   * @return a PIP type.
   */
  public PipType getType()
  {
    return _type;
  }

  /**
   * Set the PIP type.
   * @param type the PIP type to set.
   */
  public void setType(PipType type)
  {
    _type=type;
  }

  /**
   * Get the adjustment type.
   * @return the adjustment type.
   */
  public PipAdjustmentType getAdjustmentType()
  {
    return _adjustmentType;
  }

  /**
   * Set the adjustment type.
   * @param adjustmentType the adjustment type to set.
   */
  public void setAdjustmentType(PipAdjustmentType adjustmentType)
  {
    _adjustmentType=adjustmentType;
  }

  /**
   * Get the amount.
   * @return the amount.
   */
  public int getAmount()
  {
    return _amount;
  }

  /**
   * Set the amount.
   * @param amount the amount to set.
   */
  public void setAmount(int amount)
  {
    _amount=amount;
  }

  /**
   * Get the amount modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getAmountModifiers()
  {
    return _amountModifiers;
  }

  /**
   * Set the amount modifiers.
   * @param amountModifiers the modifiers to set (may be <code>null</code>).
   */
  public void setAmountModifiers(ModPropertyList amountModifiers)
  {
    _amountModifiers=amountModifiers;
  }
}
