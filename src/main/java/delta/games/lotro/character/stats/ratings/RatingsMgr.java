package delta.games.lotro.character.stats.ratings;

/**
 * Ratings manager.
 * @author DAM
 */
public class RatingsMgr
{
  /**
   * Percentage computation policies.
   * @author DAM
   */
  public enum Policy
  {
    /**
     * Curves used before Update 21 (level<=105).
     */
    PRE_UPDATE21,
    /**
     * Curves used starting with Update 21 (level<=115).
     */
    UPDATE21
  }

  private static final Policy _policy=Policy.UPDATE21;
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
    if (_policy==Policy.UPDATE21)
    {
      initForUpdate21();
    }
    if (_policy==Policy.PRE_UPDATE21)
    {
      initBeforeMordor();
    }
  }

  private void initBeforeMordor()
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
    // Damage
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

  private void initForUpdate21()
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
      _critHit=new Update21RatingCurveImpl(segments);
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
      _devHit=new Update21RatingCurveImpl(segments);
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
      _critAndDevHitMagnitude=new Update21RatingCurveImpl(segments);
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
      _finesse=new Update21RatingCurveImpl(segments);
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
      _damage=new Update21RatingCurveImpl(segments);
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
      _healing=new Update21RatingCurveImpl(segments);
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
      _resistance=new Update21RatingCurveImpl(segments);
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
      _criticalDefence=new Update21RatingCurveImpl(segments);
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
      _incomingHealing=new Update21RatingCurveImpl(segments);
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
      _avoidance=new Update21RatingCurveImpl(segments);
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
      _partialAvoidance=new Update21RatingCurveImpl(segments);
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
      _partialMitigation=new Update21RatingCurveImpl(segments);
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
      _lightMigitation=new Update21RatingCurveImpl(segments);
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
      _mediumMigitation=new Update21RatingCurveImpl(segments);
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
      _heavyMigitation=new Update21RatingCurveImpl(segments);
    }

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
