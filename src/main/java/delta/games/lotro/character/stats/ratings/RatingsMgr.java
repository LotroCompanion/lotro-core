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
    {
      double[][] critHitValues={{15.0,1190.0/3,70}, {5,794.8,794.8/19}, {5,1075.2,1075.2/19}};
      RatingCurve until105=new CurvedRatingCurveImpl(critHitValues);
      double c=17750/9;
      RatingCurve after106=new MordorRatingCurveImpl(1,c*2.5,-251.5*c,25);
      // =SI(C8<=0;0;MIN((1+1)*25/(1+($C$4*(2,5*17750/9)+-251,5*17750/9)/C8)+0,0002;25))
      _critHit=new CompoundRatingCurve(until105,after106);
    }
    // Devastate hit chance
    {
      double[][] devHitValues={{10.0, 1330.0, 1330.0/9}};
      RatingCurve until105=new CurvedRatingCurveImpl(devHitValues);
      double c=17200/9;
      RatingCurve after106=new MordorRatingCurveImpl(2,c*2.5,-251.5*c,10);
      // =SI(C9<=0;0;MIN((2+1)*10/(2+($C$4*(2,5*17200/9)+-251,5*17200/9)/C9)+0,0002;10))
      _devHit=new CompoundRatingCurve(until105,after106);
    }
    // Critical and devastate hit magnitude increase %
    {
      double[][] critAndDevHitMagnitudeValues={{99.9, 300.0, 99.9*300}};
      RatingCurve until105=new CurvedRatingCurveImpl(critAndDevHitMagnitudeValues);
      double c=31500/9;
      RatingCurve after106=new MordorRatingCurveImpl(1,c*2.5,-251.5*c,50); // TODO 100 or 50?
      // =SI(C10<=0;0;MIN((1+1)*50/(1+($C$4*(2,5*31500/9)+-251,5*31500/9)/C10)+0,0002;100))
      _critAndDevHitMagnitude=new CompoundRatingCurve(until105,after106);
    }
    // Finesse
    {
      double[][] finesseValues={{99.9, 1190.0/3.0, 99.9*1190.0/3.0}};
      RatingCurve until105=new CurvedRatingCurveImpl(finesseValues);
      double c=420000/9;
      RatingCurve after106=new MordorRatingCurveImpl(10,c*2.5,-251.5*c,50);
      // =SI(C11<=0;0;MIN((10+1)*50/(10+($C$4*(2,5*420000/9)+-251,5*420000/9)/C11)+0,0002;50))
      _finesse=new CompoundRatingCurve(until105,after106);
    }
    // Damage
    {
      RatingCurve until105=new DamageRatingCurveImpl();
      RatingCurve after106=new MordorRatingCurveImpl(1.0/9,4125,-251625,400);
      // =SI(C12<=0;0;MIN((1/9+1)*400/(1/9+($C$4*(4125)+-251625)/C12)+0,0002;400))
      _damage=new CompoundRatingCurve(until105,after106);
    }
    // Outgoing healing
    {
      double[][] healingValues={
          { 30, 1190.0/3, 170 }, { 20, 2380.0/3, 595.0/3 }, { 20, 1190, 297.5 }
      };
      RatingCurve until105=new CurvedRatingCurveImpl(healingValues);
      double c=70735/9;
      RatingCurve after106=new MordorRatingCurveImpl(1.4,c*2.5,-251.5*c,70);
      // =SI(C14<=0;0;MIN((1,4+1)*70/(1,4+($C$4*(2,5*70735/9)+-251,5*70735/9)/C14)+0,0002;70))
      _healing=new CompoundRatingCurve(until105,after106);
    }
    // Resistance
    {
      double[][] resistanceValues={
          { 30, 1190.0/3, 170 }, { 20, 2380.0/3, 595.0/3 }
      };
      RatingCurve until105=new CurvedRatingCurveImpl(resistanceValues);
      double c=39000/9;
      RatingCurve after106=new MordorRatingCurveImpl(1,c*2.5,-251.5*c,50);
      // TODO T2 penalty
      // =SI(C16<=0;0;MIN((1+1)*50/(1+($C$4*(2,5*39000/9)+-251,5*39000/9)/SI($C$5="T1";C16;C16-$C$4*90))+0,0002;50))
      _resistance=new CompoundRatingCurve(until105,after106);
    }
    // Critical defence
    {
      double[][] critDefValues={{99.9, 100.0, 99.9*100}};
      RatingCurve until105=new CurvedRatingCurveImpl(critDefValues);
      double c=10500/9;
      RatingCurve after106=new MordorRatingCurveImpl(1,c*2.5,-251.5*c,50); // TODO 50 or 100?
      // =SI(C17<=0;0;MIN((1+1)*50/(1+($C$4*(2,5*10500/9)+-251,5*10500/9)/C17)+0,0002;100))
      _criticalDefence=new CompoundRatingCurve(until105,after106);
    }
    // Incoming healing
    {
      double[][] incomingHealingValues={
          { 15, 1190.0/3, 70 }, { 10, 2380.0/3, 2380.0/27 }
      };
      RatingCurve until105=new CurvedRatingCurveImpl(incomingHealingValues);
      double c=17000/9;
      RatingCurve after106=new MordorRatingCurveImpl(1,c*2.5,-251.5*c,25);
      // =SI(C18<=0;0;MIN((1+1)*25/(1+($C$4*(2,5*17000/9)+-251,5*17000/9)/C18)+0,0002;25))
      _incomingHealing=new CompoundRatingCurve(until105,after106);
    }
    // BPE (avoidance)
    {
      double[][] avoidanceValues={{ 13, 499.95, 43329.0/580 }};
      RatingCurve until105=new CurvedRatingCurveImpl(avoidanceValues);
      double c=16000/9;
      RatingCurve after106=new MordorRatingCurveImpl(2,c*2.5,-251.5*c,13);
      // TODO T2 penalty
      // =SI(C20<=0;0;MIN((2+1)*13/(2+($C$4*(2,5*16000/9)+-251,5*16000/9)/SI($C$5="T1";C20;C20-$C$4*40))+0,0002;13))
      _avoidance=new CompoundRatingCurve(until105,after106);
    }
    // Partial BPE (partial avoidance)
    {
      double[][] partialAvoidanceValues={
          { 15, 396.66, 59499.0/850 }, { 2, 991.66, 49583.0/2450 },
          { 3, 1050, 3150.0/97 }, { 15, 1200, 3600.0/17 },
      };
      RatingCurve until105=new CurvedRatingCurveImpl(partialAvoidanceValues);
      double c=47500/9;
      RatingCurve after106=new MordorRatingCurveImpl(2.5,c*2.5,-251.5*c,35);
      // TODO T2 penalty
      // =SI(C21<=0;0;MIN((2,5+1)*35/(2,5+($C$4*(2,5*47500/9)+-251,5*47500/9)/SI($C$5="T1";C21;C21-$C$4*40))+0,0002;35))
      _partialAvoidance=new CompoundRatingCurve(until105,after106);
    }
    // Partial Mitigation
    {
      double[][] partialMitigationValues={
          { 50, 396.66, 396.66 }
      };
      RatingCurve until105=new CurvedRatingCurveImpl(partialMitigationValues);
      double c=287500/9;
      RatingCurve after106=new MordorRatingCurveImpl(50,c*2.5,-251.5*c,50);
      // TODO T2 penalty
      // Base 10% added later
      // =SI(C22<=0;10;10+MIN((50+1)*50/(50+($C$4*(2,5*287500/9)+-251,5*287500/9)/SI($C$5="T1";C22;C22-$C$4*40))+0,0002;50))
      _partialMitigation=new CompoundRatingCurve(until105,after106);
    }
    // Mitigation (light armor)
    {
      double[][] lightMitigationValues={
          { 20, 150, 37.5 }, { 20, 350, 87.5 }
      };
      RatingCurve until105=new CurvedRatingCurveImpl(lightMitigationValues);
      double c=13600/9;
      RatingCurve after106=new MordorRatingCurveImpl(1.6,c*2.5,-251.5*c,40);
      // TODO T2 penalty
      // MIN((1,6+1)*40/(1,6+($C$4*(2,5*13600/9)+-251,5*13600/9)/SI($C$5="T1";C30;C30-ENT($C$4*27/2)*5))+0,0002;40);
      _lightMigitation=new CompoundRatingCurve(until105,after106);
    }
    // Mitigation (medium armor)
    {
      double[][] mediumMitigationValues={
          { 20, 149.9175, 59967.0/1600 }, { 30, 253.003, 759009.0/7000 }
      };
      RatingCurve until105=new CurvedRatingCurveImpl(mediumMitigationValues);
      double c=15714.375/9;
      RatingCurve after106=new MordorRatingCurveImpl(10/7,c*2.5,-251.5*c,50);
      // TODO T2 penalty
      // MIN((10/7+1)*50/(10/7+($C$4*(2,5*15714,375/9)+-251,5*15714,375/9)/SI($C$5="T1";C30;C30-ENT($C$4*27/2)*5))+0,0002;50);
      _mediumMigitation=new CompoundRatingCurve(until105,after106);
    }
    // Mitigation (heavy armor)
    {
      double[][] heavyMitigationValues={
          { 10, 5697.0/38, 633.0/38 }, { 50, 5697.0/38, 5697.0/38 }
      };
      RatingCurve until105=new CurvedRatingCurveImpl(heavyMitigationValues);
      double c=17520/9;
      RatingCurve after106=new MordorRatingCurveImpl(1.2,c*2.5,-251.5*c,60);
      // TODO T2 penalty
      // MIN((1,2+1)*60/(1,2+($C$4*(2,5*17520/9)+-251,5*17520/9)/SI($C$5="T1";C30;C30-ENT($C$4*27/2)*5))+0,0002;60))))
      _heavyMigitation=new CompoundRatingCurve(until105,after106);
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
