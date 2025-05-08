package delta.games.lotro.lore.items.details.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.enums.AllegianceGroup;
import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.HousingHookCategory;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.details.AllegiancePoints;
import delta.games.lotro.lore.items.details.GrantType;
import delta.games.lotro.lore.items.details.GrantedElement;
import delta.games.lotro.lore.items.details.HousingHooks;
import delta.games.lotro.lore.items.details.ItemDetail;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.ItemReputation;
import delta.games.lotro.lore.items.details.ItemUsageCooldown;
import delta.games.lotro.lore.items.details.ItemXP;
import delta.games.lotro.lore.items.details.SkillToExecute;
import delta.games.lotro.lore.items.details.VirtueXP;
import delta.games.lotro.lore.items.details.WeaponSlayerInfo;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Writer for item details.
 * @author DAM
 */
public class ItemDetailsXMLWriter
{
  /**
   * Write details for an item to the given XML stream.
   * @param hd XML output stream.
   * @param item Item to write.
   * @throws SAXException If an error occurs.
   */
  public void writeDetails(TransformerHandler hd, Item item) throws SAXException
  {
    ItemDetailsManager mgr=item.getDetails();
    if (mgr==null)
    {
      return;
    }
    List<ItemDetail> details=mgr.getItemDetails();
    for(ItemDetail detail : details)
    {
      writeDetail(hd,detail);
    }
  }

  private void writeDetail(TransformerHandler hd, ItemDetail item) throws SAXException
  {
    if (item instanceof GrantedElement)
    {
      writeGrantedElement(hd,(GrantedElement<?>)item);
    }
    else if (item instanceof ItemXP)
    {
      writeItemXPElement(hd,(ItemXP)item);
    }
    else if (item instanceof VirtueXP)
    {
      writeVirtueXPElement(hd,(VirtueXP)item);
    }
    else if (item instanceof ItemReputation)
    {
      writeItemReputationElement(hd,(ItemReputation)item);
    }
    else if (item instanceof WeaponSlayerInfo)
    {
      writeWeaponSlayerElement(hd,(WeaponSlayerInfo)item);
    }
    else if (item instanceof ItemUsageCooldown)
    {
      writeUsageCooldownElement(hd,(ItemUsageCooldown)item);
    }
    else if (item instanceof SkillToExecute)
    {
      writeSkillToExecute(hd,(SkillToExecute)item);
    }
    else if (item instanceof HousingHooks)
    {
      writeHousingHooks(hd,(HousingHooks)item);
    }
    else if (item instanceof AllegiancePoints)
    {
      writeAllegiancePoints(hd,(AllegiancePoints)item);
    }
  }

  private void writeGrantedElement(TransformerHandler hd, GrantedElement<?> item) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    GrantType type=item.getType();
    attrs.addAttribute("","",ItemDetailsXMLConstants.GRANTS_TYPE_ATTR,XmlWriter.CDATA,type.name());
    // ID
    int id=item.getGrantedElement().getIdentifier();
    attrs.addAttribute("","",ItemDetailsXMLConstants.GRANTS_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",ItemDetailsXMLConstants.GRANTS_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.GRANTS_TAG);
  }

