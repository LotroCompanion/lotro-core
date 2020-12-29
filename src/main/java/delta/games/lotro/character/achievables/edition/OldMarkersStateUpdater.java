package delta.games.lotro.character.achievables.edition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.achievables.AchievableStatus;
import delta.games.lotro.lore.quests.geo.AchievableGeoPoint;

/**
 * Loads geo deed status from old completed 'marker IDs'. 
 * @author DAM
 */
public class OldMarkersStateUpdater
{
  /**
   * Update a deed status using the given set of completed points.
   * @param status Status to update.
   * @param pointIds Identifiers of the completed points.
   */
  public static void updateDeedStatus(AchievableStatus status, Set<Integer> pointIds)
  {
    AchievableGeoStatusManager statusMgr=new AchievableGeoStatusManager(status,null);
    List<AchievableStatusGeoItem> completedPoints=getCompletedPoints(statusMgr,pointIds);
    for(AchievableStatusGeoItem point : completedPoints)
    {
      statusMgr.handlePointChange(point,true);
    }
  }

  private static List<AchievableStatusGeoItem> getCompletedPoints(AchievableGeoStatusManager statusMgr, Set<Integer> pointIds)
  {
    List<AchievableStatusGeoItem> ret=new ArrayList<AchievableStatusGeoItem>();
    List<AchievableStatusGeoItem> points=statusMgr.getPoints();
    for(AchievableStatusGeoItem point : points)
    {
      AchievableGeoPoint geoPoint=point.getPoint();
      Integer markerId=geoPoint.getOldMarkerId();
      if ((markerId!=null) && (pointIds.contains(markerId)))
      {
        ret.add(point);
      }
    }
    return ret;
  }
}
