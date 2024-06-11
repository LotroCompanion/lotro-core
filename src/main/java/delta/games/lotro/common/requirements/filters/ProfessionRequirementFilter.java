package delta.games.lotro.common.requirements.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.crafting.CraftingStatus;
import delta.games.lotro.character.status.crafting.ProfessionStatus;
import delta.games.lotro.common.enums.CraftTier;
import delta.games.lotro.common.requirements.ProfessionRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.Profession;

/**
 * Filter on usage requirement that uses the crafting requirement.
 * The requirement is compared to the given crafting status.
 * @author DAM
 */
public class ProfessionRequirementFilter implements Filter<UsageRequirement>
{
  private CraftingStatus _status;

  /**
   * Constructor.
   * @param status Crafting status to use.
   */
  public ProfessionRequirementFilter(CraftingStatus status)
  {
    _status=status;
  }

  @Override
  public boolean accept(UsageRequirement item)
  {
    ProfessionRequirement professionRequirement=item.getProfessionRequirement();
    if (professionRequirement==null)
    {
      return true;
    }
    // Check profession
    Profession profession=professionRequirement.getProfession();
    List<Profession> activeProfessions=_status.getActiveProfessions();
    if (!activeProfessions.contains(profession))
    {
      return false;
    }
    // Check tier
    CraftTier requiredTier=professionRequirement.getTier();
    if (requiredTier==null)
    {
      return true;
    }
    ProfessionStatus professionStatus=_status.getProfessionStatus(profession);
    CraftingLevel proficiencyLevel=professionStatus.getProficiencyLevel();
    int proficiencyTier=proficiencyLevel.getTier();
    return (proficiencyTier>=requiredTier.getCode());
  }
}
