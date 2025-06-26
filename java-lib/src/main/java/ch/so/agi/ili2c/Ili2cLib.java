package ch.so.agi.ili2c;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import ch.ehi.basics.logging.EhiLogger;
import ch.interlis.ili2c.Ili2cException;
import ch.interlis.ili2c.Ili2cFailure;
import ch.interlis.ili2c.Ili2cSettings;
import ch.interlis.ili2c.config.Configuration;
import ch.interlis.ili2c.metamodel.TransferDescription;
import ch.interlis.ilirepository.IliManager;
import ch.interlis.iox_j.logging.FileLogger;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;

public class Ili2cLib {
    
    @CEntryPoint(name = "compileModel")
    public static boolean compileModel(IsolateThread thread, CCharPointer iliFile, CCharPointer logFile) {
        FileLogger fileLogger = new FileLogger(new File(CTypeConversion.toJavaString(logFile)), false);
        EhiLogger.getInstance().addListener(fileLogger);

        
        IliManager manager = new IliManager();        
        manager.setRepositories(Ili2cSettings.DEFAULT_ILIDIRS.split(";"));
        //manager.setRepositories(ilidirs.split(";"));
        ArrayList<String> iliFiles = new ArrayList<String>();
        //ilifiles.add("src/test/data/SO_ARP_SEin_Konfiguration_20250115.ili");
        iliFiles.add(CTypeConversion.toJavaString(iliFile));
        Configuration config;
        try {
            config = manager.getConfigWithFiles(iliFiles);
        } catch (Ili2cException e) {
            EhiLogger.getInstance().removeListener(fileLogger);
            return false;
        } 
        
        TransferDescription td = null;
        try {
            td = ch.interlis.ili2c.Ili2c.runCompiler(config);
        } catch (Ili2cFailure e) {
            EhiLogger.getInstance().removeListener(fileLogger);
            return false;
        }

        EhiLogger.getInstance().removeListener(fileLogger);
        return true;
    }
}
