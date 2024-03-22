package delta.games.lotro.lore.instances;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.instances.io.xml.PrivateEncountersXMLParser;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Manager for all private encounters.
 * @author DAM
 */
public class PrivateEncountersManager
{
  private static final Logger LOGGER=Logger.getLogger(PrivateEncountersManager.class);

  private static final PrivateEncountersManager _instance=load();
  private Map<Integer,PrivateEncounter> _privateEncounters;
  private Map<Integer,PrivateEncounter> _questToPE;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static PrivateEncountersManager getInstance()
  {
    return _instance;
  }

  private static PrivateEncountersManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.PRIVATE_ENCOUNTERS);
    return new PrivateEncountersXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public PrivateEncountersManager()
  {
    _privateEncounters=new HashMap<Integer,PrivateEncounter>();
    _questToPE=new HashMap<Integer,PrivateEncounter>();
  }

  /**
   * Add a private encounter.
   * @param privateEncounter Private encounter to add.
   */
  public void addPrivateEncounter(PrivateEncounter privateEncounter)
  {
    Integer key=Integer.valueOf(privateEncounter.getIdentifier());
    _privateEncounters.put(key,privateEncounter);
    // Quest
    Proxy<QuestDescription> parentQuest=privateEncounter.getQuests().getParentQuest();
    if (parentQuest!=null)
    {
      key=Integer.valueOf(parentQuest.getId());
      PrivateEncounter old=_questToPE.put(key,privateEncounter);
      if (old!=null)
      {
        LOGGER.warn("Several PE use the same parent quest: "+parentQuest.getId());
      }
    }
  }

  /**
   * Get a private encounter using its identifier.
   * @param id Private encounter identifier.
   * @return A private encounter or <code>null</code> if not found.
   */
  public PrivateEncounter getPrivateEncounterById(int id)
  {
    return _privateEncounters.get(Integer.valueOf(id));
  }

  /**
   * Get the private encounter for the given quest.
   * @param questId Quest identifier.
   * @return A private encounter or <code>null</code> if none.
   */
  public PrivateEncounter getPrivateEncounterForQuest(int questId)
  {
    return _questToPE.get(Integer.valueOf(questId));
  }

  /**
   * Get all private encounters.
   * @return a list of private encounters.
   */
  public List<PrivateEncounter> getPrivateEncounters()
  {
    List<PrivateEncounter> ret=new ArrayList<PrivateEncounter>();
    ret.addAll(_privateEncounters.values());
    Collections.sort(ret,new IdentifiableComparator<PrivateEncounter>());
    return ret;
  }

  /**
   * Get all the skirmish private encounters.
   * @return A list of skirmish private encounters, sorted by name.
   */
  public List<SkirmishPrivateEncounter> getSkirmishPrivateEncounters()
  {
    List<SkirmishPrivateEncounter> ret=new ArrayList<SkirmishPrivateEncounter>();
    for(PrivateEncounter pe : _privateEncounters.values())
    {
      if (pe instanceof SkirmishPrivateEncounter)
      {
        ret.add((SkirmishPrivateEncounter)pe);
      }
    }
    Collections.sort(ret,new NamedComparator());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    List<PrivateEncounter> privateEncounters=getPrivateEncounters();
    out.println("Private encounters: ("+privateEncounters.size()+")");
    for(PrivateEncounter privateEncounter : privateEncounters)
    {
      out.println("* "+privateEncounter);
    }
  }
}
