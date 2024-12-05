package delta.games.lotro.lore.traits;

import org.junit.jupiter.api.Test;

import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.TraitNature;

/**
 * To display the trait natures as found in TraitSystem::IsTraitNaturePersistent. 
 * @author DAM
 */
class PersistentTraitNaturesTest
{
  @Test
 void traits()
  {
    int[] traitNatures=new int[]{1, 0x0D, 0x0E, 0x13, 0x14, 0x15, 0x17, 0x18,
        0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x20, 0x21
    };
    LotroEnum<TraitNature> enumTN=LotroEnumsRegistry.getInstance().get(TraitNature.class);
    for(int code : traitNatures)
    {
      TraitNature tn=enumTN.getEntry(code);
      System.out.println(tn);
    }
  }
}
