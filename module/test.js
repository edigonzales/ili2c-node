const ili2c = require('./index'); // or require('ili2c') if published

try {
  const result = ili2c.compileModel("test/Test1.ili", "test.log");
  if (result) {
    console.log("✅ Model compiled successfully!");
  } else {
    console.log("❌ Model is invalid.");
  }
} catch (e) {
  console.error("🔥 Critical failure:", e);
}