package delta.games.lotro.lore.deeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.rewards.EmoteReward;
import delta.games.lotro.common.rewards.ItemReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SkillReward;
import delta.games.lotro.common.rewards.TitleReward;
import delta.games.lotro.common.rewards.TraitReward;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.comparators.ItemNameComparator;
import delta.games.lotro.utils.Proxy;

/**
 * Tool to explore deed rewards.
 * @author DAM
 */
public class DeedRewardsExplorer
{
  private Set<String> _emotes;
  private Set<String> _titles;
  private Set<Integer> _itemIds;
  private List<Item> _items;
  private Set<String> _skills;
  private Set<String> _traits;

  /**
   * Constructor.
   */
  public DeedRewardsExplorer()
  {
    _titles=new HashSet<String>();
    _itemIds=new HashSet<Integer>();
    _items=new ArrayList<Item>();
    _emotes=new HashSet<String>();
    _skills=new HashSet<String>();
    _traits=new HashSet<String>();
  }

  /**
   * Perform exploration.
   * @param deeds Deeds to use.
   */
  public void doIt(List<DeedDescription> deeds)
  {
    for(DeedDescription deed : deeds)
    {
      exploreDeed(deed);
    }
    resolveItems();
    //showResults();
  }

  private void exploreDeed(DeedDescription deed)
  {
    Rewards rewards=deed.getRewards();
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
      // Skills
      else if (rewardElement instanceof SkillReward)
      {
        SkillReward skillReward=(SkillReward)rewardElement;
        String skillName=skillReward.getName();
        _skills.add(skillName);
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
      // Selectable
      else if (rewardElement instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)rewardElement;
        handleRewardElements(selectableReward.getElements());
      }
    }
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
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    List<DeedDescription> deeds=DeedsManager.getInstance().getAll();
    new DeedRewardsExplorer().doIt(deeds);
  }
}
