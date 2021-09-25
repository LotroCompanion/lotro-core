package delta.games.lotro.lore.agents;

import java.util.List;

import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;

/**
 * Classification of an entity.
 * @author DAM
 */
public class EntityClassification
{
  private List<Genus> _genus;
  private Species _species;
  private SubSpecies _subSpecies;

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
   * Get the genuses.
   * @return A genus or <code>null</code> if not set.
   */
  public List<Genus> getGenuses()
  {
    return _genus;
  }

  /**
   * Get the genus label.
   * @return a genus label or <code>null</code> if none.
   */
  public String getGenusLabel()
  {
    String ret=null;
    if ((_genus!=null) && (_genus.size()>0))
    {
      StringBuilder sb=new StringBuilder();
      for(Genus genus : _genus)
      {
        if (sb.length()>0)
        {
          sb.append('/');
        }
        sb.append(genus.getLabel());
      }
      ret=sb.toString();
    }
    return ret;
  }

  /**
   * Get the genus persistence string.
   * @return a genus string for persistence or <code>null</code> if none.
   */
  public String getGenusPersistenceString()
  {
    String ret=null;
    if ((_genus!=null) && (_genus.size()>0))
    {
      StringBuilder sb=new StringBuilder();
      for(Genus genus : _genus)
      {
        if (sb.length()>0)
        {
          sb.append(',');
        }
        sb.append(genus.getCode());
      }
      ret=sb.toString();
    }
    return ret;
  }

  /**
   * Set the genus.
   * @param genus the genus to set.
   */
  public void setGenus(List<Genus> genus)
  {
    _genus=genus;
  }

  /**
   * Get the species.
   * @return the species.
   */
  public Species getSpecies()
  {
    return _species;
  }

  /**
   * Get the label for the species.
   * @return A label or <code>null</code>.
   */
  public String getSpeciesLabel()
  {
    return _species!=null?_species.getLabel():null;
  }

  /**
   * Set the species.
   * @param species the species to set.
   */
  public void setSpecies(Species species)
  {
    _species=species;
  }

  /**
   * Get the sub-species.
   * @return the sub-species .
   */
  public SubSpecies getSubSpecies()
  {
    return _subSpecies;
  }

  /**
   * Get the label for the sub-species.
   * @return A label or <code>null</code>.
   */
  public String getSubSpeciesLabel()
  {
    return _subSpecies!=null?_subSpecies.getLabel():null;
  }

  /**
   * Set the sub-species.
   * @param subSpecies the sub-species to set.
   */
  public void setSubSpecies(SubSpecies subSpecies)
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
        return _subSpecies.getLabel();
      }
      if (_subSpecies==null)
      {
        return _species.getLabel();
      }
      return _species+"/"+_subSpecies;
    }
    if (_genus!=null)
    {
      return getGenusLabel();
    }
    return "?";
  }

  @Override
  public String toString()
  {
    return "genus="+_genus+", species="+_species+", sub-species="+_subSpecies;
  }
}
