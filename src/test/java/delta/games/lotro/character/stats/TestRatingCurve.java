package delta.games.lotro.character.stats;

import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingsMgr;

/**
 * Test class for rating curves.
 * @author DAM
 */
public class TestRatingCurve
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    RatingsMgr mgr=new RatingsMgr();
    // Critical hit / critical rating
    {
      System.out.println("Critical %");
      RatingCurve curve=mgr.getCriticalHitCurve();
      System.out.println(curve.getPercentage(10000,100));
      System.out.println(curve.getPercentage(7000,100));
      System.out.println(curve.getPercentage(11183,100));
      System.out.println(curve.getPercentage(13299,100));
      System.out.println(curve.getPercentage(7000+794.8/19*100,100));
      System.out.println(curve.getPercentage(16843,100));
    }
    // Devastate hit / critical rating
    {
      System.out.println("Devastate %");
      RatingCurve curve=mgr.getDevastateHitCurve();
      System.out.println(curve.getPercentage(10000,100));
      System.out.println(curve.getPercentage(13299,100));
      System.out.println(curve.getPercentage(14778,100));
      System.out.println(curve.getPercentage(17000,100));
    }
    // Critical and devastate hit magnitude
    {
      System.out.println("Crit/dev magnitude %");
      RatingCurve curve=mgr.getCritAndDevastateHitMagnitudeCurve();
      System.out.println(curve.getPercentage(10000,100));
      System.out.println(curve.getPercentage(13299,100));
      System.out.println(curve.getPercentage(20000,100));
      System.out.println(curve.getPercentage(21000,100));
      System.out.println(curve.getPercentage(2970000,100));
    }
    // Finesse
    {
      System.out.println("Finesse");
      RatingCurve curve=mgr.getFinesse();
      System.out.println(curve.getPercentage(17000,100));
      System.out.println(curve.getPercentage(36833,100));
      System.out.println(curve.getPercentage(36834,100));
      System.out.println(curve.getPercentage(36835,100));
    }
    // Damage
    {
      System.out.println("Damage");
      RatingCurve curve=mgr.getDamage();
      System.out.println(curve.getPercentage(7500,100));
      System.out.println(curve.getPercentage(15000,100));
      System.out.println(curve.getPercentage(22500,100));
      System.out.println(curve.getPercentage(30000,100));
      System.out.println(curve.getPercentage(50000,100));
      System.out.println(curve.getPercentage(57500,100));
      System.out.println(curve.getPercentage(65000,100));
      System.out.println(curve.getPercentage(72500,100));
      System.out.println(curve.getPercentage(80000,100));
      System.out.println(curve.getPercentage(80001,100));
    }
    // Healing
    {
      System.out.println("Healing");
      RatingCurve curve=mgr.getHealing();
      System.out.println(curve.getPercentage(17000,100));
      System.out.println(curve.getPercentage(36834,100));
      System.out.println(curve.getPercentage(66584,100));
      System.out.println(curve.getPercentage(66585,100));
    }
    // Resistance
    {
      System.out.println("Resistance");
      RatingCurve curve=mgr.getResistance();
      System.out.println(curve.getPercentage(10000,100));
      System.out.println(curve.getPercentage(17000,100));
      System.out.println(curve.getPercentage(36834,100));
      System.out.println(curve.getPercentage(36835,100));
    }
    // Critical defence
    {
      System.out.println("Critical defence");
      RatingCurve curve=mgr.getCriticalDefence();
      System.out.println(curve.getPercentage(5000,100));
      System.out.println(curve.getPercentage(10000,100));
      System.out.println(curve.getPercentage(20000,100));
      System.out.println(curve.getPercentage(30000,100));
      System.out.println(curve.getPercentage(990000,100));
    }
    // Incoming healing
    {
      System.out.println("Incoming healing");
      RatingCurve curve=mgr.getIncomingHealing();
      System.out.println(curve.getPercentage(2000,100));
      System.out.println(curve.getPercentage(5000,100));
      System.out.println(curve.getPercentage(7000,100));
      System.out.println(curve.getPercentage(15815,100));
      System.out.println(curve.getPercentage(15816,100));
    }
    // Avoidance
    {
      System.out.println("Avoidance");
      RatingCurve curve=mgr.getAvoidance();
      System.out.println(curve.getPercentage(7001,100));
      System.out.println(curve.getPercentage(9025,100));
      System.out.println(curve.getPercentage(15161,100));
      System.out.println(curve.getPercentage(36045,100));
      System.out.println(curve.getPercentage(36046,100));
    }
    // Partial avoidance
    {
      System.out.println("Partial avoidance");
      RatingCurve curve=mgr.getPartialAvoidance();
      System.out.println(curve.getPercentage(10000,100));
      System.out.println(curve.getPercentage(14777,100));
      System.out.println(curve.getPercentage(14778,100));
      System.out.println(curve.getPercentage(14779,100));
    }
    // Partial mitigation
    {
      System.out.println("Partial mitigation");
      RatingCurve curve=mgr.getPartialMitigation();
      System.out.println(curve.getPercentage(2000,100));
      System.out.println(curve.getPercentage(5000,100));
      System.out.println(curve.getPercentage(6999,100));
      System.out.println(curve.getPercentage(7000,100));
      System.out.println(curve.getPercentage(7001,100));
    }
    // Light armor mitigation
    {
      System.out.println("Light armor mitigation");
      RatingCurve curve=mgr.getLightArmorMitigation();
      System.out.println(curve.getPercentage(3750,100));
      System.out.println(curve.getPercentage(12500,100));
      System.out.println(curve.getPercentage(12501,100));
    }
    // Medium armor mitigation
    {
      System.out.println("Medium armor mitigation");
      RatingCurve curve=mgr.getMediumArmorMitigation();
      System.out.println(curve.getPercentage(3750,100));
      System.out.println(curve.getPercentage(18750,100));
      System.out.println(curve.getPercentage(18751,100));
    }
    // Heavy armor mitigation
    {
      System.out.println("Heavy armor mitigation");
      RatingCurve curve=mgr.getHeavyArmorMitigation();
      System.out.println(curve.getPercentage(1666,100));
      System.out.println(curve.getPercentage(10000,100));
      System.out.println(curve.getPercentage(16658,100));
      System.out.println(curve.getPercentage(16659,100));
    }
  }
}
