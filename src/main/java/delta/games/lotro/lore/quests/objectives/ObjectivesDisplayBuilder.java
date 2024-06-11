package delta.games.lotro.lore.quests.objectives;

import java.awt.geom.Point2D;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.misc.IntegerHolder;
import delta.common.utils.variables.VariablesResolver;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.Interactable;
import delta.games.lotro.common.enums.MobDivision;
import delta.games.lotro.common.enums.QuestCategory;
import delta.games.lotro.common.geo.PositionUtils;
import delta.games.lotro.lore.agents.AgentDescription;
import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.emotes.EmoteDescription;
import delta.games.lotro.lore.geo.landmarks.LandmarkDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.maps.LandDivision;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsHtmlUtils;
import delta.games.lotro.lore.quests.dialogs.DialogElement;
import delta.games.lotro.lore.quests.geo.AchievableGeoPoint;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.html.HtmlUtils;
import delta.games.lotro.utils.strings.GenericOutput;
import delta.games.lotro.utils.strings.StringRendering;
import delta.games.lotro.utils.strings.TextSanitizer;
import delta.games.lotro.utils.style.Style;

/**
 * Display achievables objectives as text or HTML.
 * @author DAM
 */
public class ObjectivesDisplayBuilder
{
  private static final Logger LOGGER=Logger.getLogger(ObjectivesDisplayBuilder.class);

  private static final String COUNT_PATTERN="${NUMBER}/${TOTAL}";

  private VariablesResolver _resolver;
  private GenericOutput _output;

  /**
   * Constructor.
   * @param resolver Resolver.
   * @param output Output.
   */
  public ObjectivesDisplayBuilder(VariablesResolver resolver, GenericOutput output)
  {
    _resolver=resolver;
    _output=output;
  }

  private String render(String text)
  {
    return StringRendering.render(_resolver,text);
  }

  /**
   * Print HTML code for the objectives of an achievable.
   * @param sb Output.
   * @param achievable Achievable to display.
   */
  public void build(StringBuilder sb, Achievable achievable)
  {
    // Is deed?
    boolean isDeed=(achievable instanceof DeedDescription);
    // Objectives
    ObjectivesManager objectivesMgr=achievable.getObjectives();
    List<Objective> objectives=objectivesMgr.getObjectives();
    for(Objective objective : objectives)
    {
      int index=objective.getIndex();
      _output.startParagraph(sb);
      _output.startBold(sb);
      _output.printText(sb,"Objective #"+index); // I18n
      _output.endBold(sb);
      _output.endParagraph(sb);
      String text=objective.getDescription();
      text=render(text);
      if (text.length()>0)
      {
        _output.startParagraph(sb);
        _output.printText(sb,text);
        _output.endParagraph(sb);
      }
      // Conditions
      StringBuilder conditionsSb=new StringBuilder();
      for(ObjectiveCondition condition : objective.getConditions())
      {
        conditionsSb.append(getConditionDisplay(condition,isDeed));
        printLoreInfo(conditionsSb,condition);
      }
      if (conditionsSb.length()>0)
      {
        Integer count=objective.getCompletionConditionsCount();
        if (count!=null)
        {
          String countText="Complete "+count+" among:";
          _output.startParagraph(sb);
          sb.append("<u><font color=\"#498500\">").append(countText).append("</font></u>");
          _output.endParagraph(sb);
        }
        sb.append(conditionsSb);
      }
      // Dialogs
      for(DialogElement dialog : objective.getDialogs())
      {
        QuestsHtmlUtils.buildHtmlForDialog(_resolver,sb,dialog);
      }
      // Failure conditions
      List<ObjectiveCondition> failureConditions=objective.getFailureConditions();
      handleFailureConditions(sb,isDeed,false,failureConditions);
    }
    // Global failure conditions
    List<ObjectiveCondition> failureConditions=objectivesMgr.getFailureConditions();
    handleFailureConditions(sb,isDeed,true,failureConditions);
  }

