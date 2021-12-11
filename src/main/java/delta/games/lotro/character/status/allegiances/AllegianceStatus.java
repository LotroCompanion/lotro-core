package delta.games.lotro.character.status.allegiances;

import java.util.BitSet;

import delta.games.lotro.lore.allegiances.AllegianceDescription;
import delta.games.lotro.lore.allegiances.Points2LevelCurve;

/**
 * Allegiance status.
 * @author DAM
 */
public class AllegianceStatus
{
  private AllegianceDescription _allegiance;
  private int _pointsEarned;
  private BitSet _claimedRewards;
  private Points2LevelCurve _points2LevelCurve;

  /**
   * Constructor.
   * @param allegiance Associated allegiance.
   */
  public AllegianceStatus(AllegianceDescription allegiance)
  {
    if (allegiance==null)
    {
      throw new IllegalArgumentException("allegiance is null");
    }
    _allegiance=allegiance;
    _pointsEarned=0;
    _claimedRewards=new BitSet();
    _points2LevelCurve=null;
  }

  /**
   * Get the associated allegiance.
   * @return the associated allegiance.
   */
  public AllegianceDescription getAllegiance()
  {
    return _allegiance;
  }

  /**
   * Get the points earned.
   * @return a points count.
   */
  public int getPointsEarned()
  {
    return _pointsEarned;
  }

  /**
   * Set the points earned.
   * @param pointsEarned Value to set.
   */
  public void setPointsEarned(int pointsEarned)
  {
    _pointsEarned=pointsEarned;
  }

  /**
   * Get the claimed rewards bit set.
   * @return a bit set.
   */
  public BitSet getClaimedRewards()
  {
    return _claimedRewards;
  }

  /**
   * Set the claimed rewards flags.
   * @param flags Flags to set.
   */
  public void setClaimedRewardsFlags(int flags)
  {
    _claimedRewards.clear();
    int mask=1;
    int nbDeeds=_allegiance.getDeeds().size();
    for(int i=0;i<nbDeeds;i++)
    {
      if ((flags&mask)!=0)
      {
        _claimedRewards.set(i);
      }
      mask*=2;
    }
  }

  /**
   * Get the points to level curve.
   * @return a curve.
   */
  public Points2LevelCurve getPoints2LevelCurve()
  {
    return _points2LevelCurve;
  }

  /**
   * Set the points to level curve.
   * @param curve Curve to set.
   */
  public void setPoints2LevelCurve(Points2LevelCurve curve)
  {
    _points2LevelCurve=curve;
  }

  /**
   * Indicates if this allegiance is started or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isStarted()
  {
    return _points2LevelCurve!=null;
  }

  /**
   * Get the current level.
   * @return A level or <code>null</code> if not started.
   */
  public Integer getCurrentLevel()
  {
    if (_points2LevelCurve==null)
    {
      return null;
    }
    int level=_points2LevelCurve.getLevel(_pointsEarned);
    return Integer.valueOf(level);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int allegianceId=_allegiance.getIdentifier();
    sb.append("Allegiance ").append(_allegiance.getName()).append(" (").append(allegianceId).append("): ");
    if (isStarted())
    {
      Integer level=getCurrentLevel();
      int points=getPointsEarned();
      sb.append("level ").append(level).append(" (").append(points).append(')');
    }
    else
    {
      sb.append("not started");
    }
    return sb.toString();
  }
}
