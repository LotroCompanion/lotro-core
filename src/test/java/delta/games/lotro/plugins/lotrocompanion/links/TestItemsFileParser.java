package delta.games.lotro.plugins.lotrocompanion.links;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import junit.framework.TestCase;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.files.TextFileReader;
import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.common.utils.text.TextUtils;
import delta.common.utils.url.URLTools;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.io.xml.ItemXMLConstants;
import delta.games.lotro.lore.items.io.xml.ItemXMLWriter;

/**
 * Test class for the ItemsFileParser.
 * @author DAM
 */
public class TestItemsFileParser extends TestCase
{
  /**
   * Test some samples.
   */
  public void testDecoder()
  {
    loadItems("Meva.txt");
    loadItems("Ethell.txt");
    loadItems("Giswald.txt");
  }

  private void loadItems(String sampleName)
  {
    ItemsFileParser parser=new ItemsFileParser();
    String dataStr=loadData(sampleName);
    final List<ItemInstance<? extends Item>> items=parser.doIt(dataStr);

    for(ItemInstance<? extends Item> item : items)
    {
      System.out.println(item.dump());
    }
    // Test XML
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        ItemXMLWriter writer2=new ItemXMLWriter();
        hd.startElement("","",ItemXMLConstants.ITEMS_TAG,new AttributesImpl());
        for(ItemInstance<? extends Item> itemInstance : items)
        {
          writer2.writeItemInstance(hd,itemInstance);
          //writer2.write(hd,itemInstance.getReference());
        }
        hd.endElement("","",ItemXMLConstants.ITEMS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    helper.write(new File(sampleName+".xml"),EncodingNames.UTF_8,writer);
  }

  private String loadData(String sampleName)
  {
    URL url=URLTools.getFromClassPath(sampleName,this);
    TextFileReader r=new TextFileReader(url,EncodingNames.ISO8859_1);
    List<String> lines=TextUtils.readAsLines(r);
    String ret=TextUtils.concatLines(lines);
    return ret;
  }
}
