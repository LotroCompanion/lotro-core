package delta.games.lotro.character.crafting;

import delta.games.lotro.character.reputation.FactionStatus;
import delta.games.lotro.lore.crafting.Profession;
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
   * Change guild profession.
   * @param profession Profession to use.
   * @param date Date of change.
   */
  public void changeProfession(Profession profession, long date)
  {
    if (_profession!=profession)
    {
      _factionStatus.init(date);
      setProfession(profession);
    }
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
