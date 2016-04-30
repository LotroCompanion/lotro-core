package delta.games.lotro.character.stats;

import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingsMgr;

/**
 * Test class for rating curves.
 * @author DAM
 */
public class TestRatingCurve
{
  private static final int LEVEL_CAP=105;

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
      System.out.println(curve.getPercentage(10000,LEVEL_CAP));
      System.out.println(curve.getPercentage(7350,LEVEL_CAP));
      System.out.println(curve.getPercentage(11743,LEVEL_CAP));
      System.out.println(curve.getPercentage(13299,LEVEL_CAP));
      System.out.println(curve.getPercentage(7000+794.8/19*100,LEVEL_CAP));
      System.out.println(curve.getPercentage(17685,LEVEL_CAP));
    }
    // Devastate hit / critical rating
    {
      System.out.println("Devastate %");
      RatingCurve curve=mgr.getDevastateHitCurve();
      System.out.println(curve.getPercentage(10000,LEVEL_CAP));
      System.out.println(curve.getPercentage(13299,LEVEL_CAP));
      System.out.println(curve.getPercentage(15517,LEVEL_CAP));
      System.out.println(curve.getPercentage(17000,LEVEL_CAP));
    }
    // Critical and devastate hit magnitude
    {
      System.out.println("Crit/dev magnitude %");
      RatingCurve curve=mgr.getCritAndDevastateHitMagnitudeCurve();
      System.out.println(curve.getPercentage(10000,LEVEL_CAP));
      System.out.println(curve.getPercentage(13299,LEVEL_CAP));
      System.out.println(curve.getPercentage(20000,LEVEL_CAP));
      System.out.println(curve.getPercentage(21000,LEVEL_CAP));
      System.out.println(curve.getPercentage(2970000,LEVEL_CAP));
    }
    // Finesse
    {
      System.out.println("Finesse");
      RatingCurve curve=mgr.getFinesse();
      System.out.println(curve.getPercentage(17000,LEVEL_CAP));
      System.out.println(curve.getPercentage(36833,LEVEL_CAP));
      System.out.println(curve.getPercentage(36834,LEVEL_CAP));
      System.out.println(curve.getPercentage(36835,LEVEL_CAP));
      System.out.println(curve.getPercentage(50000,LEVEL_CAP));
    }
    // Damage
    {
      System.out.println("Damage");
      RatingCurve curve=mgr.getDamage();
      System.out.println(curve.getPercentage(7500,LEVEL_CAP));
      System.out.println(curve.getPercentage(15000,LEVEL_CAP));
      System.out.println(curve.getPercentage(22500,LEVEL_CAP));
      System.out.println(curve.getPercentage(30000,LEVEL_CAP));
      System.out.println(curve.getPercentage(50000,LEVEL_CAP));
      System.out.println(curve.getPercentage(57500,LEVEL_CAP));
      System.out.println(curve.getPercentage(65000,LEVEL_CAP));
      System.out.println(curve.getPercentage(72500,LEVEL_CAP));
      System.out.println(curve.getPercentage(80000,LEVEL_CAP));
      System.out.println(curve.getPercentage(80001,LEVEL_CAP));
    }
    // Healing
    {
      System.out.println("Healing");
      RatingCurve curve=mgr.getHealing();
      System.out.println(curve.getPercentage(17000,LEVEL_CAP));
      System.out.println(curve.getPercentage(36834,LEVEL_CAP));
      System.out.println(curve.getPercentage(66584,LEVEL_CAP));
      System.out.println(curve.getPercentage(66585,LEVEL_CAP));
    }
    // Resistance
    {
      System.out.println("Resistance");
      RatingCurve curve=mgr.getResistance();
      System.out.println(curve.getPercentage(10000,LEVEL_CAP));
      System.out.println(curve.getPercentage(17850,LEVEL_CAP));
      System.out.println(curve.getPercentage(38674,LEVEL_CAP));
      System.out.println(curve.getPercentage(38675,LEVEL_CAP));
    }
    // Critical defence
    {
      System.out.println("Critical defence");
      RatingCurve curve=mgr.getCriticalDefence();
      System.out.println(curve.getPercentage(5000,LEVEL_CAP));
      System.out.println(curve.getPercentage(10000,LEVEL_CAP));
      System.out.println(curve.getPercentage(20000,LEVEL_CAP));
      System.out.println(curve.getPercentage(30000,LEVEL_CAP));
      System.out.println(curve.getPercentage(990000,LEVEL_CAP));
    }
    // Incoming healing
    {
      System.out.println("Incoming healing");
      RatingCurve curve=mgr.getIncomingHealing();
      System.out.println(curve.getPercentage(2000,LEVEL_CAP));
      System.out.println(curve.getPercentage(5000,LEVEL_CAP));
      System.out.println(curve.getPercentage(7350,LEVEL_CAP));
      System.out.println(curve.getPercentage(16605,LEVEL_CAP));
      System.out.println(curve.getPercentage(16606,LEVEL_CAP));
    }
    // Avoidance
    {
      System.out.println("Avoidance");
      RatingCurve curve=mgr.getAvoidance();
      System.out.println(curve.getPercentage(7844,LEVEL_CAP));
      System.out.println(curve.getPercentage(7845,LEVEL_CAP));
    }
    // Partial avoidance
    {
      System.out.println("Partial avoidance");
      RatingCurve curve=mgr.getPartialAvoidance();
      System.out.println(curve.getPercentage(7350,LEVEL_CAP));
      System.out.println(curve.getPercentage(9475,LEVEL_CAP));
      System.out.println(curve.getPercentage(12885,LEVEL_CAP));
      System.out.println(curve.getPercentage(35119,LEVEL_CAP));
      System.out.println(curve.getPercentage(35120,LEVEL_CAP));
    }
    // Partial mitigation
    {
      System.out.println("Partial mitigation");
      RatingCurve curve=mgr.getPartialMitigation();
      System.out.println(curve.getPercentage(10000,LEVEL_CAP));
      System.out.println(curve.getPercentage(20000,LEVEL_CAP));
      System.out.println(curve.getPercentage(30000,LEVEL_CAP));
      System.out.println(curve.getPercentage(40000,LEVEL_CAP));
      System.out.println(curve.getPercentage(41649,LEVEL_CAP));
      System.out.println(curve.getPercentage(41650,LEVEL_CAP));
    }
    // Light armor mitigation
    {
      System.out.println("Light armor mitigation");
      RatingCurve curve=mgr.getLightArmorMitigation();
      System.out.println(curve.getPercentage(3938,LEVEL_CAP));
      System.out.println(curve.getPercentage(13124,LEVEL_CAP));
      System.out.println(curve.getPercentage(13125,LEVEL_CAP));
    }
    // Medium armor mitigation
    {
      System.out.println("Medium armor mitigation");
      RatingCurve curve=mgr.getMediumArmorMitigation();
      System.out.println(curve.getPercentage(3936,LEVEL_CAP));
      System.out.println(curve.getPercentage(15320,LEVEL_CAP));
      System.out.println(curve.getPercentage(15321,LEVEL_CAP));
    }
    // Heavy armor mitigation
    {
      System.out.println("Heavy armor mitigation");
      RatingCurve curve=mgr.getHeavyArmorMitigation();
      System.out.println(curve.getPercentage(1750,LEVEL_CAP));
      System.out.println(curve.getPercentage(17490,LEVEL_CAP));
      System.out.println(curve.getPercentage(17491,LEVEL_CAP));
    }
  }
}
