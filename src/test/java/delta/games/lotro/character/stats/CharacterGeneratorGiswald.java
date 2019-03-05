package delta.games.lotro.character.stats;

import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterEquipment;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.CharacterEquipment.SlotContents;
import delta.games.lotro.character.stats.tomes.TomesSet;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Effect;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.ArmourInstance;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.ItemSturdiness;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.WeaponInstance;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.LegaciesManager;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.LegendaryItem;
import delta.games.lotro.lore.items.legendary.LegendaryItemInstance;
import delta.games.lotro.lore.items.legendary.LegendaryWeapon;
import delta.games.lotro.lore.items.legendary.LegendaryWeaponInstance;
import delta.games.lotro.lore.items.legendary.PassivesManager;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegendaryAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegaciesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegacyTier;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitlesManager;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Character generator for tests.
 * @author DAM
 */
public class CharacterGeneratorGiswald
{
  private CharacterGenerationTools _tools;

  /**
   * Constructor.
   * @param tools Tools.
   */
  public CharacterGeneratorGiswald(CharacterGenerationTools tools)
  {
    _tools=tools;
  }

  /**
   * Build a test character.
   * @return a character.
   */
  public CharacterData buildCharacter()
  {
    CharacterData c=new CharacterData();
    c.setName("Giswald");
    c.setServer("Elendilmir");
    c.setRace(Race.MAN);
    c.setRegion("Gondor");
    c.setLevel(100);
    c.setShortDescription("Test for stats computation");
    c.setDescription("This toon was generated from code, and stats were computed automatically.");
    c.setCharacterClass(CharacterClass.CHAMPION);
    // Virtues
    VirtuesSet virtues=c.getVirtues();
    virtues.setVirtueValue(VirtueId.CHARITY,15);
    virtues.setVirtueValue(VirtueId.COMPASSION,11);
    virtues.setVirtueValue(VirtueId.CONFIDENCE,10);
    virtues.setVirtueValue(VirtueId.DETERMINATION,8);
    virtues.setVirtueValue(VirtueId.DISCIPLINE,4);
    virtues.setVirtueValue(VirtueId.EMPATHY,14);
    virtues.setVirtueValue(VirtueId.FIDELITY,13);
    virtues.setVirtueValue(VirtueId.FORTITUDE,7);
    virtues.setVirtueValue(VirtueId.HONESTY,11);
    virtues.setVirtueValue(VirtueId.HONOUR,10);
    virtues.setVirtueValue(VirtueId.IDEALISM,15);
    virtues.setVirtueValue(VirtueId.JUSTICE,9);
    virtues.setVirtueValue(VirtueId.LOYALTY,14);
    virtues.setVirtueValue(VirtueId.MERCY,6);
    virtues.setVirtueValue(VirtueId.PATIENCE,7);
    virtues.setVirtueValue(VirtueId.INNOCENCE,13);
    virtues.setVirtueValue(VirtueId.TOLERANCE,12);
    virtues.setVirtueValue(VirtueId.VALOUR,7);
    virtues.setVirtueValue(VirtueId.WISDOM,11);
    virtues.setVirtueValue(VirtueId.ZEAL,11);
    virtues.setSelectedVirtue(VirtueId.INNOCENCE,0);
    virtues.setSelectedVirtue(VirtueId.CHARITY,1);
    virtues.setSelectedVirtue(VirtueId.EMPATHY,2);
    virtues.setSelectedVirtue(VirtueId.ZEAL,3);
    virtues.setSelectedVirtue(VirtueId.HONOUR,4);
    // Tomes
    TomesSet tomes=c.getTomes();
    tomes.setTomeRank(WellKnownStat.MIGHT,1);
    tomes.setTomeRank(WellKnownStat.FATE,2);
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
    SlotContents sword1=equipment.getSlotContents(EQUIMENT_SLOT.MAIN_MELEE,true);
    sword1.setItem(buildWeapon());
    SlotContents sword2=equipment.getSlotContents(EQUIMENT_SLOT.OTHER_MELEE,true);
    sword2.setItem(build2ndSword());
    SlotContents bow=equipment.getSlotContents(EQUIMENT_SLOT.RANGED,true);
    bow.setItem(buildBow());
    // Tools
    SlotContents tool=equipment.getSlotContents(EQUIMENT_SLOT.TOOL,true);
    tool.setItem(buildCraftingTool());
    // Class slot
    SlotContents rune=equipment.getSlotContents(EQUIMENT_SLOT.CLASS_ITEM,true);
    rune.setItem(buildRune());
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
    // - yellow line buffs
    BasicStatsSet additionalStats=c.getAdditionalStats();
    additionalStats.addStat(WellKnownStat.MIGHT, new FixedDecimalsInteger(158));
    additionalStats.addStat(WellKnownStat.FINESSE, new FixedDecimalsInteger(2486));
    // - Balance of Man
    additionalStats.addStat(WellKnownStat.EVADE, new FixedDecimalsInteger(808));
    additionalStats.addStat(WellKnownStat.PARRY, new FixedDecimalsInteger(808));
    additionalStats.addStat(WellKnownStat.BLOCK, new FixedDecimalsInteger(808));

    return c;
  }

