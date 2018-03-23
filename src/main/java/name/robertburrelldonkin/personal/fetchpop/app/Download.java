package name.robertburrelldonkin.personal.fetchpop.app;

import static java.util.UUID.randomUUID;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
@Component
public class Download implements IOperation {

    private final Logger logger = LoggerFactory.getLogger(Download.class);

    private final File dir;

    public Download(@Value("${application.download.dir:.}") String downloadDirectory) {
        super();
        this.dir = new File(downloadDirectory);
    }

    @Override
    public void operateOn(ISession session) {
        logger.info("Downloading to directory {}", dir);
        if (!dir.exists()) {
            throw new IllegalArgumentException("Download directory should exist: " + dir.getAbsolutePath());
        }
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Download directory is not a directory: " + dir.getAbsolutePath());
        }
        session.list().stream().forEach(message -> message.downloadTo(nextFile()));
    }

    private File nextFile() {
        final File result = new File(dir, generateFileName());
        return result.exists() ? nextFile() : result;
    }

    private String generateFileName() {
        return randomUUID().toString() + ".msg";
    }
}
