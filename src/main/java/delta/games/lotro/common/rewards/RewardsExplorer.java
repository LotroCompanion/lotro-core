package delta.games.lotro.common.rewards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.comparators.ItemNameComparator;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicComparator;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;
import delta.games.lotro.utils.Proxy;

/**
 * Tool to explore rewards.
 * @author DAM
 */
public class RewardsExplorer
{
  private Set<String> _emotes;
  private Set<String> _titles;
  private Set<Integer> _itemIds;
  private List<Item> _items;
  private Set<Integer> _relicIds;
  private List<Relic> _relics;
  private Set<String> _skills;
  private Set<String> _traits;

  /**
   * Constructor.
   */
  public RewardsExplorer()
  {
    _titles=new HashSet<String>();
    _itemIds=new HashSet<Integer>();
    _items=new ArrayList<Item>();
    _relicIds=new HashSet<Integer>();
    _relics=new ArrayList<Relic>();
    _emotes=new HashSet<String>();
    _skills=new HashSet<String>();
    _traits=new HashSet<String>();
  }

  /**
   * Perform exploration.
   * @param rewards Rewards to use.
   */
  public void doIt(Rewards rewards)
  {
    handleRewardElements(rewards.getRewardElements());
  }

  private void handleRewardElements(List<RewardElement> rewardElements)
  {
    for(RewardElement rewardElement : rewardElements)
    {
      // Emotes
      if (rewardElement instanceof EmoteReward)
      {
        EmoteReward emoteReward=(EmoteReward)rewardElement;
        String emoteName=emoteReward.getName();
        _emotes.add(emoteName);
      }
      // Titles
      else if (rewardElement instanceof TitleReward)
      {
        TitleReward titleReward=(TitleReward)rewardElement;
        String titleName=titleReward.getName();
        _titles.add(titleName);
      }
      // Traits
      else if (rewardElement instanceof TraitReward)
      {
        TraitReward traitReward=(TraitReward)rewardElement;
        String traitName=traitReward.getName();
        _traits.add(traitName);
      }
      // Items
      else if (rewardElement instanceof ItemReward)
      {
        ItemReward itemReward=(ItemReward)rewardElement;
        Proxy<Item> object=itemReward.getItemProxy();
        int id=object.getId();
        if (id!=0)
        {
          _itemIds.add(Integer.valueOf(id));
        }
      }
      // Relics
      else if (rewardElement instanceof RelicReward)
      {
        RelicReward relicReward=(RelicReward)rewardElement;
        Proxy<Relic> object=relicReward.getRelicProxy();
        int id=object.getId();
        if (id!=0)
        {
          _relicIds.add(Integer.valueOf(id));
        }
      }
      // Selectable
      else if (rewardElement instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)rewardElement;
        handleRewardElements(selectableReward.getElements());
      }
    }
  }

  /**
   * Resolve proxies.
   */
  public void resolveProxies()
  {
    resolveItems();
    resolveRelics();
  }

  private void resolveItems()
  {
    ItemsManager items=ItemsManager.getInstance();
    for(Integer id : _itemIds)
    {
      Item item=items.getItem(id.intValue());
      if (item!=null)
      {
        _items.add(item);
      }
    }
  }

  private void resolveRelics()
  {
    RelicsManager relics=RelicsManager.getInstance();
    for(Integer id : _relicIds)
    {
      Relic relic=relics.getById(id.intValue());
      if (relic!=null)
      {
        _relics.add(relic);
      }
    }
  }

  /*
  private void showResults()
  {
    showList("Emotes",_emotes);
    showList("Items",_itemNames);
    showList("Skills",_skills);
    showList("Titles",_titles);
    showList("Traits",_traits);
  }

  private void showList(String category, Set<String> items)
  {
    System.out.println(category+" ("+items.size()+")");
    List<String> itemsList=new ArrayList<String>(items);
    Collections.sort(itemsList);
    for(String item : itemsList)
    {
      System.out.println("\t"+item);
    }
  }
  */

  /**
   * Get all traits.
   * @return a sorted list of traits.
   */
  public List<String> getTraits()
  {
    List<String> ret=new ArrayList<String>(_traits);
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get all skills.
   * @return a sorted list of skills.
   */
  public List<String> getSkills()
  {
    List<String> ret=new ArrayList<String>(_skills);
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get all titles.
   * @return a sorted list of titles.
   */
  public List<String> getTitles()
  {
    List<String> ret=new ArrayList<String>(_titles);
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get all emotes.
   * @return a sorted list of emotes.
   */
  public List<String> getEmotes()
  {
    List<String> ret=new ArrayList<String>(_emotes);
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get all items.
   * @return a list of items, sorted by name.
   */
  public List<Item> getItems()
  {
    List<Item> ret=new ArrayList<Item>(_items);
    Collections.sort(ret,new ItemNameComparator());
    return ret;
  }

  /**
   * Get all relics.
   * @return a list of relics, sorted by type and name.
   */
  public List<Relic> getRelics()
  {
    List<Relic> ret=new ArrayList<Relic>(_relics);
    Collections.sort(ret,new RelicComparator());
    return ret;
  }
}
