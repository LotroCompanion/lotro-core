package delta.games.lotro.character.proficiencies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class proficiencies.
 * @param <T> Type of proficiency.
 * @author DAM
 */
public class ClassProficiencies<T>
{
  private List<ClassProficiencyEntry<T>> _entries;

  /**
   * Constructor.
   */
  public ClassProficiencies()
  {
    _entries=new ArrayList<ClassProficiencyEntry<T>>();
  }

  /**
   * Add an entry.
   * @param value Value to add.
   * @param minimumLevel Minimum level.
   */
  public void addEntry(T value, int minimumLevel)
  {
    _entries.add(new ClassProficiencyEntry<T>(value,minimumLevel));
  }

  /**
   * Get the applicable proficiencies for the given character level.
   * @param level Character level.
   * @return A set of proficiencies.
   */
  public Set<T> getEntries(int level)
  {
    Set<T> ret=new HashSet<T>();
    for(ClassProficiencyEntry<T> entry : _entries)
    {
      if (level>=entry.getMinLevel())
      {
        ret.add(entry.getValue());
      }
    }
    return ret;
  }
}
