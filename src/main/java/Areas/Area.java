package Areas;

public interface Area<X extends Number, Y extends Number, Size extends Number> {
    public boolean isIn(X x, Y y, Size size);
}
