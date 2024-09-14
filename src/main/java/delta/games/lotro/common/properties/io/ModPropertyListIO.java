package delta.games.lotro.common.properties.io;

import java.util.List;

import delta.common.utils.NumericTools;
import delta.games.lotro.common.properties.ModPropertyList;

/**
 * I/O methods for modifier property lists.
 * @author DAM
 */
public class ModPropertyListIO
{
  /**
   * Get a persistable string for modifiers.
   * @param mods Input modifiers.
   * @return the persistable string.
   */
  public static String asPersistentString(ModPropertyList mods)
  {
    if (mods==null)
    {
      return "";
    }
    List<Integer> ids=mods.getIDs();
    if (ids.isEmpty())
    {
      return "";
    }
    StringBuilder sb=new StringBuilder();
    for(Integer id : ids)
    {
      if (sb.length()>0)
      {
        sb.append(',');
      }
      sb.append(id);
    }
    return sb.toString();
  }

  /**
   * Build modifiers from a persisted string.
   * @param input Input string.
   * @return A modifier property list or <code>null</code>.
   */
  public static ModPropertyList fromPersistedString(String input)
  {
    if ((input==null) || (input.isEmpty()))
    {
      return null;
    }
    ModPropertyList ret=new ModPropertyList();
    String[] ids=input.split(",");
    for(String id : ids)
    {
      ret.addID(NumericTools.parseInt(id,0));
    }
    return ret;
  }
}
