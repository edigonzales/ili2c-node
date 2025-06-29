# ili2c

A simple Node.js wrapper to compile INTERLIS models.

## Usage

```js
const { compileIliModel } = require('ili2c');

console.log(compileIliModel('MODEL Test; END Test.'));
