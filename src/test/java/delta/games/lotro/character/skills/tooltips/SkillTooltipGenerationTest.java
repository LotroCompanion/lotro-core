package delta.games.lotro.character.skills.tooltips;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import delta.common.utils.files.TextFileWriter;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.io.xml.CharacterDataIO;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.skills.SkillDisplay;
import delta.games.lotro.character.skills.SkillsDetailsManager;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;

/**
 * Test for the skill tooltip generation.
 * @author DAM
 */
class SkillTooltipGenerationTest
{
  private SkillDetails getSkillDetails(int id)
  {
    return SkillsDetailsManager.getInstance().getSkillDetails(id);
  }

  private CharacterData getCharacterData(String filename)
  {
    File from=new File("src/test/resources/characters",filename);
    return CharacterDataIO.getCharacterDescription(from);
  }

  @Test
  void test()
  {
    testGuardian();
    testRunekeeper();
    testLoremaster();
    testHunter();
    testChampion();
    testMinstrel();
    testCaptain();
    testBurglar();
  }

  private void doTest(String characterFile, int[] skillIDs)
  {
    List<String> allLines=new ArrayList<String>();
    CharacterData data=getCharacterData(characterFile);
    for(int skillID : skillIDs)
    {
      allLines.add("*****************************");
      List<String> lines=doSkill(skillID, data);
      allLines.addAll(lines);
    }
    String name=characterFile.replace(".xml",".txt");
    File toDir=new File("src/test/resources/skills/tooltips/expectedResults");
    File toFile=new File(toDir,name);
    TextFileWriter writer=new TextFileWriter(toFile);
    writer.start();
    for(String line : allLines)
    {
      writer.writeNextLine(line);
    }
    writer.terminate();
  }

  private void testRunekeeper()
  {
    int[] skills={
      // Page 1
      1879110792, // Shocking Touch
      1879199033, // Essay of Fire (use qualifier*)
      1879109291, // Essence of Storm
      1879109292, // Ceaseless Argument
      1879272269, // Fulgurite Rune-stone
      1879107949, // Smouldering Wrath
      1879109616, // Writ of Fire
      1879107945, // Scathing Mockery (use qualifier*)
      1879109122, // Chilling Rhetoric
      1879221506, // Fiery Ridicule
      1879233275, // Essence of Flame
      1879146741, // Distracting Flame
      1879109293, // Scribe's Spark
      1879153049, // Epic Conclusion
      1879272247, // Combustion
      1879109294, // Shocking Words
      // Page 2
      1879109121, // Essence of Winter
      1879109288, // Mending Verse
      1879461530, // Self-motivation
      1879109119, // Flurry of Words
      1879109290, // Prelude to Hope
      1879109286, // Epic for the Ages
      1879244911, // Armour of The Elements
    };
    doTest("runekeeper.xml",skills);
  }

  private void testLoremaster()
  {
    int[] skills={
      1879064244, // Burning Embers
      1879111679, // Wizard's Fire
      1879488018, // Wizard's Spark
      1879064247, // Gust of Wind
      1879487481, // Minor Pet Command
      1879060758, // Power of Knowledge
      1879490501, // Staff Strike
      1879060803, // Cracked Earth
      1879060811, // Wisdom of the Council
      1879064258, // Light of the Rising Dawn
      1879455086, // Wizard's Frost
      1879060771, // Light of Hope
    };
    doTest("loremaster.xml",skills);
  }

  private void testGuardian()
  {
    int[] skills={
      1879060582, // Guardian's Ward
      1879110113, // Improved Sting
      1879064292, // Whirling Retaliation
      1879064286, // Sweeping Cut
      1879064288, // Retaliation
      1879102645, // Fray the Edge
      1879060621, // Vexing Blow
      1879064084, // Let Fly
      1879064296, // Turn the Tables
      1879064295, // Stamp
      1879102643, // Stagger
    };
    doTest("guardian.xml",skills);
  }

  private void testHunter()
  {
    int[] skills={
      1879218262, // Improved Quick Shot
      1879135202, // Improved Swift Bow
      1879064379, // Blindside
      1879064371, // Barbed Arrow
      1879052499, // Bard's Arrow
      1879089875, // Heart Seeker
      1879218255, // Improved Penetrating Shot
      1879073389, // Low Cut
      1879270510, // Barrage
      1879073393, // Scourging Blow
      1879186701, // Blood Arrow
      1879064374, // Rain of Arrows
      1879186712, // Improved Dazing Blow
      1879135205, // Improved Merciful Shot
    };
    doTest("hunter.xml",skills);
  }

  private void testChampion()
  {
    int[] skills={
      1879064072, // Champion's Challenge
      1879064060, // Wild Attack
      1879060547, // Battle Frenzy
      1879073516, // Upper-cut
      1879212605, // Swift Blade
      1879111264, // Rend
      1879064078, // Hamstring
      1879243824, // Horn of Champions
      1879064076, // Clobber
      1879064065, // Brutal Strikes 1 weapon (double handed)
      1879064084, // Let Fly
      1879064073, // Merciful Strike
      1879064067, // Blade Storm
      1879064077, // Horn of Gondor
      // Page 2
      1879271239, // Fury of Blades
      1879059893, // Controlled Burn
      1879111438, // Blood Rage
      1879059881, // Raging Blade
      1879064061, // Blade Wall
      1879290949, // Feral Strikes
      1879111436, // Great Cleave
      1879216336, // True Heroics
      1879102586, // Second Wind
      1879064066, // Exchange of Blows
      1879059869, // Fight On
      1879186727 // Fear Nothing!
    };
    doTest("champion.xml",skills);
  }

