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
        new Update21RatingCurveSegment(1,50,0.66,15,71.5,0),
        new Update21RatingCurveSegment(51,84,1,20,250,-6900),
        new Update21RatingCurveSegment(85,104,1,25,165,375),
        new Update21RatingCurveSegment(105,105,1,25,3550.0/21,0),
        new Update21RatingCurveSegment(106,115,1,25,44375.0/9,-4464125.0/9)
      };
      _critHit=new Update21RatingCurveImpl(segments);
      // =SI(C8<=0;0;MIN((1+1)*25/(1+($C$4*(2,5*17750/9)+-251,5*17750/9)/C8)+0,0002;25))
    }
    // Devastate hit chance
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,50,2,10,160,0),
          new Update21RatingCurveSegment(51,104,2,10,166,-300),
          new Update21RatingCurveSegment(105,105,2,10,3440.0/21,0),
          new Update21RatingCurveSegment(106,115,2,10,43000.0/9,-4325800.0/9)
      };
      _devHit=new Update21RatingCurveImpl(segments);
      // =SI(C9<=0;0;MIN((2+1)*10/(2+($C$4*(2,5*17200/9)+-251,5*17200/9)/C9)+0,0002;10))
    }
    // Critical and devastate hit magnitude increase %
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,105,1,50,300,0),
          new Update21RatingCurveSegment(106,115,1,50,8750,-880250)
      };
      _critAndDevHitMagnitude=new Update21RatingCurveImpl(segments);
      // =SI(C10<=0;0;MIN((1+1)*50/(1+($C$4*(2,5*31500/9)+-251,5*31500/9)/C10)+0,0002;100))
    }
    // Finesse
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,105,10,50,4000,0),
          new Update21RatingCurveSegment(106,115,10,50,350000.0/3,-35210000.0/3)
      };
      _finesse=new Update21RatingCurveImpl(segments);
      // =SI(C11<=0;0;MIN((10+1)*50/(10+($C$4*(2,5*420000/9)+-251,5*420000/9)/C11)+0,0002;50))
    }
    // Damage
    {
      double c=1.0/9;
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,20,c,40,144.45,0),
          new Update21RatingCurveSegment(21,49,c,40,7733.0/58,6451.0/29),
          new Update21RatingCurveSegment(50,50,c,40,1220.0/9,0),
          new Update21RatingCurveSegment(51,59,c,80,305.5,-1691.5),
          new Update21RatingCurveSegment(60,60,c,80,2500.0/9,0),
          new Update21RatingCurveSegment(61,99,c,200,62489.0/76,-560739.0/76),
          new Update21RatingCurveSegment(100,100,c,200,2225.0/3,0),
          new Update21RatingCurveSegment(101,104,c,400,133.0/3,431567.0/3),
          new Update21RatingCurveSegment(105,105,c,400,9900.0/7,0),
          new Update21RatingCurveSegment(106,115,c,400,4125,-251625)
      };
      _damage=new Update21RatingCurveImpl(segments);
      // =SI(C12<=0;0;MIN((1/9+1)*400/(1/9+($C$4*(4125)+-251625)/C12)+0,0002;400))
    }
    // Outgoing healing
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,20,0.43,30,171.5,0),
          new Update21RatingCurveSegment(21,50,1,50,400,0),
          new Update21RatingCurveSegment(51,105,1.4,70,777,-10850),
          new Update21RatingCurveSegment(106,115,1.4,70,353675.0/18,-35579705.0/18)
      };
      _healing=new Update21RatingCurveImpl(segments);
      // =SI(C14<=0;0;MIN((1,4+1)*70/(1,4+($C$4*(2,5*70735/9)+-251,5*70735/9)/C14)+0,0002;70))
    }
    // Resistance
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,50,1,30,180,0),
          new Update21RatingCurveSegment(51,105,1,50,2600.0/7,0),
          new Update21RatingCurveSegment(106,115,1,50,32500.0/3,-3269500.0/3)
      };
      // TODO T2 penalty: OppLvl*90
      _resistance=new Update21RatingCurveImpl(segments);
      // =SI(C16<=0;0;MIN((1+1)*50/(1+($C$4*(2,5*39000/9)+-251,5*39000/9)/SI($C$5="T1";C16;C16-$C$4*90))+0,0002;50))
    }
    // Critical defence
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,105,1,50,100,0),
          new Update21RatingCurveSegment(106,115,1,50,8750.0/3,-880250.0/3)
      };
      // TODO Check cap: 50 or 100?
      _criticalDefence=new Update21RatingCurveImpl(segments);
      // =SI(C17<=0;0;MIN((1+1)*50/(1+($C$4*(2,5*10500/9)+-251,5*10500/9)/C17)+0,0002;100))
    }
    // Incoming healing
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,50,1,15,72,0),
          new Update21RatingCurveSegment(51,104,1,25,243,-8550),
          new Update21RatingCurveSegment(105,105,1,25,3400.0/21,0),
          new Update21RatingCurveSegment(106,115,1,25,42500.0/9,-4275500.0/9)
      };
      // =SI(C18<=0;0;MIN((1+1)*25/(1+($C$4*(2,5*17000/9)+-251,5*17000/9)/C18)+0,0002;25))
      _incomingHealing=new Update21RatingCurveImpl(segments);
    }
    // BPE (avoidance)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,20,2,13,115,0),
          new Update21RatingCurveSegment(21,50,2,13,90,500),
          new Update21RatingCurveSegment(51,105,2,13,200,-5000),
          new Update21RatingCurveSegment(106,115,2,13,40000.0/9,-4024000.0/9)
      };
      // TODO T2 penalty: OppLvl*40
      // =SI(C20<=0;0;MIN((2+1)*13/(2+($C$4*(2,5*16000/9)+-251,5*16000/9)/SI($C$5="T1";C20;C20-$C$4*40))+0,0002;13))
      _avoidance=new Update21RatingCurveImpl(segments);
    }
    // Partial BPE (partial avoidance)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,20,2.5,15,112.5,0),
          new Update21RatingCurveSegment(21,50,2.5,15,75,750),
          new Update21RatingCurveSegment(51,84,2.5,17,775,-34250),
          new Update21RatingCurveSegment(85,95,2.5,20,775,-34250),
          new Update21RatingCurveSegment(96,104,2.5,35,775,-34250),
          new Update21RatingCurveSegment(105,105,2.5,35,9500.0/21,0),
          new Update21RatingCurveSegment(106,115,2.5,35,118750.0/9,-11946250.0/9)
      };
      // TODO T2 penalty: OppLvl*40
      // =SI(C21<=0;0;MIN((2,5+1)*35/(2,5+($C$4*(2,5*47500/9)+-251,5*47500/9)/SI($C$5="T1";C21;C21-$C$4*40))+0,0002;35))
      _partialAvoidance=new Update21RatingCurveImpl(segments);
    }
    // Partial Mitigation
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,20,50,50,3500,0),
          new Update21RatingCurveSegment(21,49,50,50,3650,-3000),
          new Update21RatingCurveSegment(50,104,50,50,1950,82500),
          new Update21RatingCurveSegment(105,105,50,50,57500.0/21,0),
          new Update21RatingCurveSegment(106,115,50,50,718750.0/9,-72306250.0/9)
      };
      // TODO T2 penalty: OppLvl*40
      // Base 10% added later
      // =SI(C22<=0;10;10+MIN((50+1)*50/(50+($C$4*(2,5*287500/9)+-251,5*287500/9)/SI($C$5="T1";C22;C22-$C$4*40))+0,0002;50))
      _partialMitigation=new Update21RatingCurveImpl(segments);
    }
    // Mitigation (light armor)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,104,1.6,40,128,0),
          new Update21RatingCurveSegment(105,105,1.6,40,2720.0/21,0),
          new Update21RatingCurveSegment(106,115,1.6,40,34000.0/9,-3420400.0/9)
      };
      // TODO T2 penalty: Floor(OppLvl*13.5)*5
      // MIN((1,6+1)*40/(1,6+($C$4*(2,5*13600/9)+-251,5*13600/9)/SI($C$5="T1";C30;C30-ENT($C$4*27/2)*5))+0,0002;40);
      _lightMigitation=new Update21RatingCurveImpl(segments);
    }
    // Mitigation (medium armor)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,104,10.0/7,50,148.66,0),
          new Update21RatingCurveSegment(105,105,10.0/7,50,1047.625/7,0),
          new Update21RatingCurveSegment(106,115,10.0/7,50,13095.3125/3,-1317388.4375/3)
      };
      // TODO T2 penalty: Floor(OppLvl*13.5)*5
      // MIN((10/7+1)*50/(10/7+($C$4*(2,5*15714,375/9)+-251,5*15714,375/9)/SI($C$5="T1";C30;C30-ENT($C$4*27/2)*5))+0,0002;50);
      _mediumMigitation=new Update21RatingCurveImpl(segments);
    }
    // Mitigation (heavy armor)
    {
      Update21RatingCurveSegment[] segments={
          new Update21RatingCurveSegment(1,104,1.2,60,166.5,0),
          new Update21RatingCurveSegment(105,105,1.2,60,1168.0/7,0),
          new Update21RatingCurveSegment(106,115,1.2,60,14600.0/3,-1468760.0/3)
      };
      // TODO T2 penalty Floor(OppLvl*13.5)*5
      // MIN((1,2+1)*60/(1,2+($C$4*(2,5*17520/9)+-251,5*17520/9)/SI($C$5="T1";C30;C30-ENT($C$4*27/2)*5))+0,0002;60))))
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
