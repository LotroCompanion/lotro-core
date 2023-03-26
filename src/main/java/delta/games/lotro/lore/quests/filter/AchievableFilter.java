package delta.games.lotro.lore.quests.filter;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.common.filters.NamedFilter;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.common.requirements.filters.UsageRequirementFilter;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.filters.RewardsFilter;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.filter.WorldEventConditionFilter;

/**
 * Base class for Achievable filters.
 * @param <T> Type of achievables.
 * @author DAM
 */
public class AchievableFilter<T extends Achievable> implements Filter<T>
{
  // Compound filter
  private CompoundFilter<T> _filter;
  // Name
  private NamedFilter<T> _nameFilter;
  // Monster play
  private AchievableMonsterPlayFilter<T> _monsterPlayFilter;
  private HiddenAchievableFilter<T> _hiddenFilter;
  // Requirements
  private UsageRequirementFilter _requirementsFilter;
  // Rewards
  private RewardsFilter _rewardsFilter;
  // World events
  private WorldEventConditionFilter _worldEventsFilter;
  // Contents packs
  private WebStoreItemFilter<T> _webStoreItemsFilter;

  /**
   * Constructor.
   */
  public AchievableFilter()
  {
    List<Filter<T>> filters=new ArrayList<Filter<T>>();
    // Name
    _nameFilter=new NamedFilter<T>();
    filters.add(_nameFilter);
    // Monster play
    _monsterPlayFilter=new AchievableMonsterPlayFilter<T>(null);
    filters.add(_monsterPlayFilter);
    // Hidden
    _hiddenFilter=new HiddenAchievableFilter<T>(null);
    filters.add(_hiddenFilter);
    // Requirements
    {
      _requirementsFilter=new UsageRequirementFilter(null,null);
      ProxyValueResolver<T,UsageRequirement> resolver=new ProxyValueResolver<T,UsageRequirement>()
      {
        public UsageRequirement getValue(T pojo)
        {
          return pojo.getUsageRequirement();
        }
      };
      ProxyFilter<T,UsageRequirement> questRequirementsFilter=new ProxyFilter<T,UsageRequirement>(resolver,_requirementsFilter);
      filters.add(questRequirementsFilter);
    }
    // Rewards
    {
      _rewardsFilter=new RewardsFilter();
      ProxyValueResolver<T,Rewards> resolver=new ProxyValueResolver<T,Rewards>()
      {
        public Rewards getValue(T pojo)
        {
          return pojo.getRewards();
        }
      };
      ProxyFilter<T,Rewards> questRewardsFilter=new ProxyFilter<T,Rewards>(resolver,_rewardsFilter);
      filters.add(questRewardsFilter);
    }
    // World events
    {
      _worldEventsFilter=new WorldEventConditionFilter();
      ProxyValueResolver<T,AbstractWorldEventCondition> resolver=new ProxyValueResolver<T,AbstractWorldEventCondition>()
      {
        public AbstractWorldEventCondition getValue(T pojo)
        {
          return pojo.getWorldEventsRequirement();
        }
      };
      ProxyFilter<T,AbstractWorldEventCondition> questRequirementsFilter=new ProxyFilter<T,AbstractWorldEventCondition>(resolver,_worldEventsFilter);
      filters.add(questRequirementsFilter);
    }
    // Web Store items
    _webStoreItemsFilter=new WebStoreItemFilter<>(null);
    filters.add(_webStoreItemsFilter);
    _filter=new CompoundFilter<T>(Operator.AND,filters);
  }

  /**
   * Get the managed filter.
   * @return the managed filter.
   */
  public CompoundFilter<T> getFilter()
  {
    return _filter;
  }

  /**
   * Get the filter on quest name.
   * @return a quest name filter.
   */
  public NamedFilter<T> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on 'monster play' flag.
   * @return a filter on 'monster play' flag.
   */
  public AchievableMonsterPlayFilter<T> getMonsterPlayFilter()
  {
    return _monsterPlayFilter;
  }

  /**
   * Get the filter on hidden flag.
   * @return a filter on hidden flag.
   */
  public HiddenAchievableFilter<T> getHiddenFilter()
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

  /**
   * Get the filter on web store items.
   * @return the filter on web store items.
   */
  public WebStoreItemFilter<T> getWebStoreItemsFilter()
  {
    return _webStoreItemsFilter;
  }

  @Override
  public boolean accept(T item)
  {
    return _filter.accept(item);
  }
}
