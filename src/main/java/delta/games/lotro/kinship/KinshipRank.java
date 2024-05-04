package delta.games.lotro.kinship;

import delta.games.lotro.common.Named;

/**
 * Kinship member rank.
 * @author DAM
 */
public class KinshipRank implements Named
{
  private int _code;
  private int _level;
  private String _name;

  /**
   * Constructor.
   * @param code Code.
   * @param level Level (lower values mean higher rank).
   * @param name Name.
   */
  public KinshipRank(int code, int level, String name)
  {
    _code=code;
    _level=level;
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Get the internal code.
   * @return a code.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the level.
   * @return a level.
   */
  public int getLevel()
  {
    return _level;
  }

  /**
   * Get the rank name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  @Override
  public String toString()
  {
    return "Rank: code="+_code+", level="+_level+", name="+_name;
  }
}
