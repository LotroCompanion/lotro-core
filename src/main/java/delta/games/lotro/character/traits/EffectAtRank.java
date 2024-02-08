package delta.games.lotro.character.traits;

import delta.games.lotro.common.effects.Effect;

/**
 * Gathers an effect and a rank.
 * @author DAM
 */
public class EffectAtRank
{
  private Effect _effect;
  private int _rank;

  /**
   * Constructor.
   * @param effect Effect.
   * @param rank Rank.
   */
  public EffectAtRank(Effect effect, int rank)
  {
    _effect=effect;
    _rank=rank;
  }

  /**
   * Get the managed effect.
   * @return the managed effect.
   */
  public Effect getEffect()
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
