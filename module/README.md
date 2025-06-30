# ili2c

A simple Node.js wrapper to compile INTERLIS models.

## Usage

```js
const { compileIliModel } = require('ili2c');

console.log(compileIliModel('MODEL Test; END Test.'));
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