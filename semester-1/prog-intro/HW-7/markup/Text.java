package markup;

public class Text extends AbstractText {
    public Text(String s) {
        super(s);
    }
    
    @Override
    public void toMarkdown(StringBuilder s) {
    	s.append(this.text);
    }
    
    @Override
    public void toBBCode(StringBuilder s) {
    	s.append(this.text);
    }
}