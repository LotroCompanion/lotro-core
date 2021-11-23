package delta.games.lotro.character.storage.bags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Setup of all bags for a single character.
 * <p>
 * Stores the setup of each bag.
 * @author DAM
 */
public class BagsSetup
{
  private Map<Integer,SingleBagSetup> _bagSetups;

  /**
   * Constructor.
   */
  public BagsSetup()
  {
    _bagSetups=new HashMap<Integer,SingleBagSetup>();
  }

  /**
   * Get a bag setup.
   * @param bagIndex Bag index.
   * @return A bag setup or <code>null</code> if not found.
   */
  public SingleBagSetup getBagSetup(int bagIndex)
  {
    return _bagSetups.get(Integer.valueOf(bagIndex));
  }

  /**
   * Get the known bag indexes.
   * @return A sorted list of bag indexes.
   */
  public List<Integer> getBagIndexes()
  {
    List<Integer> ret=new ArrayList<Integer>(_bagSetups.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Add a bag setup.
   * @param bagSetup Bag setup to add.
   */
  public void addBagSetup(SingleBagSetup bagSetup)
  {
    Integer key=Integer.valueOf(bagSetup.getBagIndex());
    _bagSetups.put(key,bagSetup);
  }

  /**
   * Get the bags total capacity.
   * @return A capacity (slots count).
   */
  public int getCapacity()
  {
    int ret=0;
    for(SingleBagSetup setup : _bagSetups.values())
    {
      ret+=setup.getSize();
    }
    return ret;
  }
}
