package delta.games.lotro.character.storage.statistics;

import java.util.List;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.character.storage.StorageUtils;
import delta.games.lotro.character.storage.StoredItem;
import delta.games.lotro.character.storage.statistics.reputation.StorageReputationStats;
import delta.games.lotro.common.statistics.FactionStats;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Simple tool class to compute and show storage statistics for a character.
 * @author DAM
 */
public class MainTestShowStorageStatistics
{
  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    String serverName="Landroval";
    /*
    CharacterFile toon=CharactersManager.getInstance().getToonById(serverName,"Kargarth");
    CharacterStorage characterStorage=StoragesIO.loadCharacterStorage(toon);
    List<StoredItem> items=StorageUtils.buildCharacterItems(toon,characterStorage);
    */
    String accountName="glorfindel666";
    Account account=AccountsManager.getInstance().getAccountByName(accountName);
    List<StoredItem> items=StorageUtils.buildAccountItems(account,serverName);

    StorageStatistics stats=new StorageStatistics();
    new StorageStatisticsComputer().computeStatistics(items,stats);
    long totalXP=stats.getTotalItemXP();
    System.out.println("Total item XP: "+totalXP);
    StorageReputationStats reputationStats=stats.getReputationStats();
    for(FactionStats factionStats : reputationStats.getFactionStats())
    {
      Faction faction=factionStats.getFaction();
      int points=factionStats.getPoints();
      System.out.println("Total "+points+" points for faction "+faction.getName());
    }
  }
}
