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
import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.common.enums.EquipmentCategory;
import delta.games.lotro.common.enums.ItemClass;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.money.MoneyTables;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.common.stats.ConstantStatProvider;
import delta.games.lotro.common.stats.RangedStatProvider;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.SpecialEffect;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLConstants;
import delta.games.lotro.common.utils.valueTables.QualityBasedValuesTable;
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
import delta.games.lotro.lore.items.ItemUtils;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.carryalls.CarryAll;
import delta.games.lotro.lore.items.details.io.xml.ItemDetailsSaxParser;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary2.Legendary2;
import delta.games.lotro.lore.items.legendary2.LegendaryAttributes2Manager;
import delta.games.lotro.lore.items.legendary2.LegendaryAttrs2;
import delta.games.lotro.lore.items.scaling.Munging;
import delta.games.lotro.lore.items.weapons.DPSTables;
import delta.games.lotro.lore.items.weapons.WeaponSpeedEntry;
import delta.games.lotro.lore.items.weapons.WeaponSpeedsManager;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;
import delta.games.lotro.utils.maths.Progression;

/**
 * SAX parser for item files.
 * @author DAM
 */
public final class ItemSaxParser extends DefaultHandler
{
  private static final Logger LOGGER=Logger.getLogger(ItemSaxParser.class);

  private List<Item> _parsedItems;
  private Item _currentItem;
  private LotroEnum<ItemClass> _itemClassEnum;
  private LotroEnum<EquipmentCategory> _equipmentCategoryEnum;
  private LotroEnum<ItemBinding> _itemBindingEnum;
  private ItemDetailsSaxParser _detailsParser;
  private SingleLocaleLabelsManager _i18n;

