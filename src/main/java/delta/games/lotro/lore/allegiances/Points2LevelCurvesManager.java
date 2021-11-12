package delta.games.lotro.lore.allegiances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages a set of 'points to level' curves.
 * @author DAM
 */
public class Points2LevelCurvesManager
{
  private Map<Integer,Points2LevelCurve> _curves;

  /**
   * Constructor.
   */
  public Points2LevelCurvesManager()
  {
    _curves=new HashMap<Integer,Points2LevelCurve>();
  }

  /**
   * Get the curve identifiers.
   * @return A sorted list of identifiers.
   */
  public List<Integer> getCurveIdentifiers()
  {
    List<Integer> ret=new ArrayList<Integer>(_curves.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get a curve using its identifier.
   * @param id Identifier.
   * @return A curve or <code>null</code> if not found.
   */
  public Points2LevelCurve getCurve(int id)
  {
    return _curves.get(Integer.valueOf(id));
  }

  /**
   * Add a curve.
   * @param curve Curve to add.
   */
  public void addCurve(Points2LevelCurve curve)
  {
    Integer key=Integer.valueOf(curve.getIdentifier());
    _curves.put(key,curve);
  }
}