  private void handleFailureConditions(StringBuilder sb, boolean isDeed, boolean global, List<ObjectiveCondition> failureConditions)
  {
    if (!failureConditions.isEmpty())
    {
      StringBuilder childSb=new StringBuilder();
      for(ObjectiveCondition condition : failureConditions)
      {
        childSb.append(getConditionDisplay(condition,isDeed));
        printLoreInfo(childSb,condition);
      }
      if (childSb.length()>0)
      {
        int nb=failureConditions.size();
        String failureText;
        if (global)
        {
          failureText=(nb>1)?"Global failure conditions:":"Global failure condition:";
        }
        else
        {
          failureText=(nb>1)?"Failure conditions:":"Failure condition:";
        }
        _output.startParagraph(sb);
        _output.printText(sb,failureText,Style.UNDERLINE,Style.getColorStyle("#FF0000"));
        _output.endParagraph(sb);
        sb.append(childSb);
      }
    }
  }

  /**
   * Get a displayable string for an objective condition.
   * @param condition Condition to use.
   * @param isDeed Is it part of a deed (or of a quest).
   * @return A displayable string (HTML or raw).
   */
  public String getConditionDisplay(ObjectiveCondition condition, boolean isDeed)
  {
    StringBuilder sb=new StringBuilder();
    handleCondition(sb,condition,isDeed);
    if (sb.length()>0)
    {
      StringBuilder sb2=new StringBuilder();
      _output.startParagraph(sb2);
      sb2.append(sb);
      _output.endParagraph(sb2);
      return sb2.toString();
    }
    return "";
  }

  private void handleCondition(StringBuilder sb, ObjectiveCondition condition, boolean isDeed)
  {
    ConditionType type=condition.getType();
    if (isDeed)
    {
      if ((type==ConditionType.ENTER_DETECTION) || (type==ConditionType.WORLD_EVENT_CONDITION))
      {
        if (!hasProgressOverride(condition))
        {
          return;
        }
      }
    }
    if (condition instanceof QuestCompleteCondition)
    {
      QuestCompleteCondition questComplete=(QuestCompleteCondition)condition;
      handleQuestCompleteCondition(sb,questComplete);
    }
    else if (condition instanceof MonsterDiedCondition)
    {
      MonsterDiedCondition monsterDied=(MonsterDiedCondition)condition;
      handleMonsterDiedCondition(sb,monsterDied);
    }
    else if (condition instanceof LandmarkDetectionCondition)
    {
      LandmarkDetectionCondition landmarkDetection=(LandmarkDetectionCondition)condition;
      handleLandmarkDetectionCondition(sb,landmarkDetection);
    }
    else if (condition instanceof InventoryItemCondition)
    {
      InventoryItemCondition inventoryItem=(InventoryItemCondition)condition;
      handleInventoryItemCondition(sb,inventoryItem);
    }
    else if (condition instanceof ItemUsedCondition)
    {
      ItemUsedCondition inventoryItem=(ItemUsedCondition)condition;
      handleItemUsedCondition(sb,inventoryItem);
    }
    else if (condition instanceof ExternalInventoryItemCondition)
    {
      ExternalInventoryItemCondition inventoryItem=(ExternalInventoryItemCondition)condition;
      handleExternalInventoryItemCondition(sb,inventoryItem);
    }
    else if (condition instanceof ItemTalkCondition)
    {
      ItemTalkCondition inventoryItem=(ItemTalkCondition)condition;
      handleItemTalkCondition(sb,inventoryItem);
    }
    else if (condition instanceof FactionLevelCondition)
    {
      FactionLevelCondition factionLevel=(FactionLevelCondition)condition;
      handleFactionLevelCondition(sb,factionLevel);
    }
    else if (condition instanceof SkillUsedCondition)
    {
      SkillUsedCondition skillUsed=(SkillUsedCondition)condition;
      handleSkillUsedCondition(sb,skillUsed);
    }
    else if (condition instanceof NpcTalkCondition)
    {
      NpcTalkCondition npcTalk=(NpcTalkCondition)condition;
      handleNpcTalkCondition(sb,npcTalk);
    }
    else if (condition instanceof NpcUsedCondition)
    {
      NpcUsedCondition npcUsed=(NpcUsedCondition)condition;
      handleNpcUsedCondition(sb,npcUsed);
    }
    else if (condition instanceof LevelCondition)
    {
      LevelCondition level=(LevelCondition)condition;
      handleLevelCondition(sb,level);
    }
    else if (condition instanceof QuestBestowedCondition)
    {
      QuestBestowedCondition questBestowed=(QuestBestowedCondition)condition;
      handleQuestBestowedCondition(sb,questBestowed);
    }
    else if (condition instanceof DetectingCondition)
    {
      DetectingCondition detecting=(DetectingCondition)condition;
      handleDetectingCondition(sb,detecting);
    }
    else if (condition instanceof EnterDetectionCondition)
    {
      EnterDetectionCondition enterDetection=(EnterDetectionCondition)condition;
      handleEnterDetectionCondition(sb,enterDetection);
    }
    else if (condition instanceof EmoteCondition)
    {
      EmoteCondition emoteCondition=(EmoteCondition)condition;
      handleEmoteCondition(sb,emoteCondition);
    }
    else if (condition instanceof TimeExpiredCondition)
    {
      TimeExpiredCondition timeExpiredCondition=(TimeExpiredCondition)condition;
      handleTimeExpiredCondition(sb,timeExpiredCondition);
    }
    else if (condition instanceof HobbyCondition)
    {
      HobbyCondition hobbyCondition=(HobbyCondition)condition;
      handleHobbyCondition(sb,hobbyCondition);
    }
    else if (condition instanceof DefaultObjectiveCondition)
    {
      DefaultObjectiveCondition defaultCondition=(DefaultObjectiveCondition)condition;
      handleDefaultCondition(sb,defaultCondition);
    }
    else
    {
      LOGGER.warn("Unmanaged condition: "+condition.getType());
    }
  }

