package delta.games.lotro.common.rewards.io.xml;

import java.util.List;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserValve;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.enums.BillingGroup;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLConstants;
import delta.games.lotro.common.money.io.xml.MoneyXMLParser;
import delta.games.lotro.common.rewards.BillingTokenReward;
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
import delta.games.lotro.lore.crafting.CraftingData;
import delta.games.lotro.lore.crafting.CraftingSystem;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.Professions;
import delta.games.lotro.lore.emotes.EmoteDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.io.xml.SharedXMLConstants;

/**
 * SAX Parser for rewards stored in XML.
 * @author DAM
 */
public class RewardsSaxXMLParser extends SAXParserValve<Void>
{
  private Rewards _rewards;
  private List<RewardElement> _rewardElements;

  /**
   * Set the storage for loaded rewards.
   * @param rewards Rewards storage.
   */
  public void setRewards(Rewards rewards)
  {
    _rewards=rewards;
    _rewardElements=rewards.getRewardElements();
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (MoneyXMLConstants.MONEY_TAG.equals(tagName))
    {
      // Money
      Money money=_rewards.getMoney();
      MoneyXMLParser.loadMoney(attrs,money);
    }
    // LOTRO points
    else if (RewardsXMLConstants.LOTRO_POINTS.equals(tagName))
    {
      int lotroPoints=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      _rewards.setLotroPoints(lotroPoints);
    }
    // Class points
    else if (RewardsXMLConstants.CLASS_POINTS.equals(tagName))
    {
      int classPoints=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      _rewards.setClassPoints(classPoints);
    }
    // XP
    else if (RewardsXMLConstants.XP.equals(tagName))
    {
      int XP=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      _rewards.setXp(XP);
    }
    // Item XP
    else if (RewardsXMLConstants.ITEM_XP.equals(tagName))
    {
      int itemXP=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      _rewards.setItemXp(itemXP);
    }
    // Mount XP
    else if (RewardsXMLConstants.MOUNT_XP.equals(tagName))
    {
      int mountXP=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      _rewards.setMountXp(mountXP);
    }
    // Virtue XP
    else if (RewardsXMLConstants.VIRTUE_XP.equals(tagName))
    {
      int virtueXP=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      _rewards.setVirtueXp(virtueXP);
    }
    // Glory
    else if (RewardsXMLConstants.GLORY.equals(tagName))
    {
      int glory=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,0);
      _rewards.setGlory(glory);
    }
    else
    {
      parseRewardElementTag(_rewards.getRewardElements(),tagName,attrs);
    }
    return this;
  }

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if (RewardsXMLConstants.REWARDS_TAG.equals(tagName))
    {
      return getParent();
    }
    return this;
  }

  private void parseRewardElementTag(List<RewardElement> rewards, String tagName, Attributes attrs)
  {
    // Reputation
    if (RewardsXMLConstants.REPUTATION_ITEM_TAG.equals(tagName))
    {
      parseReputationReward(attrs);
    }
    // Trait
    else if (RewardsXMLConstants.TRAIT_TAG.equals(tagName))
    {
      parseTraitReward(attrs);
    }
    // Skill
    else if (RewardsXMLConstants.SKILL_TAG.equals(tagName))
    {
      parseSkillReward(attrs);
    }
    // Title
    else if (RewardsXMLConstants.TITLE_TAG.equals(tagName))
    {
      parseTitleReward(attrs);
    }
    // Virtue
    else if (RewardsXMLConstants.VIRTUE_TAG.equals(tagName))
    {
      parseVirtueReward(attrs);
    }
    // Emote
    else if (RewardsXMLConstants.EMOTE_TAG.equals(tagName))
    {
      parseEmoteReward(attrs);
    }
    // Item
    else if (RewardsXMLConstants.OBJECT_TAG.equals(tagName))
    {
      parseItemReward(attrs);
    }
    // Relic
    else if (RewardsXMLConstants.RELIC_TAG.equals(tagName))
    {
      parseRelicReward(attrs);
    }
    // Crafting XP
    else if (RewardsXMLConstants.CRAFTING_XP_TAG.equals(tagName))
    {
      parseCraftingXpReward(attrs);
    }
    // Billing Token
    else if (RewardsXMLConstants.BILLING_TOKEN_TAG.equals(tagName))
    {
      parseBillingTokenReward(attrs);
    }
    // Selectable
    else if (RewardsXMLConstants.SELECT_ONE_OF_TAG.equals(tagName))
    {
      SelectableRewardElement selectableReward=new SelectableRewardElement();
      rewards.add(selectableReward);
      _rewardElements=selectableReward.getElements();
    }
  }

  /**
   * End an element.
   * @param tagName Tag name.
   */
  public void endElement(String tagName)
  {
    if (RewardsXMLConstants.SELECT_ONE_OF_TAG.equals(tagName))
    {
      _rewardElements=_rewards.getRewardElements();
    }
  }

  private void parseReputationReward(Attributes attrs)
  {
    // Identifier
    int factionId=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.REPUTATION_ITEM_FACTION_ID_ATTR,0);
    // Amount
    int amount=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.REPUTATION_ITEM_AMOUNT_ATTR,0);
    Faction faction=FactionsRegistry.getInstance().getById(factionId);
    if ((faction!=null) && (amount!=0))
    {
      ReputationReward reputationReward=new ReputationReward(faction);
      reputationReward.setAmount(amount);
      _rewardElements.add(reputationReward);
    }
  }

  private void parseTraitReward(Attributes attrs)
  {
    Proxy<TraitDescription> proxy=new Proxy<TraitDescription>();
    parseProxy(attrs,proxy);
    TraitReward traitReward=new TraitReward(proxy);
    _rewardElements.add(traitReward);
  }

  private void parseSkillReward(Attributes attrs)
  {
    Proxy<TraitDescription> proxy=new Proxy<TraitDescription>();
    parseProxy(attrs,proxy);
    TraitReward traitReward=new TraitReward(proxy);
    _rewardElements.add(traitReward);
  }

  private void parseTitleReward(Attributes attrs)
  {
    Proxy<TitleDescription> proxy=new Proxy<TitleDescription>();
    parseProxy(attrs,proxy);
    TitleReward titleReward=new TitleReward(proxy);
    _rewardElements.add(titleReward);
  }

  private void parseVirtueReward(Attributes attrs)
  {
    String id=SAXParsingTools.getStringAttribute(attrs,RewardsXMLConstants.VIRTUE_ID_ATTR,null);
    int count=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.VIRTUE_COUNT_ATTR,1);
    if (id!=null)
    {
      VirtueDescription virtue=VirtuesManager.getInstance().getByKey(id);
      VirtueReward virtueReward=new VirtueReward(virtue,count);
      _rewardElements.add(virtueReward);
    }
  }

  private void parseEmoteReward(Attributes attrs)
  {
    Proxy<EmoteDescription> proxy=new Proxy<EmoteDescription>();
    parseProxy(attrs,proxy);
    EmoteReward emoteReward=new EmoteReward(proxy);
    _rewardElements.add(emoteReward);
  }

  private void parseItemReward(Attributes attrs)
  {
    int id=SAXParsingTools.getIntAttribute(attrs,SharedXMLConstants.PROXY_ID_ATTR,0);
    String name=SAXParsingTools.getStringAttribute(attrs,SharedXMLConstants.PROXY_NAME_ATTR,null);
    int quantity=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,1);
    if (((name!=null) || (id!=0)) && (quantity!=0))
    {
      Proxy<Item> item=new Proxy<Item>();
      item.setName(name);
      item.setId(id);
      ItemReward itemReward=new ItemReward(item,quantity);
      _rewardElements.add(itemReward);
    }
  }

  private void parseRelicReward(Attributes attrs)
  {
    int id=SAXParsingTools.getIntAttribute(attrs,SharedXMLConstants.PROXY_ID_ATTR,0);
    String name=SAXParsingTools.getStringAttribute(attrs,SharedXMLConstants.PROXY_NAME_ATTR,null);
    int quantity=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.QUANTITY_ATTR,1);
    if (((name!=null) || (id!=0)) && (quantity!=0))
    {
      Proxy<Relic> relic=new Proxy<Relic>();
      relic.setName(name);
      relic.setId(id);
      RelicReward relicReward=new RelicReward(relic,quantity);
      _rewardElements.add(relicReward);
    }
  }

  private void parseCraftingXpReward(Attributes attrs)
  {
    // Profession
    String professionKey=SAXParsingTools.getStringAttribute(attrs,RewardsXMLConstants.CRAFTING_PROFESSION_ATTR,null);
    CraftingData crafting=CraftingSystem.getInstance().getData();
    Professions professions=crafting.getProfessionsRegistry();
    Profession profession=professions.getProfessionByKey(professionKey);
    // Tier
    int tier=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.CRAFTING_TIER_ATTR,1);
    // XP
    int xp=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.CRAFTING_XP_ATTR,1);
    CraftingXpReward craftingXpReward=new CraftingXpReward(profession,tier,xp);
    _rewardElements.add(craftingXpReward);
  }

  private void parseBillingTokenReward(Attributes attrs)
  {
    // ID
    int id=SAXParsingTools.getIntAttribute(attrs,RewardsXMLConstants.BILLING_TOKEN_ID_ATTR,-1);
    LotroEnum<BillingGroup> billingGroupsEnum=LotroEnumsRegistry.getInstance().get(BillingGroup.class);
    BillingGroup billingGroup=billingGroupsEnum.getEntry(id);
    if (billingGroup!=null)
    {
      BillingTokenReward billingTokenReward=new BillingTokenReward(billingGroup);
      _rewardElements.add(billingTokenReward);
    }
  }

  private static void parseProxy(Attributes attrs, Proxy<? extends Identifiable> proxy)
  {
    int id=SAXParsingTools.getIntAttribute(attrs,SharedXMLConstants.PROXY_ID_ATTR,0);
    proxy.setId(id);
    String name=SAXParsingTools.getStringAttribute(attrs,SharedXMLConstants.PROXY_NAME_ATTR,null);
    proxy.setName(name);
  }
}
