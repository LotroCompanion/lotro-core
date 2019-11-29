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
    _genus="";
    _species="";
    _subSpecies="";
  }

  /**
   * Get the genus.
   * @return the genus.
   */
  public String getGenus()
  {
    return _genus;
  }

  /**
   * Set the genus.
   * @param genus the genus to set.
   */
  public void setGenus(String genus)
  {
    if (genus==null) genus="";
    _genus=genus;
  }

  /**
   * Get the species.
   * @return the species.
   */
  public String getSpecies()
  {
    return _species;
  }

  /**
   * Set the species.
   * @param species the species to set.
   */
  public void setSpecies(String species)
  {
    if (species==null) species="";
    _species=species;
  }

  /**
   * Get the sub-species.
   * @return the sub-species .
   */
  public String getSubSpecies()
  {
    return _subSpecies;
  }

  /**
   * Set the sub-species.
   * @param subSpecies the sub-species to set.
   */
  public void setSubSpecies(String subSpecies)
  {
    if (subSpecies==null) subSpecies="";
    _subSpecies=subSpecies;
  }

  /**
   * Get a displayable label for this object.
   * @return a displayable label.
   */
  public String getLabel()
  {
    if ((_subSpecies.length()>0) || (_species.length()>0))
    {
      if (_species.length()==0)
      {
        return _subSpecies;
      }
      if (_subSpecies.length()==0)
      {
        return _species;
      }
      return _species+"/"+_subSpecies;
    }
    if (_genus.length()>0)
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
