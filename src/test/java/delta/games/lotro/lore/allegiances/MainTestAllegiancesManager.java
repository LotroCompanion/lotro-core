package delta.games.lotro.lore.allegiances;

/**
 * Simple test class for the allegiances manager.
 * @author DAM
 */
public class MainTestAllegiancesManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    AllegiancesManager mgr=AllegiancesManager.getInstance();
    for(AllegianceDescription allegiance : mgr.getAll())
    {
      System.out.println(allegiance);
    }
  }
}
