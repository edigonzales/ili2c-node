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
        Ili2cLib ili2c = new Ili2cLib();
        //ili2c.runCompiler(logFile.toAbsolutePath().toString());
        
        // Validate
        assertTrue(true);
        String logContent = Files.readString(logFile);
        System.out.println("******"+logContent);
    }
}
