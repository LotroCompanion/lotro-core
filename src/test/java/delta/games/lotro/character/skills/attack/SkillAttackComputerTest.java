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
    testChampion();
  }

  private void testMinstrel()
  {
    String filename="minstrel.xml";
    //doSkill(1879064186,filename); // Dissonant Strike
    //doSkill(1879064190,filename); // Perfect Ballad  12012 should be 13386 (x1,114)
    //doSkill(1879221564,filename); // Major Ballad  8653 should be 9861 (x1.14)
    //doSkill(1879452871,filename); // Minor Ballad - Dissonance 13749.663 should be 15348 (x1,116)
    //doSkill(1879217094,filename); // Coda of Fury ; 29566.584 should be 33692 (x1.14)
    //doSkill(1879052912,filename); // Cry of the Wizards 21307.857 should be 23229 (x1,09)
    doSkill(1879094939,filename); // Call of the Second Age 18929.824 should be 20378 (x1,076)
    //doSkill(1879284233,filename); // Dissonant Piercing Cry 15227.821 should be 16496 (x1.083)
  }

  private void testChampion()
  {
    String filename="champion.xml";
    //doSkill(1879064084); // Let Fly
    doSkill(1879064065,filename); // Brutal Strikes 1 weapon (double handed) ; OK lack 5% men sword
    doSkill(1879073019,filename); // Brutal Strikes, 2 handed ; not tested
    doSkill(1879064067,filename); // Blade Storm ; 1 weapon (double handed) ; OK lack 5% men sword
    doSkill(1879073516,filename); // Upper-cut => wrong, uses max damage progression
    doSkill(1879111264,filename); // Rend ; OK lack 5% men sword
    doSkill(1879064077,filename); // Horn of Gondor ; OK lack 5% men sword
    doSkill(1879064073,filename); // Merciful Strike ; OK lack 5% men sword
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
