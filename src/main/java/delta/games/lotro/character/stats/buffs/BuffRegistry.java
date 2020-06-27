package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.games.lotro.character.BasicCharacterAttributes;
import delta.games.lotro.character.stats.buffs.comparators.BuffNameComparator;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Registry for buffs.
 * @author DAM
 */
public final class BuffRegistry
{
  private static final Logger LOGGER=Logger.getLogger(BuffRegistry.class);

  private static final BuffRegistry _instance=new BuffRegistry();
  private HashMap<String,Buff> _buffMap;

  /**
   * Get the global buffs registry.
   * @return the global buffs registry.
   */
  public static BuffRegistry getInstance()
  {
    return _instance;
  }

  /**
   * Constructor.
   */
  private BuffRegistry()
  {
    _buffMap=new HashMap<String,Buff>();
    BuffInitializer init=new BuffInitializer();
    init.initBuffs(this);
  }

  /**
   * Register a new buff.
   * @param buff Buff to register.
   */
  public void registerBuff(Buff buff)
  {
    String id=buff.getId();
    registerBuff(id,buff);
  }

  /**
   * Register a new buff.
   * @param id Buff ID.
   * @param buff Buff to register.
   */
  public void registerBuff(String id, Buff buff)
  {
    Buff old=_buffMap.put(id,buff);
    if (old!=null)
    {
      LOGGER.warn("Duplicate buff registration: "+id);
    }
  }

  /**
   * Get all buffs.
   * @return a list of all buffs.
   */
  private Set<Buff> getAllBuffs()
  {
    return new HashSet<Buff>(_buffMap.values());
  }

  /**
   * Build a selection of buffs.
   * @param attrs Attributes of toon to use.
   * @param buffs Buffs to skip.
   * @return A list of buffs.
   */
  public List<Buff> buildBuffSelection(BasicCharacterAttributes attrs, BuffsManager buffs)
  {
    List<Buff> ret=new ArrayList<Buff>();
    Set<String> ids=buffs.getBuffIds();
    BuffRegistry buffsRegistry=BuffRegistry.getInstance();
    Set<Buff> allBuffs=buffsRegistry.getAllBuffs();
    CharacterClass cClass=attrs.getCharacterClass();
    Race race=attrs.getRace();
    for(Buff buff : allBuffs)
    {
      if (!isSelectable(buff))
      {
        continue;
      }
      CharacterClass requiredClass=buff.getRequiredClass();
      if ((requiredClass==null) || (requiredClass==cClass))
      {
        Race requiredRace=buff.getRequiredRace();
        if ((requiredRace==null) || (requiredRace==race))
        {
          String buffId=buff.getId();
          if (!ids.contains(buffId))
          {
            ret.add(buff);
          }
        }
      }
    }
    Collections.sort(ret,new BuffNameComparator());
    return ret;
  }

  /**
   * Indicates if the given buff is selectable (=has no dedicated editor).
   * @param buff Buff to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isSelectable(Buff buff)
  {
    return buff.getType()!=BuffType.CLASS;
  }

  /**
   * Get a buff using its identifier.
   * @param buffId Identifier to use.
   * @return A buff or <code>null</code> if not found.
   */
  public Buff getBuffById(String buffId)
  {
    return _buffMap.get(buffId);
  }

  /**
   * Build a new buff instance.
   * @param buffId Buff identifier.
   * @return A new buff or <code>null</code> if buff not found.
   */
  public BuffInstance newBuffInstance(String buffId)
  {
    BuffInstance ret=null;
    Buff buff=getBuffById(buffId);
    if (buff!=null)
    {
      ret=new BuffInstance(buff);
    }
    return ret;
  }
}
