package delta.games.lotro.kinship;

/**
 * Kinship member rank.
 * @author DAM
 */
public class KinshipRank
{
  private int _code;
  private String _name;

  /**
   * Constructor.
   * @param code Code.
   * @param name Name.
   */
  public KinshipRank(int code, String name)
  {
    _code=code;
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
    return "Rank: code="+_code+", name="+_name;
  }
}
