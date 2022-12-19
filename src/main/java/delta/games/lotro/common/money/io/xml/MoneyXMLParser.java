package delta.games.lotro.common.money.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;

import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.money.Money;

/**
 * Parser for money stored in XML.
 * @author DAM
 */
public class MoneyXMLParser
{
  /**
   * Load money from XML.
   * @param root Quest description tag.
   * @param money Storage for loaded data (<code>null</code> to create if needed).
   * @return a money instance or <code>null</code> if no money tag.
   */
  public static Money loadMoney(Element root, Money money)
  {
    Money ret=null;
    Element moneyTag=DOMParsingTools.getChildTagByName(root,MoneyXMLConstants.MONEY_TAG);
    if (moneyTag!=null)
    {
      ret=(money!=null)?money:new Money();
      NamedNodeMap attrs=moneyTag.getAttributes();
      int gold=DOMParsingTools.getIntAttribute(attrs,MoneyXMLConstants.MONEY_GOLD_ATTR,0);
      ret.setGoldCoins(gold);
      int silver=DOMParsingTools.getIntAttribute(attrs,MoneyXMLConstants.MONEY_SILVER_ATTR,0);
      ret.setSilverCoins(silver);
      int copper=DOMParsingTools.getIntAttribute(attrs,MoneyXMLConstants.MONEY_COPPER_ATTR,0);
      ret.setCopperCoins(copper);
    }
    return ret;
  }

  /**
   * Load money from XML.
   * @param attrs SAX attributes to use.
   * @param money Storage for loaded data (<code>null</code> to create if needed).
   * @return a money instance or <code>null</code> if no money tag.
   */
  public static Money loadMoney(Attributes attrs, Money money)
  {
    int gold=SAXParsingTools.getIntAttribute(attrs,MoneyXMLConstants.MONEY_GOLD_ATTR,0);
    int silver=SAXParsingTools.getIntAttribute(attrs,MoneyXMLConstants.MONEY_SILVER_ATTR,0);
    int copper=SAXParsingTools.getIntAttribute(attrs,MoneyXMLConstants.MONEY_COPPER_ATTR,0);
    Money ret=null;
    if ((gold!=0) || (silver!=0) || (copper!=0))
    {
      ret=(money!=null)?money:new Money();
      ret.setGoldCoins(gold);
      ret.setSilverCoins(silver);
      ret.setCopperCoins(copper);
    }
    return ret;
  }
}
