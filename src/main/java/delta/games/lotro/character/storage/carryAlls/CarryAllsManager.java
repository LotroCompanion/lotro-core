package delta.games.lotro.character.storage.carryAlls;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.account.Account;
import delta.games.lotro.character.storage.carryAlls.io.CarryAllInstancesIo;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Manager for the carry-all of a single account/server.
 * @author DAM
 */
public class CarryAllsManager
{
  private Account _account;
  private String _server;
  private Map<Long,CarryAllInstance> _knownCarryAlls;

  /**
   * Constructor.
   * @param account Account.
   * @param server Server.
   */
  public CarryAllsManager(Account account, String server)
  {
    _account=account;
    _server=server;
    _knownCarryAlls=new HashMap<Long,CarryAllInstance>();
  }

  /**
   * Get a carry-all using its identifier.
   * @param id Identifier of the carry-all to get.
   * @return A carry-all or <code>null</code> if not found.
   */
  public CarryAllInstance getCarryAll(InternalGameId id)
  {
    CarryAllInstance ret=null;
    Long key=Long.valueOf(id.asLong());
    if (!_knownCarryAlls.containsKey(key))
    {
      ret=CarryAllInstancesIo.load(_account,_server,id);
      _knownCarryAlls.put(key,ret);
    }
    ret=_knownCarryAlls.get(key);
    return ret;
  }

  /**
   * Update a carry-all.
   * @param carryAll Carry-all to update.
   */
  public void updateCarryAll(CarryAllInstance carryAll)
  {
    InternalGameId id=carryAll.getId();
    Long key=Long.valueOf(id.asLong());
    _knownCarryAlls.put(key,carryAll);
    CarryAllInstancesIo.save(_account,_server,carryAll);
  }
}
