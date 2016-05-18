package delta.games.lotro.character.stats;

import delta.games.lotro.character.Character;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Money;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.ItemSturdiness;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.LegendaryItem;
import delta.games.lotro.lore.items.legendary.LegendaryTitle;
import delta.games.lotro.lore.items.legendary.LegendaryWeapon;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Character generator (Meva) for tests.
 * @author DAM
 */
public class CharacterGeneratorMeva
{
  private CharacterGenerationTools _tools;

  /**
   * Constructor.
   * @param tools Tools.
   */
  public CharacterGeneratorMeva(CharacterGenerationTools tools)
  {
    _tools=tools;
  }

  /**
   * Build a test character.
   * @return a character.
   */
  public Character buildCharacter()
  {
    Character c=new Character();
    c.setName("Meva");
    c.setRace(Race.HOBBIT);
    c.setLevel(100);
    c.setCharacterClass(CharacterClass.MINSTREL);
    // Virtues
    VirtuesSet virtues=c.getVirtues();
    virtues.setVirtueValue(VirtueId.CHARITY,19);
    virtues.setVirtueValue(VirtueId.COMPASSION,17);
    virtues.setVirtueValue(VirtueId.CONFIDENCE,18);
    virtues.setVirtueValue(VirtueId.DETERMINATION,12);
    virtues.setVirtueValue(VirtueId.DISCIPLINE,11);
    virtues.setVirtueValue(VirtueId.EMPATHY,17);
    virtues.setVirtueValue(VirtueId.FIDELITY,16);
    virtues.setVirtueValue(VirtueId.FORTITUDE,12);
    virtues.setVirtueValue(VirtueId.HONESTY,19);
    virtues.setVirtueValue(VirtueId.HONOUR,15);
    virtues.setVirtueValue(VirtueId.IDEALISM,19);
    virtues.setVirtueValue(VirtueId.JUSTICE,11);
    virtues.setVirtueValue(VirtueId.LOYALTY,19);
    virtues.setVirtueValue(VirtueId.MERCY,11);
    virtues.setVirtueValue(VirtueId.PATIENCE,16);
    virtues.setVirtueValue(VirtueId.INNOCENCE,19);
    virtues.setVirtueValue(VirtueId.TOLERANCE,14);
    virtues.setVirtueValue(VirtueId.VALOUR,14);
    virtues.setVirtueValue(VirtueId.WISDOM,16);
    virtues.setVirtueValue(VirtueId.ZEAL,18);
    virtues.setSelectedVirtue(VirtueId.CHARITY,0);
    virtues.setSelectedVirtue(VirtueId.LOYALTY,1);
    virtues.setSelectedVirtue(VirtueId.IDEALISM,2);
    virtues.setSelectedVirtue(VirtueId.INNOCENCE,3);
    virtues.setSelectedVirtue(VirtueId.HONOUR,4);
    // Tomes
    //TomesSet tomes=c.getTomes();
    //tomes.setTomeRank(STAT.MIGHT,1);
    //tomes.setTomeRank(STAT.FATE,2);
    // TODO Racial traits
    CharacterEquipment equipment=c.getEquipment();
    SlotContents head=equipment.getSlotContents(EQUIMENT_SLOT.HEAD,true);
    head.setItem(buildHelm());
    SlotContents shoulders=equipment.getSlotContents(EQUIMENT_SLOT.SHOULDER,true);
    shoulders.setItem(buildShoulders());
    SlotContents boots=equipment.getSlotContents(EQUIMENT_SLOT.FEET,true);
    boots.setItem(buildBoots());
    SlotContents pocket=equipment.getSlotContents(EQUIMENT_SLOT.POCKET,true);
    pocket.setItem(buildPocket());
    SlotContents cloak=equipment.getSlotContents(EQUIMENT_SLOT.BACK,true);
    cloak.setItem(buildCloak());
    SlotContents chest=equipment.getSlotContents(EQUIMENT_SLOT.BREAST,true);
    chest.setItem(buildChest());
    SlotContents gloves=equipment.getSlotContents(EQUIMENT_SLOT.HANDS,true);
    gloves.setItem(buildGloves());
    SlotContents leggings=equipment.getSlotContents(EQUIMENT_SLOT.LEGS,true);
    leggings.setItem(buildLeggings());
    // Weapons
    SlotContents club=equipment.getSlotContents(EQUIMENT_SLOT.MAIN_MELEE,true);
    club.setItem(buildWeapon());
    SlotContents shield=equipment.getSlotContents(EQUIMENT_SLOT.OTHER_MELEE,true);
    shield.setItem(buildShield());
    SlotContents bow=equipment.getSlotContents(EQUIMENT_SLOT.RANGED,true);
    bow.setItem(buildRanged());
    // Tools
    SlotContents tool=equipment.getSlotContents(EQUIMENT_SLOT.TOOL,true);
    tool.setItem(buildCraftingTool());
    // Class slot
    SlotContents rune=equipment.getSlotContents(EQUIMENT_SLOT.CLASS_ITEM,true);
    rune.setItem(buildBook());
    // Jewels
    SlotContents earring1=equipment.getSlotContents(EQUIMENT_SLOT.LEFT_EAR,true);
    earring1.setItem(buildEarring1());
    SlotContents earring2=equipment.getSlotContents(EQUIMENT_SLOT.RIGHT_EAR,true);
    earring2.setItem(buildEarring2());
    SlotContents bracelet1=equipment.getSlotContents(EQUIMENT_SLOT.LEFT_WRIST,true);
    bracelet1.setItem(buildBracelet1());
    SlotContents bracelet2=equipment.getSlotContents(EQUIMENT_SLOT.RIGHT_WRIST,true);
    bracelet2.setItem(buildBracelet2());
    SlotContents necklace=equipment.getSlotContents(EQUIMENT_SLOT.NECK,true);
    necklace.setItem(buildNecklace());
    SlotContents ring1=equipment.getSlotContents(EQUIMENT_SLOT.LEFT_FINGER,true);
    ring1.setItem(buildRing1());
    SlotContents ring2=equipment.getSlotContents(EQUIMENT_SLOT.RIGHT_FINGER,true);
    ring2.setItem(buildRing2());

    // Additional stats
    BasicStatsSet additionalStats=c.getAdditionalStats();
    // Buff
    additionalStats.addStat(STAT.MIGHT, new FixedDecimalsInteger(20));
    additionalStats.addStat(STAT.HOPE, new FixedDecimalsInteger(1));
    // Red trait tree:
    {
      // Enduring Morale, Rank 2
      additionalStats.addStat(STAT.MORALE, new FixedDecimalsInteger(537));
      // Finesse, Rank 5
      additionalStats.addStat(STAT.FINESSE, new FixedDecimalsInteger(2486));
    }
    // TODO Weird... looks like the mini has a +1 Hope buff
    return c;
  }

