package delta.games.lotro.common.rewards;

import delta.common.utils.text.EndOfLine;

/**
 * Trait.
 * @author DAM
 */
public class TraitReward
{
  private String _name;

  /**
   * Constructor.
   * @param name Trait's name.
   */
  public TraitReward(String name)
  {
    _name=name;
  }

  /**
   * Get the trait's name.
   * @return the trait's name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Dump the contents of this trait as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Trait: ").append(_name);
    sb.append(EndOfLine.NATIVE_EOL);
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
