const path = require('path');
// const platform = process.platform;
// const arch = process.arch;

// const prebuildDir = path.join(__dirname, 'prebuilds', `${platform}-${arch}`);

// console.log("***** " + prebuildDir)


// // fix dynamic lib loading for mac/linux
// if (platform === 'darwin') {
//   process.env.DYLD_LIBRARY_PATH = prebuildDir;
// }
// if (platform === 'linux') {
//   process.env.LD_LIBRARY_PATH = prebuildDir;
// }

// // native binding
// const native = require(path.join(prebuildDir, 'ili2c.node'));

// const native = require("node-gyp-build")(path.join(__dirname));

const platform = process.platform;
const arch = process.arch;

let runtime = "node";
if (process.versions.electron) {
  runtime = "electron";
}

if (process.platform === "win32") {
  const dllFolder = path.join(__dirname, 'prebuilds', `${platform}-${arch}`);
  process.env.PATH = `${dllFolder};${process.env.PATH}`;
}

const nativePath = path.join(__dirname, 'prebuilds', `${platform}-${arch}`, runtime, 'ili2c.node');
const native = require(nativePath);

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
