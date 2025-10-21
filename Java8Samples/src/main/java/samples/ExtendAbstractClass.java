package samples;

public class ExtendAbstractClass extends MyAbstractClass implements I1{

    @Override
    void m1() {
        System.out.println("this is m1111 method....");
    }

    @Override
    void m3() {
        System.out.println("this is m3 method");
    }

    @Override
    public int calculate(int a) {
        return 0;
    }
}
