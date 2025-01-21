package delta.games.lotro.lore.housing.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.enums.HouseType;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.geo.io.xml.PositionXMLConstants;
import delta.games.lotro.common.geo.io.xml.PositionXMLParser;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.lore.geo.BlockReference;
import delta.games.lotro.lore.housing.HouseDefinition;
import delta.games.lotro.lore.housing.HouseTypeInfo;
import delta.games.lotro.lore.housing.HousingManager;
import delta.games.lotro.lore.housing.Neighborhood;
import delta.games.lotro.lore.housing.NeighborhoodTemplate;
import delta.games.lotro.lore.maps.landblocks.io.xml.LandblocksXMLConstants;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for housing reference data stored in XML.
 * @author DAM
 */
public class HousingXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(HousingXMLParser.class);

  private SingleLocaleLabelsManager _i18n;
  private LotroEnum<HouseType> _houseType;

  /**
   * Constructor.
   */
  public HousingXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("housing");
    LotroEnumsRegistry registry=LotroEnumsRegistry.getInstance();
    _houseType=registry.get(HouseType.class);
  }

  /**
   * Parse the housing XML file.
   * @param source Source file.
   * @return the loaded data.
   */
  public HousingManager parseHousingFile(File source)
  {
    HousingManager mgr=new HousingManager();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      // House types
      for(Element houseInfoTypeTag : DOMParsingTools.getChildTagsByName(root,HousingXMLConstants.HOUSE_INFO_TAG))
      {
        HouseTypeInfo houseInfo=parseHouseTypeInfo(houseInfoTypeTag);
        mgr.registerHouseInfo(houseInfo);
      }
      // Houses
      for(Element houseTag : DOMParsingTools.getChildTagsByName(root,HousingXMLConstants.HOUSE_TAG))
      {
        HouseDefinition house=parseHouse(houseTag,mgr);
        mgr.registerHouse(house);
      }
      // Neighborhood templates
      for(Element neighborhoodTemplateTag : DOMParsingTools.getChildTagsByName(root,HousingXMLConstants.NEIGHBORHOOD_TEMPLATE_TAG))
      {
        NeighborhoodTemplate neighborhoodTemplate=parseNeighborhoodTemplate(neighborhoodTemplateTag,mgr);
        mgr.registerNeighborhoodTemplate(neighborhoodTemplate);
      }
      // Neighborhoods
      for(Element neighborhoodTag : DOMParsingTools.getChildTagsByName(root,HousingXMLConstants.NEIGHBORHOOD_TAG))
      {
        Neighborhood neighborhood=parseNeighborhood(neighborhoodTag,mgr);
        mgr.registerNeighborhood(neighborhood);
      }
    }
    return mgr;
  }

  private HouseTypeInfo parseHouseTypeInfo(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();

    int typeCode=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_TYPE_ATTR,0);
    HouseType type=_houseType.getEntry(typeCode);
    HouseTypeInfo ret=new HouseTypeInfo(type);
    // Icon ID
    int iconID=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_ICON_ATTR,0);
    ret.setIconID(iconID);
    // Icon 16 ID
    int icon16ID=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_ICON16_ATTR,0);
    ret.setIcon16ID(icon16ID);
    // Icon 32 ID
    int icon32ID=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_ICON32_ATTR,0);
    ret.setIcon32ID(icon32ID);
    // Large Icon ID
    int largeIconID=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_LARGE_ICON_ATTR,0);
    ret.setIconLargeID(largeIconID);
    // Panorama Icon ID
    int panoramaIconID=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_PANORAMA_ICON_ATTR,0);
    ret.setIconPanoramaID(panoramaIconID);
    // Price
    int price=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_PRICE_ATTR,0);
    ret.setPrice(Money.fromRawValue(price));
    // Upkeep
    int upkeep=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_UPKEEP_ATTR,0);
    ret.setUpkeep(Money.fromRawValue(upkeep));
    return ret;
  }

  private HouseDefinition parseHouse(Element root, HousingManager mgr)
  {
    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.IDENTIFIER_ATTR,0);
    HouseDefinition ret=new HouseDefinition(id);
    // Address
    String address=_i18n.getLabel(String.valueOf(id));
    if (address==null)
    {
      address=DOMParsingTools.getStringAttribute(attrs,HousingXMLConstants.ADDRESS_ATTR,"");
    }
    ret.setAddress(address);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,HousingXMLConstants.HOUSE_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
    ret.setDescription(description);
    // Premium
    boolean premium=DOMParsingTools.getBooleanAttribute(attrs,HousingXMLConstants.HOUSE_IS_PREMIUM_ATTR,false);
    ret.setPremium(premium);
    // Kinship
    boolean kinship=DOMParsingTools.getBooleanAttribute(attrs,HousingXMLConstants.HOUSE_IS_KINSHIP_ATTR,false);
    ret.setKinship(kinship);
    // Neighborhood template
    int neighborhoodTemplateID=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.NEIGHBORHODD_TEMPLATE_ID_ATTR,0);
    ret.setNeighborhoodTemplateID(neighborhoodTemplateID);
    // Tyoe
    int typeCode=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_TYPE_ATTR,0);
    HouseType type=_houseType.getEntry(typeCode);
    HouseTypeInfo typeInfo=mgr.getHouseInfo(type);
    ret.setInfo(typeInfo);
    // Price
    int price=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_PRICE_ATTR,0);
    ret.setPrice(Money.fromRawValue(price));
    // Upkeep
    int upkeep=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.HOUSE_UPKEEP_ATTR,0);
    ret.setUpkeep(Money.fromRawValue(upkeep));
    // Position
    Element positionTag=DOMParsingTools.getChildTagByName(root,PositionXMLConstants.POSITION);
    Position position=PositionXMLParser.parseSimplePosition(positionTag);
    ret.setPosition(position);
    // Traits
    List<TraitDescription> traits=new ArrayList<TraitDescription>();
    for(Element traitTag : DOMParsingTools.getChildTagsByName(root,HousingXMLConstants.TRAIT_TAG))
    {
      NamedNodeMap traitAttrs=traitTag.getAttributes();
      int traitID=DOMParsingTools.getIntAttribute(traitAttrs,HousingXMLConstants.IDENTIFIER_ATTR,0);
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitID);
      if (trait!=null)
      {
        traits.add(trait);
      }
      else
      {
        LOGGER.warn("Trait not found: {}",Integer.valueOf(traitID));
      }
    }
    ret.setTraits(traits);
    return ret;
  }

  private NeighborhoodTemplate parseNeighborhoodTemplate(Element root, HousingManager mgr)
  {
    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.IDENTIFIER_ATTR,0);
    NeighborhoodTemplate ret=new NeighborhoodTemplate(id);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    if (name==null)
    {
      name=DOMParsingTools.getStringAttribute(attrs,HousingXMLConstants.NAME_ATTR,"");
    }
    ret.setName(name);
    // Houses
    for(Element houseTag : DOMParsingTools.getChildTagsByName(root,HousingXMLConstants.HOUSE_REF_TAG))
    {
      NamedNodeMap houseAttrs=houseTag.getAttributes();
      int houseID=DOMParsingTools.getIntAttribute(houseAttrs,HousingXMLConstants.IDENTIFIER_ATTR,0);
      HouseDefinition house=mgr.getHouse(houseID);
      if (house!=null)
      {
        ret.addHouse(house);
      }
      else
      {
        LOGGER.warn("House not found: {}",Integer.valueOf(houseID));
      }
    }
    // Blocks
    for(Element blockTag : DOMParsingTools.getChildTagsByName(root,LandblocksXMLConstants.LANDBLOCK_TAG))
    {
      NamedNodeMap blockAttrs=blockTag.getAttributes();
      int region=DOMParsingTools.getIntAttribute(blockAttrs,LandblocksXMLConstants.REGION_ATTR,1);
      int blockX=DOMParsingTools.getIntAttribute(blockAttrs,LandblocksXMLConstants.BLOCK_X_ATTR,0);
      int blockY=DOMParsingTools.getIntAttribute(blockAttrs,LandblocksXMLConstants.BLOCK_Y_ATTR,0);
      BlockReference blockId=new BlockReference(region,blockX,blockY);
      ret.addBlock(blockId);
    }
    // Entrance
    Element entranceTag=DOMParsingTools.getChildTagByName(root,HousingXMLConstants.ENTRANCE_TAG);
    Position entrance=PositionXMLParser.parseSimplePosition(entranceTag);
    ret.setEntrance(entrance);
    // Boot
    Element bootTag=DOMParsingTools.getChildTagByName(root,HousingXMLConstants.BOOT_TAG);
    Position boot=PositionXMLParser.parseSimplePosition(bootTag);
    ret.setBoot(boot);
    return ret;
  }

  private Neighborhood parseNeighborhood(Element root, HousingManager mgr)
  {
    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.IDENTIFIER_ATTR,0);
    Neighborhood ret=new Neighborhood(id);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    if (name==null)
    {
      name=DOMParsingTools.getStringAttribute(attrs,HousingXMLConstants.NAME_ATTR,"");
    }
    ret.setName(name);
    // Neighborhood template
    int neighborhoodTemplateID=DOMParsingTools.getIntAttribute(attrs,HousingXMLConstants.NEIGHBORHODD_TEMPLATE_ID_ATTR,0);
    NeighborhoodTemplate template=mgr.getNeighborhoodTemplate(neighborhoodTemplateID);
    if (template!=null)
    {
      ret.setTemplate(template);
    }
    {
      LOGGER.warn("Template not found: {}",Integer.valueOf(neighborhoodTemplateID));
    }
    return ret;
  }
}
