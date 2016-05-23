package funny;

/**
 * Created by Tony on 23.05.2016.
 */
public class Service {

    private static final Service INSTANCE = new Service();

    public static Service getInstance() {

        return INSTANCE;

    }

    public void sayHello(String name) {

        System.out.println("Hello " + name);

    }

}
