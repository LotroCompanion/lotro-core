package delta.games.lotro.common.money;

/**
 * Represents an amount of money.
 * @author DAM
 */
public class Money
{
  private int _goldCoins;
  private int _silverCoins;
  private int _copperCoins;

  /**
   * Default constructor.
   */
  public Money()
  {
    reset();
  }

  /**
   * Copy constructor.
   * @param m Money to copy.
   */
  public Money(Money m)
  {
    _goldCoins=m._goldCoins;
    _silverCoins=m._silverCoins;
    _copperCoins=m._copperCoins;
  }

  /**
   * Full constructor.
   * @param goldCoins Number of gold coins.
   * @param silverCoins Number of silver coins.
   * @param copperCoins Number of copper coins.
   */
  public Money(int goldCoins, int silverCoins, int copperCoins)
  {
    _goldCoins=goldCoins;
    _silverCoins=silverCoins;
    _copperCoins=copperCoins;
  }

  /**
   * Build a money instance from a raw value.
   * @param rawValue Raw value.
   * @return the newly built Money instance.
   */
  public static Money fromRawValue(int rawValue)
  {
    Money ret=new Money();
    ret.setRawValue(rawValue);
    return ret;
  }

  /**
   * Indicates if this amount represents no money.
   * @return <code>true</code> if all coin amounts are zero, <code>false</code> otherwise.
   */
  public boolean isEmpty()
  {
    return ((_goldCoins==0) && (_silverCoins==0) && (_copperCoins==0));
  }

  /**
   * Get the number of gold coins.
   * @return a number of coins.
   */
  public int getGoldCoins()
  {
    return _goldCoins;
  }

  /**
   * Set the number of gold coins.
   * @param goldCoins number of coins to set.
   */
  public void setGoldCoins(int goldCoins)
  {
    _goldCoins=goldCoins;
  }

  /**
   * Get the number of silver coins.
   * @return a number of coins.
   */
  public int getSilverCoins()
  {
    return _silverCoins;
  }

  /**
   * Set the number of silver coins.
   * @param silverCoins number of coins to set.
   */
  public void setSilverCoins(int silverCoins)
  {
    _silverCoins=silverCoins;
  }

  /**
   * Get the number of copper coins.
   * @return a number of coins.
   */
  public int getCopperCoins()
  {
    return _copperCoins;
  }

  /**
   * Set the number of copper coins.
   * @param copperCoins number of coins to set.
   */
  public void setCopperCoins(int copperCoins)
  {
    _copperCoins=copperCoins;
  }

  /**
   * Set raw value.
   * @param copperCoins Copper coins count.
   */
  public void setRawValue(int copperCoins)
  {
    reset();
    setCopperCoins(copperCoins);
    simplify();
  }

  /**
   * Reset.
   */
  public void reset()
  {
    _copperCoins=0;
    _silverCoins=0;
    _goldCoins=0;
  }

  private void simplify()
  {
    while (_copperCoins>=100)
    {
      _copperCoins-=100;
      _silverCoins++;
    }
    while (_silverCoins>=1000)
    {
      _silverCoins-=1000;
      _goldCoins++;
    }
  }

  /**
   * Add an amount of money.
   * @param m Money to add.
   */
  public void add(Money m)
  {
    _goldCoins+=m.getGoldCoins();
    _silverCoins+=m.getSilverCoins();
    _copperCoins+=m.getCopperCoins();
    simplify();
  }

  /**
   * Money addition.
   * @param m1 An amount of money.
   * @param m2 Another amount of money.
   * @return A new amount of money.
   */
  public static Money add(Money m1,Money m2)
  {
    Money ret=new Money(m1);
    ret.add(m2);
    return ret;
  }

  /**
   * Get the internal value for this money amount.
   * @return An internal value (silvers).
   */
  public int getInternalValue()
  {
    return ((_goldCoins*1000)+_silverCoins)*100+_copperCoins;
  }

  /**
   * Get a short label for this value.
   * @return a displayable label.
   */
  public String getShortLabel()
  {
    StringBuilder sb=new StringBuilder();
    if (_goldCoins>0)
    {
      sb.append(_goldCoins).append("g");
    }
    if (_silverCoins>0)
    {
      sb.append(_silverCoins).append("s");
    }
    if (_copperCoins>0)
    {
      sb.append(_copperCoins).append("c");
    }
    return sb.toString();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_goldCoins>0)
    {
      sb.append(_goldCoins).append(" gold");
    }
    if (_silverCoins>0)
    {
      if (sb.length()>0) sb.append(", ");
      sb.append(_silverCoins).append(" silver");
    }
    if (_copperCoins>0)
    {
      if (sb.length()>0) sb.append(", ");
      sb.append(_copperCoins).append(" copper");
    }
    return sb.toString();
  }
}
