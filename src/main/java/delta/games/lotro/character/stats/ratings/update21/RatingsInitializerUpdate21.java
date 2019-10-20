package delta.games.lotro.character.stats.ratings.update21;

import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingCurveId;
import delta.games.lotro.character.stats.ratings.RatingsMgr;

/**
 * Initializer for Update 21 'rating to percentage' curves.
 * @author DAM
 */
public class RatingsInitializerUpdate21
{
  /**
   * Initializes the given ratings manager with the curve used for Update 21.
   * @param ratingsMgr Ratings manager to use.
   */
  public static void init(RatingsMgr ratingsMgr)
  {
    // Critical hit chance
    {
      Update21RatingCurveSegment[] segments={
        new Update21RatingCurveSegment(1,75,1,25,200,0),
        new Update21RatingCurveSegment(76,76,1,25,4125.0/19,0),
        new Update21RatingCurveSegment(77,100,1,25,540,-24000),
        new Update21RatingCurveSegment(101,105,1,25,3000,-270000),
        new Update21RatingCurveSegment(106,115,1,25,4000,-370000),
        new Update21RatingCurveSegment(116,120,1,25,31500,-3555000)
      };
      RatingCurve critHit=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.CRITICAL_HIT,critHit);
    }
    // Devastate hit chance
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,10,400,0),
          new Update21RatingCurveSegment(76,76,1,10,8250.0/19,0),
          new Update21RatingCurveSegment(77,100,1,10,1080,-48000),
          new Update21RatingCurveSegment(101,105,1,10,6000,-540000),
          new Update21RatingCurveSegment(106,115,1,10,8000,-740000),
          new Update21RatingCurveSegment(116,120,1,10,63000,-7110000)
      };
      RatingCurve devHit=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.DEVASTATE_HIT,devHit);
    }
    // Critical and devastate hit magnitude increase %
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,100,500,0),
          new Update21RatingCurveSegment(76,76,1,100,10325.0/19,0),
          new Update21RatingCurveSegment(77,100,1,100,1348,-59800),
          new Update21RatingCurveSegment(101,105,1,100,7500,-675000),
          new Update21RatingCurveSegment(106,115,1,100,10000,-925000),
          new Update21RatingCurveSegment(116,120,1,100,78750,-8887500)
      };
      RatingCurve critAndDevHitMagnitude=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.CRIT_DEVASTATE_MAGNITUDE,critAndDevHitMagnitude);
    }
    // Finesse
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,50,200,0),
          new Update21RatingCurveSegment(76,76,1,50,4125.0/19,0),
          new Update21RatingCurveSegment(77,100,1,50,540,-24000),
          new Update21RatingCurveSegment(101,105,1,50,3000,-270000),
          new Update21RatingCurveSegment(106,115,1,50,4000,-370000),
          new Update21RatingCurveSegment(116,120,1,50,31500,-3555000)
      };
      RatingCurve finesse=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.FINESSE,finesse);
    }
    // Damage
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,200,270,0),
          new Update21RatingCurveSegment(76,76,1,200,5575.0/19,0),
          new Update21RatingCurveSegment(77,100,1,200,728,-32300),
          new Update21RatingCurveSegment(101,105,1,200,4050,-364500),
          new Update21RatingCurveSegment(106,115,1,200,5400,-499500),
          new Update21RatingCurveSegment(116,120,1,200,42525,-4799250)
      };
      RatingCurve damage=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.DAMAGE,damage);
    }
    // Outgoing healing
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,70,200,0),
          new Update21RatingCurveSegment(76,76,1,70,4125.0/19,0),
          new Update21RatingCurveSegment(77,100,1,70,540,-24000),
          new Update21RatingCurveSegment(101,105,1,70,3000,-270000),
          new Update21RatingCurveSegment(106,115,1,70,4000,-370000),
          new Update21RatingCurveSegment(116,120,1,70,31500,-3555000)
      };
      RatingCurve healing=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.HEALING,healing);
    }
    // Resistance
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,50,200,0),
          new Update21RatingCurveSegment(76,76,1,50,4125.0/19,0),
          new Update21RatingCurveSegment(77,100,1,50,540,-24000),
          new Update21RatingCurveSegment(101,105,1,50,3000,-270000),
          new Update21RatingCurveSegment(106,115,1,50,4000,-370000),
          new Update21RatingCurveSegment(116,120,1,50,31500,-3555000)
      };
      // TODO T2 penalty: OppLvl*90
      RatingCurve resistance=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.RESISTANCE,resistance);
    }
    // Critical defence
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,80,200,0),
          new Update21RatingCurveSegment(76,76,1,80,4125.0/19,0),
          new Update21RatingCurveSegment(77,100,1,80,540,-24000),
          new Update21RatingCurveSegment(101,105,1,80,3000,-270000),
          new Update21RatingCurveSegment(106,115,1,80,4000,-370000),
          new Update21RatingCurveSegment(116,120,1,80,31500,-3555000)
      };
      RatingCurve criticalDefence=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.CRITICAL_DEFENCE,criticalDefence);
    }
    // Incoming healing
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,25,200,0),
          new Update21RatingCurveSegment(76,76,1,25,4125.0/19,0),
          new Update21RatingCurveSegment(77,100,1,25,540,-24000),
          new Update21RatingCurveSegment(101,105,1,25,3000,-270000),
          new Update21RatingCurveSegment(106,115,1,25,4000,-370000),
          new Update21RatingCurveSegment(116,120,1,25,31500,-3555000)
      };
      RatingCurve incomingHealing=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.INCOMING_HEALING,incomingHealing);
    }
    // BPE (avoidance)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,13,200,0),
          new Update21RatingCurveSegment(76,76,1,13,4125.0/19,0),
          new Update21RatingCurveSegment(77,100,1,13,540,-24000),
          new Update21RatingCurveSegment(101,105,1,13,3000,-270000),
          new Update21RatingCurveSegment(106,115,1,13,4000,-370000),
          new Update21RatingCurveSegment(116,120,1,13,31500,-3555000)
      };
      // TODO T2 penalty: OppLvl*40
      RatingCurve avoidance=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.AVOIDANCE,avoidance);
    }
    // Partial BPE (partial avoidance)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,35,400,0),
          new Update21RatingCurveSegment(76,76,1,35,8250.0/19,0),
          new Update21RatingCurveSegment(77,100,1,35,1080,-48000),
          new Update21RatingCurveSegment(101,105,1,35,6000,-540000),
          new Update21RatingCurveSegment(106,115,1,35,8000,-740000),
          new Update21RatingCurveSegment(116,120,1,35,63000,-7110000)
      };
      // TODO T2 penalty: OppLvl*40
      RatingCurve partialAvoidance=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.PARTIAL_AVOIDANCE,partialAvoidance);
    }
    // Partial Mitigation
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1,50,400,0),
          new Update21RatingCurveSegment(76,76,1,50,8250.0/19,0),
          new Update21RatingCurveSegment(77,100,1,50,1080,-48000),
          new Update21RatingCurveSegment(101,105,1,50,6000,-540000),
          new Update21RatingCurveSegment(106,115,1,50,8000,-740000),
          new Update21RatingCurveSegment(116,120,1,50,63000,-7110000)
      };
      // TODO T2 penalty: OppLvl*40
      // Base 10% added later
      RatingCurve partialMitigation=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.PARTIAL_MITIGATION,partialMitigation);
    }
    // Mitigation (light armor)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1.6,40,448.0/3,0),
          new Update21RatingCurveSegment(76,76,1.6,40,3080.0/19,0),
          new Update21RatingCurveSegment(77,100,1.6,40,403.2,-17920),
          new Update21RatingCurveSegment(101,105,1.6,40,2240,-201600),
          new Update21RatingCurveSegment(106,115,1.6,40,8960.0/3,-828800.0/3),
          new Update21RatingCurveSegment(116,120,1.6,40,23520,-2654400)
      };
      // TODO T2 penalty: Floor(OppLvl*13.5)*5
      RatingCurve lightMigitation=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.LIGHT_MITIGATION,lightMigitation);
    }
    // Mitigation (medium armor)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,10.0/7,50,3820.0/21,0),
          new Update21RatingCurveSegment(76,76,10.0/7,50,26250.0/133,0),
          new Update21RatingCurveSegment(77,100,10.0/7,50,3440.0/7,-153000.0/7),
          new Update21RatingCurveSegment(101,105,10.0/7,50,19100.0/7,-1719000.0/7),
          new Update21RatingCurveSegment(106,115,10.0/7,50,76400.0/21,-7067000.0/21),
          new Update21RatingCurveSegment(116,120,10.0/7,50,200550.0/7,-22633500.0/7)
      };
      // TODO T2 penalty: Floor(OppLvl*13.5)*5
      RatingCurve mediumMigitation=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.MEDIUM_MITIGATION,mediumMigitation);
    }
    // Mitigation (heavy armor)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,75,1.2,60,208.8,0),
          new Update21RatingCurveSegment(76,76,1.2,60,4320.0/19,0),
          new Update21RatingCurveSegment(77,100,1.2,60,561.6,-24840),
          new Update21RatingCurveSegment(101,105,1.2,60,3132,-281880),
          new Update21RatingCurveSegment(106,115,1.2,60,4176,-386280),
          new Update21RatingCurveSegment(116,120,1.2,60,32886,-3711420)
      };
      // TODO T2 penalty Floor(OppLvl*13.5)*5
      RatingCurve heavyMigitation=new Update21RatingCurveImpl(segments);
      ratingsMgr.setCurve(RatingCurveId.HEAVY_MITIGATION,heavyMigitation);
    }

    // TODO Cope with T2 penalty:
    // If IsT2Zone Then RL = RL-67.5
  }
}