  private void handleQuestCompleteCondition(StringBuilder sb, QuestCompleteCondition questComplete)
  {
    String progressOverride=getProgressOverrideWithCount(questComplete);
    Proxy<Achievable> proxy=questComplete.getProxy();
    QuestCategory questCategory=questComplete.getQuestCategory();

    if (progressOverride==null)
    {
      if (proxy!=null)
      {
        String link=getAchievableLink(proxy,null);
        sb.append("Complete ").append(link); // I18n
      }
      else if (questCategory!=null)
      {
        sb.append("Complete quests in category ").append(questCategory); // I18n
      }
      int count=questComplete.getCount();
      if (count>1)
      {
        sb.append(" (x").append(count).append(')');
      }
    }
    else
    {
      if (proxy!=null)
      {
        String link=getAchievableLink(proxy,progressOverride);
        sb.append(link);
      }
      else
      {
        sb.append(HtmlUtils.toHtml(progressOverride));
      }
    }
  }

  private void handleMonsterDiedCondition(StringBuilder sb, MonsterDiedCondition monsterDied)
  {
    boolean hasProgressOverride=printProgressOverrideWithCount(sb,monsterDied);
    if (!hasProgressOverride)
    {
      String mobName=monsterDied.getMobName();
      List<MobSelection> mobSelections=monsterDied.getMobSelections();
      if (mobName!=null)
      {
        sb.append("Kill ").append(mobName); // I18n
      }
      else
      {
        if (!mobSelections.isEmpty())
        {
          sb.append("Kill "); // I18n
          int index=0;
          for(MobSelection mobSelection : mobSelections)
          {
            EntityClassification what=mobSelection.getWhat();
            MobLocation where=mobSelection.getWhere();
            String whatStr=(what!=null)?what.getLabel():"Mob"; // I18n
            if (index>0)
            {
              sb.append(" or "); // I18n
            }
            sb.append(whatStr);
            if (where!=null)
            {
              String whereStr=renderMobLocation(where);
              sb.append(" in ").append(whereStr); // I18n
            }
            index++;
          }
        }
      }
      int count=monsterDied.getCount();
      if (count>1)
      {
        sb.append(" (x").append(count).append(')');
      }
    }
  }

