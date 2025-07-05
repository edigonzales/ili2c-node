#include <napi.h>
#include "graal_isolate.h"
#include "libili2c.h"

// global isolate/thread
graal_isolate_t* isolate = nullptr;
graal_isolatethread_t* thread = nullptr;

// initialize isolate
Napi::Value InitIsolate(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();

    if (isolate != nullptr) {
        return Napi::String::New(env, "Isolate already initialized");
    }

    int status = graal_create_isolate(nullptr, &isolate, &thread);
    if (status != 0) {
        Napi::Error::New(env, "Could not create Graal isolate").ThrowAsJavaScriptException();
        return env.Null();
    }

    return Napi::String::New(env, "Isolate initialized");
}

// compile model
Napi::Value CompileModel(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();

    if (thread == nullptr) {
        Napi::Error::New(env, "Isolate not initialized. Call initIsolate first.").ThrowAsJavaScriptException();
        return env.Null();
    }

    if (info.Length() < 2 || !info[0].IsString() || !info[1].IsString()) {
        Napi::TypeError::New(env, "Expected (string iliFile, string logFile)").ThrowAsJavaScriptException();
        return env.Null();
    }

    std::string iliFile = info[0].As<Napi::String>();
    std::string logFile = info[1].As<Napi::String>();

    int res = compileModel(thread, const_cast<char*>(iliFile.c_str()), const_cast<char*>(logFile.c_str()));

    if (res < 0) {
        Napi::Error::New(env, "compileModel critical failure with code: " + std::to_string(res)).ThrowAsJavaScriptException();
        return env.Null();
    }

    // 0 means success, 1 means model compile failure
    return Napi::Boolean::New(env, res == 0);
}

// pretty print
Napi::Value PrettyPrint(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();

    if (thread == nullptr) {
        Napi::Error::New(env, "Isolate not initialized. Call initIsolate first.").ThrowAsJavaScriptException();
        return env.Null();
    }

    if (info.Length() < 1 || !info[0].IsString()) {
        Napi::TypeError::New(env, "Expected (string iliFile)").ThrowAsJavaScriptException();
        return env.Null();
    }

    std::string iliFile = info[0].As<Napi::String>();

    int res = prettyPrint(thread, const_cast<char*>(iliFile.c_str()));

    if (res < 0) {
        Napi::Error::New(env, "prettyPrint critical failure with code: " + std::to_string(res)).ThrowAsJavaScriptException();
        return env.Null();
    }

    // 0 means success, 1 means model compile failure
    return Napi::Boolean::New(env, res == 0);
}

// teardown
Napi::Value TearDownIsolate(const Napi::CallbackInfo& info) {
    Napi::Env env = info.Env();

    if (thread != nullptr) {
        graal_tear_down_isolate(thread);
        isolate = nullptr;
        thread = nullptr;
    }

    return Napi::String::New(env, "Isolate torn down");
}

// module exports
Napi::Object InitAll(Napi::Env env, Napi::Object exports) {
    exports.Set("initIsolate", Napi::Function::New(env, InitIsolate));
    exports.Set("compileModel", Napi::Function::New(env, CompileModel));
    exports.Set("prettyPrint", Napi::Function::New(env, PrettyPrint));
    exports.Set("tearDownIsolate", Napi::Function::New(env, TearDownIsolate));
    return exports;
}

NODE_API_MODULE(ili2caddon, InitAll)
