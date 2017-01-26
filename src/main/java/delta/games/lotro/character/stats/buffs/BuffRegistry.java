package delta.games.lotro.character.stats.buffs;

import java.util.HashMap;

/**
 * Registry for buffs.
 * @author DAM
 */
public class BuffRegistry
{
  private HashMap<String,Buff> _buffMap;

  /**
   * Constructor.
   */
  public BuffRegistry()
  {
    _buffMap=new HashMap<String,Buff>();
  }

  /**
   * Register a new buff.
   * @param buff Buff to register.
   */
  public void registerBuff(Buff buff)
  {
    String id=buff.getId();
    _buffMap.put(id,buff);
  }

  private Buff getBuffById(String buffId)
  {
    return _buffMap.get(buffId);
  }

  /**
   * Build a new buff instance.
   * @param buffId Buff identifier.
   * @return A new buff or <code>null</code> if buff not found.
   */
  public BuffInstance newBuffInstance(String buffId)
  {
    BuffInstance ret=null;
    Buff buff=getBuffById(buffId);
    if (buff!=null)
    {
      ret=new BuffInstance(buff);
    }
    return ret;
  }
}
