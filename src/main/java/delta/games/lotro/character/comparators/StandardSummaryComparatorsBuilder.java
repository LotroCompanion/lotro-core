package delta.games.lotro.character.comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import delta.common.utils.collections.CompoundComparator;
import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Builder facilities for comparator on characters.
 * @author DAM
 */
public class StandardSummaryComparatorsBuilder
{
  /**
   * Build a comparator that uses account, server and character name.
   * @return a comparator. 
   */
  public static <T extends BaseCharacterSummary> Comparator<T> buildAccountNameServerComparator()
  {
    List<Comparator<T>> comparators=new ArrayList<Comparator<T>>();
    comparators.add(new CharacterAccountComparator<T>());
    comparators.add(new CharacterServerComparator<T>());
    comparators.add(new CharacterNameComparator<T>());
    CompoundComparator<T> ret=new CompoundComparator<T>(comparators);
    return ret;
  }

  /**
   * Build a character file comparator based on a summary comparator.
   * @param summaryComparator Underlying comparator.
   * @return A character file comparator. 
   */
  public static Comparator<CharacterFile> buildCharacterFileComparator(Comparator<CharacterSummary> summaryComparator)
  {
    DataProvider<CharacterFile,CharacterSummary> provider=new DataProvider<CharacterFile,CharacterSummary>()
    {
      @Override
      public CharacterSummary getData(CharacterFile file)
      {
        return file.getSummary();
      }
    };
    DelegatingComparator<CharacterFile,CharacterSummary> ret=new DelegatingComparator<CharacterFile,CharacterSummary>(provider,summaryComparator);
    return ret;
  }
}
