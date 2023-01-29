package delta.games.lotro.character.storage;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.account.AccountUtils;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.carryAlls.CarryAllInstance;
import delta.games.lotro.character.storage.carryAlls.CarryAllsManager;
import delta.games.lotro.character.storage.carryAlls.CarryAllsUtils;
import delta.games.lotro.character.storage.location.StorageLocation;
import delta.games.lotro.character.storage.location.StorageLocation.LocationType;
import delta.games.lotro.character.storage.vaults.Chest;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.character.storage.vaults.io.VaultsIo;
import delta.games.lotro.character.storage.wallet.Wallet;
import delta.games.lotro.character.storage.wallet.io.xml.WalletsIO;
import delta.games.lotro.common.owner.AccountOwner;
import delta.games.lotro.common.owner.AccountServerOwner;
import delta.games.lotro.common.owner.CharacterOwner;
import delta.games.lotro.common.owner.Owner;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemProvider;
import delta.games.lotro.lore.items.carryalls.CarryAll;

/**
 * Utility methods for storage management.
 * @author DAM
 */
public class StorageUtils
{
  /**
   * Build character items.
   * @param toon Targeted character.
   * @param characterStorage Storage for this character.
   * @return A list of stored items.
   */
  public static List<StoredItem> buildCharacterItems(CharacterFile toon, CharacterStorage characterStorage)
  {
    List<StoredItem> items=new ArrayList<StoredItem>();
    // Build owner
    AccountOwner accountOwner=new AccountOwner("???");
    String server=toon.getServerName();
    AccountServerOwner accountServer=new AccountServerOwner(accountOwner,server);
    String characterName=toon.getName();
    CharacterOwner owner=new CharacterOwner(accountServer,characterName);

    // Own bags
    {
      BagsManager bags=characterStorage.getBags();
      List<StoredItem> storedItems=getAllItems(owner,bags,LocationType.BAG);
      items.addAll(storedItems);
    }
    // Own vault
    {
      Vault ownVault=characterStorage.getOwnVault();
      List<StoredItem> storedItems=getAllItems(owner,ownVault,LocationType.VAULT);
      items.addAll(storedItems);
    }
    // Own wallet
    {
      Wallet ownWallet=characterStorage.getWallet();
      List<StoredItem> storedItems=getAllItems(owner,ownWallet,LocationType.WALLET);
      items.addAll(storedItems);
    }
    // Shared vault
    {
      Vault sharedVault=characterStorage.getSharedVault();
      if (sharedVault!=null)
      {
        List<StoredItem> storedItems=getAllItems(accountServer,sharedVault,LocationType.SHARED_VAULT);
        items.addAll(storedItems);
      }
    }
    // Shared wallet
    {
      Wallet sharedWallet=characterStorage.getSharedWallet();
      if (sharedWallet!=null)
      {
        List<StoredItem> storedItems=getAllItems(accountServer,sharedWallet,LocationType.SHARED_WALLET);
        items.addAll(storedItems);
      }
    }
    CarryAllsManager mgr=CarryAllsUtils.buildCarryAllManager(toon);
    items.addAll(getItemsInCarryAlls(mgr,items));
    return items;
  }

  /**
   * Build account/server items.
   * @param accountOnServer Targeted account/server.
   * @return A list of stored items.
   */
  public static List<StoredItem> buildAccountItems(AccountOnServer accountOnServer)
  {
    List<StoredItem> items=new ArrayList<StoredItem>();

    Account account=accountOnServer.getAccount();
    String accountName=account.getAccountName();
    String serverName=accountOnServer.getServerName();
    AccountOwner accountOwner=new AccountOwner(accountName);
    AccountServerOwner accountServer=new AccountServerOwner(accountOwner,serverName);

    // Characters
    List<CharacterFile> characters=AccountUtils.getCharacters(accountOnServer);
    for(CharacterFile character : characters)
    {
      // Build owner
      String characterName=character.getName();
      CharacterOwner owner=new CharacterOwner(accountServer,characterName);
  
      CharacterStorage characterStorage=StoragesIO.loadCharacterStorage(character);
      // Own bags
      {
        BagsManager container=characterStorage.getBags();
        List<StoredItem> storedItems=getAllItems(owner,container,LocationType.BAG);
        items.addAll(storedItems);
      }
      // Own vault
      {
        Vault container=characterStorage.getOwnVault();
        List<StoredItem> storedItems=getAllItems(owner,container,LocationType.VAULT);
        items.addAll(storedItems);
      }
      // Own wallet
      {
        Wallet ownWallet=characterStorage.getWallet();
        List<StoredItem> storedItems=getAllItems(owner,ownWallet,LocationType.WALLET);
        items.addAll(storedItems);
      }
    }
    // Account/server storage
    {
      // Shared vault
      Vault sharedVault=VaultsIo.load(account,serverName);
      if (sharedVault!=null)
      {
        List<StoredItem> storedItems=getAllItems(accountServer,sharedVault,LocationType.SHARED_VAULT);
        items.addAll(storedItems);
      }
      // Shared wallet
      Wallet sharedWallet=WalletsIO.loadAccountSharedWallet(account,serverName);
      if (sharedWallet!=null)
      {
        List<StoredItem> storedItems=getAllItems(accountServer,sharedWallet,LocationType.SHARED_WALLET);
        items.addAll(storedItems);
      }
    }
    CarryAllsManager mgr=new CarryAllsManager(account,serverName);
    items.addAll(getItemsInCarryAlls(mgr,items));
    return items;
  }

