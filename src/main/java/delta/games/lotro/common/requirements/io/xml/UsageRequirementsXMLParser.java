package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import delta.games.lotro.common.requirements.Requirements;

/**
 * Read usage requirements from XML documents.
 * @author DAM
 */
public class UsageRequirementsXMLParser
{
  private static final RequirementsIO IO=new RequirementsIO();

  /**
   * Load usage requirements from an XML tag.
   * @param requirements Storage for loaded data.
   * @param tag Tag to use.
   */
  public static void parseRequirements(Requirements requirements, Element tag)
  {
    IO.readRequirements(tag.getAttributes(),requirements);
  }

  /**
   * Parse requirements (SAX mode).
   * @param requirements Storage for loaded data.
   * @param attributes Input data.
   */
  public static void parseRequirements(Requirements requirements, Attributes attributes)
  {
    IO.readRequirements(attributes,requirements);
  }
}
