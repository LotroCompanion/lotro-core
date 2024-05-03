package delta.games.lotro.character.storage.summary;

/**
 * Storage summary for a single character.
 * @author DAM
 */
public class CharacterStorageSummary
{
  private SingleStorageSummary _bags;
  private SingleStorageSummary _ownVault;

  /**
   * Constructor.
   */
  public CharacterStorageSummary()
  {
    _bags=new SingleStorageSummary();
    _ownVault=new SingleStorageSummary();
  }

  /**
   * Get the bags summary.
   * @return  the bags summary.
   */
  public SingleStorageSummary getBags()
  {
    return _bags;
  }

  /**
   * Get the own vault summary.
   * @return  the own vault summary.
   */
  public SingleStorageSummary getOwnVault()
  {
    return _ownVault;
  }

  @Override
  public String toString()
  {
    return "Bags: "+_bags+" ; own vault: "+_ownVault;
  }
}
