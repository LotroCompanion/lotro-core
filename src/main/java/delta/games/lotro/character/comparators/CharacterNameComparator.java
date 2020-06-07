package delta.games.lotro.character.comparators;

import java.util.Comparator;

import delta.games.lotro.character.CharacterFile;

/**
 * Comparator for CharacterFiles using the character name.
 * @author DAM
 */
public class CharacterNameComparator implements Comparator<CharacterFile>
{
  @Override
  public int compare(CharacterFile o1, CharacterFile o2)
  {
    String name1=o1.getName();
    String name2=o2.getName();
    return name1.compareTo(name2);
  }
}
