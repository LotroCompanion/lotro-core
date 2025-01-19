package delta.games.lotro.lore.housing;

import delta.games.lotro.common.enums.HouseType;
import delta.games.lotro.common.money.Money;

/**
 * Information about a single house type. 
 * @author DAM
 */
public class HouseTypeInfo
{
  private HouseType _houseType; // HousingControl_HouseType
  private Money _price; // HousingControl_HousePrice
  private Money _upkeep; // HousingControl_HouseUpkeep
  private int _iconID; // HousingControl_Icon
  private int _icon16ID; // HousingControl_Icon16
  private int _icon32ID; // HousingControl_Icon32
  private int _iconLargeID; // HousingControl_Icon_Large
  private int _iconPanoramaID; // HousingControl_Icon_Panorama

  /**
   * Constructor.
   * @param houseType House type.
   */
  public HouseTypeInfo(HouseType houseType)
  {
    _houseType=houseType;
  }

  /**
   * @return the houseType
   */
  public HouseType getHouseType()
  {
    return _houseType;
  }

  /**
   * @return the price
   */
  public Money getPrice()
  {
    return _price;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(Money price)
  {
    _price=price;
  }

  /**
   * @return the upkeep
   */
  public Money getUpkeep()
  {
    return _upkeep;
  }

  /**
   * @param upkeep the upkeep to set
   */
  public void setUpkeep(Money upkeep)
  {
    _upkeep=upkeep;
  }

  /**
   * @return the iconID
   */
  public int getIconID()
  {
    return _iconID;
  }

  /**
   * @param iconID the iconID to set
   */
  public void setIconID(int iconID)
  {
    _iconID=iconID;
  }

  /**
   * @return the icon16ID
   */
  public int getIcon16ID()
  {
    return _icon16ID;
  }

  /**
   * @param icon16id the icon16ID to set
   */
  public void setIcon16ID(int icon16id)
  {
    _icon16ID=icon16id;
  }

  /**
   * @return the icon32ID
   */
  public int getIcon32ID()
  {
    return _icon32ID;
  }

  /**
   * @param icon32id the icon32ID to set
   */
  public void setIcon32ID(int icon32id)
  {
    _icon32ID=icon32id;
  }

  /**
   * @return the iconLargeID
   */
  public int getIconLargeID()
  {
    return _iconLargeID;
  }

  /**
   * @param iconLargeID the iconLargeID to set
   */
  public void setIconLargeID(int iconLargeID)
  {
    _iconLargeID=iconLargeID;
  }

  /**
   * @return the iconPanoramaID
   */
  public int getIconPanoramaID()
  {
    return _iconPanoramaID;
  }

  /**
   * @param iconPanoramaID the iconPanoramaID to set
   */
  public void setIconPanoramaID(int iconPanoramaID)
  {
    _iconPanoramaID=iconPanoramaID;
  }
}
