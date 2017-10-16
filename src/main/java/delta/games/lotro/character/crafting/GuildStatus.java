package delta.games.lotro.character.crafting;

import delta.games.lotro.character.reputation.FactionData;
import delta.games.lotro.crafting.Profession;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Guild status.
 * @author DAM
 */
public class GuildStatus
{
  private Profession _profession;

  private FactionData _factionData;

  /**
   * Constructor.
   */
  public GuildStatus()
  {
    _profession=null;
    Faction guildFaction=FactionsRegistry.getInstance().getGuildFaction();
    _factionData=new FactionData(guildFaction);
  }

  /**
   * Get the profession of the guild.
   * @return A profession or <code>null</code>.
   */
  public Profession getProfession()
  {
    return _profession;
  }

  /**
   * Set the profession of the guild.
   * @param profession Profession to set.
   */
  public void setProfesssion(Profession profession)
  {
    _profession=profession;
  }

  /**
   * Get the guild faction data.
   * @return the guild faction data.
   */
  public FactionData getFactionData()
  {
    return _factionData;
  }
}
