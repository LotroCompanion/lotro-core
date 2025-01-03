package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.utils.Proxy;

/**
 * Revive effect.
 * @author DAM
 */
public class ReviveEffect extends InstantEffect implements ParentEffect
{
  private List<ReviveVitalData> _reviveVitals;
  private List<Proxy<Effect>> _reviveEffects;

  /**
   * Constructor.
   */
  public ReviveEffect()
  {
    super();
    _reviveEffects=new ArrayList<Proxy<Effect>>();
    _reviveVitals=new ArrayList<ReviveVitalData>();
  }

  /**
   * Add a revive vital data.
   * @param reviveVitalData Element to add.
   */
  public void addReviveVitalData(ReviveVitalData reviveVitalData)
  {
    _reviveVitals.add(reviveVitalData);
  }

  /**
   * Get the revive vital data elements.
   * @return A list of revive vital data.
   */
  public List<ReviveVitalData> getReviveVitalData()
  {
    return _reviveVitals;
  }

  /**
   * Add a revive effect.
   * @param effect Effect to add.
   */
  public void addReviveEffect(Proxy<Effect> effect)
  {
    _reviveEffects.add(effect);
  }

  /**
   * Get the revive effects.
   * @return A list of effects.
   */
  public List<Proxy<Effect>> getReviveEffects()
  {
    return _reviveEffects;
  }

  @Override
  public Set<Effect> getChildEffects()
  {
    HashSet<Effect> ret=new HashSet<Effect>();
    for(Proxy<Effect> proxy : _reviveEffects)
    {
      ret.add(proxy.getObject());
    }
    return ret;
  }
}