  private Item buildHelm()
  {
    // Light Nadhin Hood (level 192)
    Item ret=_tools.getItemById(1879313783);
    EssencesSet essences=new EssencesSet(4);
    Item will=_tools.getEssenceByName(7,"Supreme Essence of Will");
    //will.getStats().setStat(STAT.WILL,139);
    //will.getStats().setStat(STAT.VITALITY,17);
    essences.setEssence(0,will);
    essences.setEssence(1,will);
    Item vitalityS=_tools.getEssenceByName(7,"Supreme Essence of Vitality");
    //vitalityS.getStats().setStat(STAT.VITALITY,139);
    //vitalityS.getStats().setStat(STAT.FATE,17);
    essences.setEssence(2,vitalityS);
    Item vitalityG=_tools.getEssenceByName(7,"Greater Essence of Vitality");
    //vitalityG.getStats().setStat(STAT.VITALITY,136);
    essences.setEssence(3,vitalityG);
    ret.setEssences(essences);
    return ret;
  }

  private Item buildShoulders()
  {
    // Light Nadhin Shoulders
    Item ret=_tools.getItemById(1879313778);
    EssencesSet essences=new EssencesSet(4);
    Item will=_tools.getEssenceByName(7,"Greater Essence of Will");
    //will.getStats().setStat(STAT.WILL,136);
    essences.setEssence(0,will);
    essences.setEssence(1,will);
    Item critRating=_tools.getEssenceByName(7,"Major Essence of Critical Rating");
    //critRating.getStats().setStat(STAT.CRITICAL_RATING,1075);
    essences.setEssence(2,critRating);
    Item tacticalMastery=_tools.getEssenceByName(7,"Major Essence of Tactical Mastery");
    //tacticalMastery.getStats().setStat(STAT.TACTICAL_MASTERY,1075);
    essences.setEssence(3,tacticalMastery);
    ret.setEssences(essences);
    return ret;
  }

  private Item buildBoots()
  {
    // Boots of the Storied Mariner
    return _tools.getItemById(1879313346);
  }

  private Item buildPocket()
  {
    // Second Exquisite Minstrel's Token of Helm Hammerhand
    return _tools.getItemById(1879311026);
  }

  private Item buildCloak()
  {
    // Greater Resolute Cloak of Tactics
    return _tools.getItemById(1879286095);
  }

  private Item buildChest()
  {
    // Jacket of the Storied Mariner
    return _tools.getItemById(1879313353);
  }

