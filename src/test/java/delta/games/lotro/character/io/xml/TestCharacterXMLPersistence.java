package delta.games.lotro.character.io.xml;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.CharacterGenerationTools;
import delta.games.lotro.character.stats.CharacterGeneratorGiswald;

/**
 * Test character persistence in XML.
 * @author DAM
 */
public class TestCharacterXMLPersistence extends TestCase
{
  /**
   * Test that writes then read a generated character.
   */
  public void testWriteReadCharacter()
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
    Assert.assertEquals(c1.getName(),c2.getName());
  }
}
