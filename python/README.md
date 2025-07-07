# ili2c Python

## Develop

### Requirements
On Ubuntu 22.04:

```
sudo apt update
sudo apt install python3-pip
sudo apt install python3.10-venv
sudo apt-get install unzip zip
sudo apt-get install build-essential libz-dev zlib1g-dev
```

The latter two are needed for SDKMan and GraalVM Native Image.

### Python setup

```
python3 -m venv .venv
source .venv/bin/activate
python3 -m pip install --upgrade setuptools wheel install importlib-resources pytest
```

**TODO:** Warum importlib-resources? Ist seit Python 3.8 im Kern.

### Building

```
python3 setup.py sdist bdist_wheel
python3 setup.py sdist bdist_wheel --plat-name=manylinux2014_aarch64 
python3 setup.py sdist bdist_wheel --plat-name=manylinux2014_x86_64 
```

### Install locally

```
pip install -e .
pip install -e .[test]
```

### Running tests

```
pytest ilivalidator
```
