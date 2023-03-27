package delta.games.lotro.account.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.account.AccountReference;
import delta.games.lotro.account.AccountSummary;
import delta.games.lotro.account.AccountType;

/**
 * Parser for account summary stored in XML.
 * @author DAM
 */
public class AccountSummaryXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed summary or <code>null</code>.
   */
  public AccountSummary parseXML(File source)
  {
    AccountSummary summary=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      summary=new AccountSummary();
      parseAccount(root, summary);
    }
    return summary;
  }

  /**
   * Read account summary attributes from a tag.
   * @param root Tag to read.
   * @param summary Summary to write to.
   */
  public static void parseAccount(Element root, AccountSummary summary)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Account name
    String name=DOMParsingTools.getStringAttribute(attrs,AccountXMLConstants.ACCOUNT_NAME_ATTR,"");
    // Subscription key
    String subscriptionKey=DOMParsingTools.getStringAttribute(attrs,AccountXMLConstants.ACCOUNT_SUBSCRIPTION_KEY_ATTR,"");
    AccountReference id=new AccountReference(name,subscriptionKey);
    summary.setAccountID(id);
    // Signup date
    long signupDateValue=DOMParsingTools.getLongAttribute(attrs,AccountXMLConstants.ACCOUNT_SIGNUP_DATE_ATTR,0);
    Long signupDate=(signupDateValue!=0)?Long.valueOf(signupDateValue):null;
    summary.setSignupDate(signupDate);
    // Account Type
    AccountType accountType=DOMParsingTools.getEnumAttribute(attrs,AccountXMLConstants.ACCOUNT_TYPE_ATTR,null,AccountType.class);
    summary.setAccountType(accountType);
    // User data
    String userData=DOMParsingTools.getStringAttribute(attrs,AccountXMLConstants.ACCOUNT_USER_DATA_ATTR,"");
    summary.setUserData(userData);
  }
}
