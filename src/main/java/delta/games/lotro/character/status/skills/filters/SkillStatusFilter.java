package delta.games.lotro.character.status.skills.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.status.skills.SkillStatus;
import delta.games.lotro.common.filters.NamedFilter;

/**
 * Skill status filter.
 * @author DAM
 */
public class SkillStatusFilter implements Filter<SkillStatus>
{
  private Filter<SkillStatus> _filter;
  private NamedFilter<SkillDescription> _nameFilter;
  private KnownSkillFilter _knownFilter;

  /**
   * Constructor.
   */
  public SkillStatusFilter()
  {
    List<Filter<SkillStatus>> filters=new ArrayList<Filter<SkillStatus>>();
    // Name
    _nameFilter=new NamedFilter<SkillDescription>();
    ProxyValueResolver<SkillStatus,SkillDescription> proxySolver=new ProxyValueResolver<SkillStatus,SkillDescription>()
    {
      @Override
      public SkillDescription getValue(SkillStatus pojo)
      {
        return pojo.getSkill();
      }
    };
    ProxyFilter<SkillStatus,SkillDescription> nameFilter=new ProxyFilter<SkillStatus,SkillDescription>(proxySolver,_nameFilter);
    filters.add(nameFilter);
    // Known
    _knownFilter=new KnownSkillFilter(null);
    filters.add(_knownFilter);
    _filter=new CompoundFilter<SkillStatus>(Operator.AND,filters);
  }

  /**
   * Get the filter on skill name.
   * @return a skill name filter.
   */
  public NamedFilter<SkillDescription> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on known/unknown state.
   * @return a known/unknown filter.
   */
  public KnownSkillFilter getKnownFilter()
  {
    return _knownFilter;
  }

  @Override
  public boolean accept(SkillStatus item)
  {
    return _filter.accept(item);
  }
}
