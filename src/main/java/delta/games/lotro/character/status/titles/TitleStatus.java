package delta.games.lotro.character.status.titles;

import java.util.Date;

import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Title status.
 * @author DAM
 */
public class TitleStatus
{
  private TitleDescription _title;
  private TitleState _state;
  private Long _acquisitionDate;
  private Double _acquisitionTimestamp;
  private Integer _acquisitionOrder;

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
    _state=TitleState.UNDEFINED;
    _acquisitionDate=null;
    _acquisitionTimestamp=null;
    _acquisitionOrder=null;
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
   * Get the title state.
   * @return the title state.
   */
  public TitleState getState()
  {
    return _state;
  }

  /**
   * Set the title state.
   * @param state State to set.
   */
  public void setState(TitleState state)
  {
    _state=state;
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

  /**
   * Get the acquisition order.
   * @return An index or <code>null</code> if not set.
   */
  public Integer getAcquisitionOrder()
  {
    return _acquisitionOrder;
  }

  /**
   * Set the acquisition order.
   * @param acquisitionOrder Acquisition order to set.
   */
  public void setAcquisitionOrder(Integer acquisitionOrder)
  {
    _acquisitionOrder=acquisitionOrder;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int titleId=_title.getIdentifier();
    sb.append("Title ").append(_title.getName()).append(" (").append(titleId).append("): ");
    sb.append(_state);
    if (_state==TitleState.ACQUIRED)
    {
      if (_acquisitionDate!=null)
      {
        sb.append(" (").append(new Date(_acquisitionDate.longValue())).append(')');
      }
      if (_acquisitionTimestamp!=null)
      {
        sb.append(" (").append(_acquisitionTimestamp).append(')');
      }
    }
    return sb.toString();
  }
}
