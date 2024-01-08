package delta.games.lotro.utils.maths.io.xml;

/**
 * Constants for tags and attribute names used in the
 * XML persistence of derived stats contributions.
 * @author DAM
 */
public class ProgressionsXMLConstants
{
  /**
   * Tag 'progressions'.
   */
  public static final String PROGRESSIONS_TAG="progressions";
  /**
   * Tag 'linearInterpolationProgression'.
   */
  public static final String LINEAR_INTERPOLATION_PROGRESSION_TAG="linearInterpolationProgression";
  /**
   * Tag 'arrayProgression'.
   */
  public static final String ARRAY_PROGRESSION_TAG="arrayProgression";
  /**
   * Tag 'arrayProgression', attribute 'minX'.
   */
  public static final String MIN_X_ATTR="minX";
  /**
   * Tag 'arrayProgression', attribute 'type'.
   */
  public static final String TYPE_ATTR="type";
  /**
   * Tag 'arrayProgression' or 'linearInterpolationProgression', attribute 'identifier'.
   */
  public static final String IDENTIFIER_ATTR="identifier";
  /**
   * Tag 'arrayProgression' or 'linearInterpolationProgression', attribute 'nbPoints'.
   */
  public static final String NB_POINTS_ATTR="nbPoints";
  /**
   * Tag 'point'.
   */
  public static final String POINT_TAG="point";
  /**
   * Tag 'point', attribute 'x'.
   */
  public static final String X_ATTR="x";
  /**
   * Tag 'point', attribute 'count'.
   */
  public static final String COUNT_ATTR="count";
  /**
   * Tag 'point', attribute 'y'.
   */
  public static final String Y_ATTR="y";
}
