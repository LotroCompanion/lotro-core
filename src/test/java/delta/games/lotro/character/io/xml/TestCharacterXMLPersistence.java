package delta.games.lotro.character.io.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.CharacterGenerationTools;
import delta.games.lotro.character.stats.CharacterGeneratorGiswald;

/**
 * Test character persistence in XML.
 * @author DAM
 */
class TestCharacterXMLPersistence
{
  /**
   * Test that writes then read a generated character.
   */
  @Test
  void testWriteReadCharacter()
  {
    CharacterGenerationTools tools=new CharacterGenerationTools();
    CharacterGeneratorGiswald generator=new CharacterGeneratorGiswald(tools);
    CharacterData c=generator.buildCharacter();
    System.out.println("Original: "+c);
    File tmpFile=new File("giswald.xml");
    tmpFile.deleteOnExit();
    CharacterXMLWriter writer=new CharacterXMLWriter();
    writer.write(tmpFile,c,EncodingNames.UTF_8);
    CharacterData newCharacter=CharacterDataIO.getCharacterDescription(tmpFile);
    System.out.println("Reloaded: "+newCharacter);
    compareCharacters(c,newCharacter);
  }

  private void compareCharacters(CharacterData c1, CharacterData c2)
  {
    assertEquals(c1.getName(),c2.getName());
  }
}
