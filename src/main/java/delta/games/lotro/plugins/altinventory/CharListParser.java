package delta.games.lotro.plugins.altinventory;

import java.io.File;
import java.util.Map;
import java.util.Set;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.storage.AccountServerStorage;
import delta.games.lotro.character.storage.CharacterStorage;
import delta.games.lotro.character.storage.Chest;
import delta.games.lotro.character.storage.Vault;
import delta.games.lotro.plugins.LuaParser;

/**
 * Parser for the "CharList" file of AltInventory.
 * @author DAM
 */
public class CharListParser
{
  /**
   * Parse/use data from the "CharList" file of AltInventory.
   * @param storage Storage to use.
   * @param f Input file.
   * @throws Exception If an error occurs.
   */
  public void doIt(AccountServerStorage storage, File f) throws Exception
  {
    LuaParser parser=new LuaParser();
    Map<String,Object> data=parser.read(f);
    useData(storage,data);
  }

  private void useData(AccountServerStorage storage, Map<String,Object> data)
  {
    Set<String> keys=data.keySet();
    for(String key : keys)
    {
      boolean vault=false;
      String character=key;
      boolean sharedVault=false;
      if (key.equals(AltInventoryConstants.SHARED_STORAGE))
      {
        sharedVault=true;
      }
      else if (key.endsWith(AltInventoryConstants.VAULT))
      {
        vault=true;
        character=key.substring(0,key.length()-AltInventoryConstants.VAULT.length()).trim();
      }

      @SuppressWarnings("unchecked")
      Map<String,Object> characterData=(Map<String,Object>)data.get(key);

      Vault container=null;
      if (sharedVault)
      {
        container=storage.getSharedVault();
      }
      else
      {
        CharacterStorage characterStorage=storage.getStorage(character,true);
        if (vault)
        {
          container=characterStorage.getOwnVault();
        }
        else
        {
          container=characterStorage.getBags();
          // Get vault chest names
          @SuppressWarnings("unchecked")
          Map<String,Object> vaultChestNames=(Map<String,Object>)characterData.get("VaultChestNames");
          if (vaultChestNames!=null)
          {
            Vault ownVault=characterStorage.getOwnVault();
            for(String chestIdStr : vaultChestNames.keySet())
            {
              int chestId=NumericTools.parseInt(chestIdStr,-1);
              if (chestId!=-1)
              {
                String chestName=(String)vaultChestNames.get(chestIdStr);
                Chest chest=ownVault.getChest(chestId-1);
                chest.setName(chestName);
              }
            }
          }
        }
      }
      if (container!=null)
      {
        // Get capacity
        Integer used=NumericTools.parseInteger((String)characterData.get("used"));
        if (used!=null)
        {
          container.setUsed(used.intValue());
        }
        Integer capacity=NumericTools.parseInteger((String)characterData.get("capacity"));
        if (capacity!=null)
        {
          container.setCapacity(capacity.intValue());
        }
      }
    }
  }
}
