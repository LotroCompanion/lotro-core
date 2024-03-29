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
   * Get the number of sockets.
   * @return A sockets count.
   */
  public int getSocketsCount()
  {
    return _entries.size();
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
   * @param itemLevel Item level of the parent LI.
   * @param characterLevel Level of the parent character.
   * @return a set of stats.
   */
  public BasicStatsSet getStats(int itemLevel, int characterLevel)
  {
    BasicStatsSet ret=new BasicStatsSet();
    for(SocketEntryInstance entry : _entries)
    {
      boolean enabled=entry.getTemplate().isEnabled(itemLevel);
      if (enabled)
      {
        Tracery tracery=entry.getTracery();
        if (tracery!=null)
        {
          boolean applicable=tracery.isApplicable(characterLevel,itemLevel);
          if (applicable)
          {
            BasicStatsSet entryStats=entry.getStats();
            ret.addStats(entryStats);
          }
        }
      }
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
