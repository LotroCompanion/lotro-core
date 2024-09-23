package delta.games.lotro.character.skills.geometry.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.geometry.Arc;
import delta.games.lotro.character.skills.geometry.Box;
import delta.games.lotro.character.skills.geometry.Shape;
import delta.games.lotro.character.skills.geometry.SkillGeometry;
import delta.games.lotro.character.skills.geometry.Sphere;
import delta.games.lotro.common.enums.AreaEffectAnchorType;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;

/**
 * XML I/O for skill geometry data of skills.
 * @author DAM
 */
public class SkillGeometryXmlIO
{
  /**
   * Write skill geometry data.
   * @param hd Output
   * @param data Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeGeometryData(TransformerHandler hd, SkillGeometry data) throws SAXException
  {
    if (data==null)
    {
      return;
    }
    AttributesImpl attrs=new AttributesImpl();

    // Detection anchor
    AreaEffectAnchorType detectionAnchor=data.getDetectionAnchor();
    if (detectionAnchor!=null)
    {
      attrs.addAttribute("","",SkillGeometryXMLConstants.DETECTION_ANCHOR_ATTR,XmlWriter.CDATA,String.valueOf(detectionAnchor.getCode()));
    }
    // Minimum range
    Float minRange=data.getMinRange();
    if (minRange!=null)
    {
      attrs.addAttribute("","",SkillGeometryXMLConstants.MIN_RANGE_ATTR,XmlWriter.CDATA,minRange.toString());
    }
    // Max range
    float maxRange=data.getMaxRange();
    attrs.addAttribute("","",SkillGeometryXMLConstants.MAX_RANGE_MODS_ATTR,XmlWriter.CDATA,String.valueOf(maxRange));
    // Max range modifiers
    ModPropertyList mods=data.getMaxRangeMods();
    String maxRangeModsStr=ModPropertyListIO.asPersistentString(mods);
    if (!maxRangeModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillGeometryXMLConstants.MAX_RANGE_MODS_ATTR,XmlWriter.CDATA,maxRangeModsStr);
    }
    // Shape
    writeShape(attrs,data.getShape());
    hd.startElement("","",SkillGeometryXMLConstants.GEOMETRY_TAG,attrs);
    hd.endElement("","",SkillGeometryXMLConstants.GEOMETRY_TAG);
  }

  private static void writeShape(AttributesImpl attrs, Shape shape)
  {
    if (shape==null)
    {
      return;
    }
    // Arc
    if (shape instanceof Arc)
    {
      // Type
      attrs.addAttribute("","",SkillGeometryXMLConstants.SHAPE_TYPE_ATTR,XmlWriter.CDATA,"arc");
      Arc arc=(Arc)shape;
      // Radius
      float radius=arc.getRadius();
      attrs.addAttribute("","",SkillGeometryXMLConstants.RADIUS_ATTR,XmlWriter.CDATA,String.valueOf(radius));
      // Degrees
      float degrees=arc.getDegrees();
      attrs.addAttribute("","",SkillGeometryXMLConstants.DEGREES_ATTR,XmlWriter.CDATA,String.valueOf(degrees));
      // Heading offset
      Float headingOffset=arc.getHeadingOffset();
      if (headingOffset!=null)
      {
        attrs.addAttribute("","",SkillGeometryXMLConstants.HEADING_OFFSET_ATTR,XmlWriter.CDATA,headingOffset.toString());
      }
    }
    else if (shape instanceof Box)
    {
      // Type
      attrs.addAttribute("","",SkillGeometryXMLConstants.SHAPE_TYPE_ATTR,XmlWriter.CDATA,"box");
      Box box=(Box)shape;
      // Length
      float length=box.getLength();
      attrs.addAttribute("","",SkillGeometryXMLConstants.LENGTH_ATTR,XmlWriter.CDATA,String.valueOf(length));
      // Width
      float width=box.getWidth();
      attrs.addAttribute("","",SkillGeometryXMLConstants.WIDTH_ATTR,XmlWriter.CDATA,String.valueOf(width));
    }
    else if (shape instanceof Sphere)
    {
      // Type
      attrs.addAttribute("","",SkillGeometryXMLConstants.SHAPE_TYPE_ATTR,XmlWriter.CDATA,"sphere");
      Sphere sphere=(Sphere)shape;
      // Radius
      float radius=sphere.getRadius();
      attrs.addAttribute("","",SkillGeometryXMLConstants.RADIUS_ATTR,XmlWriter.CDATA,String.valueOf(radius));
    }
  }

  /**
   * Read skill geometry data.
   * @param geometryTag Parent tag.
   * @return the loaded data or <code>null</code> if no data.
   */
  public static SkillGeometry readGeometryData(Element geometryTag)
  {
    if (geometryTag==null)
    {
      return null;
    }
    NamedNodeMap attrs=geometryTag.getAttributes();

    SkillGeometry ret=new SkillGeometry();
    // Detection anchor
    Integer detectionAnchorCode=DOMParsingTools.getIntegerAttribute(attrs,SkillGeometryXMLConstants.DETECTION_ANCHOR_ATTR,null);
    if (detectionAnchorCode!=null)
    {
      LotroEnum<AreaEffectAnchorType> anchorTypeEnum=LotroEnumsRegistry.getInstance().get(AreaEffectAnchorType.class);
      AreaEffectAnchorType detectionAnchor=anchorTypeEnum.getEntry(detectionAnchorCode.intValue());
      ret.setDetectionAnchor(detectionAnchor);
    }
    // Minimum range
    Float minRange=DOMParsingTools.getFloatAttribute(attrs,SkillGeometryXMLConstants.MIN_RANGE_ATTR,null);
    ret.setMinRange(minRange);
    // Max range
    float maxRange=DOMParsingTools.getFloatAttribute(attrs,SkillGeometryXMLConstants.MAX_RANGE_ATTR,0);
    ret.setMaxRange(maxRange);
    // Max range modifiers
    String maxRangeModsStr=DOMParsingTools.getStringAttribute(attrs,SkillGeometryXMLConstants.MAX_RANGE_MODS_ATTR,null);
    ModPropertyList mods=ModPropertyListIO.fromPersistedString(maxRangeModsStr);
    ret.setMaxRangeMods(mods);
    // Shape
    Shape shape=readShape(attrs);
    ret.setShape(shape);
    return ret;
  }

