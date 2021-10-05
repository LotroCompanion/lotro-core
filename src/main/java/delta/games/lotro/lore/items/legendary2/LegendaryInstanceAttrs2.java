package delta.games.lotro.lore.items.legendary2;

import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Legendary item instance description (reloaded).
 * @author DAM
 */
public class LegendaryInstanceAttrs2
{
  private SocketsSetupInstance _sockets;

  /**
   * Constructor.
   * @param setup Sockets setup template.
   */
  public LegendaryInstanceAttrs2(SocketsSetup setup)
  {
    _sockets=new SocketsSetupInstance(setup);
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public LegendaryInstanceAttrs2(LegendaryInstanceAttrs2 source)
  {
    _sockets=new SocketsSetupInstance(source._sockets);
  }

  /**
   * Get the sockets setup.
   * @return the sockets setup.
   */
  public SocketsSetupInstance getSocketsSetup()
  {
    return _sockets;
  }

  /**
   * Get the total stats for these legendary attributes.
   * @return a set of stats.
   */
  public BasicStatsSet getStats()
  {
    return _sockets.getStats();
  }

  /**
   * Dump the contents of this legendary attributes as a string.
   * @return A readable string.
   */
  public String dump()
  {
    return _sockets.dump();
  }

  @Override
  public String toString()
  {
    return dump();
  }
}
