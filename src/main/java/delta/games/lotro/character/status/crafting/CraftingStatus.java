package delta.games.lotro.character.status.crafting;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.ProfessionComparator;
import delta.games.lotro.lore.crafting.Vocation;

/**
 * Crafting status for a single toon.
 * @author DAM
 */
public class CraftingStatus
{
  private String _name;
  private Vocation _vocation;
  private HashMap<Profession,ProfessionStatus> _stats;
  private HashMap<Profession,GuildStatus> _guildStatuses;

  /**
   * Constructor.
   * @param toonName Character name.
   */
  public CraftingStatus(String toonName)
  {
    _name=toonName;
    _stats=new HashMap<Profession,ProfessionStatus>();
    _guildStatuses=new HashMap<Profession,GuildStatus>();
  }

  /**
   * Get the name of the managed toon.
   * @return the name of the managed toon.
   */
  public String getName()
  {
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
   * Get the guild status for a given profession.
   * @param profession Profession to use.
   * @param createIfNecessary Create a status if needed.
   * @return A guild status or <code>null</code> if no guild for this profession.
   */
  public GuildStatus getGuildStatus(Profession profession, boolean createIfNecessary)
  {
    GuildStatus status=_guildStatuses.get(profession);
    if ((status==null) && (createIfNecessary))
    {
      if (profession.hasGuild())
      {
        status=new GuildStatus(profession);
        _guildStatuses.put(profession,status);
      }
    }
    return status;
  }

  /**
   * Change vocation.
   * @param vocation New vocation.
   * @param date Date of new vocation.
   */
  public void changeVocation(Vocation vocation, long date)
  {
    // Check if vocation has changed...
    if (vocation==_vocation)
    {
      return;
    }
    if (_vocation==null)
    {
      // Vocation creation
      for(Profession profession : vocation.getProfessions())
      {
        ProfessionStatus stat=getProfessionStatus(profession,true);
        stat.initProfession(date);
      }
    }
    else if (vocation==null)
    {
      // No data removal to avoid losing history!
    }
    else
    {
      // Vocation change!
      // No data removal to avoid losing history!
      // Add new professions
      for(Profession profession : vocation.getProfessions())
      {
        ProfessionStatus stat=getProfessionStatus(profession);
        if (stat==null)
        {
          stat=getProfessionStatus(profession,true);
          stat.initProfession(date);
        }
      }
    }
    setVocation(vocation);
  }

  /**
   * Get all managed professions.
   * @return A list of sorted profession names.
   */
  public List<Profession> getProfessions()
  {
    return (_vocation!=null)?_vocation.getProfessions():new ArrayList<Profession>();
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
      // Guild?
      GuildStatus guildStatus=getGuildStatus(profession,false);
      if (guildStatus!=null)
      {
        ps.println("Guild: "+profession);
        guildStatus.getFactionStatus().dump(ps);
      }
    }
  }
}
