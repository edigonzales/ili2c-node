from ctypes import *
from importlib_resources import files
import platform

if platform.uname()[0] == "Windows":
    lib_name = "libilivalidator.dll"
elif platform.uname()[0] == "Linux":
    lib_name = "libili2c.so"
else:
    lib_name = "libili2c.dylib"

class Ili2c:
    @staticmethod
    def create_ilismetas16(iliFile: str, xtfFile: str) -> bool:
        lib_path = files('ili2c.lib_ext').joinpath(lib_name)
        # str() seems to be necessary on windows: https://github.com/TimDettmers/bitsandbytes/issues/30
        dll = CDLL(str(lib_path))
        isolate = c_void_p()
        isolatethread = c_void_p()
        dll.graal_create_isolate(None, byref(isolate), byref(isolatethread))

        try:
            result = dll.createIlisMeta16(isolatethread, c_char_p(bytes(iliFile, "utf8")), c_char_p(bytes(xtfFile, "utf8")))
            return result == 0
        finally:
            dll.graal_tear_down_isolate(isolatethread)