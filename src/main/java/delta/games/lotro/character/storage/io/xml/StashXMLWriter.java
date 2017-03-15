package delta.games.lotro.character.storage.io.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.StreamTools;
import delta.games.lotro.character.storage.ItemsStash;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.io.xml.ItemXMLWriter;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Writes character stashs to XML files.
 * @author DAM
 */
public class StashXMLWriter
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  /**
   * Write a character to a XML file.
   * @param outFile Output file.
   * @param stash Character to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, ItemsStash stash, String encoding)
  {
    boolean ret;
    FileOutputStream fos=null;
    try
    {
      fos=new FileOutputStream(outFile);
      SAXTransformerFactory tf=(SAXTransformerFactory)TransformerFactory.newInstance();
      TransformerHandler hd=tf.newTransformerHandler();
      Transformer serializer=hd.getTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");

      StreamResult streamResult=new StreamResult(fos);
      hd.setResult(streamResult);
      hd.startDocument();
      write(hd,stash);
      hd.endDocument();
      ret=true;
    }
    catch (Exception exception)
    {
      _logger.error("",exception);
      ret=false;
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ret;
  }

  private void write(TransformerHandler hd, ItemsStash stash) throws Exception
  {
    AttributesImpl characterAttrs=new AttributesImpl();
    hd.startElement("","",StashXMLConstants.STASH_TAG,characterAttrs);
    List<Item> items=stash.getAll();
    if (items.size()>0)
    {
      ItemXMLWriter writer=new ItemXMLWriter();
      for(Item item : items)
      {
        writer.write(hd,item);
      }
    }
    hd.endElement("","",StashXMLConstants.STASH_TAG);
  }
}
