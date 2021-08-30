package delta.games.lotro.lore.relics.melding;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * Relic melding recipe.
 * @author DAM
 */
public class RelicMeldingRecipe implements Identifiable
{
  private int _id;
  private String _nameOverride;
  private int _iconOverride;
  private String _tooltipOverride;
  private String _category;
  private int _cost;
  private MeldingInput _input;
  private MeldingOutput _output;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public RelicMeldingRecipe(int id)
  {
    _id=id;
    _input=new MeldingInput();
    _output=new MeldingOutput();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the recipe name.
   * @return A name.
   */
  public String getName()
  {
    String ret=getNameOverride();
    if (ret==null)
    {
      Relic relic=_output.getFirstRelic();
      return (relic!=null)?relic.getName():"?";
    }
    return ret;
  }

  /**
   * Get the name override, if any.
   * @return a name override or <code>null</code> if not set.
   */
  public String getNameOverride()
  {
    return _nameOverride;
  }

  /**
   * Set the name override.
   * @param nameOverride the name override to set.
   */
  public void setNameOverride(String nameOverride)
  {
    _nameOverride=nameOverride;
  }

  /**
   * Get the icon filename.
   * @return An icon name.
   */
  public String getIconFilename()
  {
    int ret=getIconOverride();
    if (ret==0)
    {
      Relic relic=_output.getFirstRelic();
      return (relic!=null)?relic.getIconFilename():"?";
    }
    return ret+".png";
  }

  /**
   * Get the icon override, if any.
   * @return an icon ID or 0 if none.
   */
  public int getIconOverride()
  {
    return _iconOverride;
  }

  /**
   * Set the icon override.
   * @param iconOverride the ID of the icon override to set.
   */
  public void setIconOverride(int iconOverride)
  {
    _iconOverride=iconOverride;
  }

  /**
   * Get the tooltip for this recipe.
   * @return A tooltip.
   */
  public String getTooltip()
  {
    String ret=getTooltipOverride();
    if (ret==null)
    {
      Relic relic=_output.getFirstRelic();
      return "Create a "+((relic!=null)?relic.getName():"?");
    }
    return ret;
  }

  /**
   * Get the tooltip override, if any.
   * @return a tooltip override or <code>null</code> if not set.
   */
  public String getTooltipOverride()
  {
    return _tooltipOverride;
  }

  /**
   * Set the tooltip override.
   * @param tooltipOverride the tooltip override to set.
   */
  public void setTooltipOverride(String tooltipOverride)
  {
    _tooltipOverride=tooltipOverride;
  }

  /**
   * Get the category of this recipe.
   * @return a category.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category for this recipe.
   * @param category the category to set.
   */
  public void setCategory(String category)
  {
    _category=category;
  }

  /**
   * Get the shard cost for this recipe.
   * @return a shards count).
   */
  public int getCost()
  {
    return _cost;
  }

  /**
   * Set the shard cost for this recipe.
   * @param cost the cost to set
   */
  public void setCost(int cost)
  {
    _cost=cost;
  }

  /**
   * Get the melding input.
   * @return the melding input data.
   */
  public MeldingInput getInput()
  {
    return _input;
  }

  /**
   * Get the melding output.
   * @return the melding output data.
   */
  public MeldingOutput getOutput()
  {
    return _output;
  }
}
