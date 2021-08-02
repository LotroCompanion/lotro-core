package delta.games.lotro.character.status.crafting;

import delta.games.lotro.character.status.reputation.FactionStatus;
import delta.games.lotro.lore.crafting.Profession;

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
   * @param profession Profession.
   */
  public GuildStatus(Profession profession)
  {
    _profession=profession;
    _factionStatus=new FactionStatus(profession.getGuildFaction());
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
   * Get the guild faction status.
   * @return the guild faction status.
   */
  public FactionStatus getFactionStatus()
  {
    return _factionStatus;
  }
}
