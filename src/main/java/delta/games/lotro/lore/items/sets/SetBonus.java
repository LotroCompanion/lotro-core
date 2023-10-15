package delta.games.lotro.lore.items.sets;

import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Bonus of an items set.
 * @author DAM
 */
public class SetBonus
{
  private int _piecesCount;
  private StatsProvider _stats;
  private ItemSetEffectsManager _effects;

  /**
   * Constructor.
   * @param piecesCount Number of pieces to activate the bonus.
   */
  public SetBonus(int piecesCount)
  {
    _piecesCount=piecesCount;
  }

  /**
   * Get the number of pieces for this bonus.
   * @return a pieces count.
   */
  public int getPiecesCount()
  {
    return _piecesCount;
  }

  /**
   * Get the bonus.
   * @return a stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    return _stats;
  }

  /**
   * Set the bonus.
   * @param statsProvider Stats provider.
   */
  public void setStatsProvider(StatsProvider statsProvider)
  {
    _stats=statsProvider;
  }

  @Override
  public String toString()
  {
    return _piecesCount+": "+_stats;
  }

  /**
   * Get the effects manager.
   * @return an effects manager or <code>null</code> if no effects.
   */
  public ItemSetEffectsManager getEffects()
  {
    return _effects;
  }

  /**
   * Add an effect to this bonus.
   * @param effect Effect to add.
   */
  public void addEffect(EffectGenerator effect)
  {
    if (_effects==null)
    {
      _effects=new ItemSetEffectsManager();
    }
    _effects.addEffect(effect);
  }
}
