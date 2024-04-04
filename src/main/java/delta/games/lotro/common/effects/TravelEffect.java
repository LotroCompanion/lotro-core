package delta.games.lotro.common.effects;

import delta.games.lotro.common.geo.Position;

/**
 * Travel effect.
 * @author DAM
 */
public class TravelEffect extends Effect
{
  private Position _destination;
  // We could add:
  // Scene ID: not very interesting
  // Private encounter ID: only used once (in Wildermore) 
  /*
      EffectGenerator_EffectDataList: 
        #1: EffectGenerator_EffectData_SceneID 1879048837
        #2: EffectGenerator_EffectData_Destination rohan_wold_harwick_meadhall_exit
      Effect_Applied_Description: You travel to Harwick.
      Effect_Travel_PrivateEncounter: 1879262955
      */

  /**
   * Constructor.
   */
  public TravelEffect()
  {
    super();
    _destination=null;
  }

  /**
   * Get the destination position.
   * @return a position.
   */
  public Position getDestination()
  {
    return _destination;
  }

  /**
   * Set the destination position.
   * @param destination Position to set.
   */
  public void setDestination(Position destination)
  {
    _destination=destination;
  }
}
