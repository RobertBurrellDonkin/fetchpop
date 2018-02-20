package name.robertburrelldonkin.personal.fetchpop.app;

import static org.springframework.boot.SpringApplication.exit;
import static org.springframework.boot.SpringApplication.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    private final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        System.exit(exit(run(App.class, args)));
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            // TODO: TDD
            // Account account = new Account(userName, credentials, hostName,
            // hostPort);
            // args.getNonOptionArgs().stream().forEachOrdered(op -> {
            // switch (op) {
            // case "status":
            // account.perform(new Client(), new
            // PrintStatusOperation(System.out));
            // break;
            // }
            // });
        };
    }

}
