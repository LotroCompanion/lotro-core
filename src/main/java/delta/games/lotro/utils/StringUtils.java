package delta.games.lotro.utils;

/**
 * Utility methods related to strings.
 * @author DAM
 */
public class StringUtils
{
  /**
   * Fix a name (remove any trailing [...]).
   * @param name Name to fix.
   * @return Fixed name.
   */
  public static String fixName(String name)
  {
    if (name==null)
    {
      return name;
    }
    int index=name.lastIndexOf('[');
    if (index!=-1)
    {
      name=name.substring(0,index).trim();
    }
    return name;
  }
}
