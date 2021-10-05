package delta.games.lotro.lore.items.legendary2;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Instance of a sockets setup.
 * @author DAM
 */
public class SocketsSetupInstance
{
  private SocketsSetup _setup;
  private List<SocketEntryInstance> _entries;

  /**
   * Constructor.
   * @param setup Template setup.
   */
  public SocketsSetupInstance(SocketsSetup setup)
  {
    _setup=setup;
    init();
  }

  /**
   * Get an entry.
   * @param index Index of the entry.
   * @return An entry or <code>null</code> if bad index.
   */
  public SocketEntryInstance getEntry(int index)
  {
    if ((index>=0) && (index<_entries.size()))
    {
      return _entries.get(index);
    }
    return null;
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public SocketsSetupInstance(SocketsSetupInstance source)
  {
    _setup=source._setup;
    _entries=new ArrayList<SocketEntryInstance>();
    for(SocketEntryInstance entry : source._entries)
    {
      _entries.add(new SocketEntryInstance(entry));
    }
  }

  private void init()
  {
    int nbSockets=_setup.getSocketsCount();
    _entries=new ArrayList<SocketEntryInstance>(nbSockets);
    for(int i=0;i<nbSockets;i++)
    {
      SocketEntry entry=_setup.getSocket(i);
      SocketEntryInstance instance=new SocketEntryInstance(entry);
      _entries.add(instance);
    }
  }

  /**
   * Get the sockets setup template.
   * @return the sockets setup template.
   */
  public SocketsSetup getSetupTemplate()
  {
    return _setup;
  }

  /**
   * Get the total stats for all socket entries.
   * @return a set of stats.
   */
  public BasicStatsSet getStats()
  {
    BasicStatsSet ret=new BasicStatsSet();
    for(SocketEntryInstance entry : _entries)
    {
      BasicStatsSet entryStats=entry.getStats();
      ret.addStats(entryStats);
    }
    return ret;
  }

  /**
   * Dump the contents of this sockets setup as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    int nbSockets=_entries.size();
    for(int i=0;i<nbSockets;i++)
    {
      sb.append('#').append(i+1).append(": ");
      sb.append(_entries.get(i)).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return dump();
  }
}
