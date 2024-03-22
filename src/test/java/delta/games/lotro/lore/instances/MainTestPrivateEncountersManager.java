package delta.games.lotro.lore.instances;

/**
 * Simple test class for private encounters maps.
 * @author DAM
 */
public class MainTestPrivateEncountersManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    PrivateEncountersManager mgr=PrivateEncountersManager.getInstance();
    mgr.dump(System.out);
  }
}
