package delta.games.lotro.character.traits;

import delta.games.lotro.common.effects.Effect2;

/**
 * Gathers an effect and a rank.
 * @author DAM
 */
public class EffectAtRank
{
  private Effect2 _effect;
  private int _rank;

  /**
   * Constructor.
   * @param effect Effect.
   * @param rank Rank.
   */
  public EffectAtRank(Effect2 effect, int rank)
  {
    _effect=effect;
    _rank=rank;
  }

  /**
   * Get the managed effect.
   * @return the managed effect.
   */
  public Effect2 getEffect()
  {
    return _effect;
  }

  /**
   * Get the involved rank.
   * @return a rank.
   */
  public int getRank()
  {
    return _rank;
  }
}
