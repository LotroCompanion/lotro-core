package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.ClassRequirement;

/**
 * XML I/O for class requirements.
 * @author DAM
 */
public class ClassRequirementXMLIO implements RequirementSAXWriter<ClassRequirement>,RequirementXMLReader<ClassRequirement>
{
  @Override
  public ClassRequirement readSAX(Attributes attributes)
  {
    String requiredClasses=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_CLASS_ATTR);
    return ClassRequirement.fromString(requiredClasses);
  }

  @Override
  public ClassRequirement readDOM(NamedNodeMap attrs)
  {
    String requiredClasses=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_CLASS_ATTR,null);
    return ClassRequirement.fromString(requiredClasses);
  }

  @Override
  public void write(AttributesImpl attrs, ClassRequirement classRequirement)
  {
    if (classRequirement!=null)
    {
      String classReqStr=classRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_CLASS_ATTR,XmlWriter.CDATA,classReqStr);
    }
  }
}
