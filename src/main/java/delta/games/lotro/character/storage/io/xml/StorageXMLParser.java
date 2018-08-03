package delta.games.lotro.character.storage.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.Chest;
import delta.games.lotro.character.storage.ItemsContainer;
import delta.games.lotro.character.storage.Vault;
import delta.games.lotro.character.storage.Wallet;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemProxy;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Parser for the storages stored in XML.
 * @author DAM
 */
public class StorageXMLParser
{
  /**
   * Parse a vault XML file.
   * @param source Source file.
   * @param vault Storage.
   */
  public static void parseVaultXML(File source, Vault vault)
  {
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      parseVault(vault,root);
    }
  }

  private static void parseVault(Vault vault, Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    int used=DOMParsingTools.getIntAttribute(attrs,StorageXMLConstants.VAULT_USED_ATTR,0);
    vault.setUsed(used);
    int capacity=DOMParsingTools.getIntAttribute(attrs,StorageXMLConstants.VAULT_CAPACITY_ATTR,0);
    vault.setCapacity(capacity);

    List<Element> chestTags=DOMParsingTools.getChildTagsByName(root,StorageXMLConstants.CHEST_TAG);
    int chestIndex=0;
    for(Element chestTag : chestTags)
    {
      Chest chest=vault.getChest(chestIndex);
      NamedNodeMap chestAttrs=chestTag.getAttributes();
      String chestName=DOMParsingTools.getStringAttribute(chestAttrs,StorageXMLConstants.CHEST_NAME_ATTR,null);
      chest.setName(chestName);
      parseItemsContainer(chest,chestTag);
      chestIndex++;
    }
  }

  /**
   * Parse a wallet XML file.
   * @param source Source file.
   * @param wallet Storage.
   */
  public static void parseWalletXML(File source, Wallet wallet)
  {
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      parseWallet(wallet,root);
    }
  }

  private static void parseWallet(Wallet wallet, Element root)
  {
    parseItemsContainer(wallet,root);
  }

  private static void parseItemsContainer(ItemsContainer container, Element root)
  {
    ItemsManager itemsManager=ItemsManager.getInstance();
    List<Element> itemTags=DOMParsingTools.getChildTagsByName(root,StorageXMLConstants.ITEM_TAG);
    for(Element itemTag : itemTags)
    {
      NamedNodeMap attrs=itemTag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(attrs,StorageXMLConstants.ITEM_ID_ATTR,0);
      String name=DOMParsingTools.getStringAttribute(attrs,StorageXMLConstants.ITEM_NAME_ATTR,null);
      String icon=DOMParsingTools.getStringAttribute(attrs,StorageXMLConstants.ITEM_ICON_ATTR,null);
      int quantity=DOMParsingTools.getIntAttribute(attrs,StorageXMLConstants.ITEM_QUANTITY_ATTR,0);
      ItemProxy proxy=new ItemProxy();
      if (id!=0)
      {
        Item item=itemsManager.getItem(id);
        proxy.setItem(item);
      }
      proxy.setId(id);
      proxy.setName(name);
      proxy.setIcon(icon);
      CountedItem item=new CountedItem(proxy,quantity);
      container.addItem(item);
    }
  }
}
