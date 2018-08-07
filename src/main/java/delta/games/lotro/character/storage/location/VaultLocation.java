package delta.games.lotro.character.storage.location;

/**
 * Vault location: a specific chest into a vault (shared or not).
 * @author DAM
 */
public class VaultLocation extends StorageLocation
{
  private String _chestName;

  /**
   * Constructor.
   * @param chestName Chest name.
   */
  public VaultLocation(String chestName)
  {
    if (chestName==null)
    {
      chestName="";
    }
    _chestName=chestName;
  }

  @Override
  public String getLabel()
  {
    return _chestName.isEmpty()?"Chest":"Chest '"+_chestName+"'";
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    if (!(object instanceof VaultLocation))
    {
      return false;
    }
    VaultLocation other=(VaultLocation)object;
    return _chestName.equals(other._chestName);
  }

  @Override
  public int hashCode()
  {
    return _chestName.hashCode();
  }
}
