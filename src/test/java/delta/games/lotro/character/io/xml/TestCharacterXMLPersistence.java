package delta.games.lotro.character.io.xml;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.Character;
import delta.games.lotro.character.stats.CharacterGenerator;

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
    CharacterGenerator generator=new CharacterGenerator();
    Character c=generator.buildCharacter();
    System.out.println("Original: "+c);
    File tmpFile=new File("giswald.xml");
    tmpFile.deleteOnExit();
    CharacterXMLWriter writer=new CharacterXMLWriter();
    writer.write(tmpFile,c,EncodingNames.UTF_8);
    CharacterXMLParser parser=new CharacterXMLParser();
    Character newCharacter=parser.parseXML(tmpFile);
    System.out.println("Reloaded: "+newCharacter);
    compareCharacters(c,newCharacter);
  }

  private void compareCharacters(Character c1, Character c2)
  {
    Assert.assertEquals(c1.getName(),c2.getName());
  }
}
