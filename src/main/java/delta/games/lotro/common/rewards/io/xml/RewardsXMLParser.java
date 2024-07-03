package delta.games.lotro.common.rewards.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.common.enums.BillingGroup;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLParser;
import delta.games.lotro.common.rewards.BillingTokenReward;
import delta.games.lotro.common.rewards.CraftingXpReward;
import delta.games.lotro.common.rewards.EmoteReward;
import delta.games.lotro.common.rewards.ItemReward;
import delta.games.lotro.common.rewards.QuestCompleteReward;
import delta.games.lotro.common.rewards.RelicReward;
import delta.games.lotro.common.rewards.ReputationReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.TitleReward;
import delta.games.lotro.common.rewards.TraitReward;
import delta.games.lotro.common.rewards.VirtueReward;
import delta.games.lotro.lore.crafting.CraftingData;
import delta.games.lotro.lore.crafting.CraftingSystem;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.Professions;
import delta.games.lotro.lore.emotes.EmoteDescription;
import delta.games.lotro.lore.emotes.EmotesManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.lore.titles.TitlesManager;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.io.xml.SharedXMLConstants;

/**
 * Parser for rewards stored in XML (for collections only!).
 * @author DAM
 */
public class RewardsXMLParser
{
  /**
   * Load rewards from XML.
   * @param root Reward description tag.
   * @param rewards Storage for loaded data. 
   */
  public static void loadRewards(Element root, Rewards rewards)
  {
    Element rewardsTag=DOMParsingTools.getChildTagByName(root,RewardsXMLConstants.REWARDS_TAG);
    if (rewardsTag!=null)
    {
      // Money
      Money money=rewards.getMoney();
      MoneyXMLParser.loadMoney(rewardsTag,money);

      List<Element> childTags=DOMParsingTools.getChildTags(rewardsTag);
      for(Element childTag : childTags)
      {
        parseRewardTag(rewards, childTag);
      }
    }
  }

  private static void parseRewardTag(Rewards rewards, Element rewardTag)
  {
    String tagName=rewardTag.getTagName();
    // LOTRO points
    if (RewardsXMLConstants.LOTRO_POINTS.equals(tagName))
    {
      NamedNodeMap attrs=rewardTag.getAttributes();
      int lotroPoints=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      rewards.setLotroPoints(lotroPoints);
    }
    // Class points
    else if (RewardsXMLConstants.CLASS_POINTS.equals(tagName))
    {
      NamedNodeMap attrs=rewardTag.getAttributes();
      int classPoints=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      rewards.setClassPoints(classPoints);
    }
    // XP
    else if (RewardsXMLConstants.XP.equals(tagName))
    {
      NamedNodeMap attrs=rewardTag.getAttributes();
      int xp=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      rewards.setXp(xp);
    }
    // Item XP
    else if (RewardsXMLConstants.ITEM_XP.equals(tagName))
    {
      NamedNodeMap attrs=rewardTag.getAttributes();
      int itemXP=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      rewards.setItemXp(itemXP);
    }
    // Mount XP
    else if (RewardsXMLConstants.MOUNT_XP.equals(tagName))
    {
      NamedNodeMap attrs=rewardTag.getAttributes();
      int mountXP=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      rewards.setMountXp(mountXP);
    }
    // Virtue XP
    else if (RewardsXMLConstants.VIRTUE_XP.equals(tagName))
    {
      NamedNodeMap attrs=rewardTag.getAttributes();
      int virtueXP=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      rewards.setVirtueXp(virtueXP);
    }
    // Glory
    else if (RewardsXMLConstants.GLORY.equals(tagName))
    {
      NamedNodeMap attrs=rewardTag.getAttributes();
      int glory=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      rewards.setGlory(glory);
    }
    else
    {
      parseRewardElementTag(rewards.getRewardElements(),rewardTag);
    }
  }

