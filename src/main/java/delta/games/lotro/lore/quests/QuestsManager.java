package delta.games.lotro.lore.quests;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.enums.QuestCategory;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;
import delta.games.lotro.common.rewards.RewardsExplorer;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.quests.io.xml.QuestsSaxParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for quests access.
 * @author DAM
 */
public final class QuestsManager
{
  private static QuestsManager _instance=null;

  private HashMap<Integer,QuestDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static QuestsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new QuestsManager();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private QuestsManager()
  {
    _cache=new HashMap<Integer,QuestDescription>(1000);
    loadAll();
  }


  /**
   * Load all quests.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File questsFile=cfg.getFile(DataFiles.QUESTS);
    long now=System.currentTimeMillis();
    List<QuestDescription> quests=QuestsSaxParser.parseQuestsFile(questsFile);
    for(QuestDescription quest : quests)
    {
      _cache.put(Integer.valueOf(quest.getIdentifier()),quest);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"quests",duration);
  }

  /**
   * Get a list of all quests, sorted by identifier.
   * @return A list of quests.
   */
  public List<QuestDescription> getAll()
  {
    ArrayList<QuestDescription> quests=new ArrayList<QuestDescription>();
    quests.addAll(_cache.values());
    Collections.sort(quests,new IdentifiableComparator<QuestDescription>());
    return quests;
  }

  /**
   * Get the rewards explorer.
   * @return the rewards explorer.
   */
  public RewardsExplorer buildRewardsExplorer()
  {
    RewardsExplorer rewardsExplorer=new RewardsExplorer();
    List<QuestDescription> quests=getAll();
    for(QuestDescription quest : quests)
    {
      rewardsExplorer.doIt(quest.getRewards());
    }
    rewardsExplorer.resolveProxies();
    return rewardsExplorer;
  }

  /**
   * Get a quest using its identifier.
   * @param id Quest identifier.
   * @return A quest description or <code>null</code> if not found.
   */
  public QuestDescription getQuest(int id)
  {
    QuestDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get all quest categories.
   * @return a sorted list of quest categories.
   */
  public List<QuestCategory> getCategories()
  {
    Set<QuestCategory> categories=new HashSet<QuestCategory>();
    for(QuestDescription quest : _cache.values())
    {
      categories.add(quest.getCategory());
    }
    List<QuestCategory> ret=new ArrayList<QuestCategory>(categories);
    Collections.sort(ret,new LotroEnumEntryNameComparator<QuestCategory>());
    return ret;
  }
}
