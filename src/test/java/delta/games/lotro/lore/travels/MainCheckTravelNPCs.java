package delta.games.lotro.lore.travels;

import delta.common.utils.statistics.ValueSetStatistics;
import delta.games.lotro.common.geo.ExtendedPosition;

/**
 * Check travel NPCs.
 * @author DAM
 */
public class MainCheckTravelNPCs
{
  private void doIt()
  {
    TravelNpcsManager mgr=TravelNpcsManager.getInstance();
    statistics(mgr);
    assessNPCsCountByNode(mgr);
    showDetails(mgr);
  }

  private void statistics(TravelNpcsManager mgr)
  {
    ValueSetStatistics nodeStats=new ValueSetStatistics();
    ValueSetStatistics positionStats=new ValueSetStatistics();
    ValueSetStatistics mustBeDiscoveredStats=new ValueSetStatistics();
    for(TravelNpc npc : mgr.getAll())
    {
      TravelNode node=npc.getNode();
      nodeStats.addValue((node!=null)?1:0);
      ExtendedPosition position=npc.getPosition();
      positionStats.addValue((position!=null)?1:0);
      boolean mustBeDiscovered=npc.isMustBeDiscovered();
      mustBeDiscoveredStats.addValue(mustBeDiscovered?1:0);
    }
    System.out.println("With node:"); // 100%! Registered travel NPCs do have a node!
    System.out.println(nodeStats.dumpByValue().trim());
    System.out.println("With position:");
    System.out.println(positionStats.dumpByValue().trim());
    System.out.println("Must be discovered:");
    System.out.println(mustBeDiscoveredStats.dumpByValue().trim());
  }

  private void showDetails(TravelNpcsManager mgr)
  {
    System.out.println("NPCs with no position:");
    // Candaith and Saeradan
    // 5 festival announcers with unique position => add position?
    // 11 mission recruiters (M/F)
    for(TravelNpc npc : mgr.getAll())
    {
      ExtendedPosition position=npc.getPosition();
      if (position==null)
      {
        System.out.println("\t"+npc);
      }
    }
    System.out.println("'must be discovered' NPCs:");
    for(TravelNpc npc : mgr.getAll())
    {
      boolean mustBeDiscovered=npc.isMustBeDiscovered();
      if (mustBeDiscovered)
      {
        System.out.println("\t"+npc);
      }
    }
  }

  private void assessNPCsCountByNode(TravelNpcsManager mgr)
  {
    ValueSetStatistics stats=new ValueSetStatistics();
    for(TravelNpc npc : mgr.getAll())
    {
      TravelNode node=npc.getNode();
      stats.addValue(node.getIdentifier());
    }
    System.out.println("Number of NPCs per node:");
    System.out.println(stats.dumpByOccurrence());
    TravelsManager travelsMgr=TravelsManager.getInstance();
    for(TravelNode node : travelsMgr.getNodes())
    {
      int nb=stats.getCountForValue(node.getIdentifier());
      if (nb==0)
      {
        System.out.println("No NPC for node: "+node);
      }
    }
    // Conclusions:
    // Most but not all nodes have one NPC. 5 have 2. 1 has 4 and 1 has 18.
    // Some nodes (10) have no NPC!
  }

  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainCheckTravelNPCs().doIt();
  }
}
