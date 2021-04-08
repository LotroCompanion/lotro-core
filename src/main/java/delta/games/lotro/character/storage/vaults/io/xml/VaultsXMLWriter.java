package delta.games.lotro.character.storage.vaults.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.storage.vaults.Chest;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.lore.items.CountedItemInstance;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.io.xml.ItemXMLWriter;

/**
 * Writes a vault to an XML file.
 * @author DAM
 */
public class VaultsXMLWriter
{
  /**
   * Write a vault to an XML file.
   * @param outFile Output file.
   * @param vault Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final Vault vault, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeVault(hd,vault);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeVault(TransformerHandler hd, Vault vault) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",VaultsXMLConstants.VAULT_TAG,attrs);

    List<Integer> chestIds=vault.getChestIds();
    for(Integer chestId : chestIds)
    {
      Chest chest=vault.getChest(chestId.intValue());
      writeChest(hd,chest);
    }
    hd.endElement("","",VaultsXMLConstants.VAULT_TAG);
  }

  private void writeChest(TransformerHandler hd, Chest chest) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Chest ID
    int chestId=chest.getChestId();
    attrs.addAttribute("","",VaultsXMLConstants.CHEST_ID_ATTR,XmlWriter.CDATA,String.valueOf(chestId));
    // Chest name
    String chestName=chest.getName();
    attrs.addAttribute("","",VaultsXMLConstants.CHEST_NAME_ATTR,XmlWriter.CDATA,chestName);
    hd.startElement("","",VaultsXMLConstants.CHEST_TAG,attrs);
    // Write contents
    ItemXMLWriter writer=new ItemXMLWriter();
    List<CountedItemInstance> countedItemInstances=chest.getAllItemInstances();
    for(CountedItemInstance countedItemInstance : countedItemInstances)
    {
      AttributesImpl slotAttrs=new AttributesImpl();
      // Count
      int count=countedItemInstance.getQuantity();
      if (count>1)
      {
        slotAttrs.addAttribute("","",VaultsXMLConstants.SLOT_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
      }
      hd.startElement("","",VaultsXMLConstants.SLOT_TAG,slotAttrs);
      ItemInstance<? extends Item> itemInstance=countedItemInstance.getItemInstance();
      writer.writeItemInstance(hd,itemInstance);
      hd.endElement("","",VaultsXMLConstants.SLOT_TAG);
    }
    hd.endElement("","",VaultsXMLConstants.CHEST_TAG);
  }
}
