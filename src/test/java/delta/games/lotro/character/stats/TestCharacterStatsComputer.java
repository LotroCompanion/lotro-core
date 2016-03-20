package delta.games.lotro.character.stats;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.Character;
import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.character.io.xml.CharacterXMLWriter;
import delta.games.lotro.character.legendary.LegendaryItem;
import delta.games.lotro.character.legendary.LegendaryTitle;
import delta.games.lotro.character.legendary.relics.Relic;
import delta.games.lotro.character.legendary.relics.RelicType;
import delta.games.lotro.character.stats.base.BaseStatsManager;
import delta.games.lotro.character.stats.base.DerivatedStatsContributionsMgr;
import delta.games.lotro.character.stats.tomes.TomesContributionsMgr;
import delta.games.lotro.character.stats.tomes.TomesSet;
import delta.games.lotro.character.stats.virtues.VirtuesContributionsMgr;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.VirtueId;

/**
 * Test for the character stats computer.
 * @author DAM
 */
public class TestCharacterStatsComputer
{
  private Character buildCharacter()
  {
    Character c=new Character();
    c.setName("Giswald");
    c.setRace(Race.MAN);
    c.setLevel(100);
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
    tomes.setTomeRank(STAT.MIGHT,1);
    tomes.setTomeRank(STAT.FATE,2);
    // TODO Racial traits
    // TODO legendary items
    LegendaryItem weapon=buildWeapon();
    c.setMainLI(weapon);
    LegendaryItem classItem=buildRune();
    c.setClassLI(classItem);
    // TODO Equipment

    CharacterXMLWriter w=new CharacterXMLWriter();
    w.write(new File("giswald.xml"),c,EncodingNames.UTF_8);
    return c;
  }

  private LegendaryItem buildWeapon()
  {
    LegendaryItem weapon=new LegendaryItem();
    weapon.setItemType("Reshaped Champion's Sword of the First Age");
    weapon.setName("Ost Magol II");
    weapon.setCrafterName("Giswald");
    // Passives
    //BasicStatsSet passives=weapon.getPassives();
    // TODO +1% parry chance
    // Title
    LegendaryTitle title=new LegendaryTitle("Potency of Eldar Days III");
    BasicStatsSet titleStats=title.getStats();
    titleStats.setStat(STAT.CRITICAL_RATING,460);
    weapon.setTitle(title);
    // Relics
    {
      // Setting
      Relic setting=new Relic("Westemnet Setting of Endings", RelicType.SETTING, 90, 90);
      BasicStatsSet stats=setting.getStats();
      // TODO 7.5% devastate magnitude
      stats.setStat(STAT.CRITICAL_RATING,1454);
      stats.setStat(STAT.ICMR,182);
      weapon.setSetting(setting);
    }
    {
      // Gem
      Relic gem=new Relic("True Gem of the Wizard's Vale", RelicType.GEM, 75, 75);
      BasicStatsSet stats=gem.getStats();
      stats.setStat(STAT.ICPR,90);
      stats.setStat(STAT.CRITICAL_RATING,1212);
      stats.setStat(STAT.FATE,30);
      weapon.setGem(gem);
    }
    {
      // Rune
      Relic rune=new Relic("Great River Rune of Power", RelicType.RUNE, 75, 75);
      BasicStatsSet stats=rune.getStats();
      stats.setStat(STAT.PHYSICAL_MITIGATION,170);
      stats.setStat(STAT.PHYSICAL_MASTERY,606);
      stats.setStat(STAT.TACTICAL_MASTERY,606);
      stats.setStat(STAT.FATE,27);
      weapon.setRune(rune);
    }
    {
      // Crafted relic
      Relic craftedRelic=new Relic("Westemnet Device of Battle", RelicType.CRAFTED_RELIC, 95, 95);
      BasicStatsSet stats=craftedRelic.getStats();
      stats.setStat(STAT.MIGHT,40);
      stats.setStat(STAT.CRITICAL_RATING,740);
      stats.setStat(STAT.PHYSICAL_MASTERY,740);
      weapon.setCraftedRelic(craftedRelic);
    }
    // Stat legacies
    // TODO
    return weapon;
  }

