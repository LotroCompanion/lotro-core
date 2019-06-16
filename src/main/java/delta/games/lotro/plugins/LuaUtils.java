package delta.games.lotro.plugins;

import java.util.Map;

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

  /**
   * Load a buffer from raw LUA data
   * @param data Input data.
   * @param dataKey Map key (usually "rawData").
   * @return the loaded buffer.
   */
  @SuppressWarnings("unchecked")
  public static byte[] loadBuffer(Map<String,Object> data, String dataKey)
  {
    byte[] buffer=null;
    Map<String,Double> rawData=(Map<String,Double>)data.get(dataKey);
    if (rawData!=null)
    {
      int nb=rawData.size();
      buffer=new byte[nb];
      for(int i=0;i<nb;i++)
      {
        String key=(i+1)+".0";
        Double value=rawData.get(key);
        if (value!=null)
        {
          buffer[i]=value.byteValue();
        }
      }
    }
    return buffer;
  }
}
