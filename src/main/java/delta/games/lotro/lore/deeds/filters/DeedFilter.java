package delta.games.lotro.lore.deeds.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.common.requirements.filters.UsageRequirementFilter;
import delta.games.lotro.common.rewards.filters.RewardsFilter;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.filter.AchievableMonsterPlayFilter;
import delta.games.lotro.lore.quests.filter.HiddenAchievableFilter;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.filter.WorldEventConditionFilter;

/**
 * Deed filter.
 * @author DAM
 */
public class DeedFilter implements Filter<DeedDescription>
{
  private Filter<DeedDescription> _filter;

  private DeedNameFilter _nameFilter;
  private DeedTypeFilter _typeFilter;
  private DeedCategoryFilter _categoryFilter;
  private AchievableMonsterPlayFilter<DeedDescription> _monsterPlayFilter;
  private HiddenAchievableFilter<DeedDescription> _hiddenFilter;
  // Requirements
  private UsageRequirementFilter _requirementsFilter;
  // Rewards
  private RewardsFilter _rewardsFilter;
  // World events
  private WorldEventConditionFilter _worldEventsFilter;

  /**
   * Constructor.
   */
  public DeedFilter()
  {
    List<Filter<DeedDescription>> filters=new ArrayList<Filter<DeedDescription>>();
    // Name
    _nameFilter=new DeedNameFilter();
    filters.add(_nameFilter);
    // Type
    _typeFilter=new DeedTypeFilter(null);
    filters.add(_typeFilter);
    // Category
    _categoryFilter=new DeedCategoryFilter(null);
    filters.add(_categoryFilter);
    // Monster play
    _monsterPlayFilter=new AchievableMonsterPlayFilter<DeedDescription>(null);
    filters.add(_monsterPlayFilter);
    // Hidden
    _hiddenFilter=new HiddenAchievableFilter<DeedDescription>(null);
    filters.add(_hiddenFilter);
    // Requirements
    {
      _requirementsFilter=new UsageRequirementFilter(null,null);
      ProxyValueResolver<DeedDescription,UsageRequirement> resolver=new ProxyValueResolver<DeedDescription,UsageRequirement>()
      {
        public UsageRequirement getValue(DeedDescription pojo)
        {
          return pojo.getUsageRequirement();
        }
      };
      ProxyFilter<DeedDescription,UsageRequirement> deedRequirementsFilter=new ProxyFilter<DeedDescription,UsageRequirement>(resolver,_requirementsFilter);
      filters.add(deedRequirementsFilter);
    }
    // Rewards
    _rewardsFilter=new RewardsFilter();
    filters.add(new DeedRewardFilter(_rewardsFilter));
    // World events
    {
      _worldEventsFilter=new WorldEventConditionFilter();
      ProxyValueResolver<DeedDescription,AbstractWorldEventCondition> resolver=new ProxyValueResolver<DeedDescription,AbstractWorldEventCondition>()
      {
        public AbstractWorldEventCondition getValue(DeedDescription pojo)
        {
          return pojo.getWorldEventsRequirement();
        }
      };
      ProxyFilter<DeedDescription,AbstractWorldEventCondition> questRequirementsFilter=new ProxyFilter<DeedDescription,AbstractWorldEventCondition>(resolver,_worldEventsFilter);
      filters.add(questRequirementsFilter);
    }
    _filter=new CompoundFilter<DeedDescription>(Operator.AND,filters);
  }

  /**
   * Get the filter on deed name.
   * @return a deed name filter.
   */
  public DeedNameFilter getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on deed type.
   * @return a deed type filter.
   */
  public DeedTypeFilter getTypeFilter()
  {
    return _typeFilter;
  }

  /**
   * Get the filter on deed category.
   * @return a deed category filter.
   */
  public DeedCategoryFilter getCategoryFilter()
  {
    return _categoryFilter;
  }

  /**
   * Get the filter on 'monster play' flag.
   * @return a filter on 'monster play' flag.
   */
  public AchievableMonsterPlayFilter<DeedDescription> getMonsterPlayFilter()
  {
    return _monsterPlayFilter;
  }

  /**
   * Get the filter on hidden flag.
   * @return a filter on hidden flag.
   */
  public HiddenAchievableFilter<DeedDescription> getHiddenFilter()
  {
    return _hiddenFilter;
  }

  /**
   * Get the filter on requirements.
   * @return the requirements filter.
   */
  public UsageRequirementFilter getRequirementsFilter()
  {
    return _requirementsFilter;
  }

  /**
   * Get the filter on rewards.
   * @return the rewards filter.
   */
  public RewardsFilter getRewardsFilter()
  {
    return _rewardsFilter;
  }

  /**
   * Get the filter on world events conditions.
   * @return the filter on world events conditions.
   */
  public WorldEventConditionFilter getWorldEventsFilter()
  {
    return _worldEventsFilter;
  }

  @Override
  public boolean accept(DeedDescription item)
  {
    return _filter.accept(item);
  }
}
