package delta.games.lotro.character.titles;

import java.util.Date;

import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Title status.
 * @author DAM
 */
public class TitleStatus
{
  private TitleDescription _title;
  private boolean _acquired;
  private Long _acquisitionDate;
  private Double _acquisitionTimestamp;

  /**
   * Constructor.
   * @param title Associated title.
   */
  public TitleStatus(TitleDescription title)
  {
    if (title==null)
    {
      throw new IllegalArgumentException("title is null");
    }
    _title=title;
    _acquisitionDate=null;
    _acquisitionTimestamp=null;
  }

  /**
   * Get the associated title.
   * @return the associated title.
   */
  public TitleDescription getTitle()
  {
    return _title;
  }

  /**
   * Get the identifier of the managed title.
   * @return the identifier of the managed title.
   */
  public int getTitleId()
  {
    return _title.getIdentifier();
  }

  /**
   * Indicates if this title is acquired or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isAcquired()
  {
    return _acquired;
  }

  /**
   * Set the 'acquired' flag.
   * @param acquired Value to set.
   */
  public void setAcquired(boolean acquired)
  {
    _acquired=acquired;
  }

  /**
   * Get the completion date.
   * @return A date or <code>null</code> if completion date is not known.
   */
  public Long getAcquisitionDate()
  {
    return _acquisitionDate;
  }

  /**
   * Set the acquisition date.
   * @param acquisitionDate Acquisition date to set.
   */
  public void setAcquisitionDate(Long acquisitionDate)
  {
    _acquisitionDate=acquisitionDate;
  }

  /**
   * Get the acquisition timestamp.
   * @return A timestamp or <code>null</code> if not set.
   */
  public Double getAcquisitionTimeStamp()
  {
    return _acquisitionTimestamp;
  }

  /**
   * Set the acquisition timestamp.
   * @param acquisitionTimestamp Acquisition timestamp to set.
   */
  public void setAcquisitionTimeStamp(Double acquisitionTimestamp)
  {
    _acquisitionTimestamp=acquisitionTimestamp;
  }
  
  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int titleId=_title.getIdentifier();
    sb.append("Title ").append(_title.getName()).append(" (").append(titleId).append("): ");
    if (_acquired)
    {
      sb.append("acquired");
      if (_acquisitionDate!=null)
      {
        sb.append(" (").append(new Date(_acquisitionDate.longValue())).append(')');
      }
      if (_acquisitionTimestamp!=null)
      {
        sb.append(" (").append(_acquisitionTimestamp).append(')');
      }
    }
    else
    {
      sb.append("not acquired");
    }
    return sb.toString();
  }
}
