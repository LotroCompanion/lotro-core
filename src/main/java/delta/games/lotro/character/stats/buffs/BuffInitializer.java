package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Initializes a buff registry.
 * @author DAM
 */
public class BuffInitializer
{
  /**
   * Init buffs.
   * @param registry Registry to use.
   */
  public void initBuffs(BuffRegistry registry)
  {
    initRacialBuffs(registry);
    initCaptainBuffs(registry);
    initChampionBuffs(registry);
  }

  private void initRacialBuffs(BuffRegistry registry)
  {
    // Man
    // - Balance of Man
    {
      Buff bom=new Buff("BALANCE_OF_MAN", "Balance of Man");
      bom.setIcon("Balance_of_Man-icon");
      bom.setRequiredClass(CharacterClass.CAPTAIN);
      bom.setComputer(new BalanceOfMan());
      registry.registerBuff(bom);
    }
  }

  private void initCaptainBuffs(BuffRegistry registry)
  {
    // Captain buffs
    // - In Defence of Middle Earth
    {
      Buff idome=new Buff("IN_DEFENCE_OF_MIDDLE_EARTH", "In Defence of Middle-Earth");
      idome.setIcon("In_Defence_of_Middle-earth-icon");
      idome.setRequiredClass(CharacterClass.CAPTAIN);
      idome.setComputer(new InDefenceOfMiddleEarth());
      registry.registerBuff(idome);
    }

    // Red tree
    // - Arterial Strikes
    {
      Buff arterialStrikes=new Buff("ARTERIAL_STRIKES", "Arterial Strikes");
      arterialStrikes.setIcon("Arterial_Strikes-icon");
      arterialStrikes.setRequiredClass(CharacterClass.CAPTAIN);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=4;tier++)
      {
        buff.addTier(tier,buildBasicSet(STAT.CRITICAL_MELEE_PERCENTAGE,tier));
      }
      arterialStrikes.setComputer(buff);
      registry.registerBuff(arterialStrikes);
    }
    // - Martial Prowess
    {
      Buff martialProwess=new Buff("MARTIAL_PROWESS", "Martial Prowess");
      martialProwess.setIcon("Martial_Prowess-icon");
      martialProwess.setRequiredClass(CharacterClass.CAPTAIN);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=3;tier++)
      {
        BasicStatsSet stats=new BasicStatsSet();
        stats.addStat(STAT.MELEE_DAMAGE_PERCENTAGE,new FixedDecimalsInteger(1));
        //ret.addStat(STAT.ATTACK_SPEED,new FixedDecimalsInteger(5*tier));
        buff.addTier(tier,stats);
      }
      martialProwess.setComputer(buff);
      registry.registerBuff(martialProwess);
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
      Buff mightIncrease=new Buff("MIGHT_INCREASE", "Might Increase");
      mightIncrease.setIcon("Might_Increase-icon");
      mightIncrease.setRequiredClass(CharacterClass.CHAMPION);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      buff.addTier(1,buildBasicSet(STAT.MIGHT,84));
      buff.addTier(2,buildBasicSet(STAT.MIGHT,100));
      buff.addTier(3,buildBasicSet(STAT.MIGHT,126));
      buff.addTier(4,buildBasicSet(STAT.MIGHT,134));
      buff.addTier(5,buildBasicSet(STAT.MIGHT,168));
      mightIncrease.setComputer(buff);
      registry.registerBuff(mightIncrease);
    }
    // - Finesse Increase
    {
      Buff finesseIncrease=new Buff("FINESSE_INCREASE", "Finesse Increase");
      finesseIncrease.setIcon("Finesse_Increase-icon");
      finesseIncrease.setRequiredClass(CharacterClass.CHAMPION);
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=5;tier++)
      {
        buff.addTier(tier,buildBasicSet(STAT.FINESSE,521.2f*tier));
      }
      finesseIncrease.setComputer(buff);
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
