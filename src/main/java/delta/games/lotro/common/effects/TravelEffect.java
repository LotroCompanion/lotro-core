package delta.games.lotro.common.effects;

import delta.games.lotro.common.geo.Position;

/**
 * Travel effect.
 * @author DAM
 */
public class TravelEffect implements EffectAspect
{
  private Position _destination;
  // Scene ID: not very interesting
  //private int _sceneID;
  // 749
  /*
      EffectGenerator_EffectDataList: 
        #1: EffectGenerator_EffectData_SceneID 1879048837
        #2: EffectGenerator_EffectData_Destination rohan_wold_harwick_meadhall_exit
      Effect_Applied_Description: You travel to Harwick.
      // Only used once (in Wildermore):
      Effect_Travel_PrivateEncounter: 1879262955
      */

  /**
   * Constructor.
   */
  public TravelEffect()
  {
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
