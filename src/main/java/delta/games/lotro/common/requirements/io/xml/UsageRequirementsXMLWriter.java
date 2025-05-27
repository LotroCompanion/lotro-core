package delta.games.lotro.common.requirements.io.xml;

import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.common.requirements.Requirement;
import delta.games.lotro.common.requirements.Requirements;
import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * Writes usage requirements to XML documents.
 * @author DAM
 */
public class UsageRequirementsXMLWriter
{
  private static final RequirementsIO IO=new RequirementsIO();

  /**
   * Write a usage requirement to the given XML stream.
   * @param attrs Storage for needed XML attributes.
   * @param requirements Requirements to store.
   */
  public static void write(AttributesImpl attrs, Requirements requirements)
  {
    for(Class<? extends Requirement> requirementClass : UsageRequirement.REQUIREMENT_CLASSES)
    {
      Requirement requirement=requirements.getRequirement(requirementClass);
      if (requirement!=null)
      {
        IO.writeRequirement(requirement,attrs);
      }
    }
  }
}
