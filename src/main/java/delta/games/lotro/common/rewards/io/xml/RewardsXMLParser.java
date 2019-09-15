package delta.games.lotro.common.rewards.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLParser;
import delta.games.lotro.common.rewards.CraftingXpReward;
import delta.games.lotro.common.rewards.EmoteReward;
import delta.games.lotro.common.rewards.ItemReward;
import delta.games.lotro.common.rewards.RelicReward;
import delta.games.lotro.common.rewards.ReputationReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.TitleReward;
import delta.games.lotro.common.rewards.TraitReward;
import delta.games.lotro.common.rewards.VirtueReward;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.emotes.EmoteDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Parser for rewards stored in XML.
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
      MoneyXMLParser.loadMoney(root,money);

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
      int XP=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      rewards.setXp(XP);
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
    if (RewardsXMLConstants.REPUTATION_TAG.equals(tagName))
    {
      for(Element repItemTag : DOMParsingTools.getChildTags(rewardTag))
      {
        parseReputationReward(rewards,repItemTag);
      }
    }
    else if (RewardsXMLConstants.REPUTATION_ITEM_TAG.equals(tagName))
    {
      parseReputationReward(rewards,rewardTag);
    }
    // Trait
    else if (RewardsXMLConstants.TRAIT_TAG.equals(tagName))
    {
      parseTraitReward(rewards,rewardTag);
    }
    // Skill
    else if (RewardsXMLConstants.SKILL_TAG.equals(tagName))
    {
      parseSkillReward(rewards,rewardTag);
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
    String factionName=DOMParsingTools.getStringAttribute(attrs,RewardsXMLConstants.REPUTATION_ITEM_FACTION_ATTR,null);
    int amount=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.REPUTATION_ITEM_AMOUNT_ATTR,0);
    Faction faction=FactionsRegistry.getInstance().getByName(factionName);
    if ((faction!=null) && (amount!=0))
    {
      ReputationReward reputationReward=new ReputationReward(faction);
      reputationReward.setAmount(amount);
      rewards.add(reputationReward);
    }
  }

  private static void parseTraitReward(List<RewardElement> rewards, Element traitTag)
  {
    Proxy<TraitDescription> proxy=new Proxy<TraitDescription>();
    parseProxy(traitTag.getAttributes(),proxy);
    TraitReward traitReward=new TraitReward(proxy);
    rewards.add(traitReward);
  }

  private static void parseSkillReward(List<RewardElement> rewards, Element skillTag)
  {
    Proxy<TraitDescription> proxy=new Proxy<TraitDescription>();
    parseProxy(skillTag.getAttributes(),proxy);
    TraitReward traitReward=new TraitReward(proxy);
    rewards.add(traitReward);
  }

  private static void parseTitleReward(List<RewardElement> rewards, Element titleTag)
  {
    Proxy<TitleDescription> proxy=new Proxy<TitleDescription>();
    parseProxy(titleTag.getAttributes(),proxy);
    TitleReward titleReward=new TitleReward(proxy);
    rewards.add(titleReward);
  }

  private static void parseVirtueReward(List<RewardElement> rewards, Element virtueTag)
  {
    NamedNodeMap attrs=virtueTag.getAttributes();
    String id=DOMParsingTools.getStringAttribute(attrs,RewardsXMLConstants.VIRTUE_ID_ATTR,null);
    int count=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.VIRTUE_COUNT_ATTR,1);
    if (id!=null)
    {
      VirtueId virtueId=VirtueId.valueOf(id.toUpperCase());
      VirtueReward virtueReward=new VirtueReward(virtueId,count);
      rewards.add(virtueReward);
    }
  }

  private static void parseEmoteReward(List<RewardElement> rewards, Element emoteTag)
  {
    Proxy<EmoteDescription> proxy=new Proxy<EmoteDescription>();
    parseProxy(emoteTag.getAttributes(),proxy);
    EmoteReward emoteReward=new EmoteReward(proxy);
    rewards.add(emoteReward);
  }

  private static void parseItemReward(List<RewardElement> rewards, Element itemTag)
  {
    NamedNodeMap attrs=itemTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.PROXY_ID_ATTR,0);
    String name=DOMParsingTools.getStringAttribute(attrs,RewardsXMLConstants.PROXY_NAME_ATTR,null);
    int quantity=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,1);
    if (((name!=null) || (id!=0)) && (quantity!=0))
    {
      Proxy<Item> item=new Proxy<Item>();
      item.setName(name);
      item.setId(id);
      ItemReward itemReward=new ItemReward(item,quantity);
      rewards.add(itemReward);
    }
  }

  private static void parseRelicReward(List<RewardElement> rewards, Element relicTag)
  {
    NamedNodeMap attrs=relicTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.PROXY_ID_ATTR,0);
    String name=DOMParsingTools.getStringAttribute(attrs,RewardsXMLConstants.PROXY_NAME_ATTR,null);
    int quantity=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,1);
    if (((name!=null) || (id!=0)) && (quantity!=0))
    {
      Proxy<Relic> relic=new Proxy<Relic>();
      relic.setName(name);
      relic.setId(id);
      RelicReward relicReward=new RelicReward(relic,quantity);
      rewards.add(relicReward);
    }
  }

  private static void parseCraftingXpReward(List<RewardElement> rewards, Element craftingXpTag)
  {
    NamedNodeMap attrs=craftingXpTag.getAttributes();
    String professionKey=DOMParsingTools.getStringAttribute(attrs,RewardsXMLConstants.CRAFTING_PROFESSION_ATTR,null);
    Profession profession=Profession.getByKey(professionKey);
    int tier=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.CRAFTING_TIER_ATTR,1);
    int xp=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.CRAFTING_XP_ATTR,1);
    CraftingXpReward craftingXpReward=new CraftingXpReward(profession,tier,xp);
    rewards.add(craftingXpReward);
  }

  private static void parseProxy(NamedNodeMap attrs, Proxy<? extends Identifiable> proxy)
  {
    int id=DOMParsingTools.getIntAttribute(attrs,RewardsXMLConstants.PROXY_ID_ATTR,0);
    proxy.setId(id);
    String name=DOMParsingTools.getStringAttribute(attrs,RewardsXMLConstants.PROXY_NAME_ATTR,null);
    proxy.setName(name);
  }
}
