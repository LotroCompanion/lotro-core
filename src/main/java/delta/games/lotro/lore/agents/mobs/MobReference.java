package delta.games.lotro.lore.agents.mobs;

import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.utils.Proxy;

/**
 * Mob reference.
 * @author DAM
 */
public class MobReference extends EntityClassification
{
  private Proxy<MobDescription> _mobProxy;

  /**
   * Constructor.
   */
  public MobReference()
  {
    super();
    _mobProxy=null;
  }

  /**
   * Get the proxy to a named mob.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<MobDescription> getMobProxy()
  {
    return _mobProxy;
  }

  /**
   * Set the proxy to a named mob.
   * @param mobProxy the proxy to set (may be <code>null</code>).
   */
  public void setMobProxy(Proxy<MobDescription> mobProxy)
  {
    _mobProxy=mobProxy;
  }

  /**
   * Get a displayable label for this mob reference.
   * @return a displayable label.
   */
  public String getLabel()
  {
    if (_mobProxy!=null)
    {
      String mobName=_mobProxy.getName();
      return mobName;
    }
    return super.getLabel();
  }

  @Override
  public String toString()
  {
    return super.toString()+", mob="+_mobProxy;
  }
}
