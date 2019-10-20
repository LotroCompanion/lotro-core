package delta.games.lotro.character.stats.ratings.before21;

import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingCurveId;
import delta.games.lotro.character.stats.ratings.RatingsMgr;

/**
 * Initializer for pre-Mordor 'rating to percentage' curves.
 * @author DAM
 */
public class RatingsInitializerBeforeUpdate21
{
  /**
   * Initializes the given ratings manager with the curve used before Mordor (before Update 21).
   * @param ratingsMgr Ratings manager to use.
   */
  public static void init(RatingsMgr ratingsMgr)
  {
    // Critical hit chance
    double[][] critHitValues={{15.0,1190.0/3,70}, {5,794.8,794.8/19}, {5,1075.2,1075.2/19}};
    RatingCurve critHit=new CurvedRatingCurveImpl(critHitValues);
    ratingsMgr.setCurve(RatingCurveId.CRITICAL_HIT,critHit);
    // Devastate hit chance
    double[][] devHitValues={{10.0, 1330.0, 1330.0/9}};
    RatingCurve devHit=new CurvedRatingCurveImpl(devHitValues);
    ratingsMgr.setCurve(RatingCurveId.DEVASTATE_HIT,devHit);
    // Critical and devastate hit magnitude increase %
    double[][] critAndDevHitMagnitudeValues={{99.9, 300.0, 99.9*300}};
    RatingCurve critAndDevHitMagnitude=new CurvedRatingCurveImpl(critAndDevHitMagnitudeValues);
    ratingsMgr.setCurve(RatingCurveId.CRIT_DEVASTATE_MAGNITUDE,critAndDevHitMagnitude);
    // Finesse
    double[][] finesseValues={{99.9, 1190.0/3.0, 99.9*1190.0/3.0}};
    RatingCurve finesse=new CurvedRatingCurveImpl(finesseValues);
    ratingsMgr.setCurve(RatingCurveId.FINESSE,finesse);
    // Damage
    RatingCurve damage=new DamageRatingCurveImpl();
    ratingsMgr.setCurve(RatingCurveId.DAMAGE,damage);
    // Outgoing healing
    double[][] healingValues={
        { 30, 1190.0/3, 170 }, { 20, 2380.0/3, 595.0/3 }, { 20, 1190, 297.5 }
    };
    RatingCurve healing=new CurvedRatingCurveImpl(healingValues);
    ratingsMgr.setCurve(RatingCurveId.HEALING,healing);
    // Resistance
    double[][] resistanceValues={
        { 30, 1190.0/3, 170 }, { 20, 2380.0/3, 595.0/3 }
    };
    RatingCurve resistance=new CurvedRatingCurveImpl(resistanceValues);
    ratingsMgr.setCurve(RatingCurveId.RESISTANCE,resistance);
    // Critical defence
    double[][] critDefValues={{99.9, 100.0, 99.9*100}};
    RatingCurve criticalDefence=new CurvedRatingCurveImpl(critDefValues);
    ratingsMgr.setCurve(RatingCurveId.CRITICAL_DEFENCE,criticalDefence);
    // Incoming healing
    double[][] incomingHealingValues={
        { 15, 1190.0/3, 70 }, { 10, 2380.0/3, 2380.0/27 }
    };
    RatingCurve incomingHealing=new CurvedRatingCurveImpl(incomingHealingValues);
    ratingsMgr.setCurve(RatingCurveId.INCOMING_HEALING,incomingHealing);
    // BPE (avoidance)
    double[][] avoidanceValues={{ 13, 499.95, 43329.0/580 }};
    RatingCurve avoidance=new CurvedRatingCurveImpl(avoidanceValues);
    ratingsMgr.setCurve(RatingCurveId.AVOIDANCE,avoidance);
    // Partial BPE (partial avoidance)
    double[][] partialAvoidanceValues={
        { 15, 396.66, 59499.0/850 }, { 2, 991.66, 49583.0/2450 },
        { 3, 1050, 3150.0/97 }, { 15, 1200, 3600.0/17 },
    };
    RatingCurve partialAvoidance=new CurvedRatingCurveImpl(partialAvoidanceValues);
    ratingsMgr.setCurve(RatingCurveId.PARTIAL_AVOIDANCE,partialAvoidance);
    // Partial Mitigation
    double[][] partialMitigationValues={
        { 50, 396.66, 396.66 }
    };
    RatingCurve partialMitigation=new CurvedRatingCurveImpl(partialMitigationValues);
    ratingsMgr.setCurve(RatingCurveId.PARTIAL_MITIGATION,partialMitigation);
    // Mitigation (light armor)
    double[][] lightMitigationValues={
        { 20, 150, 37.5 }, { 20, 350, 87.5 }
    };
    RatingCurve lightMigitation=new CurvedRatingCurveImpl(lightMitigationValues);
    ratingsMgr.setCurve(RatingCurveId.LIGHT_MITIGATION,lightMigitation);
    // Mitigation (medium armor)
    double[][] mediumMitigationValues={
        { 20, 149.9175, 59967.0/1600 }, { 30, 253.003, 759009.0/7000 }
    };
    RatingCurve mediumMigitation=new CurvedRatingCurveImpl(mediumMitigationValues);
    ratingsMgr.setCurve(RatingCurveId.MEDIUM_MITIGATION,mediumMigitation);
    // Mitigation (heavy armor)
    double[][] heavyMitigationValues={
        { 10, 5697.0/38, 633.0/38 }, { 50, 5697.0/38, 5697.0/38 }
    };
    RatingCurve heavyMigitation=new CurvedRatingCurveImpl(heavyMitigationValues);
    ratingsMgr.setCurve(RatingCurveId.HEAVY_MITIGATION,heavyMigitation);
    // TODO Cope with T2 penalty:
    // If IsT2Zone Then RL = RL-67.5
  }
}
