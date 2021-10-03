package delta.games.lotro.lore.items.legendary2;

import java.util.ArrayList;
import java.util.List;

/**
 * Setup of sockets.
 * @author DAM
 */
public class SocketsSetup
{
  private List<SocketEntry> _entries;

  /**
   * Constructor.
   */
  public SocketsSetup()
  {
    _entries=new ArrayList<SocketEntry>();
  }

  /**
   * Get the number of sockets.
   * @return A sockets count.
   */
  public int getSocketsCount()
  {
    return _entries.size();
  }

  /**
   * Add a socket.
   * @param entry Socket to add.
   */
  public void addSocket(SocketEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get a socket.
   * @param index Index of socket to get, starting at 0.
   * @return A socket.
   */
  public SocketEntry getSocket(int index)
  {
    return _entries.get(index);
  }
}
