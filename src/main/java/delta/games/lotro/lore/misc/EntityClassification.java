package delta.games.lotro.lore.misc;

/**
 * Classification of an entity.
 * @author DAM
 */
public class EntityClassification
{
  private String _genus;
  private String _species;
  private String _subSpecies;

  /**
   * Constructor.
   */
  public EntityClassification()
  {
    _genus=null;
    _species=null;
    _subSpecies=null;
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
   * Get a displayable label for this object.
   * @return a displayable label.
   */
  public String getLabel()
  {
    if ((_subSpecies!=null) || (_species!=null))
    {
      if (_species==null)
      {
        return _subSpecies;
      }
      if (_subSpecies==null)
      {
        return _species;
      }
      return _species+"/"+_subSpecies;
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
    return "genus="+_genus+", species="+_species+", sub-species="+_subSpecies;
  }
}
