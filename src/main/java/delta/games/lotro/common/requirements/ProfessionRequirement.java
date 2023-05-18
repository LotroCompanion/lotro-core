package delta.games.lotro.common.requirements;

import delta.common.utils.NumericTools;
import delta.games.lotro.common.enums.CraftTier;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.crafting.CraftingSystem;
import delta.games.lotro.lore.crafting.Profession;

/**
 * Profession requirement.
 * @author DAM
 */
public class ProfessionRequirement
{
  private static final String SEPARATOR=";";
  private Profession _profession;
  private CraftTier _tier;

  /**
   * Constructor.
   * @param profession Profession.
   * @param tier Required tier (may be <code>null</code>).
   */
  public ProfessionRequirement(Profession profession, CraftTier tier)
  {
    _profession=profession;
    _tier=tier;
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
   * Get the required tier.
   * @return a tier (may be <code>null</code> if not tier requirement).
   */
  public CraftTier getTier()
  {
    return _tier;
  }

  /**
   * Get a string representation of this requirement.
   * @return A persistable string.
   */
  public String asString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_profession.getIdentifier());
    if (_tier!=null)
    {
      sb.append(SEPARATOR);
      sb.append(_tier.getCode());
    }
    return sb.toString();
  }

  /**
   * Build a profession requirement from a string.
   * @param input Input string ("professionID;tier").
   * @return A profession requirement or <code>null</code> if none.
   */
  public static ProfessionRequirement fromString(String input)
  {
    ProfessionRequirement ret=null;
    if ((input!=null) && (input.length()>0))
    {
      String[] parts=input.split(SEPARATOR);
      if (parts.length>=1)
      {
        int professionId=NumericTools.parseInt(parts[0],0);
        Profession profession=CraftingSystem.getInstance().getData().getProfessionsRegistry().getProfessionById(professionId);
        if (profession!=null)
        {
          CraftTier tier=null;
          if (parts.length>=2)
          {
            int tierCode=NumericTools.parseInt(parts[1],1);
            tier=LotroEnumsRegistry.getInstance().get(CraftTier.class).getEntry(tierCode);
          }
          ret=new ProfessionRequirement(profession,tier);
        }
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    if (_profession!=null)
    {
      String ret=_profession.getName();
      if (_tier!=null)
      {
        String tierName=_tier.getLabel();
        ret=ret+":"+tierName;
      }
      return ret;
    }
    return "";
  }
}
