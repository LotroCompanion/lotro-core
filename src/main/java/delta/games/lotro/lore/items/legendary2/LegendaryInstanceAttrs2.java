package delta.games.lotro.lore.items.legendary2;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Legendary item instance description (reloaded).
 * @author DAM
 */
public class LegendaryInstanceAttrs2
{
  // Name
  private String _legendaryName;
  // Traceries
  private SocketsSetupInstance _sockets;

  /**
   * Constructor.
   * @param setup Sockets setup template.
   */
  public LegendaryInstanceAttrs2(SocketsSetup setup)
  {
    _legendaryName="";
    _sockets=new SocketsSetupInstance(setup);
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public LegendaryInstanceAttrs2(LegendaryInstanceAttrs2 source)
  {
    _legendaryName=source._legendaryName;
    _sockets=new SocketsSetupInstance(source._sockets);
  }

  /**
   * Get the name of this legendary item.
   * @return a name (never <code>null</code>).
   */
  public String getLegendaryName()
  {
    return _legendaryName;
  }

  /**
   * Set the name of this legendary item.
   * @param legendaryName the name to set.
   */
  public void setLegendaryName(String legendaryName)
  {
    if (legendaryName==null)
    {
      legendaryName="";
    }
    _legendaryName=legendaryName;
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
    StringBuilder sb=new StringBuilder();
    sb.append("Name: ").append(_legendaryName).append(EndOfLine.NATIVE_EOL);
    sb.append(_sockets.dump());
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return dump();
  }
}
