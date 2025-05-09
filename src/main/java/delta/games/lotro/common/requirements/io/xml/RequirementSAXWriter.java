package delta.games.lotro.common.requirements.io.xml;

import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.common.requirements.Requirement;

/**
 * Interface of a requirement writer.
 * @param <T> Type of managed requirement.
 * @author DAM
 */
public interface RequirementSAXWriter<T extends Requirement>
{
  /**
   * Write a requirement to SAX attributes.
   * @param attrs Storage for data to write.
   * @param requirement Input requirement.
   */
  void write(AttributesImpl attrs, T requirement);
}