  private String renderMobLocation(MobLocation where)
  {
    String ret="";
    // Mob division
    MobDivision mobDivision=where.getMobDivision();
    if (mobDivision!=null)
    {
      ret=mobDivision.getLabel();
    }
    // Land division
    LandDivision landDivision=where.getLandDivision();
    if (landDivision!=null)
    {
      String landDivisionName=landDivision.getName();
      if (ret.length()>0)
      {
        ret=ret+"/"+landDivisionName;
      }
      else
      {
        ret=landDivisionName;
      }
    }
    // Landmark
    LandmarkDescription landmark=where.getLandmark();
    if (landmark!=null)
    {
      if (ret.length()>0)
      {
        ret=ret+"/"+landmark.getName();
      }
      else
      {
        ret=landmark.getName();
      }
    }
    return ret;
  }

  private void handleLandmarkDetectionCondition(StringBuilder sb, LandmarkDetectionCondition condition)
  {
    boolean hasProgressOverride=printProgressOverride(sb,condition);
    if (!hasProgressOverride)
    {
      LandmarkDescription landmark=condition.getLandmark();
      if (landmark!=null)
      {
        String name=landmark.getName();
        sb.append("Find ").append(name); // I18n
      }
    }
  }

  private void handleInventoryItemCondition(StringBuilder sb, InventoryItemCondition condition)
  {
    handleItemCondition(sb,condition,"Get"); // I18n
  }

  private void handleItemUsedCondition(StringBuilder sb, ItemUsedCondition condition)
  {
    handleItemCondition(sb,condition,"Use"); // I18n
  }

  private void handleExternalInventoryItemCondition(StringBuilder sb, ExternalInventoryItemCondition condition)
  {
    handleItemCondition(sb,condition,"Obtain"); // I18n
  }

  private void handleItemTalkCondition(StringBuilder sb, ItemTalkCondition condition)
  {
    handleItemCondition(sb,condition,"Use"); // I18n
  }

  private void handleItemCondition(StringBuilder sb, ItemCondition condition, String verb)
  {
    boolean hasProgressOverride=printProgressOverrideWithCount(sb,condition);
    if (!hasProgressOverride)
    {
      Item item=condition.getItem();
      if (item!=null)
      {
        sb.append(verb).append(' ');
        String rawName=item.getName();
        String name=render(rawName);
        _output.printLink(sb,name,item);
        int count=condition.getCount();
        if (count>1)
        {
          sb.append(" x").append(count);
        }
      }
    }
  }

  private void handleFactionLevelCondition(StringBuilder sb, FactionLevelCondition condition)
  {
    boolean hasProgressOverride=printProgressOverride(sb,condition);
    if (!hasProgressOverride)
    {
      Faction faction=condition.getFaction();
      int tier=condition.getTier();
      if (faction!=null)
      {
        String name=faction.getName();
        String levelName="?";
        FactionLevel level=faction.getLevelByTier(tier);
        if (level!=null)
        {
          levelName=render(level.getName());
        }
        sb.append("Reach reputation '").append(levelName); // I18n
        sb.append("' (tier ").append(tier).append(") with ").append(name); // I18n
      }
    }
  }

  private void handleSkillUsedCondition(StringBuilder sb, SkillUsedCondition condition)
  {
    boolean hasProgressOverride=printProgressOverrideWithCount(sb,condition);
    if (!hasProgressOverride)
    {
      SkillDescription skill=condition.getSkill();
      if (skill!=null)
      {
        String name=skill.getName();
        sb.append("Use skill ").append(name); // I18n
        int count=condition.getCount();
        if (count>1)
        {
          sb.append(" x").append(count);
        }
        Integer maxPerDay=condition.getMaxPerDay();
        if (maxPerDay!=null)
        {
          sb.append(" (max ").append(maxPerDay).append("/day)"); // I18n
        }
      }
      else
      {
        sb.append("No skill and no progress override");
      }
    }
  }

  private void handleNpcTalkCondition(StringBuilder sb, NpcTalkCondition condition)
  {
    handleNpcCondition(sb,condition);
  }

  private void handleNpcUsedCondition(StringBuilder sb, NpcUsedCondition condition)
  {
    handleNpcCondition(sb,condition);
  }

