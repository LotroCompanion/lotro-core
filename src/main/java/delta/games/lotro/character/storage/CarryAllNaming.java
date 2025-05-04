package delta.games.lotro.character.storage;

import java.util.HashMap;
import java.util.Map;

import delta.common.utils.misc.IntegerHolder;
import delta.games.lotro.character.storage.carryAlls.CarryAllInstance;

/**
 * Naming policy for a set of carry-alls.
 * @author DAM
 */
public class CarryAllNaming
{
  private Map<String,IntegerHolder> _counters;

  /**
   * Constructor.
   */
  public CarryAllNaming()
  {
    _counters=new HashMap<String,IntegerHolder>();
  }

  /**
   * Get the name for a carry-all.
   * @param carryAll Carry-all to use.
   * @return A name.
   */
  public String getCarryAllName(CarryAllInstance carryAll)
  {
    String name=carryAll.getEffectiveName();
    IntegerHolder counter=_counters.get(name);
    if (counter==null)
    {
      counter=new IntegerHolder();
      _counters.put(name,counter);
    }
    counter.increment();
    int index=counter.getInt();
    if (index>1)
    {
      name=name+" #"+index;
    }
    return name;
  }
}
