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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.SocketException;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private final String username = "USERNAME";
	private final String password = "PASSWORD";
	private final File dir = new File("DIRECTORY");

	public static final void printMessageInfo(BufferedReader reader, int id) throws IOException {
		String from = "";
		String subject = "";
		String line;
		while ((line = reader.readLine()) != null) {
			String lower = line.toLowerCase(Locale.ENGLISH);
			if (lower.startsWith("from: ")) {
				from = line.substring(6).trim();
			} else if (lower.startsWith("subject: ")) {
				subject = line.substring(9).trim();
			}
		}

		System.out.println(Integer.toString(id) + " From: " + from + "  Subject: " + subject);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			FileUtils.forceMkdirParent(dir);
			for (int i = 0; i < 80; i++) {
				doOnce();
			}
		};
	}

	private void doOnce() throws SocketException, IOException {
		final POP3SClient client = new POP3SClient(true);
		client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
		client.connect("SERVER", 995);
		System.out.println("Connected");
		try {
			client.login(username, password);
			try {
				POP3MessageInfo status = client.status();
				if (status == null) {
					System.err.println("Could not retrieve status.");
					client.logout();
					client.disconnect();
					return;
				}

				POP3MessageInfo[] messages = client.listMessages();

				if (messages == null) {
					System.err.println("Could not retrieve message list.");
					client.logout();
					client.disconnect();
					return;
				} else if (messages.length == 0) {
					System.out.println("No messages");
					client.logout();
					client.disconnect();
					return;
				}

				System.out.println("Message count: " + messages.length);
				System.out.println("Status: " + status);

				for (int i = 1; i < 100; i++) {
					printMessage(client, i);
					System.out.println(
							(client.deleteMessage(i) ? "Successfully deleted" : "Failed to delete") + " message #" + i);
				}

			} finally {
				client.logout();
			}
		} finally {
			client.disconnect();
		}
	}

	private void printMessage(final POP3SClient client, final int messageId) throws IOException {
		final File file = new File(dir, UUID.randomUUID().toString() + ".msg");
		final FileWriter outputStreamWriter = new FileWriter(file);
		final Reader inboundMessageStream = client.retrieveMessage(messageId);
		long copied = IOUtils.copyLarge(inboundMessageStream, outputStreamWriter);
		outputStreamWriter.flush();
		inboundMessageStream.close();
		System.out.println("---------------------------------------------------------");
		System.out.println("Copied " + copied + " bytes to " + file);
	}

}
