package delta.games.lotro.common.requirements.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.requirements.SimpleProfessionRequirement;
import delta.games.lotro.lore.crafting.Profession;

/**
 * Filter for a profession requirement.
 * @author DAM
 */
public class SimpleProfessionRequirementFilter implements Filter<SimpleProfessionRequirement>
{
  private Profession _profession;

  /**
   * Constructor.
   */
  public SimpleProfessionRequirementFilter()
  {
    _profession=null;
  }

  /**
   * Get the profession to use.
   * @return A profession or <code>null</code>.
   */
  public Profession getProfession()
  {
    return _profession;
  }

  /**
   * Set the profession to select.
   * @param profession Profession to use, may be <code>null</code>.
   */
  public void setProfession(Profession profession)
  {
    _profession=profession;
  }

  @Override
  public boolean accept(SimpleProfessionRequirement professionRequirement)
  {
    if (_profession==null)
    {
      return true;
    }
    if (professionRequirement==null)
    {
      return true;
    }
    Profession requiredProfession=professionRequirement.getProfession();
    return requiredProfession==_profession;
  }
}
