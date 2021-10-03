package delta.games.lotro.lore.items.legendary2;

/**
 * Attributes of a legendary item model (reloaded).
 * @author DAM
 */
public class LegendaryAttrs2
{
  private SocketsSetup _sockets;

  /**
   * Constructor.
   */
  public LegendaryAttrs2()
  {
    _sockets=new SocketsSetup();
  }

  /**
   * Get the sockets setup.
   * @return a sockets setup.
   */
  public SocketsSetup getSockets()
  {
    return _sockets;
  }

  @Override
  public String toString()
  {
    return _sockets.toString();
  }
}
