package delta.games.lotro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility methods related to performance.
 * @author DAM
 */
public class PerfUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(PerfUtils.class);

  /**
   * Show a 'loaded' log.
   * @param count Count of loaded objects.
   * @param name Objects name.
   * @param duration Load duration.
   */
  public static void showLoadedLog(int count, String name, long duration)
  {
    LOGGER.info("Loaded {} {} in {}ms.", Integer.valueOf(count),name,Long.valueOf(duration));
  }
}
