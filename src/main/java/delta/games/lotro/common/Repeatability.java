package delta.games.lotro.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Provide light-weight, shareable repeatability representation.
 * @author DAM
 */
public class Repeatability
{
  private static Map<Byte,Repeatability> _mapByCode=new HashMap<Byte,Repeatability>();

  /**
   * Infinitely repeatable.
   */
  public static Repeatability INFINITELY_REPEATABLE=new Repeatability((byte)-1,"Repeatable");
  /**
   * Not repeatable.
   */
  public static Repeatability NOT_REPEATABLE=new Repeatability((byte)0,"Not repeatable");
  private byte _code;
  private String _label;

  /**
   * Private constructor.
   * @param code Internal code.
   * @param label
   */
  private Repeatability(byte code, String label)
  {
    _code=code;
    _label=label;
    _mapByCode.put(Byte.valueOf(code),this);
  }

  /**
   * Get a repeatability instance using its internal code.
   * @param code Code to use (0=not repeatable, -1: infinitely repeatable, N>1: repeatable N times).
   * @return A repeatability instance (immutable, shared).
   */
  public static Repeatability getByCode(byte code)
  {
    Repeatability repeatability=_mapByCode.get(Byte.valueOf(code));
    if (repeatability==null)
    {
      String label=code+" time"+((code>1)?"s":"");
      repeatability=new Repeatability(code,label);
    }
    return repeatability;
  }

  /**
   * Get the internal code for this repeatability.
   * @return A byte code.
   */
  public byte getCode()
  {
    return _code;
  }

  /**
   * Indicates if this repeatability is repeatable or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isRepeatable()
  {
    return (_code!=0);
  }

  /**
   * Get the displayable label for this repeatability.
   * @return A displayable label.
   */
  public String getLabel()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
