const path = require('path');
const platform = process.platform;
const arch = process.arch;

const prebuildDir = path.join(__dirname, 'prebuilds', `${platform}-${arch}`);

console.log("***** " + prebuildDir)


// fix dynamic lib loading for mac/linux
if (platform === 'darwin') {
  process.env.DYLD_LIBRARY_PATH = prebuildDir;
}
if (platform === 'linux') {
  process.env.LD_LIBRARY_PATH = prebuildDir;
}

// native binding
const native = require(path.join(prebuildDir, 'ili2c.node'));

console.log("***********.   " + native);


let initialized = false;

// wrapper function that automatically handles isolate
function compileModel(iliFile, logFile) {
  if (!initialized) {
    native.initIsolate();
    initialized = true;
  }
  return native.compileModel(iliFile, logFile);
}

// auto-teardown on exit
process.on('exit', () => {
  if (initialized) {
    native.tearDownIsolate();
  }
});

// expose only compileModel
module.exports = {
  compileModel
};
