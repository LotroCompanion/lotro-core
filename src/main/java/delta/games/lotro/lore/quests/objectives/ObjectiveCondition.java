package delta.games.lotro.lore.quests.objectives;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.quests.geo.AchievableGeoPoint;

/**
 * Condition of an objective.
 * @author DAM
 */
public abstract class ObjectiveCondition
{
  private int _index;
  private boolean _showProgressText;
  private boolean _showBillboardText;
  private String _loreInfo;
  private String _progressOverride;
  private List<AchievableGeoPoint> _points;
  private int _count;

  /**
   * Constructor.
   */
  public ObjectiveCondition()
  {
    _index=1;
    _showProgressText=true;
    _showBillboardText=true;
    _points=new ArrayList<AchievableGeoPoint>();
    _count=1;
  }

  /**
   * Get the objective index, starting at 1.
   * @return An index.
   */
  public int getIndex()
  {
    return _index;
  }

  /**
   * Set the objective index.
   * @param index Index to set.
   */
  public void setIndex(int index)
  {
    _index=index;
  }

  /**
   * Indicats if the progress text is to be displayed or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isShowProgressText()
  {
    return _showProgressText;
  }

  /**
   * Set the 'show progress text' flag.
   * @param showProgressText Value to set.
   */
  public void setShowProgressText(boolean showProgressText)
  {
    _showProgressText=showProgressText;
  }

  /**
   * Indicats if the billboard text is to be displayed or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isShowBillboardText()
  {
    return _showBillboardText;
  }

  /**
   * Set the 'show billboard text' flag.
   * @param showBillboardText Value to set.
   */
  public void setShowBillboardText(boolean showBillboardText)
  {
    _showBillboardText=showBillboardText;
  }

  /**
   * Get lore info.
   * @return a lore info string or <code>null</code>.
   */
  public String getLoreInfo()
  {
    return _loreInfo;
  }

  /**
   * Set the lore info string.
   * @param loreInfo the lore info to set.
   */
  public void setLoreInfo(String loreInfo)
  {
    _loreInfo=loreInfo;
  }

  /**
   * Get the progress override string.
   * @return a progress override string or <code>null</code>.
   */
  public String getProgressOverride()
  {
    return _progressOverride;
  }

  /**
   * Set the progress override string.
   * @param progressOverride the progress override to set.
   */
  public void setProgressOverride(String progressOverride)
  {
    _progressOverride=progressOverride;
  }

  /**
   * Get the condition type.
   * @return a condition type.
   */
  public abstract ConditionType getType();

  /**
   * Get the expected count.
   * @return a count (default 1).
   */
  public int getCount()
  {
    return _count;
  }

  /**
   * Set the expected count.
   * @param count Count to set.
   */
  public void setCount(int count)
  {
    _count=count;
  }

  /**
   * Add a geo point.
   * @param point Point to add.
   */
  public void addPoint(AchievableGeoPoint point)
  {
    _points.add(point);
  }

  /**
   * Remove all points.
   */
  public void removeAllPoints()
  {
    _points.clear();
  }

  /**
   * Get the geo points.
   * @return a list of geo points.
   */
  public List<AchievableGeoPoint> getPoints()
  {
    return _points;
  }

  /**
   * Indicates if this objective has geo data.
   * @return <code>true</code> if it has geo data.
   */
  public boolean hasGeoData()
  {
    return _points.size()>0;
  }
}
