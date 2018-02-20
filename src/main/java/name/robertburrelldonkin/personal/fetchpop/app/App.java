package name.robertburrelldonkin.personal.fetchpop.app;

import static org.springframework.boot.SpringApplication.exit;
import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        System.exit(exit(run(App.class, args)));
    }
}
