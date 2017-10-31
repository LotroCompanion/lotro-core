package delta.games.lotro.common;

/**
 * Virtue identifiers.
 * @author DAM
 */
public enum VirtueId {
  /**
   * Charity.
   */
  CHARITY,
  /**
   * Compassion.
   */
  COMPASSION,
  /**
   * Confidence.
   */
  CONFIDENCE,
  /**
   * Determination.
   */
  DETERMINATION,
  /**
   * Discipline.
   */
  DISCIPLINE,
  /**
   * Empathy.
   */
  EMPATHY,
  /**
   * Fidelity.
   */
  FIDELITY,
  /**
   * Fortitude.
   */
  FORTITUDE,
  /**
   * Hosnesty.
   */
  HONESTY,
  /**
   * Honour.
   */
  HONOUR,
  /**
   * Idealism.
   */
  IDEALISM,
  /**
   * Justice.
   */
  JUSTICE,
  /**
   * Loyalty.
   */
  LOYALTY,
  /**
   * Mercy.
   */
  MERCY,
  /**
   * Patience.
   */
  PATIENCE,
  /**
   * Innocence.
   */
  INNOCENCE,
  /**
   * Tolerance.
   */
  TOLERANCE,
  /**
   * Valour.
   */
  VALOUR,
  /**
   * Wisdom.
   */
  WISDOM,
  /**
   * Zeal.
   */
  ZEAL;

  private String _label;

  private VirtueId()
  {
    String virtueLabel=name().toLowerCase();
    virtueLabel=virtueLabel.substring(0,1).toUpperCase()+virtueLabel.substring(1);
    _label=virtueLabel;
  }

  /**
   * Get a displayable label for this virtue.
   * @return a label.
   */
  public String getLabel()
  {
    return _label;
  }
}
