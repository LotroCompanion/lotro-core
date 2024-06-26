package delta.games.lotro.utils.strings;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.WellKnownCharacterClassKeys;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.common.Genders;
import delta.games.lotro.lore.parameters.Game;
import delta.games.lotro.lore.pvp.RankScale;
import delta.games.lotro.lore.pvp.RankScaleEntry;
import delta.games.lotro.lore.pvp.RankScaleKeys;
import delta.games.lotro.lore.pvp.RanksManager;

/**
 * Utility methods for contexte-sensitive strings rendering.
 * @author DAM
 */
public class RenderingUtils
{
  /**
   * Build a default summary.
   * @return A default summary.
   */
  public static BaseCharacterSummary buildDefaultSummary()
  {
    BaseCharacterSummary ret=new BaseCharacterSummary();
    // Class
    ClassDescription characterClass=ClassesManager.getInstance().getCharacterClassByKey(WellKnownCharacterClassKeys.CHAMPION);
    ret.setCharacterClass(characterClass);
    // Race
    RaceDescription race=RacesManager.getInstance().getByKey("man");
    ret.setRace(race);
    // Gender
    ret.setCharacterSex(Genders.MALE);
    // Level
    int maxLevel=Game.getParameters().getMaxCharacterLevel();
    ret.setLevel(maxLevel);
    // Name
    ret.setName("(character name)"); // I18n
    // Surname
    ret.setSurname("(surname)"); // I18n
    // Rank
    ret.setRankCode(null);
    return ret;
  }


  /**
   * Setup a context with the given character data.
   * @param summary Data to use.
   * @return the new context. 
   */
  public static Map<String,String> setupContext(BaseCharacterSummary summary)
  {
    if (summary==null)
    {
      summary=buildDefaultSummary();
    }
    Map<String,String> ret=new HashMap<String,String>();
    // Gender
    CharacterSex gender=summary.getCharacterSex();
    char genderTag=(gender==Genders.FEMALE)?'f':'m';
    // Name
    String name=summary.getName()+"["+genderTag+"]";
    ret.put(WellKnownVariables.NAME,name);
    ret.put(WellKnownVariables.PLAYERNAME,name);
    ret.put(WellKnownVariables.PLAYER_NAME,name);
    ret.put(WellKnownVariables.PLAYER,name);
    // Surname
    ret.put(WellKnownVariables.SURNAME,summary.getSurname());
    // Rank
    String rankLabel="";
    Integer rankCode=summary.getRankCode();
    if (rankCode!=null)
    {
      RankScale scale=RanksManager.getInstance().getRankScale(RankScaleKeys.RENOWN);
      RankScaleEntry rank=scale.getRankByCode(rankCode.intValue());
      if (rank!=null)
      {
        rankLabel=rank.getRank().getName();
      }
    }
    ret.put(WellKnownVariables.RANK,rankLabel);
    // Class
    ClassDescription characterClass=summary.getCharacterClass();
    if (characterClass!=null)
    {
      String className=characterClass.getName();
      String classTag=characterClass.getTag();
      ret.put(WellKnownVariables.CLASS,className+"["+classTag+"]");
    }
    // Race
    RaceDescription race=summary.getRace();
    if (race!=null)
    {
      String raceName=race.getName();
      String raceTag=race.getTag();
      ret.put(WellKnownVariables.RACE,raceName+"["+raceTag+"]");
    }
    return ret;
  }
}