  private static List<StoredItem> getAllItems(Owner owner, BagsManager container, LocationType type)
  {
    List<StoredItem> items=new ArrayList<StoredItem>();
    StorageLocation location=new StorageLocation(owner,type,"Bags");
    List<CountedItem<ItemInstance<? extends Item>>> bagItems=container.getAll();
    for(CountedItem<ItemInstance<? extends Item>> bagItem : bagItems)
    {
      CountedItem<ItemProvider> countedItem=new CountedItem<ItemProvider>(bagItem.getManagedItem(),bagItem.getQuantity());
      StoredItem storedItem=new StoredItem(countedItem);
      storedItem.setOwner(owner);
      storedItem.setLocation(location);
      items.add(storedItem);
    }
    return items;
  }

  private static List<StoredItem> getAllItems(Owner owner, Wallet container, LocationType type)
  {
    StorageLocation location=new StorageLocation(owner,type,null);
    List<StoredItem> items=new ArrayList<StoredItem>();
    for(CountedItem<Item> walletItem : container.getAllItemsSortedByID())
    {
      CountedItem<ItemProvider> countedItem=new CountedItem<ItemProvider>(walletItem.getManagedItem(),walletItem.getQuantity());
      StoredItem storedItem=new StoredItem(countedItem);
      storedItem.setOwner(owner);
      storedItem.setLocation(location);
      items.add(storedItem);
    }
    return items;
  }

  private static List<StoredItem> getAllItems(Owner owner, Vault container, LocationType type)
  {
    List<StoredItem> items=new ArrayList<StoredItem>();
    for(Integer chestId : container.getChestIds())
    {
      Chest chest=container.getChest(chestId.intValue());
      if (chest!=null)
      {
        String chestName=chest.getName();
        if (chestName.length()==0)
        {
          chestName="#"+chestId;
        }
        StorageLocation location=new StorageLocation(owner,type,chestName);
        List<CountedItem<ItemInstance<? extends Item>>> chestItems=chest.getAllItemInstances();
        for(CountedItem<ItemInstance<? extends Item>> chestItem : chestItems)
        {
          CountedItem<ItemProvider> countedItem=new CountedItem<ItemProvider>(chestItem.getManagedItem(),chestItem.getQuantity());
          StoredItem storedItem=new StoredItem(countedItem);
          storedItem.setOwner(owner);
          storedItem.setLocation(location);
          items.add(storedItem);
        }
      }
    }
    return items;
  }

  private static List<StoredItem> getItemsInCarryAlls(CarryAllsManager mgr, List<StoredItem> items)
  {
    List<StoredItem> ret=new ArrayList<StoredItem>();
    for(StoredItem storedItem : items)
    {
      CountedItem<ItemProvider> countedItem=storedItem.getItem();
      ItemProvider itemProvider=countedItem.getManagedItem();
      if (itemProvider instanceof ItemInstance<?>)
      {
        Item item=itemProvider.getItem();
        if (item instanceof CarryAll)
        {
          ItemInstance<?> itemInstance=(ItemInstance<?>)itemProvider;
          CarryAllInstance carryAll=mgr.getCarryAll(itemInstance.getInstanceId());
          if (carryAll!=null)
          {
            Owner owner=storedItem.getOwner();
            List<StoredItem> carryAllItems=getItemsInCarryAll(owner,carryAll);
            ret.addAll(carryAllItems);
          }
        }
      }
    }
    return ret;
  }

  private static List<StoredItem> getItemsInCarryAll(Owner owner, CarryAllInstance carryAll)
  {
    StorageLocation location=new StorageLocation(owner,LocationType.CARRY_ALL,null);
    List<StoredItem> items=new ArrayList<StoredItem>();
    List<CountedItem<Item>> carryAllItems=carryAll.getItems();
    for(CountedItem<Item> item : carryAllItems)
    {
      CountedItem<ItemProvider> countedItem=new CountedItem<ItemProvider>(item.getManagedItem(),item.getQuantity());
      StoredItem storedItem=new StoredItem(countedItem);
      storedItem.setOwner(owner);
      storedItem.setLocation(location);
      items.add(storedItem);
    }
    return items;
  }

  /**
   * Get the item instances from a vault.
   * @param vault Input vault.
   * @return A list of item instances.
   */
  public static List<ItemInstance<? extends Item>> getVaultItems(Vault vault)
  {
    List<ItemInstance<? extends Item>> ret=new ArrayList<ItemInstance<? extends Item>>();
    for(Integer chestId : vault.getChestIds())
    {
      Chest chest=vault.getChest(chestId.intValue());
      if (chest==null)
      {
        continue;
      }
      List<CountedItem<ItemInstance<? extends Item>>> chestItems=chest.getAllItemInstances();
      for(CountedItem<ItemInstance<? extends Item>> chestItem : chestItems)
      {
        ret.add(chestItem.getManagedItem());
      }
    }
    return ret;
  }
}
