package delta.games.lotro.utils.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.NumericTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Utility methods for enums, related to XML.
 * @author DAM
 */
public class EnumXMLUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(EnumXMLUtils.class);

  /**
   * Get a list of enum entries from a persisted list of enum codes.
   * @param <T> Type of enum entries.
   * @param value Input value.
   * @param enumEntryClass Enum entry class.
   * @return A list of enum entries.
   */
  public static <T extends LotroEnumEntry> List<T> readEnumEntriesList(String value, Class<T> enumEntryClass)
  {
    if ((value==null) || (value.isEmpty()))
    {
      return Collections.emptyList();
    }
    List<T> ret=new ArrayList<T>();
    LotroEnum<T> lotroEnum=LotroEnumsRegistry.getInstance().get(enumEntryClass);
    String[] codeStrs=value.split(",");
    for(String codeStr : codeStrs)
    {
      int code=NumericTools.parseInt(codeStr,0);
      T entry=lotroEnum.getEntry(code);
      if (entry!=null)
      {
        ret.add(entry);
      }
      else
      {
        LOGGER.warn("null entry: code={} for class: {}",codeStr,enumEntryClass);
      }
    }
    return ret;
  }
}
