package delta.games.lotro.character.details;

import java.util.Date;

import delta.games.lotro.common.Duration;
import delta.games.lotro.common.money.Money;

/**
 * Details for a single character.
 * @author DAM
 */
public class CharacterDetails
{
  private long _xp;
  private Object _position;
  private String _area;
  private int _ingameTime;
  private Integer _currentTitleId;
  private Money _money;
  private Long _lastLogoutDate;

  /**
   * Constructor.
   */
  public CharacterDetails()
  {
    _xp=0;
    _position=null;
    _area="";
    _ingameTime=0;
    _currentTitleId=null;
    _money=new Money();
    _lastLogoutDate=null;
  }

  /**
   * Get the XP amount.
   * @return an XP amount.
   */
  public long getXp()
  {
    return _xp;
  }

  /**
   * Set the XP amount.
   * @param xp the XP amount to set.
   */
  public void setXp(long xp)
  {
    _xp=xp;
  }

  /**
   * Get the current character position.
   * @return a position.
   */
  public Object getPosition()
  {
    return _position;
  }

  /**
   * Set the current character position.
   * @param position the position to set.
   */
  public void setPosition(Object position)
  {
    _position=position;
  }

  /**
   * Get the current area for this character.
   * @return an area name.
   */
  public String getArea()
  {
    return _area;
  }

  /**
   * Set the current area for this character.
   * @param area the name of the area to set.
   */
  public void setArea(String area)
  {
    _area=area;
  }

  /**
   * Get the total in-game time for this character.
   * @return a duration (seconds).
   */
  public int getIngameTime()
  {
    return _ingameTime;
  }

  /**
   * Set the total in-game time for this character.
   * @param ingameTime the time to set (seconds).
   */
  public void setIngameTime(int ingameTime)
  {
    _ingameTime=ingameTime;
  }

  /**
   * Get the identifier of the current title for this character.
   * @return a title identifier or <code>null</code> if none.
   */
  public Integer getCurrentTitleId()
  {
    return _currentTitleId;
  }

  /**
   * Set the identifier of the current title for this character.
   * @param currentTitleId title identifier to set.
   */
  public void setCurrentTitleId(Integer currentTitleId)
  {
    _currentTitleId=currentTitleId;
  }

  /**
   * Get the current money for this character.
   * @return a money amount.
   */
  public Money getMoney()
  {
    return _money;
  }

  /**
   * Set the current money for this character.
   * @param money money amount to set.
   */
  public void setMoney(Money money)
  {
    _money=money;
  }

  /**
   * Get the last logout date for this character.
   * @return a timestamp (milliseconds since Epoch) or <code>null</code>.
   */
  public Long getLastLogoutDate()
  {
    return _lastLogoutDate;
  }

  /**
   * Set the last logout date for this character.
   * @param lastLogoutDate the date to set.
   */
  public void setLastLogoutDate(Long lastLogoutDate)
  {
    _lastLogoutDate=lastLogoutDate;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("XP [").append(_xp).append("], ");
    if (_position!=null)
    {
      sb.append("Position [").append(_position).append("], ");
    }
    sb.append("Area [").append(_area).append("], ");
    sb.append("In-game time [").append(_ingameTime);
    sb.append(" = ").append(Duration.getDurationString(_ingameTime)).append("], ");
    if (_currentTitleId!=null)
    {
      sb.append("Title ID [").append(_currentTitleId).append("], ");
    }
    sb.append("Money [").append(_money).append("], ");
    if (_lastLogoutDate!=null)
    {
      sb.append("Last logout date [").append(_lastLogoutDate);
      sb.append(" = ").append(new Date(_lastLogoutDate.longValue())).append("], ");
    }
    return sb.toString();
  }
}
