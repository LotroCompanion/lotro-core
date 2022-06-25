package delta.games.lotro.character.stats;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterFactory;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.character.gear.CharacterGear;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlotContents;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.ItemSturdiness;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.LegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.LegendaryItem;
import delta.games.lotro.lore.items.legendary.LegendaryItemInstance;
import delta.games.lotro.lore.items.legendary.LegendaryWeapon;
import delta.games.lotro.lore.items.legendary.LegendaryWeaponInstance;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.relics.RelicsSet;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;

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
  public CharacterData buildCharacter()
  {
    CharacterSummary summary=new CharacterSummary();
    summary.setName("Meva");
    summary.setRace(Race.HOBBIT);
    summary.setLevel(100);
    summary.setCharacterClass(CharacterClass.MINSTREL);
    CharacterData c=CharacterFactory.buildNewData(summary);

    // Virtues
    VirtuesSet virtues=c.getVirtues();
    virtues.setVirtueValue(_tools.getVirtue("CHARITY"),19);
    virtues.setVirtueValue(_tools.getVirtue("COMPASSION"),17);
    virtues.setVirtueValue(_tools.getVirtue("CONFIDENCE"),18);
    virtues.setVirtueValue(_tools.getVirtue("DETERMINATION"),12);
    virtues.setVirtueValue(_tools.getVirtue("DISCIPLINE"),11);
    virtues.setVirtueValue(_tools.getVirtue("EMPATHY"),17);
    virtues.setVirtueValue(_tools.getVirtue("FIDELITY"),16);
    virtues.setVirtueValue(_tools.getVirtue("FORTITUDE"),12);
    virtues.setVirtueValue(_tools.getVirtue("HONESTY"),19);
    virtues.setVirtueValue(_tools.getVirtue("HONOUR"),15);
    virtues.setVirtueValue(_tools.getVirtue("IDEALISM"),19);
    virtues.setVirtueValue(_tools.getVirtue("JUSTICE"),11);
    virtues.setVirtueValue(_tools.getVirtue("LOYALTY"),19);
    virtues.setVirtueValue(_tools.getVirtue("MERCY"),11);
    virtues.setVirtueValue(_tools.getVirtue("PATIENCE"),16);
    virtues.setVirtueValue(_tools.getVirtue("INNOCENCE"),19);
    virtues.setVirtueValue(_tools.getVirtue("TOLERANCE"),14);
    virtues.setVirtueValue(_tools.getVirtue("VALOUR"),14);
    virtues.setVirtueValue(_tools.getVirtue("WISDOM"),16);
    virtues.setVirtueValue(_tools.getVirtue("ZEAL"),18);
    virtues.setSelectedVirtue(_tools.getVirtue("CHARITY"),0);
    virtues.setSelectedVirtue(_tools.getVirtue("LOYALTY"),1);
    virtues.setSelectedVirtue(_tools.getVirtue("IDEALISM"),2);
    virtues.setSelectedVirtue(_tools.getVirtue("INNOCENCE"),3);
    virtues.setSelectedVirtue(_tools.getVirtue("HONOUR"),4);
    // Tomes
    //TomesSet tomes=c.getTomes();
    //tomes.setTomeRank(STAT.MIGHT,1);
    //tomes.setTomeRank(STAT.FATE,2);
    // TODO Racial traits
    CharacterGear equipment=c.getEquipment();
    GearSlotContents head=equipment.getSlotContents(GearSlot.HEAD,true);
    head.setItem(buildHelm());
    GearSlotContents shoulders=equipment.getSlotContents(GearSlot.SHOULDER,true);
    shoulders.setItem(buildShoulders());
    GearSlotContents boots=equipment.getSlotContents(GearSlot.FEET,true);
    boots.setItem(buildBoots());
    GearSlotContents pocket=equipment.getSlotContents(GearSlot.POCKET,true);
    pocket.setItem(buildPocket());
    GearSlotContents cloak=equipment.getSlotContents(GearSlot.BACK,true);
    cloak.setItem(buildCloak());
    GearSlotContents chest=equipment.getSlotContents(GearSlot.BREAST,true);
    chest.setItem(buildChest());
    GearSlotContents gloves=equipment.getSlotContents(GearSlot.HANDS,true);
    gloves.setItem(buildGloves());
    GearSlotContents leggings=equipment.getSlotContents(GearSlot.LEGS,true);
    leggings.setItem(buildLeggings());
    // Weapons
    GearSlotContents club=equipment.getSlotContents(GearSlot.MAIN_MELEE,true);
    club.setItem(buildWeapon());
    GearSlotContents shield=equipment.getSlotContents(GearSlot.OTHER_MELEE,true);
    shield.setItem(buildShield());
    GearSlotContents bow=equipment.getSlotContents(GearSlot.RANGED,true);
    bow.setItem(buildRanged());
    // Tools
    GearSlotContents tool=equipment.getSlotContents(GearSlot.TOOL,true);
    tool.setItem(buildCraftingTool());
    // Class slot
    GearSlotContents rune=equipment.getSlotContents(GearSlot.CLASS_ITEM,true);
    rune.setItem(buildBook());
    // Jewels
    GearSlotContents earring1=equipment.getSlotContents(GearSlot.LEFT_EAR,true);
    earring1.setItem(buildEarring1());
    GearSlotContents earring2=equipment.getSlotContents(GearSlot.RIGHT_EAR,true);
    earring2.setItem(buildEarring2());
    GearSlotContents bracelet1=equipment.getSlotContents(GearSlot.LEFT_WRIST,true);
    bracelet1.setItem(buildBracelet1());
    GearSlotContents bracelet2=equipment.getSlotContents(GearSlot.RIGHT_WRIST,true);
    bracelet2.setItem(buildBracelet2());
    GearSlotContents necklace=equipment.getSlotContents(GearSlot.NECK,true);
    necklace.setItem(buildNecklace());
    GearSlotContents ring1=equipment.getSlotContents(GearSlot.LEFT_FINGER,true);
    ring1.setItem(buildRing1());
    GearSlotContents ring2=equipment.getSlotContents(GearSlot.RIGHT_FINGER,true);
    ring2.setItem(buildRing2());

    // Additional stats
    BasicStatsSet additionalStats=c.getAdditionalStats();
    // Buff
    additionalStats.addStat(WellKnownStat.MIGHT,Integer.valueOf(20));
    additionalStats.addStat(WellKnownStat.HOPE,Integer.valueOf(1));

    // Red trait tree:
    {
      // Enduring Morale, Rank 2
      additionalStats.addStat(WellKnownStat.MORALE,Integer.valueOf(537));
      // Finesse, Rank 5
      additionalStats.addStat(WellKnownStat.FINESSE,Integer.valueOf(2486));
    }
    equipment.setWearer(c.getSummary());
    return c;
  }

  private ItemInstance<? extends Item> buildHelm()
  {
    // Light Nadhin Hood (level 192)
    ItemInstance<? extends Item> instance=_tools.getItemById(1879313783);

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
    instance.setEssences(essences);
    return instance;
  }

  private ItemInstance<? extends Item> buildShoulders()
  {
    // Light Nadhin Shoulders
    ItemInstance<? extends Item> instance=_tools.getItemById(1879313778);

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
    instance.setEssences(essences);
    return instance;
  }

  private ItemInstance<? extends Item> buildBoots()
  {
    // Boots of the Storied Mariner
    return _tools.getItemById(1879313346);
  }

  private ItemInstance<? extends Item> buildPocket()
  {
    // Second Exquisite Minstrel's Token of Helm Hammerhand
    return _tools.getItemById(1879311026);
  }

  private ItemInstance<? extends Item> buildCloak()
  {
    // Greater Resolute Cloak of Tactics
    return _tools.getItemById(1879286095);
  }

  private ItemInstance<? extends Item> buildChest()
  {
    // Jacket of the Storied Mariner
    return _tools.getItemById(1879313353);
  }

  private ItemInstance<? extends Item> buildGloves()
  {
    // Gloves of the Great Shore
    return _tools.getItemById(1879310211);
  }

  private ItemInstance<? extends Item> buildLeggings()
  {
    // Greater Resolute Leggings of Penetration
    return _tools.getItemById(1879286081);
  }

  private ItemInstance<? extends Item> buildShield()
  {
    // Blackroot Vibrant Shield
    return _tools.getItemById(1879313707);
  }

  private ItemInstance<? extends Item> buildRanged()
  {
    // Exquisite Walnut Theorbo of the Battle-singer
    return _tools.getItemById(1879283354);
  }

  private ItemInstance<? extends Item> buildEarring1()
  {
    // Third Exquisite Minstrel's Earring of Helm Hammerhand
    return _tools.getItemById(1879311185);
  }

  private ItemInstance<? extends Item> buildEarring2()
  {
    // First Exquisite Minstrel's Earring of Helm Hammerhand
    return _tools.getItemById(1879311050);
  }

  private ItemInstance<? extends Item> buildNecklace()
  {
    // First Exquisite Minstrel's Necklace of Helm Hammerhand
    return _tools.getItemById(1879311004);
  }

  private ItemInstance<? extends Item> buildBracelet1()
  {
    // Second Exquisite Minstrel's Bracelet of Helm Hammerhand
    return _tools.getItemById(1879311011);
  }

  private ItemInstance<? extends Item> buildBracelet2()
  {
    // Adept Officer's Persevering Bracelet
    // Level 100
    ItemInstance<? extends Item> ret=_tools.getItemById(1879310845);
    return ret;
  }

  private ItemInstance<? extends Item> buildRing1()
  {
    // Second Exquisite Minstrel's Ring of Helm Hammerhand
    return _tools.getItemById(1879311024);
  }

  private ItemInstance<? extends Item> buildRing2()
  {
    // 1879318796" name="Advisor's Fateful Ring
    ItemInstance<? extends Item> ret=_tools.getItemById(1879318796);
    EssencesSet essences=new EssencesSet(1);
    Item tacticalMastery=_tools.getEssenceByName(6,"Supreme Essence of Tactical Mastery");
    tacticalMastery.getStats().setStat(WellKnownStat.TACTICAL_MASTERY,1165);
    tacticalMastery.getStats().setStat(WellKnownStat.POWER,88);
    essences.setEssence(0,tacticalMastery);
    ret.setEssences(essences);
    return ret;
  }

  private LegendaryWeaponInstance buildWeapon()
  {
    LegendaryWeapon weapon=new LegendaryWeapon();
    weapon.setName("Reshaped Minstrel's Club of the First Age");
    weapon.setRequiredClass(CharacterClass.MINSTREL);
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

    // Instance
    LegendaryWeaponInstance instance=new LegendaryWeaponInstance();
    instance.setReference(weapon);
    instance.setBirthName("Cavern Club");
    instance.setCrafterName("Glumlug");

    // Passives
    //BasicStatsSet passives=weapon.getPassives();
    // -40% attack speed
    //passives.setStat(STAT.PARRY_PERCENTAGE,1);
    LegendaryInstanceAttrs attrs=instance.getLegendaryAttributes();
    // Title
    LegendaryTitle title=new LegendaryTitle();
    title.setName("Elven Orc-Hewer");
    //BasicStatsSet titleStats=title.getStats();
    //titleStats.setStat(STAT.CRITICAL_RATING,460);
    attrs.setTitle(title);
    // Relics
    RelicsSet relics=attrs.getRelicsSet();
    {
      // Setting
      Relic setting=_tools.buildRelic(0,"True Setting of the North", RelicType.SETTING, Integer.valueOf(80));
      BasicStatsSet stats=setting.getStats();
      stats.setStat(WellKnownStat.MORALE, 330);
      stats.setStat(WellKnownStat.CRITICAL_RATING,1293);
      stats.setStat(WellKnownStat.INCOMING_HEALING,1616);
      relics.setSetting(setting);
    }
    {
      // Gem
      Relic gem=_tools.buildRelic(0,"True Gem of the Wizard's Vale", RelicType.GEM, Integer.valueOf(75));
      BasicStatsSet stats=gem.getStats();
      stats.setStat(WellKnownStat.ICPR,90);
      stats.setStat(WellKnownStat.CRITICAL_RATING,1212);
      stats.setStat(WellKnownStat.FATE,30);
      relics.setGem(gem);
    }
    {
      // Rune
      Relic rune=_tools.buildRelic(0,"Great River Rune of Power", RelicType.RUNE, Integer.valueOf(75));
      BasicStatsSet stats=rune.getStats();
      stats.setStat(WellKnownStat.PHYSICAL_MITIGATION,170);
      stats.setStat(WellKnownStat.PHYSICAL_MASTERY,606);
      stats.setStat(WellKnownStat.TACTICAL_MASTERY,606);
      stats.setStat(WellKnownStat.FATE,27);
      relics.setRune(rune);
    }
    {
      // Crafted relic
      Relic craftedRelic=_tools.buildRelic(0,"Westemnet Device of Tactics", RelicType.CRAFTED_RELIC, Integer.valueOf(95));
      BasicStatsSet stats=craftedRelic.getStats();
      stats.setStat(WellKnownStat.WILL,40);
      stats.setStat(WellKnownStat.CRITICAL_RATING,740);
      stats.setStat(WellKnownStat.TACTICAL_MASTERY,740);
      relics.setCraftedRelic(craftedRelic);
    }
    // Stat legacies
    // TODO
    return instance;
  }

  private LegendaryItemInstance buildBook()
  {
    LegendaryItem classItem=new LegendaryItem();
    classItem.setName("Reforged Minstrel's Songbook of the Second Age");
    classItem.setRequiredClass(CharacterClass.MINSTREL);
    classItem.setMinLevel(Integer.valueOf(95));
    classItem.setDurability(Integer.valueOf(80));
    classItem.setSturdiness(ItemSturdiness.NORMAL);
    classItem.setEquipmentLocation(EquipmentLocation.CLASS_SLOT);
    classItem.setQuality(ItemQuality.LEGENDARY);

    LegendaryItemInstance instance=new LegendaryItemInstance();
    instance.setReference(classItem);
    instance.setBirthName("Small blue book");
    instance.setCrafterName("Utharr");

    // Passives
    //BasicStatsSet passives=classItem.getPassives();
    //passives.setStat(STAT.BLADE_LINE_AOE_POWER_COST_PERCENTAGE,-1);
    //passives.setStat(STAT.STRIKE_SKILLS_POWER_COST_PERCENTAGE,-4);
    //passives.setStat(STAT.INCOMING_HEALING,6300);
    // -1% perceived threat
    // +95.24 Tactical Healing Rating
    LegendaryInstanceAttrs attrs=instance.getLegendaryAttributes();
    // Title
    // 5% light-type damage
    // Relics
    RelicsSet relics=attrs.getRelicsSet();
    {
      // Setting
      Relic setting=_tools.buildRelic(0,"True Setting of the North", RelicType.SETTING, Integer.valueOf(80));
      BasicStatsSet stats=setting.getStats();
      stats.setStat(WellKnownStat.MORALE, 330);
      stats.setStat(WellKnownStat.CRITICAL_RATING,1293);
      stats.setStat(WellKnownStat.INCOMING_HEALING,1616);
      relics.setSetting(setting);
    }
    {
      // Gem
      Relic gem=_tools.buildRelic(0,"True Gem of the Wizard's Vale", RelicType.GEM, Integer.valueOf(75));
      BasicStatsSet stats=gem.getStats();
      stats.setStat(WellKnownStat.ICPR,90);
      stats.setStat(WellKnownStat.CRITICAL_RATING,1212);
      stats.setStat(WellKnownStat.FATE,30);
      relics.setGem(gem);
    }
    {
      // Rune
      Relic rune=_tools.buildRelic(0,"Great River Rune of Power", RelicType.RUNE, Integer.valueOf(75));
      BasicStatsSet stats=rune.getStats();
      stats.setStat(WellKnownStat.PHYSICAL_MITIGATION,170);
      stats.setStat(WellKnownStat.PHYSICAL_MASTERY,606);
      stats.setStat(WellKnownStat.TACTICAL_MASTERY,606);
      stats.setStat(WellKnownStat.FATE,27);
      relics.setRune(rune);
    }
    {
      // Crafted relic
      Relic craftedRelic=_tools.buildRelic(0,"Westemnet Device of Tactics", RelicType.CRAFTED_RELIC, Integer.valueOf(95));
      BasicStatsSet stats=craftedRelic.getStats();
      stats.setStat(WellKnownStat.WILL,40);
      stats.setStat(WellKnownStat.CRITICAL_RATING,740);
      stats.setStat(WellKnownStat.TACTICAL_MASTERY,740);
      relics.setCraftedRelic(craftedRelic);
    }
    // Stat legacies
    // None...
    return instance;
  }

  private ItemInstance<Item> buildCraftingTool()
  {
    Item ret=new Item();
    ret.setName("Superior Tools of the Westemnet Tinker");
    ret.setItemLevel(Integer.valueOf(90));
    ret.setMinLevel(Integer.valueOf(90));
    ret.setDurability(Integer.valueOf(60));
    ret.setSturdiness(ItemSturdiness.TOUGH);
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.get("JEWELLER_CRIT_CHANCE_PERCENTAGE"), 25);
    stats.setStat(WellKnownStat.get("COOK_CRIT_CHANCE_PERCENTAGE"), 25);
    stats.setStat(WellKnownStat.get("PROSPECTOR_MINING_DURATION"), -4);
    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    return instance;
  }
}
