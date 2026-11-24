package design;

/**
 * Three thread-safe ways to write a Singleton (a class that has only one object).
 *
 * <p>Keywords: Singleton, double-checked locking, volatile, holder class, enum singleton.
 *
 * <p>First written: 2026-11-24.
 */
public class Singletons {

    // Way 1: double-checked locking. The object is created only when it is first needed.
    // volatile stops other threads from seeing an object that is only half built.
    static final class LazySingleton {
        private static volatile LazySingleton instance;

        private LazySingleton() {
        }

        static LazySingleton getInstance() {
            if (instance == null) {                  // 1st check: no lock, fast
                synchronized (LazySingleton.class) {
                    if (instance == null) {          // 2nd check: inside the lock
                        instance = new LazySingleton();
                    }
                }
            }
            return instance;
        }
    }

    // Way 2: holder class. The JVM creates Holder.INSTANCE once, the first time it is used,
    // and the JVM makes this thread-safe for us.
    static final class HolderSingleton {
        private HolderSingleton() {
        }

        private static final class Holder {
            private static final HolderSingleton INSTANCE = new HolderSingleton();
        }

        static HolderSingleton getInstance() {
            return Holder.INSTANCE;
        }
    }

    // Way 3: enum. The simplest. It is also safe against reflection and serialization tricks
    // that can create a second object of the other two kinds.
    enum EnumSingleton {
        INSTANCE;

        String hello() {
            return "Hello from the enum singleton";
        }
    }

    public static void main(String[] args) {
        System.out.println("Double-checked locking gives the same object: "
                + (LazySingleton.getInstance() == LazySingleton.getInstance()));      // true
        System.out.println("Holder class gives the same object: "
                + (HolderSingleton.getInstance() == HolderSingleton.getInstance()));  // true
        System.out.println(EnumSingleton.INSTANCE.hello());
    }
}
