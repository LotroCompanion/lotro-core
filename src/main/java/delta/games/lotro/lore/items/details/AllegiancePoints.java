package delta.games.lotro.lore.items.details;

import delta.games.lotro.common.enums.AllegianceGroup;

/**
 * Allegiance points.
 * @author DAM
 */
public class AllegiancePoints extends ItemDetail
{
  private int _points;
  private AllegianceGroup _group;

  /**
   * Constructor.
   * @param group Allegiance group.
   * @param points Points.
   */
  public AllegiancePoints(AllegianceGroup group, int points)
  {
    _group=group;
    _points=points;
  }

  /**
   * Get the allegiance group.
   * @return the allegiance group.
   */
  public AllegianceGroup getGroup()
  {
    return _group;
  }

  /**
   * Get the points.
   * @return a points count.
   */
  public int getPoints()
  {
    return _points;
  }
}
