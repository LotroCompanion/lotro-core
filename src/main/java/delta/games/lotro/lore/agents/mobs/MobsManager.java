package delta.games.lotro.lore.agents.mobs;

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
import delta.games.lotro.lore.agents.mobs.io.xml.MobsXMLParser;

/**
 * Manager for all mobs.
 * @author DAM
 */
public class MobsManager
{
  private static final MobsManager _instance=load();
  private Map<Integer,MobDescription> _mobs;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static MobsManager getInstance()
  {
    return _instance;
  }

  private static MobsManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.MOBS);
    return new MobsXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public MobsManager()
  {
    _mobs=new HashMap<Integer,MobDescription>();
  }

  /**
   * Add a mob.
   * @param mob Mob to add.
   */
  public void addMob(MobDescription mob)
  {
    Integer key=Integer.valueOf(mob.getIdentifier());
    _mobs.put(key,mob);
  }

  /**
   * Get a mob using its identifier.
   * @param id Mob identifier.
   * @return A mob or <code>null</code> if not found.
   */
  public MobDescription getMobById(int id)
  {
    return _mobs.get(Integer.valueOf(id));
  }

  /**
   * Get all mobs.
   * @return a list of mobs.
   */
  public List<MobDescription> getMobs()
  {
    List<MobDescription> ret=new ArrayList<MobDescription>();
    ret.addAll(_mobs.values());
    Collections.sort(ret,new IdentifiableComparator<MobDescription>());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    List<MobDescription> mobs=getMobs();
    out.println("Mobs: ("+mobs.size()+")");
    for(MobDescription mob : mobs)
    {
      out.println("* "+mob);
    }
  }
}
