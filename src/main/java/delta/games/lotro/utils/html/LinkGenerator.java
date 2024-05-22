package delta.games.lotro.utils.html;

import delta.games.lotro.common.Identifiable;

/**
 * Interface of a link generator.
 * @author DAM
 */
public interface LinkGenerator
{
  /**
   * Build an URL for the given identifiable.
   * @param identifiable Target.
   * @return the result URL.
   */
  String buildURL(Identifiable identifiable);
}
