package markup;

import java.util.*;

public class Strong extends AbstractText {
    public Strong(List<AbstractText> s) {
    	super(s);
    	this.toMDString = "__";
    	this.toBBStringSt = "[b]";
    	this.toBBStringEnd = "[/b]";
    }
}