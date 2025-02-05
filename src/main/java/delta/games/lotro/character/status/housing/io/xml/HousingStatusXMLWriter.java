package delta.games.lotro.character.status.housing.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.math.geometry.Vector3D;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.status.housing.AccountHousingData;
import delta.games.lotro.character.status.housing.House;
import delta.games.lotro.character.status.housing.HouseAddress;
import delta.games.lotro.character.status.housing.HouseContents;
import delta.games.lotro.character.status.housing.HouseIdentifier;
import delta.games.lotro.character.status.housing.HouseReference;
import delta.games.lotro.character.status.housing.HousingItem;
import delta.games.lotro.common.enums.HousingHookID;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.geo.io.xml.PositionXMLWriter;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.common.status.io.xml.StatusMetadataIO;
import delta.games.lotro.lore.items.Item;

/**
 * Writes housing data to XML files.
 * @author DAM
 */
public class HousingStatusXMLWriter
{
  /**
   * Write account housing data to a XML file.
   * @param toFile File to write to.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeAccountHousingData(File toFile, final AccountHousingData data)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeAccountHousingData(hd,data);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write account housing data.
   * @param hd Output.
   * @param data Data to write.
   * @throws SAXException
   */
  private void writeAccountHousingData(TransformerHandler hd, AccountHousingData data) throws SAXException
  {
    hd.startElement("","",HousingStatusXMLConstants.ACCOUNT_HOUSES_TAG,new AttributesImpl());
    // Classic house
    HouseReference classicHouse=data.getClassicHouse();
    if (classicHouse!=null)
    {
      writeHouseReference(hd,classicHouse,HousingStatusXMLConstants.CLASSIC_HOUSE_TAG);
    }
    // Premium houses
    for(HouseReference premimumHouse : data.getPremiumHouses())
    {
      writeHouseReference(hd,premimumHouse,HousingStatusXMLConstants.PREMIUM_HOUSE_TAG);
    }
    hd.endElement("","",HousingStatusXMLConstants.ACCOUNT_HOUSES_TAG);
  }

