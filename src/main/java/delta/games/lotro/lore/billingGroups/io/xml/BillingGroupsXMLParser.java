package delta.games.lotro.lore.billingGroups.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.BillingGroup;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.billingGroups.BillingGroupDescription;
import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.lore.titles.TitlesManager;

/**
 * Parser for billing groups stored in XML.
 * @author DAM
 */
public class BillingGroupsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded billing groups or <code>null</code>.
   */
  public List<BillingGroupDescription> parseXML(File source)
  {
    List<BillingGroupDescription> ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseBillingGroups(root);
    }
    return ret;
  }

  private static List<BillingGroupDescription> parseBillingGroups(Element mainTag)
  {
    List<BillingGroupDescription> ret=new ArrayList<BillingGroupDescription>();
    List<Element> elementTags=DOMParsingTools.getChildTagsByName(mainTag,BillingGroupsXMLConstants.BILLING_GROUP_TAG,false);
    for(Element elementTag : elementTags)
    {
      BillingGroupDescription billingGroupDescription=parseBillingGroup(elementTag);
      if (billingGroupDescription!=null)
      {
        ret.add(billingGroupDescription);
      }
    }
    return ret;
  }

  /**
   * Read a billing group from a tag.
   * @param elementTag Tag to read.
   * @return the loaded billing group or <code>null</code>.
   */
  private static BillingGroupDescription parseBillingGroup(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,BillingGroupsXMLConstants.BILLING_GROUP_ID_ATTR,0);
    LotroEnum<BillingGroup> billingGroupsEnum=LotroEnumsRegistry.getInstance().get(BillingGroup.class);
    BillingGroup group=billingGroupsEnum.getEntry(code);
    if (group==null)
    {
      return null;
    }
    BillingGroupDescription ret=new BillingGroupDescription(group);
    // Titles
    List<Element> titleTags=DOMParsingTools.getChildTagsByName(elementTag,BillingGroupsXMLConstants.TITLE_TAG);
    for(Element titleTag : titleTags)
    {
      NamedNodeMap titleAttrs=titleTag.getAttributes();
      int titleID=DOMParsingTools.getIntAttribute(titleAttrs,BillingGroupsXMLConstants.TITLE_ID_ATTR,0);
      TitlesManager titlesMgr=TitlesManager.getInstance();
      TitleDescription title=titlesMgr.getTitle(titleID);
      if (title!=null)
      {
        ret.addAccountTitle(title);
      }
    }
    return ret;
  }
}
