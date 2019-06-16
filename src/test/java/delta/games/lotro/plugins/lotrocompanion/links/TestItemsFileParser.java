package delta.games.lotro.plugins.lotrocompanion.links;

import java.net.URL;
import java.util.List;

import junit.framework.TestCase;
import delta.common.utils.files.TextFileReader;
import delta.common.utils.text.EncodingNames;
import delta.common.utils.text.TextUtils;
import delta.common.utils.url.URLTools;

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
    parser.doIt(dataStr);
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
