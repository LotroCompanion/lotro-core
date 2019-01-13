package delta.games.lotro.plugins.lotrocompanion;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.character.storage.currencies.CurrenciesFacade;
import delta.games.lotro.character.storage.currencies.CurrencyKeys;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.plugins.LuaParser;
import delta.games.lotro.plugins.PluginConstants;

/**
 * Parser for the main data as found in LotroCompanion plugin data.
 * @author DAM
 */
public class MainParser
{
  private static final Logger LOGGER=Logger.getLogger(MainParser.class);

  private String _account;
  private String _server;
  private String _character;

  /**
   * Constructor.
   * @param account Account name.
   * @param server Server name.
   * @param character Character name.
   */
  public MainParser(String account, String server, String character)
  {
    _account=account;
    _server=server;
    _character=character;
  }

  /**
   * Parse/use data from the "Main" file of the LotroCompanion plugin.
   * @throws Exception If an error occurs.
   */
  public void doIt() throws Exception
  {
    File dataDir=PluginConstants.getCharacterDir(_account,_server,_character);
    File dataFile=new File(dataDir,"LotroCompanionCharacter.plugindata");
    LuaParser parser=new LuaParser();
    Map<String,Object> data=parser.read(dataFile);
    useData(data);
  }

  private Map<String,StatDescription> loadStatsMap()
  {
    Map<String,StatDescription> statMap=new HashMap<String,StatDescription>();
    statMap.put("MAXMORALE",WellKnownStat.MORALE);
    statMap.put("ICMR",WellKnownStat.ICMR);
    statMap.put("OCMR",WellKnownStat.OCMR);
    statMap.put("POWER",WellKnownStat.POWER);
    statMap.put("ICPR",WellKnownStat.ICPR);
    statMap.put("OCPR",WellKnownStat.OCPR);

    statMap.put("ARMOR",WellKnownStat.ARMOUR);

    statMap.put("MIGHT",WellKnownStat.MIGHT);
    statMap.put("AGILITY",WellKnownStat.AGILITY);
    statMap.put("VITALITY",WellKnownStat.VITALITY);
    statMap.put("WILL",WellKnownStat.WILL);
    statMap.put("FATE",WellKnownStat.FATE);

    statMap.put("MELEE_DAMAGE",WellKnownStat.PHYSICAL_MASTERY);
    statMap.put("TACTICAL_DAMAGE",WellKnownStat.TACTICAL_MASTERY);
    statMap.put("CRITICAL_RATING",WellKnownStat.CRITICAL_RATING);
    statMap.put("FINESSE",WellKnownStat.FINESSE);

    statMap.put("CRITICAL_DEFENCE",WellKnownStat.CRITICAL_DEFENCE);
    statMap.put("BLOCK",WellKnownStat.BLOCK);
    statMap.put("PARRY",WellKnownStat.PARRY);
    statMap.put("EVADE",WellKnownStat.EVADE);

    statMap.put("RESISTANCE",WellKnownStat.RESISTANCE);
    statMap.put("COMMON_MITIGATION",WellKnownStat.PHYSICAL_MITIGATION);
    statMap.put("TACTICAL_MITIGATION",WellKnownStat.TACTICAL_MITIGATION);

    statMap.put("INCOMING_HEALING",WellKnownStat.INCOMING_HEALING);
    statMap.put("OUTGOING_HEALING",WellKnownStat.OUTGOING_HEALING);
    return statMap;
  }

  @SuppressWarnings("unchecked")
  private void useData(Map<String,Object> data)
  {
    // Stats
    Map<String,Object> statsMap=(Map<String,Object>)data.get("stats");
    if (statsMap!=null)
    {
      Map<String,StatDescription> map=loadStatsMap();
      for(Map.Entry<String,StatDescription> entry : map.entrySet())
      {
        Double statValue=(Double)statsMap.get(entry.getKey());
        StatDescription stat=map.get(entry.getKey());
        System.out.println("\t"+stat.getName()+": "+statValue);
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
      Money money=new Money();
      money.setGoldCoins(gold.intValue());
      money.setSilverCoins(silver.intValue());
      money.setCopperCoins(copper.intValue());
      int value=money.getInternalValue();
      updateMoney(value);
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
      String mostRecentCharacter=PluginConstants.getMostRecentLoggedInCharacter(_account,_server);
      if (_character.equals(mostRecentCharacter))
      {
        updateDestinyPoints(destinyPoints.intValue());
      }
    }
  }

  private void updateMoney(int value)
  {
    CurrenciesFacade.updateToonCurrency(_server,_character,CurrencyKeys.GOLD,value,true);
  }

  private void updateDestinyPoints(int value)
  {
    CurrenciesFacade.updateAccountServerCurrency(_server,_account,CurrencyKeys.DESTINY_POINTS,value,true);
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    doAccountServer("glorfindel666","Landroval");
    doAccountServer("meva666","Landroval");
  }

  private static void doAccountServer(String account, String server)
  {
    List<String> characters=PluginConstants.getCharacters(account,server,false);
    for(String character : characters)
    {
      File dataDir=PluginConstants.getCharacterDir(account,server,character);
      File dataFile=new File(dataDir,"LotroCompanionCharacter.plugindata");
      if (dataFile.exists())
      {
        try
        {
          System.out.println("Doing: " + character);
          MainParser parser=new MainParser(account,server,character);
          parser.doIt();
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
