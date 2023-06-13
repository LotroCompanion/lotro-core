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
   * @param input Input (label or i18n key).
   * @return the i18ned value.
   */
  public static String getLabel(SingleLocaleLabelsManager mgr, String input)
  {
    String value=(mgr!=null)?mgr.getLabel(input):null;
    return (value!=null)?value:input;
  }
}
