package delta.games.lotro.character.storage.location;

/**
 * Location that represents a wallet (shared or not).
 * @author DAM
 */
public class WalletLocation extends StorageLocation
{
  @Override
  public String getLabel()
  {
    return "Wallet";
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    return (object instanceof WalletLocation);
  }
}
