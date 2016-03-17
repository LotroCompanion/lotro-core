package delta.games.lotro.character.stats;

import delta.games.lotro.character.Character;
import delta.games.lotro.character.stats.base.BaseStatsManager;
import delta.games.lotro.character.stats.base.DerivatedStatsContributionsMgr;
import delta.games.lotro.character.stats.virtues.VirtuesContributionsMgr;
import delta.games.lotro.character.stats.virtues.VirtuesSet;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.VirtueId;

/**
 * Test for the character stats computer.
 * @author DAM
 */
public class TestCharacterStatsComputer
{
  private Character buildCharacter()
  {
    Character c=new Character();
    c.setName("Giswald");
    c.setRace(Race.MAN);
    c.setLevel(100);
    c.setCharacterClass(CharacterClass.CHAMPION);
    VirtuesSet virtues=c.getVirtues();
    virtues.setVirtueValue(VirtueId.CHARITY,15);
    virtues.setVirtueValue(VirtueId.COMPASSION,11);
    virtues.setVirtueValue(VirtueId.CONFIDENCE,10);
    virtues.setVirtueValue(VirtueId.DETERMINATION,8);
    virtues.setVirtueValue(VirtueId.DISCIPLINE,4);
    virtues.setVirtueValue(VirtueId.EMPATHY,14);
    virtues.setVirtueValue(VirtueId.FIDELITY,13);
    virtues.setVirtueValue(VirtueId.FORTITUDE,7);
    virtues.setVirtueValue(VirtueId.HONESTY,11);
    virtues.setVirtueValue(VirtueId.HONOUR,10);
    virtues.setVirtueValue(VirtueId.IDEALISM,15);
    virtues.setVirtueValue(VirtueId.JUSTICE,9);
    virtues.setVirtueValue(VirtueId.LOYALTY,14);
    virtues.setVirtueValue(VirtueId.MERCY,6);
    virtues.setVirtueValue(VirtueId.PATIENCE,7);
    virtues.setVirtueValue(VirtueId.INNOCENCE,13);
    virtues.setVirtueValue(VirtueId.TOLERANCE,12);
    virtues.setVirtueValue(VirtueId.VALOUR,7);
    virtues.setVirtueValue(VirtueId.WISDOM,11);
    virtues.setVirtueValue(VirtueId.ZEAL,11);
    virtues.setSelectedVirtue(VirtueId.INNOCENCE,0);
    virtues.setSelectedVirtue(VirtueId.CHARITY,1);
    virtues.setSelectedVirtue(VirtueId.EMPATHY,2);
    virtues.setSelectedVirtue(VirtueId.ZEAL,3);
    virtues.setSelectedVirtue(VirtueId.HONOUR,4);
    return c;
  }

  private BasicStatsSet getRawVirtuesContribution(VirtuesSet virtues)
  {
    BasicStatsSet ret=new BasicStatsSet();
    VirtuesContributionsMgr virtuesMgr=new VirtuesContributionsMgr();
    for(int i=0;i<VirtuesSet.MAX_VIRTUES;i++)
    {
      VirtueId virtue=virtues.getSelectedVirtue(i);
      if (virtue!=null)
      {
        int rank=virtues.getVirtueRank(virtue);
        BasicStatsSet virtueContrib=virtuesMgr.getContribution(virtue,rank);
        ret.addStats(virtueContrib);
      }
    }
    return ret;
  }

  private void doIt()
  {
    Character c=buildCharacter();
    BaseStatsManager baseStatsMgr=new BaseStatsManager();
    BasicStatsSet baseStats=baseStatsMgr.getBaseStats(c.getCharacterClass(),c.getRace(),c.getLevel());
    BasicStatsSet virtuesStats=getRawVirtuesContribution(c.getVirtues());
    BasicStatsSet raw=new BasicStatsSet();
    raw.addStats(baseStats);
    raw.addStats(virtuesStats);
    DerivatedStatsContributionsMgr derivedStatsMgr=new DerivatedStatsContributionsMgr();
    BasicStatsSet derivedCtontrib=derivedStatsMgr.getContribution(c.getCharacterClass(),raw);
    raw.addStats(derivedCtontrib);
    System.out.println(raw);
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new TestCharacterStatsComputer().doIt();
  }
}
