package delta.games.lotro.lore.maps;

/**
 * Simple test class for dungeons.
 * @author DAM
 */
public class MainTestDungeons
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    DungeonsManager mgr=DungeonsManager.getInstance();
    mgr.dump(System.out);
  }
}
