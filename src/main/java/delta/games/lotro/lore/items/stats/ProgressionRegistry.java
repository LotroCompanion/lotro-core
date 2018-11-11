package delta.games.lotro.lore.items.stats;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.utils.maths.Progression;

/**
 * Registry for progression curves.
 * @author DAM
 */
public class ProgressionRegistry
{
  private Map<Integer,Progression> _map;

  /**
   * Constructor.
   */
  public ProgressionRegistry()
  {
    _map=new HashMap<Integer,Progression>();
  }

  /**
   * Get a progression using its identifier.
   * @param id Identifier of the progression to get.
   * @return A progression or <code>null</code> if not found.
   */
  public Progression getProgression(int id)
  {
    return _map.get(Integer.valueOf(id));
  }

  /**
   * Register a new progression.
   * @param id Progression ID.
   * @param progression Progression to register.
   */
  public void registerProgression(int id, Progression progression)
  {
    _map.put(Integer.valueOf(id),progression);
  }
}
