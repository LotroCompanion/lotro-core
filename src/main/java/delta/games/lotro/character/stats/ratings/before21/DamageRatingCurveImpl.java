package delta.games.lotro.character.stats.ratings.before21;

import delta.games.lotro.character.stats.ratings.RatingCurve;

/**
 * Linear rating/percentage curve for damage.
 * @author DAM
 */
public class DamageRatingCurveImpl implements RatingCurve
{
  public Double getRatingForCap(int level)
  {
    // TODO This is for level>100 only
    return Double.valueOf(400/0.0027);
  }

  /**
   * Get the percentage for a given rating and level.
   * @param rating Rating.
   * @param level Level.
   * @return A percentage or <code>null</code> if not available.
   */
  public Double getPercentage(double rating, int level)
  {
    double ret;
    if (level<=20) ret=Math.min(rating*0.0146,400);
    else if (level<=30) ret=Math.min(rating*((level*-0.00048)+0.0242),400);
    else if (level<=40) ret=Math.min(rating*((level*-0.00024)+0.017),400);
    else if (level<=50) ret=Math.min(rating*((level*-0.00015)+0.0134),400);
    else if (level<=60) ret=Math.min(rating*((level*-0.00011)+0.0114),400);
    else if (level<=70) ret=Math.min(rating*((level*-0.00009)+0.0102),400);
    else if (level<=80) ret=Math.min(rating*((level*-0.00005)+0.0074),400);
    else if (level<=90) ret=Math.min(rating*((level*-0.00004)+0.0066),400);
    else if (level<=100) ret=Math.min(rating*((level*-0.00003)+0.0057),400);
    else ret=Math.min(rating*0.0027,400);
    return Double.valueOf(ret);
  }
}
