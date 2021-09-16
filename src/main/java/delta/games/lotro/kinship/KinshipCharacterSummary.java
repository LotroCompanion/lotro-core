package delta.games.lotro.kinship;

import java.util.Date;

import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.lore.crafting.CraftingData;
import delta.games.lotro.lore.crafting.CraftingSystem;
import delta.games.lotro.lore.crafting.Vocation;
import delta.games.lotro.lore.crafting.Vocations;
import delta.games.lotro.lore.maps.Area;
import delta.games.lotro.lore.maps.GeoAreasManager;

/**
 * Storage class for a LOTRO kinship character summary.
 * @author DAM
 */
public class KinshipCharacterSummary extends BaseCharacterSummary
{
  private Integer _vocationID;
  private String _vocation;
  private Long _lastLogoutDate;
  private Integer _areaID;
  private String _area;

  /**
   * Constructor.
   */
  public KinshipCharacterSummary()
  {
    super();
    _vocationID=null;
    _vocation=null;
    _lastLogoutDate=null;
    _areaID=null;
    _area=null;
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
   * Get the vocation ID;
   * @return the vocation ID.
   */
  public Integer getVocationID()
  {
    return _vocationID;
  }

  /**
   * Set the vocation ID.
   * @param vocationID Vocation ID to set.
   */
  public void setVocationID(Integer vocationID)
  {
    _vocationID=vocationID;
    _vocation=null;
  }

  /**
   * Get the vocation label.
   * @return A label or <code>null</code> if no vocation.
   */
  public String getVocation()
  {
    if ((_vocationID!=null) && (_vocation==null))
    {
      CraftingData craftingData=CraftingSystem.getInstance().getData();
      Vocations vocations=craftingData.getVocationsRegistry();
      Vocation vocation=vocations.getVocationById(_vocationID.intValue());
      _vocation=(vocation!=null)?vocation.getName():"?";
    }
    return _vocation;
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

  /**
   * Get the area ID;
   * @return the area ID.
   */
  public Integer getAreaID()
  {
    return _areaID;
  }

  /**
   * Set the area ID.
   * @param areaID Area ID to set.
   */
  public void setAreaID(Integer areaID)
  {
    _areaID=areaID;
    _area=null;
  }

  /**
   * Get the vocation label.
   * @return A label or <code>null</code> if no vocation.
   */
  public String getArea()
  {
    if ((_areaID!=null) && (_area==null))
    {
      GeoAreasManager areasMgr=GeoAreasManager.getInstance();
      Area area=areasMgr.getAreaById(_areaID.intValue());
      _area=(area!=null)?area.getName():"?";
    }
    return _area;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(super.toString());
    if (_vocationID!=null)
    {
      sb.append(", Vocation ID [").append(_vocationID).append("] (").append(getVocation()).append(')');
    }
    if (_lastLogoutDate!=null)
    {
      sb.append(", Last logout date [").append(_lastLogoutDate);
      sb.append(" = ").append(new Date(_lastLogoutDate.longValue())).append("], ");
    }
    if (_areaID!=null)
    {
      sb.append(", Area ID [").append(_areaID).append("] (").append(getArea()).append(')');
    }
    return sb.toString();
  }
}
