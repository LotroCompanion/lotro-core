package delta.games.lotro.business.crafting;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.status.crafting.CraftingStatus;
import delta.games.lotro.character.status.crafting.CraftingStatusManager;
import delta.games.lotro.character.status.crafting.KnownRecipes;
import delta.games.lotro.character.status.crafting.ProfessionStatus;
import delta.games.lotro.lore.crafting.Profession;

/**
 * Test class for the {@link KnownRecipesComputer}.
 * @author DAM
 */
class KnownRecipesComputerTest
{
  /**
   * Load a crafting status and show the recipes status for it.
   */
  @Test
  void test()
  {
    CraftingStatus craftingStatus=load();
    for(Profession profession : craftingStatus.getKnownProfessions())
    {
      ProfessionStatus professionStatus=craftingStatus.getProfessionStatus(profession);
      assertNotNull(professionStatus);
      KnownRecipes knownRecipes=professionStatus.getKnownRecipes();
      assertNotNull(knownRecipes);
      KnownRecipesComputer.showRecipesStatusForProfession(profession,knownRecipes);
    }
  }

  private CraftingStatus load()
  {
    CharacterFile file=CharactersManager.getInstance().getToonById("Landroval","Lorewyne");
    CraftingStatusManager craftingStatusMgr=file.getCraftingMgr();
    CraftingStatus craftingStatus=craftingStatusMgr.getCraftingStatus();
    return craftingStatus;
  }
}
