package delta.games.lotro.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Provide light-weight, shareable challenge level representation.
 * @author DAM
 */
public class ChallengeLevel
{
  private static Map<Integer,ChallengeLevel> _mapByCode=new HashMap<Integer,ChallengeLevel>();

  /**
   * Level 1.
   */
  public static final ChallengeLevel ONE=getByCode(1);
  /**
   * Character level.
   */
  public static final ChallengeLevel CHARACTER_LEVEL=new ChallengeLevel(-1,0,"Character Level");
  /**
   * Skirmish level.
   */
  public static final ChallengeLevel SKIRMISH_LEVEL=new ChallengeLevel(-2,0,"Skirmish Level");
  private int _code;
  private int _level;
  private String _label;

  /**
   * Private constructor.
   * @param code Internal code.
   * @param level Effective level.
   * @param label Displayable label.
   */
  private ChallengeLevel(int code, int level, String label)
  {
    _code=code;
    _level=level;
    _label=label;
    _mapByCode.put(Integer.valueOf(code),this);
  }

  /**
   * Get a challenge level using its internal code.
   * @param code Code to use (>=1:level,-1:character level, -2:skirmish level).
   * @return A challenge level instance (immutable, shared).
   */
  public static ChallengeLevel getByCode(int code)
  {
    ChallengeLevel challengeLevel=_mapByCode.get(Integer.valueOf(code));
    if (challengeLevel==null)
    {
      String label=String.valueOf(code);
      challengeLevel=new ChallengeLevel(code,code,label);
    }
    return challengeLevel;
  }

  /**
   * Get the internal code for this repeatability.
   * @return A code.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the displayable label for this repeatability.
   * @return A displayable label.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Get the effective level for this challenge.
   * @return A level or <code>0</code> to indicate level cap.
   */
  public int getEffectiveLevel()
  {
    return _level;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