  private Item buildGloves()
  {
    // Gloves of the Great Shore
    return _tools.getItemById(1879310211);
  }

  private Item buildLeggings()
  {
    // Greater Resolute Leggings of Penetration
    return _tools.getItemById(1879286081);
  }

  private Item buildShield()
  {
    // Blackroot Vibrant Shield
    return _tools.getItemById(1879313707);
  }

  private Item buildRanged()
  {
    // Exquisite Walnut Theorbo of the Battle-singer
    return _tools.getItemById(1879283354);
  }

  private Item buildEarring1()
  {
    // Third Exquisite Minstrel's Earring of Helm Hammerhand
    return _tools.getItemById(1879311185);
  }

  private Item buildEarring2()
  {
    // First Exquisite Minstrel's Earring of Helm Hammerhand
    return _tools.getItemById(1879311050);
  }

  private Item buildNecklace()
  {
    // First Exquisite Minstrel's Necklace of Helm Hammerhand
    return _tools.getItemById(1879311004);
  }

  private Item buildBracelet1()
  {
    // Second Exquisite Minstrel's Bracelet of Helm Hammerhand
    return _tools.getItemById(1879311011);
  }

  private Item buildBracelet2()
  {
    // Adept Officer's Persevering Bracelet
    // Level 100
    Item ret=_tools.getItemById(1879310845);
    return ret;
  }

  private Item buildRing1()
  {
    // Second Exquisite Minstrel's Ring of Helm Hammerhand
    return _tools.getItemById(1879311024);
  }

  private Item buildRing2()
  {
    // 1879318796" name="Advisor's Fateful Ring
    Item ret=_tools.getItemById(1879318796);
    EssencesSet essences=new EssencesSet(1);
    Item tacticalMastery=_tools.getEssenceByName(6,"Supreme Essence of Tactical Mastery");
    tacticalMastery.getStats().setStat(STAT.TACTICAL_MASTERY,1165);
    tacticalMastery.getStats().setStat(STAT.POWER,88);
    essences.setEssence(0,tacticalMastery);
    ret.setEssences(essences);
    return ret;
  }

  private Item buildWeapon()
  {
    LegendaryWeapon weapon=new LegendaryWeapon();
    weapon.setName("Reshaped Minstrel's Club of the First Age");
    weapon.setBirthName("Cavern Club");
    weapon.setCrafterName("Glumlug");
    weapon.setRequiredClass(CharacterClass.MINSTREL);
    weapon.setCategory(ItemCategory.WEAPON);
    weapon.setMinLevel(Integer.valueOf(100));
    weapon.setDurability(Integer.valueOf(100));
    weapon.setSturdiness(ItemSturdiness.NORMAL);
    weapon.setEquipmentLocation(EquipmentLocation.HAND);
    weapon.setQuality(ItemQuality.LEGENDARY);
    // Weapon specifics
    weapon.setWeaponType(WeaponType.ONE_HANDED_CLUB);
    weapon.setDPS(242.1f);
    weapon.setMinDamage(345);
    weapon.setMaxDamage(575);
    weapon.setDamageType(DamageType.BELERIAND);
    // Passives
    //BasicStatsSet passives=weapon.getPassives();
    // -40% attack speed
    //passives.setStat(STAT.PARRY_PERCENTAGE,1);
    LegendaryAttrs attrs=weapon.getLegendaryAttrs();
    // Title
    LegendaryTitle title=new LegendaryTitle("Elven Orc-Hewer");
    //BasicStatsSet titleStats=title.getStats();
    //titleStats.setStat(STAT.CRITICAL_RATING,460);
    attrs.setTitle(title);
    // Relics
    {
      // Setting
      Relic setting=new Relic("True Setting of the North", RelicType.SETTING, Integer.valueOf(80));
      BasicStatsSet stats=setting.getStats();
      stats.setStat(STAT.MORALE, 330);
      stats.setStat(STAT.CRITICAL_RATING,1293);
      stats.setStat(STAT.INCOMING_HEALING,1616);
      attrs.setSetting(setting);
    }
    {
      // Gem
      Relic gem=new Relic("True Gem of the Wizard's Vale", RelicType.GEM, Integer.valueOf(75));
      BasicStatsSet stats=gem.getStats();
      stats.setStat(STAT.ICPR,90);
      stats.setStat(STAT.CRITICAL_RATING,1212);
      stats.setStat(STAT.FATE,30);
      attrs.setGem(gem);
    }
    {
      // Rune
      Relic rune=new Relic("Great River Rune of Power", RelicType.RUNE, Integer.valueOf(75));
      BasicStatsSet stats=rune.getStats();
      stats.setStat(STAT.PHYSICAL_MITIGATION,170);
      stats.setStat(STAT.PHYSICAL_MASTERY,606);
      stats.setStat(STAT.TACTICAL_MASTERY,606);
      stats.setStat(STAT.FATE,27);
      attrs.setRune(rune);
    }
    {
      // Crafted relic
      Relic craftedRelic=new Relic("Westemnet Device of Tactics", RelicType.CRAFTED_RELIC, Integer.valueOf(95));
      BasicStatsSet stats=craftedRelic.getStats();
      stats.setStat(STAT.WILL,40);
      stats.setStat(STAT.CRITICAL_RATING,740);
      stats.setStat(STAT.TACTICAL_MASTERY,740);
      attrs.setCraftedRelic(craftedRelic);
    }
    // Stat legacies
    // TODO
    return weapon;
  }

