package delta.games.lotro.character.crafting;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.crafting.Profession;
import delta.games.lotro.crafting.ProfessionComparator;
import delta.games.lotro.crafting.Vocation;

/**
 * Crafting status for a single toon.
 * @author DAM
 */
public class CraftingStatus
{
  private String _name;
  private Vocation _vocation;
  private HashMap<Profession,ProfessionStatus> _stats;
  private GuildStatus _guildStatus;

  /**
   * Constructor.
   * @param toonName Character name.
   */
  public CraftingStatus(String toonName)
  {
    _name=toonName;
    _stats=new HashMap<Profession,ProfessionStatus>();
    _guildStatus=new GuildStatus();
  }

  /**
   * Get the name of the managed toon.
   * @return the name of the managed toon.
   */
  public String getName() {
    return _name;
  }

  /**
   * Get the current vocation.
   * @return A vocation name.
   */
  public Vocation getVocation()
  {
    return _vocation;
  }

  /**
   * Set the current vocation.
   * @param vocation Vocation to set.
   */
  public void setVocation(Vocation vocation)
  {
    _vocation=vocation;
  }

  /**
   * Get guild status.
   * @return guild status.
   */
  public GuildStatus getGuildStatus()
  {
    return _guildStatus;
  }

  /**
   * Reset vocation.
   * @param vocation Vocation to use.
   * @param date Start date for the given vocation.
   */
  public void resetVocation(Vocation vocation, long date)
  {
    setVocation(vocation);
    _stats.clear();
    if (vocation!=null)
    {
      Profession[] professions=vocation.getProfessions();
      for(Profession profession : professions)
      {
        ProfessionStatus stat=getProfessionStatus(profession,true);
        stat.initProfession(date);
      }
    }
  }

  /**
   * Get all managed professions.
   * @return A array of sorted profession names.
   */
  public Profession[] getProfessions()
  {
    return (_vocation!=null)?_vocation.getProfessions():new Profession[0];
  }

  /**
   * Get all managed profession status.
   * @return A collection of profession status.
   */
  public Collection<ProfessionStatus> getProfessionStatus()
  {
    return _stats.values();
  }

  /**
   * Get the status for a given profession.
   * @param profession Profession name.
   * @return A profession status object, or <code>null</code> if the toon does not
   * have the given profession.
   */
  public ProfessionStatus getProfessionStatus(Profession profession)
  {
    ProfessionStatus stat=_stats.get(profession);
    return stat;
  }

  /**
   * Get the status for a given profession.
   * @param profession Profession name.
   * @param createItIfNeeded Create status if needed.
   * @return A profession status.
   */
  public ProfessionStatus getProfessionStatus(Profession profession, boolean createItIfNeeded)
  {
    ProfessionStatus stat=_stats.get(profession);
    if ((stat==null) && (createItIfNeeded))
    {
      stat=new ProfessionStatus(profession);
      _stats.put(profession,stat);
    }
    return stat;
  }

  /**
   * Dump the contents of this object to the given stream.
   * @param ps Output stream to use.
   */
  public void dump(PrintStream ps)
  {
    ps.println("Crafting status for ["+_name+"]:");
    // Vocation
    ps.println("Vocation:"+_vocation);
    // Professions
    ps.println("Professions:");
    List<Profession> professions=new ArrayList<Profession>(_stats.keySet());
    Collections.sort(professions,new ProfessionComparator());
    for(Profession profession : professions)
    {
      ProfessionStatus stat=getProfessionStatus(profession);
      stat.dump(ps);
    }
    // Guild
    Profession guildProfession=_guildStatus.getProfession();
    if (guildProfession!=null)
    {
      ps.println("Guild:"+guildProfession);
      _guildStatus.getFactionStatus().dump(ps);
    }
  }
}