  private void testMinstrel()
  {
    int[] skills={
      // Page 1
      1879217094, // Coda of Fury
      1879064192, // Lesser Anthem III - Composure (may be 1879449576)
      1879060863, // Greater Anthem II - War (may be 1879449575)
      1879221564, // Major Ballad
      1879284233, // Dissonant Piercing Cry
      1879064191, // Greater Anthem III - Prowess (may be 1879449577)
      1879052912, // Cry of the Wizards
      1879110224, // Call to Fate
      1879221557, // Improved Cry of the Chorus (Dissonance)
      1879452871, // Minor Ballad - Dissonance
      // Page 2
      1879205049, // Lesser Anthem II - Dissonance (may be 1879449574)
      1879182841, // Song of the Hammerhand
      1879060868, // Cry of the Valar
      1879064190, // Perfect Ballad
      1879218616, // Raise My Spirit
      1879094938, // Call of Oromë
      1879094942, // Gift of the Hammerhand
      1879218619, // Bolster My Courage
      1879094936, // Timeless Echoes of Battle
      1879094939, // Call of the Second Age
      1879218623, // Chord of My Salvation
      1879064186, // Dissonant Strike
      1879064203, // Story of Courage
      1879064194, // Invocation of Elbereth
      // Page 3
      1879064208, // Still As Death
      1879218437, // Improved Song of Distraction
      1879073495, // Hobbit-silence
      1879096322, // Irresistible Melody
      1879286666, // Spirit of Freedom
      1879096837, // Scribe Stance
      1879099270, // Mentor: Bagpipes
      1879218436, // Improved Enlivening Grace
      1879073486, // Hobbit-resilience
      1879052881 // Rally!
    };
    doTest("minstrel.xml",skills);
  }

  private void testCaptain()
  {
    int[] skills={
      // Page 1
      1879060327, // Battle-shout
      1879060431, // Devastating Blow
      1879064144, // Pressing Attack
      1879389418, // Defensive Strike
      1879053062, // Oathbreaker's Shame
      1879458531, // Gallant Display
      1879091728, // Withdraw
      1879091730, // Time of Need
      1879272830, // Improved Sure Strike
      1879064143, // Noble Mark
      1879060321, // Routing Cry
      1879091726, // Kick
      // Page 2
      1879092634, // Blade of Elendil
      1879060303, // Rallying Cry
      1879064141, // Telling Mark
      1879287459, // Cleanse Corruption
      1879073537, // Strength of Morale
      1879060250, // Call to Arms: Herald of Victory
      1879113611, // Shadow's Lament
      1879060377, // Muster Courage
      1879064142, // Make Haste
      1879215869, // Inspire (Blade-brother)
      1879064145, // Words of Courage
      1879073530, // Duty-bound
      1879301903, // Improved Standard of War
      1879279086, // Improved Motivating Speech
      // Page 3
      1879242893, // Inspiriting Call
      1879060293, // Escape from Darkness
      1879064154, // Command Respect
      1879215871, // Blade-brother
      1879234184, // Festive Azure Horse
    };
    doTest("captain.xml",skills);
  }

  private void testBurglar()
  {
    int[] skills={
      // Page 1
      1879112560, // Improved Feint Attack
      1879102532, // Knives Out
      1879052125, // Flashing Blades
      1879064332, // Double-edged Strike
      1879060961, // Aim
      1879263587, // Coup De Grâce
      1879061010, // Sneak
      1879064329, // Cunning Attack
      1879064337, // Reveal Weakness // Ambiguous
      1879064330, // Provoke
      1879064331, // Gambler's Advantage
      1879245711, // Improved Addle
      // Page 2
      1879064328, // Surprise Strike
      1879384439, // Throw Knife
      1879064339, // Touch and Go
      1879102534, // Practical Joke
      1879064338, // Ready and Able
      1879269660, // Stun Dust
      1879060947, // Subtle Stab
      1879064340, // Find Footing
      1879064333, // Exploit Opening
      1879073486, // Hobbit-resilience
      1879176356, // Burglar's Antidote
      1879212933, // Improved Riddle
      1879287436, // Purge Corruption
      1879064327, // Trip
      1879073470, // Throw Stone
      1879073491, // Hobbit-stealth
      // Page 3
      1879064342, // Burgle
      1879136770, // Improved Hide in Plain Sight
      1879064341, // Track Treasure
      1879073480, // Return to the Shire
      1879060981, // Diversion
      1879102536, // Share The Fun
      1879204157, // Safe Fall
      1879102530, // Contact Pedlar
      1879233861, // War-steed: Light
      1879062906, // Track Wood
      1879062867, // Track Mines
    };
    doTest("burglar.xml",skills);
  }

  private List<String> doSkill(int id, CharacterData data)
  {
    SkillDetails details=getSkillDetails(id);
    CharacterDataForSkills dataForSkills=new CharacterDataForSkills(data);
    SkillDescription skill=SkillsManager.getInstance().getSkill(id);
    SkillDisplay d=new SkillDisplay(dataForSkills,skill,details);
    return d.getLines();
  }
}
