package delta.games.lotro.character.deeds;

import java.util.Date;

import delta.games.lotro.character.deeds.geo.DeedGeoStatus;

/**
 * Status of a single deed for a single character.
 * @author DAM
 */
public class DeedStatus
{
  private String _deedKey;
  private boolean _completed;
  private Long _completionDate;
  private DeedGeoStatus _geoStatus;

  /**
   * Constructor.
   * @param deedKey Key of the associated deed.
   */
  public DeedStatus(String deedKey)
  {
    _deedKey=deedKey;
    _geoStatus=null;
  }

  /**
   * Get the key of the associated deed.
   * @return a deed key.
   */
  public String getDeedKey()
  {
    return _deedKey;
  }

  /**
   * Indicates if this deed is completed, or not.
   * @return A boolean.
   */
  public boolean isCompleted()
  {
    return _completed;
  }

  /**
   * Set the completion status.
   * @param completed Completion status to set.
   */
  public void setCompleted(boolean completed)
  {
    _completed=completed;
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
    if ((_completionDate!=null) || (_completed))
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
    sb.append("Deed '").append(_deedKey).append("': ");
    if (_completed)
    {
      sb.append("completed");
      if (_completionDate!=null)
      {
        sb.append(" (").append(new Date(_completionDate.longValue())).append(')');
      }
    }
    else
    {
      sb.append("NOT completed");
    }
    if (_geoStatus!=null)
    {
      sb.append(" ; ").append(_geoStatus);
    }
    return sb.toString();
  }
}
