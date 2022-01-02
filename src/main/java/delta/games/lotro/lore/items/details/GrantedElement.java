package delta.games.lotro.lore.items.details;

import delta.games.lotro.common.Identifiable;

/**
 * Granted element.
 * @param <T> Type of granted element.
 * @author DAM
 */
public class GrantedElement<T extends Identifiable> extends ItemDetail
{
  private GrantType _type;
  private T _grantedElement;

  /**
   * Constructor.
   * @param type Grant type.
   * @param grantedElement Granted element.
   */
  public GrantedElement(GrantType type, T grantedElement)
  {
    _type=type;
    _grantedElement=grantedElement;
  }

  /**
   * Get the grant type.
   * @return a grant type.
   */
  public GrantType getType()
  {
    return _type;
  }

  /**
   * Get the granted element.
   * @return the granted element.
   */
  public T getGrantedElement()
  {
    return _grantedElement;
  }

  @Override
  public String toString()
  {
    return "grants "+_grantedElement+" ("+_type+")";
  }
}
