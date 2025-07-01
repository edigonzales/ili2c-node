{
    "targets": [
        {
            "target_name": "ili2caddon",
            "sources": [
                "ili2c.cpp"
            ],
            "include_dirs": [
                "node_modules/node-addon-api",
                "lib_ext",
                "."
            ],
            "cflags_cc": [
                "-std=c++17",
                "-fexceptions"
            ],
            "defines": [
                "NODE_ADDON_API_DISABLE_CPP_EXCEPTIONS"
            ],
            "conditions": [
                [
                    "OS=='mac'",
                    {
                        "libraries": [
                            "-L<(module_root_dir)/lib_ext",
                            "-lili2c"
                        ],
                        "copies": [
                            {
                                "files": [ "lib_ext/libili2c.dylib" ],
                                "destination": "<(module_root_dir)/build/Release"
                            }
                        ]
                    }
                ],
                [
                    "OS=='linux'",
                    {
                        "libraries": [
                            "-L<(module_root_dir)/lib_ext",
                            "-lili2c"
                        ],
                        "copies": [
                            {
                                "files": [ "lib_ext/libili2c.so" ],
                                "destination": "<(module_root_dir)/build/Release"
                            }
                        ]
                    }
                ],
                [
                    "OS=='win'",
                    {
                        "libraries": [
                            "libili2c.lib"
                        ],
                        "copies": [
                            {
                                "files": [ 
                                    "lib_ext\\libili2c.dll", 
                                    "lib_ext\\libili2c.lib"
                                ],
                                "destination": "<(module_root_dir)/build/Release"
                            }
                        ]
                    }
                ]
            ]
        }
    ]
}