  private LegendaryItem buildBook()
  {
    LegendaryItem classItem=new LegendaryItem();
    classItem.setName("Reforged Minstrel's Songbook of the Second Age");
    classItem.setBirthName("Small blue book");
    classItem.setCrafterName("Utharr");
    classItem.setRequiredClass(CharacterClass.MINSTREL);
    classItem.setCategory(ItemCategory.ITEM);
    classItem.setMinLevel(Integer.valueOf(95));
    classItem.setDurability(Integer.valueOf(80));
    classItem.setSturdiness(ItemSturdiness.NORMAL);
    classItem.setEquipmentLocation(EquipmentLocation.CLASS_SLOT);
    classItem.setQuality(ItemQuality.LEGENDARY);

    // Passives
    //BasicStatsSet passives=classItem.getPassives();
    //passives.setStat(STAT.BLADE_LINE_AOE_POWER_COST_PERCENTAGE,-1);
    //passives.setStat(STAT.STRIKE_SKILLS_POWER_COST_PERCENTAGE,-4);
    //passives.setStat(STAT.INCOMING_HEALING,6300);
    // -1% perceived threat
    // +95.24 Tactical Healing Rating
    LegendaryAttrs attrs=classItem.getLegendaryAttrs();
    // Title
    // 5% light-type damage
    // Relics
    {
      // Setting
      Relic setting=new Relic("True Setting of the North", RelicType.SETTING, Integer.valueOf(80));
      BasicStatsSet stats=setting.getStats();
      stats.setStat(STAT.MORALE, 330);
      stats.setStat(STAT.CRITICAL_RATING,1293);
      stats.setStat(STAT.INCOMING_HEALING,1616);
      attrs.setSetting(setting);
    }
    {
      // Gem
      Relic gem=new Relic("True Gem of the Wizard's Vale", RelicType.GEM, Integer.valueOf(75));
      BasicStatsSet stats=gem.getStats();
      stats.setStat(STAT.ICPR,90);
      stats.setStat(STAT.CRITICAL_RATING,1212);
      stats.setStat(STAT.FATE,30);
      attrs.setGem(gem);
    }
    {
      // Rune
      Relic rune=new Relic("Great River Rune of Power", RelicType.RUNE, Integer.valueOf(75));
      BasicStatsSet stats=rune.getStats();
      stats.setStat(STAT.PHYSICAL_MITIGATION,170);
      stats.setStat(STAT.PHYSICAL_MASTERY,606);
      stats.setStat(STAT.TACTICAL_MASTERY,606);
      stats.setStat(STAT.FATE,27);
      attrs.setRune(rune);
    }
    {
      // Crafted relic
      Relic craftedRelic=new Relic("Westemnet Device of Tactics", RelicType.CRAFTED_RELIC, Integer.valueOf(95));
      BasicStatsSet stats=craftedRelic.getStats();
      stats.setStat(STAT.WILL,40);
      stats.setStat(STAT.CRITICAL_RATING,740);
      stats.setStat(STAT.TACTICAL_MASTERY,740);
      attrs.setCraftedRelic(craftedRelic);
    }
    // Stat legacies
    // None...
    return classItem;
  }

  private Item buildCraftingTool()
  {
    Item ret=new Item();
    ret.setName("Superior Tools of the Westemnet Tinker");
    ret.setItemLevel(Integer.valueOf(90));
    ret.setMinLevel(Integer.valueOf(90));
    ret.setDurability(Integer.valueOf(60));
    ret.setSturdiness(ItemSturdiness.TOUGH);
    ret.setValue(new Money(0,5,40));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(STAT.JEWELLER_CRIT_CHANCE_PERCENTAGE, 25);
    stats.setStat(STAT.COOK_CRIT_CHANCE_PERCENTAGE, 25);
    stats.setStat(STAT.PROSPECTOR_MINING_DURATION, -4);
    return ret;
  }
}
