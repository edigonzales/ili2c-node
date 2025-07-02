package ch.so.agi.ili2c;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ili2cLibTest {
    
    @TempDir
    File tempDirectory;

    @Test 
    public void fubar() throws IOException {
        // Prepare
        Path logFile = tempDirectory.toPath().resolve("ili2c.log");

        // Execute
        int ret = Ili2cLib.compileModelImpl("src/test/Test1.ili", logFile.toAbsolutePath().toString());
//        int ret = Ili2cLib.compileModelImpl("src/test/data/Test1.ili", "/Users/stefan/tmp/ili2c.log");
        
        // Validate
        assertTrue(true);
        String logContent = Files.readString(logFile);
        System.out.println("******"+logContent);
    }
}
