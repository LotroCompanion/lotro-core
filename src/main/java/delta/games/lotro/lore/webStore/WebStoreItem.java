package delta.games.lotro.lore.webStore;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.BillingGroup;

/**
 * Web store item.
 * @author DAM
 */
public class WebStoreItem implements Identifiable,Named
{
  private int _id;
  private BillingGroup _billingToken;
  private String _name;
  private boolean _freeForSubscribers;
  private String _sku;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public WebStoreItem(int id)
  {
    _id=id;
    _billingToken=null;
    _name="";
    _freeForSubscribers=false;
    _sku="";
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  @Override
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this item.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Get the associated billing token.
   * @return a billing token or <code>null</code>.
   */
  public BillingGroup getBillingToken()
  {
    return _billingToken;
  }

  /**
   * Set the associated billing token.
   * @param billingToken the billing token to set.
   */
  public void setBillingToken(BillingGroup billingToken)
  {
    _billingToken=billingToken;
  }

  /**
   * Indicates if this store item is free for subscribers or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isFreeForSubscribers()
  {
    return _freeForSubscribers;
  }

  /**
   * Set the 'free for subscribers' flag.
   * @param freeForSubscribers value to set.
   */
  public void setFreeForSubscribers(boolean freeForSubscribers)
  {
    _freeForSubscribers=freeForSubscribers;
  }

  /**
   * Get the 'SKU'.
   * @return the SKU.
   */
  public String getSku()
  {
    return _sku;
  }

  /**
   * Set the SKU.
   * @param sku the sku to set.
   */
  public void setSku(String sku)
  {
    if (sku==null)
    {
      sku="";
    }
    _sku=sku;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Web store item: ID=");
    sb.append(_id);
    if (_name.length()>0)
    {
      sb.append(", name=");
      sb.append(_name);
    }
    if (_freeForSubscribers)
    {
      sb.append(", free for subscribers");
    }
    if (_billingToken!=null)
    {
      sb.append(", billing token=");
      sb.append(_billingToken.getLabel());
    }
    if (_sku.length()>0)
    {
      sb.append(", SKU=");
      sb.append(_sku);
    }
    return sb.toString();
  }
}
