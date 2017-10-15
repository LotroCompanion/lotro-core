package delta.games.lotro.plugins.lotrocompanion;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.games.lotro.character.crafting.CraftingLevelStatus;
import delta.games.lotro.character.crafting.CraftingLevelTierStatus;
import delta.games.lotro.character.crafting.CraftingStatus;
import delta.games.lotro.character.crafting.ProfessionStatus;
import delta.games.lotro.crafting.CraftingLevel;
import delta.games.lotro.crafting.Profession;
import delta.games.lotro.crafting.Vocation;
import delta.games.lotro.crafting.Vocations;
import delta.games.lotro.plugins.LuaParser;
import delta.games.lotro.plugins.PluginConstants;

/**
 * Parser for the crafting status as found in LotroCompanion plugin data.
 * @author DAM
 */
public class CraftingParser
{
  private static final Logger LOGGER=Logger.getLogger(CraftingParser.class);

  private String _character;

  /**
   * Constructor.
   * @param characterName Character name.
   */
  public CraftingParser(String characterName)
  {
    _character=characterName;
  }

  /**
   * Parse/use data from the "Crafting Status" file of the LotroCompanion plugin.
   * @param f Input file.
   * @throws Exception If an error occurs.
   */
  public void doIt(File f) throws Exception
  {
    LuaParser parser=new LuaParser();
    Map<String,Object> data=parser.read(f);
    useData(data);
  }

  @SuppressWarnings("unchecked")
  private void useData(Map<String,Object> data)
  {
    CraftingStatus craftingStatus=new CraftingStatus(_character);
    String vocationStr=(String)data.get("Vocation");
    if (vocationStr!=null)
    {
      Vocation vocation=Vocations.getInstance().getVocationByName(vocationStr);
      if (vocation!=null)
      {
        craftingStatus.setVocation(vocation);
      }
      Map<String,Object> professionsMap=(Map<String,Object>)data.get("Professions");
      if (professionsMap!=null)
      {
        Set<String> keys=professionsMap.keySet();
        for(String professionKey : keys)
        {
          Profession profession=Profession.getByLabel(professionKey);
          Map<String,Object> professionMap=(Map<String,Object>)professionsMap.get(professionKey);
          if (professionMap!=null)
          {
            ProfessionStatus status=craftingStatus.getProfessionStatus(profession,true);
            parseProfession(status,professionMap);
          }
        }
      }
    }
    craftingStatus.dump(System.out);
  }

  private void parseProfession(ProfessionStatus status, Map<String,Object> professionMap)
  {
    int maxTier=CraftingLevel.getMaximumLevel().getTier();
    // Proficiency
    Double proficiencyLevel=(Double)professionMap.get("ProficiencyLevel");
    if (proficiencyLevel!=null)
    {
      int tier=proficiencyLevel.intValue();
      for(int i=0;i<=tier;i++)
      {
        CraftingLevelStatus levelStatus=status.getLevelStatus(i);
        CraftingLevelTierStatus tierStatus=levelStatus.getProficiency();
        tierStatus.setCompleted(true);
        tierStatus.setAcquiredXP(levelStatus.getLevel().getProficiency().getXP());
     }
      Double proficiencyExperience=(Double)professionMap.get("ProficiencyExperience");
      if (proficiencyExperience!=null)
      {
        int xp=proficiencyExperience.intValue();
        if ((xp>0) && (tier<maxTier))
        {
          CraftingLevelStatus levelStatus=status.getLevelStatus(tier+1);
          CraftingLevelTierStatus tierStatus=levelStatus.getProficiency();
          tierStatus.setCompleted(false);
          tierStatus.setAcquiredXP(xp);
        }
      }
    }
    // Mastery
    Double masteryLevel=(Double)professionMap.get("MasteryLevel");
    if (masteryLevel!=null)
    {
      int tier=masteryLevel.intValue();
      for(int i=0;i<=tier;i++)
      {
        CraftingLevelStatus levelStatus=status.getLevelStatus(i);
        CraftingLevelTierStatus tierStatus=levelStatus.getMastery();
        tierStatus.setCompleted(true);
        tierStatus.setAcquiredXP(levelStatus.getLevel().getMastery().getXP());
      }
      Double masteryExperience=(Double)professionMap.get("MasteryExperience");
      if (masteryExperience!=null)
      {
        int xp=masteryExperience.intValue();
        if ((xp>0) && (tier<maxTier))
        {
          CraftingLevelStatus levelStatus=status.getLevelStatus(tier+1);
          CraftingLevelTierStatus tierStatus=levelStatus.getMastery();
          tierStatus.setCompleted(false);
          tierStatus.setAcquiredXP(xp);
        }
      }
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    String account="glorfindel666";
    String server="Landroval";
    List<String> characters=PluginConstants.getCharacters(account,server,false);
    for(String character : characters)
    {
      try
      {
        File dataFile=PluginConstants.getCharacterDir("glorfindel666","Landroval",character);
        File inputFile=new File(dataFile,"LotroCompanionData.plugindata");
        if (inputFile.exists())
        {
          CraftingParser parser=new CraftingParser(character);
          parser.doIt(inputFile);
        }
        else
        {
          System.out.println("No data for: " + character);
        }
      }
      catch(Exception e)
      {
        LOGGER.error("Could not parse crafting data for character "+character,e);
      }
    }
  }
}
