package delta.games.lotro.character.storage.location;

/**
 * Bag location: a specific bag on a character.
 * @author DAM
 */
public class BagLocation extends StorageLocation
{
  private String _bagName;

  /**
   * Constructor.
   * @param bagName Bag name.
   */
  public BagLocation(String bagName)
  {
    if (bagName==null)
    {
      bagName="";
    }
    _bagName=bagName;
  }

  @Override
  public String getLabel()
  {
    return _bagName.isEmpty()?"Bag":"Bag '"+_bagName+"'";
  }
}
