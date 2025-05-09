package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;

import delta.games.lotro.common.requirements.Requirement;

/**
 * Reads requirements from XML (SAX or DOM).
 * @author DAM
 * @param <T> Type of managed requirement.
 */
public interface RequirementXMLReader<T extends Requirement>
{
  /**
   * Read a requirement (SAX method).
   * @param attrs Input data.
   * @return the loaded requirement or <code>null</code>.
   */
  T readSAX(Attributes attrs);

  /**
   * Read a requirement (DOM method).
   * @param attrs Input data.
   * @return the loaded requirement or <code>null</code>.
   */
  T readDOM(NamedNodeMap attrs);
}
