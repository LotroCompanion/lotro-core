package delta.games.lotro.character.status.housing;

import delta.games.lotro.common.id.InternalGameId;

/**
 * Reference to a house: address+owner.
 * @author DAM
 */
public class HouseReference
{
  private HouseAddress _address; 
  private InternalGameId _owner; // From HousingSystem_AccountPremiumHouseOwner_IID

  /**
   * Constructor.
   * @param address Address.
   * @param owner Owner.
   */
  public HouseReference(HouseAddress address, InternalGameId owner)
  {
    _address=address;
    _owner=owner;
  }

  /**
   * Get the house address.
   * @return the house address.
   */
  public HouseAddress getAddress()
  {
    return _address;
  }

  /**
   * Get the identifier of the house owner.
   * @return a owner identifier.
   */
  public InternalGameId getOwner()
  {
    return _owner;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("House reference: address=");
    sb.append(_address);
    sb.append(", owner=");
    sb.append (_owner);
    return sb.toString();
  }
}
