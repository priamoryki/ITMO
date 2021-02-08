package markup;

import java.util.*;

public class Emphasis extends AbstractText {
    public Emphasis(List<AbstractText> s) {
    	super(s);
    	this.toMDString = "*";
    	this.toBBStringSt = "[i]";
    	this.toBBStringEnd = "[/i]";
    }
}