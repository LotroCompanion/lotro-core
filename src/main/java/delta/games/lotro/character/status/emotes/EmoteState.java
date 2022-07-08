package delta.games.lotro.character.status.emotes;

/**
 * State of an emote.
 * @author DAM
 */
public enum EmoteState
{
  /**
   * Acquired.
   */
  ACQUIRED("Acquired"),
  /**
   * Automatically acquired.
   */
  AUTO("Automatic"),
  /**
   * Not known.
   */
  NOT_KNOWN("Not known");

  private String _label;
  private EmoteState(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
