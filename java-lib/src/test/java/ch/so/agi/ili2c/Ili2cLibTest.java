package ch.so.agi.ili2c;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Ili2cLibTest {
    
    @TempDir
    File tempDirectory;
    
    @Test
    public void createIlisMeta16_Ok() throws IOException {
        // Prepare
        Path iliFile = Paths.get("src/test/data/SO_ARP_SEin_Konfiguration_20250115.ili");
        Path xtfFile = tempDirectory.toPath().resolve("SO_ARP_SEin_Konfiguration_20250115.xtf");
        
        // Execute
        int ret = Ili2cLib.createIlisMetas16Impl(iliFile.toAbsolutePath().toString(), xtfFile.toAbsolutePath().toString());
        
        // Validate
        assertEquals(0, ret);
        String content = Files.readString(xtfFile);
        String expected = Files.readString(Paths.get("src/test/data/SO_ARP_SEin_Konfiguration_20250115.xtf"));
        assertEquals(expected.trim(), content.trim());
    }
    
    @Test
    public void prettyPrint_Ok() throws IOException {
        // Prepare
        Path source = Paths.get("src/test/data/SO_ARP_SEin_Konfiguration_20250116_unpretty.ili");
        Path target = tempDirectory.toPath().resolve(source.getFileName());
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        
        // Execute
        int ret = Ili2cLib.prettyPrintImpl(target.toAbsolutePath().toString());
        
        // Validate
        assertEquals(0, ret);
        String content = Files.readString(target);
        String expected = Files.readString(Paths.get("src/test/data/SO_ARP_SEin_Konfiguration_20250116_expected.ili"));
        assertEquals(expected.trim(), content.trim());
    }

    @Test 
    public void compileModel_NotValid() throws IOException {
        // Prepare
        Path logFile = tempDirectory.toPath().resolve("ili2c.log");

        // Execute
        int ret = Ili2cLib.compileModelImpl("src/test/data/Test1.ili", logFile.toAbsolutePath().toString());
//        int ret = Ili2cLib.compileModelImpl("src/test/data/Test1.ili", "/Users/stefan/tmp/ili2c.log");
        
        // TODO
        // Validate
//        assertEquals(0, ret);
        String logContent = Files.readString(logFile);
        System.out.println(logContent);
//        assertTrue(logContent.contains("Info: ...compiler run done"));
    }
}
