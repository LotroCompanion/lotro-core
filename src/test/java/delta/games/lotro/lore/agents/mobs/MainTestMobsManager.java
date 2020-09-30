package delta.games.lotro.lore.agents.mobs;

/**
 * Simple test class for mobs.
 * @author DAM
 */
public class MainTestMobsManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    MobsManager mgr=MobsManager.getInstance();
    mgr.dump();
  }
}
