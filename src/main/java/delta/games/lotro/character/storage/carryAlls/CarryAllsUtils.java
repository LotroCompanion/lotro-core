package delta.games.lotro.character.storage.carryAlls;

import delta.games.lotro.account.Account;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.utils.CharacterUtils;

/**
 * Utility methods related to carry-alls management.
 * @author DAM
 */
public class CarryAllsUtils
{
  /**
   * Build a carry-alls manager for a character.
   * @param toon Character to use.
   * @return A carry-alls manager.
   */
  public static CarryAllsManager buildCarryAllManager(CharacterFile toon)
  {
    Account account=CharacterUtils.getAccount(toon);
    if (account!=null)
    {
      String serverName=toon.getServerName();
      CarryAllsManager mgr=new CarryAllsManager(account,serverName);
      return mgr;
    }
    return null;
  }
}
