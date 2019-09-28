package delta.games.lotro.common.rewards.io.xml;

import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLWriter;
import delta.games.lotro.common.rewards.CraftingXpReward;
import delta.games.lotro.common.rewards.EmoteReward;
import delta.games.lotro.common.rewards.ItemReward;
import delta.games.lotro.common.rewards.RelicReward;
import delta.games.lotro.common.rewards.ReputationReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.RewardElementComparator;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.TitleReward;
import delta.games.lotro.common.rewards.TraitReward;
import delta.games.lotro.common.rewards.VirtueReward;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.utils.Proxy;

/**
 * Writes LOTRO rewards to XML documents.
 * @author DAM
 */
public class RewardsXMLWriter
{
  private static final String CDATA="CDATA";

  /**
   * Write a rewards object to an XML document.
   * @param hd Output transformer.
   * @param rewards Rewards to write.
   * @throws Exception If an error occurs.
   */
  public static void write(TransformerHandler hd, Rewards rewards) throws Exception
  {
    hd.startElement("","",RewardsXMLConstants.REWARDS_TAG,new AttributesImpl());
    Money money=rewards.getMoney();
    MoneyXMLWriter.writeMoney(hd,money);
    // Reputation rewards
    List<ReputationReward> reputationRewards=rewards.getRewardElementsOfClass(ReputationReward.class);
    if (reputationRewards.size()>0)
    {
      for(RewardElement rewardElement : reputationRewards)
      {
        writeRewardElement(hd,rewardElement);
      }
    }
    // LOTRO points
    int lotroPoints=rewards.getLotroPoints();
    writeAmount(hd,RewardsXMLConstants.LOTRO_POINTS,lotroPoints);
    // Class points
    int classPoints=rewards.getClassPoints();
    writeAmount(hd,RewardsXMLConstants.CLASS_POINTS,classPoints);
    // XP
    int xp=rewards.getXp();
    writeAmount(hd,RewardsXMLConstants.XP,xp);
    // Item XP
    int itemXP=rewards.getItemXp();
    writeAmount(hd,RewardsXMLConstants.ITEM_XP,itemXP);
    // Mount XP
    int mountXP=rewards.getMountXp();
    writeAmount(hd,RewardsXMLConstants.MOUNT_XP,mountXP);
    // Glory
    int glory=rewards.getGlory();
    writeAmount(hd,RewardsXMLConstants.GLORY,glory);
    // Other rewards
    Collections.sort(rewards.getRewardElements(),new RewardElementComparator());
    for(RewardElement rewardElement : rewards.getRewardElements())
    {
      if (!(rewardElement instanceof ReputationReward))
      {
        writeRewardElement(hd,rewardElement);
      }
    }
    hd.endElement("","",RewardsXMLConstants.REWARDS_TAG);
  }

  private static void writeAmount(TransformerHandler hd, String tagName, int value) throws Exception
  {
    if (value>0)
    {
      AttributesImpl attrs=new AttributesImpl();
      attrs.addAttribute("","",RewardsXMLConstants.QUANTITY_ATTR,CDATA,String.valueOf(value));
      hd.startElement("","",tagName,attrs);
      hd.endElement("","",tagName);
    }
  }

  private static void writeRewardElement(TransformerHandler hd, RewardElement rewardElement) throws SAXException
  {
    // Reputation
    if (rewardElement instanceof ReputationReward)
    {
      writeReputationReward(hd,(ReputationReward)rewardElement);
    }
    // Trait
    else if (rewardElement instanceof TraitReward)
    {
      TraitReward traitReward=(TraitReward)rewardElement;
      writeProxy(hd,RewardsXMLConstants.TRAIT_TAG,traitReward.getTraitProxy());
    }
    // Title
    else if (rewardElement instanceof TitleReward)
    {
      TitleReward titleReward=(TitleReward)rewardElement;
      writeProxy(hd,RewardsXMLConstants.TITLE_TAG,titleReward.getTitleProxy());
    }
    // Virtue
    else if (rewardElement instanceof VirtueReward)
    {
      writeVirtueReward(hd,(VirtueReward)rewardElement);
    }
    // Emote
    else if (rewardElement instanceof EmoteReward)
    {
      EmoteReward emoteReward=(EmoteReward)rewardElement;
      writeProxy(hd,RewardsXMLConstants.EMOTE_TAG,emoteReward.getEmoteProxy());
    }
    // Item
    else if (rewardElement instanceof ItemReward)
    {
      writeItemReward(hd,(ItemReward)rewardElement);
    }
    // Relic
    else if (rewardElement instanceof RelicReward)
    {
      writeRelicReward(hd,(RelicReward)rewardElement);
    }
    // Crafting XP
    else if (rewardElement instanceof CraftingXpReward)
    {
      writeCraftingXpReward(hd,(CraftingXpReward)rewardElement);
    }
    // Selectable
    else if (rewardElement instanceof SelectableRewardElement)
    {
      writeSelectableRewardElement(hd,(SelectableRewardElement)rewardElement);
    }
  }