  /**
   * Write an house reference.
   * @param hd Output.
   * @param houseRef House reference to write.
   * @param tag Tag to use.
   * @throws SAXException
   */
  private void writeHouseReference(TransformerHandler hd, HouseReference houseRef, String tag) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Owner
    InternalGameId ownerID=houseRef.getOwner();
    if (ownerID!=null)
    {
      attrs.addAttribute("","",HousingStatusXMLConstants.OWNER_ID_ATTR,XmlWriter.CDATA,ownerID.asPersistedString());
    }
    hd.startElement("","",tag,attrs);
    HouseAddress address=houseRef.getAddress();
    if (address!=null)
    {
      writeAddress(hd,address);
    }
    hd.endElement("","",tag);
  }

  /**
   * Write a house to a XML file.
   * @param toFile File to write to.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeHouseContents(File toFile, final House data)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeHouse(hd,data);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write a house.
   * @param hd Output.
   * @param house House to write.
   * @throws SAXException
   */
  private void writeHouse(TransformerHandler hd, House house) throws SAXException
  {
    AttributesImpl houseAttrs=new AttributesImpl();
    writeHouseIdentifierAttributes(houseAttrs,house.getIdentifier());
    hd.startElement("","",HousingStatusXMLConstants.HOUSE_TAG,houseAttrs);
    // Interior
    HouseContents interior=house.getInterior();
    writeContents(hd,interior,HousingStatusXMLConstants.INTERIOR_TAG);
    // Interior
    HouseContents exterior=house.getExterior();
    writeContents(hd,exterior,HousingStatusXMLConstants.EXTERIOR_TAG);
    hd.endElement("","",HousingStatusXMLConstants.HOUSE_TAG);
  }

  private void writeContents(TransformerHandler hd, HouseContents contents, String tag) throws SAXException
  {
    if (contents==null)
    {
      return;
    }
    hd.startElement("","",tag,new AttributesImpl());
    // Status
    StatusMetadataIO.writeStatusMetadata(hd,contents.getStatusMetadata());
    // House items 
    List<HousingItem> items=contents.getItems();
    for(HousingItem item : items)
    {
      writeHouseItem(hd,item);
    }
    hd.endElement("","",tag);
  }

  private void writeHouseIdentifierAttributes(AttributesImpl attrs, HouseIdentifier houseIdentifier)
  {
    // Server
    String server=houseIdentifier.getServer();
    attrs.addAttribute("","",HousingStatusXMLConstants.HOUSE_SERVER_ATTR,XmlWriter.CDATA,server);
    writeAddressAttributes(attrs,houseIdentifier.getAddress());
  }

  /**
   * Write an address.
   * @param hd Output.
   * @param address Address to write.
   * @throws SAXException
   */
  public static void writeAddress(TransformerHandler hd, HouseAddress address) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    writeAddressAttributes(attrs,address);
    hd.startElement("","",HousingStatusXMLConstants.ADDRESS_TAG,attrs);
    hd.endElement("","",HousingStatusXMLConstants.ADDRESS_TAG);
  }

  private static void writeAddressAttributes(AttributesImpl attrs, HouseAddress address)
  {
    // Neighborhood ID
    int neighborhoodID=address.getNeighborhoodID();
    attrs.addAttribute("","",HousingStatusXMLConstants.ADDRESS_NEIGHBORHOOD_ID_ATTR,XmlWriter.CDATA,String.valueOf(neighborhoodID));
    // Neighborhood name
    // TODO
    // House ID
    int houseID=address.getHouseID();
    attrs.addAttribute("","",HousingStatusXMLConstants.ADDRESS_HOUSE_ID_ATTR,XmlWriter.CDATA,String.valueOf(houseID));
    // House name
    // TODO
  }

  /**
   * Write a house item.
   * @param hd Output.
   * @param houseItem House item to write.
   * @throws SAXException
   */
  private void writeHouseItem(TransformerHandler hd, HousingItem houseItem) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Item
    Item item=houseItem.getItem();
    if (item!=null)
    {
      int itemID=item.getIdentifier();
      attrs.addAttribute("","",HousingStatusXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemID));
      // Name
      String itemName=item.getName();
      if (itemName!=null)
      {
        attrs.addAttribute("","",HousingStatusXMLConstants.NAME_ATTR,XmlWriter.CDATA,itemName);
      }
    }
    // Hook ID
    HousingHookID hookID=houseItem.getHookID();
    if (hookID!=null)
    {
      attrs.addAttribute("","",HousingStatusXMLConstants.ITEM_HOOK_ID_ATTR,XmlWriter.CDATA,String.valueOf(hookID.getCode()));
    }
    // Rotation offset
    float rotationOffset=houseItem.getRotationOffset();
    attrs.addAttribute("","",HousingStatusXMLConstants.ITEM_ROTATION_OFFSET_ATTR,XmlWriter.CDATA,String.valueOf(rotationOffset));
    // Hook rotation
    float hookRotation=houseItem.getHookRotation();
    attrs.addAttribute("","",HousingStatusXMLConstants.ITEM_HOOK_ROTATION_ATTR,XmlWriter.CDATA,String.valueOf(hookRotation));
    // Bound to
    InternalGameId boundTo=houseItem.getBoundTo();
    if (boundTo!=null)
    {
      attrs.addAttribute("","",HousingStatusXMLConstants.ITEM_BOUND_TO_ATTR,XmlWriter.CDATA,boundTo.asPersistedString());
    }
    hd.startElement("","",HousingStatusXMLConstants.ITEM_TAG,attrs);
    // Position
    Position position=houseItem.getPosition();
    PositionXMLWriter.writePosition(hd,position);
    // Position offset
    Vector3D positionOffset=houseItem.getPositionOffset();
    if (positionOffset!=null)
    {
      AttributesImpl positionAttrs=new AttributesImpl();
      positionAttrs.addAttribute("","",HousingStatusXMLConstants.X_ATTR,XmlWriter.CDATA,String.valueOf(positionOffset.getX()));
      positionAttrs.addAttribute("","",HousingStatusXMLConstants.Y_ATTR,XmlWriter.CDATA,String.valueOf(positionOffset.getY()));
      positionAttrs.addAttribute("","",HousingStatusXMLConstants.Z_ATTR,XmlWriter.CDATA,String.valueOf(positionOffset.getZ()));
      hd.startElement("","",HousingStatusXMLConstants.POSITION_OFFSET_TAG,positionAttrs);
      hd.endElement("","",HousingStatusXMLConstants.POSITION_OFFSET_TAG);
    }
    hd.endElement("","",HousingStatusXMLConstants.ITEM_TAG);
  }
}
