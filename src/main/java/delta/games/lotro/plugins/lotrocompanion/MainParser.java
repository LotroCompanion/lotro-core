package delta.games.lotro.plugins.lotrocompanion;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.storage.currencies.Currencies;
import delta.games.lotro.character.storage.currencies.CurrenciesSummary;
import delta.games.lotro.character.storage.currencies.Currency;
import delta.games.lotro.character.storage.currencies.CurrencyHistory;
import delta.games.lotro.character.storage.currencies.CurrencyKeys;
import delta.games.lotro.character.storage.currencies.CurrencyStatus;
import delta.games.lotro.character.storage.currencies.io.CurrenciesIo;
import delta.games.lotro.common.money.Money;
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
    long now=System.currentTimeMillis();
    CharactersManager charsManager=CharactersManager.getInstance();
    CharacterFile character=charsManager.getToonById(_server,_character);
    if (character!=null)
    {
      CurrenciesSummary summary=CurrenciesIo.load(character);
      CurrencyStatus goldStatus=summary.getCurrency(CurrencyKeys.GOLD,true);
      goldStatus.setDate(now);
      goldStatus.setValue(value);
      CurrenciesIo.save(character,summary);
      //boolean keepHistory=goldStatus.isKeepHistory();
      //if (keepHistory)
      {
        Currency currency=Currencies.get().getByKey(CurrencyKeys.GOLD);
        CurrencyHistory history=CurrenciesIo.load(character,currency);
        if (history==null)
        {
          history=new CurrencyHistory(currency);
        }
        history.getStorage().setValueAt(now,value);
        CurrenciesIo.save(character,history);
      }
    }
  }

  private void updateDestinyPoints(int value)
  {
    Account account=AccountsManager.getInstance().getAccountByName(_account);
    if (account!=null)
    {
      long now=System.currentTimeMillis();
      CurrenciesSummary summary=CurrenciesIo.load(account,_server);
      CurrencyStatus destinyPointsStatus=summary.getCurrency(CurrencyKeys.DESTINY_POINTS,true);
      destinyPointsStatus.setDate(now);
      destinyPointsStatus.setValue(value);
      CurrenciesIo.save(account,_server,summary);
      //boolean keepHistory=goldStatus.isKeepHistory();
      //if (keepHistory)
      {
        Currency currency=Currencies.get().getByKey(CurrencyKeys.DESTINY_POINTS);
        CurrencyHistory history=CurrenciesIo.load(account,_server,currency);
        if (history==null)
        {
          history=new CurrencyHistory(currency);
        }
        history.getStorage().setValueAt(now,value);
        CurrenciesIo.save(account,_server,history);
      }
    }
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
