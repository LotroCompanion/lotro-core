package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.utils.Proxy;

/**
 * Dispel effect.
 * @author DAM
 */
public class DispelEffect extends InstantEffect implements ParentEffect
{
  /*
Effect_Dispel_ByEffectList: 
  #1: Effect_WSLEffect 1879415740
  #2: Effect_WSLEffect 1879415438
  #3: Effect_WSLEffect 1879415437
Effect_Dispel_ByTypeList: 
  #1: Effect_EquivalenceClass 2764 (Landscape_Difficulty_Propmod)
  #2: Effect_EquivalenceClass 2953 (Landscape_Difficulty_Reward_Killproc)
Effect_Dispel_DispelCasters: 0
   */
  private List<Proxy<Effect>> _effectsList;
  private boolean _dispelCasters;

  /**
   * Constructor.
   */
  public DispelEffect()
  {
    super();
    _effectsList=new ArrayList<Proxy<Effect>>();
    _dispelCasters=false;
  }

  /**
   * Add an effect.
   * @param effect Effect to add.
   */
  public void addEffect(Proxy<Effect> effect)
  {
    _effectsList.add(effect);
  }

  /**
   * Get the effects.
   * @return A list of effect proxies (may be empty but never <code>null</code>).
   */
  public List<Proxy<Effect>> getEffects()
  {
    return _effectsList;
  }

  /**
   * Get the 'dispel casters' flag.
   * @return the flag value.
   */
  public boolean isDispelCasters()
  {
    return _dispelCasters;
  }

  /**
   * Set the 'dispel casters' flag.
   * @param dispelCasters Value to set.
   */
  public void setDispelCasters(boolean dispelCasters)
  {
    _dispelCasters=dispelCasters;
  }

  @Override
  public Set<Effect> getChildEffects()
  {
    HashSet<Effect> ret=new HashSet<Effect>();
    for(Proxy<Effect> proxy : _effectsList)
    {
      ret.add(proxy.getObject());
    }
    return ret;
  }
}