  private ItemSaxParser()
  {
    _parsedItems=new ArrayList<Item>();
    _itemClassEnum=LotroEnumsRegistry.getInstance().get(ItemClass.class);
    _equipmentCategoryEnum=LotroEnumsRegistry.getInstance().get(EquipmentCategory.class);
    _itemBindingEnum=LotroEnumsRegistry.getInstance().get(ItemBinding.class);
    _detailsParser=new ItemDetailsSaxParser();
    _i18n=I18nFacade.getLabelsMgr("items");
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
      SAXParserFactory factory=SAXParserFactory.newInstance();
      SAXParser saxParser=factory.newSAXParser();
      saxParser.parse(source,handler);
      saxParser.reset();
      return handler._parsedItems;
    }
    catch (Exception e)
    {
      LOGGER.error("Error when loading items file "+source,e);
    }
    return null;
  }

  @Override
  public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
  {
    if (ItemXMLConstants.ITEM_TAG.equals(qualifiedName))
    {
      // Category
      String categoryStr=attributes.getValue(ItemXMLConstants.ITEM_CATEGORY_ATTR);
      ItemCategory category=ItemCategory.valueOf(categoryStr);
      _currentItem=ItemFactory.buildItem(category);
      // Identifier
      String idStr=attributes.getValue(ItemXMLConstants.ITEM_KEY_ATTR);
      int id=NumericTools.parseInt(idStr,-1);
      _currentItem.setIdentifier(id);
      // Icon
      String icon=attributes.getValue(ItemXMLConstants.ITEM_ICON_ATTR);
      _currentItem.setIcon(icon);
      // Set identifier
      String setId=attributes.getValue(ItemXMLConstants.ITEM_SET_ID_ATTR);
      _currentItem.setSetKey(setId);
      // Name
      String name=_i18n.getLabel(String.valueOf(id));
      if (name==null)
      {
        name=attributes.getValue(ItemXMLConstants.ITEM_NAME_ATTR);
      }
      _currentItem.setName(name);
      // Item level
      String itemLevel=attributes.getValue(ItemXMLConstants.ITEM_LEVEL_ATTR);
      if (itemLevel!=null)
      {
        _currentItem.setItemLevel(Integer.valueOf(itemLevel));
      }
      // Item level offset
      String itemLevelOffset=attributes.getValue(ItemXMLConstants.ITEM_LEVEL_OFFSET_ATTR);
      if (itemLevelOffset!=null)
      {
        _currentItem.setItemLevelOffset(Integer.valueOf(itemLevelOffset));
      }
      // Slot
      EquipmentLocation slot=null;
      String slotStr=attributes.getValue(ItemXMLConstants.ITEM_SLOT_ATTR);
      if (slotStr!=null)
      {
        slot=EquipmentLocation.getByKey(slotStr);
      }
      _currentItem.setEquipmentLocation(slot);
      // Item class
      String itemClassCodeStr=attributes.getValue(ItemXMLConstants.ITEM_CLASS_ATTR);
      if (itemClassCodeStr!=null)
      {
        int itemClassCode=NumericTools.parseInt(itemClassCodeStr,0);
        ItemClass itemClass=_itemClassEnum.getEntry(itemClassCode);
        _currentItem.setItemClass(itemClass);
      }
      // Equipment category
      String equipmentCategoryCodeStr=attributes.getValue(ItemXMLConstants.ITEM_EQUIPMENT_CATEGORY_ATTR);
      if (equipmentCategoryCodeStr!=null)
      {
        int equipmentCategoryCode=NumericTools.parseInt(equipmentCategoryCodeStr,0);
        EquipmentCategory equipmentCategory=_equipmentCategoryEnum.getEntry(equipmentCategoryCode);
        _currentItem.setEquipmentCategory(equipmentCategory);
      }
      // Item binding
      ItemBinding binding=null;
      String bindingStr=attributes.getValue(ItemXMLConstants.ITEM_BINDING_ATTR);
      if (bindingStr!=null)
      {
        binding=_itemBindingEnum.getByKey(bindingStr);
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
        sturdiness=ItemSturdiness.getItemSturdinessByKey(sturdinessStr);
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
      // Requirements
      UsageRequirementsXMLParser.parseRequirements(_currentItem.getUsageRequirements(),attributes);
      // Full description
      String description=attributes.getValue(ItemXMLConstants.ITEM_DESCRIPTION_ATTR);
      description=I18nRuntimeUtils.getLabel(_i18n,description);
      _currentItem.setDescription(description);
      // Value table
      String valueTableIdStr=attributes.getValue(ItemXMLConstants.ITEM_VALUE_TABLE_ID_ATTR);
      if (valueTableIdStr!=null)
      {
        int valueTableId=NumericTools.parseInt(valueTableIdStr,0);
        QualityBasedValuesTable table=MoneyTables.getMoneyTablesManager().getValueTable(valueTableId);
        _currentItem.setValueTable(table);
      }
      // Stack max
      String stackMaxStr=attributes.getValue(ItemXMLConstants.ITEM_STACK_MAX_ATTR);
      if (stackMaxStr!=null)
      {
        _currentItem.setStackMax(Integer.valueOf(stackMaxStr));
      }
      // Essence slots
      String nbEssenceSlotsStr=attributes.getValue(ItemXMLConstants.ITEM_ESSENCE_SLOTS_ATTR);
      if (nbEssenceSlotsStr!=null)
      {
        _currentItem.setEssenceSlots(Integer.parseInt(nbEssenceSlotsStr));
      }
      // Munging
      String mungingStr=attributes.getValue(ItemXMLConstants.ITEM_SCALING_ATTR);
      if (mungingStr!=null)
      {
        Munging munging=Munging.fromString(mungingStr);
        _currentItem.setMunging(munging);
      }
      // Armour specific:
      if (_currentItem instanceof Armour)
      {
        Armour armour=(Armour)_currentItem;
        String armourTypeStr=attributes.getValue(ItemXMLConstants.ARMOUR_TYPE_ATTR);
        if (armourTypeStr!=null)
        {
          ArmourType type=ArmourType.getArmourTypeByKey(armourTypeStr);
          armour.setArmourType(type);
        }
        // Armour value is loaded as a regular stat
      }
      // Weapon specific:
      if (_currentItem instanceof Weapon)
      {
        Weapon weapon=(Weapon)_currentItem;
        // DPS
        String dpsStr=attributes.getValue(ItemXMLConstants.DPS_ATTR);
        if (dpsStr!=null)
        {
          weapon.setDPS(Float.parseFloat(dpsStr));
        }
        // DPS table
        String dpsTableIdStr=attributes.getValue(ItemXMLConstants.DPS_TABLE_ID_ATTR);
        if (dpsTableIdStr!=null)
        {
          int dpsTableId=NumericTools.parseInt(dpsTableIdStr,0);
          QualityBasedValuesTable table=DPSTables.getDPSTablesManager().getValueTable(dpsTableId);
          weapon.setDPSTable(table);
        }
        // Damage min/max
        String minDamageStr=attributes.getValue(ItemXMLConstants.MIN_DAMAGE_ATTR);
        if (minDamageStr!=null)
        {
          weapon.setMinDamage(Integer.parseInt(minDamageStr));
        }
        String maxDamage=attributes.getValue(ItemXMLConstants.MAX_DAMAGE_ATTR);
        weapon.setMaxDamage(Integer.parseInt(maxDamage));
        // Damage type
        String damageTypeStr=attributes.getValue(ItemXMLConstants.DAMAGE_TYPE_ATTR);
        if (damageTypeStr!=null)
        {
          DamageType type=DamageType.getDamageTypeByKey(damageTypeStr);
          weapon.setDamageType(type);
        }
        // Weapon type
        String weaponTypeStr=attributes.getValue(ItemXMLConstants.WEAPON_TYPE_ATTR);
        if (weaponTypeStr!=null)
        {
          WeaponType type=WeaponType.getWeaponTypeByKey(weaponTypeStr);
          weapon.setWeaponType(type);
        }
        // Weapon speed
        String speedCodeStr=attributes.getValue(ItemXMLConstants.WEAPON_SPEED_ATTR);
        if (speedCodeStr!=null)
        {
          int speedCode=Integer.parseInt(speedCodeStr);
          WeaponSpeedEntry entry=WeaponSpeedsManager.getWeaponSpeedsManager().getSpeedEntry(weapon.getWeaponType(),speedCode);
          weapon.setSpeed(entry);
        }
      }
      // Legendary specifics
      if (_currentItem instanceof Legendary)
      {
        Legendary legendary=(Legendary)_currentItem;
        LegendaryAttrs attrs=legendary.getLegendaryAttrs();
        // - Main legacy ID
        String mainLegacyIdStr=attributes.getValue(ItemXMLConstants.MAIN_LEGACY_ID_ATTR);
        int mainLegacyId=NumericTools.parseInt(mainLegacyIdStr,0);
        attrs.setMainLegacyId(mainLegacyId);
        // - Main legacy base rank
        String mainLegacyBaseRankStr=attributes.getValue(ItemXMLConstants.MAIN_LEGACY_BASE_RANK_ATTR);
        int mainLegacyBaseRank=NumericTools.parseInt(mainLegacyBaseRankStr,0);
        attrs.setMainLegacyBaseRank(mainLegacyBaseRank);
      }
      if (_currentItem instanceof Legendary2)
      {
        Legendary2 legendary=(Legendary2)_currentItem;
        LegendaryAttributes2Manager mgr=LegendaryAttributes2Manager.getInstance();
        LegendaryAttrs2 attrs=mgr.getLegendaryAttributes(id);
        legendary.getLegendaryAttrs().setSockets(attrs.getSockets());
      }
      if (_currentItem instanceof CarryAll)
      {
        CarryAll carryAll=(CarryAll)_currentItem;
        // Max items
        String maxItemsStr=attributes.getValue(ItemXMLConstants.CARRY_ALL_MAX_ITEMS_ATTR);
        int maxItems=NumericTools.parseInt(maxItemsStr,0);
        carryAll.setMaxItems(maxItems);
        // Item stack max
        String itemStackMaxStr=attributes.getValue(ItemXMLConstants.CARRY_ALL_ITEM_STACK_MAX_ATTR);
        int itemStackMax=NumericTools.parseInt(itemStackMaxStr,0);
        carryAll.setItemStackMax(itemStackMax);
      }
    }
    else if (BasicStatsSetXMLConstants.STAT_TAG.equals(qualifiedName))
    {
      // Stat name
      String statName=attributes.getValue(BasicStatsSetXMLConstants.STAT_NAME_ATTR);
      StatDescription stat=StatsRegistry.getInstance().getByKey(statName);
      // Stat provider
      StatProvider statProvider=parseStatProvider(stat,attributes);
      if (statProvider!=null)
      {
        // Stat operator
        StatOperator operator=getOperator(attributes.getValue(BasicStatsSetXMLConstants.STAT_OPERATOR_ATTR));
        statProvider.setOperator(operator);
        // Description override
        String descriptionOverride=attributes.getValue(BasicStatsSetXMLConstants.STAT_DESCRIPTION_OVERRIDE_ATTR);
        descriptionOverride=I18nRuntimeUtils.getLabel(_i18n,descriptionOverride);
        statProvider.setDescriptionOverride(descriptionOverride);
        StatsProvider statsProvider=_currentItem.getStatsProvider();
        if (statsProvider==null)
        {
          statsProvider=new StatsProvider();
          _currentItem.setStatsProvider(statsProvider);
        }
        statsProvider.addStatProvider(statProvider);
      }
    }
    else if (StatsProviderXMLConstants.SPECIAL_EFFECT_TAG.equals(qualifiedName))
    {
      String label=attributes.getValue(StatsProviderXMLConstants.SPECIAL_EFFECT_LABEL_ATTR);
      label=I18nRuntimeUtils.getLabel(_i18n,label);
      SpecialEffect specialEffect=new SpecialEffect(label);
      StatsProvider statsProvider=_currentItem.getStatsProvider();
      if (statsProvider==null)
      {
        statsProvider=new StatsProvider();
        _currentItem.setStatsProvider(statsProvider);
      }
      statsProvider.addSpecialEffect(specialEffect);
    }
    else
    {
      _detailsParser.startElement(_currentItem,qualifiedName,attributes);
    }
  }

  private StatOperator getOperator(String operatorStr)
  {
    StatOperator operator=StatOperator.getByName(operatorStr);
    if (operator==null)
    {
      operator=StatOperator.ADD;
    }
    return operator;
  }

  private StatProvider parseStatProvider(StatDescription stat, Attributes attributes)
  {
    String constantStr=attributes.getValue(StatsProviderXMLConstants.STAT_CONSTANT_ATTR);
    if (constantStr!=null)
    {
      float value=NumericTools.parseFloat(constantStr,0);
      ConstantStatProvider constantStatProvider=new ConstantStatProvider(stat,value);
      return constantStatProvider;
    }
    // Scalable stat provider?
    String progressionStr=attributes.getValue(StatsProviderXMLConstants.STAT_SCALING_ATTR);
    if (progressionStr!=null)
    {
      int progressionId=NumericTools.parseInt(progressionStr,-1);
      Progression progression=ProgressionsManager.getInstance().getProgression(progressionId);
      if (progression==null)
      {
        LOGGER.warn("Progression not found: "+progressionId);
      }
      return new ScalableStatProvider(stat,progression);
    }
    // Ranged stat provider?
    String rangedStr=attributes.getValue(StatsProviderXMLConstants.STAT_RANGED_ATTR);
    if (rangedStr!=null)
    {
      return parseRangedStatProvider(stat,rangedStr);
    }
    return null;
  }

  private RangedStatProvider parseRangedStatProvider(StatDescription stat, String rangedStr)
  {
    RangedStatProvider provider=new RangedStatProvider(stat);
    ProgressionsManager progressionsMgr=ProgressionsManager.getInstance();
    String[] rangeStrs=rangedStr.split(",");
    for(String rangeStr:rangeStrs)
    {
      int separator=rangeStr.indexOf(':');
      // Levels
      String levels=rangeStr.substring(0,separator);
      int separatorLevels=levels.indexOf('-');
      String minLevelStr=levels.substring(0,separatorLevels);
      Integer minLevel=NumericTools.parseInteger(minLevelStr);
      String maxLevelStr=levels.substring(separatorLevels+1);
      Integer maxLevel=NumericTools.parseInteger(maxLevelStr);
      // Progression
      String progressionStr=rangeStr.substring(separator+1);
      int progressionId=NumericTools.parseInt(progressionStr,0);
      Progression progression=progressionsMgr.getProgression(progressionId);
      if ((minLevel!=null)||(maxLevel!=null))
      {
        if (progression!=null)
        {
          ScalableStatProvider scalableProvider=new ScalableStatProvider(stat,progression);
          provider.addRange(minLevel,maxLevel,scalableProvider);
        }
        else
        {
          LOGGER.warn("Progression not found: "+progressionId);
        }
      }
    }
    return provider;
  }

  /**
   * Handle end of element.
   */
  @Override
  public void endElement(String uri, String localName, String qualifiedName)
  {
    if ("item".equals(qualifiedName))
    {
      ItemUtils.injectGenericEffects(_currentItem);
      _currentItem.setStatsFromStatsProvider();
      _parsedItems.add(_currentItem);
      _currentItem=null;
    }
  }
}
