package delta.games.lotro.account.io.xml;

import java.io.File;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.account.AccountSummary;

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
    // Name
    String name=DOMParsingTools.getStringAttribute(root.getAttributes(),AccountXMLConstants.ACCOUNT_NAME_ATTR,"");
    summary.setName(name);
  }
}
