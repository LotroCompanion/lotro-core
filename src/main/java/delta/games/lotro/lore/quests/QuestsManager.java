package delta.games.lotro.lore.quests;

import java.io.File;
import java.io.InputStream;

import org.apache.log4j.Logger;

import delta.common.utils.cache.WeakReferencesCache;
import delta.common.utils.files.archives.ArchiveManager;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.quests.io.xml.QuestXMLParser;

/**
 * Facade for quests access.
 * @author DAM
 */
public final class QuestsManager
{
  private static final Logger LOGGER=Logger.getLogger(QuestsManager.class);

  private static QuestsManager _instance=new QuestsManager();

  private ArchiveManager _archive;
  private WeakReferencesCache<Integer,QuestDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static QuestsManager getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private QuestsManager()
  {
    _cache=new WeakReferencesCache<Integer,QuestDescription>(100);
    File dir=LotroCoreConfig.getInstance().getFile(DataFiles.ROOT);
    File questsArchive=new File(dir,"data/lore/quests.zip");
    _archive=new ArchiveManager(questsArchive);
    _archive.open();
  }

  /**
   * Get a quest using its identifier.
   * @param id Quest identifier.
   * @return A quest description or <code>null</code> if not found.
   */
  public QuestDescription getQuest(int id)
  {
    QuestDescription ret=null;
    if (id>0)
    {
      Integer idKey=Integer.valueOf(id);
      ret=(_cache!=null)?_cache.getObject(idKey):null;
      if (ret==null)
      {
        ret=loadQuest(id);
        if (ret!=null)
        {
          if (_cache!=null)
          {
            _cache.registerObject(idKey,ret);
          }
        }
      }
    }
    return ret;
  }

  private QuestDescription loadQuest(int id)
  {
    QuestDescription ret=null;
    String fileName=String.valueOf(id)+".xml";
    InputStream is=_archive.getEntry(fileName);
    if (is!=null)
    {
      QuestXMLParser parser=new QuestXMLParser();
      ret=parser.parseXML(is);
      if (ret==null)
      {
        LOGGER.error("Cannot load quest ["+fileName+"]!");
      }
    }
    return ret;
  }
}
