package delta.games.lotro.common;

import delta.common.utils.text.EndOfLine;

/**
 * Emote.
 * @author DAM
 */
public class Emote
{
  private String _name;

  /**
   * Constructor.
   * @param name Emote's name.
   */
  public Emote(String name)
  {
    _name=name;
  }

  /**
   * Get the emote's name.
   * @return the emote's name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Dump the contents of this emote as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Emote: ").append(_name);
    sb.append(EndOfLine.NATIVE_EOL);
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
