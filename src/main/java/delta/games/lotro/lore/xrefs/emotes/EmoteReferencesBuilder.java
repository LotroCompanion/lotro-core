package delta.games.lotro.lore.xrefs.emotes;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.rewards.EmoteReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.emotes.EmoteDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.details.GrantedElement;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;
import delta.games.lotro.lore.xrefs.Reference;

/**
 * Finds references to emotes.
 * @author DAM
 */
public class EmoteReferencesBuilder
{
  private List<Reference<?,EmoteRole>> _storage;

  /**
   * Constructor.
   */
  public EmoteReferencesBuilder()
  {
    _storage=new ArrayList<Reference<?,EmoteRole>>();
  }

  /**
   * Search for an emote.
   * @param emoteID Emote identifier.
   * @return the found references.
   */
  public List<Reference<?,EmoteRole>> inspectEmote(int emoteID)
  {
    _storage.clear();
    findInRewards(emoteID);
    findInItems(emoteID);
    List<Reference<?,EmoteRole>> ret=new ArrayList<Reference<?,EmoteRole>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInRewards(int emoteID)
  {
    // Quests
    QuestsManager questsManager=QuestsManager.getInstance();
    List<QuestDescription> quests=questsManager.getAll();
    for(QuestDescription quest : quests)
    {
      findInRewards(quest,quest.getRewards(),emoteID);
    }
    // Deeds
    DeedsManager deedsManager=DeedsManager.getInstance();
    List<DeedDescription> deeds=deedsManager.getAll();
    for(DeedDescription deed : deeds)
    {
      findInRewards(deed,deed.getRewards(),emoteID);
    }
  }

  private void findInRewards(Achievable context, Rewards rewards, int emoteID)
  {
    List<RewardElement> elements=rewards.getRewardElements();
    findInRewardsElements(context,elements,emoteID);
  }

  private void findInRewardsElements(Achievable context, List<RewardElement> elements, int emoteID)
  {
    for(RewardElement element : elements)
    {
      if (element instanceof EmoteReward)
      {
        EmoteReward emoteReward=(EmoteReward)element;
        int emoteRewardID=emoteReward.getEmote().getIdentifier();
        if (emoteRewardID==emoteID)
        {
          EmoteRole role=EmoteRole.REGULAR_REWARD;
          _storage.add(new Reference<Achievable,EmoteRole>(context,role));
        }
      }
      else if (element instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)element;
        findInRewardsElements(context,selectableReward.getElements(),emoteID);
      }
    }
  }

  private void findInItems(int emoteID)
  {
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      ItemDetailsManager detailsMgr=item.getDetails();
      if (detailsMgr==null)
      {
        continue;
      }
      for(GrantedElement<?> element : detailsMgr.getItemDetails(GrantedElement.class))
      {
        Identifiable identifiable=element.getGrantedElement();
        if (identifiable instanceof EmoteDescription)
        {
          if (identifiable.getIdentifier()==emoteID)
          {
            _storage.add(new Reference<Item,EmoteRole>(item,EmoteRole.GRANTED_BY_ITEM));
          }
        }
      }
    }
  }

}
