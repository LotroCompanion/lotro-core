package delta.games.lotro.account.io.xml;

import java.io.File;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.account.AccountSummary;
import delta.games.lotro.account.AccountType;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Parser for account summary stored in XML.
 * @author DAM
 */
public class AccountSummaryXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(AccountSummaryXMLParser.class);

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
    // ID
    String accountIdStr=DOMParsingTools.getStringAttribute(attrs,AccountXMLConstants.ACCOUNT_ID_ATTR,null);
    if (accountIdStr!=null)
    {
      InternalGameId accountId=InternalGameId.fromString(accountIdStr);
      summary.setAccountID(accountId);
    }
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,AccountXMLConstants.ACCOUNT_NAME_ATTR,"");
    summary.setName(name);
    // Signup date
    long signupDateValue=DOMParsingTools.getLongAttribute(attrs,AccountXMLConstants.ACCOUNT_SIGNUP_DATE_ATTR,0);
    Long signupDate=(signupDateValue!=0)?Long.valueOf(signupDateValue):null;
    summary.setSignupDate(signupDate);
    // Account Type
    String accountTypeStr=DOMParsingTools.getStringAttribute(attrs,AccountXMLConstants.ACCOUNT_TYPE_ATTR,null);
    AccountType accountType=null;
    if (accountTypeStr!=null)
    {
      try
      {
        accountType=AccountType.valueOf(accountTypeStr);
      }
      catch(Exception e)
      {
        LOGGER.warn("Bad account type: "+accountTypeStr);
      }
    }
    summary.setAccountType(accountType);
  }
}