  private void handleNpcCondition(StringBuilder sb, NpcCondition condition)
  {
    boolean hasProgressOverride=printProgressOverride(sb,condition);
    if (!hasProgressOverride)
    {
      Interactable npc=condition.getNpc();
      if (npc!=null)
      {
        String name=npc.getName();
        String action=condition.getAction();
        sb.append(action).append(' ').append(name);
      }
      else
      {
        List<AchievableGeoPoint> points=condition.getPoints();
        if (!points.isEmpty())
        {
          String action=condition.getAction();
          sb.append(action).append(" NPC at "); // I18n
          sb.append(getPositions(points));
        }
        else
        {
          LOGGER.warn("No NPC and no progress override");
        }
      }
    }
  }

  private void handleLevelCondition(StringBuilder sb, LevelCondition condition)
  {
    boolean hasProgressOverride=printProgressOverride(sb,condition);
    if (!hasProgressOverride)
    {
      int level=condition.getLevel();
      sb.append("Reach level ").append(level); // I18n
    }
  }

  private void handleQuestBestowedCondition(StringBuilder sb, QuestBestowedCondition questBestowed)
  {
    boolean hasProgressOverride=printProgressOverride(sb,questBestowed);
    if (!hasProgressOverride)
    {
      Proxy<Achievable> proxy=questBestowed.getProxy();
      if (proxy!=null)
      {
        String link=getAchievableLink(proxy,null);
        sb.append("Have ").append(link).append(" bestowed"); // I18n
      }
    }
  }

  private void handleDetectingCondition(StringBuilder sb, DetectingCondition condition)
  {
    handleDetectionCondition(sb,condition);
  }

  private void handleEnterDetectionCondition(StringBuilder sb, EnterDetectionCondition condition)
  {
    handleDetectionCondition(sb,condition);
  }

  private void handleDetectionCondition(StringBuilder sb, DetectionCondition condition)
  {
    boolean hasProgressOverride=printProgressOverride(sb,condition);
    if (!hasProgressOverride)
    {
      String target=getTarget(condition.getTarget());
      if (target!=null)
      {
        sb.append("Go near ").append(target); // I18n
      }
      else
      {
        List<AchievableGeoPoint> points=condition.getPoints();
        if (!points.isEmpty())
        {
          String action=condition.getAction();
          sb.append(action).append("Go near "); // I18n
          sb.append(getPositions(points));
        }
        else
        {
          LOGGER.warn("No NPC, no mob and no progress override");
        }
      }
    }
  }

  private void handleEmoteCondition(StringBuilder sb, EmoteCondition condition)
  {
    boolean hasProgressOverride=printProgressOverride(sb,condition);
    if (!hasProgressOverride)
    {
      EmoteDescription emote=condition.getEmote();
      String command=emote.getName();
      sb.append("Perform emote ").append(command); // I18n
      int count=condition.getCount();
      if (count>1)
      {
        sb.append(" x").append(count);
      }
      Integer maxDaily=condition.getMaxDaily();
      if (maxDaily!=null)
      {
        sb.append(" (max ").append(maxDaily).append("/day"); // I18n
      }
      ConditionTarget target=condition.getTarget();
      String targetLabel=getTarget(target);
      if (targetLabel!=null)
      {
        sb.append(" on "); // I18n
        sb.append(targetLabel);
      }
    }
  }

  private void handleTimeExpiredCondition(StringBuilder sb, TimeExpiredCondition condition)
  {
    boolean hasProgressOverride=printProgressOverride(sb,condition);
    if (!hasProgressOverride)
    {
      sb.append("Timer expired");
      int duration=condition.getDuration();
      if (duration>0)
      {
        sb.append(" (").append(Duration.getSmartDurationString(duration)).append(')');
      }
    }
  }

  private void handleHobbyCondition(StringBuilder sb, HobbyCondition condition)
  {
    handleItemCondition(sb,condition,"Catch"); // I18n
  }

  private static String getTarget(ConditionTarget target)
  {
    String ret=null;
    if (target!=null)
    {
      AgentDescription agent=target.getAgent();
      if (agent!=null)
      {
        ret=agent.getName();
      }
    }
    return ret;
  }

