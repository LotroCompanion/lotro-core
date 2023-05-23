package delta.games.lotro.account.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.account.AccountOnServer;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Parser for "account on server" data stored in XML.
 * @author DAM
 */
public class AccountOnServerXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @param storage Storage for loaded data.
   */
  public static void parseXML(File source, AccountOnServer storage)
  {
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      parseAccountOnServer(root, storage);
    }
  }

  private static void parseAccountOnServer(Element root, AccountOnServer storage)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Account ID
    String idStr=DOMParsingTools.getStringAttribute(attrs,AccountOnServerXMLConstants.ACCOUNT_ON_SERVER_ID_ATTR,"");
    if (!idStr.isEmpty())
    {
      InternalGameId id=InternalGameId.fromString(idStr);
      storage.setAccountID(id);
    }
  }
}
