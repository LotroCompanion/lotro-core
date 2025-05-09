package delta.games.lotro.common.requirements;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;

/**
 * Trait requirement.
 * @author DAM
 */
public class TraitRequirement implements Requirement
{
  private TraitDescription _trait;

  /**
   * Constructor.
   * @param trait Trait.
   */
  public TraitRequirement(TraitDescription trait)
  {
    _trait=trait;
  }

  /**
   * Get the targeted trait.
   * @return a trait.
   */
  public TraitDescription getTrait()
  {
    return _trait;
  }

  /**
   * Get a string representation of this requirement.
   * @return A persistable string.
   */
  public String asString()
  {
    return String.valueOf(_trait.getIdentifier());
  }

  /**
   * Build a faction requirement from a string.
   * @param input Input string ("factionKey;tier").
   * @return A faction requirement or <code>null</code> if none.
   */
  public static TraitRequirement fromString(String input)
  {
    int traitId=NumericTools.parseInt(input,0);
    TraitDescription trait=TraitsManager.getInstance().getTrait(traitId);
    TraitRequirement ret=new TraitRequirement(trait);
    return ret;
  }

  @Override
  public String toString()
  {
    if (_trait!=null)
    {
      return "Trait "+_trait.getName();
    }
    return "";
  }
}