  private static void parseRewardElementTag(List<RewardElement> rewards, Element rewardTag)
  {
    String tagName=rewardTag.getTagName();
    // Reputation
    if (RewardsXMLConstants.REPUTATION_ITEM_TAG.equals(tagName))
    {
      parseReputationReward(rewards,rewardTag);
    }
    // Trait
    else if (RewardsXMLConstants.TRAIT_TAG.equals(tagName))
    {
      parseTraitReward(rewards,rewardTag);
    }
    // Title
    else if (RewardsXMLConstants.TITLE_TAG.equals(tagName))
    {
      parseTitleReward(rewards,rewardTag);
    }
    // Virtue
    else if (RewardsXMLConstants.VIRTUE_TAG.equals(tagName))
    {
      parseVirtueReward(rewards,rewardTag);
    }
    // Emote
    else if (RewardsXMLConstants.EMOTE_TAG.equals(tagName))
    {
      parseEmoteReward(rewards,rewardTag);
    }
    // Item
    else if (RewardsXMLConstants.OBJECT_TAG.equals(tagName))
    {
      parseItemReward(rewards,rewardTag);
    }
    // Relic
    else if (RewardsXMLConstants.RELIC_TAG.equals(tagName))
    {
      parseRelicReward(rewards,rewardTag);
    }
    // Crafting XP
    else if (RewardsXMLConstants.CRAFTING_XP_TAG.equals(tagName))
    {
      parseCraftingXpReward(rewards,rewardTag);
    }
    // Billing Token
    else if (RewardsXMLConstants.BILLING_TOKEN_TAG.equals(tagName))
    {
      parseBillingTokenReward(rewards,rewardTag);
    }
    // Quest Complete
    else if (RewardsXMLConstants.QUEST_COMPLETE_TAG.equals(tagName))
    {
      parseQuestCompleteReward(rewards,rewardTag);
    }
    // Selectable
    else if (RewardsXMLConstants.SELECT_ONE_OF_TAG.equals(tagName))
    {
      SelectableRewardElement selectableReward=new SelectableRewardElement();
      List<RewardElement> selectableRewardElements=selectableReward.getElements();
      List<Element> selectableRewardTags=DOMParsingTools.getChildTags(rewardTag);
      for(Element selectableRewardTag : selectableRewardTags)
      {
        parseRewardElementTag(selectableRewardElements,selectableRewardTag);
      }
      rewards.add(selectableReward);
    }
  }

