package expression;

public class Square<T> extends AbstractUnaryOperation<T> {
    public Square(AbstractComponent c, UnaryOperations<T> operationType) {
        super(c, operationType);
    }

    @Override
    public T apply(T a) {
        return operationType.square(a);
    }
}