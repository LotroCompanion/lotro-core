package delta.games.lotro.lore.items.legendary2;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.enums.SocketType;

/**
 * Manager for all known traceries.
 * @author DAM
 */
public class TraceriesManager
{
  private List<Tracery> _traceries;

  /**
   * Constructor.
   */
  public TraceriesManager()
  {
    _traceries=new ArrayList<Tracery>();
  }
 
  /**
   * Get all the traceries for a given socket type.
   * @param type Socket type.
   * @return A list of traceries.
   */
  public List<Tracery> getTracery(SocketType type)
  {
    List<Tracery> ret=new ArrayList<Tracery>();
    for(Tracery tracery : _traceries)
    {
      if (tracery.getType()==type)
      {
        ret.add(tracery);
      }
    }
    return ret;
  }
}
