package delta.games.lotro.character.skills.attack;

import java.io.File;

import org.junit.jupiter.api.Test;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.io.xml.CharacterDataIO;
import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.skills.SkillsDetailsManager;

/**
 * Test for the skill attack damage computer.
 * @author DAM
 */
class SkillAttackComputerTest
{
  private SkillDetails getSkillDetails(int id)
  {
    return SkillsDetailsManager.getInstance().getSkillDetails(id);
  }

  private CharacterData getCharacterData(String filename)
  {
    File from=new File(filename);
    return CharacterDataIO.getCharacterDescription(from);
  }

  @Test
  void test()
  {
    testMinstrel();
  }

  private void testBurglar()
  {
    String filename="burglar.xml";
    //doSkill(1879064332,filename); // ~OK Double-edged Strike ; #1: 5943.338 / 6240 (lack 5% club damage) ; #2: 3359.742 / 3363
    //doSkill(1879384439,filename); // ~OK Throw Knife ; 1484.5181 / 1559 (x1.05 lack 5% club damage??)
    //doSkill(1879263587,filename); // ~OK Coup De Grâce ; #1: 7283.1772 / 7647 (x1.05) ; #2: 4008.4202 / 4011 ; #3: 5443.9365 / 5719 (x1.05)
    doSkill(1879052125,filename); // ~OK Flashing Blades ; #1: 9371.625=>7178.2656 (x1.05) / 7537 ; #2: 4570.784 / 4575 => modifier 268444443 x2!!
  }

  private void testHunter()
  {
    String filename="hunter.xml";
    doSkill(1879218262,filename); // Improved Quick Shot ; 3600.227 should be 3705 (x1,029)
    doSkill(1879064371,filename); // Barbed Arrow ; 6963.619 should be 7167 (x1,029)
    doSkill(1879218255,filename); // Improved Penetrating Shot ; 8395.061 should be 8640 (x1,029)
    doSkill(1879073393,filename); // ~OK Scourging Blow ; #1: 5287.5576 should be 5552 (x1.05) miss 5% dmg axe for dwarves ; # 2 1579 OK
    doSkill(1879186712,filename); // ~OK Improved Dazing Blow ; #1: 2637.989 OK ; #2: 7348.9907 should be 7716 miss 5% dmg axe for dwarves
    doSkill(1879073389,filename); // ~OK Low Cut ; #1: 4553.877 should be 4782 (x1.05) miss 5% dmg axe for dwarves #2: 1349.2766 OK
    doSkill(1879135202,filename); // Improved Swift Bow ; #1: 8605.41 / 8856 ; #2: 7172.792 / 7382 ; #3: 5428.0586 / 5586 (x1.029 for all 3)
    doSkill(1879052499,filename); // Bard's Arrow ; 14488.718 should be 14911 (x1,029)
  }

  private void testMinstrel()
  {
    String filename="minstrel.xml";
    doSkill(1879064186,filename); // Dissonant Strike ; 10726.226 / 10210 (x1.05 too much)
    doSkill(1879064190,filename); // Perfect Ballad  12012.069 should be 13386 (x1,114)
    doSkill(1879221564,filename); // Major Ballad  8653.634 should be 9861 (x1.14)
    doSkill(1879452871,filename); // Minor Ballad - Dissonance 13749.663 should be 15348 (x1,116)
    doSkill(1879217094,filename); // Coda of Fury ; 29566.584 should be 33692 (x1.14)
    doSkill(1879052912,filename); // Cry of the Wizards 21307.857 should be 23229 (x1,09)
    doSkill(1879094939,filename); // Call of the Second Age 18929.824 should be 20378 (x1,076) (x1.019)
    doSkill(1879284233,filename); // Dissonant Piercing Cry 15227.821 should be 16496 (x1.083)
  }

  private void testChampion()
  {
    String filename="champion.xml";
    doSkill(1879064084,filename); // Let Fly
    doSkill(1879064065,filename); // Brutal Strikes 1 weapon (double handed) ; OK lack 5% men sword
    doSkill(1879073019,filename); // Brutal Strikes, 2 handed ; not tested
    doSkill(1879064067,filename); // Blade Storm ; 1 weapon (double handed) ; OK lack 5% men sword
    doSkill(1879073516,filename); // Upper-cut => wrong, uses max damage progression
    doSkill(1879111264,filename); // Rend ; OK lack 5% men sword
    doSkill(1879064077,filename); // Horn of Gondor ; OK lack 5% men sword
    doSkill(1879064073,filename); // Merciful Strike ; OK lack 5% men sword
    doSkill(1879060547,filename); // Battle Frenzy
  }

  private void doSkill(int id, String filename)
  {
    SkillDetails details=getSkillDetails(id);
    System.out.println("\n*****************************");
    System.out.println("Skill ID="+id+", name="+details.getName());
    CharacterData data=getCharacterData(filename);
    CharacterDataForSkills dataForSkills=new CharacterDataForSkills(data);
    SkillAttackComputer c=new SkillAttackComputer(dataForSkills,details);
    int index=1;
    for(SkillAttack attack : details.getAttacks().getAttacks())
    {
      System.out.println("Attack #"+index);
      float damage=c.getAttackDamage(attack,false);
      System.out.println("Damage: "+damage);
      index++;
    }
  }
}
