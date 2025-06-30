{
    "targets": [
        {
            "target_name": "ili2caddon",
            "sources": [
                "ili2c.cpp"
            ],
            "include_dirs": [
                "node_modules/node-addon-api",
                "lib/macos",
                "."
            ],
            "libraries": [
                "-L<(module_root_dir)/lib/macos",
                "-lili2c"
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
                            "-L<(module_root_dir)/lib/macos",
                            "-lili2c"
                        ],
                        "copies": [
                            {
                                "files": [ "lib/macos/libili2c.dylib" ],
                                "destination": "<(module_root_dir)/build/Release"
                            }
                        ]
                    }
                ],
                [
                    "OS=='linux'",
                    {
                        "libraries": [
                            "-L<(module_root_dir)/lib/linux",
                            "-lili2c"
                        ],
                        "copies": [
                            {
                                "files": [ "lib/linux/libili2c.so" ],
                                "destination": "<(module_root_dir)/build/Release"
                            }
                        ]
                    }
                ],
                [
                    "OS=='win'",
                    {
                        "libraries": [
                            "<(module_root_dir)\\lib\\win\\ili2c.lib"
                        ],
                        "copies": [
                            {
                                "files": [ "lib\\win\\ili2c.dll" ],
                                "destination": "<(module_root_dir)/build/Release"
                            }
                        ]
                    }
                ]
            ]
        }
    ]
}