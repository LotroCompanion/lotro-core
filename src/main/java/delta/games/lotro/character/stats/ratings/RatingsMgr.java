package delta.games.lotro.character.stats.ratings;

import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ratings manager.
 * @author DAM
 */
public class RatingsMgr
{
  private static final Logger LOGGER=LoggerFactory.getLogger(RatingsMgr.class);

  private Map<RatingCurveId,RatingCurve> _curves;

  /**
   * Constructor.
   */
  public RatingsMgr()
  {
    _curves=new EnumMap<RatingCurveId,RatingCurve>(RatingCurveId.class);
  }

  /**
   * Get the curve for the given identifier.
   * @param id Curve identifier.
   * @return A curve or <code>null</code> if not found.
   */
  public RatingCurve getCurve(RatingCurveId id)
  {
    RatingCurve curve=_curves.get(id);
    if (curve==null)
    {
      LOGGER.warn("Curve not found: "+id);
    }
    return curve;
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