  private void writeItemXPElement(TransformerHandler hd, ItemXP itemXP) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Amount
    int amount=itemXP.getAmount();
    attrs.addAttribute("","",ItemDetailsXMLConstants.ITEM_XP_AMOUNT_ATTR,XmlWriter.CDATA,String.valueOf(amount));
    hd.startElement("","",ItemDetailsXMLConstants.ITEM_XP_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.ITEM_XP_TAG);
  }

  private void writeVirtueXPElement(TransformerHandler hd, VirtueXP virtueXP) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Amount
    int amount=virtueXP.getAmount();
    attrs.addAttribute("","",ItemDetailsXMLConstants.VIRTUE_XP_AMOUNT_ATTR,XmlWriter.CDATA,String.valueOf(amount));
    // Bonus?
    boolean bonus=virtueXP.isBonus();
    if (bonus)
    {
      attrs.addAttribute("","",ItemDetailsXMLConstants.VIRTUE_XP_BONUS_ATTR,XmlWriter.CDATA,String.valueOf(bonus));
    }
    hd.startElement("","",ItemDetailsXMLConstants.VIRTUE_XP_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.ITEM_XP_TAG);
  }

  private void writeItemReputationElement(TransformerHandler hd, ItemReputation reputation) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Faction ID
    Faction faction=reputation.getFaction();
    int factionID=faction.getIdentifier();
    attrs.addAttribute("","",ItemDetailsXMLConstants.REPUTATION_FACTION_ID_ATTR,XmlWriter.CDATA,String.valueOf(factionID));
    // Faction name
    String factionName=faction.getName();
    attrs.addAttribute("","",ItemDetailsXMLConstants.REPUTATION_FACTION_NAME_ATTR,XmlWriter.CDATA,factionName);
    // Amount
    int amount=reputation.getAmount();
    attrs.addAttribute("","",ItemDetailsXMLConstants.REPUTATION_AMOUNT_ATTR,XmlWriter.CDATA,String.valueOf(amount));
    hd.startElement("","",ItemDetailsXMLConstants.REPUTATION_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.REPUTATION_TAG);
  }

  private void writeWeaponSlayerElement(TransformerHandler hd, WeaponSlayerInfo info) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Slayer value
    float slayer=info.getSlayer();
    attrs.addAttribute("","",ItemDetailsXMLConstants.SLAYER_VALUE_ATTR,XmlWriter.CDATA,String.valueOf(slayer));
    // Genus
    List<Genus> genuses=info.getGenus();
    StringBuilder sb=new StringBuilder();
    for(Genus genus : genuses)
    {
      if (sb.length()>0)
      {
        sb.append(',');
      }
      sb.append(genus.getCode());
    }
    attrs.addAttribute("","",ItemDetailsXMLConstants.SLAYER_GENUS_ATTR,XmlWriter.CDATA,sb.toString());
    hd.startElement("","",ItemDetailsXMLConstants.SLAYER_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.SLAYER_TAG);
  }

  private void writeUsageCooldownElement(TransformerHandler hd, ItemUsageCooldown info) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Duration value
    float duration=info.getDuration();
    attrs.addAttribute("","",ItemDetailsXMLConstants.COOLDOWN_DURATION_ATTR,XmlWriter.CDATA,String.valueOf(duration));
    // Channel ID
    Integer channelID=info.getChannelID();
    if (channelID!=null)
    {
      attrs.addAttribute("","",ItemDetailsXMLConstants.COOLDOWN_CHANNEL_ID_ATTR,XmlWriter.CDATA,channelID.toString());
    }
    hd.startElement("","",ItemDetailsXMLConstants.COOLDOWN_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.COOLDOWN_TAG);
  }

  private void writeSkillToExecute(TransformerHandler hd, SkillToExecute info) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Skill ID
    SkillDescription skill=info.getSkill();
    int skillID=skill.getIdentifier();
    attrs.addAttribute("","",ItemDetailsXMLConstants.SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(skillID));
    // Skill name
    String skillName=skill.getName();
    attrs.addAttribute("","",ItemDetailsXMLConstants.SKILL_NAME_ATTR,XmlWriter.CDATA,skillName);
    // Level
    Integer level=info.getLevel();
    if (level!=null)
    {
      attrs.addAttribute("","",ItemDetailsXMLConstants.SKILL_LEVEL_ATTR,XmlWriter.CDATA,level.toString());
    }
    hd.startElement("","",ItemDetailsXMLConstants.SKILL_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.SKILL_TAG);
  }

  private void writeAllegiancePoints(TransformerHandler hd, AllegiancePoints allegiancePoints) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Group
    AllegianceGroup group=allegiancePoints.getGroup();
    attrs.addAttribute("","",ItemDetailsXMLConstants.ALLEGIANCE_GROUP_ATTR,XmlWriter.CDATA,String.valueOf(group.getCode()));
    attrs.addAttribute("","",ItemDetailsXMLConstants.ALLEGIANCE_GROUPNAME_ATTR,XmlWriter.CDATA,group.getLabel());
    // Points
    int points=allegiancePoints.getPoints();
    attrs.addAttribute("","",ItemDetailsXMLConstants.ALLEGIANCE_POINTS_ATTR,XmlWriter.CDATA,String.valueOf(points));
    hd.startElement("","",ItemDetailsXMLConstants.ALLEGIANCE_POINTS_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.ALLEGIANCE_POINTS_TAG);
  }

  private void writeHousingHooks(TransformerHandler hd, HousingHooks info) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Categories
    List<HousingHookCategory> categories=info.getHookCategories();
    StringBuilder sb=new StringBuilder();
    for(HousingHookCategory category : categories)
    {
      if (sb.length()>0)
      {
        sb.append(',');
      }
      sb.append(category.getCode());
    }
    attrs.addAttribute("","",ItemDetailsXMLConstants.HOUSING_HOOKS_CATEGORIES_ATTR,XmlWriter.CDATA,sb.toString());
    hd.startElement("","",ItemDetailsXMLConstants.HOUSING_HOOKS_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.HOUSING_HOOKS_TAG);
  }
}
