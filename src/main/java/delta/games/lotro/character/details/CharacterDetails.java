package delta.games.lotro.character.details;

import java.util.Date;

import delta.games.lotro.common.Duration;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.money.Money;

/**
 * Details for a single character.
 * @author DAM
 */
public class CharacterDetails
{
  private long _xp;
  private Position _position;
  private Integer _areaID;
  private Integer _dungeonID;
  private int _ingameTime;
  private Integer _currentTitleId;
  private Integer _currentVocationId;
  private Money _money;
  private Long _lastLogoutDate;

  /**
   * Constructor.
   */
  public CharacterDetails()
  {
    _xp=0;
    _position=null;
    _areaID=null;
    _dungeonID=null;
    _ingameTime=0;
    _currentTitleId=null;
    _currentVocationId=null;
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
  public Position getPosition()
  {
    return _position;
  }

  /**
   * Set the current character position.
   * @param position the position to set.
   */
  public void setPosition(Position position)
  {
    _position=position;
  }

  /**
   * Get the current area for this character.
   * @return an area ID or <code>null</code>.
   */
  public Integer getAreaID()
  {
    return _areaID;
  }

  /**
   * Set the ID of the current area for this character.
   * @param areaID the identifier of the area to set.
   */
  public void setAreaID(Integer areaID)
  {
    _areaID=areaID;
  }

  /**
   * Get the current dungeon for this character.
   * @return a dungeon ID or <code>null</code>.
   */
  public Integer getDungeonID()
  {
    return _dungeonID;
  }

  /**
   * Set the ID of the current dungeon for this character.
   * @param dungeonID the identifier of the dungeon to set.
   */
  public void setDungeonID(Integer dungeonID)
  {
    _dungeonID=dungeonID;
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
   * Get the identifier of the current vocation for this character.
   * @return a vocation identifier or <code>null</code> if none.
   */
  public Integer getCurrentVocationId()
  {
    return _currentVocationId;
  }

  /**
   * Set the identifier of the current vocation for this character.
   * @param currentVocationId vocation identifier to set.
   */
  public void setCurrentVocationId(Integer currentVocationId)
  {
    _currentVocationId=currentVocationId;
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
    if (_areaID!=null)
    {
      sb.append("Area ID [").append(_areaID).append("], ");
    }
    if (_dungeonID!=null)
    {
      sb.append("Dungeon ID [").append(_dungeonID).append("], ");
    }
    sb.append("In-game time [").append(_ingameTime);
    sb.append(" = ").append(Duration.getDurationString(_ingameTime)).append("], ");
    if (_currentTitleId!=null)
    {
      sb.append("Title ID [").append(_currentTitleId).append("], ");
    }
    if (_currentVocationId!=null)
    {
      sb.append("Vocation ID [").append(_currentVocationId).append("], ");
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
