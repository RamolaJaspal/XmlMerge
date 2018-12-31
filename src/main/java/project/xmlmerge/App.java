package project.xmlmerge;

import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.atteo.xmlcombiner.XmlCombiner;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

//https://github.com/atteo/xml-combiner

public class App 
{
    public static void main( String[] args ) throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
    	
    	// create combiner
    	XmlCombiner combiner = new XmlCombiner();
    	combiner.setFilter(new filter());
    	// combine files
    	combiner.combine(Paths.get("testng1.xml"));
    	combiner.combine(Paths.get("testng2.xml"));
    	// store the result
    	combiner.buildDocument(Paths.get("Result.xml"));
    	
    }
}

class filter implements XmlCombiner.Filter
{

	public void postProcess(Element recessive, Element dominant, Element result) {
		if (recessive == null || dominant == null) {
			return;
		}
		Attr recessiveNode = recessive.getAttributeNode("tests");
		Attr dominantNode = dominant.getAttributeNode("tests");
		if (recessiveNode == null || dominantNode == null) {
			return;
		}

		int recessiveWeight = Integer.parseInt(recessiveNode.getValue());
		int dominantWeight = Integer.parseInt(dominantNode.getValue());

		result.setAttribute("tests", Integer.toString(recessiveWeight + dominantWeight));
		
		 recessiveNode = recessive.getAttributeNode("failures");
		 dominantNode = dominant.getAttributeNode("failures");
		if (recessiveNode == null || dominantNode == null) {
			return;
		}
		recessiveWeight = Integer.parseInt(recessiveNode.getValue());
		dominantWeight = Integer.parseInt(dominantNode.getValue());
		result.setAttribute("failures", Integer.toString(recessiveWeight + dominantWeight));

		 recessiveNode = recessive.getAttributeNode("errors");
		 dominantNode = dominant.getAttributeNode("errors");
		if (recessiveNode == null || dominantNode == null) {
			return;
		}
		recessiveWeight = Integer.parseInt(recessiveNode.getValue());
		dominantWeight = Integer.parseInt(dominantNode.getValue());
		result.setAttribute("errors", Integer.toString(recessiveWeight + dominantWeight));
		
		
	}
	}