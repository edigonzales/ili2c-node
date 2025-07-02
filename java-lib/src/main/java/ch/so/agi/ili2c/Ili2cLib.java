package ch.so.agi.ili2c;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        String iliFileStr = CTypeConversion.toJavaString(iliFile);
        String logFileStr = CTypeConversion.toJavaString(logFile);
        return compileModelImpl(iliFileStr, logFileStr);        
    }
    
    public static int compileModelImpl(String iliFile, String logFile) {
        FileLogger fileLogger = new FileLogger(new File(logFile), false);
        EhiLogger.getInstance().addListener(fileLogger);
        
        EhiLogger.logState("ilifile <" + iliFile + ">");
        
        IliManager manager = new IliManager();        
        manager.setRepositories(Ili2cSettings.DEFAULT_ILIDIRS.split(";"));
        //manager.setRepositories(ilidirs.split(";"));
        ArrayList<String> iliFiles = new ArrayList<String>();        
        iliFiles.add(iliFile);
        Configuration config;
        try {
            config = manager.getConfigWithFiles(iliFiles);
        } catch (Ili2cException e) {
            EhiLogger.getInstance().removeListener(fileLogger);
            return 1;
        } 
        
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        String dateOut = dateFormatter.format(today);
        
        TransferDescription td = null;
        try {
            td = ch.interlis.ili2c.Ili2c.runCompiler(config);
        } catch (Ili2cFailure e) {
            e.printStackTrace();
            EhiLogger.logError("...compiler run failed " + dateOut);
            EhiLogger.getInstance().removeListener(fileLogger);
            return 1;
        }

        EhiLogger.logState("...compiler run done " + dateOut);
        EhiLogger.getInstance().removeListener(fileLogger);
        return 0;
    }
}