  private static void parseReputationReward(List<RewardElement> rewards, Element reputationTag)
  {
    NamedNodeMap attrs=reputationTag.getAttributes();
    // Identifier
    int factionId=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.REPUTATION_ITEM_FACTION_ID_ATTR,0);
    // Amount
    int amount=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.REPUTATION_ITEM_AMOUNT_ATTR,0);
    Faction faction=FactionsRegistry.getInstance().getById(factionId);
    if ((faction!=null) && (amount!=0))
    {
      ReputationReward reputationReward=new ReputationReward(faction);
      reputationReward.setAmount(amount);
      rewards.add(reputationReward);
    }
  }

  private static void parseTraitReward(List<RewardElement> rewards, Element traitTag)
  {
    int id=DOMParsingTools.getIntAttribute(traitTag.getAttributes(),SharedXMLConstants.PROXY_ID_ATTR,0);
    TraitDescription trait=TraitsManager.getInstance().getTrait(id);
    if (trait!=null)
    {
      TraitReward traitReward=new TraitReward(trait);
      rewards.add(traitReward);
    }
  }

  private static void parseTitleReward(List<RewardElement> rewards, Element titleTag)
  {
    int id=DOMParsingTools.getIntAttribute(titleTag.getAttributes(),SharedXMLConstants.PROXY_ID_ATTR,0);
    TitleDescription title=TitlesManager.getInstance().getTitle(id);
    if (title!=null)
    {
      TitleReward titleReward=new TitleReward(title);
      rewards.add(titleReward);
    }
  }

  private static void parseVirtueReward(List<RewardElement> rewards, Element virtueTag)
  {
    NamedNodeMap attrs=virtueTag.getAttributes();
    String id=DOMParsingTools.getStringAttribute(attrs,RewardsXMLConstants.VIRTUE_ID_ATTR,null);
    int count=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.VIRTUE_COUNT_ATTR,1);
    if (id!=null)
    {
      VirtueDescription virtue=VirtuesManager.getInstance().getByKey(id);
      VirtueReward virtueReward=new VirtueReward(virtue,count);
      rewards.add(virtueReward);
    }
  }

  private static void parseEmoteReward(List<RewardElement> rewards, Element emoteTag)
  {
    int id=DOMParsingTools.getIntAttribute(emoteTag.getAttributes(),SharedXMLConstants.PROXY_ID_ATTR,0);
    EmoteDescription emote=EmotesManager.getInstance().getEmote(id);
    if (emote!=null)
    {
      EmoteReward emoteReward=new EmoteReward(emote);
      rewards.add(emoteReward);
    }
  }

  private static void parseItemReward(List<RewardElement> rewards, Element itemTag)
  {
    NamedNodeMap attrs=itemTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,SharedXMLConstants.PROXY_ID_ATTR,0);
    int quantity=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,1);
    if ((id!=0) && (quantity!=0))
    {
      Item item=ItemsManager.getInstance().getItem(id);
      if (item!=null)
      {
        ItemReward itemReward=new ItemReward(item,quantity);
        rewards.add(itemReward);
      }
    }
  }

  private static void parseRelicReward(List<RewardElement> rewards, Element relicTag)
  {
    NamedNodeMap attrs=relicTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,SharedXMLConstants.PROXY_ID_ATTR,0);
    int quantity=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,1);
    if ((id!=0) && (quantity!=0))
    {
      Relic relic=RelicsManager.getInstance().getById(id);
      if (relic!=null)
      {
        RelicReward relicReward=new RelicReward(relic,quantity);
        rewards.add(relicReward);
      }
    }
  }

  private static void parseCraftingXpReward(List<RewardElement> rewards, Element craftingXpTag)
  {
    NamedNodeMap attrs=craftingXpTag.getAttributes();

    // Profession
    String professionKey=DOMParsingTools.getStringAttribute(attrs,RewardsXMLConstants.CRAFTING_PROFESSION_ATTR,null);
    CraftingData crafting=CraftingSystem.getInstance().getData();
    Professions professions=crafting.getProfessionsRegistry();
    Profession profession=professions.getProfessionByKey(professionKey);
    // Tier
    int tier=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.CRAFTING_TIER_ATTR,1);
    // XP
    int xp=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.CRAFTING_XP_ATTR,1);
    CraftingXpReward craftingXpReward=new CraftingXpReward(profession,tier,xp);
    rewards.add(craftingXpReward);
  }

  private static void parseBillingTokenReward(List<RewardElement> rewards, Element billingTokenTag)
  {
    NamedNodeMap attrs=billingTokenTag.getAttributes();

    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.BILLING_TOKEN_ID_ATTR,-1);
    LotroEnum<BillingGroup> billingGroupsEnum=LotroEnumsRegistry.getInstance().get(BillingGroup.class);
    BillingGroup billingGroup=billingGroupsEnum.getEntry(id);
    if (billingGroup!=null)
    {
      BillingTokenReward billingTokenReward=new BillingTokenReward(billingGroup);
      rewards.add(billingTokenReward);
    }
  }

  private static void parseQuestCompleteReward(List<RewardElement> rewards, Element questCompleteTag)
  {
    NamedNodeMap attrs=questCompleteTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUEST_COMPLETE_ID_ATTR,-1);
    Proxy<Achievable> achievableProxy=new Proxy<Achievable>();
    achievableProxy.setId(id);
    QuestCompleteReward reward=new QuestCompleteReward(achievableProxy);
    rewards.add(reward);
  }
}
