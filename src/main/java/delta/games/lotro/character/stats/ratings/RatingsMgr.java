package delta.games.lotro.character.stats.ratings;

/**
 * Ratings manager.
 * @author DAM
 */
public class RatingsMgr
{
  private RatingCurve _critHit;
  private RatingCurve _devHit;
  private RatingCurve _critAndDevHitMagnitude;
  private RatingCurve _finesse;
  private RatingCurve _damage;
  private RatingCurve _healing;
  private RatingCurve _resistance;
  private RatingCurve _criticalDefence;
  private RatingCurve _incomingHealing;
  private RatingCurve _avoidance; // Block/Parry/Evade
  private RatingCurve _partialAvoidance; // Block/Parry/Evade
  private RatingCurve _partialMitigation;
  private RatingCurve _lightMigitation;
  private RatingCurve _mediumMigitation;
  private RatingCurve _heavyMigitation;

  /**
   * Constructor.
   */
  public RatingsMgr()
  {
    // Critical hit chance
    double[][] critHitValues={{15.0,1190.0/3,70}, {5,794.8,794.8/19}, {5,1075.2,1075.2/19}};
    _critHit=new RatingCurve(critHitValues);
    // Devastate hit chance
    double[][] devHitValues={{10.0, 1330.0, 1330.0/9}};
    _devHit=new RatingCurve(devHitValues);
    // Critical and devastate hit magnitude increase %
    double[][] critAndDevHitMagnitudeValues={{99.0, 300.0, 99*300}};
    _critAndDevHitMagnitude=new RatingCurve(critAndDevHitMagnitudeValues);
    // Finesse
    double[][] finesseValues={{30.0,1190.0/3,170}, {20,2380.0/3,595.0/3}};
    _finesse=new RatingCurve(finesseValues);
    // Damage (melee, ranged or tactical)
    double[][] damageValues={
        { 20, 300, 75 }, { 20, 300, 75 }, { 20, 300, 75 }, { 20, 300, 75 },
        { 40, 300, 200 },
        { 20, 300, 75 }, { 20, 300, 75 }, { 20, 300, 75 }, { 20, 300, 75 }
    };
    _damage=new RatingCurve(damageValues);
    // Outgoing healing
    double[][] healingValues={
        { 30, 1190.0/3, 170 }, { 20, 2380.0/3, 595.0/3 }, { 20, 1190, 297.5 }
    };
    _healing=new RatingCurve(healingValues);
    // Resistance
    double[][] resistanceValues={
        { 30, 1190.0/3, 170 }, { 20, 2380.0/3, 595.0/3 }
    };
    _resistance=new RatingCurve(resistanceValues);
    // Critical defence
    double[][] critDefValues={{99.0, 100.0, 99*100}};
    _criticalDefence=new RatingCurve(critDefValues);
    // Incoming healing
    double[][] incomingHealingValues={
        { 15, 1190.0/3, 70 }, { 10, 2380.0/3, 2380.0/27 }
    };
    _incomingHealing=new RatingCurve(incomingHealingValues);
    // BPE (avoidance)
    double[][] avoidanceValues={
        { 15, 396.7, 1190.1/17 }, { 2, 991.5, 991.5/49 },
        { 3, 1984, 5952/97 }, { 5, 3968, 3968/19 }
    };
    _avoidance=new RatingCurve(avoidanceValues);
    // Partial BPE (partial avoidance)
    double[][] partialAvoidanceValues={
        { 10, 1330, 1330.0/9 }
    };
    _partialAvoidance=new RatingCurve(partialAvoidanceValues);
    // Partial BPE (partial avoidance)
    double[][] partialMitigationValues={
        { 15, 1190.0/3, 70 }
    };
    _partialMitigation=new RatingCurve(partialMitigationValues);
    // Mitigation (light armor)
    double[][] lightMitigationValues={
        { 20, 150, 37.5 }, { 20, 350, 87.5 }
    };
    _lightMigitation=new RatingCurve(lightMitigationValues);
    // Mitigation (medium armor)
    double[][] mediumMitigationValues={
        { 20, 150, 37.5 }, { 30, 350, 150 }
    };
    _mediumMigitation=new RatingCurve(mediumMitigationValues);
    // Mitigation (heavy armor)
    double[][] heavyMitigationValues={
        { 10, 5697.0/38, 633.0/38 }, { 50, 5697.0/38, 5697.0/38 }
    };
    _heavyMigitation=new RatingCurve(heavyMitigationValues);

    /*
Partially Block, Partially Parry & Partially Evade  Block, Parry & Evade  1   10  1330  1330/9  10  ∞   14778   
Physical Mitigation, Orc-craft Mitigation, Fell-wrought Mitigation & Tactical Mitigation:   Physical Mitigation, Orc-craft Mitigation, Fell-wrought Mitigation & Tactical Mitigation                
Light Armour    1   20  150   37.5  20    3750  
    2   20  350   87.5  40  ∞   12500   
    T2pen**   -   -   67.5  40  ∞   19250   U16
Medium Armour     1   20  150   37.5  20    3750  
    2   30  350   150   50  ∞   18750   
    T2pen**   -   -   67.5  50  ∞   25500   U16
Heavy Armour    1   10  5697/38   633/38  10    1666  U13
    2   50  5697/38   5697/38   60  ∞   16658   U13
    T2pen**   -   -   67.5  60  ∞   23408   U16 
         * 
     */
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
