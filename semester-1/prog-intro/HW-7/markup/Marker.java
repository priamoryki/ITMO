package markup;

public interface Marker {
	public void toMarkdown(StringBuilder s);
	
	public void toBBCode(StringBuilder s);
}