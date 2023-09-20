package delta.games.lotro.lore.items.details;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.enums.Genus;

/**
 * Slayer details for a weapon.
 * @author DAM
 */
public class WeaponSlayerInfo extends ItemDetail
{
  private float _slayer;
  private List<Genus> _genuses;

  /**
   * Constructor.
   * @param slayer Slayer value.
   */
  public WeaponSlayerInfo(float slayer)
  {
    _slayer=slayer;
    _genuses=new ArrayList<Genus>();
  }

  /**
   * Get the slayer value.
   * @return A value.
   */
  public float getSlayer()
  {
    return _slayer;
  }

  /**
   * Add a genus.
   * @param genus Genus to add.
   */
  public void addGenus(Genus genus)
  {
    _genuses.add(genus);
  }

  /**
   * Get all genuses.
   * @return A list of genus.
   */
  public List<Genus> getGenus()
  {
    return _genuses;
  }

  /**
   * Get a displayable label for this info.
   * @return A displayable label.
   */
  public String getLabel()
  {
    StringBuilder sb=new StringBuilder("+");
    sb.append(Math.round(_slayer)).append(" Damage to ");
    int index=0;
    for(Genus genus : _genuses)
    {
      if (index>0)
      {
        sb.append(", ");
      }
      sb.append(genus.getLabel());
      index++;
    }
    String label=sb.toString();
    return label;
  }

  @Override
  public String toString()
  {
    return getLabel();
  }
}
