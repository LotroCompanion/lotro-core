package delta.games.lotro.character.storage.carryalls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.id.InternalGameId;

/**
 * Manager for all the carry-alls of a character.
 * @author DAM
 */
public class CarryAllsManager
{
  private Map<String,CarryAll> _carryAlls;

  /**
   * Constructor.
   */
  public CarryAllsManager()
  {
    _carryAlls=new HashMap<String,CarryAll>();
  }

  /**
   * Get all keys.
   * @return a sorted list of keys.
   */
  public List<String> getKeys()
  {
    List<String> ret=new ArrayList<String>(_carryAlls.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get a carry-all using its key.
   * @param key Key to use.
   * @return A carry-all or <code>null</code> if no such carry-all.
   */
  public CarryAll getCarryAllByKey(String key)
  {
    return _carryAlls.get(key);
  }

  /**
   * Add a carry-all.
   * @param carryAll Carry-all to add.
   */
  public void addCarryAll(CarryAll carryAll)
  {
    InternalGameId id=carryAll.getInstanceId();
    String key=id.asPersistedString();
    _carryAlls.put(key,carryAll);
  }
}
