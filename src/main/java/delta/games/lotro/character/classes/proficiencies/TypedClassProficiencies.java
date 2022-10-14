package delta.games.lotro.character.classes.proficiencies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class proficiencies.
 * @param <T> Type of proficiency.
 * @author DAM
 */
public class TypedClassProficiencies<T>
{
  private List<TypedClassProficiencyEntry<T>> _entries;

  /**
   * Constructor.
   */
  public TypedClassProficiencies()
  {
    _entries=new ArrayList<TypedClassProficiencyEntry<T>>();
  }

  /**
   * Add an entry.
   * @param value Value to add.
   * @param minimumLevel Minimum level.
   */
  public void addEntry(T value, int minimumLevel)
  {
    _entries.add(new TypedClassProficiencyEntry<T>(value,minimumLevel));
  }

  /**
   * Get the managed entries.
   * @return the managed entries.
   */
  public List<TypedClassProficiencyEntry<T>> getEntries()
  {
    return _entries;
  }

  /**
   * Get the applicable proficiencies for the given character level.
   * @param level Character level.
   * @return A set of proficiencies.
   */
  public Set<T> getEntries(int level)
  {
    Set<T> ret=new HashSet<T>();
    for(TypedClassProficiencyEntry<T> entry : _entries)
    {
      if (level>=entry.getMinLevel())
      {
        ret.add(entry.getValue());
      }
    }
    return ret;
  }
}
