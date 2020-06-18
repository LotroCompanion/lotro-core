package delta.games.lotro.common;

import java.util.Comparator;

/**
 * Comparator for character classes, using their name.
 * @author DAM
 */
public class CharacterClassNameComparator implements Comparator<CharacterClass>
{
  @Override
  public int compare(CharacterClass o1, CharacterClass o2)
  {
    return o1.getLabel().compareTo(o2.getLabel());
  }
}
