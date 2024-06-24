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
      int index2=name.indexOf(']',index);
      if (index2==name.length()-1)
      {
        name=name.substring(0,index).trim();
      }
    }
    return name;
  }

  /**
   * Remove marks ([...]) from a text.
   * @param text Text to fix.
   * @return Fixed text.
   */
  public static String removeMarks(String text)
  {
    if (text==null)
    {
      return text;
    }
    while(true)
    {
      int startIndex=text.indexOf('[');
      if (startIndex!=-1)
      {
        int endIndex=text.indexOf(']',startIndex+1);
        if (endIndex!=-1)
        {
          text=text.substring(0,startIndex)+text.substring(endIndex+1);
        }
        else
        {
          text=text.substring(0,startIndex);
        }
        text=text.trim();
      }
      else
      {
        break;
      }
    }
    return text;
  }
}
