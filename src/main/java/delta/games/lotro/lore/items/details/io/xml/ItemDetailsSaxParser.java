package delta.games.lotro.lore.items.details.io.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.enums.AllegianceGroup;
import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.HousingHookCategory;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.emotes.EmoteDescription;
import delta.games.lotro.lore.emotes.EmotesManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.details.AllegiancePoints;
import delta.games.lotro.lore.items.details.GrantType;
import delta.games.lotro.lore.items.details.GrantedElement;
import delta.games.lotro.lore.items.details.HousingHooks;
import delta.games.lotro.lore.items.details.ItemReputation;
import delta.games.lotro.lore.items.details.ItemUsageCooldown;
import delta.games.lotro.lore.items.details.ItemXP;
import delta.games.lotro.lore.items.details.SkillToExecute;
import delta.games.lotro.lore.items.details.VirtueXP;
import delta.games.lotro.lore.items.details.WeaponSlayerInfo;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * SAX parser for item details.
 * @author DAM
 */
public class ItemDetailsSaxParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ItemDetailsSaxParser.class);

  private LotroEnum<AllegianceGroup> _allegianceGroupEnum;

  /**
   * Constructor.
   */
  public ItemDetailsSaxParser()
  {
    _allegianceGroupEnum=LotroEnumsRegistry.getInstance().get(AllegianceGroup.class);
  }

  /**
   * Handle an element start. 
   * @param item Parent item.
   * @param qualifiedName Tag name.
   * @param attributes Attributes.
   * @return <code>true</code> if tag was used, <code>false</code> otherwise.
   * @throws SAXException If an error occurs.
   */
  public boolean startElement(Item item, String qualifiedName, Attributes attributes) throws SAXException
  {
    if (ItemDetailsXMLConstants.GRANTS_TAG.equals(qualifiedName))
    {
      // Type
      String typeStr=attributes.getValue(ItemDetailsXMLConstants.GRANTS_TYPE_ATTR);
      GrantType type=GrantType.valueOf(typeStr);
      // ID
      String idStr=attributes.getValue(ItemDetailsXMLConstants.GRANTS_ID_ATTR);
      int id=NumericTools.parseInt(idStr,-1);
      GrantedElement<?> grantedElement=buildGrantedElement(id,type);
      if (grantedElement!=null)
      {
        Item.addDetail(item,grantedElement);
        return true;
      }
    }
    else if (ItemDetailsXMLConstants.ITEM_XP_TAG.equals(qualifiedName))
    {
      // Amount
      String amountStr=attributes.getValue(ItemDetailsXMLConstants.ITEM_XP_AMOUNT_ATTR);
      int amount=NumericTools.parseInt(amountStr,-1);
      ItemXP itemXP=new ItemXP(amount);
      Item.addDetail(item,itemXP);
      return true;
    }
    else if (ItemDetailsXMLConstants.VIRTUE_XP_TAG.equals(qualifiedName))
    {
      // Amount
      int amount=SAXParsingTools.getIntAttribute(attributes,ItemDetailsXMLConstants.VIRTUE_XP_AMOUNT_ATTR,0);
      // Bonus?
      boolean bonus=SAXParsingTools.getBooleanAttribute(attributes,ItemDetailsXMLConstants.VIRTUE_XP_BONUS_ATTR,false);
      VirtueXP virtueXP=new VirtueXP(amount,bonus);
      Item.addDetail(item,virtueXP);
      return true;
    }
    else if (ItemDetailsXMLConstants.REPUTATION_TAG.equals(qualifiedName))
    {
      handleReputation(item,attributes);
      return true;
    }
    else if (ItemDetailsXMLConstants.SLAYER_TAG.equals(qualifiedName))
    {
      handleSlayer(item,attributes);
      return true;
    }
    else if (ItemDetailsXMLConstants.COOLDOWN_TAG.equals(qualifiedName))
    {
      handleCooldown(item,attributes);
      return true;
    }
    else if (ItemDetailsXMLConstants.SKILL_TAG.equals(qualifiedName))
    {
      handleSkill(item,attributes);
      return true;
    }
    else if (ItemDetailsXMLConstants.ALLEGIANCE_POINTS_TAG.equals(qualifiedName))
    {
      handleAllegiancePoints(item,attributes);
      return true;
    }
    else if (ItemDetailsXMLConstants.HOUSING_HOOKS_TAG.equals(qualifiedName))
    {
      handleHousingHook(item,attributes);
      return true;
    }
    return false;
  }

  private void handleReputation(Item item, Attributes attributes)
  {
    // Faction ID
    String factionIDStr=attributes.getValue(ItemDetailsXMLConstants.REPUTATION_FACTION_ID_ATTR);
    int factionID=NumericTools.parseInt(factionIDStr,-1);
    Faction faction=FactionsRegistry.getInstance().getById(factionID);
    if (faction!=null)
    {
      // Amount
      String amountStr=attributes.getValue(ItemDetailsXMLConstants.REPUTATION_AMOUNT_ATTR);
      int amount=NumericTools.parseInt(amountStr,-1);
      ItemReputation reputation=new ItemReputation(faction,amount);
      Item.addDetail(item,reputation);
    }
    else
    {
      LOGGER.warn("Could not find faction with ID: {}",Integer.valueOf(factionID));
    }
  }

  private void handleSlayer(Item item, Attributes attributes)
  {
    // Slayer value
    String slayerValueStr=attributes.getValue(ItemDetailsXMLConstants.SLAYER_VALUE_ATTR);
    float slayerValue=NumericTools.parseFloat(slayerValueStr,-1);
    WeaponSlayerInfo info=new WeaponSlayerInfo(slayerValue);
    // Genus
    String genusStr=attributes.getValue(ItemDetailsXMLConstants.SLAYER_GENUS_ATTR);
    String[] genusCodeStrs=genusStr.split(",");
    LotroEnum<Genus> genusEnum=LotroEnumsRegistry.getInstance().get(Genus.class);
    for(String genusCodeStr : genusCodeStrs)
    {
      int genusCode=NumericTools.parseInt(genusCodeStr,-1);
      Genus genus=genusEnum.getEntry(genusCode);
      if (genus!=null)
      {
        info.addGenus(genus);
      }
    }
    Item.addDetail(item,info);
  }

  private void handleCooldown(Item item, Attributes attributes)
  {
    // Duration value
    String durationStr=attributes.getValue(ItemDetailsXMLConstants.COOLDOWN_DURATION_ATTR);
    float duration=NumericTools.parseFloat(durationStr,-1);
    // Channel ID
    String channelIDStr=attributes.getValue(ItemDetailsXMLConstants.COOLDOWN_CHANNEL_ID_ATTR);
    Integer channelID=null;
    if (channelIDStr!=null)
    {
      channelID=NumericTools.parseInteger(channelIDStr);
    }
    ItemUsageCooldown info=new ItemUsageCooldown(duration,channelID);
    Item.addDetail(item,info);
  }

  private void handleSkill(Item item, Attributes attributes)
  {
    // Skill ID
    String skillIDStr=attributes.getValue(ItemDetailsXMLConstants.SKILL_ID_ATTR);
    int skillID=NumericTools.parseInt(skillIDStr,0);
    SkillDescription skill=SkillsManager.getInstance().getSkill(skillID);
    // Level
    Integer level=null;
    String levelStr=attributes.getValue(ItemDetailsXMLConstants.SKILL_LEVEL_ATTR);
    if (levelStr!=null)
    {
      level=NumericTools.parseInteger(levelStr);
    }
    SkillToExecute info=new SkillToExecute(skill,level);
    Item.addDetail(item,info);
  }

  private void handleAllegiancePoints(Item item, Attributes attributes)
  {
    // Group
    int groupCode=SAXParsingTools.getIntAttribute(attributes,ItemDetailsXMLConstants.ALLEGIANCE_GROUP_ATTR,0);
    AllegianceGroup group=_allegianceGroupEnum.getEntry(groupCode);
    // Points
    int points=SAXParsingTools.getIntAttribute(attributes,ItemDetailsXMLConstants.ALLEGIANCE_POINTS_ATTR,0);
    AllegiancePoints allegiancePoints=new AllegiancePoints(group,points);
    Item.addDetail(item,allegiancePoints);
  }

  private void handleHousingHook(Item item, Attributes attributes)
  {
    HousingHooks info=new HousingHooks();
    // Categories
    String categoriesStr=attributes.getValue(ItemDetailsXMLConstants.HOUSING_HOOKS_CATEGORIES_ATTR);
    String[] categoryCodeStrs=categoriesStr.split(",");
    LotroEnum<HousingHookCategory> housingHookCategoryEnum=LotroEnumsRegistry.getInstance().get(HousingHookCategory.class);
    for(String categoryCodeStr : categoryCodeStrs)
    {
      int code=NumericTools.parseInt(categoryCodeStr,-1);
      HousingHookCategory category=housingHookCategoryEnum.getEntry(code);
      if (category!=null)
      {
        info.addCategory(category);
      }
    }
    Item.addDetail(item,info);
  }

  private GrantedElement<?> buildGrantedElement(int id, GrantType type)
  {
    switch(type)
    {
      case SHORT_MOUNT:
      case TALL_MOUNT:
      case SKILL:
      {
        SkillDescription skill=SkillsManager.getInstance().getSkill(id);
        if (skill!=null)
        {
          return new GrantedElement<SkillDescription>(type,skill);
        }
      }
      break;
      case TRAIT:
      {
        TraitDescription trait=TraitsManager.getInstance().getTrait(id);
        if (trait!=null)
        {
          return new GrantedElement<TraitDescription>(type,trait);
        }
      }
      break;
      case EMOTE:
      {
        EmoteDescription emote=EmotesManager.getInstance().getEmote(id);
        if (emote!=null)
        {
          return new GrantedElement<EmoteDescription>(type,emote);
        }
      }
      break;
      default:
    }
    return null;
  }
}
