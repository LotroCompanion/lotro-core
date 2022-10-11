package delta.games.lotro.character.proficiencies;

/**
 * Entry of a class proficiency.
 * @param <T> Type of proficiency.
 * @author DAM
 */
public class ClassProficiencyEntry<T>
{
  private T _value;
  private int _minLevel;

  /**
   * Constructor.
   * @param value Value.
   * @param minLevel Minimum level.
   */
  public ClassProficiencyEntry(T value, int minLevel)
  {
    _value=value;
    _minLevel=minLevel;
  }

  /**
   * Get the managed value.
   * @return the managed value.
   */
  public T getValue()
  {
    return _value;
  }

  /**
   * Get the minimum level.
   * @return the minimum evel.
   */
  public int getMinLevel()
  {
    return _minLevel;
  }

  @Override
  public String toString()
  {
    return _value+((_minLevel>1)?" (level "+_minLevel:"");
  }
}
