package name.robertburrelldonkin.personal.fetchpop.app;

/*
MIT License

Copyright (c) 2018 Robert Burrell Donkin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static org.springframework.boot.SpringApplication.exit;

@SpringBootApplication
public class App implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        System.exit(exit(SpringApplication.run(App.class, args)));
    }

    private final Account account;

    public App(final Account account) {
        this.account = account;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Running FetchPop...");

        final List<String> operationNames = args.getNonOptionArgs();
        if (operationNames.isEmpty()) {
            logger.warn("No operations to execute. Pass operation names by the command line.");
        } else {
            operationNames
                    .stream()
                    .map(OperationFactory::nameToOperation)
                    .forEachOrdered(this.account::perform);
        }

        logger.info("Completed FetchPop. Bye.", args);
    }
}
