package delta.games.lotro.lore.items.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.effects.Effect2;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.enums.EquipmentCategory;
import delta.games.lotro.common.stats.SpecialEffect;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Item effects for an equipment category.
 * @author DAM
 */
public class GenericItemEffects
{
  private EquipmentCategory _category;
  private List<Effect2> _effects;
  private StatsProvider _statsProvider;

  /**
   * Constructor.
   * @param category Equipment category.
   */
  public GenericItemEffects(EquipmentCategory category)
  {
    _category=category;
    _effects=new ArrayList<Effect2>();
  }

  /**
   * Get the managed equipment category.
   * @return the managed equipment category.
   */
  public EquipmentCategory getCategory()
  {
    return _category;
  }

  /**
   * Add an effect.
   * @param effect Effect to add.
   */
  public void addEffect(Effect2 effect)
  {
    _effects.add(effect);
  }

  /**
   * Get the managed effects.
   * @return a list of effects.
   */
  public List<Effect2> getEffects()
  {
    return _effects;
  }

  /**
   * Get the stats provider.
   * @return the stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    if (_statsProvider==null)
    {
      _statsProvider=buildStatsProvider();
    }
    return _statsProvider;
  }

  private StatsProvider buildStatsProvider()
  {
    StatsProvider ret=new StatsProvider();
    for(Effect2 effect : _effects)
    {
      if (effect instanceof PropertyModificationEffect)
      {
        PropertyModificationEffect propModEffect=(PropertyModificationEffect)effect;
        StatsProvider provider=propModEffect.getStatsProvider();
        addProvider(provider,ret);
      }
    }
    return ret;
  }

  private void addProvider(StatsProvider from, StatsProvider to)
  {
    // Stat providers
    int nbProviders=from.getNumberOfStatProviders();
    for(int i=0;i<nbProviders;i++)
    {
      to.addStatProvider(from.getStatProvider(i));
    }
    // Special effects
    for(SpecialEffect effect : from.getSpecialEffects())
    {
      to.addSpecialEffect(effect);
    }
  }
}
