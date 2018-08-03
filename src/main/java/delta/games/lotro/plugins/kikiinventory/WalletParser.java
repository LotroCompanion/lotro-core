package delta.games.lotro.plugins.kikiinventory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.games.lotro.character.storage.AccountServerStorage;
import delta.games.lotro.character.storage.CharacterStorage;
import delta.games.lotro.character.storage.Wallet;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.ItemProxy;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.finder.ItemSelector;
import delta.games.lotro.lore.items.finder.ItemsFinder;
import delta.games.lotro.plugins.LuaParser;
import delta.games.lotro.plugins.LuaUtils;
import delta.games.lotro.plugins.PluginConstants;

/**
 * Parser for the "Wallet" file of KikiInventory.
 * @author DAM
 */
public class WalletParser
{
  /**
   * Parse/use data from the "Wallet" file of KikiInventory.
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
    String account=storage.getAccount();
    String server=storage.getServer();
    String mostRecentCharacter=PluginConstants.getMostRecentLoggedInCharacter(account,server);

    ItemsFinder finder=ItemsManager.getInstance().getFinder();
    ItemSelector selector=new BarterItemSelector();
    Set<String> keys=data.keySet();
    for(String key : keys)
    {
      if (key.startsWith("~"))
      {
        // Ignore
        continue;
      }

      CharacterStorage characterStorage=storage.getStorage(key,true);
      @SuppressWarnings("unchecked")
      Map<String,Object> toonData=(Map<String,Object>)data.get(key);
      List<String> languages=new ArrayList<String>(toonData.keySet());
      Collections.sort(languages);
      @SuppressWarnings("unchecked")
      Map<String,Object> englishItems=(Map<String,Object>)toonData.get("ENGLISH");
      if (englishItems!=null)
      {
        Wallet wallet=characterStorage.getWallet();
        Wallet sharedWallet=characterStorage.getSharedWallet();
        for(String itemName : englishItems.keySet())
        {
          @SuppressWarnings("unchecked")
          Map<String,Object> itemDef=(Map<String,Object>)englishItems.get(itemName);
          // Quantity
          String qtyStr=(String)itemDef.get("qty");
          Integer qty=LuaUtils.parseIntValue(qtyStr);
          // Icon ID
          String iconIdStr=(String)itemDef.get("iconId");
          Integer iconId=LuaUtils.parseIntValue(iconIdStr);
          // No background icon...

          if (iconId==null)
          {
            continue;
          }
          ItemProxy proxy=finder.buildProxy(itemName,iconId.intValue(),selector);
          int quantity=(qty!=null)?qty.intValue():1;
          CountedItem item=new CountedItem(proxy,quantity);

          // Shared?
          Boolean shared=(Boolean)itemDef.get("acc");
          if ((shared!=null) && (shared.booleanValue()))
          {
            if (key.equals(mostRecentCharacter))
            {
              CountedItem oldItem=sharedWallet.getByName(itemName);
              if (oldItem!=null)
              {
                if (oldItem.getQuantity()!=item.getQuantity())
                {
                  System.out.println("Quantity conflict: " + oldItem.getQuantity()+"!="+item.getQuantity());
                }
              }
              else
              {
                sharedWallet.addItem(item);
              }
            }
          }
          else
          {
            wallet.addItem(item);
          }
        }
      }
    }
  }
}
