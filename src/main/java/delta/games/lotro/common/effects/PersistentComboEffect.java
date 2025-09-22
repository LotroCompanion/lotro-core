package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.utils.Proxy;

/**
 * Persistent combo effect.
 * @author DAM
 */
public class PersistentComboEffect extends Effect implements ParentEffect
{
  private List<Proxy<Effect>> _presentList;
  private List<Proxy<Effect>> _toAddIfAbsent;
  private List<Proxy<Effect>> _toAddIfPresent;
  private Proxy<Effect> _toExamine;

  /**
   * Constructor.
   */
  public PersistentComboEffect()
  {
    super();
    _presentList=new ArrayList<Proxy<Effect>>();
    _toAddIfAbsent=new ArrayList<Proxy<Effect>>();
    _toAddIfPresent=new ArrayList<Proxy<Effect>>();
    _toExamine=null;
  }

  /**
   * Add a 'present' effect.
   * @param effect Effect to add.
   */
  public void addPresentEffect(Proxy<Effect> effect)
  {
    _presentList.add(effect);
  }

  /**
   * Get the 'present' effects.
   * @return A list of effect proxies (may be empty but never <code>null</code>).
   */
  public List<Proxy<Effect>> getPresentEffects()
  {
    return _presentList;
  }

  /**
   * Get the 'to add if absent' effects.
   * @return A list of effect proxies (may be empty but never <code>null</code>).
   */
  public List<Proxy<Effect>> getToAddIfAbsent()
  {
    return _toAddIfAbsent;
  }

  /**
   * Add a 'to add if absent' effect.
   * @param effect Effect to add.
   */
  public void addToAddIfAbsent(Proxy<Effect> effect)
  {
    _toAddIfAbsent.add(effect);
  }

  /**
   * Get the 'to add if present' effects.
   * @return A list of effect proxies (may be empty but never <code>null</code>).
   */
  public List<Proxy<Effect>> getToAddIfPresent()
  {
    return _toAddIfPresent;
  }

  /**
   * Add a 'to add if present' effect.
   * @param effect Effect to add.
   */
  public void addToAddIfPresent(Proxy<Effect> effect)
  {
    _toAddIfPresent.add(effect);
  }

  /**
   * Get the 'to examine' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect> getToExamine()
  {
    return _toExamine;
  }

  /**
   * Set the 'to examine' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToExamine(Proxy<Effect> effect)
  {
    _toExamine=effect;
  }

  @Override
  public Set<Effect> getChildEffects()
  {
    HashSet<Effect> ret=new HashSet<Effect>();
    if (!_presentList.isEmpty())
    {
      for(Proxy<Effect> proxy : _presentList)
      {
        ret.add(proxy.getObject());
      }
    }
    if (!_toAddIfAbsent.isEmpty())
    {
      for(Proxy<Effect> proxy : _toAddIfAbsent)
      {
        ret.add(proxy.getObject());
      }
    }
    if (!_toAddIfPresent.isEmpty())
    {
      for(Proxy<Effect> proxy : _toAddIfPresent)
      {
        ret.add(proxy.getObject());
      }
    }
    if (_toExamine!=null)
    {
      ret.add(_toExamine.getObject());
    }
    return ret;
  }
}
