package delta.games.lotro.account.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.account.AccountSummary;
import delta.games.lotro.account.AccountType;

/**
 * Writes LOTRO account summaries to XML files.
 * @author DAM
 */
public class AccountSummaryXMLWriter
{
  /**
   * Write an account summary to a XML file.
   * @param outFile Output file.
   * @param summary Summary to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final AccountSummary summary, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl accountAttrs=new AttributesImpl();
        write(accountAttrs,summary);
        hd.startElement("","",AccountXMLConstants.ACCOUNT_TAG,accountAttrs);
        hd.endElement("","",AccountXMLConstants.ACCOUNT_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
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
    // Account name
    String accountName=account.getName();
    if (accountName.length()>0)
    {
      accountAttrs.addAttribute("","",AccountXMLConstants.ACCOUNT_NAME_ATTR,XmlWriter.CDATA,accountName);
    }
    // Subscription key
    String subscriptionKey=account.getSubscriptionKey();
    if (subscriptionKey.length()>0)
    {
      accountAttrs.addAttribute("","",AccountXMLConstants.ACCOUNT_SUBSCRIPTION_KEY_ATTR,XmlWriter.CDATA,subscriptionKey);
    }
    // Signup date
    Long signupDate=account.getSignupDate();
    if (signupDate!=null)
    {
      accountAttrs.addAttribute("","",AccountXMLConstants.ACCOUNT_SIGNUP_DATE_ATTR,XmlWriter.CDATA,signupDate.toString());
    }
    // Account type
    AccountType accountType=account.getAccountType();
    if (accountType!=null)
    {
      accountAttrs.addAttribute("","",AccountXMLConstants.ACCOUNT_TYPE_ATTR,XmlWriter.CDATA,accountType.name());
    }
    // User data
    String userData=account.getUserData();
    if (userData.length()>0)
    {
      accountAttrs.addAttribute("","",AccountXMLConstants.ACCOUNT_USER_DATA_ATTR,XmlWriter.CDATA,userData);
    }
  }
}
