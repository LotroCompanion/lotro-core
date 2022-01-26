package delta.games.lotro.lore.items.details.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.details.GrantType;
import delta.games.lotro.lore.items.details.GrantedElement;
import delta.games.lotro.lore.items.details.ItemDetail;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.ItemXP;

/**
 * Writer for item details.
 * @author DAM
 */
public class ItemDetailsXMLWriter
{
  /**
   * Write details for an item to the given XML stream.
   * @param hd XML output stream.
   * @param item Item to write.
   * @throws Exception If an error occurs.
   */
  public void writeDetails(TransformerHandler hd, Item item) throws Exception
  {
    ItemDetailsManager mgr=item.getDetails();
    if (mgr==null)
    {
      return;
    }
    List<ItemDetail> details=mgr.getItemDetails();
    for(ItemDetail detail : details)
    {
      writeDetail(hd,detail);
    }
  }

  private void writeDetail(TransformerHandler hd, ItemDetail item) throws Exception
  {
    if (item instanceof GrantedElement)
    {
      writeGrantedElement(hd,(GrantedElement<?>)item);
    }
    else if (item instanceof ItemXP)
    {
      writeItemXPElement(hd,(ItemXP)item);
    }
  }

  private void writeGrantedElement(TransformerHandler hd, GrantedElement<?> item) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    GrantType type=item.getType();
    attrs.addAttribute("","",ItemDetailsXMLConstants.GRANTS_TYPE_ATTR,XmlWriter.CDATA,type.name());
    // ID
    int id=item.getGrantedElement().getIdentifier();
    attrs.addAttribute("","",ItemDetailsXMLConstants.GRANTS_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",ItemDetailsXMLConstants.GRANTS_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.GRANTS_TAG);
  }

  private void writeItemXPElement(TransformerHandler hd, ItemXP itemXP) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Amount
    int amount=itemXP.getAmount();
    attrs.addAttribute("","",ItemDetailsXMLConstants.ITEM_XP_AMOUNT_ATTR,XmlWriter.CDATA,String.valueOf(amount));
    hd.startElement("","",ItemDetailsXMLConstants.ITEM_XP_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.ITEM_XP_TAG);
  }
}
