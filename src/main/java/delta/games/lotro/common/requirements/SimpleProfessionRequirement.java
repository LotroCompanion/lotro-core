package delta.games.lotro.common.requirements;

import delta.games.lotro.lore.crafting.Profession;

/**
 * Profession requirement.
 * @author DAM
 */
public abstract class SimpleProfessionRequirement implements Requirement
{
  private Profession _profession;

  /**
   * Constructor.
   * @param profession Profession.
   */
  protected SimpleProfessionRequirement(Profession profession)
  {
    _profession=profession;
  }

  /**
   * Get the targeted profession.
   * @return a profession.
   */
  public Profession getProfession()
  {
    return _profession;
  }

  /**
   * Get a string representation of this requirement.
   * @return A persistable string.
   */
  public String asString()
  {
    return String.valueOf(_profession.getIdentifier());
  }

  @Override
  public String toString()
  {
    if (_profession!=null)
    {
      return _profession.getName();
    }
    return "";
  }
}
