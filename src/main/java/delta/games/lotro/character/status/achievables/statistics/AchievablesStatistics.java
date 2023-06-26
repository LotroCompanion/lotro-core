package delta.games.lotro.character.status.achievables.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.character.status.achievables.statistics.emotes.EmoteEvent;
import delta.games.lotro.character.status.achievables.statistics.emotes.EmoteEventNameComparator;
import delta.games.lotro.character.status.achievables.statistics.reputation.AchievablesFactionStats;
import delta.games.lotro.character.status.achievables.statistics.reputation.AchievablesReputationStats;
import delta.games.lotro.character.status.achievables.statistics.titles.TitleEvent;
import delta.games.lotro.character.status.achievables.statistics.titles.TitleEventNameComparator;
import delta.games.lotro.character.status.achievables.statistics.traits.TraitEvent;
import delta.games.lotro.character.status.achievables.statistics.traits.TraitEventNameComparator;
import delta.games.lotro.character.status.achievables.statistics.virtues.VirtueStatsFromAchievables;
import delta.games.lotro.character.status.achievables.statistics.virtues.VirtueXPStatsFromAchievables;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.common.rewards.EmoteReward;
import delta.games.lotro.common.rewards.ItemReward;
import delta.games.lotro.common.rewards.ReputationReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.TitleReward;
import delta.games.lotro.common.rewards.TraitReward;
import delta.games.lotro.common.rewards.VirtueReward;
import delta.games.lotro.common.statistics.items.ItemsStats;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.WellKnownItems;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Gather statistics about a collection of achievables.
 * @author DAM
 */
public class AchievablesStatistics
{
  private int _acquiredLP;
  private int _classPoints;
  private int _marksCount;
  private int _medallionsCount;
  private List<TitleEvent> _titles;
  private List<EmoteEvent> _emotes;
  private List<TraitEvent> _traits;
  private Map<VirtueDescription,VirtueStatsFromAchievables> _virtues;
  private VirtueXPStatsFromAchievables _virtueXP;
  private AchievablesReputationStats _reputation;
  private ItemsStats _items;

  /**
   * Constructor.
   */
  public AchievablesStatistics()
  {
    _titles=new ArrayList<TitleEvent>();
    _emotes=new ArrayList<EmoteEvent>();
    _traits=new ArrayList<TraitEvent>();
    _virtues=new HashMap<VirtueDescription,VirtueStatsFromAchievables>();
    _virtueXP=new VirtueXPStatsFromAchievables();
    _reputation=new AchievablesReputationStats();
    _items=new ItemsStats();
    reset();
  }

  /**
   * Reset contents.
   */
  public void reset()
  {
    _acquiredLP=0;
    _classPoints=0;
    _marksCount=0;
    _medallionsCount=0;
    _titles.clear();
    _emotes.clear();
    _traits.clear();
    _virtues.clear();
    _virtueXP.clear();
    _reputation.reset();
    _items.reset();
  }

  void endStatsComputation()
  {
    Collections.sort(_titles,new TitleEventNameComparator());
    Collections.sort(_emotes,new EmoteEventNameComparator());
    Collections.sort(_traits,new TraitEventNameComparator());
  }

  void useAchievable(AchievableStatus status, Achievable achievable, int count)
  {
    Rewards rewards=achievable.getRewards();
    // LOTRO points
    int lp=rewards.getLotroPoints();
    _acquiredLP+=lp;
    // Class points
    int classPoints=rewards.getClassPoints();
    _classPoints+=classPoints;
    // Virtue XP
    int virtueXP=rewards.getVirtueXp();
    if (virtueXP>0)
    {
      _virtueXP.addAchievable(achievable,virtueXP,count);
    }
    // Other rewards
    for(RewardElement rewardElement : rewards.getRewardElements())
    {
      // Item
      if (rewardElement instanceof ItemReward)
      {
        ItemReward itemReward=(ItemReward)rewardElement;
        Item item=itemReward.getItem();
        int itemId=item.getIdentifier();
        int itemsCount=itemReward.getQuantity();
        int totalItemsCount=itemsCount*count;
        // Marks
        if (itemId==WellKnownItems.MARK)
        {
          _marksCount+=totalItemsCount;
        }
        // Medallions
        if (itemId==WellKnownItems.MEDALLION)
        {
          _medallionsCount+=totalItemsCount;
        }
        _items.add(itemId,totalItemsCount);
      }
      // Title
      else if (rewardElement instanceof TitleReward)
      {
        TitleReward titleReward=(TitleReward)rewardElement;
        Long date=status.getCompletionDate();
        TitleEvent event=new TitleEvent(titleReward.getName(),date,achievable);
        _titles.add(event);
      }
      // Emote
      else if (rewardElement instanceof EmoteReward)
      {
        EmoteReward emoteReward=(EmoteReward)rewardElement;
        Long date=status.getCompletionDate();
        EmoteEvent event=new EmoteEvent(emoteReward.getName(),date,achievable);
        _emotes.add(event);
      }
      // Trait
      else if (rewardElement instanceof TraitReward)
      {
        TraitReward traitReward=(TraitReward)rewardElement;
        Long date=status.getCompletionDate();
        TraitEvent event=new TraitEvent(traitReward.getName(),date,achievable);
        _traits.add(event);
      }
      // Virtue
      else if (rewardElement instanceof VirtueReward)
      {
        VirtueReward virtueReward=(VirtueReward)rewardElement;
        VirtueDescription virtue=virtueReward.getVirtue();
        VirtueStatsFromAchievables virtueStats=_virtues.get(virtue);
        if (virtueStats==null)
        {
          virtueStats=new VirtueStatsFromAchievables(virtue);
          _virtues.put(virtue,virtueStats);
        }
        int points=virtueReward.getCount();
        virtueStats.add(points);
      }
      // Reputation
      else if (rewardElement instanceof ReputationReward)
      {
        ReputationReward reputationReward=(ReputationReward)rewardElement;
        Faction faction=reputationReward.getFaction();
        AchievablesFactionStats factionStats=_reputation.get(faction,true);
        int amount=reputationReward.getAmount();
        factionStats.addCompletions(count,amount);
      }
    }
  }

