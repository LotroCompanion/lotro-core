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
  private PipType _type;
  private PipAdjustmentType _adjustmentType;
  private boolean _reset;
  private int _amount;
  private ModPropertyList _amountModifiers;

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
   * Indicates if PIP is reset or not.
   * @return <code>true</code> to reset, <code>false</code> otherwise.
   */
  public boolean isReset()
  {
    return _reset;
  }

  /**
   * Set the 'reset' flag.
   * @param reset Value to set.
   */
  public void setReset(boolean reset)
  {
    _reset=reset;
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
