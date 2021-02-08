package markup;

import java.util.*;

public class Strikeout extends AbstractText {
    public Strikeout(List<AbstractText> s) {
    	super(s);
    	this.toMDString = "~";
    	this.toBBStringSt = "[s]";
    	this.toBBStringEnd = "[/s]";
    }
}