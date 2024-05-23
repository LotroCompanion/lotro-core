package delta.games.lotro.lore.pvp;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Rank.
 * @author DAM
 */
public class Rank implements Identifiable,Named
{
  private int _code;
  private String _label;

  /**
   * Constructor.
   * @param code Code.
   * @param label Label.
   */
  public Rank(int code, String label)
  {
    _code=code;
    _label=label;
  }

  @Override
  public int getIdentifier()
  {
    return getCode();
  }

  /**
   * Get the internal code.
   * @return the internal code.
   */
  public int getCode()
  {
    return _code;
  }

  @Override
  public String getName()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return _code+": "+_label;
  }
}
