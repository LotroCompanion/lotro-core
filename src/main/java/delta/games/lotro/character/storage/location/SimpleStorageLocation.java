package delta.games.lotro.character.storage.location;

import delta.games.lotro.common.owner.CharacterOwner;
import delta.games.lotro.common.owner.Owner;

/**
 * Simple storage location.
 * @author DAM
 */
public class SimpleStorageLocation extends StorageLocation
{
  /**
   * Location type.
   * @author DAM
   */
  public enum LocationType
  {
    /**
     * Bag.
     */
    BAG,
    /**
     * Vault (for a single character).
     */
    VAULT,
    /**
     * Shared vault (for an account on a server).
     */
    SHARED_VAULT,
    /**
     * Wallet.
     */
    WALLET,
    /**
     * Shared wallet.
     */
    SHARED_WALLET,
    /**
     * Carry-all.
     */
    CARRY_ALL
  }
  private Owner _owner;
  private LocationType _type;
  private String _chestName;

  /**
   * Constructor.
   * @param owner Owner.
   * @param type Location type.
   * @param chestName Chest name.
   */
  public SimpleStorageLocation(Owner owner, LocationType type, String chestName)
  {
    _owner=owner;
    _type=type;
    if (chestName==null)
    {
      chestName="";
    }
    _chestName=chestName;
  }

  /**
   * Get the owner.
   * @return the owner.
   */
  public Owner getOwner()
  {
    return _owner;
  }

  /**
   * Get the location type.
   * @return the location type.
   */
  public LocationType getLocationType()
  {
    return _type;
  }

  /**
   * Get the chest name.
   * @return the chest name.
   */
  public String getChestName()
  {
    return _chestName;
  }

  @Override
  public String getLabel()
  {
    String prefix=null;
    if (_owner instanceof CharacterOwner)
    {
      String characterName=((CharacterOwner)_owner).getCharacterName();
      prefix=characterName;
    }
    String ret="";
    if (_type==LocationType.VAULT)
    {
      ret="Vault: Chest "+_chestName;
    }
    else if (_type==LocationType.BAG)
    {
      ret="Bag";
    }
    else if (_type==LocationType.SHARED_VAULT)
    {
      ret="Shared vault: Chest "+_chestName;
    }
    else if (_type==LocationType.WALLET)
    {
      ret="Wallet";
    }
    else if (_type==LocationType.SHARED_WALLET)
    {
      ret="Shared Wallet";
    }
    else if (_type==LocationType.CARRY_ALL)
    {
      ret="Carry-all";
    }
    if (prefix!=null)
    {
      ret=prefix+": "+ret;
    }
    return ret;
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    if (!(object instanceof SimpleStorageLocation))
    {
      return false;
    }
    SimpleStorageLocation other=(SimpleStorageLocation)object;
    if(!_chestName.equals(other._chestName))
    {
      return false;
    }
    if (!_owner.equals(other._owner))
    {
      return false;
    }
    return _type==other._type;
  }

  @Override
  public int hashCode()
  {
    return _chestName.hashCode()+_type.hashCode()+_owner.hashCode();
  }
}
