package delta.games.lotro.character.storage.stash.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.storage.stash.ItemsStash;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.io.xml.ItemInstanceXMLWriter;

/**
 * Writes character stashs to XML files.
 * @author DAM
 */
public class StashXMLWriter
{
  /**
   * Write a character stash to a XML file.
   * @param outFile Output file.
   * @param stash Stash to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final ItemsStash stash, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,stash);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, ItemsStash stash) throws SAXException
  {
    AttributesImpl stashAttrs=new AttributesImpl();
    int nextId=stash.getNextId();
    stashAttrs.addAttribute("","",StashXMLConstants.NEXT_ID_ATTR,XmlWriter.CDATA,String.valueOf(nextId));
    hd.startElement("","",StashXMLConstants.STASH_TAG,stashAttrs);
    List<ItemInstance<? extends Item>> items=stash.getAll();
    if (!items.isEmpty())
    {
      ItemInstanceXMLWriter writer=new ItemInstanceXMLWriter();
      for(ItemInstance<? extends Item> item : items)
      {
        writer.writeItemInstance(hd,item);
      }
    }
    hd.endElement("","",StashXMLConstants.STASH_TAG);
  }
}
