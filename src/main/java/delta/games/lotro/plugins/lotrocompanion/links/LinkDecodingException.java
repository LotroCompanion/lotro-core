package delta.games.lotro.plugins.lotrocompanion.links;

/**
 * Exception used by the item links decoder.
 * @author DAM
 */
public class LinkDecodingException extends Exception
{
  /**
   * Constructor.
   * @param message Exception message.
   */
  public LinkDecodingException(String message)
  {
    super(message);
  }
}
