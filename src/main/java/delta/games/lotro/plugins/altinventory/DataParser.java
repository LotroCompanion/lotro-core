package delta.games.lotro.plugins.altinventory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.storage.AccountServerStorage;
import delta.games.lotro.character.storage.CharacterStorage;
import delta.games.lotro.character.storage.Chest;
import delta.games.lotro.character.storage.StoredItem;
import delta.games.lotro.character.storage.Vault;
import delta.games.lotro.plugins.LuaParser;

/**
 * Parser for the "Data" file of AltInventory.
 * @author DAM
 */
public class DataParser
{
  /**
   * Parse/use data from the "Data" file of AltInventory.
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

  @SuppressWarnings("unchecked")
  private void useData(AccountServerStorage storage, Map<String,Object> data)
  {
    Set<String> keys=data.keySet();
    for(String itemName : keys)
    {
      Map<String,Object> itemData=(Map<String,Object>)data.get(itemName);
      String iconId=(String)itemData.get("IconImageID");
      String backgroundIconId=(String)itemData.get("BackgroundImageID");
      Map<String,Object> itemDataPerCharacter=(Map<String,Object>)itemData.get("Qty");
      for(String key : itemDataPerCharacter.keySet())
      {
        String character=key;
        boolean vault=false;
        boolean sharedVault=false;
        if (character.endsWith(AltInventoryConstants.VAULT))
        {
          vault=true;
          character=character.substring(0,character.length()-AltInventoryConstants.VAULT.length()).trim();
        }
        if (character.equals(AltInventoryConstants.SHARED_STORAGE))
        {
          sharedVault=true;
        }
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
          }
        }
        if (container!=null)
        {
          Map<String,Object> characterData=(Map<String,Object>)itemDataPerCharacter.get(key);
          List<String> ids=new ArrayList<String>(characterData.keySet());
          ids.remove("Subtotal");
          for(String idStr : ids)
          {
            int quantity=NumericTools.parseInt((String)characterData.get(idStr),-1);
            int id=NumericTools.parseInt(idStr,-1);
            if ((id!=-1) && (quantity!=-1))
            {
              if ((!vault) && (!sharedVault))
              {
                id=id-1;
              }
              Chest chest=container.getChest(id);
              StoredItem item=new StoredItem(itemName);
              item.setIconId(NumericTools.parseInteger(iconId));
              item.setBackgroundIconId(NumericTools.parseInteger(backgroundIconId));
              item.setQuantity(quantity);
              chest.addItem(item);
            }
          }
        }
      }
    }
  }
}
