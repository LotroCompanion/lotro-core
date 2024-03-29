package delta.games.lotro.common.rewards.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.common.rewards.Rewards;

/**
 * Filter on rewards.
 * @author DAM
 */
public class RewardsFilter implements Filter<Rewards>
{
  private Filter<Rewards> _filter;

  private ReputationRewardFilter _reputationFilter;
  private LotroPointsRewardFilter _lotroPointsFilter;
  private ClassPointRewardFilter _classPointsFilter;
  private XpRewardFilter _xpFilter;
  private ItemXpRewardFilter _itemXpFilter;
  private MountXpRewardFilter _mountXpFilter;
  private VirtueXpRewardFilter _virtueXpFilter;
  private GloryRewardFilter _gloryFilter;
  private TraitRewardFilter _traitFilter;
  private TitleRewardFilter _titleFilter;
  private VirtueRewardFilter _virtueFilter;
  private EmoteRewardFilter _emoteFilter;
  private ItemRewardFilter _itemFilter;
  private RelicRewardFilter _relicFilter;
  private BillingGroupRewardFilter _billingGroupFilter;

  /**
   * Constructor.
   */
  public RewardsFilter()
  {
    List<Filter<Rewards>> filters=new ArrayList<Filter<Rewards>>();
    // - reputation
    _reputationFilter=new ReputationRewardFilter(null);
    filters.add(_reputationFilter);
    // - LOTRO points
    _lotroPointsFilter=new LotroPointsRewardFilter(null);
    filters.add(_lotroPointsFilter);
    // - class points
    _classPointsFilter=new ClassPointRewardFilter(null);
    filters.add(_classPointsFilter);
    // - XP
    _xpFilter=new XpRewardFilter(null);
    filters.add(_xpFilter);
    // - item XP
    _itemXpFilter=new ItemXpRewardFilter(null);
    filters.add(_itemXpFilter);
    // - mount XP
    _mountXpFilter=new MountXpRewardFilter(null);
    filters.add(_mountXpFilter);
    // - virtue XP
    _virtueXpFilter=new VirtueXpRewardFilter(null);
    filters.add(_virtueXpFilter);
    // - glory
    _gloryFilter=new GloryRewardFilter(null);
    filters.add(_gloryFilter);
    // - trait
    _traitFilter=new TraitRewardFilter(null);
    filters.add(_traitFilter);
    // - title
    _titleFilter=new TitleRewardFilter(null);
    filters.add(_titleFilter);
    // - virtue
    _virtueFilter=new VirtueRewardFilter(null);
    filters.add(_virtueFilter);
    // - emote
    _emoteFilter=new EmoteRewardFilter(null);
    filters.add(_emoteFilter);
    // - item
    _itemFilter=new ItemRewardFilter(null);
    filters.add(_itemFilter);
    // - relic
    _relicFilter=new RelicRewardFilter(null);
    filters.add(_relicFilter);
    // - billing group
    _billingGroupFilter=new BillingGroupRewardFilter(null);
    filters.add(_billingGroupFilter);
    _filter=new CompoundFilter<Rewards>(Operator.AND,filters);
  }

  /**
   * Get the filter on reputation reward.
   * @return a reputation reward filter.
   */
  public ReputationRewardFilter getReputationFilter()
  {
    return _reputationFilter;
  }

  /**
   * Get the filter on LOTRO points reward.
   * @return a filter.
   */
  public LotroPointsRewardFilter getLotroPointsFilter()
  {
    return _lotroPointsFilter;
  }

  /**
   * Get the filter on class point reward.
   * @return a filter.
   */
  public ClassPointRewardFilter getClassPointsFilter()
  {
    return _classPointsFilter;
  }

  /**
   * Get the filter on XP reward.
   * @return a filter.
   */
  public XpRewardFilter getXpFilter()
  {
    return _xpFilter;
  }

  /**
   * Get the filter on item XP reward.
   * @return a filter.
   */
  public ItemXpRewardFilter getItemXpFilter()
  {
    return _itemXpFilter;
  }

  /**
   * Get the filter on mount XP reward.
   * @return a filter.
   */
  public MountXpRewardFilter getMountXpFilter()
  {
    return _mountXpFilter;
  }

  /**
   * Get the filter on virtue XP reward.
   * @return a filter.
   */
  public VirtueXpRewardFilter getVirtueXpFilter()
  {
    return _virtueXpFilter;
  }

  /**
   * Get the filter on glory reward.
   * @return a filter.
   */
  public GloryRewardFilter getGloryFilter()
  {
    return _gloryFilter;
  }

  /**
   * Get the filter on trait reward.
   * @return a trait reward filter.
   */
  public TraitRewardFilter getTraitFilter()
  {
    return _traitFilter;
  }

  /**
   * Get the filter on title reward.
   * @return a title reward filter.
   */
  public TitleRewardFilter getTitleFilter()
  {
    return _titleFilter;
  }

  /**
   * Get the filter on virtue reward.
   * @return a virtue reward filter.
   */
  public VirtueRewardFilter getVirtueFilter()
  {
    return _virtueFilter;
  }

  /**
   * Get the filter on emote reward.
   * @return an emote reward filter.
   */
  public EmoteRewardFilter getEmoteFilter()
  {
    return _emoteFilter;
  }

  /**
   * Get the filter on item reward.
   * @return an item reward filter.
   */
  public ItemRewardFilter getItemFilter()
  {
    return _itemFilter;
  }

  /**
   * Get the filter on relic reward.
   * @return a relic reward filter.
   */
  public RelicRewardFilter getRelicFilter()
  {
    return _relicFilter;
  }

  /**
   * Get the filter on billing group reward.
   * @return a billing group reward filter.
   */
  public BillingGroupRewardFilter getBillingGroupFilter()
  {
    return _billingGroupFilter;
  }

  @Override
  public boolean accept(Rewards item)
  {
    return _filter.accept(item);
  }
}
