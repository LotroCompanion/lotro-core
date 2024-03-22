package delta.games.lotro.lore.maps;

/**
 * Simple test class for parchment maps.
 * @author DAM
 */
public class MainTestParchmentMaps
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    ParchmentMapsManager mgr=ParchmentMapsManager.getInstance();
    mgr.dump(System.out);
  }
}
