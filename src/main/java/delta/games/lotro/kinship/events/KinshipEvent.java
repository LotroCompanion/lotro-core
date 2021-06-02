package delta.games.lotro.kinship.events;

import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.kinship.Kinship;
import delta.games.lotro.utils.events.Event;

/**
 * Data for a kinship event.
 * @author DAM
 */
public class KinshipEvent extends Event
{
  private KinshipEventType _type;
  private Kinship _kinship;
  private TypedProperties _props;

  /**
   * Constructor.
   * @param type Event type.
   * @param kinship Targeted kinship.
   */
  public KinshipEvent(KinshipEventType type, Kinship kinship)
  {
    _type=type;
    _kinship=kinship;
  }

  /**
   * Get the type of this event.
   * @return A kinship event type.
   */
  public KinshipEventType getType()
  {
    return _type;
  }

  /**
   * Get the targeted kinship.
   * @return the targeted kinship.
   */
  public Kinship getKinship()
  {
    return _kinship;
  }

  /**
   * Get the properties associated with this event.
   * @return some typed properties.
   */
  public TypedProperties getProperties()
  {
    if (_props==null)
    {
      _props=new TypedProperties();
    }
    return _props;
  }
}
