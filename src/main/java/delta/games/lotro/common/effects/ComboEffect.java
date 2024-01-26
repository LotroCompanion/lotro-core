package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.utils.Proxy;

/**
 * Combo effect.
 * @author DAM
 */
public class ComboEffect extends Effect2
{
  private List<Proxy<Effect2>> _presentList;
  private Proxy<Effect2> _toAddIfNotPresent;
  private Proxy<Effect2> _toAddIfPresent;
  private Proxy<Effect2> _toGiveBackIfNotPresent;
  private Proxy<Effect2> _toGiveBackIfPresent;
  private Proxy<Effect2> _toExamine;

  /**
   * Constructor.
   */
  public ComboEffect()
  {
    super();
    _presentList=new ArrayList<Proxy<Effect2>>();
  }

  /**
   * Add a 'present' effect.
   * @param effect Effect to add.
   */
  public void addPresentEffect(Proxy<Effect2> effect)
  {
    _presentList.add(effect);
  }

  /**
   * Get the 'present' effects.
   * @return A list of effect proxies (may be empty but never <code>null</code>).
   */
  public List<Proxy<Effect2>> getPresentEffects()
  {
    return _presentList;
  }

  /**
   * Get the 'to add if not present' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect2> getToAddIfNotPresent()
  {
    return _toAddIfNotPresent;
  }

  /**
   * Set the 'to add if not present' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToAddIfNotPresent(Proxy<Effect2> effect)
  {
    _toAddIfNotPresent=effect;
  }

  /**
   * Get the 'to add if present' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect2> getToAddIfPresent()
  {
    return _toAddIfPresent;
  }

  /**
   * Set the 'to add if present' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToAddIfPresent(Proxy<Effect2> effect)
  {
    _toAddIfPresent=effect;
  }

  /**
   * Get the 'to give back if not present' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect2> getToGiveBackIfNotPresent()
  {
    return _toGiveBackIfNotPresent;
  }

  /**
   * Set the 'to give back if not present' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToGiveBackIfNotPresent(Proxy<Effect2> effect)
  {
    _toGiveBackIfNotPresent=effect;
  }

  /**
   * Get the 'to give back if present' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect2> getToGiveBackIfPresent()
  {
    return _toGiveBackIfPresent;
  }

  /**
   * Set the 'to give back if present' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToGiveBackIfPresent(Proxy<Effect2> effect)
  {
    _toGiveBackIfPresent=effect;
  }

  /**
   * Get the 'to examine' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect2> getToExamine()
  {
    return _toExamine;
  }

  /**
   * Set the 'to examine' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToExamine(Proxy<Effect2> effect)
  {
    _toExamine=effect;
  }
}
