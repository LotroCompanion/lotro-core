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
  }

  private void doTest(String characterFile, int[] skillIDs)
  {
    List<String> allLines=new ArrayList<String>();
    CharacterData data=getCharacterData(characterFile);
    for(int skillID : skillIDs)
    {
      allLines.add("*****************************");
      List<String> lines= doSkill(skillID, data);
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

  private List<String> doSkill(int id, CharacterData data)
  {
    SkillDetails details=getSkillDetails(id);
    CharacterDataForSkills dataForSkills=new CharacterDataForSkills(data);
    SkillDescription skill=SkillsManager.getInstance().getSkill(id);
    SkillDisplay d=new SkillDisplay(dataForSkills,skill,details);
    return d.getLines();
  }
}