  private ArmourInstance buildHelm()
  {
    Armour ret=new Armour();
    ret.setName("Heavy Nadhin Mask");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(90));
    ret.setValue(new Money(0,54,18));
    ret.setArmourType(ArmourType.HEAVY);
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,119);
    stats.setStat(WellKnownStat.ARMOUR,1024);

    ArmourInstance instance=new ArmourInstance();
    instance.setReference(ret);
    EssencesSet essences=new EssencesSet(4);
    Item might=_tools.getEssenceByName(7,"Major Essence of Might");
    //might.getStats().setStat(STAT.MIGHT,119);
    essences.setEssence(0,might);
    essences.setEssence(1,might);
    Item critDef=_tools.getEssenceByName(7,"Major Essence of Critical Defence");
    //critDef.getStats().setStat(STAT.CRITICAL_DEFENCE,2702);
    essences.setEssence(2,critDef);
    Item physicalMitigation=_tools.getEssenceByName(7,"Major Essence of Physical Mitigation");
    //physicalMitigation.getStats().setStat(STAT.PHYSICAL_MITIGATION,2420);
    essences.setEssence(3,physicalMitigation);
    instance.setEssences(essences);
    return instance;
  }

  private ArmourInstance buildShoulders()
  {
    Armour ret=new Armour();
    ret.setName("Heavy Nadhin Shoulders");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(80));
    ret.setValue(new Money(0,53,91));
    ret.setArmourType(ArmourType.HEAVY);
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,119);
    stats.setStat(WellKnownStat.ARMOUR,1024);

    ArmourInstance instance=new ArmourInstance();
    instance.setReference(ret);
    EssencesSet essences=new EssencesSet(4);
    Item morale=_tools.getEssenceByName(7,"Major Essence of Morale");
    //morale.getStats().setStat(STAT.MORALE,765);
    essences.setEssence(0,morale);
    essences.setEssence(1,morale);
    essences.setEssence(2,morale);
    essences.setEssence(3,morale);
    instance.setEssences(essences);
    return instance;
  }

  private ArmourInstance buildBoots()
  {
    Armour ret=new Armour();
    ret.setName("Heavy Nadhin Boots");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(65));
    ret.setArmourType(ArmourType.HEAVY);
    ret.setValue(new Money(0,54,0));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,119);
    stats.setStat(WellKnownStat.ARMOUR,1365);

    ArmourInstance instance=new ArmourInstance();
    instance.setReference(ret);
    EssencesSet essences=new EssencesSet(4);
    Item morale=_tools.getEssenceByName(7,"Greater Essence of Morale");
    //morale.getStats().setStat(STAT.MORALE,875);
    essences.setEssence(0,morale);
    essences.setEssence(1,morale);
    essences.setEssence(2,morale);
    Item supremeMorale=_tools.getEssenceByName(7,"Supreme Essence of Morale");
    //supremeMorale.getStats().setStat(STAT.MORALE,891);
    //supremeMorale.getStats().setStat(STAT.POWER,96);
    essences.setEssence(3,supremeMorale);
    instance.setEssences(essences);
    return instance;
  }

  private ItemInstance<Item> buildPocket()
  {
    Item ret=new Item();
    ret.setName("Greater Combative Scroll of Anorien");
    ret.setItemLevel(Integer.valueOf(201));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(100));
    ret.setValue(new Money(0,72,9));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,270);
    stats.setStat(WellKnownStat.VITALITY,180);
    stats.setStat(WellKnownStat.MORALE,578);
    stats.setStat(WellKnownStat.CRITICAL_RATING,804);

    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    return instance;
  }

  private ArmourInstance buildCloak()
  {
    Armour ret=new Armour();
    ret.setName("Supple Fangorn Cloack of Might");
    ret.setItemLevel(Integer.valueOf(182));
    ret.setMinLevel(Integer.valueOf(95));
    ret.setDurability(Integer.valueOf(40));
    ret.setArmourType(ArmourType.LIGHT);
    ret.setValue(new Money(0,42,93));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,225);
    stats.setStat(WellKnownStat.VITALITY,193);
    stats.setStat(WellKnownStat.CRITICAL_RATING,1165);
    stats.setStat(WellKnownStat.PHYSICAL_MASTERY,145);
    stats.setStat(WellKnownStat.ARMOUR,1151);

    ArmourInstance instance=new ArmourInstance();
    instance.setReference(ret);
    return instance;
  }

  private ArmourInstance buildChest()
  {
    Armour ret=new Armour();
    ret.setName("Jacket of the Osgiliath Storm");
    ret.setItemLevel(Integer.valueOf(201));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(95));
    ret.setArmourType(ArmourType.HEAVY);
    ret.setValue(new Money(0,0,0));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,360);
    stats.setStat(WellKnownStat.VITALITY,90);
    stats.setStat(WellKnownStat.PHYSICAL_MASTERY,1608);
    stats.setStat(WellKnownStat.ARMOUR,3534);

    ArmourInstance instance=new ArmourInstance();
    instance.setReference(ret);
    return instance;
  }

  private ArmourInstance buildGloves()
  {
    Armour ret=new Armour();
    ret.setName("Gloves of the Osgiliath Storm");
    ret.setItemLevel(Integer.valueOf(201));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(65));
    ret.setArmourType(ArmourType.HEAVY);
    ret.setValue(new Money(0,0,0));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,360);
    stats.setStat(WellKnownStat.VITALITY,90);
    stats.setStat(WellKnownStat.MORALE,578);
    stats.setStat(WellKnownStat.FINESSE,2450);
    stats.setStat(WellKnownStat.ARMOUR,1767);

    ArmourInstance instance=new ArmourInstance();
    instance.setReference(ret);
    return instance;
  }

  private ArmourInstance buildLeggings()
  {
    Armour ret=new Armour();
    ret.setName("Leggings of the Sea Reaver");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(90));
    ret.setArmourType(ArmourType.HEAVY);
    ret.setValue(new Money(0,0,0));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,239);
    stats.setStat(WellKnownStat.VITALITY,205);
    stats.setStat(WellKnownStat.FATE,85);
    stats.setStat(WellKnownStat.POWER,377);
    stats.setStat(WellKnownStat.ARMOUR,2838);

    ArmourInstance instance=new ArmourInstance();
    instance.setReference(ret);
    return instance;
  }

  private WeaponInstance<Weapon> build2ndSword()
  {
    Weapon ret=new Weapon();
    ret.setName("Greater Valourous Sword of Finesse");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(100));
    ret.setDamageType(DamageType.WESTERNESSE);
    ret.setDPS(234.1f);
    ret.setMinDamage(334);
    ret.setMaxDamage(556);
    ret.setValue(new Money(0,42,57));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.PARRY_PERCENTAGE,1);
    stats.setStat(WellKnownStat.MIGHT,239);
    stats.setStat(WellKnownStat.VITALITY,205);
    stats.setStat(WellKnownStat.FINESSE,1874);
    stats.setStat(WellKnownStat.PHYSICAL_MASTERY,461);
    stats.setStat(WellKnownStat.CRITICAL_RATING,307);

    WeaponInstance<Weapon> instance=new WeaponInstance<Weapon>();
    instance.setReference(ret);
    return instance;
  }

  private WeaponInstance<Weapon> buildBow()
  {
    Weapon ret=new Weapon();
    ret.setName("Greater Valourous Bow of Finesse");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(100));
    ret.setDamageType(DamageType.WESTERNESSE);
    ret.setDPS(251.2f);
    ret.setMinDamage(392);
    ret.setMaxDamage(713);
    ret.setValue(new Money(0,42,57));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.get("RANGED_AUTO_ATTACKS_CRIT_CHANCE_PERCENTAGE"),1);
    stats.setStat(WellKnownStat.MIGHT,239);
    stats.setStat(WellKnownStat.VITALITY,205);
    stats.setStat(WellKnownStat.FINESSE,1874);
    stats.setStat(WellKnownStat.PHYSICAL_MASTERY,461);
    stats.setStat(WellKnownStat.CRITICAL_RATING,307);

    WeaponInstance<Weapon> instance=new WeaponInstance<Weapon>();
    instance.setReference(ret);
    return instance;
  }

  private ItemInstance<Item> buildEarring1()
  {
    Item ret=new Item();
    ret.setName("Skilled Vanguard's Valourous Earring");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(60));
    ret.setValue(new Money(0,16,53));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,239);
    stats.setStat(WellKnownStat.VITALITY,205);
    stats.setStat(WellKnownStat.CRITICAL_RATING,614);
    stats.setStat(WellKnownStat.PHYSICAL_MASTERY,461);
    stats.setStat(WellKnownStat.MORALE,219);

    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    return instance;
  }

  private ItemInstance<Item> buildEarring2()
  {
    Item ret=new Item();
    ret.setName("First Exquisite Champion's Earring of Helm Hammerhand");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(60));
    ret.setValue(new Money(0,22,4));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,273);
    stats.setStat(WellKnownStat.VITALITY,239);
    stats.setStat(WellKnownStat.CRITICAL_RATING,614);
    stats.setStat(WellKnownStat.PHYSICAL_MASTERY,614);
    stats.setStat(WellKnownStat.MORALE,219);
    // TODO Set effects

    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    return instance;
  }

  private ItemInstance<Item> buildNecklace()
  {
    Item ret=new Item();
    ret.setName("First Exquisite Champion's Necklace of Helm Hammerhand");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(60));
    ret.setValue(new Money(0,22,4));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,273);
    stats.setStat(WellKnownStat.VITALITY,239);
    stats.setStat(WellKnownStat.PHYSICAL_MASTERY,614);
    stats.setStat(WellKnownStat.CRITICAL_RATING,614);
    stats.setStat(WellKnownStat.POWER,189);
    // TODO Set effects

    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    return instance;
  }

  private ItemInstance<Item> buildBracelet1()
  {
    Item ret=new Item();
    ret.setName("Gleaming Bracelet of the Prince");
    ret.setItemLevel(Integer.valueOf(195));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(80));
    ret.setValue(new Money(0,17,55));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,261);
    stats.setStat(WellKnownStat.VITALITY,174);
    stats.setStat(WellKnownStat.PHYSICAL_MITIGATION,1756);
    stats.setStat(WellKnownStat.TACTICAL_MITIGATION,1756);

    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    return instance;
  }

  private ItemInstance<Item> buildBracelet2()
  {
    Item ret=new Item();
    ret.setName("Skilled Vanguard's Enduring Bracelet");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(80));
    ret.setValue(new Money(0,17,28));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.VITALITY,239);
    stats.setStat(WellKnownStat.MIGHT,205);
    stats.setStat(WellKnownStat.FATE,68);
    stats.setStat(WellKnownStat.EVADE,1037);
    stats.setStat(WellKnownStat.MORALE,219);

    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    return instance;
  }

  private ItemInstance<Item> buildRing1()
  {
    Item ret=new Item();
    ret.setName("Elite Soldier's Fateful Ring");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(50));
    ret.setValue(new Money(0,22,4));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,307);
    stats.setStat(WellKnownStat.FATE,171);
    stats.setStat(WellKnownStat.FINESSE,2343);

    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    EssencesSet essences=new EssencesSet(1);
    Item supremeMight=_tools.getEssenceByName(7,"Supreme Essence of Might");
    //supremeMight.getStats().setStat(STAT.MIGHT,139);
    //supremeMight.getStats().setStat(STAT.VITALITY,17);
    essences.setEssence(0,supremeMight);
    instance.setEssences(essences);
    return instance;
  }

  private ItemInstance<Item> buildRing2()
  {
    Item ret=new Item();
    ret.setName("Elite Soldier's Sturdy Ring");
    ret.setItemLevel(Integer.valueOf(192));
    ret.setMinLevel(Integer.valueOf(100));
    ret.setDurability(Integer.valueOf(50));
    ret.setValue(new Money(0,22,4));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.MIGHT,256);
    stats.setStat(WellKnownStat.VITALITY,256);
    stats.setStat(WellKnownStat.TACTICAL_MITIGATION,1037);

    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    EssencesSet essences=new EssencesSet(1);
    Item supremeMight=_tools.getEssenceByName(7,"Supreme Essence of Might");
    //supremeMight.getStats().setStat(STAT.MIGHT,139);
    //supremeMight.getStats().setStat(STAT.VITALITY,17);
    essences.setEssence(0,supremeMight);
    instance.setEssences(essences);
    return instance;
  }

  private LegendaryWeaponInstance buildWeapon()
  {
    LegendaryWeapon weapon=new LegendaryWeapon();
    weapon.setName("Reshaped Champion's Sword of the First Age");
    weapon.setRequiredClass(CharacterClass.CHAMPION);
    weapon.setCategory(ItemCategory.LEGENDARY_WEAPON);
    weapon.setMinLevel(Integer.valueOf(100));
    weapon.setDurability(Integer.valueOf(100));
    weapon.setSturdiness(ItemSturdiness.NORMAL);
    weapon.setEquipmentLocation(EquipmentLocation.HAND);
    weapon.setQuality(ItemQuality.LEGENDARY);
    // Weapon specifics
    weapon.setWeaponType(WeaponType.ONE_HANDED_SWORD);
    weapon.setDPS(255);
    weapon.setMinDamage(363);
    weapon.setMaxDamage(606);
    weapon.setDamageType(DamageType.BELERIAND);
    BasicStatsSet weaponStats=weapon.getStats();
    weaponStats.setStat(WellKnownStat.PARRY_PERCENTAGE,1);

    LegendaryWeaponInstance instance=new LegendaryWeaponInstance();
    instance.setReference(weapon);
    instance.setBirthName("Ost Magol II");
    instance.setCrafterName("Giswald");

    LegendaryAttrs attrs=instance.getLegendaryAttributes();
    // Title
    LegendaryTitlesManager titlesMgr=LegendaryTitlesManager.getInstance();
    LegendaryTitle title=titlesMgr.getLegendaryTitle(1879269422);
    attrs.setTitle(title);
    // Legendary name
    attrs.setLegendaryName("Stormbringer");
    // Relics
    {
      // Setting
      Relic setting=new Relic(1,"Westemnet Setting of Endings", RelicType.SETTING, Integer.valueOf(90));
      BasicStatsSet stats=setting.getStats();
      stats.setStat(WellKnownStat.get("DEVASTATE_MAGNITUDE_PERCENTAGE"), 7.5f);
      stats.setStat(WellKnownStat.CRITICAL_RATING,1454);
      stats.setStat(WellKnownStat.ICMR,182);
      attrs.setSetting(setting);
    }
    {
      // Gem
      Relic gem=new Relic(2,"True Gem of the Wizard's Vale", RelicType.GEM, Integer.valueOf(75));
      BasicStatsSet stats=gem.getStats();
      stats.setStat(WellKnownStat.ICPR,90);
      stats.setStat(WellKnownStat.CRITICAL_RATING,1212);
      stats.setStat(WellKnownStat.FATE,30);
      attrs.setGem(gem);
    }
    {
      // Rune
      Relic rune=new Relic(3,"Great River Rune of Power", RelicType.RUNE, Integer.valueOf(75));
      BasicStatsSet stats=rune.getStats();
      stats.setStat(WellKnownStat.PHYSICAL_MITIGATION,170);
      stats.setStat(WellKnownStat.PHYSICAL_MASTERY,606);
      stats.setStat(WellKnownStat.TACTICAL_MASTERY,606);
      stats.setStat(WellKnownStat.FATE,27);
      attrs.setRune(rune);
    }
    {
      // Crafted relic
      Relic craftedRelic=new Relic(4,"Westemnet Device of Battle", RelicType.CRAFTED_RELIC, Integer.valueOf(95));
      BasicStatsSet stats=craftedRelic.getStats();
      stats.setStat(WellKnownStat.MIGHT,40);
      stats.setStat(WellKnownStat.CRITICAL_RATING,740);
      stats.setStat(WellKnownStat.PHYSICAL_MASTERY,740);
      attrs.setCraftedRelic(craftedRelic);
    }
    // Passives
    {
      PassivesManager passivesMgr=PassivesManager.getInstance();
      List<Effect> passives=passivesMgr.getAll();
      for(int i=0;i<2;i++)
      {
        attrs.addPassive(passives.get(i));
      }
    }
    // Non-imbued data
    {
      NonImbuedLegendaryAttrs nonImbuedAttrs=attrs.getNonImbuedAttrs();
      // - attributes
      nonImbuedAttrs.setNbUpgrades(2);
      nonImbuedAttrs.setLegendaryItemLevel(70);
      nonImbuedAttrs.setPointsSpent(100);
      nonImbuedAttrs.setPointsLeft(500);
      // - legacies
      NonImbuedLegaciesManager nonImbuedLegaciesMgr=NonImbuedLegaciesManager.getInstance();
      // - default legacy
      List<DefaultNonImbuedLegacy> possibleDefaultLegacies=nonImbuedLegaciesMgr.getDefaultLegacies(CharacterClass.CHAMPION,EquipmentLocation.MAIN_HAND);
      if (possibleDefaultLegacies.size()>0)
      {
        DefaultNonImbuedLegacy defaultLegacy=possibleDefaultLegacies.get(0);
        DefaultNonImbuedLegacyInstance defaultLegacyInstance=new DefaultNonImbuedLegacyInstance();
        defaultLegacyInstance.setLegacy(defaultLegacy);
        defaultLegacyInstance.setRank(100);
        nonImbuedAttrs.setDefaultLegacy(defaultLegacyInstance);
      }
      // - other legacies
      List<TieredNonImbuedLegacy> possibleLegacies=nonImbuedLegaciesMgr.getTieredLegacies(CharacterClass.CHAMPION,EquipmentLocation.MAIN_HAND);
      int nbLegacies=Math.min(possibleLegacies.size(),6);
      for(int i=0;i<nbLegacies;i++)
      {
        TieredNonImbuedLegacy legacy=possibleLegacies.get(i);
        TieredNonImbuedLegacyInstance legacyInstance=new TieredNonImbuedLegacyInstance();
        NonImbuedLegacyTier legacyTier=legacy.getTier(i+1);
        legacyInstance.setLegacyTier(legacyTier);
        legacyInstance.setRank(100);
        nonImbuedAttrs.addLegacy(legacyInstance);
      }
    }
    // Imbued data
    {
      ImbuedLegendaryAttrs imbuedAttrs=new ImbuedLegendaryAttrs();
      instance.getLegendaryAttributes().setImbuedAttrs(imbuedAttrs);
      // - legacies
      LegaciesManager legaciesMgr=LegaciesManager.getInstance();
      List<ImbuedLegacy> possibleLegacies=legaciesMgr.get(CharacterClass.CHAMPION,EquipmentLocation.MAIN_HAND);
      int nbLegacies=Math.min(possibleLegacies.size(),6);
      for(int i=0;i<nbLegacies;i++)
      {
        ImbuedLegacy legacy=possibleLegacies.get(i);
        ImbuedLegacyInstance legacyInstance=new ImbuedLegacyInstance();
        legacyInstance.setLegacy(legacy);
        legacyInstance.setXp(134567*i);
        legacyInstance.setUnlockedLevels(30);
        imbuedAttrs.addLegacy(legacyInstance);
      }
    }
    System.out.println(instance.getLegendaryAttributes().dump());
    return instance;
  }

  private LegendaryItemInstance buildRune()
  {
    LegendaryItem classItem=new LegendaryItem();
    classItem.setName("Reshaped Champion's Rune of the First Age");
    classItem.setRequiredClass(CharacterClass.CHAMPION);
    classItem.setCategory(ItemCategory.ITEM);
    classItem.setMinLevel(Integer.valueOf(100));
    classItem.setDurability(Integer.valueOf(80));
    classItem.setSturdiness(ItemSturdiness.NORMAL);
    classItem.setEquipmentLocation(EquipmentLocation.CLASS_SLOT);
    classItem.setQuality(ItemQuality.LEGENDARY);

    LegendaryItemInstance instance=new LegendaryItemInstance();
    instance.setReference(classItem);
    instance.setBirthName("Gondorian Rune II");
    instance.setCrafterName("Ethell");

    // Passives (as regular stats...)
    BasicStatsSet itemStats=classItem.getStats();
    itemStats.setStat(WellKnownStat.get("BLADE_LINE_AOE_POWER_COST_PERCENTAGE"),-1);
    itemStats.setStat(WellKnownStat.get("STRIKE_SKILLS_POWER_COST_PERCENTAGE"),-4);
    itemStats.setStat(WellKnownStat.INCOMING_HEALING,6300);
    LegendaryAttrs attrs=instance.getLegendaryAttributes();
    // Title
    // .. none ..
    // Relics
    {
      // Setting
      Relic setting=new Relic(0,"Westemnet Setting of Endings", RelicType.SETTING, Integer.valueOf(90));
      BasicStatsSet stats=setting.getStats();
      stats.setStat(WellKnownStat.get("DEVASTATE_MAGNITUDE_PERCENTAGE"),7.5f);
      stats.setStat(WellKnownStat.CRITICAL_RATING,1454);
      stats.setStat(WellKnownStat.ICMR,182);
      attrs.setSetting(setting);
    }
    {
      // Gem
      Relic gem=new Relic(0,"True Gem of the Wizard's Vale", RelicType.GEM, Integer.valueOf(75));
      BasicStatsSet stats=gem.getStats();
      stats.setStat(WellKnownStat.ICPR,90);
      stats.setStat(WellKnownStat.CRITICAL_RATING,1212);
      stats.setStat(WellKnownStat.FATE,30);
      attrs.setGem(gem);
    }
    {
      // Rune
      Relic rune=new Relic(0,"True Rune of the White Mountains", RelicType.RUNE, Integer.valueOf(80));
      BasicStatsSet stats=rune.getStats();
      stats.setStat(WellKnownStat.get("ATTACK_DURATION_PERCENTAGE"), -2.5f);
      stats.setStat(WellKnownStat.PHYSICAL_MASTERY,646);
      stats.setStat(WellKnownStat.TACTICAL_MASTERY,646);
      stats.setStat(WellKnownStat.AGILITY,35);
      attrs.setRune(rune);
    }
    {
      // Crafted relic
      Relic craftedRelic=new Relic(0,"Westemnet Device of Battle", RelicType.CRAFTED_RELIC, Integer.valueOf(95));
      BasicStatsSet stats=craftedRelic.getStats();
      stats.setStat(WellKnownStat.MIGHT,40);
      stats.setStat(WellKnownStat.CRITICAL_RATING,740);
      stats.setStat(WellKnownStat.PHYSICAL_MASTERY,740);
      attrs.setCraftedRelic(craftedRelic);
    }
    // Stat legacies
    // None...
    return instance;
  }

  private ItemInstance<Item> buildCraftingTool()
  {
    Item ret=new Item();
    ret.setName("Superior Tools of the Westemnet Armsman");
    ret.setItemLevel(Integer.valueOf(90));
    ret.setMinLevel(Integer.valueOf(90));
    ret.setDurability(Integer.valueOf(60));
    ret.setSturdiness(ItemSturdiness.TOUGH);
    ret.setValue(new Money(0,5,40));
    BasicStatsSet stats=ret.getStats();
    stats.setStat(WellKnownStat.get("WEAPONSMITH_CRIT_CHANCE_PERCENTAGE"), 25);
    stats.setStat(WellKnownStat.get("WOODWORKER_CRIT_CHANCE_PERCENTAGE"), 25);
    stats.setStat(WellKnownStat.get("PROSPECTOR_MINING_DURATION"), -4);

    ItemInstance<Item> instance=new ItemInstance<Item>();
    instance.setReference(ret);
    return instance;
  }
}
