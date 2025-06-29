const koffi = require('koffi');
const path = require('path');

// Load the library - adjust the path and extension for your platform
const libPath = './lib/macos/libili2c.dylib';
const lib = koffi.load(libPath);

// Define the opaque types
const graal_isolate_t = koffi.opaque('graal_isolate_t');
const graal_isolatethread_t = koffi.opaque('graal_isolatethread_t');

// Define the functions
const graal_create_isolate = lib.func('graal_create_isolate', 'int', ['void*', 'graal_isolate_t**', 'graal_isolatethread_t**']);
const graal_tear_down_isolate = lib.func('graal_tear_down_isolate', 'int', ['graal_isolatethread_t*']);
const compileModel = lib.func('compileModel', 'int', ['graal_isolatethread_t*', 'str', 'str']);

function runCompileModel() {
    console.log("Hallo Welt.");

    const isolatePtr = [null];
    const isolateThreadPtr = [null];
    
    // Create isolate
    const createResult = graal_create_isolate(null, isolatePtr, isolateThreadPtr);
    if (createResult !== 0) {
        throw new Error(`Failed to create isolate: ${createResult}`);
    }
    
    try {
        // Call compileModel
        const result = compileModel(isolateThreadPtr[0], param1, param2);
        return result;
    } finally {
        // Clean up
        if (isolateThreadPtr[0]) {
            graal_tear_down_isolate(isolateThreadPtr[0]);
        }
    }
}

module.exports = { runCompileModel };
