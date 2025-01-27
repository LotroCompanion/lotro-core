package delta.games.lotro.character.status.housing.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.math.geometry.Vector3D;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.housing.AccountHousingData;
import delta.games.lotro.character.status.housing.House;
import delta.games.lotro.character.status.housing.HouseAddress;
import delta.games.lotro.character.status.housing.HouseContents;
import delta.games.lotro.character.status.housing.HouseContentsType;
import delta.games.lotro.character.status.housing.HouseIdentifier;
import delta.games.lotro.character.status.housing.HouseReference;
import delta.games.lotro.character.status.housing.HousingItem;
import delta.games.lotro.common.enums.HousingHookID;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Parser for housing status data stored in XML.
 * @author DAM
 */
public class HousingStatusXMLParser
{
  private LotroEnum<HousingHookID> _hookIDEnum;

  /**
   * Constructor.
   */
  public HousingStatusXMLParser()
  {
    LotroEnumsRegistry registry=LotroEnumsRegistry.getInstance();
    _hookIDEnum=registry.get(HousingHookID.class);
  }

  /**
   * Parse a account housing data XML file.
   * @param source Source file.
   * @return the loaded data.
   */
  public AccountHousingData parseAccountHousingData(File source)
  {
    Element root=DOMParsingTools.parse(source);
    if (root==null)
    {
      return null;
    }
    AccountHousingData ret=new AccountHousingData();
    // Classic house
    Element classicHouseTag=DOMParsingTools.getChildTagByName(root,HousingStatusXMLConstants.CLASSIC_HOUSE_TAG);
    HouseReference classicHouse=parseHouseReference(classicHouseTag);
    ret.setClassicHouse(classicHouse);
    // Premium houses
    List<Element> premiumHouseTags=DOMParsingTools.getChildTagsByName(root,HousingStatusXMLConstants.PREMIUM_HOUSE_TAG);
    for(Element premiumHouseTag : premiumHouseTags)
    {
      HouseReference premiumHouse=parseHouseReference(premiumHouseTag);
      if (premiumHouse!=null)
      {
        ret.addPremiumHouse(premiumHouse);
      }
    }
    return ret;
  }

  private HouseReference parseHouseReference(Element tag)
  {
    if (tag==null)
    {
      return null;
    }
    InternalGameId owner=null;
    String ownerIDStr=DOMParsingTools.getStringAttribute(tag.getAttributes(),HousingStatusXMLConstants.OWNER_ID_ATTR,null);
    if (ownerIDStr!=null)
    {
      owner=InternalGameId.fromString(ownerIDStr);
    }
    HouseAddress address=null;
    Element addressTag=DOMParsingTools.getChildTagByName(tag,HousingStatusXMLConstants.ADDRESS_TAG);
    if (addressTag!=null)
    {
      NamedNodeMap attrs=addressTag.getAttributes();
      int neighborhoodID=DOMParsingTools.getIntAttribute(attrs,HousingStatusXMLConstants.ADDRESS_NEIGHBORHOOD_ID_ATTR,0);
      int houseID=DOMParsingTools.getIntAttribute(attrs,HousingStatusXMLConstants.ADDRESS_HOUSE_ID_ATTR,0);
      address=new HouseAddress(neighborhoodID,houseID);
    }
    if ((owner!=null) && (address!=null))
    {
      return new HouseReference(address,owner);
    }
    return null;
  }

  /**
   * Parse a house XML file.
   * @param source Source file.
   * @return the loaded data.
   */
  public House parseHouseFile(File source)
  {
    Element root=DOMParsingTools.parse(source);
    if (root==null)
    {
      return null;
    }
    NamedNodeMap attrs=root.getAttributes();
    String server=DOMParsingTools.getStringAttribute(attrs,HousingStatusXMLConstants.HOUSE_SERVER_ATTR,"");
    int neighborhoodID=DOMParsingTools.getIntAttribute(attrs,HousingStatusXMLConstants.ADDRESS_NEIGHBORHOOD_ID_ATTR,0);
    int houseID=DOMParsingTools.getIntAttribute(attrs,HousingStatusXMLConstants.ADDRESS_HOUSE_ID_ATTR,0);
    HouseAddress address=new HouseAddress(neighborhoodID,houseID);
    HouseIdentifier id=new HouseIdentifier(server,address);
    House ret=new House(id);
    // Interior
    Element interiorTag=DOMParsingTools.getChildTagByName(root,HousingStatusXMLConstants.INTERIOR_TAG);
    HouseContents interior=parseContents(interiorTag,HouseContentsType.INTERIOR);
    ret.setInterior(interior);
    // Exterior
    Element exteriorTag=DOMParsingTools.getChildTagByName(root,HousingStatusXMLConstants.EXTERIOR_TAG);
    HouseContents exterior=parseContents(exteriorTag,HouseContentsType.EXTERIOR);
    ret.setExterior(exterior);
    return ret;
  }

  private HouseContents parseContents(Element tag, HouseContentsType type)
  {
    if (tag==null)
    {
      return null;
    }
    HouseContents ret=new HouseContents(type);
    // Contents
    List<Element> itemTags=DOMParsingTools.getChildTagsByName(tag,HousingStatusXMLConstants.ITEM_TAG);
    for(Element itemTag : itemTags)
    {
      HousingItem item=parseHousingItem(itemTag);
      ret.addItem(item);
    }
    return ret;
  }

  private HousingItem parseHousingItem(Element itemTag)
  {
    NamedNodeMap attrs=itemTag.getAttributes();
    // Item ID
    int itemID=DOMParsingTools.getIntAttribute(attrs,HousingStatusXMLConstants.ITEM_ID_ATTR,0);
    // Entity ID
    String entityIDStr=DOMParsingTools.getStringAttribute(attrs,HousingStatusXMLConstants.ITEM_ENTITY_ID_ATTR,"");
    InternalGameId entityID=InternalGameId.fromString(entityIDStr);
    // HooK ID
    int hookIDCode=DOMParsingTools.getIntAttribute(attrs,HousingStatusXMLConstants.ITEM_HOOK_ID_ATTR,0);
    HousingHookID hookID=_hookIDEnum.getEntry(hookIDCode);
    HousingItem ret=new HousingItem(itemID,entityID,hookID);
    // Rotation offset
    float rotationOffset=DOMParsingTools.getFloatAttribute(attrs,HousingStatusXMLConstants.ITEM_ROTATION_OFFSET_ATTR,0);
    ret.setRotationOffset(rotationOffset);
    // Hook rotation
    float hookRotation=DOMParsingTools.getFloatAttribute(attrs,HousingStatusXMLConstants.ITEM_HOOK_ROTATION_ATTR,0);
    ret.setHookRotation(hookRotation);
    // Position offset
    Element positionTag=DOMParsingTools.getChildTagByName(itemTag,HousingStatusXMLConstants.POSITION_TAG);
    if (positionTag!=null)
    {
      NamedNodeMap posAttrs=positionTag.getAttributes();
      float x=DOMParsingTools.getFloatAttribute(posAttrs,HousingStatusXMLConstants.X_ATTR,0);
      float y=DOMParsingTools.getFloatAttribute(posAttrs,HousingStatusXMLConstants.Y_ATTR,0);
      float z=DOMParsingTools.getFloatAttribute(posAttrs,HousingStatusXMLConstants.Z_ATTR,0);
      Vector3D positionOffset=new Vector3D();
      positionOffset.set(x,y,z);
      ret.setPositionOffset(positionOffset);
    }
    return ret;
  }
}
