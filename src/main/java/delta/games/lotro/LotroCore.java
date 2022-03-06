package delta.games.lotro;

import delta.games.lotro.misc.migration.CarryAllsMigration;

/**
 * Facade for lotro-core.
 * @author DAM
 */
public class LotroCore
{
  /**
   * Init.
   */
  public static void init()
  {
    migration();
  }

  private static void migration()
  {
    new CarryAllsMigration().doIt();
  }
}
