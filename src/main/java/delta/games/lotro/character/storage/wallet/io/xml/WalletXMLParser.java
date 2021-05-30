package delta.games.lotro.character.storage.wallet.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.wallet.Wallet;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Parser for the wallets stored in XML.
 * @author DAM
 */
public class WalletXMLParser
{
  /**
   * Parse a wallet XML file.
   * @param source Source file.
   * @param wallet Storage for loaded data.
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
    ItemsManager itemsManager=ItemsManager.getInstance();
    List<Element> itemTags=DOMParsingTools.getChildTagsByName(root,WalletXMLConstants.ITEM_TAG);
    for(Element itemTag : itemTags)
    {
      NamedNodeMap attrs=itemTag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(attrs,WalletXMLConstants.ITEM_ID_ATTR,0);
      int quantity=DOMParsingTools.getIntAttribute(attrs,WalletXMLConstants.ITEM_QUANTITY_ATTR,0);
      if (id!=0)
      {
        Item item=itemsManager.getItem(id);
        CountedItem<Item> countedItem=new CountedItem<Item>(item,quantity);
        wallet.addItem(countedItem);
      }
    }
  }
}
