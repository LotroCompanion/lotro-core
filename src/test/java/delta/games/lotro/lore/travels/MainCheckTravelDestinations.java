package delta.games.lotro.lore.travels;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.travels.io.xml.TravelsWebXMLParser;

/**
 * Check travel destinations.
 * @author DAM
 */
public class MainCheckTravelDestinations
{
  private void doIt()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.TRAVELS_WEB);
    TravelsManager mgr=new TravelsWebXMLParser().parseXML(from);
    checkOrphanDestinations(mgr);
  }
  private void checkOrphanDestinations(TravelsManager mgr)
  {
    // Check that all destinations are attached to a node
    Set<TravelDestination> destinations=new HashSet<TravelDestination>(mgr.getDestinations());
    for(TravelNode node : mgr.getNodes())
    {
      for(TravelDestination location : node.getLocations())
      {
        destinations.remove(location);
      }
    }
    List<TravelDestination> unmanagedDestinations=new ArrayList<TravelDestination>(destinations);
    Collections.sort(unmanagedDestinations,new NamedComparator());
    for(TravelDestination unmanagedDestination : unmanagedDestinations)
    {
      System.out.println("Orphan: "+unmanagedDestination);
    }
  }

  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainCheckTravelDestinations().doIt();
  }
}
