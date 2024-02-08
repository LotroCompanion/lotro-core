package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.utils.Proxy;

/**
 * Combo effect.
 * @author DAM
 */
public class ComboEffect extends Effect
{
  private List<Proxy<Effect>> _presentList;
  private Proxy<Effect> _toAddIfNotPresent;
  private Proxy<Effect> _toAddIfPresent;
  private Proxy<Effect> _toGiveBackIfNotPresent;
  private Proxy<Effect> _toGiveBackIfPresent;
  private Proxy<Effect> _toExamine;

  /**
   * Constructor.
   */
  public ComboEffect()
  {
    super();
    _presentList=new ArrayList<Proxy<Effect>>();
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
   * Get the 'to add if not present' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect> getToAddIfNotPresent()
  {
    return _toAddIfNotPresent;
  }

  /**
   * Set the 'to add if not present' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToAddIfNotPresent(Proxy<Effect> effect)
  {
    _toAddIfNotPresent=effect;
  }

  /**
   * Get the 'to add if present' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect> getToAddIfPresent()
  {
    return _toAddIfPresent;
  }

  /**
   * Set the 'to add if present' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToAddIfPresent(Proxy<Effect> effect)
  {
    _toAddIfPresent=effect;
  }

  /**
   * Get the 'to give back if not present' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect> getToGiveBackIfNotPresent()
  {
    return _toGiveBackIfNotPresent;
  }

  /**
   * Set the 'to give back if not present' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToGiveBackIfNotPresent(Proxy<Effect> effect)
  {
    _toGiveBackIfNotPresent=effect;
  }

  /**
   * Get the 'to give back if present' effect proxy.
   * @return A proxy or <code>null</code> if none.
   */
  public Proxy<Effect> getToGiveBackIfPresent()
  {
    return _toGiveBackIfPresent;
  }

  /**
   * Set the 'to give back if present' effect proxy.
   * @param effect A proxy or <code>null</code> if none.
   */
  public void setToGiveBackIfPresent(Proxy<Effect> effect)
  {
    _toGiveBackIfPresent=effect;
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
}
