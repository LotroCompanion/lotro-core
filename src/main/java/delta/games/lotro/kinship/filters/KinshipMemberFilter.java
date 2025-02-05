package delta.games.lotro.kinship.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.filters.CharacterSummaryFilter;
import delta.games.lotro.kinship.KinshipMember;

/**
 * Kinship member filter.
 * @author DAM
 */
public class KinshipMemberFilter implements Filter<KinshipMember>
{
  private Filter<KinshipMember> _filter;

  private CharacterSummaryFilter _summaryFilter;
  private KinshipMemberNotesFilter _notesFilter;
  private KinshipRankFilter _rankFilter;

  /**
   * Constructor.
   */
  public KinshipMemberFilter()
  {
    List<Filter<KinshipMember>> filters=new ArrayList<Filter<KinshipMember>>();
    // Summary
    _summaryFilter=new CharacterSummaryFilter();
    ProxyValueResolver<KinshipMember,BaseCharacterSummary> resolver=new ProxyValueResolver<KinshipMember,BaseCharacterSummary>()
    {
      public BaseCharacterSummary getValue(KinshipMember pojo)
      {
        return pojo.getSummary();
      }
    };
    ProxyFilter<KinshipMember,BaseCharacterSummary> summaryFilter=new ProxyFilter<KinshipMember,BaseCharacterSummary>(resolver,_summaryFilter);
    filters.add(summaryFilter);
    // Rank
    _rankFilter=new KinshipRankFilter(null);
    filters.add(_rankFilter);
    // Notes
    _notesFilter=new KinshipMemberNotesFilter();
    filters.add(_notesFilter);
    _filter=new CompoundFilter<KinshipMember>(Operator.AND,filters);
  }

  /**
   * Get the filter on character summary.
   * @return a character summary filter.
   */
  public CharacterSummaryFilter getSummaryFilter()
  {
    return _summaryFilter;
  }

  /**
   * Get the filter on rank.
   * @return a rank filter.
   */
  public KinshipRankFilter getRankFilter()
  {
    return _rankFilter;
  }

  /**
   * Get the filter on notes.
   * @return a member notes filter.
   */
  public KinshipMemberNotesFilter getNotesFilter()
  {
    return _notesFilter;
  }

  @Override
  public boolean accept(KinshipMember item)
  {
    return _filter.accept(item);
  }
}
