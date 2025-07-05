package ch.so.agi.ili2c;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.ehi.basics.logging.EhiLogger;
import ch.interlis.ili2c.Ili2cException;
import ch.interlis.ili2c.Ili2cFailure;
import ch.interlis.ili2c.Ili2cSettings;
import ch.interlis.ili2c.ModelScan;
import ch.interlis.ili2c.config.Configuration;
import ch.interlis.ili2c.generator.Interlis2Generator;
import ch.interlis.ili2c.metamodel.Ili2cMetaAttrs;
import ch.interlis.ili2c.metamodel.Model;
import ch.interlis.ili2c.metamodel.TransferDescription;
import ch.interlis.ilirepository.IliManager;
import ch.interlis.ili2c.parser.Ili23Parser;
import ch.interlis.ili2c.parser.Ili24Parser;
import ch.interlis.iox_j.logging.FileLogger;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;



public class Ili2cLib {
    
    @CEntryPoint(name = "prettyPrint")
    public static int prettyPrint(IsolateThread thread, CCharPointer iliFile) {   
        String iliFileStr = CTypeConversion.toJavaString(iliFile);
        return prettyPrintImpl(iliFileStr);        
    }

    public static int prettyPrintImpl(String iliFile) {        
        //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));    
        Path resultIliFile;
        OutputStreamWriter out;
        try {
            resultIliFile = Files.createTempFile("", ".ili");            
            out = new OutputStreamWriter(new FileOutputStream(resultIliFile.toAbsolutePath().toString()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
      
        // So kennt er die importierten Modell nicht!?
//        TransferDescription desc = new TransferDescription();
//        InputStreamReader stream;
//        try {
//            stream = new InputStreamReader(new FileInputStream(iliFile),"UTF-8");
//        } catch (UnsupportedEncodingException | FileNotFoundException e) {
//            e.printStackTrace();
//            return 1;
//        }
//        double version = ModelScan.getIliFileVersion(new File(iliFile));
//        if (version == 2.3) {
//            if (!Ili23Parser.parseIliFile(desc, iliFile, stream,
//                    true, 0, new Ili2cMetaAttrs())) {
//                return 1;
//            }
//        } else if (version == 2.4) {
//            
//        } else {
//            System.err.println("not supported version: <"+version+">");
//            return 1;
//        }
                
        try {
            TransferDescription desc = new TransferDescription();
            TransferDescription td = getTransferDescription(iliFile);
            for (Model model : td.getModelsFromLastFile()) {
                desc.add(model);
            }
            
            Interlis2Generator gen = new Interlis2Generator();
            gen.generate(out, desc, false); // emitPredefined = config.isIncPredefModel() ?
            
            Files.copy(resultIliFile, Paths.get(iliFile), StandardCopyOption.REPLACE_EXISTING);
            return 0;
        } catch (IOException | Ili2cException e) {
            e.printStackTrace();
            return 1;
        }
    }
    
    @CEntryPoint(name = "compileModel")
    public static int compileModel(IsolateThread thread, CCharPointer iliFile, CCharPointer logFile) {   
        String iliFileStr = CTypeConversion.toJavaString(iliFile);
        String logFileStr = CTypeConversion.toJavaString(logFile);
        return compileModelImpl(iliFileStr, logFileStr);        
    }
    
    public static int compileModelImpl(String iliFile, String logFile) {
        try {
            Files.deleteIfExists(new File(logFile).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileLogger fileLogger = new FileLogger(new File(logFile), false);
        EhiLogger.getInstance().addListener(fileLogger);

        EhiLogger.logState("ili2c-"+TransferDescription.getVersion());
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
            EhiLogger.logError("...compiler run failed " + dateOut);
            EhiLogger.getInstance().removeListener(fileLogger);
            return 1;
        }

        EhiLogger.logState("...compiler run done " + dateOut);
        EhiLogger.getInstance().removeListener(fileLogger);
        return 0;
    }
    
    private static TransferDescription getTransferDescription(String iliFile) throws Ili2cException {
        IliManager manager = new IliManager();        
        manager.setRepositories(Ili2cSettings.DEFAULT_ILIDIRS.split(";"));
        //manager.setRepositories(ilidirs.split(";"));
        ArrayList<String> iliFiles = new ArrayList<String>();        
        iliFiles.add(iliFile);
        Configuration config = manager.getConfigWithFiles(iliFiles);
        TransferDescription td = ch.interlis.ili2c.Ili2c.runCompiler(config);
        return td;
    }
}
