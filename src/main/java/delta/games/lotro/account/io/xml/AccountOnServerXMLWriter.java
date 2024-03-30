package delta.games.lotro.account.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Writes "account on server" data to XML files.
 * @author DAM
 */
public class AccountOnServerXMLWriter
{
  /**
   * Write an "account on server" to a XML file.
   * @param outFile Output file.
   * @param accountOnServer Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File outFile, final AccountOnServer accountOnServer)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,accountOnServer);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write "account on server" data.
   * @param hd Output stream.
   * @param accountOnServer Source data.
   * @throws SAXException If an error occurs.
   */
  private static void write(TransformerHandler hd, AccountOnServer accountOnServer) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Account ID
    InternalGameId id=accountOnServer.getAccountID();
    if (id!=null)
    {
      String idStr=id.asPersistedString();
      attrs.addAttribute("","",AccountOnServerXMLConstants.ACCOUNT_ON_SERVER_ID_ATTR,XmlWriter.CDATA,idStr);
    }
    hd.startElement("","",AccountOnServerXMLConstants.ACCOUNT_ON_SERVER_TAG,attrs);
    hd.endElement("","",AccountOnServerXMLConstants.ACCOUNT_ON_SERVER_TAG);
  }
}
