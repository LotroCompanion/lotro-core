package delta.games.lotro.lore.housing.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.enums.HouseType;
import delta.games.lotro.common.geo.io.xml.PositionXMLWriter;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.lore.geo.BlockReference;
import delta.games.lotro.lore.housing.HouseDefinition;
import delta.games.lotro.lore.housing.HouseTypeInfo;
import delta.games.lotro.lore.housing.HousingManager;
import delta.games.lotro.lore.housing.Neighborhood;
import delta.games.lotro.lore.housing.NeighborhoodTemplate;
import delta.games.lotro.lore.maps.landblocks.io.xml.LandblocksXMLConstants;

/**
 * Writes housing data to XML files.
 * @author DAM
 */
public class HousingXMLWriter
{
  /**
   * Write housing data to a XML file.
   * @param toFile File to write to.
   * @param mgr Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeHousingData(File toFile, final HousingManager mgr)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeHousingData(hd,mgr);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private void writeHousingData(TransformerHandler hd, HousingManager mgr) throws SAXException
  {
    hd.startElement("","",HousingXMLConstants.HOUSING_TAG,new AttributesImpl());
    // Infos
    for(HouseTypeInfo info : mgr.getHouseInfos())
    {
      writeHouseInfo(hd,info);
    }
    // Houses
    for(HouseDefinition house : mgr.getHouses())
    {
      writeHouse(hd,house);
    }
    // Neighborhood templates
    for(NeighborhoodTemplate neighborhoodTemplate : mgr.getNeighborhoodTemplates())
    {
      writeNeighborhoodTemplate(hd,neighborhoodTemplate);
    }
    // Neighborhoods
    for(Neighborhood neighborhood : mgr.getNeighborhoods())
    {
      writeNeighborhood(hd,neighborhood);
    }
    hd.endElement("","",HousingXMLConstants.HOUSING_TAG);
  }

  /**
   * Write a house.
   * @param hd Output.
   * @param house House to write.
   * @throws SAXException
   */
  private void writeHouse(TransformerHandler hd, HouseDefinition house) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=house.getIdentifier();
    attrs.addAttribute("","",HousingXMLConstants.IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Address
    String address=house.getAddress();
    attrs.addAttribute("","",HousingXMLConstants.ADDRESS_ATTR,XmlWriter.CDATA,address);
    // Description
    String description=house.getDescription();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    // Premium
    boolean premium=house.isPremium();
    if (premium)
    {
      attrs.addAttribute("","",HousingXMLConstants.HOUSE_IS_PREMIUM_ATTR,XmlWriter.CDATA,String.valueOf(premium));
    }
    // Kinship
    boolean kinship=house.isKinship();
    if (kinship)
    {
      attrs.addAttribute("","",HousingXMLConstants.HOUSE_IS_KINSHIP_ATTR,XmlWriter.CDATA,String.valueOf(kinship));
    }
    // Neighborhood template
    int neighborhoodTemplateID=house.getNeighborhoodTemplateID();
    attrs.addAttribute("","",HousingXMLConstants.NEIGHBORHODD_TEMPLATE_ID_ATTR,XmlWriter.CDATA,String.valueOf(neighborhoodTemplateID));
    // Position
    PositionXMLWriter.writePosition(hd,house.getPosition());
    // Tyoe
    HouseType type=house.getInfo().getHouseType();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type.getCode()));
    // Price
    Money price=house.getPrice();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_PRICE_ATTR,XmlWriter.CDATA,String.valueOf(price.getInternalValue()));
    // Upkeep
    Money upkeep=house.getUpkeep();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_UPKEEP_ATTR,XmlWriter.CDATA,String.valueOf(upkeep.getInternalValue()));
    hd.startElement("","",HousingXMLConstants.HOUSE_TAG,attrs);
    // Traits
    for(TraitDescription trait : house.getTraits())
    {
      AttributesImpl traitAttrs=new AttributesImpl();
      int traitID=trait.getIdentifier();
      traitAttrs.addAttribute("","",HousingXMLConstants.IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(traitID));
      String traitName=trait.getName();
      traitAttrs.addAttribute("","",HousingXMLConstants.NAME_ATTR,XmlWriter.CDATA,traitName);
      hd.startElement("","",HousingXMLConstants.TRAIT_TAG,traitAttrs);
      hd.endElement("","",HousingXMLConstants.TRAIT_TAG);
    }
    hd.endElement("","",HousingXMLConstants.HOUSE_TAG);
  }

  /**
   * Write a neighborhood.
   * @param hd Output.
   * @param neighborhood Neighborhood to write.
   * @throws SAXException
   */
  private void writeNeighborhood(TransformerHandler hd, Neighborhood neighborhood) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=neighborhood.getIdentifier();
    attrs.addAttribute("","",HousingXMLConstants.IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=neighborhood.getName();
    attrs.addAttribute("","",HousingXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Template
    NeighborhoodTemplate template=neighborhood.getTemplate();
    int templateID=template.getIdentifier();
    attrs.addAttribute("","",HousingXMLConstants.NEIGHBORHODD_TEMPLATE_ID_ATTR,XmlWriter.CDATA,String.valueOf(templateID));
    hd.startElement("","",HousingXMLConstants.NEIGHBORHOOD_TAG,attrs);
    hd.endElement("","",HousingXMLConstants.NEIGHBORHOOD_TAG);
  }

  /**
   * Write a neighborhood template.
   * @param hd Output.
   * @param neighborhoodTemplate Neighborhood template to write.
   * @throws SAXException
   */
  private void writeNeighborhoodTemplate(TransformerHandler hd, NeighborhoodTemplate neighborhoodTemplate) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=neighborhoodTemplate.getIdentifier();
    attrs.addAttribute("","",HousingXMLConstants.IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=neighborhoodTemplate.getName();
    attrs.addAttribute("","",HousingXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",HousingXMLConstants.NEIGHBORHOOD_TEMPLATE_TAG,attrs);
    // Houses
    for(Integer houseID : neighborhoodTemplate.getHouses())
    {
      AttributesImpl houseAttrs=new AttributesImpl();
      houseAttrs.addAttribute("","",HousingXMLConstants.IDENTIFIER_ATTR,XmlWriter.CDATA,houseID.toString());
      // House name?
      hd.startElement("","",HousingXMLConstants.HOUSE_REF_TAG,houseAttrs);
      hd.endElement("","",HousingXMLConstants.HOUSE_REF_TAG);
    }
    // Blocks
    for(BlockReference blockRef : neighborhoodTemplate.getBlocks())
    {
      AttributesImpl blockAttrs=new AttributesImpl();
      blockAttrs.addAttribute("","",LandblocksXMLConstants.REGION_ATTR,XmlWriter.CDATA,String.valueOf(blockRef.getRegion()));
      blockAttrs.addAttribute("","",LandblocksXMLConstants.BLOCK_X_ATTR,XmlWriter.CDATA,String.valueOf(blockRef.getBlockX()));
      blockAttrs.addAttribute("","",LandblocksXMLConstants.BLOCK_Y_ATTR,XmlWriter.CDATA,String.valueOf(blockRef.getBlockY()));
      hd.startElement("","",LandblocksXMLConstants.LANDBLOCK_TAG,blockAttrs);
      hd.endElement("","",LandblocksXMLConstants.LANDBLOCK_TAG);
    }
    PositionXMLWriter.writePosition(hd,neighborhoodTemplate.getEntrance(),HousingXMLConstants.ENTRANCE_TAG);
    PositionXMLWriter.writePosition(hd,neighborhoodTemplate.getBoot(),HousingXMLConstants.BOOT_TAG);
    hd.endElement("","",HousingXMLConstants.NEIGHBORHOOD_TEMPLATE_TAG);
  }

  /**
   * Write a house info.
   * @param hd Output.
   * @param houseInfo House info to write.
   * @throws SAXException
   */
  private void writeHouseInfo(TransformerHandler hd, HouseTypeInfo houseInfo) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    HouseType type=houseInfo.getHouseType();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type.getCode()));
    // Icon ID
    int iconID=houseInfo.getIconID();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_ICON_ATTR,XmlWriter.CDATA,String.valueOf(iconID));
    // Icon 16 ID
    int icon16ID=houseInfo.getIcon16ID();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_ICON16_ATTR,XmlWriter.CDATA,String.valueOf(icon16ID));
    // Icon 32 ID
    int icon32ID=houseInfo.getIcon32ID();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_ICON32_ATTR,XmlWriter.CDATA,String.valueOf(icon32ID));
    // Large Icon ID
    int largeIconID=houseInfo.getIconLargeID();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_LARGE_ICON_ATTR,XmlWriter.CDATA,String.valueOf(largeIconID));
    // Panorama Icon ID
    int panoramaIconID=houseInfo.getIconPanoramaID();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_PANORAMA_ICON_ATTR,XmlWriter.CDATA,String.valueOf(panoramaIconID));
    // Price
    int price=houseInfo.getPrice().getInternalValue();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_PRICE_ATTR,XmlWriter.CDATA,String.valueOf(price));
    // Upkeep
    int upkeep=houseInfo.getUpkeep().getInternalValue();
    attrs.addAttribute("","",HousingXMLConstants.HOUSE_UPKEEP_ATTR,XmlWriter.CDATA,String.valueOf(upkeep));
    hd.startElement("","",HousingXMLConstants.HOUSE_INFO_TAG,attrs);
    hd.endElement("","",HousingXMLConstants.HOUSE_INFO_TAG);
  }
}
