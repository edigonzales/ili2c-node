const ili2c = require('./index');

const result = ili2c.runCompileModel();


// async function runTests() {
//     console.log('Testing ili2c module with Koffi...');
    
//     try {
//         // Test with sample inputs - adjust based on your ili2c requirements
//         const testInput1 = 'SO_ARP_SEin_Konfiguration_20250115.ili';
//         const testInput2 = 'ili2c.log';
        
//         console.log('Testing compileModel with inputs:');
//         console.log('Input 1:', testInput1);
//         console.log('Input 2:', testInput2);
        
//         const result = ili2c.compileModel(testInput1, testInput2);
//         console.log('Compilation result (boolean):', result);
        
//         if (result) {
//             console.log('✅ Compilation succeeded!');
//         } else {
//             console.log('❌ Compilation failed!');
//         }
        
//         console.log('Test completed successfully!');
        
//     } catch (error) {
//         console.error('Test failed:', error.message);
        
//         // Additional debugging information
//         console.log('\nDebugging information:');
//         console.log('Platform:', process.platform);
//         console.log('Architecture:', process.arch);
//         console.log('Node.js version:', process.version);
        
//         process.exit(1);
//     }
// }

// runTests();