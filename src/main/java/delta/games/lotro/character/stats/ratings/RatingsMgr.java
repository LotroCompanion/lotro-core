package delta.games.lotro.character.stats.ratings;

/**
 * Ratings manager.
 * @author DAM
 */
public class RatingsMgr
{
  private CurvedRatingCurveImpl _critHit;
  private CurvedRatingCurveImpl _devHit;
  private CurvedRatingCurveImpl _critAndDevHitMagnitude;
  private CurvedRatingCurveImpl _finesse;
  private DamageRatingCurveImpl _damage;
  private CurvedRatingCurveImpl _healing;
  private CurvedRatingCurveImpl _resistance;
  private CurvedRatingCurveImpl _criticalDefence;
  private CurvedRatingCurveImpl _incomingHealing;
  private CurvedRatingCurveImpl _avoidance; // Block/Parry/Evade
  private CurvedRatingCurveImpl _partialAvoidance; // Block/Parry/Evade
  private CurvedRatingCurveImpl _partialMitigation;
  private CurvedRatingCurveImpl _lightMigitation;
  private CurvedRatingCurveImpl _mediumMigitation;
  private CurvedRatingCurveImpl _heavyMigitation;

  /**
   * Constructor.
   */
  public RatingsMgr()
  {
    // Critical hit chance
    double[][] critHitValues={{15.0,1190.0/3,70}, {5,794.8,794.8/19}, {5,1075.2,1075.2/19}};
    _critHit=new CurvedRatingCurveImpl(critHitValues);
    // Devastate hit chance
    double[][] devHitValues={{10.0, 1330.0, 1330.0/9}};
    _devHit=new CurvedRatingCurveImpl(devHitValues);
    // Critical and devastate hit magnitude increase %
    double[][] critAndDevHitMagnitudeValues={{99.9, 300.0, 99.9*300}};
    _critAndDevHitMagnitude=new CurvedRatingCurveImpl(critAndDevHitMagnitudeValues);
    // Finesse
    double[][] finesseValues={{99.9, 1190.0/3.0, 99.9*1190.0/3.0}};
    _finesse=new CurvedRatingCurveImpl(finesseValues);
    _damage=new DamageRatingCurveImpl();
    // Outgoing healing
    double[][] healingValues={
        { 30, 1190.0/3, 170 }, { 20, 2380.0/3, 595.0/3 }, { 20, 1190, 297.5 }
    };
    _healing=new CurvedRatingCurveImpl(healingValues);
    // Resistance
    double[][] resistanceValues={
        { 30, 1190.0/3, 170 }, { 20, 2380.0/3, 595.0/3 }
    };
    _resistance=new CurvedRatingCurveImpl(resistanceValues);
    // Critical defence
    double[][] critDefValues={{99.9, 100.0, 99.9*100}};
    _criticalDefence=new CurvedRatingCurveImpl(critDefValues);
    // Incoming healing
    double[][] incomingHealingValues={
        { 15, 1190.0/3, 70 }, { 10, 2380.0/3, 2380.0/27 }
    };
    _incomingHealing=new CurvedRatingCurveImpl(incomingHealingValues);
    // BPE (avoidance)
    double[][] avoidanceValues={{ 13, 499.95, 43329.0/580 }};
    _avoidance=new CurvedRatingCurveImpl(avoidanceValues);
    // Partial BPE (partial avoidance)
    double[][] partialAvoidanceValues={
        { 15, 396.66, 59499.0/850 }, { 2, 991.66, 49583.0/2450 },
        { 3, 1050, 3150.0/97 }, { 15, 1200, 3600.0/17 },
    };
    _partialAvoidance=new CurvedRatingCurveImpl(partialAvoidanceValues);
    // Partial Mitigation
    double[][] partialMitigationValues={
        { 50, 396.66, 396.66 }
    };
    _partialMitigation=new CurvedRatingCurveImpl(partialMitigationValues);
    // Mitigation (light armor)
    double[][] lightMitigationValues={
        { 20, 150, 37.5 }, { 20, 350, 87.5 }
    };
    _lightMigitation=new CurvedRatingCurveImpl(lightMitigationValues);
    // Mitigation (medium armor)
    double[][] mediumMitigationValues={
        { 20, 149.9175, 59967.0/1600 }, { 30, 253.003, 759009.0/7000 }
    };
    _mediumMigitation=new CurvedRatingCurveImpl(mediumMitigationValues);
    // Mitigation (heavy armor)
    double[][] heavyMitigationValues={
        { 10, 5697.0/38, 633.0/38 }, { 50, 5697.0/38, 5697.0/38 }
    };
    _heavyMigitation=new CurvedRatingCurveImpl(heavyMitigationValues);

    // TODO Cope with T2 penalty:
    // If IsT2Zone Then RL = RL-67.5
  }

  /**
   * Get rating curve for critical hits.
   * @return a rating curve.
   */
  public RatingCurve getCriticalHitCurve()
  {
    return _critHit;
  }

  /**
   * Get rating curve for devastate hits.
   * @return a rating curve.
   */
  public RatingCurve getDevastateHitCurve()
  {
    return _devHit;
  }

  /**
   * Get rating curve for critical and devastate hits magnitude.
   * @return a rating curve.
   */
  public RatingCurve getCritAndDevastateHitMagnitudeCurve()
  {
    return _critAndDevHitMagnitude;
  }

  /**
   * Get rating curve for finesse.
   * @return a rating curve.
   */
  public RatingCurve getFinesse()
  {
    return _finesse;
  }

  /**
   * Get rating curve for damage (melee, ranged or tactical).
   * @return a rating curve.
   */
  public RatingCurve getDamage()
  {
    return _damage;
  }

  /**
   * Get rating curve for outgoing healing.
   * @return a rating curve.
   */
  public RatingCurve getHealing()
  {
    return _healing;
  }

  /**
   * Get rating curve for resistance.
   * @return a rating curve.
   */
  public RatingCurve getResistance()
  {
    return _resistance;
  }

  /**
   * Get rating curve for critical defence.
   * @return a rating curve.
   */
  public RatingCurve getCriticalDefence()
  {
    return _criticalDefence;
  }

  /**
   * Get rating curve for incoming healing.
   * @return a rating curve.
   */
  public RatingCurve getIncomingHealing()
  {
    return _incomingHealing;
  }

  /**
   * Get rating curve for avoidance (B/P/E).
   * @return a rating curve.
   */
  public RatingCurve getAvoidance()
  {
    return _avoidance;
  }

  /**
   * Get rating curve for partial avoidance (B/P/E).
   * @return a rating curve.
   */
  public RatingCurve getPartialAvoidance()
  {
    return _partialAvoidance;
  }

  /**
   * Get rating curve for partial mitigation.
   * @return a rating curve.
   */
  public RatingCurve getPartialMitigation()
  {
    return _partialMitigation;
  }

  /**
   * Get rating curve for light armor mitigation.
   * @return a rating curve.
   */
  public RatingCurve getLightArmorMitigation()
  {
    return _lightMigitation;
  }

  /**
   * Get rating curve for medium armor mitigation.
   * @return a rating curve.
   */
  public RatingCurve getMediumArmorMitigation()
  {
    return _mediumMigitation;
  }

  /**
   * Get rating curve for heavy armor mitigation.
   * @return a rating curve.
   */
  public RatingCurve getHeavyArmorMitigation()
  {
    return _heavyMigitation;
  }
}
