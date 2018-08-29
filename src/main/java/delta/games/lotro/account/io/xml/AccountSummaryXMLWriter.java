package delta.games.lotro.account.io.xml;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.StreamTools;
import delta.games.lotro.account.AccountSummary;
import delta.games.lotro.account.AccountType;

/**
 * Writes LOTRO account summaries to XML files.
 * @author DAM
 */
public class AccountSummaryXMLWriter
{
  private static final Logger LOGGER=Logger.getLogger(AccountSummaryXMLWriter.class);

  private static final String CDATA="CDATA";

  /**
   * Write an account summary to a XML file.
   * @param outFile Output file.
   * @param summary Summary to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, AccountSummary summary, String encoding)
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
      AttributesImpl accountAttrs=new AttributesImpl();
      write(accountAttrs,summary);
      hd.startElement("","",AccountXMLConstants.ACCOUNT_TAG,accountAttrs);
      hd.endElement("","",AccountXMLConstants.ACCOUNT_TAG);
      hd.endDocument();
      ret=true;
    }
    catch (Exception exception)
    {
      LOGGER.error("",exception);
      ret=false;
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ret;
  }

  /**
   * Write account summary attributes.
   * @param accountAttrs Attributes to write to.
   * @param account Source data.
   * @throws Exception If an error occurs.
   */
  public static void write(AttributesImpl accountAttrs, AccountSummary account) throws Exception
  {
    // Name
    String name=account.getName();
    if ((name!=null) && (name.length()>0))
    {
      accountAttrs.addAttribute("","",AccountXMLConstants.ACCOUNT_NAME_ATTR,CDATA,name);
    }
    // Signup date
    Long signupDate=account.getSignupDate();
    if (signupDate!=null)
    {
      accountAttrs.addAttribute("","",AccountXMLConstants.ACCOUNT_SIGNUP_DATE_ATTR,CDATA,signupDate.toString());
    }
    // Account type
    AccountType accountType=account.getAccountType();
    if (accountType!=null)
    {
      accountAttrs.addAttribute("","",AccountXMLConstants.ACCOUNT_TYPE_ATTR,CDATA,accountType.name());
    }
  }
}
