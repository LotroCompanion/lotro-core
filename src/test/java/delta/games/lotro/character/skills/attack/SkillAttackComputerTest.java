package delta.games.lotro.character.skills.attack;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassSkill;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.WellKnownCharacterClassKeys;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.io.xml.CharacterDataIO;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.skills.SkillDisplay;
import delta.games.lotro.character.skills.SkillsDetailsManager;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;
import delta.games.lotro.character.status.traits.TraitsStatus;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.IdentifiableComparator;

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
    /*
    testWarden();
    testBurglar();
    testCaptain();
    testChampion();
    testGuardian();
    testHunter();
    testLoremaster();
    testMinstrel();
    */
    testRunekeeper();
    /*
    testMariner();
    testPip();
    testMinstrel();
    */
  }

  void testClassSkills(String key, CharacterData data)
  {
    ClassDescription charClass=ClassesManager.getInstance().getCharacterClassByKey(key);
    for(ClassSkill classSkill : charClass.getSkills())
    {
      SkillDescription skill=classSkill.getSkill();
      doSkill(skill.getIdentifier(),data);
    }
  }

  void testClass(String key, CharacterData data)
  {
    for(SkillDescription skill : getSkills(data))
    {
      doSkill(skill.getIdentifier(),data);
    }
  }

  private List<SkillDescription> getSkills(CharacterData data)
  {
    List<SkillDescription> ret=new ArrayList<SkillDescription>();
    // Skills from class
    ClassDescription charClass=data.getCharacterClass();
    int charLevel=data.getLevel();
    for(ClassSkill classSkill : charClass.getSkills())
    {
      int requiredLevel=classSkill.getRequiredLevel();
      if (requiredLevel<charLevel)
      {
        ret.add(classSkill.getSkill());
      }
    }
    // Skills from traits
    TraitsStatus traitsStatus=data.getTraits();
    TraitTreeStatus traitTreeStatus=traitsStatus.getTraitTreeStatus();
    TraitTree tree=traitTreeStatus.getTraitTree();
    List<TraitDescription> traits=tree.getAllTraits();
    for(TraitDescription trait : traits)
    {
      //Integer rank=traitTreeStatus.getRankForTrait(trait.getIdentifier());
      //if ((rank!=null) && (rank.intValue()>0))
      {
        List<SkillDescription> traitSkills=trait.getSkills();
        ret.addAll(traitSkills);
      }
    }
    Collections.sort(ret,new IdentifiableComparator<SkillDescription>());
    return ret;
  }

  void testWarden()
  {
    CharacterData data=getCharacterData("warden.xml");
    testClass(WellKnownCharacterClassKeys.WARDEN,data);
  }

  void testMariner()
  {
    CharacterData data=getCharacterData("mariner.xml");
    testClass(WellKnownCharacterClassKeys.CORSAIR,data);
  }

  void testRunekeeper()
  {
    // No tree
    /*
    CharacterData data=getCharacterData("rk no tree.xml"); // OK
    doSkill(1879110792,data); // Shocking Touch 3783.6074 / 3784 OK
    doSkill(1879107947,data); // Fiery Ridicule 5611.707 / 5612 OK
    doSkill(1879109293,data); // Scribe's Spark 8989.852 / 8990 OK
    doSkill(1879109291,data); // Essence of Storm 11686.806 / 11687 OK
    */
    // Tree red + 5 Deliberate Address
    /*
    CharacterData data=getCharacterData("rk no tree 5DA.xml"); // OK
    doSkill(1879110792,data); // Shocking Touch 3972.7869 / 3973 OK
    doSkill(1879107947,data); // Fiery Ridicule 6765.2236 / 6765 OK
    doSkill(1879109293,data); // Scribe's Spark 9439.342 / 9439 OK
    doSkill(1879109291,data); // Essence of Storm 12271.145 / 12271 OK
    doSkill(1879109121,data); // Essence of Winter 10817.778 / 10818 OK
    */
    // Standard
    CharacterData data=getCharacterData("runekeeper.xml"); // OK
    //doSkill(1879219450,data); // Improved Rousing Words
    //testClass(WellKnownCharacterClassKeys.RUNE_KEEPER,data);
    /*
    doSkill(1879109290, data); // Prelude to Hope OK
    doSkill(1879109286, data); // Epic for the Ages OK
    doSkill(1879109288, data); // Mending Verse OK
    doSkill(1879461530, data); // Self-motivation ~OK (!=1)
    */
    //doSkill(1879109119, data); // Flurry of Words (do not use qualifier*)
    doSkill(1879199033, data); // Essay of Fire (use qualifier*)
    doSkill(1879107945, data); // Scathing Mockery (use qualifier*)
    doSkill(1879107949, data); // Smouldering Wrath
    doSkill(1879272247, data); // Combustion
    /*
    doSkill(1879110792,data); // Shocking Touch 3965.6267 / 3966 OK
    doSkill(1879221506,data); // Fiery Ridicule 6535.1904 / 6535 OK
    doSkill(1879109293,data); // Scribe's Spark 9422.329 / 9423 ~OK
    doSkill(1879109291,data); // Essence of Storm 12249.028 / 12249 OK
    */
  }

  void testLoremaster() // ~OK
  {
    CharacterData data=getCharacterData("loremaster.xml");
    testClass(WellKnownCharacterClassKeys.LORE_MASTER,data);
    // (Fire) Burning Embers: 4714.4585 / 4715 ~OK
    // (Frost) Staff Strike: #1 7382.149 / 7382 ; #2 2036.4579 / 2036 OK
    // (Lightning) Wizard's Spark: 5235.174 / 5235 OK
    // (Frost) Wizard's Frost: 33451.1545 / 3451 OK
    // (Fire) Wizard's Fire: 3272.5842 / 3273 OK
    // (Fire) Cracked Earth: 8996.994 / 8997 OK
    // (Frost) Gust of Wind: 3075.8953 / 3076 OK
    // Light of the Rising Dawn: 615.1791 / 615 OK
    // Minor Pet Command: 615.1791 / 615 OK
  }

  void testCaptain() // ~OK
  {
    CharacterData data=getCharacterData("captain.xml");
    testClass(WellKnownCharacterClassKeys.CAPTAIN,data);
    // Battle-shout: 5913.9556 / 5914 OK
    // Devastating Blow: 18268.68 / 18269 OK
    // Routing Cry: 4788.183 / 4788 OK
    // Pressing Attack: #1 5811.407 / 5812 ~OK, #2 6129.218 / 6129 OK
    // Blade of Elendil: 9189.601 / 9190 OK
    // Grave Wound: 11040.234 / 11041 ~OK
    // Defensive Strike: 3896.7153 / 3897 OK
  }

  void testGuardian() // ~OK
  {
    CharacterData data=getCharacterData("guardian.xml");
    testClass(WellKnownCharacterClassKeys.GUARDIAN,data);
    // Guardian's Ward: 9505.975 (9514)
    // Retaliation: 10218.923 (10228)
    // Sweeping Cut: 4293.021 (4297), 3679.7324 (3683)
    // Whirling Retaliation: 13005.553 (13017)
    // Let Fly: 3370.5676 (5274)
    // Vexing Blow: 8773.861 (8781)
    // Turn the Tables: 7424.0215 (7430)
    // Stagger: 5519.5986 (5524)
    // Stamp: 7577.416 (7584)
    // Improved Sting: 6891.8315 (6898)
    // Fray the Edge: -
  }

  void testBurglar()
  {
    String filename="burglar.xml";
    CharacterData data=getCharacterData(filename);
    doSkill(1879064332,data); // Double-edged Strike ; #1: 6240.5737 / ~6240 OK ; #2: 3359.7793 / 3363 ~OK
    doSkill(1879384439,data); // Throw Knife ; 1558.7612 / 1559 OK
    doSkill(1879263587,data); // Coup De Grâce ; #1: 7647.4214 / 7647 OK ; #2: 4011.8667 / 4011 ~OK ; #3: 5719.269 / 5719 ~OK
    doSkill(1879052125,data); // Flashing Blades ; #1: 7537.2627 / 7537 OK ; #2: 4570.835 / 4575 ~OK
  }

  void testHunter()
  {
    String filename="hunter.xml"; // OK
    CharacterData data=getCharacterData(filename);
    /*
    doSkill(1879218262,data); // Improved Quick Shot ; 3705.0732 / 3705 OK
    doSkill(1879064371,data); // Barbed Arrow ; 7165.932 / 7167 OK
    doSkill(1879218255,data); // Improved Penetrating Shot ; 8639.543 / 8640 OK
    doSkill(1879073393,data); // Scourging Blow ; #1: 5551.878 / 5552 OK ; # 2 1579.2509 / 1579 OK
    doSkill(1879186712,data); // Improved Dazing Blow ; #1: 2637.9622 / 2638 OK ; #2: 7716.361 / 7716 OK
    doSkill(1879073389,data); // Low Cut ; #1: 4781.571 / 4782 OK #2: 1349.2628 / 1349 OK
    doSkill(1879135202,data); // Improved Swift Bow ; #1: 8856.019 / 8856 OK ; #2: 7381.6787 / 7382 OK ; #3: 5586.1353 / 5586 OK
    doSkill(1879052499,data); // Bard's Arrow ; 14910.659 / 14911 OK
    */
    testClass(WellKnownCharacterClassKeys.HUNTER,data);
  }

  void testMinstrel()
  {
    CharacterData data=getCharacterData("mini_noLIs.xml");
    doSkill(1879218616,data); // Raise My Spirit
    doSkill(1879218619,data); // Bolster My Courage
    doSkill(1879096321,data); // Chord of Salvation
    //testClass(WellKnownCharacterClassKeys.MINSTREL,data);
    /*
    doSkill(1879064186,data); // Dissonant Strike ; 10485.629 / 10219 (x1.026 too much)
    doSkill(1879064190,data); // Perfect Ballad 13388.382 / 13389 OK
    doSkill(1879221564,data); // Major Ballad 9862.915 / 9863 OK
    doSkill(1879452871,data); // Minor Ballad - Dissonance 15350.472 / 15351 OK
    doSkill(1879217094,data); // Coda of Fury ; 33698.29 / 33700 OK
    doSkill(1879052912,data); // Cry of the Wizards 23233.809 / 23235 OK
    doSkill(1879094939,data); // Call of the Second Age 20381.605 / 20382 OK
    doSkill(1879284233,data); // Dissonant Piercing Cry 16499.396 / 16500 OK
    */
  }

  void testChampion()
  {
    String filename="champion.xml";
    CharacterData data=getCharacterData(filename);
    /*
    doSkill(1879064084,data); // Let Fly : 6946.133 / 6946 OK
    doSkill(1879064065,data); // Brutal Strikes 1 weapon (double handed) ; #1 15886.874 / 15887 OK ; #2 12444.718 / 12445 OK ; #3 10326.468 / 10326 OK
    doSkill(1879073019,data); // Brutal Strikes, 2 handed ; not tested
    doSkill(1879064067,data); // Blade Storm ; 1 weapon (double handed) ; 15044.505 / 15044 OK
    doSkill(1879073516,data); // Upper-cut => wrong, uses max damage progression
    doSkill(1879111264,data); // Rend ; 11074.086 / 11074 OK
    doSkill(1879064077,data); // Horn of Gondor ; 14679.4375 / 14680 OK
    doSkill(1879064073,data); // Merciful Strike ; 11214.265 / 11214 OK
    doSkill(1879060547,data); // Battle Frenzy
    */
    testClass(WellKnownCharacterClassKeys.CHAMPION,data);
  }

  void testPip()
  {
    String filename="champion.xml";
    CharacterData data=getCharacterData(filename);
    doSkill(1879090228,data); // Rampage
    doSkill(1879139378,data); // Fire Under The Mountain
    doSkill(1879319277,data); // Bear-form
    doSkill(1879319928,data); // Positive Thinking
    doSkill(1879319929,data); // Steel Skin
    doSkill(1879319930,data); // Ride for Ruin
    doSkill(1879319935,data); // Sacrifice
  }

  private void doSkill(int id, CharacterData data)
  {
    SkillDetails details=getSkillDetails(id);
    //System.out.println("\n*****************************");
    //System.out.println("Skill ID="+id+", name="+details.getName());
    CharacterDataForSkills dataForSkills=new CharacterDataForSkills(data);
    SkillDescription skill=SkillsManager.getInstance().getSkill(id);
    SkillDisplay d=new SkillDisplay(dataForSkills,skill,details);
    //d.doPip();
    System.out.println(d.getText());
    /*
    SkillAttackComputer c=new SkillAttackComputer(dataForSkills,details);
    int index=1;
    for(SkillAttack attack : details.getAttacks().getAttacks())
    {
      System.out.println("Attack #"+index);
      float damage=c.getAttackDamage(attack,false);
      System.out.println("Damage: "+damage);
      index++;
    }
    */
  }
}
