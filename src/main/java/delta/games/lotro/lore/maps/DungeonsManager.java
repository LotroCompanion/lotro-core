package delta.games.lotro.lore.maps;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.maps.io.xml.DungeonXMLParser;

/**
 * Manager for all dungeons.
 * @author DAM
 */
public class DungeonsManager
{
  private static DungeonsManager _instance=null;
  private Map<Integer,Dungeon> _dungeons;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static DungeonsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  private static DungeonsManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.DUNGEONS);
    return new DungeonXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public DungeonsManager()
  {
    _dungeons=new HashMap<Integer,Dungeon>();
  }

  /**
   * Add a dungeon.
   * @param dungeon Dungeon to add.
   */
  public void addDungeon(Dungeon dungeon)
  {
    Integer key=Integer.valueOf(dungeon.getIdentifier());
    _dungeons.put(key,dungeon);
  }

  /**
   * Get a dungeon using its identifier.
   * @param dungeonId Dungeon identifier.
   * @return A dungeon or <code>null</code> if not found.
   */
  public Dungeon getDungeonById(int dungeonId)
  {
    return _dungeons.get(Integer.valueOf(dungeonId));
  }

  /**
   * Get all dungeons.
   * @return a list of dungeons.
   */
  public List<Dungeon> getDungeons()
  {
    List<Dungeon> ret=new ArrayList<Dungeon>();
    ret.addAll(_dungeons.values());
    Collections.sort(ret,new IdentifiableComparator<Dungeon>());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    List<Dungeon> dungeons=getDungeons();
    out.println("Dungeons: ("+dungeons.size()+")");
    for(Dungeon dungeon : dungeons)
    {
      out.println("\t"+dungeon);
    }
  }
}
