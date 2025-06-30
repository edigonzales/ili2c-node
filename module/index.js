const path = require('path');
const platform = process.platform;
const arch = process.arch;

const prebuildDir = path.join(__dirname, 'prebuilds', `${platform}-${arch}`);

// set DYLD_LIBRARY_PATH on macOS so the .node can find the .dylib
if (platform === 'darwin') {
  process.env.DYLD_LIBRARY_PATH = prebuildDir;
}

// for Linux you’d use:
if (platform === 'linux') {
  process.env.LD_LIBRARY_PATH = prebuildDir;
}

// Windows will find the .dll if it is next to the .node

const ili2c = require(path.join(prebuildDir, 'ili2c.node'));

// test it
console.log(ili2c.initIsolate());
console.log(ili2c.compileModel('SO_ARP_SEin_Konfiguration_20250115.ili', 'ili2c.log'));
console.log(ili2c.tearDownIsolate());
