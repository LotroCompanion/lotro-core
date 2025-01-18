package delta.games.lotro.lore.housing;

import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.geo.ExtendedPosition;
import delta.games.lotro.common.money.Money;

/**
 * @author dm
 */
public class HouseDefinition implements Identifiable
{
  private int _id;
  private String _address; // House_Address (i18n)
  private List<TraitDescription> _traits; // House_CharacteristicArray/House_Characteristic
  private String _description; // House_Description (i18n)
  private boolean _isPremium; // House_IsKinshipHouse
  private boolean _isKinship; // House_IsPremiumHouse
  private NeighborhoodTemplate _neigborhood; // House_NeighborhoodTemplate
  private ExtendedPosition _position; // House_Telepad
  private HouseTypeInfo _info; // derivated from House_Type
  private Money _price; // derivated from HouseTypeInfo and House_ValueTier
  private Money _upkeep;

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * @return the address
   */
  public String getAddress()
  {
    return _address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address)
  {
    _address=address;
  }

  /**
   * @return the traits
   */
  public List<TraitDescription> getTraits()
  {
    return _traits;
  }

  /**
   * @param traits the traits to set
   */
  public void setTraits(List<TraitDescription> traits)
  {
    _traits=traits;
  }

  /**
   * @return the description
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * @return the isPremium
   */
  public boolean isPremium()
  {
    return _isPremium;
  }

  /**
   * @param isPremium the isPremium to set
   */
  public void setPremium(boolean isPremium)
  {
    _isPremium=isPremium;
  }

  /**
   * @return the isKinship
   */
  public boolean isKinship()
  {
    return _isKinship;
  }

  /**
   * @param isKinship the isKinship to set
   */
  public void setKinship(boolean isKinship)
  {
    _isKinship=isKinship;
  }

  /**
   * @return the neigborhood
   */
  public NeighborhoodTemplate getNeigborhood()
  {
    return _neigborhood;
  }

  /**
   * @param neigborhood the neigborhood to set
   */
  public void setNeigborhood(NeighborhoodTemplate neigborhood)
  {
    _neigborhood=neigborhood;
  }

  /**
   * @return the position
   */
  public ExtendedPosition getPosition()
  {
    return _position;
  }

  /**
   * @param position the position to set
   */
  public void setPosition(ExtendedPosition position)
  {
    _position=position;
  }

  /**
   * @return the info
   */
  public HouseTypeInfo getInfo()
  {
    return _info;
  }

  /**
   * @param info the info to set
   */
  public void setInfo(HouseTypeInfo info)
  {
    _info=info;
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
}
