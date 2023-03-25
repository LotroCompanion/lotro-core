package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Quest filter.
 * @author DAM
 */
public class QuestFilter extends AchievableFilter<QuestDescription>
{
  private QuestCategoryFilter _categoryFilter;
  private QuestArcFilter _questArcFilter;
  private QuestSizeFilter _questSizeFilter;
  private InstancedQuestFilter _instancedQuestFilter;
  private ShareableQuestFilter _shareableQuestFilter;
  private SessionPlayQuestFilter _sessionPlayQuestFilter;
  private AutoBestowedQuestFilter _autoBestowedQuestFilter;
  private RepeatabilityFilter _repeatabilityFilter;
  private LockTypeFilter _lockTypeFilter;

  /**
   * Constructor.
   */
  public QuestFilter()
  {
    super();
    CompoundFilter<QuestDescription> filter=getFilter();
    // Category
    _categoryFilter=new QuestCategoryFilter(null);
    filter.addFilter(_categoryFilter);
    // Quest arc
    _questArcFilter=new QuestArcFilter(null);
    filter.addFilter(_questArcFilter);
    // Quest size
    _questSizeFilter=new QuestSizeFilter(null);
    filter.addFilter(_questSizeFilter);
    // Instanced quests
    _instancedQuestFilter=new InstancedQuestFilter(null);
    filter.addFilter(_instancedQuestFilter);
    // Shareable quests
    _shareableQuestFilter=new ShareableQuestFilter(null);
    filter.addFilter(_shareableQuestFilter);
    // Session-play quests
    _sessionPlayQuestFilter=new SessionPlayQuestFilter(null);
    filter.addFilter(_sessionPlayQuestFilter);
    // Auto-bestowed quests
    _autoBestowedQuestFilter=new AutoBestowedQuestFilter(null);
    filter.addFilter(_autoBestowedQuestFilter);
    // Repeatability
    _repeatabilityFilter=new RepeatabilityFilter(null);
    filter.addFilter(_repeatabilityFilter);
    // Lock type
    _lockTypeFilter=new LockTypeFilter(null);
    filter.addFilter(_lockTypeFilter);
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
}
