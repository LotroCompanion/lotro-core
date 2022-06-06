package delta.games.lotro.lore.webStore.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.BillingGroup;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.webStore.WebStoreItem;

/**
 * Parser for web store items stored in XML.
 * @author DAM
 */
public class WebStoreItemsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded web store items or <code>null</code>.
   */
  public List<WebStoreItem> parseXML(File source)
  {
    List<WebStoreItem> ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseWebStoreItems(root);
    }
    return ret;
  }

  private static List<WebStoreItem> parseWebStoreItems(Element mainTag)
  {
    List<WebStoreItem> ret=new ArrayList<WebStoreItem>();
    List<Element> elementTags=DOMParsingTools.getChildTagsByName(mainTag,WebStoreItemsXMLConstants.WEB_STORE_ITEM_TAG,false);
    for(Element elementTag : elementTags)
    {
      WebStoreItem webStoreItem=parseWebStoreItem(elementTag);
      if (webStoreItem!=null)
      {
        ret.add(webStoreItem);
      }
    }
    return ret;
  }

  /**
   * Read a web store item from a tag.
   * @param elementTag Tag to read.
   * @return the loaded web store item or <code>null</code>.
   */
  private static WebStoreItem parseWebStoreItem(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,WebStoreItemsXMLConstants.WEB_STORE_ITEM_ID_ATTR,0);
    WebStoreItem ret=new WebStoreItem(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,WebStoreItemsXMLConstants.WEB_STORE_ITEM_NAME_ATTR,"");
    ret.setName(name);
    // Free for subscribers
    boolean freeForSubscribers=DOMParsingTools.getBooleanAttribute(attrs,WebStoreItemsXMLConstants.WEB_STORE_ITEM_FREE_FOR_SUBSCRIBERS_ATTR,false);
    ret.setFreeForSubscribers(freeForSubscribers);
    // Billing group
    int billingGroupId=DOMParsingTools.getIntAttribute(attrs,WebStoreItemsXMLConstants.WEB_STORE_ITEM_BILLING_GROUP_ATTR,-1);
    if (billingGroupId>=0)
    {
      LotroEnum<BillingGroup> billingGroupsEnum=LotroEnumsRegistry.getInstance().get(BillingGroup.class);
      BillingGroup group=billingGroupsEnum.getEntry(billingGroupId);
      ret.setBillingToken(group);
    }
    // SKU
    return ret;
  }
}
