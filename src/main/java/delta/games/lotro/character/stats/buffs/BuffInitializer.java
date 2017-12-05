package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.stats.Slice;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Initializes a buff registry.
 * @author DAM
 */
public class BuffInitializer
{
  /**
   * Generic buffs (for all characters).
   */
  private static final String GENERIC="Generic";
  /**
   * Racial buffs.
   */
  private static final String RACIAL="Racial";
  /**
   * Class buffs.
   */
  private static final String CLASS="Class";
  /**
   * Red tree buffs.
   */
  private static final String RED_TREE="Red Tree";
  /**
   * Yellow tree buffs.
   */
  private static final String YELLOW_TREE="Yellow Tree";
  /**
   * Blue tree buffs.
   */
  private static final String BLUE_TREE="Blue Tree";

  /**
   * Init buffs.
   * @param registry Registry to use.
   */
  public void initBuffs(BuffRegistry registry)
  {
    initSharedBuffs(registry);
    initRacialBuffs(registry);
    initCaptainBuffs(registry);
    initChampionBuffs(registry);
    initGuardianBuffs(registry);
    initHunterBuffs(registry);
    initMinstrelBuffs(registry);
    initRuneKeeperBuffs(registry);
  }

  private void initSharedBuffs(BuffRegistry registry)
  {
    // - Hope
    {
      Buff hope=new Buff("HOPE", GENERIC, "Hope");
      hope.setIcon("Hope-icon");
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=10;tier++)
      {
        buff.addTier(tier,buildBasicSet(STAT.HOPE,tier));
      }
      hope.setImpl(buff);
      registry.registerBuff(hope);
    }
  }

  private void initRacialBuffs(BuffRegistry registry)
  {
    // Man
    // - Balance of Man
    {
      Buff bom=new Buff("BALANCE_OF_MAN", RACIAL, "Balance of Man");
      bom.setIcon("Balance_of_Man-icon");
      bom.setRequiredRace(Race.MAN);
      bom.setImpl(new BalanceOfMan());
      registry.registerBuff(bom);
    }
    // - Man of the Fourth Age
    {
      Buff manOfFourthAge=new Buff("MAN_OF_THE_FOURTH_AGE", RACIAL, "Man of the Fourth Age");
      manOfFourthAge.setIcon("Man_of_the_Fourth_Age-icon");
      manOfFourthAge.setRequiredRace(Race.MAN);
      RacialStatBuff buff=new RacialStatBuff(STAT.WILL);
      manOfFourthAge.setImpl(buff);
      registry.registerBuff(manOfFourthAge);
    }
    // Dwarf
    // - Shield Browler
    {
      Buff shieldBrowler=new Buff("SHIELD_BRAWLER", RACIAL, "Shield Brawler");
      shieldBrowler.setIcon("Shield_Brawler-icon");
      shieldBrowler.setRequiredRace(Race.DWARF);
      shieldBrowler.setImpl(new ShieldBrawler());
      registry.registerBuff(shieldBrowler);
    }
    // - Fateful Dwarf
    {
      Buff fatefulDwarf=new Buff("FATEFUL_DWARF", RACIAL, "Fateful Dwarf");
      fatefulDwarf.setIcon("Fateful_Dwarf-icon");
      fatefulDwarf.setRequiredRace(Race.DWARF);
      RacialStatBuff buff=new RacialStatBuff(STAT.FATE);
      fatefulDwarf.setImpl(buff);
      registry.registerBuff(fatefulDwarf);
    }
    // Elf
    // - Friend of Man
    {
      Buff friendOfMan=new Buff("FRIEND_OF_MAN", RACIAL, "Friend of Man");
      friendOfMan.setIcon("Friend_of_Man-icon");
      friendOfMan.setRequiredRace(Race.ELF);
      RacialStatBuff buff=new RacialStatBuff(STAT.FATE);
      friendOfMan.setImpl(buff);
      registry.registerBuff(friendOfMan);
    }
    // Beorning
    // - Emissary
    {
      Buff emissary=new Buff("EMISSARY", RACIAL, "Emissary");
      emissary.setIcon("Emissary_(Beorning_Trait)-icon");
      emissary.setRequiredRace(Race.BEORNING);
      RacialStatBuff buff=new RacialStatBuff(STAT.FATE);
      emissary.setImpl(buff);
      registry.registerBuff(emissary);
    }
    // TODO - Natural Diet: 1% Disease Resistance
    // Hobbit
    // - Hobbit-Stature
    {
      Buff hobbitStature=new Buff("HOBBIT_STATURE", RACIAL, "Hobbit-Stature");
      hobbitStature.setIcon("Hobbit-stature-icon");
      hobbitStature.setRequiredRace(Race.HOBBIT);
      RacialStatBuff buff=new RacialStatBuff(STAT.MIGHT);
      hobbitStature.setImpl(buff);
      registry.registerBuff(hobbitStature);
    }
    // High Elf
    // - Those Who Remain
    {
      Buff thoseWhoRemain=new Buff("THOSE_WHO_REMAIN", RACIAL, "Those Who Remain");
      thoseWhoRemain.setIcon("Those_who_Remain-icon");
      thoseWhoRemain.setRequiredRace(Race.HIGH_ELF);
      RacialStatBuff buff=new RacialStatBuff(STAT.WILL);
      thoseWhoRemain.setImpl(buff);
      registry.registerBuff(thoseWhoRemain);
    }
    // - Grace of the Firstborn: +10% Out of Combat Run Speed
    // - Blade Dancer: +2.5% One-handed Sword, +2.5% Two-handed Sword
    // - Virtuous High Elf: +1 Wisdom, Confidence, Justice
    // - Enmity of Darkness: +5% Light-type damage
    // - Wrath of the Firstborn: +5% damage (fellowship, 10s)
    // - Grace of Valinor: Rez target with 50% morale, 40% power
    // - Glory of the first age: 5s stun
    // - Travel to Caras Galadhon in Lothlorien
  }

  private void initCaptainBuffs(BuffRegistry registry)
  {
    // Captain buffs
    // - In Defence of Middle Earth
    {
      Buff idome=new Buff("IN_DEFENCE_OF_MIDDLE_EARTH", CLASS, "In Defence of Middle-Earth");
      idome.setIcon("In_Defence_of_Middle-earth-icon");
      idome.setRequiredClass(CharacterClass.CAPTAIN);
      idome.setImpl(new InDefenceOfMiddleEarth());
      registry.registerBuff(idome);
    }
    // - Motivated
    {
      Buff motivated=new Buff("MOTIVATED", CLASS, "Motivated");
      motivated.setIcon("Motivating_Speech-icon");
      motivated.setRequiredClass(CharacterClass.CAPTAIN);
      motivated.setImpl(new Motivated());
      registry.registerBuff(motivated);
    }

    // Red tree
    // - Arterial Strikes
    {
      Buff arterialStrikes=new Buff("ARTERIAL_STRIKES", RED_TREE, "Arterial Strikes");
      arterialStrikes.setIcon("Arterial_Strikes-icon");
      arterialStrikes.setRequiredClass(CharacterClass.CAPTAIN);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=4;tier++)
      {
        buff.addTier(tier,buildBasicSet(STAT.CRITICAL_MELEE_PERCENTAGE,tier));
      }
      arterialStrikes.setImpl(buff);
      registry.registerBuff(arterialStrikes);
    }
    // - Martial Prowess
    {
      Buff martialProwess=new Buff("MARTIAL_PROWESS", RED_TREE, "Martial Prowess");
      martialProwess.setIcon("Martial_Prowess-icon");
      martialProwess.setRequiredClass(CharacterClass.CAPTAIN);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=3;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.MELEE_DAMAGE_PERCENTAGE,new FixedDecimalsInteger(tier));
        //ret.addStat(STAT.ATTACK_SPEED,new FixedDecimalsInteger(5*tier));
        buff.addTier(tier,stats);
      }
      martialProwess.setImpl(buff);
      registry.registerBuff(martialProwess);
    }
    // - Steeled resolve
    {
      Buff steeledResolve=new Buff("STEELED_RESOLVE", YELLOW_TREE, "Steeled Resolve");
      steeledResolve.setIcon("Steeled_Resolve-icon");
      steeledResolve.setRequiredClass(CharacterClass.CAPTAIN);
      steeledResolve.setImpl(new SteeledResolve());
      registry.registerBuff(steeledResolve);
    }
  }

  private void initChampionBuffs(BuffRegistry registry)
  {
    // Blue tree
    // TODO
    // Red tree
    // - Critical Chance Increase
    {
      Buff critChanceIncrease=new Buff("CRIT_CHANCE_INCREASE", RED_TREE, "Critical Chance Increase");
      critChanceIncrease.setIcon("Critical_Chance_Increase-icon");
      critChanceIncrease.setRequiredClass(CharacterClass.CHAMPION);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int i=1;i<=5;i++)
      {
        buff.addTier(i,buildBasicSet(STAT.CRITICAL_MELEE_PERCENTAGE,i));
      }
      critChanceIncrease.setImpl(buff);
      registry.registerBuff(critChanceIncrease);
    }
    // TODO
    // Yellow tree
    // - Might Increase
    {
      Buff mightIncrease=new Buff("MIGHT_INCREASE", YELLOW_TREE, "Might Increase");
      mightIncrease.setIcon("Might_Increase-icon");
      mightIncrease.setRequiredClass(CharacterClass.CHAMPION);

      float[] sliceCounts=new float[]{2,2.4f,3,3.2f,4};
      Formula formula=new Formula()
      {
        public float compute(int level, float sliceCount)
        {
          return (float)Slice.getBaseStat(level,sliceCount);
        }
      };
      FormulaBasedBuff buff=new FormulaBasedBuff(sliceCounts,STAT.MIGHT,formula);
      mightIncrease.setImpl(buff);
      registry.registerBuff(mightIncrease);
    }
    // - Finesse Increase
    {
      Buff finesseIncrease=new Buff("FINESSE_INCREASE", YELLOW_TREE, "Finesse Increase");
      finesseIncrease.setIcon("Finesse_Increase-icon");
      finesseIncrease.setRequiredClass(CharacterClass.CHAMPION);

      float[] sliceCounts=new float[]{0.4f,0.8f,1.2f,1.6f,2};
      Formula formula=new Formula()
      {
        public float compute(int level, float sliceCount)
        {
          return Slice.getFinesse(level,sliceCount);
        }
      };
      FormulaBasedBuff buff=new FormulaBasedBuff(sliceCounts,STAT.FINESSE,formula);
      finesseIncrease.setImpl(buff);
      registry.registerBuff(finesseIncrease);
    }
  }

  private void initGuardianBuffs(BuffRegistry registry)
  {
    // Guardian buffs
    // Red tree
    // - Overpower
    {
      Buff overpower=new Buff("OVERPOWER", RED_TREE, "Overpower");
      overpower.setIcon("Overpower-icon");
      overpower.setRequiredClass(CharacterClass.GUARDIAN);
      BasicStatsSet stats=new BasicStatsSet();
      stats.addStat(STAT.MELEE_DAMAGE_PERCENTAGE,new FixedDecimalsInteger(5));
      stats.addStat(STAT.PARTIAL_PARRY_PERCENTAGE,new FixedDecimalsInteger(2.5f));
      stats.addStat(STAT.PARTIAL_PARRY_MITIGATION_PERCENTAGE,new FixedDecimalsInteger(2.5f));
      SimpleStatsBuff buff=new SimpleStatsBuff(stats);
      // TODO 10% crit damage x
      overpower.setImpl(buff);
      registry.registerBuff(overpower);
    }
    // - Heavy Blows
    {
      Buff heavyBlows=new Buff("HEAVY_BLOWS", RED_TREE, "Heavy Blows");
      heavyBlows.setIcon("Heavy_Blows-icon");
      heavyBlows.setRequiredClass(CharacterClass.GUARDIAN);
      BasicStatsSet stats=new BasicStatsSet();
      stats.addStat(STAT.MELEE_DAMAGE_PERCENTAGE,new FixedDecimalsInteger(5));
      SimpleStatsBuff buff=new SimpleStatsBuff(stats);
      // TODO 10% crit damage x
      heavyBlows.setImpl(buff);
      registry.registerBuff(heavyBlows);
    }
    // - Skilled Deflection
    {
      Buff skilledDeflection=new Buff("SKILLED_DEFLECTION", RED_TREE, "Skilled Deflection");
      skilledDeflection.setIcon("Skilled_Deflection-icon");
      skilledDeflection.setRequiredClass(CharacterClass.GUARDIAN);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=5;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.PARRY_PERCENTAGE,new FixedDecimalsInteger(tier));
        buff.addTier(tier,stats);
      }
      skilledDeflection.setImpl(buff);
      registry.registerBuff(skilledDeflection);
    }
  }

  private void initHunterBuffs(BuffRegistry registry)
  {
    // Hunter buffs
    // Red tree
    // - Critical Eye
    {
      Buff criticalEye=new Buff("CRITICAL_EYE", RED_TREE, "Critical Eye");
      criticalEye.setIcon("Critical_Eye-icon");
      criticalEye.setRequiredClass(CharacterClass.HUNTER);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=5;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.CRITICAL_RANGED_PERCENTAGE,new FixedDecimalsInteger(tier));
        buff.addTier(tier,stats);
      }
      criticalEye.setImpl(buff);
      registry.registerBuff(criticalEye);
    }
    // - Strength TODO
    // Blue tree
    // - Impact arrows
    {
      Buff impactArrows=new Buff("IMPACT_ARROWS", BLUE_TREE, "Impact Arrows");
      impactArrows.setIcon("Impact_Arrows-icon");
      impactArrows.setRequiredClass(CharacterClass.HUNTER);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=5;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.RANGED_DAMAGE_PERCENTAGE,new FixedDecimalsInteger(tier));
        buff.addTier(tier,stats);
      }
      impactArrows.setImpl(buff);
      registry.registerBuff(impactArrows);
    }
    // Yellow tree TODO
    // - Elusive
    // - Endurance
    // - Survival Gear
    // Stance related buffs
  }

  private void initMinstrelBuffs(BuffRegistry registry)
  {
    // Minstrel buffs
    // Yellow tree
    // Blue tree
    // Red tree
    // - Enduring Morale
    {
      Buff enduringMorale=new Buff("ENDURING_MORALE", RED_TREE, "Enduring Morale");
      enduringMorale.setIcon("Enduring_Morale-icon");
      enduringMorale.setRequiredClass(CharacterClass.MINSTREL);
      float[] sliceCounts=new float[]{2,2.4f,3,3.4f,4};
      Formula formula=new Formula()
      {
        public float compute(int level, float sliceCount)
        {
          return Slice.getMorale(level,sliceCount);
        }
      };
      FormulaBasedBuff buff=new FormulaBasedBuff(sliceCounts,STAT.MORALE,formula);
      enduringMorale.setImpl(buff);
      registry.registerBuff(enduringMorale);
    }
    // - Finesse
    {
      Buff finesse=new Buff("FINESSE_MINSTREL", RED_TREE, "Finesse");
      finesse.setIcon("Finesse_(Minstrel_Trait)-icon");
      finesse.setRequiredClass(CharacterClass.MINSTREL);
      float[] sliceCounts=new float[]{0.4f,0.8f,1.2f,1.6f,2};
      Formula formula=new Formula()
      {
        public float compute(int level, float sliceCount)
        {
          return Slice.getFinesse(level,sliceCount);
        }
      };
      FormulaBasedBuff buff=new FormulaBasedBuff(sliceCounts,STAT.FINESSE,formula);
      finesse.setImpl(buff);
      registry.registerBuff(finesse);
    }
    // - Critical Strikes
    {
      Buff criticalStrikes=new Buff("CRITICAL_STRIKES", RED_TREE, "Critical Strikes");
      criticalStrikes.setIcon("Critical_Strikes-icon");
      criticalStrikes.setRequiredClass(CharacterClass.MINSTREL);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=5;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.CRITICAL_MELEE_PERCENTAGE,new FixedDecimalsInteger(tier));
        stats.addStat(STAT.CRITICAL_RANGED_PERCENTAGE,new FixedDecimalsInteger(tier));
        stats.addStat(STAT.CRITICAL_TACTICAL_PERCENTAGE,new FixedDecimalsInteger(tier));
        stats.addStat(STAT.TACTICAL_CRITICAL_MULTIPLIER,new FixedDecimalsInteger(tier));
        buff.addTier(tier,stats);
      }
      criticalStrikes.setImpl(buff);
      registry.registerBuff(criticalStrikes);
    }
  }

  private void initRuneKeeperBuffs(BuffRegistry registry)
  {
    // Rune-keeper buffs
    // Yellow tree
    // - Exacting Wards
    {
      Buff exactingWards=new Buff("EXACTING_WARDS", YELLOW_TREE, "Exacting Wards");
      exactingWards.setIcon("Exacting_Wards-icon");
      exactingWards.setRequiredClass(CharacterClass.RUNE_KEEPER);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      int[] values={1,2,3,4,7};
      for(int tier=0;tier<values.length;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.MELEE_CRITICAL_DEFENCE,new FixedDecimalsInteger(values[tier]));
        stats.addStat(STAT.RANGED_CRITICAL_DEFENCE,new FixedDecimalsInteger(values[tier]));
        stats.addStat(STAT.TACTICAL_CRITICAL_DEFENCE,new FixedDecimalsInteger(values[tier]));
        buff.addTier(tier+1,stats);
      }
      exactingWards.setImpl(buff);
      registry.registerBuff(exactingWards);
    }
    // - Fortune Smiles
    {
      Buff fortuneSmiles=new Buff("FORTUNE_SMILES", YELLOW_TREE, "Fortune Smiles");
      fortuneSmiles.setIcon("Fortune_Smiles-icon");
      fortuneSmiles.setRequiredClass(CharacterClass.RUNE_KEEPER);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      int[] values={79,94,118,126,158};
      for(int tier=0;tier<values.length;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.FATE,new FixedDecimalsInteger(values[tier]));
        buff.addTier(tier+1,stats);
      }
      fortuneSmiles.setImpl(buff);
      registry.registerBuff(fortuneSmiles);
    }
    // - Cutting Remarks
    {
      Buff cuttingRemarks=new Buff("CUTTING_REMARKS", YELLOW_TREE, "Cutting Remarks");
      cuttingRemarks.setIcon("Cutting_Remarks-icon");
      cuttingRemarks.setRequiredClass(CharacterClass.RUNE_KEEPER);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=5;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.CRITICAL_TACTICAL_PERCENTAGE,new FixedDecimalsInteger(tier));
        buff.addTier(tier,stats);
      }
      cuttingRemarks.setImpl(buff);
      registry.registerBuff(cuttingRemarks);
    }
    // Blue tree
    // - Determination
    {
      Buff determination=new Buff("DETERMINATION", BLUE_TREE, "Determination");
      determination.setIcon("Determination-icon");
      determination.setRequiredClass(CharacterClass.RUNE_KEEPER);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      // TODO use formulas
      int[] values={79,94,118,126,158};
      for(int tier=0;tier<values.length;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.WILL,new FixedDecimalsInteger(values[tier]));
        buff.addTier(tier+1,stats);
      }
      determination.setImpl(buff);
      registry.registerBuff(determination);
    }
    // - Light on One's Feet
    {
      Buff lightOnOnesFeet=new Buff("LIGHT_ON_ONES_FEET", BLUE_TREE, "Light on One's Feet");
      lightOnOnesFeet.setIcon("Light_on_One's_Feet-icon");
      lightOnOnesFeet.setRequiredClass(CharacterClass.RUNE_KEEPER);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=5;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.EVADE_PERCENTAGE,new FixedDecimalsInteger(tier));
        buff.addTier(tier,stats);
      }
      lightOnOnesFeet.setImpl(buff);
      registry.registerBuff(lightOnOnesFeet);
    }
    // Red tree
    // - Deliberate Address
    {
      Buff deliberateAddress=new Buff("DELIBERATE_ADDRESS", RED_TREE, "Deliberate Address");
      deliberateAddress.setIcon("Deliberate_Address-icon");
      deliberateAddress.setRequiredClass(CharacterClass.RUNE_KEEPER);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=5;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.TACTICAL_DAMAGE_PERCENTAGE,new FixedDecimalsInteger(tier));
        buff.addTier(tier,stats);
      }
      deliberateAddress.setImpl(buff);
      registry.registerBuff(deliberateAddress);
    }
    // - TODO Thick Skin
  }

  private BasicStatsSet buildBasicSet(STAT stat, float value)
  {
    BasicStatsSet ret=new BasicStatsSet();
    ret.addStat(stat,new FixedDecimalsInteger(value));
    return ret;
  }
}
