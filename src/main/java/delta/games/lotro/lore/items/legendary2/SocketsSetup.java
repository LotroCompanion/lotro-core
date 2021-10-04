package delta.games.lotro.lore.items.legendary2;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Setup of sockets.
 * @author DAM
 */
public class SocketsSetup implements Identifiable
{
  private int _itemId;
  private List<SocketEntry> _entries;

  /**
   * Constructor.
   * @param itemId Identifier of the parent item.
   */
  public SocketsSetup(int itemId)
  {
    _itemId=itemId;
    _entries=new ArrayList<SocketEntry>();
  }

  @Override
  public int getIdentifier()
  {
    return _itemId;
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
