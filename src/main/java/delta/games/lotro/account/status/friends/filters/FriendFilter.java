package delta.games.lotro.account.status.friends.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.account.status.friends.Friend;
import delta.games.lotro.character.filters.CharacterClassFilter;
import delta.games.lotro.character.filters.CharacterNameFilter;

/**
 * Friend filter.
 * @author DAM
 */
public class FriendFilter implements Filter<Friend>
{
  private Filter<Friend> _filter;

  private CharacterNameFilter<Friend> _nameFilter;
  private CharacterClassFilter<Friend> _classFilter;
  private FriendNoteFilter _notesFilter;

  /**
   * Constructor.
   */
  public FriendFilter()
  {
    List<Filter<Friend>> filters=new ArrayList<Filter<Friend>>();
    // Name
    _nameFilter=new CharacterNameFilter<Friend>();
    filters.add(_nameFilter);
    // Class
    _classFilter=new CharacterClassFilter<Friend>(null);
    filters.add(_classFilter);
    // Notes
    _notesFilter=new FriendNoteFilter();
    filters.add(_notesFilter);
    _filter=new CompoundFilter<Friend>(Operator.AND,filters);
  }

  /**
   * Get the filter on character name.
   * @return a character name filter.
   */
  public CharacterNameFilter<Friend> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on character class.
   * @return a character class filter.
   */
  public CharacterClassFilter<Friend> getClassFilter()
  {
    return _classFilter;
  }

  /**
   * Get the filter on notes.
   * @return a member notes filter.
   */
  public FriendNoteFilter getNotesFilter()
  {
    return _notesFilter;
  }

  @Override
  public boolean accept(Friend item)
  {
    return _filter.accept(item);
  }
}
