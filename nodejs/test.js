const fs = require('fs');
const os = require('os');
const path = require('path');
const ili2c = require('./index'); // or require('ili2c') if published

try {
  const result = ili2c.compileModel("test/Test1.ili", "test.log");
  if (result) {
    console.log("✅ Model compiled successfully!");
  } else {
    console.log("❌ Model is invalid.");
  }

  const content = fs.readFileSync('test.log', 'utf-8');
  console.log(content);

} catch (e) {
  console.error("🔥 Critical failure:", e);
}

try {
  const tempDir = os.tmpdir();
  const iliFileName = "SO_ARP_SEin_Konfiguration_20250115_unpretty.ili";
  const iliFilePath = path.join(tempDir, iliFileName);
  console.log(iliFilePath);
  fs.copyFileSync("test/SO_ARP_SEin_Konfiguration_20250115_unpretty.ili", iliFilePath);

  const result = ili2c.prettyPrint(iliFilePath);
} catch (e) {
  console.error("🔥 Critical failure:", e);
}