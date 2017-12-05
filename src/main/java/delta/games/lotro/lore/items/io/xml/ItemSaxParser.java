package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLConstants;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemBinding;
import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.ItemSturdiness;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.io.xml.LegendaryAttrsXMLConstants;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * SAX parser for item files.
 * 
 * @author DAM
 */

public class ItemSaxParser extends DefaultHandler {

    private static final Logger LOGGER=Logger.getLogger(ItemSaxParser.class);

    private List<Item> _parsedItems;
    private Item _currentItem;

    private ItemSaxParser()
    {
      _parsedItems=new ArrayList<Item>();
    }

    /**
     * Parse the XML file.
     * @param source Source file.
     * @return List of parsed items.
     */
    public static List<Item> parseItemsFile(File source)
    {
      try
      {
        ItemSaxParser handler=new ItemSaxParser();
  
        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(source, handler);
        saxParser.reset();
        return handler._parsedItems;
      }
      catch(Exception e) {
        LOGGER.error("Error when loading items file " + source, e);
      }
      return null;
    }

    /**
     * Identify start of element.
     */

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes)
            throws SAXException {

        if ("item".equals(qualifiedName)) {
          // Category
          String categoryStr=attributes.getValue(ItemXMLConstants.ITEM_CATEGORY_ATTR);
          ItemCategory category=ItemCategory.valueOf(categoryStr);
          _currentItem=ItemFactory.buildItem(category);
          // Key
          String key=attributes.getValue(ItemXMLConstants.ITEM_ID_ATTR);
          _currentItem.setKey(key);
          // Identifier
          String idStr=attributes.getValue(ItemXMLConstants.ITEM_KEY_ATTR);
          int id=NumericTools.parseInt(idStr,-1);
          _currentItem.setIdentifier(id);
          // Set identifier
          String setId=attributes.getValue(ItemXMLConstants.ITEM_SET_ID_ATTR);
          _currentItem.setSetKey(setId);
          // Name
          String name=attributes.getValue(ItemXMLConstants.ITEM_NAME_ATTR);
          _currentItem.setName(name);
          // Item level
          String itemLevel=attributes.getValue(ItemXMLConstants.ITEM_LEVEL_ATTR);
          if (itemLevel!=null)
          {
            _currentItem.setItemLevel(Integer.valueOf(itemLevel));
          }
          // Slot
          EquipmentLocation slot=null;
          String slotStr=attributes.getValue(ItemXMLConstants.ITEM_SLOT_ATTR);
          if (slotStr!=null)
          {
            slot=EquipmentLocation.getByKey(slotStr);
          }
          _currentItem.setEquipmentLocation(slot);
          // Icon URL
          String iconURL=attributes.getValue(ItemXMLConstants.ITEM_ICON_URL_ATTR);
          _currentItem.setIconURL(iconURL);
          // Sub-category
          String subCategory=attributes.getValue(ItemXMLConstants.ITEM_SUBCATEGORY_ATTR);
          _currentItem.setSubCategory(subCategory);
          // Item binding
          ItemBinding binding=null;
          String bindingStr=attributes.getValue(ItemXMLConstants.ITEM_BINDING_ATTR);
          if (bindingStr!=null)
          {
            binding=ItemBinding.valueOf(bindingStr);
          }
          _currentItem.setBinding(binding);
          // Uniqueness
          String uniqueStr=attributes.getValue(ItemXMLConstants.ITEM_UNIQUE_ATTR);
          _currentItem.setUnique("true".equals(uniqueStr));
          // Durability
          String durabilityStr=attributes.getValue(ItemXMLConstants.ITEM_DURABILITY_ATTR);
          if (durabilityStr!=null)
          {
            _currentItem.setDurability(Integer.valueOf(durabilityStr));
          }
          // Sturdiness
          ItemSturdiness sturdiness=null;
          String sturdinessStr=attributes.getValue(ItemXMLConstants.ITEM_STURDINESS_ATTR);
          if (sturdinessStr!=null)
          {
            sturdiness=ItemSturdiness.valueOf(sturdinessStr);
          }
          _currentItem.setSturdiness(sturdiness);
          // Quality
          ItemQuality quality=null;
          String qualityStr=attributes.getValue(ItemXMLConstants.ITEM_QUALITY_ATTR);
          if (qualityStr!=null)
          {
            quality=ItemQuality.fromCode(qualityStr);
          }
          _currentItem.setQuality(quality);
          // Minimum level
          String minimumLevelStr=attributes.getValue(ItemXMLConstants.ITEM_MINLEVEL_ATTR);
          if (minimumLevelStr!=null)
          {
            _currentItem.setMinLevel(Integer.valueOf(minimumLevelStr));
          }
          // Required class
          CharacterClass cClass=null;
          String requiredClass=attributes.getValue(ItemXMLConstants.ITEM_REQUIRED_CLASS_ATTR);
          if (requiredClass!=null)
          {
            cClass=CharacterClass.getByKey(requiredClass);
          }
          _currentItem.setRequiredClass(cClass);
          // Full description
          String description=attributes.getValue(ItemXMLConstants.ITEM_DESCRIPTION_ATTR);
          _currentItem.setDescription(description);

          // Stack max
          String stackMaxStr=attributes.getValue(ItemXMLConstants.ITEM_STACK_MAX_ATTR);
          if (stackMaxStr!=null)
          {
            _currentItem.setStackMax(Integer.valueOf(stackMaxStr));
          }
          // Essence slots
          String nbEssenceSlotsStr=attributes.getValue(ItemXMLConstants.ITEM_ESSENCE_SLOTS);
          if (nbEssenceSlotsStr!=null)
          {
            _currentItem.setEssenceSlots(Integer.parseInt(nbEssenceSlotsStr));
          }
          // Essences
          /*
          Element essencesTag=DOMParsingTools.getChildTagByName(root,ItemXMLConstants.ESSENCES_TAG);
          if (essencesTag!=null)
          {
            List<Element> essenceTags=DOMParsingTools.getChildTagsByName(essencesTag,ItemXMLConstants.ITEM_TAG,false);
            List<Item> essences=new ArrayList<Item>();
            for(Element essenceTag : essenceTags)
            {
              Item essence=parseItem(essenceTag);
              essences.add(essence);
            }
            if (essences.size()>0)
            {
              int slots=Math.max(ret.getEssenceSlots(),essences.size());
              EssencesSet essencesSet=new EssencesSet(slots);
              int index=0;
              for(Item essence : essences)
              {
                essencesSet.setEssence(index,essence);
                index++;
              }
              ret.setEssences(essencesSet);
            }
          }
          */

          // Armour specific:
          if (category==ItemCategory.ARMOUR)
          {
            Armour armour=(Armour)_currentItem;
            String armourValueStr=attributes.getValue(ItemXMLConstants.ARMOUR_ATTR);
            if (armourValueStr!=null)
            {
              armour.setArmourValue(Integer.parseInt(armourValueStr));
            }
            String armourTypeStr=attributes.getValue(ItemXMLConstants.ARMOUR_TYPE_ATTR);
            if (armourTypeStr!=null)
            {
              ArmourType type=ArmourType.getArmourTypeByKey(armourTypeStr);
              armour.setArmourType(type);
            }
          }
          // Weapon specific:
          if ((category==ItemCategory.WEAPON) || (category==ItemCategory.LEGENDARY_WEAPON))
          {
            Weapon weapon=(Weapon)_currentItem;
            String dpsStr=attributes.getValue(ItemXMLConstants.DPS_ATTR);
            if (dpsStr!=null)
            {
              weapon.setDPS(Float.parseFloat(dpsStr));
            }
            String minDamageStr=attributes.getValue(ItemXMLConstants.MIN_DAMAGE_ATTR);
            if (minDamageStr!=null)
            {
              weapon.setMinDamage(Integer.parseInt(minDamageStr));
            }
            String maxDamage=attributes.getValue(ItemXMLConstants.MAX_DAMAGE_ATTR);
            weapon.setMaxDamage(Integer.parseInt(maxDamage));
            String damageTypeStr=attributes.getValue(ItemXMLConstants.DAMAGE_TYPE_ATTR);
            if (damageTypeStr!=null)
            {
              DamageType type=DamageType.getDamageTypeByKey(damageTypeStr);
              weapon.setDamageType(type);
            }
            String weaponTypeStr=attributes.getValue(ItemXMLConstants.WEAPON_TYPE_ATTR);
            if (weaponTypeStr!=null)
            {
              WeaponType type=WeaponType.getWeaponType(weaponTypeStr);
              weapon.setWeaponType(type);
            }
          }
        } else if (ItemXMLConstants.PROPERTY_TAG.equals(qualifiedName)) {
          String propertyName=attributes.getValue(ItemXMLConstants.PROPERTY_KEY_ATTR);
          String propertyValue=attributes.getValue(ItemXMLConstants.PROPERTY_VALUE_ATTR);
          _currentItem.setProperty(propertyName,propertyValue);
        } else if (BasicStatsSetXMLConstants.STAT_TAG.equals(qualifiedName)) {
          String statName=attributes.getValue(BasicStatsSetXMLConstants.STAT_NAME_ATTR);
          String statValue=attributes.getValue(BasicStatsSetXMLConstants.STAT_VALUE_ATTR);
          STAT stat=STAT.getByName(statName);
          FixedDecimalsInteger value=FixedDecimalsInteger.fromString(statValue);
          _currentItem.getStats().setStat(stat,value);
        } else if (MoneyXMLConstants.MONEY_TAG.equals(qualifiedName)) {
          // Item value
          Money money=_currentItem.getValue();
          String goldStr=attributes.getValue(MoneyXMLConstants.MONEY_GOLD_ATTR);
          money.setGoldCoins(NumericTools.parseInt(goldStr,0));
          String silverStr=attributes.getValue(MoneyXMLConstants.MONEY_SILVER_ATTR);
          money.setSilverCoins(NumericTools.parseInt(silverStr,0));
          String copperStr=attributes.getValue(MoneyXMLConstants.MONEY_COPPER_ATTR);
          money.setCopperCoins(NumericTools.parseInt(copperStr,0));
        } else if (ItemXMLConstants.BONUS_TAG.equals(qualifiedName)) {
          // Bonus
          String bonus=attributes.getValue(ItemXMLConstants.BONUS_VALUE_ATTR);
          if (bonus!=null)
          {
            _currentItem.addBonus(bonus);
          }
        } else if (LegendaryAttrsXMLConstants.RELIC_TAG.equals(qualifiedName)) {
          // Relics
          String typeStr=attributes.getValue(LegendaryAttrsXMLConstants.RELIC_TYPE_ATTR);
          if (typeStr!=null)
          {
            RelicType type=RelicType.valueOf(typeStr);
            Relic relic=null;
            String name=attributes.getValue(LegendaryAttrsXMLConstants.RELIC_NAME_ATTR);
            if (name!=null)
            {
              RelicsManager relicsMgr=RelicsManager.getInstance();
              relic=relicsMgr.getByName(name);
            }
            if (_currentItem instanceof Legendary)
            {
              LegendaryAttrs legendaryAttrs=((Legendary)_currentItem).getLegendaryAttrs();
              if (type==RelicType.SETTING) legendaryAttrs.setSetting(relic);
              if (type==RelicType.RUNE) legendaryAttrs.setRune(relic);
              if (type==RelicType.GEM) legendaryAttrs.setGem(relic);
              if (type==RelicType.CRAFTED_RELIC) legendaryAttrs.setCraftedRelic(relic);
            }
          }
        } else {
          // ...
        }
    }

    /**
     * Identify end of element.
     */

    @Override
    public void endElement(String uri, String localName, String qualifiedName) {

        if ("item".equals(qualifiedName)) {
          _parsedItems.add(_currentItem);
          _currentItem=null;
        }
    }
}
