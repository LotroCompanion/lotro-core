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

  private CharacterData getCharacterData()
  {
    File from=new File("current.xml");
    return CharacterDataIO.getCharacterDescription(from);
  }

  @Test
  void test()
  {
    //doSkill(1879064084); // Let Fly
    //doSkill(1879064065); // Brutal Strikes 1 weapon (double handed) ; OK lack 5% men sword
    //doSkill(1879073019); // Brutal Strikes, 2 handed ; not tested
    //doSkill(1879064067); // Blade Storm ; 1 weapon (double handed) ; OK lack 5% men sword
    //doSkill(1879073516); // Upper-cut => wrong, uses max damage progression
    //doSkill(1879111264); // Rend ; OK lack 5% men sword
    //doSkill(1879064077); // Horn of Gondor ; OK lack 5% men sword
    doSkill(1879064073); // Merciful Strike ; OK lack 5% men sword
  }

  private void doSkill(int id)
  {
    SkillDetails details=getSkillDetails(id);
    System.out.println("\n*****************************");
    System.out.println("Skill ID="+id+", name="+details.getName());
    CharacterData data=getCharacterData();
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
