package delta.games.lotro.lore.instances;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.instances.comparators.PrivateEncounterNameComparator;
import delta.games.lotro.lore.instances.io.xml.PrivateEncountersXMLParser;

/**
 * Manager for all private encounters.
 * @author DAM
 */
public class PrivateEncountersManager
{
  private static final PrivateEncountersManager _instance=load();
  private Map<Integer,PrivateEncounter> _privateEncounters;

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
  }

  /**
   * Add a private encounter.
   * @param privateEncounter Private encounter to add.
   */
  public void addPrivateEncounter(PrivateEncounter privateEncounter)
  {
    Integer key=Integer.valueOf(privateEncounter.getIdentifier());
    _privateEncounters.put(key,privateEncounter);
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
    Collections.sort(ret,new PrivateEncounterNameComparator());
    return ret;
  }

  /**
   * Dump the contents of this manager.
   */
  public void dump()
  {
    List<PrivateEncounter> privateEncounters=getPrivateEncounters();
    System.out.println("Private encounters: ("+privateEncounters.size()+")");
    for(PrivateEncounter privateEncounter : privateEncounters)
    {
      System.out.println("* "+privateEncounter);
    }
  }
}