  /**
   * Get the number of acquired LOTRO points.
   * @return A LOTRO points count.
   */
  public int getAcquiredLP()
  {
    return _acquiredLP;
  }

  /**
   * Get the number of acquired class points.
   * @return A class points count.
   */
  public int getClassPoints()
  {
    return _classPoints;
  }

  /**
   * Get the number of acquired marks.
   * @return A marks count.
   */
  public int getMarksCount()
  {
    return _marksCount;
  }

  /**
   * Get the number of acquired medallions.
   * @return A medallions count.
   */
  public int getMedallionsCount()
  {
    return _medallionsCount;
  }

  /**
   * Get the title events.
   * @return A list of title events, sorted by name.
   */
  public List<TitleEvent> getTitles()
  {
    return _titles;
  }

  /**
   * Get the acquired emote events.
   * @return A list of emote events, sorted by name.
   */
  public List<EmoteEvent> getEmotes()
  {
    return _emotes;
  }

  /**
   * Get the trait events.
   * @return A list of trait events, sorted by name.
   */
  public List<TraitEvent> getTraits()
  {
    return _traits;
  }

  /**
   * Get the statistics about the acquired virtues.
   * @return A map of virtues statistics.
   */
  public Map<VirtueDescription,VirtueStatsFromAchievables> getVirtues()
  {
    return _virtues;
  }

  /**
   * Get the total number of virtue points.
   * @return A virtue points count.
   */
  public int getTotalVirtuePoints()
  {
    int total=0;
    for(VirtueStatsFromAchievables virtueStats : _virtues.values())
    {
      int points=virtueStats.getPoints();
      total+=points;
    }
    return total;
  }

  /**
   * Get the virtue XP statistics.
   * @return  the virtue XP statistics.
   */
  public VirtueXPStatsFromAchievables getVirtueXPStats()
  {
    return _virtueXP;
  }

  /**
   * Get the statistics about the acquired reputation.
   * @return Reputation statistics.
   */
  public AchievablesReputationStats getReputationStats()
  {
    return _reputation;
  }

  /**
   * Get the statistics about the acquired items.
   * @return Items statistics.
   */
  public ItemsStats getItemsStats()
  {
    return _items;
  }

  /**
   * Dump results.
   * @return a displayable string.
   */
  public String dumpResults()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("LOTRO points: ").append(_acquiredLP).append(EndOfLine.NATIVE_EOL);
    sb.append("Class points: ").append(_classPoints).append(EndOfLine.NATIVE_EOL);
    sb.append("Marks: ").append(_marksCount).append(EndOfLine.NATIVE_EOL);
    sb.append("Medallions: ").append(_medallionsCount).append(EndOfLine.NATIVE_EOL);
    if (_titles.size()>0)
    {
      sb.append("Titles: (").append(_titles.size()).append(')').append(EndOfLine.NATIVE_EOL);
      for(TitleEvent title : _titles)
      {
        sb.append('\t').append(title).append(EndOfLine.NATIVE_EOL);
      }
    }
    if (_emotes.size()>0)
    {
      sb.append("Emotes: (").append(_emotes.size()).append(')').append(EndOfLine.NATIVE_EOL);
      for(EmoteEvent emote : _emotes)
      {
        sb.append('\t').append(emote).append(EndOfLine.NATIVE_EOL);
      }
    }
    if (_traits.size()>0)
    {
      sb.append("Traits: (").append(_traits.size()).append(')').append(EndOfLine.NATIVE_EOL);
      for(TraitEvent trait : _traits)
      {
        sb.append('\t').append(trait).append(EndOfLine.NATIVE_EOL);
      }
    }
    if (_virtues.size()>0)
    {
      sb.append("Virtues:").append(EndOfLine.NATIVE_EOL);
      List<VirtueDescription> virtues=VirtuesManager.getInstance().getAll();
      for(VirtueDescription virtue : virtues)
      {
        VirtueStatsFromAchievables virtueStats=_virtues.get(virtue);
        if (virtueStats!=null)
        {
          int points=virtueStats.getPoints();
          sb.append('\t').append(virtue.getName()).append(": ").append(points).append(EndOfLine.NATIVE_EOL);
        }
      }
    }
    if (_reputation.getFactionsCount()>0)
    {
      sb.append("Reputation:").append(EndOfLine.NATIVE_EOL);
      List<Faction> factions=FactionsRegistry.getInstance().getAll();
      for(Faction faction : factions)
      {
        AchievablesFactionStats factionStats=_reputation.get(faction,false);
        if (factionStats!=null)
        {
          int points=factionStats.getPoints();
          int achievables=factionStats.getAchievablesCount();
          sb.append('\t').append(faction.getName()).append(": ").append(points).append(" (").append(achievables).append(" achievables)").append(EndOfLine.NATIVE_EOL);
        }
      }
    }
    String display=sb.toString().trim();
    return display;
  }

  @Override
  public String toString()
  {
    return dumpResults();
  }
}
