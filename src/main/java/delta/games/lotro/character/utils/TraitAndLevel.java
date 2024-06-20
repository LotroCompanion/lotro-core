package delta.games.lotro.character.utils;

import delta.games.lotro.character.traits.TraitDescription;

/**
 * A trait and a level.
 * @author DAM
 */
public class TraitAndLevel
{
  private int _requiredLevel;
  private TraitDescription _trait;

  /**
   * Constructor.
   * @param requiredLevel Required level.
   * @param trait Trait.
   */
  public TraitAndLevel(int requiredLevel, TraitDescription trait)
  {
    _requiredLevel=requiredLevel;
    _trait=trait;
  }

  /**
   * Get the character level required for this trait.
   * @return a character level.
   */
  public int getRequiredLevel()
  {
    return _requiredLevel;
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
