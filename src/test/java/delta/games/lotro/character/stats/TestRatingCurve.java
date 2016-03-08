package delta.games.lotro.character.stats;

import delta.games.lotro.character.stats.ratings.RatingCurve;

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
    // Critical hit / critical rating
    double[][] values= { {15.0,1190.0/3,70}, {5,794.8,794.8/19}, {5,1075.2,1075.2/19}};
    RatingCurve curve=new RatingCurve(values);
    System.out.println(curve.getPercentage(10000,100));
    System.out.println(curve.getPercentage(7000,100));
    System.out.println(curve.getPercentage(11183,100));
    System.out.println(curve.getPercentage(7000+794.8/19*100,100));
    System.out.println(curve.getPercentage(16843,100));
  }
}
