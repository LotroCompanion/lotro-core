package delta.games.lotro.character.status.achievables.io;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.log.CharacterLog;
import delta.games.lotro.character.log.CharacterLogItem;
import delta.games.lotro.character.log.CharacterLogItem.LogItemType;
import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.character.status.achievables.AchievablesStatusManager;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;

/**
 * Resolve deed statuses using the character log.
 * @author DAM
 */
public class DeedsStatusFromCharacterLog
{
  private static final Logger LOGGER=LoggerFactory.getLogger(DeedsStatusFromCharacterLog.class);

  /**
   * Do it.
   */
  public void doIt()
  {
    CharactersManager mgr=CharactersManager.getInstance();
    List<CharacterFile> files=mgr.getAllToons();
    for(CharacterFile file : files)
    {
      doIt(file);
    }
  }

  private void doIt(CharacterFile character)
  {
    AchievablesStatusManager status=DeedsStatusIo.load(character);
    CharacterLog log=character.getLastCharacterLog();
    if (log!=null)
    {
      useCharacterLog(status,log);
      DeedsStatusIo.save(character,status);
    }
  }

  private void useCharacterLog(AchievablesStatusManager status, CharacterLog log)
  {
    int nbItems=log.getNbItems();
    for(int i=0;i<nbItems;i++)
    {
      CharacterLogItem item=log.getLogItem(i);
      if (item.getLogItemType()==LogItemType.DEED)
      {
        Integer deedId=item.getResourceIdentifier();
        if (deedId!=null)
        {
          String label=item.getLabel();
          DeedDescription deed=resolveDeedById(deedId.intValue(),label);
          if (deed!=null)
          {
            AchievableStatus deedStatus=status.get(deed,true);
            Long date=deedStatus.getCompletionDate();
            if (date==null)
            {
              deedStatus.setCompletionDate(Long.valueOf(item.getDate()));
            }
            deedStatus.setCompleted(true);
          }
        }
      }
    }
  }

  private DeedDescription resolveDeedById(int id, String label)
  {
    DeedsManager deedsMgr=DeedsManager.getInstance();
    DeedDescription ret=deedsMgr.getDeed(id);
    if (ret==null)
    {
      LOGGER.warn("Fail to get deed with id={}, label={}",Integer.valueOf(id),label);
    }
    return ret;
  }

  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new DeedsStatusFromCharacterLog().doIt();
  }
}