  private static void writeReputationReward(TransformerHandler hd, ReputationReward reputationReward) throws SAXException
  {
    AttributesImpl reputationAttrs=new AttributesImpl();
    // ID
    Faction faction=reputationReward.getFaction();
    int factionId=faction.getIdentifier();
    reputationAttrs.addAttribute("","",RewardsXMLConstants.REPUTATION_ITEM_FACTION_ID_ATTR,CDATA,String.valueOf(factionId));
    // Name
    String factionName=faction.getName();
    reputationAttrs.addAttribute("","",RewardsXMLConstants.REPUTATION_ITEM_FACTION_ATTR,CDATA,factionName);
    // Amount
    reputationAttrs.addAttribute("","",RewardsXMLConstants.REPUTATION_ITEM_AMOUNT_ATTR,CDATA,String.valueOf(reputationReward.getAmount()));
    hd.startElement("","",RewardsXMLConstants.REPUTATION_ITEM_TAG,reputationAttrs);
    hd.endElement("","",RewardsXMLConstants.REPUTATION_ITEM_TAG);
  }

  private static void writeVirtueReward(TransformerHandler hd, VirtueReward virtueReward) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    VirtueId id=virtueReward.getIdentifier();
    attrs.addAttribute("","",RewardsXMLConstants.VIRTUE_ID_ATTR,CDATA,id.toString());
    int count=virtueReward.getCount();
    if (count!=1)
    {
      attrs.addAttribute("","",RewardsXMLConstants.VIRTUE_COUNT_ATTR,CDATA,String.valueOf(count));
    }
    hd.startElement("","",RewardsXMLConstants.VIRTUE_TAG,attrs);
    hd.endElement("","",RewardsXMLConstants.VIRTUE_TAG);
  }

  private static void writeItemReward(TransformerHandler hd, ItemReward itemReward) throws SAXException
  {
    Proxy<Item> item=itemReward.getItemProxy();
    int id=item.getId();
    String name=item.getName();
    int quantity=itemReward.getQuantity();
    writeQuantifiedReward(hd,RewardsXMLConstants.OBJECT_TAG,id,name,quantity);
  }

  private static void writeRelicReward(TransformerHandler hd, RelicReward relicReward) throws SAXException
  {
    Proxy<Relic> relic=relicReward.getRelicProxy();
    int id=relic.getId();
    String name=relic.getName();
    int quantity=relicReward.getQuantity();
    writeQuantifiedReward(hd,RewardsXMLConstants.RELIC_TAG,id,name,quantity);
  }

  private static void writeCraftingXpReward(TransformerHandler hd, CraftingXpReward craftingXpReward) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Profession
    Profession profession=craftingXpReward.getProfession();
    attrs.addAttribute("","",RewardsXMLConstants.CRAFTING_PROFESSION_ATTR,CDATA,profession.getKey());
    // Tier
    int tier=craftingXpReward.getTier();
    attrs.addAttribute("","",RewardsXMLConstants.CRAFTING_TIER_ATTR,CDATA,String.valueOf(tier));
    // XP value
    int xpValue=craftingXpReward.getXp();
    attrs.addAttribute("","",RewardsXMLConstants.CRAFTING_XP_ATTR,CDATA,String.valueOf(xpValue));
    hd.startElement("","",RewardsXMLConstants.CRAFTING_XP_TAG,attrs);
    hd.endElement("","",RewardsXMLConstants.CRAFTING_XP_TAG);
  }

  private static void writeProxy(TransformerHandler hd, String tagName, Proxy<? extends Identifiable> proxy) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    int id=proxy.getId();
    if (id!=0)
    {
      attrs.addAttribute("","",RewardsXMLConstants.PROXY_ID_ATTR,CDATA,String.valueOf(id));
    }
    String name=proxy.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",RewardsXMLConstants.PROXY_NAME_ATTR,CDATA,name);
    }
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }

  private static void writeQuantifiedReward(TransformerHandler hd, String tagName, int id, String name, int quantity) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    if (id!=0)
    {
      attrs.addAttribute("","",RewardsXMLConstants.PROXY_ID_ATTR,CDATA,String.valueOf(id));
    }
    if (name!=null)
    {
      attrs.addAttribute("","",RewardsXMLConstants.PROXY_NAME_ATTR,CDATA,name);
    }
    if (quantity!=1)
    {
      attrs.addAttribute("","",RewardsXMLConstants.QUANTITY_ATTR,CDATA,String.valueOf(quantity));
    }
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }

  private static void writeSelectableRewardElement(TransformerHandler hd, SelectableRewardElement selectableReward) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",RewardsXMLConstants.SELECT_ONE_OF_TAG,attrs);
    for(RewardElement element : selectableReward.getElements())
    {
      writeRewardElement(hd,element);
    }
    hd.endElement("","",RewardsXMLConstants.SELECT_ONE_OF_TAG);
  }
}
