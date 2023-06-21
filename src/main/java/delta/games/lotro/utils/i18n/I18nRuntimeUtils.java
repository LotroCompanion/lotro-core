package delta.games.lotro.utils.i18n;

import delta.common.utils.i18n.SingleLocaleLabelsManager;

/**
 * Runtime utilities related to i18n.
 * @author DAM
 */
public class I18nRuntimeUtils
{
  /**
   * Get an internationalized label.
   * @param mgr Labels manager.
   * @param id Identifier.
   * @return the i18ned value.
   */
  public static String getLabel(SingleLocaleLabelsManager mgr, int id)
  {
    if (mgr!=null)
    {
      String key=String.valueOf(id);
      String value=mgr.getLabel(key);
      return value;
    }
    return null;
  }

  /**
   * Get an internationalized label.
   * @param mgr Labels manager.
   * @param input Input (label or i18n key).
   * @return the i18ned value.
   */
  public static String getLabel(SingleLocaleLabelsManager mgr, String input)
  {
    String value=(mgr!=null)?mgr.getLabel(input):null;
    return (value!=null)?value:input;
  }
}
