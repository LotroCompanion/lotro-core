package delta.games.lotro.lore.xrefs.relics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.Container;
import delta.games.lotro.lore.items.io.xml.ContainerXMLParser;
import delta.games.lotro.lore.items.legendary.relics.RelicsContainer;

/**
 * Finds references to relics.
 * @author DAM
 */
public class RelicReferencesBuilder
{
  private List<RelicReference<?>> _storage;

  /**
   * Constructor.
   */
  public RelicReferencesBuilder()
  {
    _storage=new ArrayList<RelicReference<?>>();
  }

  /**
   * Search for an item.
   * @param itemId Item identifier.
   * @return the found references.
   */
  public List<RelicReference<?>> inspectItem(int itemId)
  {
    _storage.clear();
    findInContainers(itemId);
    List<RelicReference<?>> ret=new ArrayList<RelicReference<?>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInContainers(int itemId)
  {
    File containersFile=LotroCoreConfig.getInstance().getFile(DataFiles.CONTAINERS);
    List<Container> containers=new ContainerXMLParser().parseXML(containersFile);
    for(Container container : containers)
    {
      if (container instanceof RelicsContainer)
      {
        RelicsContainer relicsContainer=(RelicsContainer)container;
        boolean found=relicsContainer.contains(itemId);
        if (found)
        {
          _storage.add(new RelicReference<Container>(container,RelicRole.CONTAINED_IN));
        }
      }
    }
  }
}
