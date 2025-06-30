# ili2c

A simple Node.js wrapper to compile INTERLIS models.

## Usage

```js
const ili2c = require('ili2c');
const result = ili2c.compileModel("test/SO_ARP_SEin_Konfiguration_20250115.ili", "test.log");
```

## Dev

```
npm install node-gyp
```

```
npm install node-addon-api
```

```
npm install --save-dev prebuild
npm install --save-dev prebuildify
```

```
npx node-gyp rebuild
```

Falls man das binding.gyp ändert: 

```
npx node-gyp clean
npx node-gyp configure
npx node-gyp rebuild
```

```
npx prebuild --target 22.0.0
npx prebuildify --target 22.0.0
```

Lokal paketieren (zum Testen, was reingepackt wird):
```
npm pack
```