package delta.games.lotro.lore.allegiances;

import delta.games.lotro.common.Identifiable;

/**
 * Points to level curve.
 * @author DAM
 */
public class Points2LevelCurve implements Identifiable
{
  private int _identifier;
  private int[] _points2Level;

  /**
   * Constructor.
   * @param identifier Identifier.
   * @param points2Level Points for level changes.
   */
  public Points2LevelCurve(int identifier, int[] points2Level)
  {
    _identifier=identifier;
    _points2Level=points2Level;
  }

  @Override
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the level for the given points.
   * @param points Points to use.
   * @return A level.
   */
  public int getLevel(int points)
  {
    int ret=0;
    for(int i=0;i<_points2Level.length;i++)
    {
      if (points>=_points2Level[i])
      {
        ret=i;
      }
      else
      {
        break;
      }
    }
    return ret;
  }

  /**
   * Get the minimum number of points for the given level.
   * @param level Level to use.
   * @return A number of points.
   */
  public int getMinPointsForLevel(int level)
  {
    if (level>=_points2Level.length)
    {
      return -1;
    }
    return _points2Level[level];
  }

  /**
   * Get the number of points to reach the maximum level.
   * @return A number of points.
   */
  public int getMaxPoints()
  {
    return _points2Level[_points2Level.length-1];
  }

  /**
   * Get the maximum level.
   * @return A level.
   */
  public int getMaxLevel()
  {
    return _points2Level.length-1;
  }
}
