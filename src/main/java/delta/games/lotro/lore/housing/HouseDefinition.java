package delta.games.lotro.lore.housing;

import java.util.List;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.money.Money;

/**
 * Definition of a house.
 * @author DAM
 */
public class HouseDefinition implements Identifiable,Named
{
  private int _id;
  private String _address;
  private String _description;
  private List<TraitDescription> _traits;
  private boolean _isPremium;
  private boolean _isKinship;
  private int _neighborhoodTemplateID;
  private Position _position;
  private HouseTypeInfo _info;
  private Money _price;
  private Money _upkeep;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public HouseDefinition(int id)
  {
    _id=id;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  @Override
  public String getName()
  {
    return _address;
  }

  /**
   * Get the house address
   * @return a displayable address.
   */
  public String getAddress()
  {
    return _address;
  }

  /**
   * Set the house address.
   * @param address the address to set.
   */
  public void setAddress(String address)
  {
    _address=address;
  }

  /**
   * Get the house description.
   * @return a description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the house description.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * Get the traits given to the house owner.
   * @return a list of traits.
   */
  public List<TraitDescription> getTraits()
  {
    return _traits;
  }

  /**
   * Set the traits.
   * @param traits the traits to set.
   */
  public void setTraits(List<TraitDescription> traits)
  {
    _traits=traits;
  }

  /**
   * Indicates if this is a premium house (solo or kinship).
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isPremium()
  {
    return _isPremium;
  }

  /**
   * Set the 'premium' flag.
   * @param isPremium Value to set.
   */
  public void setPremium(boolean isPremium)
  {
    _isPremium=isPremium;
  }

  /**
   * Indicates if this is a kinship house (standard or premium).
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isKinship()
  {
    return _isKinship;
  }

  /**
   * Set the 'kinship' flag.
   * @param isKinship Value to set.
   */
  public void setKinship(boolean isKinship)
  {
    _isKinship=isKinship;
  }

  /**
   * Get the neighborhood template ID.
   * @return the neighborhood template ID.
   */
  public int getNeighborhoodTemplateID()
  {
    return _neighborhoodTemplateID;
  }

  /**
   * Set the neighborhood template ID.
   * @param neighborhoodTemplateID the ID to set.
   */
  public void setNeighborhoodTemplateID(int neighborhoodTemplateID)
  {
    _neighborhoodTemplateID=neighborhoodTemplateID;
  }

  /**
   * Get the position of the house entrance.
   * @return A position.
   */
  public Position getPosition()
  {
    return _position;
  }

  /**
   * Set the position of the house entrance.
   * @param position the position to set.
   */
  public void setPosition(Position position)
  {
    _position=position;
  }

  /**
   * Get the information about the house (house model).
   * @return the house info.
   */
  public HouseTypeInfo getInfo()
  {
    return _info;
  }

  /**
   * Set the house information.
   * @param info the information to set.
   */
  public void setInfo(HouseTypeInfo info)
  {
    _info=info;
  }

  /**
   * Get the price of this house.
   * @return a price.
   */
  public Money getPrice()
  {
    return _price;
  }

  /**
   * Set the price of this house.
   * @param price the price to set.
   */
  public void setPrice(Money price)
  {
    _price=price;
  }

  /**
   * Set the upkeep for this house.
   * @return the house upkeep.
   */
  public Money getUpkeep()
  {
    return _upkeep;
  }

  /**
   * Set the upkeep of this house.
   * @param upkeep the upkeep to set.
   */
  public void setUpkeep(Money upkeep)
  {
    _upkeep=upkeep;
  }
}
