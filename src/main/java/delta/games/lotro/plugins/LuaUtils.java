package delta.games.lotro.plugins;

import delta.common.utils.NumericTools;

/**
 * Collection of tool methods related to LUA data.
 * @author DAM
 */
public class LuaUtils
{
  /**
   * Parse an integer value from a LUA string.
   * @param value String value.
   * @return An integer or <code>null</code>.
   */
  public static Integer parseIntValue(String value)
  {
    Integer ret=null;
    if (value!=null)
    {
      value=value.trim();
      if ((value.startsWith("<num>")) && (value.endsWith("</num>")))
      {
        value=value.substring(5);
        value=value.substring(0,value.length()-6);
      }
      if (value.endsWith(".000000"))
      {
        value=value.substring(0,value.length()-7);
      }
      ret=NumericTools.parseInteger(value);
    }
    return ret;
  }
}
