package delta.games.lotro.lore.travels;

import delta.games.lotro.common.Identifiable;

/**
 * Travel destination.
 * @author DAM
 */
public class TravelDestination implements Identifiable
{
  private int _id;
  private String _name;
  private boolean _swiftTravel;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Destination name.
   * @param swiftTravel Swift travel destination or not.
   */
  public TravelDestination(int id, String name, boolean swiftTravel)
  {
    _id=id;
    _name=name;
    _swiftTravel=swiftTravel;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the destination name.
   * @return the destination name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Indicates if this is a swift travel location or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isSwiftTravel()
  {
    return _swiftTravel;
  }

  public String toString()
  {
    return "Destination: ID="+_id+", name="+_name+", swift travel="+_swiftTravel;
  }
}
