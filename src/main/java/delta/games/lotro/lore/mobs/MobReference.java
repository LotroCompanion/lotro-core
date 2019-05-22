package delta.games.lotro.lore.mobs;

import delta.games.lotro.utils.Proxy;

/**
 * Mob reference.
 * @author DAM
 */
public class MobReference
{
  private String _genus;
  private String _species;
  private String _subSpecies;
  private Proxy<MobDescription> _mobProxy;

  /**
   * Constructor.
   */
  public MobReference()
  {
    _genus=null;
    _species=null;
    _subSpecies=null;
    _mobProxy=null;
  }

  /**
   * Get the genus.
   * @return the genus (may be <code>null</code>).
   */
  public String getGenus()
  {
    return _genus;
  }

  /**
   * Set the genus.
   * @param genus the genus to set (may be <code>null</code>).
   */
  public void setGenus(String genus)
  {
    _genus=genus;
  }

  /**
   * Get the species.
   * @return the species (may be <code>null</code>).
   */
  public String getSpecies()
  {
    return _species;
  }

  /**
   * Set the species.
   * @param species the species to set (may be <code>null</code>).
   */
  public void setSpecies(String species)
  {
    _species=species;
  }

  /**
   * Get the sub-species.
   * @return the sub-species (may be <code>null</code>).
   */
  public String getSubSpecies()
  {
    return _subSpecies;
  }

  /**
   * Set the sub-species.
   * @param subSpecies the sub-species to set (may be <code>null</code>).
   */
  public void setSubSpecies(String subSpecies)
  {
    _subSpecies=subSpecies;
  }

  /**
   * Get the proxy to a named mob.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<MobDescription> getMobProxy()
  {
    return _mobProxy;
  }

  /**
   * Set the proxy to a named mob.
   * @param mobProxy the proxy to set (may be <code>null</code>).
   */
  public void setMobProxy(Proxy<MobDescription> mobProxy)
  {
    _mobProxy=mobProxy;
  }

  /**
   * Get a displayable label for this mob reference.
   * @return a displayable label.
   */
  public String getLabel()
  {
    if (_mobProxy!=null)
    {
      String mobName=_mobProxy.getName();
      return mobName;
    }
    if (_subSpecies!=null)
    {
      return _subSpecies;
    }
    if (_species!=null)
    {
      return _species;
    }
    if (_genus!=null)
    {
      return _genus;
    }
    return "?";
  }

  @Override
  public String toString()
  {
    return "genus="+_genus+", species="+_species+", sub-species="+_subSpecies+", mob="+_mobProxy;
  }
}
