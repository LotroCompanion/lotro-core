package delta.games.lotro.character.status.housing;

import delta.common.utils.math.geometry.Vector3D;
import delta.games.lotro.common.enums.HousingHookID;
import delta.games.lotro.common.id.InternalGameId;

/**
 * @author dm
 */
public class HousingItem
{
  private InternalGameId _entityID;
  private int _itemID;
  private HousingHookID _hookID;
  private Vector3D _positionOffset;
  private float _rotationOffset;
  private float _hookRotation;
  private InternalGameId _boundTo;
  private int _quantity; // really?
}
