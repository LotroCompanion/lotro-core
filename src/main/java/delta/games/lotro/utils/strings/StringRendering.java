package delta.games.lotro.utils.strings;

import delta.common.utils.variables.VariablesResolver;

/**
 * String rendering utils.
 * @author DAM
 */
public class StringRendering
{
  /**
   * Render a given string using the given resolver.
   * @param resolver Resolver.
   * @param rawFormat Input string.
   * @return the rendered string.
   */
  public static String render(VariablesResolver resolver, String rawFormat)
  {
    if (rawFormat==null)
    {
      return null;
    }
    if (rawFormat.indexOf("${")==-1)
    {
      return rawFormat;
    }
    String ret=resolver.render(rawFormat);
    ret=ret.replace(" ,",",");
    ret=ret.replace("  "," ");
    ret=ret.trim();
    return ret;
  }
}
