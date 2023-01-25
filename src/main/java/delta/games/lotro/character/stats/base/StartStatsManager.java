package delta.games.lotro.character.stats.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.comparators.NamedComparator;

/**
 * Manager for character start stats.
 * @author DAM
 */
public class StartStatsManager
{
  private static final Logger LOGGER=Logger.getLogger(StartStatsManager.class);

  private HashMap<ClassDescription,HashMap<Integer,BasicStatsSet>> _startStatsByClass;

  /**
   * Constructor.
   */
  public StartStatsManager()
  {
    _startStatsByClass=new HashMap<ClassDescription,HashMap<Integer,BasicStatsSet>>();
  }

  /**
   * Get the managed classes, sorted by name.
   * @return A list of classes.
   */
  public List<ClassDescription> getClasses()
  {
    List<ClassDescription> ret=new ArrayList<ClassDescription>();
    ret.addAll(_startStatsByClass.keySet());
    Collections.sort(ret,new NamedComparator());
    return ret;
  }

  /**
   * Set the start stats for a character class and level.
   * @param characterClass Character class.
   * @param level Level, starting at 1.
   * @param stats Start stats.
   */
  public void setStats(ClassDescription characterClass, int level, BasicStatsSet stats)
  {
    HashMap<Integer,BasicStatsSet> mapForClass=getMapForClass(characterClass);
    mapForClass.put(Integer.valueOf(level),stats);
  }

  /**
   * Get the available levels for a given class.
   * @param characterClass Character class.
   * @return A list of sorted levels.
   */
  public List<Integer> getLevels(ClassDescription characterClass)
  {
    HashMap<Integer,BasicStatsSet> mapForClass=getMapForClass(characterClass);
    List<Integer> levels=new ArrayList<Integer>(mapForClass.keySet());
    Collections.sort(levels);
    return levels;
  }

  /**
   * Get the start stats for a given character class and level.
   * @param characterClass Character class.
   * @param level Level, starting at 1.
   * @return Some stats or <code>null</code> if not supported.
   */
  public BasicStatsSet getStats(ClassDescription characterClass, int level)
  {
    HashMap<Integer,BasicStatsSet> mapForClass=getMapForClass(characterClass);
    BasicStatsSet ret=mapForClass.get(Integer.valueOf(level));
    if (ret==null)
    {
      LOGGER.warn("Could not find start stats for class="+characterClass+", level="+level);
      ret=new BasicStatsSet();
    }
    return ret;
  }

  private HashMap<Integer,BasicStatsSet> getMapForClass(ClassDescription characterClass)
  {
    HashMap<Integer,BasicStatsSet> ret=_startStatsByClass.get(characterClass);
    if (ret==null)
    {
      ret=new HashMap<Integer,BasicStatsSet>();
      _startStatsByClass.put(characterClass,ret);
    }
    return ret;
  }
}
