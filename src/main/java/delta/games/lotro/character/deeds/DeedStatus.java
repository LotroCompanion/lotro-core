package delta.games.lotro.character.deeds;

import java.util.Date;

import delta.games.lotro.character.deeds.geo.DeedGeoStatus;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Status of a single deed for a single character.
 * @author DAM
 */
public class DeedStatus extends AchievableStatus
{
  private Long _completionDate;
  private DeedGeoStatus _geoStatus;

  /**
   * Constructor.
   * @param deed Associated deed.
   */
  public DeedStatus(DeedDescription deed)
  {
    super(deed);
    _geoStatus=null;
  }

  /**
   * Get the completion date.
   * @return A date or <code>null</code> if not completed or completion date is not known.
   */
  public Long getCompletionDate()
  {
    return _completionDate;
  }

  /**
   * Set the completion date.
   * @param completionDate Completion date to set.
   */
  public void setCompletionDate(Long completionDate)
  {
    _completionDate=completionDate;
  }

  /**
   * Get the geographic status of this deed, if any.
   * @return A geo status or <code>null</code>.
   */
  public DeedGeoStatus getGeoStatus()
  {
    return _geoStatus;
  }

  /**
   * Set the geographic status of this deed.
   * @param geoStatus A geo status or <code>null</code>.
   */
  public void setGeoStatus(DeedGeoStatus geoStatus)
  {
    _geoStatus=geoStatus;
  }

  /**
   * Indicates if this deed status is empty (contains no data).
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEmpty()
  {
    if ((_completionDate!=null) || (getState()!=AchievableElementState.UNDEFINED))
    {
      return false;
    }
    if ((_geoStatus==null) || (_geoStatus.isEmpty()))
    {
      return true;
    }
    return false;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int achievableId=getAchievableId();
    sb.append("Deed ").append(achievableId).append(" (").append(getAchievable().getName()).append("): ");
    sb.append(getState());
    if (_completionDate!=null)
    {
      sb.append(" (").append(new Date(_completionDate.longValue())).append(')');
    }
    if (_geoStatus!=null)
    {
      sb.append(" ; ").append(_geoStatus);
    }
    return sb.toString();
  }
}
