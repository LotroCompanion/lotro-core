package delta.games.lotro.lore.xrefs.relics;

import java.util.List;

import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

/**
 * Test class for the relic references builder.
 * @author DAM
 */
public class MainTestRelicReferencesBuilder
{
  /**
   * Do it.
   */
  private void doIt()
  {
    RelicReferencesBuilder builder=new RelicReferencesBuilder();
    RelicsManager relicsMgr=RelicsManager.getInstance();
    for(Relic relic : relicsMgr.getAll())
    {
      List<RelicReference<?>> refs=builder.inspectItem(relic.getIdentifier());
      if (refs.size()>0)
      {
        System.out.println("References for relic: "+relic);
      }
      for(RelicReference<?> ref : refs)
      {
        System.out.println("\t"+ref);
      }
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestRelicReferencesBuilder().doIt();
  }
}
