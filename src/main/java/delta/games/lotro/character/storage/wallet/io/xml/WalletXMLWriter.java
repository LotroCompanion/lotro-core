package delta.games.lotro.character.storage.wallet.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.storage.wallet.Wallet;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;

/**
 * Writes wallets to XML files.
 * @author DAM
 */
public class WalletXMLWriter
{
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
    hd.startElement("","",WalletXMLConstants.WALLET_TAG,attrs);
    List<CountedItem<Item>> items=wallet.getAllItemsSortedByName();
    for(CountedItem<Item> item : items)
    {
      writeItem(hd,item);
    }
    hd.endElement("","",WalletXMLConstants.WALLET_TAG);
  }

  private void writeItem(TransformerHandler hd, CountedItem<Item> item) throws Exception
  {
    AttributesImpl itemAttrs=new AttributesImpl();
    // ID
    int id=item.getId();
    if (id!=0)
    {
      itemAttrs.addAttribute("","",WalletXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Name
    String name=item.getName();
    if (name!=null)
    {
      itemAttrs.addAttribute("","",WalletXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Icon
    String icon=item.getIcon();
    if ((icon!=null) && (icon.length()>0))
    {
      itemAttrs.addAttribute("","",WalletXMLConstants.ITEM_ICON_ATTR,XmlWriter.CDATA,icon);
    }
    // Quantity
    int quantity=item.getQuantity();
    itemAttrs.addAttribute("","",WalletXMLConstants.ITEM_QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
    hd.startElement("","",WalletXMLConstants.ITEM_TAG,itemAttrs);
    hd.endElement("","",WalletXMLConstants.ITEM_TAG);
  }
}
