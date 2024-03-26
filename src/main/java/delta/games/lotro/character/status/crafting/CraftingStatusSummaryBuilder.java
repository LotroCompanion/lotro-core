package delta.games.lotro.character.status.crafting;

import delta.games.lotro.character.status.crafting.summary.CraftingStatusSummary;
import delta.games.lotro.character.status.crafting.summary.ProfessionStatusSummary;
import delta.games.lotro.character.status.reputation.FactionStatus;
import delta.games.lotro.common.enums.CraftTier;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.reputation.FactionLevel;

/**
 * Builds crafting status summaries from a full crafting status.
 * @author DAM
 */
public class CraftingStatusSummaryBuilder
{
  /**
   * Build a crafting status summary from the given crafting status.
   * @param status Status to use.
   * @return A crafting status summary.
   */
  public CraftingStatusSummary buildSummary(CraftingStatus status)
  {
    CraftingStatusSummary ret=new CraftingStatusSummary();
    for(Profession profession : status.getActiveProfessions())
    {
      ProfessionStatus professionStatus=status.getProfessionStatus(profession,true);
      ProfessionStatusSummary professionSummary=buildSummary(status,professionStatus);
      ret.addProfessionStatus(professionSummary);
    }
    return ret;
  }

  private ProfessionStatusSummary buildSummary(CraftingStatus craftingStatus, ProfessionStatus status)
  {
    Profession profession=status.getProfession();
    ProfessionStatusSummary ret=new ProfessionStatusSummary(profession);
    // Mastery
    CraftingLevel masteryLevel=status.getMasteryLevel();
    CraftTier mastery=(masteryLevel!=null)?masteryLevel.getCraftTier():null;
    ret.setMastery(mastery);
    // Proficiency
    CraftingLevel proficiencyLevel=status.getProficiencyLevel();
    CraftTier proficiency=(proficiencyLevel!=null)?proficiencyLevel.getCraftTier():null;
    ret.setProficiency(proficiency);
    // Guild
    GuildStatus guildStatus=craftingStatus.getGuildStatus(profession,false);
    if (guildStatus!=null)
    {
      FactionStatus factionStatus=guildStatus.getFactionStatus();
      FactionLevel guildLevel=factionStatus.getFactionLevel();
      ret.setGuildLevel(guildLevel);
    }
    return ret;
  }
}
