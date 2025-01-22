package delta.games.lotro.character.status.housing;

import java.util.ArrayList;
import java.util.List;

/**
 * Housing data for an account.
 * @author DAM
 */
public class AccountHousingData
{
  private List<HouseReference> _premiumHouses; 
  private HouseReference _classicHouse;

  /**
   * Constructor.
   */
  public AccountHousingData()
  {
    _premiumHouses=new ArrayList<HouseReference>();
    _classicHouse=null;
  }

  /**
   * Add a premium house reference.
   * @param houseReference Reference to add.
   */
  public void addPremiumHouse(HouseReference houseReference)
  {
    _premiumHouses.add(houseReference);
  }

  /**
   * Get the premium houses.
   * @return A possibly empty but never <code>null</code> list of house references.
   */
  public List<HouseReference> getPremiumHouses()
  {
    return new ArrayList<HouseReference>(_premiumHouses);
  }

  /**
   * Get the reference to the classic house of the account.
   * @return A reference or <code>null</code> if no classic house for the account.
   */
  public HouseReference getClassicHouse()
  {
    return _classicHouse;
  }

  /**
   * Set the classic house for the account.
   * @param classicHouse the reference to set.
   */
  public void setClassicHouse(HouseReference classicHouse)
  {
    _classicHouse=classicHouse;
  }
}
