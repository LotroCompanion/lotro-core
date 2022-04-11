package delta.games.lotro.character.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.character.BaseCharacterSummary;

/**
 * Character summary filter.
 * @author DAM
 */
public class CharacterSummaryFilter implements Filter<BaseCharacterSummary>
{
  private Filter<BaseCharacterSummary> _filter;
  private CharacterNameFilter<BaseCharacterSummary> _nameFilter;
  private CharacterClassFilter<BaseCharacterSummary> _classFilter;
  private RaceFilter _raceFilter;
  private CharacterSexFilter _sexFilter;

  /**
   * Constructor.
   */
  public CharacterSummaryFilter()
  {
    List<Filter<BaseCharacterSummary>> filters=new ArrayList<Filter<BaseCharacterSummary>>();
    // Name
    _nameFilter=new CharacterNameFilter<BaseCharacterSummary>();
    filters.add(_nameFilter);
    // Class
    _classFilter=new CharacterClassFilter<BaseCharacterSummary>(null);
    filters.add(_classFilter);
    // Race
    _raceFilter=new RaceFilter(null);
    filters.add(_raceFilter);
    // Sex
    _sexFilter=new CharacterSexFilter(null);
    filters.add(_sexFilter);
    _filter=new CompoundFilter<BaseCharacterSummary>(Operator.AND,filters);
  }

  /**
   * Get the filter on character name.
   * @return a character name filter.
   */
  public CharacterNameFilter<BaseCharacterSummary> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on character class.
   * @return a character class filter.
   */
  public CharacterClassFilter<BaseCharacterSummary> getClassFilter()
  {
    return _classFilter;
  }

  /**
   * Get the filter on race.
   * @return a race filter.
   */
  public RaceFilter getRaceFilter()
  {
    return _raceFilter;
  }

  /**
   * Get the filter on character sex.
   * @return a character sex filter.
   */
  public CharacterSexFilter getSexFilter()
  {
    return _sexFilter;
  }

  @Override
  public boolean accept(BaseCharacterSummary item)
  {
    return _filter.accept(item);
  }
}
