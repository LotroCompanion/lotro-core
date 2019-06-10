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
   * Innocence.
   */
  INNOCENCE,
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
   * Wit.
   */
  WIT,
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
