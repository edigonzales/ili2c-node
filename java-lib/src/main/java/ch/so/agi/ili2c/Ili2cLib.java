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
    public static int compileModel(IsolateThread thread, CCharPointer iliFile, CCharPointer logFile) {        
        FileLogger fileLogger = new FileLogger(new File(CTypeConversion.toJavaString(logFile)), false);
        EhiLogger.getInstance().addListener(fileLogger);
        
        IliManager manager = new IliManager();        
        manager.setRepositories(Ili2cSettings.DEFAULT_ILIDIRS.split(";"));
        //manager.setRepositories(ilidirs.split(";"));
        ArrayList<String> iliFiles = new ArrayList<String>();
        iliFiles.add(CTypeConversion.toJavaString(iliFile));
        Configuration config;
        try {
            config = manager.getConfigWithFiles(iliFiles);
        } catch (Ili2cException e) {
            EhiLogger.getInstance().removeListener(fileLogger);
            return 1;
        } 
        
        TransferDescription td = null;
        try {
            td = ch.interlis.ili2c.Ili2c.runCompiler(config);
        } catch (Ili2cFailure e) {
            EhiLogger.getInstance().removeListener(fileLogger);
            return 1;
        }

        EhiLogger.getInstance().removeListener(fileLogger);
        return 0;
    }
}
