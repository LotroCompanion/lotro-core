package delta.games.lotro.common.rewards.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.common.VirtueId;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLWriter;
import delta.games.lotro.common.rewards.EmoteReward;
import delta.games.lotro.common.rewards.ItemReward;
import delta.games.lotro.common.rewards.ReputationReward;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.SkillReward;
import delta.games.lotro.common.rewards.TitleReward;
import delta.games.lotro.common.rewards.TraitReward;
import delta.games.lotro.common.rewards.VirtueReward;
import delta.games.lotro.lore.items.Item;
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
    // Destiny points
    int destinyPoints=rewards.getDestinyPoints();
    if (destinyPoints>0)
    {
      AttributesImpl attrs=new AttributesImpl();
      attrs.addAttribute("","",RewardsXMLConstants.QUANTITY_DESTINY_POINTS_ATTR,CDATA,String.valueOf(destinyPoints));
      hd.startElement("","",RewardsXMLConstants.DESTINY_POINTS_TAG,attrs);
      hd.endElement("","",RewardsXMLConstants.DESTINY_POINTS_TAG);
    }
    // LOTRO points
    int lotroPoints=rewards.getLotroPoints();
    if (lotroPoints>0)
    {
      AttributesImpl attrs=new AttributesImpl();
      attrs.addAttribute("","",RewardsXMLConstants.QUANTITY_LOTRO_POINTS_ATTR,CDATA,String.valueOf(lotroPoints));
      hd.startElement("","",RewardsXMLConstants.LOTRO_POINTS_TAG,attrs);
      hd.endElement("","",RewardsXMLConstants.LOTRO_POINTS_TAG);
    }
    // Class points
    int classPoints=rewards.getClassPoints();
    if (classPoints>0)
    {
      AttributesImpl attrs=new AttributesImpl();
      attrs.addAttribute("","",RewardsXMLConstants.QUANTITY_CLASS_POINTS_ATTR,CDATA,String.valueOf(classPoints));
      hd.startElement("","",RewardsXMLConstants.CLASS_POINTS_TAG,attrs);
      hd.endElement("","",RewardsXMLConstants.CLASS_POINTS_TAG);
    }
    // Item XP
    boolean hasItemXP=rewards.hasItemXP();
    if (hasItemXP)
    {
      hd.startElement("","",RewardsXMLConstants.ITEM_XP_TAG,new AttributesImpl());
      hd.endElement("","",RewardsXMLConstants.ITEM_XP_TAG);
    }
    for(RewardElement rewardElement : rewards.getRewardElements())
    {
      writeRewardElement(hd,rewardElement);
    }
    hd.endElement("","",RewardsXMLConstants.REWARDS_TAG);
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
      writeTraitReward(hd,(TraitReward)rewardElement);
    }
    // Skill
    else if (rewardElement instanceof SkillReward)
    {
      writeSkillReward(hd,(SkillReward)rewardElement);
    }
    // Title
    else if (rewardElement instanceof TitleReward)
    {
      writeTitleReward(hd,(TitleReward)rewardElement);
    }
    // Virtue
    else if (rewardElement instanceof VirtueReward)
    {
      writeVirtueReward(hd,(VirtueReward)rewardElement);
    }
    // Emote
    else if (rewardElement instanceof EmoteReward)
    {
      writeEmoteReward(hd,(EmoteReward)rewardElement);
    }
    // Item
    else if (rewardElement instanceof ItemReward)
    {
      writeItemReward(hd,(ItemReward)rewardElement);
    }
    else if (rewardElement instanceof SelectableRewardElement)
    {
      writeSelectableRewardElement(hd,(SelectableRewardElement)rewardElement);
    }
  }

  private static void writeReputationReward(TransformerHandler hd, ReputationReward reputationReward) throws SAXException
  {
    AttributesImpl reputationAttrs=new AttributesImpl();
    String factionName=reputationReward.getFaction().getName();
    reputationAttrs.addAttribute("","",RewardsXMLConstants.REPUTATION_ITEM_FACTION_ATTR,CDATA,factionName);
    reputationAttrs.addAttribute("","",RewardsXMLConstants.REPUTATION_ITEM_AMOUNT_ATTR,CDATA,String.valueOf(reputationReward.getAmount()));
    hd.startElement("","",RewardsXMLConstants.REPUTATION_ITEM_TAG,reputationAttrs);
    hd.endElement("","",RewardsXMLConstants.REPUTATION_ITEM_TAG);
  }

  private static void writeTraitReward(TransformerHandler hd, TraitReward traitReward) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    //String id=trait.getIdentifier();
    //attrs.addAttribute("","",RewardsXMLConstants.TRAIT_ID_ATTR,CDATA,id);
    String name=traitReward.getName();
    attrs.addAttribute("","",RewardsXMLConstants.TRAIT_NAME_ATTR,CDATA,name);
    hd.startElement("","",RewardsXMLConstants.TRAIT_TAG,attrs);
    hd.endElement("","",RewardsXMLConstants.TRAIT_TAG);
  }

  private static void writeSkillReward(TransformerHandler hd, SkillReward skillReward) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    //String id=skill.getIdentifier();
    //attrs.addAttribute("","",RewardsXMLConstants.SKILL_ID_ATTR,CDATA,id);
    //String type=skill.getType().toString();
    //attrs.addAttribute("","",RewardsXMLConstants.SKILL_TYPE_ATTR,CDATA,type);
    String name=skillReward.getName();
    attrs.addAttribute("","",RewardsXMLConstants.SKILL_NAME_ATTR,CDATA,name);
    hd.startElement("","",RewardsXMLConstants.SKILL_TAG,attrs);
    hd.endElement("","",RewardsXMLConstants.SKILL_TAG);
  }

  private static void writeTitleReward(TransformerHandler hd, TitleReward titleReward) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    //String id=title.getIdentifier();
    //attrs.addAttribute("","",RewardsXMLConstants.TITLE_ID_ATTR,CDATA,id);
    String name=titleReward.getName();
    attrs.addAttribute("","",RewardsXMLConstants.TITLE_NAME_ATTR,CDATA,name);
    hd.startElement("","",RewardsXMLConstants.TITLE_TAG,attrs);
    hd.endElement("","",RewardsXMLConstants.TITLE_TAG);
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

  private static void writeEmoteReward(TransformerHandler hd, EmoteReward emoteReward) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    //String id=emote.getIdentifier();
    //attrs.addAttribute("","",RewardsXMLConstants.EMOTE_ID_ATTR,CDATA,id);
    String name=emoteReward.getName();
    attrs.addAttribute("","",RewardsXMLConstants.EMOTE_NAME_ATTR,CDATA,name);
    hd.startElement("","",RewardsXMLConstants.EMOTE_TAG,attrs);
    hd.endElement("","",RewardsXMLConstants.EMOTE_TAG);
  }

  private static void writeItemReward(TransformerHandler hd, ItemReward itemReward) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Proxy<Item> item=itemReward.getItemProxy();
    int id=item.getId();
    if (id!=0)
    {
      attrs.addAttribute("","",RewardsXMLConstants.OBJECT_ID_ATTR,CDATA,String.valueOf(id));
    }
    String name=item.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",RewardsXMLConstants.OBJECT_NAME_ATTR,CDATA,name);
    }
    int quantity=itemReward.getQuantity();
    if (quantity!=1)
    {
      attrs.addAttribute("","",RewardsXMLConstants.OBJECT_QUANTITY_ATTR,CDATA,String.valueOf(quantity));
    }
    hd.startElement("","",RewardsXMLConstants.OBJECT_TAG,attrs);
    hd.endElement("","",RewardsXMLConstants.OBJECT_TAG);
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