  private LegendaryItem buildRune()
  {
    LegendaryItem classItem=new LegendaryItem();
    classItem.setItemType("Reshaped Champion's Rune of the First Age");
    classItem.setName("Gondorian Rune II");
    classItem.setCrafterName("Ethell");
    // Passives
    BasicStatsSet passives=classItem.getPassives();
    // TODO -1% Blade line AoE power cost
    // TODO -4% strike skill power cost
    passives.setStat(STAT.INCOMING_HEALING,6300);
    // Title
    // .. none ..
    // Relics
    {
      // Setting
      Relic setting=new Relic("Westemnet Setting of Endings", RelicType.SETTING, 90, 90);
      BasicStatsSet stats=setting.getStats();
      // TODO 7.5% devastate magnitude
      stats.setStat(STAT.CRITICAL_RATING,1454);
      stats.setStat(STAT.ICMR,182);
      classItem.setSetting(setting);
    }
    {
      // Gem
      Relic gem=new Relic("True Gem of the Wizard's Vale", RelicType.GEM, 75, 75);
      BasicStatsSet stats=gem.getStats();
      stats.setStat(STAT.ICPR,90);
      stats.setStat(STAT.CRITICAL_RATING,1212);
      stats.setStat(STAT.FATE,30);
      classItem.setGem(gem);
    }
    {
      // Rune
      Relic rune=new Relic("True Rune of the White Mountains", RelicType.RUNE, 80, 80);
      BasicStatsSet stats=rune.getStats();
      // TODO -2.5% attack duration
      stats.setStat(STAT.PHYSICAL_MASTERY,646);
      stats.setStat(STAT.TACTICAL_MASTERY,646);
      stats.setStat(STAT.AGILITY,35);
      classItem.setRune(rune);
    }
    {
      // Crafted relic
      Relic craftedRelic=new Relic("Westemnet Device of Battle", RelicType.CRAFTED_RELIC, 95, 95);
      BasicStatsSet stats=craftedRelic.getStats();
      stats.setStat(STAT.MIGHT,40);
      stats.setStat(STAT.CRITICAL_RATING,740);
      stats.setStat(STAT.PHYSICAL_MASTERY,740);
      classItem.setCraftedRelic(craftedRelic);
    }
    // Stat legacies
    // TODO
    return classItem;
  }

  private BasicStatsSet getRawVirtuesContribution(VirtuesSet virtues)
  {
    BasicStatsSet ret=new BasicStatsSet();
    VirtuesContributionsMgr virtuesMgr=new VirtuesContributionsMgr();
    for(int i=0;i<VirtuesSet.MAX_VIRTUES;i++)
    {
      VirtueId virtue=virtues.getSelectedVirtue(i);
      if (virtue!=null)
      {
        int rank=virtues.getVirtueRank(virtue);
        BasicStatsSet virtueContrib=virtuesMgr.getContribution(virtue,rank);
        ret.addStats(virtueContrib);
      }
    }
    return ret;
  }

  private void doIt()
  {
    Character c=buildCharacter();
    BaseStatsManager baseStatsMgr=new BaseStatsManager();
    BasicStatsSet baseStats=baseStatsMgr.getBaseStats(c.getCharacterClass(),c.getRace(),c.getLevel());
    BasicStatsSet virtuesStats=getRawVirtuesContribution(c.getVirtues());
    BasicStatsSet tomesStats=new TomesContributionsMgr().getContribution(c.getTomes());
    BasicStatsSet raw=new BasicStatsSet();
    raw.addStats(baseStats);
    raw.addStats(virtuesStats);
    raw.addStats(tomesStats);
    // Legendary Items
    LegendaryItem mainLI=c.getMainLI();
    if (mainLI!=null)
    {
      BasicStatsSet liStats=mainLI.getRawStats();
      raw.addStats(liStats);
    }
    LegendaryItem classLI=c.getClassLI();
    if (classLI!=null)
    {
      BasicStatsSet liStats=classLI.getRawStats();
      raw.addStats(liStats);
    }
    DerivatedStatsContributionsMgr derivedStatsMgr=new DerivatedStatsContributionsMgr();
    BasicStatsSet derivedContrib=derivedStatsMgr.getContribution(c.getCharacterClass(),raw);
    raw.addStats(derivedContrib);
    System.out.println(raw);
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new TestCharacterStatsComputer().doIt();
  }
}
