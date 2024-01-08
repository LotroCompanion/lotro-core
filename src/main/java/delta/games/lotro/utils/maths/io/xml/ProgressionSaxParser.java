package delta.games.lotro.utils.maths.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.utils.maths.ArrayProgression;
import delta.games.lotro.utils.maths.ArrayProgressionConstants;
import delta.games.lotro.utils.maths.LinearInterpolatingProgression;
import delta.games.lotro.utils.maths.Progression;

/**
 * SAX parser for progressions stored in XML.
 * @author DAM
 */
public final class ProgressionSaxParser extends DefaultHandler
{
  private static final Logger LOGGER=Logger.getLogger(ProgressionSaxParser.class);

  private List<Progression> _parsedProgressions;
  private ArrayProgression _arrayProgression;
  private LinearInterpolatingProgression _linearInterpolatingProgression;
  private int _index;

  private ProgressionSaxParser()
  {
    _parsedProgressions=new ArrayList<Progression>();
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return List of parsed items.
   */
  public static List<Progression> parseProgressionsFile(File source)
  {
    try
    {
      ProgressionSaxParser handler=new ProgressionSaxParser();

      // Use the default (non-validating) parser
      SAXParserFactory factory=SAXParserFactory.newInstance();
      SAXParser saxParser=factory.newSAXParser();
      saxParser.parse(source,handler);
      saxParser.reset();
      return handler._parsedProgressions;
    }
    catch (Exception e)
    {
      LOGGER.error("Error when loading progressions file "+source,e);
    }
    return null;
  }

  /**
   * Identify start of element.
   */

  @Override
  public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
  {

    if (ProgressionsXMLConstants.ARRAY_PROGRESSION_TAG.equals(qualifiedName))
    {
      String identifierStr=attributes.getValue(ProgressionsXMLConstants.IDENTIFIER_ATTR);
      int identifier=NumericTools.parseInt(identifierStr,0);
      int minX=SAXParsingTools.getIntAttribute(attributes,ProgressionsXMLConstants.MIN_X_ATTR,1);
      int nbPoints=SAXParsingTools.getIntAttribute(attributes,ProgressionsXMLConstants.NB_POINTS_ATTR,0);
      _index=minX;
      String type=SAXParsingTools.getStringAttribute(attributes,ProgressionsXMLConstants.TYPE_ATTR,ArrayProgressionConstants.FLOAT);
      _arrayProgression=new ArrayProgression(identifier,type,minX,nbPoints);
      _parsedProgressions.add(_arrayProgression);
    }
    else if (ProgressionsXMLConstants.LINEAR_INTERPOLATION_PROGRESSION_TAG.equals(qualifiedName))
    {
      String identifierStr=attributes.getValue(ProgressionsXMLConstants.IDENTIFIER_ATTR);
      int identifier=NumericTools.parseInt(identifierStr,0);
      String nbPointsStr=attributes.getValue(ProgressionsXMLConstants.NB_POINTS_ATTR);
      int nbPoints=NumericTools.parseInt(nbPointsStr,0);
      _index=0;
      _linearInterpolatingProgression=new LinearInterpolatingProgression(identifier,nbPoints);
      _parsedProgressions.add(_linearInterpolatingProgression);
    }
    else if (ProgressionsXMLConstants.POINT_TAG.equals(qualifiedName))
    {
      if (_linearInterpolatingProgression!=null)
      {
        int x=SAXParsingTools.getIntAttribute(attributes,ProgressionsXMLConstants.X_ATTR,0);
        float y=SAXParsingTools.getFloatAttribute(attributes,ProgressionsXMLConstants.Y_ATTR,0);
        _linearInterpolatingProgression.set(_index,x,y);
        _index++;
      }
      else if (_arrayProgression!=null)
      {
        String yStr=attributes.getValue(ProgressionsXMLConstants.Y_ATTR);
        Number y=_arrayProgression.parseValue(yStr);
        int count=SAXParsingTools.getIntAttribute(attributes,ProgressionsXMLConstants.COUNT_ATTR,1);
        for(int i=0;i<count;i++)
        {
          _arrayProgression.set(_index,y);
          _index++;
        }
      }
    }
    else
    {
      // ...
    }
  }

  /**
   * Identify end of element.
   */

  @Override
  public void endElement(String uri, String localName, String qualifiedName)
  {
    if (ProgressionsXMLConstants.ARRAY_PROGRESSION_TAG.equals(qualifiedName))
    {
      _arrayProgression=null;
    }
    else if (ProgressionsXMLConstants.LINEAR_INTERPOLATION_PROGRESSION_TAG.equals(qualifiedName))
    {
      _linearInterpolatingProgression=null;
    }
  }
}
