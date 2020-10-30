package delta.games.lotro.lore.items;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.io.xml.ContainerXMLParser;

/**
 * Manager for all containers.
 * @author DAM
 */
public class ContainersManager
{
  private static final ContainersManager _instance=load();
  private Map<Integer,Container> _maps;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static ContainersManager getInstance()
  {
    return _instance;
  }

  private static ContainersManager load()
  {
    ContainersManager containersMgr=new ContainersManager();
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.CONTAINERS);
    List<Container> containers=new ContainerXMLParser().parseXML(from);
    for(Container container : containers)
    {
      containersMgr.addContainer(container);
    }
    return containersMgr;
  }

  /**
   * Constructor.
   */
  public ContainersManager()
  {
    _maps=new HashMap<Integer,Container>();
  }

  /**
   * Add a container.
   * @param container Container to add.
   */
  public void addContainer(Container container)
  {
    Integer key=Integer.valueOf(container.getIdentifier());
    _maps.put(key,container);
  }

  /**
   * Get a container using its identifier.
   * @param containerId Container identifier.
   * @return A container or <code>null</code> if not found.
   */
  public Container getContainerById(int containerId)
  {
    return _maps.get(Integer.valueOf(containerId));
  }

  /**
   * Get all containers.
   * @return A list of containers, sorted by identifier.
   */
  public List<Container> getContainers()
  {
    List<Container> ret=new ArrayList<Container>(_maps.values());
    Collections.sort(ret,new IdentifiableComparator<Container>());
    return ret;
  }
}
