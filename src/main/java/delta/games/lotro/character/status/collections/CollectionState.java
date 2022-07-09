package delta.games.lotro.character.status.collections;

/**
 * State of a collection.
 * @author DAM
 */
public enum CollectionState
{
  /**
   * Completed.
   */
  COMPLETED("Completed"),
  /**
   * Started.
   */
  STARTED("Started"),
  /**
   * Not started.
   */
  NOT_STARTED("Not started");

  private String _label;
  private CollectionState(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
