package delta.games.lotro.lore.relics.melding;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.relics.melding.io.xml.MeldingRecipesXMLParser;
import delta.games.lotro.utils.PerfUtils;
import delta.games.lotro.utils.Registry;

/**
 * Melding recipes manager.
 * @author DAM
 */
public class RelicMeldingRecipesManager
{
  private static RelicMeldingRecipesManager _instance;

  private Registry<RelicMeldingRecipe> _meldingRecipes;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static RelicMeldingRecipesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public RelicMeldingRecipesManager()
  {
    _meldingRecipes=new Registry<RelicMeldingRecipe>();
  }

  private static RelicMeldingRecipesManager load()
  {
    RelicMeldingRecipesManager ret=new RelicMeldingRecipesManager();
    File meldingRecipesFile=LotroCoreConfig.getInstance().getFile(DataFiles.RELIC_MELDING_RECIPES);
    if (meldingRecipesFile.exists())
    {
      long now=System.currentTimeMillis();
      MeldingRecipesXMLParser parser=new MeldingRecipesXMLParser();
      List<RelicMeldingRecipe> recipes=parser.parseXML(meldingRecipesFile);
      for(RelicMeldingRecipe recipe : recipes)
      {
        ret.getMeldingRecipes().add(recipe);
      }
      long now2=System.currentTimeMillis();
      long duration=now2-now;
      PerfUtils.showLoadedLog(recipes.size(),"relic melding recipes",duration);
    }
    return ret;
  }

  /**
   * Get the registry for relic melding recipes.
   * @return the registry for relic melding recipes.
   */
  public Registry<RelicMeldingRecipe> getMeldingRecipes()
  {
    return _meldingRecipes;
  }

  /**
   * Dump some statistics about melding recipes.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    out.println("Relic melding recipes manager has:");
    out.println("\t"+_meldingRecipes.size()+" recipes");
  }
}
