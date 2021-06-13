package markup;

import java.util.*;

public abstract class AbstractText implements Marker {
	public String toMDString = "";
	public String toBBStringSt = "";
	public String toBBStringEnd = "";
	public List<AbstractText> words;
    public StringBuilder text = new StringBuilder("");
    
    public AbstractText(String s) {
    	this.text.append(s);
    }

    public AbstractText(List<AbstractText> s) {
    	words = s;
        for (AbstractText i : s) {
            this.text.append(i);
        }
    }
    
    public void toMarkdown(StringBuilder s) {
    	s.append(toMDString);
    	for (AbstractText i : words) {
    		i.toMarkdown(s);
    	}
    	s.append(toMDString); 
    }

    public void toBBCode(StringBuilder s) {
    	s.append(toBBStringSt);
    	for (AbstractText i : words) {
    		i.toBBCode(s);
    	}
    	s.append(toBBStringEnd);
    }
}