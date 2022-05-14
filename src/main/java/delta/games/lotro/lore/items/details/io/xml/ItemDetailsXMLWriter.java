package delta.games.lotro.lore.items.details.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.details.GrantType;
import delta.games.lotro.lore.items.details.GrantedElement;
import delta.games.lotro.lore.items.details.ItemDetail;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.items.details.ItemReputation;
import delta.games.lotro.lore.items.details.ItemXP;
import delta.games.lotro.lore.items.details.VirtueXP;
import delta.games.lotro.lore.reputation.Faction;

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
   * @throws SAXException If an error occurs.
   */
  public void writeDetails(TransformerHandler hd, Item item) throws SAXException
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

  private void writeDetail(TransformerHandler hd, ItemDetail item) throws SAXException
  {
    if (item instanceof GrantedElement)
    {
      writeGrantedElement(hd,(GrantedElement<?>)item);
    }
    else if (item instanceof ItemXP)
    {
      writeItemXPElement(hd,(ItemXP)item);
    }
    else if (item instanceof VirtueXP)
    {
      writeVirtueXPElement(hd,(VirtueXP)item);
    }
    else if (item instanceof ItemReputation)
    {
      writeItemReputationElement(hd,(ItemReputation)item);
    }
  }

  private void writeGrantedElement(TransformerHandler hd, GrantedElement<?> item) throws SAXException
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

  private void writeItemXPElement(TransformerHandler hd, ItemXP itemXP) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Amount
    int amount=itemXP.getAmount();
    attrs.addAttribute("","",ItemDetailsXMLConstants.ITEM_XP_AMOUNT_ATTR,XmlWriter.CDATA,String.valueOf(amount));
    hd.startElement("","",ItemDetailsXMLConstants.ITEM_XP_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.ITEM_XP_TAG);
  }

  private void writeVirtueXPElement(TransformerHandler hd, VirtueXP virtueXP) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Amount
    int amount=virtueXP.getAmount();
    attrs.addAttribute("","",ItemDetailsXMLConstants.VIRTUE_XP_AMOUNT_ATTR,XmlWriter.CDATA,String.valueOf(amount));
    hd.startElement("","",ItemDetailsXMLConstants.VIRTUE_XP_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.ITEM_XP_TAG);
  }

  private void writeItemReputationElement(TransformerHandler hd, ItemReputation reputation) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Faction ID
    Faction faction=reputation.getFaction();
    int factionID=faction.getIdentifier();
    attrs.addAttribute("","",ItemDetailsXMLConstants.REPUTATION_FACTION_ID_ATTR,XmlWriter.CDATA,String.valueOf(factionID));
    // Faction name
    String factionName=faction.getName();
    attrs.addAttribute("","",ItemDetailsXMLConstants.REPUTATION_FACTION_NAME_ATTR,XmlWriter.CDATA,factionName);
    // Amount
    int amount=reputation.getAmount();
    attrs.addAttribute("","",ItemDetailsXMLConstants.REPUTATION_AMOUNT_ATTR,XmlWriter.CDATA,String.valueOf(amount));
    hd.startElement("","",ItemDetailsXMLConstants.REPUTATION_TAG,attrs);
    hd.endElement("","",ItemDetailsXMLConstants.REPUTATION_TAG);
  }
}
