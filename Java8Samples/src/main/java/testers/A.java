package testers;

public class A {

//    input = 8.547
//    output = 8.55

    public static void main(String[] args) {
        double input = 8.547;

//        remainder
        call(null);

    }

    private static void call(String s) {
        System.out.println("string called");
    }

    private static void call(Object o) {
        System.out.println("Object called");
    }




}
