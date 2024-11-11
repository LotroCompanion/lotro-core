package delta.games.lotro.common.properties.io;

import java.util.List;

import delta.common.utils.NumericTools;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatOperator;

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
    StatOperator operator=mods.getOperator();
    if ((operator!=null) && (operator!=StatOperator.ADD))
    {
      sb.append(operator.name()).append(':');
    }
    boolean notFirst=false;
    for(Integer id : ids)
    {
      if (notFirst)
      {
        sb.append(',');
      }
      sb.append(id);
      notFirst=true;
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
    int separator=input.indexOf(':');
    if (separator!=-1)
    {
      StatOperator operator=StatOperator.valueOf(input.substring(0,separator));
      input=input.substring(separator+1);
      ret.setOperator(operator);
    }
    String[] ids=input.split(",");
    for(String id : ids)
    {
      ret.addID(NumericTools.parseInt(id,0));
    }
    return ret;
  }
}
