
package delta.games.lotro.lore.maps.resources;

import java.util.Comparator;

import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.ProfessionComparator;

/**
 * Comparator for resources map descriptors.
 * @author DAM
 */
public class ResourcesMapDescriptorComparator implements Comparator<ResourcesMapDescriptor>
{
  private ProfessionComparator _professionComparator;

  /**
   * Constructor.
   */
  public ResourcesMapDescriptorComparator()
  {
    _professionComparator=new ProfessionComparator();
  }

  @Override
  public int compare(ResourcesMapDescriptor rmd1, ResourcesMapDescriptor rmd2)
  {
    // Compare professions
    CraftingLevel level1=rmd1.getLevel();
    Profession profession1=level1.getProfession();
    CraftingLevel level2=rmd2.getLevel();
    Profession profession2=level2.getProfession();
    int professionComparison=_professionComparator.compare(profession1,profession2);
    if (professionComparison!=0)
    {
      return professionComparison;
    }
    // Compare tiers
    int tier1=level1.getTier();
    int tier2=level2.getTier();
    return Integer.compare(tier1,tier2);
  }
}
