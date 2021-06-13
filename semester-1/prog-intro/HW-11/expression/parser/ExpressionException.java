package expression.parser;

/**
 * @author Pavel Lymar
 */
public class ExpressionException extends Exception{
    private final String massage;
 
    public ExpressionException(String massage) {
        super();
        this.massage = massage;
    }
    
    public String toString(){
        return this.massage;
    }
}