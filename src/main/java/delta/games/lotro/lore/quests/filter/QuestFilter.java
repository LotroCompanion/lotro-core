package delta.games.lotro.lore.quests.filter;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.common.requirements.filters.UsageRequirementFilter;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.filters.RewardsFilter;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Quest filter.
 * @author DAM
 */
public class QuestFilter implements Filter<QuestDescription>
{
  private Filter<QuestDescription> _filter;

  private QuestNameFilter _nameFilter;
  private QuestCategoryFilter _categoryFilter;
  private QuestArcFilter _questArcFilter;
  private QuestSizeFilter _questSizeFilter;
  private AchievableMonsterPlayFilter<QuestDescription> _monsterPlayFilter;
  private InstancedQuestFilter _instancedQuestFilter;
  private ShareableQuestFilter _shareableQuestFilter;
  private SessionPlayQuestFilter _sessionPlayQuestFilter;
  private AutoBestowedQuestFilter _autoBestowedQuestFilter;
  private RepeatabilityFilter _repeatabilityFilter;
  private LockTypeFilter _lockTypeFilter;
  private HiddenAchievableFilter<QuestDescription> _hiddenFilter;
  // Requirements
  private UsageRequirementFilter _requirementsFilter;
  // Rewards
  private RewardsFilter _rewardsFilter;

  /**
   * Constructor.
   */
  public QuestFilter()
  {
    List<Filter<QuestDescription>> filters=new ArrayList<Filter<QuestDescription>>();
    // Name
    _nameFilter=new QuestNameFilter();
    filters.add(_nameFilter);
    // Category
    _categoryFilter=new QuestCategoryFilter(null);
    filters.add(_categoryFilter);
    // Quest arc
    _questArcFilter=new QuestArcFilter(null);
    filters.add(_questArcFilter);
    // Quest size
    _questSizeFilter=new QuestSizeFilter(null);
    filters.add(_questSizeFilter);
    // Monster play
    _monsterPlayFilter=new AchievableMonsterPlayFilter<QuestDescription>(null);
    filters.add(_monsterPlayFilter);
    // Instanced quests
    _instancedQuestFilter=new InstancedQuestFilter(null);
    filters.add(_instancedQuestFilter);
    // Shareable quests
    _shareableQuestFilter=new ShareableQuestFilter(null);
    filters.add(_shareableQuestFilter);
    // Session-play quests
    _sessionPlayQuestFilter=new SessionPlayQuestFilter(null);
    filters.add(_sessionPlayQuestFilter);
    // Auto-bestowed quests
    _autoBestowedQuestFilter=new AutoBestowedQuestFilter(null);
    filters.add(_autoBestowedQuestFilter);
    // Repeatability
    _repeatabilityFilter=new RepeatabilityFilter(null);
    filters.add(_repeatabilityFilter);
    // Lock type
    _lockTypeFilter=new LockTypeFilter(null);
    filters.add(_lockTypeFilter);
    // Hidden
    _hiddenFilter=new HiddenAchievableFilter<QuestDescription>(null);
    filters.add(_hiddenFilter);
    // Requirements
    {
      _requirementsFilter=new UsageRequirementFilter(null,null);
      ProxyValueResolver<QuestDescription,UsageRequirement> resolver=new ProxyValueResolver<QuestDescription,UsageRequirement>()
      {
        public UsageRequirement getValue(QuestDescription pojo)
        {
          return pojo.getUsageRequirement();
        }
      };
      ProxyFilter<QuestDescription,UsageRequirement> questRequirementsFilter=new ProxyFilter<QuestDescription,UsageRequirement>(resolver,_requirementsFilter);
      filters.add(questRequirementsFilter);
    }
    // Rewards
    {
      _rewardsFilter=new RewardsFilter();
      ProxyValueResolver<QuestDescription,Rewards> resolver=new ProxyValueResolver<QuestDescription,Rewards>()
      {
        public Rewards getValue(QuestDescription pojo)
        {
          return pojo.getRewards();
        }
      };
      ProxyFilter<QuestDescription,Rewards> questRequirementsFilter=new ProxyFilter<QuestDescription,Rewards>(resolver,_rewardsFilter);
      filters.add(questRequirementsFilter);
    }
    _filter=new CompoundFilter<QuestDescription>(Operator.AND,filters);
  }

  /**
   * Get the filter on quest name.
   * @return a quest name filter.
   */
  public QuestNameFilter getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on quest category.
   * @return a quest category filter.
   */
  public QuestCategoryFilter getCategoryFilter()
  {
    return _categoryFilter;
  }

  /**
   * Get the filter on quest arc.
   * @return a quest arc filter.
   */
  public QuestArcFilter getQuestArcFilter()
  {
    return _questArcFilter;
  }

  /**
   * Get the filter on quest size.
   * @return a quest size filter.
   */
  public QuestSizeFilter getQuestSizeFilter()
  {
    return _questSizeFilter;
  }

  /**
   * Get the filter on 'monster play' flag.
   * @return a filter on 'monster play' flag.
   */
  public AchievableMonsterPlayFilter<QuestDescription> getMonsterPlayFilter()
  {
    return _monsterPlayFilter;
  }

  /**
   * Get the filter on instanced quests.
   * @return a filter on instanced quests.
   */
  public InstancedQuestFilter getInstancedQuestFilter()
  {
    return _instancedQuestFilter;
  }

  /**
   * Get the filter on shareable quests.
   * @return a filter on shareable quests.
   */
  public ShareableQuestFilter getShareableQuestFilter()
  {
    return _shareableQuestFilter;
  }

  /**
   * Get the filter on session-play quests.
   * @return a filter on session-play quests.
   */
  public SessionPlayQuestFilter getSessionPlayQuestFilter()
  {
    return _sessionPlayQuestFilter;
  }

  /**
   * Get the filter on auto-bestowed quests.
   * @return a filter on auto-bestowed quests.
   */
  public AutoBestowedQuestFilter getAutoBestowedQuestFilter()
  {
    return _autoBestowedQuestFilter;
  }

  /**
   * Get the filter on repeatability.
   * @return a filter on repeatability.
   */
  public RepeatabilityFilter getRepeatabilityFilter()
  {
    return _repeatabilityFilter;
  }

  /**
   * Get the filter on lock type.
   * @return a filter on lock type.
   */
  public LockTypeFilter getLockTypeFilter()
  {
    return _lockTypeFilter;
  }

  /**
   * Get the filter on hidden flag.
   * @return a filter on hidden flag.
   */
  public HiddenAchievableFilter<QuestDescription> getHiddenFilter()
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

  @Override
  public boolean accept(QuestDescription item)
  {
    return _filter.accept(item);
  }
}
