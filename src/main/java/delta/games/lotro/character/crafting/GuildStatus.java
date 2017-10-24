package delta.games.lotro.character.crafting;

import delta.games.lotro.character.reputation.FactionStatus;
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

  private FactionStatus _factionStatus;

  /**
   * Constructor.
   */
  public GuildStatus()
  {
    _profession=null;
    Faction guildFaction=FactionsRegistry.getInstance().getGuildFaction();
    _factionStatus=new FactionStatus(guildFaction);
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
  public void setProfession(Profession profession)
  {
    _profession=profession;
  }

  /**
   * Initialize guild.
   * @param date Event date.
   */
  public void initGuild(long date)
  {
    _factionStatus.init(date);
  }

  /**
   * Get the guild faction status.
   * @return the guild faction status.
   */
  public FactionStatus getFactionStatus()
  {
    return _factionStatus;
  }
}
