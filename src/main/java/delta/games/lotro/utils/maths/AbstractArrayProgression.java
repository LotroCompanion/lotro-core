package delta.games.lotro.utils.maths;

/**
 * Progression that uses predefined Y values for all supported X values.
 * @author DAM
 */
public abstract class AbstractArrayProgression extends AbstractProgression implements Progression
{
  protected int[] _xValues;

  /**
   * Constructor.
   * @param identifier Identifier.
   * @param size Number of supported points.
   */
  public AbstractArrayProgression(int identifier, int size)
  {
    super(identifier);
    _xValues=new int[size];
  }

  /**
   * Get the number of points.
   * @return the number of points.
   */
  public int getNumberOfPoints()
  {
    return _xValues.length;
  }

  /**
   * Get the X value at the specified index.
   * @param index Point index, starting at 0.
   * @return A X value.
   */
  public int getX(int index)
  {
    return _xValues[index];
  }

  /**
   * Set the value of a single point.
   * @param index Index of the point to set.
   * @param x X value to set.
   */
  public void set(int index, int x)
  {
    _xValues[index]=x;
  }
}
