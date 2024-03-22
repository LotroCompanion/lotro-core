package delta.games.lotro.lore.agents.npcs;

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
import delta.games.lotro.lore.agents.npcs.io.xml.NPCsXMLParser;

/**
 * Manager for all NPCs.
 * @author DAM
 */
public class NPCsManager
{
  private static final NPCsManager _instance=load();
  private Map<Integer,NpcDescription> _npcs;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static NPCsManager getInstance()
  {
    return _instance;
  }

  private static NPCsManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.NPCS);
    return new NPCsXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public NPCsManager()
  {
    _npcs=new HashMap<Integer,NpcDescription>();
  }

  /**
   * Add a NPC.
   * @param npc NPC to add.
   */
  public void addNPC(NpcDescription npc)
  {
    Integer key=Integer.valueOf(npc.getIdentifier());
    _npcs.put(key,npc);
  }

  /**
   * Get a NPC using its identifier.
   * @param id NPC identifier.
   * @return A NPC or <code>null</code> if not found.
   */
  public NpcDescription getNPCById(int id)
  {
    return _npcs.get(Integer.valueOf(id));
  }

  /**
   * Get all NPCs.
   * @return a list of NPCs.
   */
  public List<NpcDescription> getNPCs()
  {
    List<NpcDescription> ret=new ArrayList<NpcDescription>();
    ret.addAll(_npcs.values());
    Collections.sort(ret,new IdentifiableComparator<NpcDescription>());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    List<NpcDescription> npcs=getNPCs();
    out.println("NPCs: ("+npcs.size()+")");
    for(NpcDescription npc : npcs)
    {
      out.println("* "+npc);
    }
  }
}
