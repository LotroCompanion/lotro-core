package delta.games.lotro.lore.perks;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.enums.PerkUICategory;

/**
 * Description of a perk.
 * @author DAM
 */
public class PerkDescription implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private String _description;
  private int _iconId;
  private Effect _effect;
  private PerkUICategory _uiCategory;
  private int _minLevel;
  private int _pointsCost;

  /**
   * Constructor.
   */
  public PerkDescription()
  {
    super();
    _identifier=0;
    _name="";
    _description="";
    _iconId=0;
    _effect=null;
    _uiCategory=null;
    _minLevel=1;
    _pointsCost=0;
  }

  /**
   * Get the identifier of this perk.
   * @return a perk identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this perk.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the name of this perk.
   * @return a perk name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this perk.
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
   * Get the description of this perk.
   * @return a perk description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this perk.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Get the perk icon ID.
   * @return an icon ID.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the perk icon ID.
   * @param iconId the icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the associated effect.
   * @return the associated effect.
   */
  public Effect getEffect()
  {
    return _effect;
  }

  /**
   * Set the associated effect.
   * @param effect Effect to set.
   */
  public void setEffect(Effect effect)
  {
    _effect=effect;
  }

  /**
   * Get the UI category.
   * @return the UI category.
   */
  public PerkUICategory getUICategory()
  {
    return _uiCategory;
  }

  /**
   * Set the UI category.
   * @param uiCategory UI Category to set.
   */
  public void setUICategory(PerkUICategory uiCategory)
  {
    _uiCategory=uiCategory;
  }

  /**
   * Get the minimum level for this perk.
   * @return the minimum level for this perk.
   */
  public int getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Set the minimum level for this perk.
   * @param minLevel the minimum level to set.
   */
  public void setMinLevel(int minLevel)
  {
    _minLevel=minLevel;
  }

  /**
   * Get the points cost.
   * @return a points cost.
   */
  public int getPointsCost()
  {
    return _pointsCost;
  }

  /**
   * Set the points cost.
   * @param pointsCost Points cost to set.
   */
  public void setPointsCost(int pointsCost)
  {
    _pointsCost=pointsCost;
  }
}
