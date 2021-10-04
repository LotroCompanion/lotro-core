package delta.games.lotro.lore.items.legendary2;

import delta.games.lotro.common.Identifiable;

/**
 * Attributes of a legendary item model (reloaded).
 * @author DAM
 */
public class LegendaryAttrs2 implements Identifiable
{
  private SocketsSetup _sockets;

  /**
   * Constructor.
   */
  public LegendaryAttrs2()
  {
    _sockets=null;
  }

  @Override
  public int getIdentifier()
  {
    return (_sockets!=null)?_sockets.getIdentifier():0;
  }

  /**
   * Get the sockets setup.
   * @return a sockets setup.
   */
  public SocketsSetup getSockets()
  {
    return _sockets;
  }

  /**
   * Set the sockets setup.
   * @param sockets Setup to set.
   */
  public void setSockets(SocketsSetup sockets)
  {
    _sockets=sockets;
  }

  @Override
  public String toString()
  {
    return _sockets.toString();
  }
}