  private static Shape readShape(NamedNodeMap attrs)
  {
    Shape ret=null;
    // Type
    String type=DOMParsingTools.getStringAttribute(attrs,SkillGeometryXMLConstants.SHAPE_TYPE_ATTR,null);
    // Arc
    if ("arc".equals(type))
    {
      Arc arc=new Arc();
      ret=arc;
      // Radius
      float radius=DOMParsingTools.getFloatAttribute(attrs,SkillGeometryXMLConstants.RADIUS_ATTR,0);
      arc.setRadius(radius);
      // Degrees
      float degrees=DOMParsingTools.getFloatAttribute(attrs,SkillGeometryXMLConstants.DEGREES_ATTR,0);
      arc.setDegrees(degrees);
      // Heading offset
      Float headingOffset=DOMParsingTools.getFloatAttribute(attrs,SkillGeometryXMLConstants.HEADING_OFFSET_ATTR,null);
      arc.setHeadingOffset(headingOffset);
    }
    // Box
    else if ("box".equals(type))
    {
      Box box=new Box();
      ret=box;
      // Length
      float length=DOMParsingTools.getFloatAttribute(attrs,SkillGeometryXMLConstants.LENGTH_ATTR,0);
      box.setLength(length);
      // Width
      float width=DOMParsingTools.getFloatAttribute(attrs,SkillGeometryXMLConstants.WIDTH_ATTR,0);
      box.setWidth(width);
    }
    // Sphere
    else if ("sphere".equals(type))
    {
      Sphere sphere=new Sphere();
      ret=sphere;
      // Radius
      float radius=DOMParsingTools.getFloatAttribute(attrs,SkillGeometryXMLConstants.RADIUS_ATTR,0);
      sphere.setRadius(radius);
    }
    return ret;
  }
}