  private String getAchievableLink(Proxy<Achievable> proxy, String text)
  {
    StringBuilder sb=new StringBuilder();
    Achievable achievable=proxy.getObject();
    if (achievable!=null)
    {
      if (text==null)
      {
        boolean isQuest=(achievable instanceof QuestDescription);
        String type=isQuest?"quest ":"deed "; // I18n
        sb.append(type);
        text=achievable.getName();
        text=render(text);
      }
      text=TextSanitizer.sanitize(text);
      _output.startBold(sb);
      _output.printLink(sb,text,achievable);
      _output.endBold(sb);
    }
    else
    {
      LOGGER.warn("Could not resolve deed/quest ID="+proxy.getId()+", name="+proxy.getName());
      sb.append("quest/deed "+proxy.getId()); // I18n
    }
    return sb.toString();
  }

  /**
   * Counters.
   */
  public static Map<ConditionType,IntegerHolder> _counters=new EnumMap<ConditionType,IntegerHolder>(ConditionType.class);

  private void handleDefaultCondition(StringBuilder sb, DefaultObjectiveCondition condition)
  {
    boolean hasProgressOverride=printProgressOverride(sb,condition);
    if (!hasProgressOverride)
    {
      ConditionType type=condition.getType();
      String text=getDefaultConditionText(type);
      if (text!=null)
      {
        sb.append(text);
      }
      else
      {
        LOGGER.warn("Missing data for condition: type="+type);
        /*
        IntegerHolder counter=_counters.get(type);
        if (counter==null)
        {
          counter=new IntegerHolder();
          _counters.put(type,counter);
        }
        counter.increment();
        */
      }
    }
  }

  private static String getDefaultConditionText(ConditionType type)
  {
    if (type==ConditionType.SELF_DIED)
    {
      return "Died";
    }
    else if (type==ConditionType.DISMOUNTED)
    {
      return "Dismounted";
    }
    else if (type==ConditionType.TELEPORTED)
    {
      return "Teleported";
    }
    return null;
  }

  private static boolean hasProgressOverride(ObjectiveCondition condition)
  {
    String progressOverride=condition.getProgressOverride();
    return ((progressOverride!=null) && (progressOverride.length()>0));
  }

  private boolean printProgressOverrideWithCount(StringBuilder sb, ObjectiveCondition condition)
  {
    String progressOverride=getProgressOverrideWithCount(condition);
    if (progressOverride!=null)
    {
      _output.printText(sb,progressOverride);
      return true;
    }
    return false;
  }

  private String getProgressOverrideWithCount(ObjectiveCondition condition)
  {
    int count=condition.getCount();
    String progressOverride=condition.getProgressOverride();
    if ((progressOverride!=null) && (progressOverride.length()>0))
    {
      if (progressOverride.contains(COUNT_PATTERN))
      {
        String countStr="0/"+count;
        progressOverride=progressOverride.replace(COUNT_PATTERN,countStr);
      }
      else
      {
        if (count>1)
        {
          progressOverride=progressOverride+" (x"+count+")";
        }
      }
    }
    progressOverride=render(progressOverride);
    return progressOverride;
  }

  private boolean printProgressOverride(StringBuilder sb, ObjectiveCondition condition)
  {
    String progressOverride=condition.getProgressOverride();
    if ((progressOverride!=null) && (progressOverride.length()>0))
    {
      progressOverride=getProgressOverrideWithCount(condition);
      _output.printText(sb,progressOverride);
      return true;
    }
    return false;
  }

  private void printLoreInfo(StringBuilder sb, ObjectiveCondition condition)
  {
    String loreInfo=condition.getLoreInfo();
    if ((loreInfo!=null) && (loreInfo.trim().length()>0))
    {
      loreInfo=render(loreInfo);
      _output.startParagraph(sb);
      _output.startItalic(sb);
      _output.printText(sb,loreInfo);
      _output.endItalic(sb);
      _output.endParagraph(sb);
    }
  }

  private String getPositions(List<AchievableGeoPoint> points)
  {
    StringBuilder sb=new StringBuilder();
    int index=0;
    for(AchievableGeoPoint point : points)
    {
      Point2D.Float lonLat=point.getLonLat();
      String positionStr=PositionUtils.getLabel(lonLat.y,lonLat.x,null);
      if (index>0) sb.append(" ; ");
      sb.append(positionStr);
      index++;
    }
    return sb.toString();
  }
}
