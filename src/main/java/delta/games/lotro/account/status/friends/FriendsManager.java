package delta.games.lotro.account.status.friends;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Friends manager.
 * @author DAM
 */
public class FriendsManager
{
  private Map<InternalGameId,Friend> _friends;

  /**
   * Constructor.
   */
  public FriendsManager()
  {
    _friends=new HashMap<InternalGameId,Friend>();
  }

  /**
   * Add a friend.
   * @param friend Friend to add.
   */
  public void addFriend(Friend friend)
  {
    _friends.put(friend.getId(),friend);
  }

  /**
   * Get a list of all friends, sorted by name.
   * @return A list of friends.
   */
  public List<Friend> getAll()
  {
    List<Friend> ret=new ArrayList<Friend>(_friends.values());
    Collections.sort(ret,new NamedComparator());
    return ret;
  }
}
