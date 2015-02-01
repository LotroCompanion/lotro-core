package delta.games.lotro.common.icons;

import java.io.File;

/**
 * Facade for icons access.
 * @author DAM
 */
public class BasicIconsManager
{
  private static BasicIconsManager _instance=new BasicIconsManager();
  
  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static BasicIconsManager getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private BasicIconsManager()
  {
    // Nothing to do!
  }

  /**
   * Get the icon at the given URL.
   * @param url URL of icon.
   * @return A file or <code>null</code> if not found.
   */
  public File getIconFile(String url)
  {
    // TODO
    File ret=null;
    return ret;
  }
}
