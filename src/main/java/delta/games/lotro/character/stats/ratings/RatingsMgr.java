package delta.games.lotro.character.stats.ratings;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.character.stats.ratings.before21.RatingsInitializerBeforeUpdate21;
import delta.games.lotro.character.stats.ratings.update21.RatingsInitializerUpdate21;

/**
 * Ratings manager.
 * @author DAM
 */
public class RatingsMgr
{
  private Map<RatingCurveId,RatingCurve> _curves;

  /**
   * Constructor.
   */
  public RatingsMgr()
  {
    _curves=new HashMap<RatingCurveId,RatingCurve>();
    RatingsInitializerUpdate21.init(this);
  }

  /**
   * Get the curve for the given identifier.
   * @param id Curve identifier.
   * @return A curve or <code>null</code> if not found.
   */
  public RatingCurve getCurve(RatingCurveId id)
  {
    return _curves.get(id);
  }

  /**
   * Set the curve for the given identifier.
   * @param id Curve identifier.
   * @param curve Curve to set.
   */
  public void setCurve(RatingCurveId id, RatingCurve curve)
  {
    _curves.put(id,curve);
  }
}
