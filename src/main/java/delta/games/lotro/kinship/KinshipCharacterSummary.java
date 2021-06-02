package delta.games.lotro.kinship;

import java.util.Date;

import delta.games.lotro.character.BaseCharacterSummary;

/**
 * Storage class for a LOTRO kinship character summary.
 * @author DAM
 */
public class KinshipCharacterSummary extends BaseCharacterSummary
{
  private Integer _vocationID;
  private Long _lastLogoutDate;
  private Integer _areaID;

  /**
   * Constructor.
   */
  public KinshipCharacterSummary()
  {
    super();
    _vocationID=null;
    _lastLogoutDate=null;
    _areaID=null;
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public KinshipCharacterSummary(KinshipCharacterSummary source)
  {
    super(source);
    _vocationID=source._vocationID;
    _lastLogoutDate=source._lastLogoutDate;
    _areaID=source._areaID;
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
    sb.append(super.toString());
    if (_vocationID!=null)
    {
      // TODO Vocation name display
      sb.append(", Vocation ID [").append(_vocationID).append(']');
    }
    if (_lastLogoutDate!=null)
    {
      sb.append(", Last logout date [").append(_lastLogoutDate);
      sb.append(" = ").append(new Date(_lastLogoutDate.longValue())).append("], ");
    }
    if (_areaID!=null)
    {
      sb.append(", Area ID [").append(_areaID).append(']');
    }
    return sb.toString();
  }
}
