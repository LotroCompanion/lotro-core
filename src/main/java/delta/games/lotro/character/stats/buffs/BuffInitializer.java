package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
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
   * Init buffs.
   * @param registry Registry to use.
   */
  public void initBuffs(BuffRegistry registry)
  {
    initSharedBuffs(registry);
    initRacialBuffs(registry);
    initCaptainBuffs(registry);
    initChampionBuffs(registry);
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
    // TODO
    // Yellow tree
    // - Might Increase
    {
      Buff mightIncrease=new Buff("MIGHT_INCREASE", YELLOW_TREE, "Might Increase");
      mightIncrease.setIcon("Might_Increase-icon");
      mightIncrease.setRequiredClass(CharacterClass.CHAMPION);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      buff.addTier(1,buildBasicSet(STAT.MIGHT,84));
      buff.addTier(2,buildBasicSet(STAT.MIGHT,100));
      buff.addTier(3,buildBasicSet(STAT.MIGHT,126));
      buff.addTier(4,buildBasicSet(STAT.MIGHT,134));
      buff.addTier(5,buildBasicSet(STAT.MIGHT,168));
      mightIncrease.setImpl(buff);
      registry.registerBuff(mightIncrease);
    }
    // - Finesse Increase
    {
      Buff finesseIncrease=new Buff("FINESSE_INCREASE", YELLOW_TREE, "Finesse Increase");
      finesseIncrease.setIcon("Finesse_Increase-icon");
      finesseIncrease.setRequiredClass(CharacterClass.CHAMPION);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=5;tier++)
      {
        buff.addTier(tier,buildBasicSet(STAT.FINESSE,521.2f*tier));
      }
      finesseIncrease.setImpl(buff);
      registry.registerBuff(finesseIncrease);
    }
  }

  private BasicStatsSet buildBasicSet(STAT stat, float value)
  {
    BasicStatsSet ret=new BasicStatsSet();
    ret.addStat(stat,new FixedDecimalsInteger(value));
    return ret;
  }
}
