package delta.games.lotro.plugins.lotrocompanion;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.plugins.LuaParser;
import delta.games.lotro.plugins.PluginConstants;

/**
 * Parser for the main data as found in LotroCompanion plugin data.
 * @author DAM
 */
public class MainParser
{
  private static final Logger LOGGER=Logger.getLogger(MainParser.class);

  /**
   * Parse/use data from the "Main" file of the LotroCompanion plugin.
   * @param f Input file.
   * @throws Exception If an error occurs.
   */
  public void doIt(File f) throws Exception
  {
    LuaParser parser=new LuaParser();
    Map<String,Object> data=parser.read(f);
    useData(data);
  }

  private Map<String,STAT> loadStatsMap()
  {
    Map<String,STAT> statMap=new HashMap<String,STAT>();
    statMap.put("MAXMORALE",STAT.MORALE);
    statMap.put("ICMR",STAT.ICMR);
    statMap.put("OCMR",STAT.OCMR);
    statMap.put("POWER",STAT.POWER);
    statMap.put("ICPR",STAT.ICPR);
    statMap.put("OCPR",STAT.OCPR);

    statMap.put("ARMOR",STAT.ARMOUR);

    statMap.put("MIGHT",STAT.MIGHT);
    statMap.put("AGILITY",STAT.AGILITY);
    statMap.put("VITALITY",STAT.VITALITY);
    statMap.put("WILL",STAT.WILL);
    statMap.put("FATE",STAT.FATE);

    statMap.put("MELEE_DAMAGE",STAT.PHYSICAL_MASTERY);
    statMap.put("TACTICAL_DAMAGE",STAT.TACTICAL_MASTERY);
    statMap.put("CRITICAL_RATING",STAT.CRITICAL_RATING);
    statMap.put("FINESSE",STAT.FINESSE);

    statMap.put("CRITICAL_DEFENCE",STAT.CRITICAL_DEFENCE);
    statMap.put("BLOCK",STAT.BLOCK);
    statMap.put("PARRY",STAT.PARRY);
    statMap.put("EVADE",STAT.EVADE);

    statMap.put("RESISTANCE",STAT.RESISTANCE);
    statMap.put("COMMON_MITIGATION",STAT.PHYSICAL_MITIGATION);
    statMap.put("TACTICAL_MITIGATION",STAT.TACTICAL_MITIGATION);

    statMap.put("INCOMING_HEALING",STAT.INCOMING_HEALING);
    statMap.put("OUTGOING_HEALING",STAT.OUTGOING_HEALING);
    return statMap;
  }

  @SuppressWarnings("unchecked")
  private void useData(Map<String,Object> data)
  {
    // Stats
    Map<String,Object> statsMap=(Map<String,Object>)data.get("stats");
    if (statsMap!=null)
    {
      Map<String,STAT> map=loadStatsMap();
      for(Map.Entry<String,STAT> entry : map.entrySet())
      {
        Double statValue=(Double)statsMap.get(entry.getKey());
        STAT stat=map.get(entry.getKey());
        System.out.println("\t"+stat+": "+statValue);
      }
    }

    // Money
    Map<String,Object> moneyMap=(Map<String,Object>)data.get("money");
    if (moneyMap!=null)
    {
      Double gold=(Double)moneyMap.get("gold");
      Double silver=(Double)moneyMap.get("silver");
      Double copper=(Double)moneyMap.get("copper");
      System.out.println("\tGold: "+gold);
      System.out.println("\tSilver: "+silver);
      System.out.println("\tCopper: "+copper);
    }
    // Infos
    Map<String,Object> infosMap=(Map<String,Object>)data.get("infos");
    if (infosMap!=null)
    {
      String className=(String)infosMap.get("Class");
      System.out.println("\tClass: "+className);
      String race=(String)infosMap.get("Race");
      System.out.println("\tRace: "+race);
      Double level=(Double)infosMap.get("Level");
      System.out.println("\tLevel: "+level);
      Double destinyPoints=(Double)infosMap.get("DestinyPoints");
      System.out.println("\tDestiny points: "+destinyPoints);
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
      File dataDir=PluginConstants.getCharacterDir("glorfindel666","Landroval",character);
      File dataFile=new File(dataDir,"LotroCompanionData.plugindata");
      if (dataFile.exists())
      {
        try
        {
          System.out.println("Doing: " + character);
          MainParser parser=new MainParser();
          parser.doIt(dataFile);
        }
        catch(Exception e)
        {
          LOGGER.error("Error when loading gear from file "+dataFile, e);
        }
      }
      else
      {
        System.out.println("No data for: " + character);
      }
    }
  }
}
