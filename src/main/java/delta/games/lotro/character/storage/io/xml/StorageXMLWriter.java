package delta.games.lotro.character.storage.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.storage.Chest;
import delta.games.lotro.character.storage.ItemsContainer;
import delta.games.lotro.character.storage.Vault;
import delta.games.lotro.character.storage.Wallet;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.ItemProxy;

/**
 * Writes storages to XML files.
 * @author DAM
 */
public class StorageXMLWriter
{
  private static final String CDATA="CDATA";

  /**
   * Write a vault to an XML file.
   * @param outFile Output file.
   * @param vault Vault to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeVault(File outFile, final Vault vault)
  {
    return writeVault(outFile,vault,StorageXMLConstants.VAULT_TAG,EncodingNames.UTF_8);
  }

  /**
   * Write bags contents to an XML file.
   * @param outFile Output file.
   * @param bags Bags to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeBags(File outFile, final Vault bags)
  {
    return writeVault(outFile,bags,StorageXMLConstants.BAGS_TAG,EncodingNames.UTF_8);
  }

  /**
   * Write a vault to an XML file.
   * @param outFile Output file.
   * @param vault Vault to write.
   * @param tag Tag to use.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  private boolean writeVault(File outFile, final Vault vault, final String tag, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeVault(hd,vault,tag);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a vault to the given XML stream.
   * @param hd XML output stream.
   * @param vault Vault to write.
   * @param tag Vault tag (vault or bags).
   * @throws Exception If an error occurs.
   */
  private void writeVault(TransformerHandler hd, Vault vault, String tag) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    int used=vault.getUsed();
    attrs.addAttribute("","",StorageXMLConstants.VAULT_USED_ATTR,CDATA,String.valueOf(used));
    int capacity=vault.getCapacity();
    attrs.addAttribute("","",StorageXMLConstants.VAULT_CAPACITY_ATTR,CDATA,String.valueOf(capacity));
    hd.startElement("","",tag,attrs);

    int nbChests=vault.getChestCount();
    for(int i=0;i<nbChests;i++)
    {
      Chest chest=vault.getChest(i);
      AttributesImpl chestAttr=new AttributesImpl();

      // Chest name
      String chestName=chest.getName();
      if (chestName!=null)
      {
        chestAttr.addAttribute("","",StorageXMLConstants.CHEST_NAME_ATTR,CDATA,chestName);
      }
      hd.startElement("","",StorageXMLConstants.CHEST_TAG,chestAttr);

      writeContainer(hd,chest);
      hd.endElement("","",StorageXMLConstants.CHEST_TAG);
    }
    hd.endElement("","",tag);
  }

  /**
   * Write a wallet to an XML file.
   * @param outFile Output file.
   * @param wallet Wallet to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeWallet(File outFile, final Wallet wallet)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeWallet(hd,wallet);
      }
    };
    boolean ret=helper.write(outFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write a wallet to the given XML stream.
   * @param hd XML output stream.
   * @param wallet Wallet to write.
   * @throws Exception If an error occurs.
   */
  private void writeWallet(TransformerHandler hd, Wallet wallet) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",StorageXMLConstants.WALLET_TAG,attrs);
    writeContainer(hd,wallet);
    hd.endElement("","",StorageXMLConstants.WALLET_TAG);
  }

  private void writeContainer(TransformerHandler hd, ItemsContainer container) throws Exception
  {
    List<CountedItem> items=container.getAllItemsByName();
    for(CountedItem storedItem : items)
    {
      AttributesImpl itemAttrs=new AttributesImpl();
      ItemProxy proxy=storedItem.getProxy();
      // ID
      int id=proxy.getId();
      if (id!=0)
      {
        itemAttrs.addAttribute("","",StorageXMLConstants.ITEM_ID_ATTR,CDATA,String.valueOf(id));
      }
      // Name
      String name=proxy.getName();
      if (name!=null)
      {
        itemAttrs.addAttribute("","",StorageXMLConstants.ITEM_NAME_ATTR,CDATA,name);
      }
      // Icon
      String icon=proxy.getIcon();
      if (icon!=null)
      {
        itemAttrs.addAttribute("","",StorageXMLConstants.ITEM_ICON_ATTR,CDATA,icon);
      }
      // Quantity
      int quantity=storedItem.getQuantity();
      itemAttrs.addAttribute("","",StorageXMLConstants.ITEM_QUANTITY_ATTR,CDATA,String.valueOf(quantity));
      hd.startElement("","",StorageXMLConstants.ITEM_TAG,itemAttrs);
      hd.endElement("","",StorageXMLConstants.ITEM_TAG);
    }
  }
}
