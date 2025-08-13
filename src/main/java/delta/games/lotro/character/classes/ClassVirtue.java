package delta.games.lotro.character.classes;

import delta.games.lotro.character.traits.TraitDescription;

/**
 * Virtue for a (monster play) class.
 * @author DAM
 */
public class ClassVirtue
{
  private int _startRank;
  private int _maxRank;
  private TraitDescription _trait;

  /**
   * Constructor.
   * @param startRank Start rank.
   * @param maxRank Max rank.
   * @param trait Trait.
   */
  public ClassVirtue(int startRank, int maxRank, TraitDescription trait)
  {
    _startRank=startRank;
    _maxRank=maxRank;
    _trait=trait;
  }

  /**
   * Get the start rank for this virtue.
   * @return a rank.
   */
  public int getStartRank()
  {
    return _startRank;
  }

  /**
   * Get the max rank for this virtue.
   * @return a rank.
   */
  public int getMaxRank()
  {
    return _maxRank;
  }

  /**
   * Get the managed trait.
   * @return a trait.
   */
  public TraitDescription getTrait()
  {
    return _trait;
  }
}
