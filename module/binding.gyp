{
  "targets": [
    {
      "target_name": "ili2caddon",
      "sources": [
        "ili2c.cpp"
      ],
      "include_dirs": [
        "node_modules/node-addon-api",
        "lib_ext/mac",
        "lib_ext/linux",
        "lib_ext/win",
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
          "OS=='mac' and target_arch=='arm64'",
          {
            "libraries": [
              "-L<(module_root_dir)/lib_ext/mac",
              "-lili2c"
            ],
            "copies": [
              {
                "files": [
                  "lib_ext/mac/libili2c.dylib"
                ],
                "destination": "<(module_root_dir)/prebuilds/darwin-arm64/"
              }
            ]
          }
        ],
        [
          "OS=='mac' and target_arch=='x64'",
          {
            "libraries": [
              "-L<(module_root_dir)/lib_ext/mac",
              "-lili2c"
            ],
            "copies": [
              {
                "files": [
                  "lib_ext/mac/libili2c.dylib"
                ],
                "destination": "<(module_root_dir)/prebuilds/darwin-x64/"
              }
            ]
          }
        ],
        [
          "OS=='linux'",
          {
            "libraries": [
              "-L<(module_root_dir)/lib_ext/linux",
              "-lili2c"
            ],
            "copies": [
              {
                "files": [
                  "lib_ext/linux/libili2c.so"
                ],
                "destination": "<(module_root_dir)/prebuilds/linux-x64/"
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
                  "lib_ext\\win\\libili2c.lib"
                ],
                "destination": "<(module_root_dir)"
              },
              {
                "files": [
                  "lib_ext\\win\\libili2c.dll"
                ],
                "destination": "<(module_root_dir)/prebuilds/win32-x64"
              }
            ],
            "msvs_settings": {
              "VCLinkerTool": {
                "AdditionalLibraryDirectories": [
                  "<(module_root_dir)"
                ]
              }
            }
          }
        ]
      ]
    }
  ]
}