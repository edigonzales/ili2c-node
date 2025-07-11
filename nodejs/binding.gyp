{
  "targets": [
    {
      "target_name": "ili2caddon",
      "sources": [
        "ili2c.cpp"
      ],
      "include_dirs": [
        "node_modules/node-addon-api",
        "lib_ext/darwin-arm64",
        "lib_ext/darwin-x64",
        "lib_ext/linux-x64",
        "lib_ext/win32-x64",
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
              "-L<(module_root_dir)/lib_ext/darwin-arm64",
              "-lili2c"
            ],
            "copies": [
              {
                "files": [
                  "lib_ext/darwin-arm64/libili2c.dylib"
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
              "-L<(module_root_dir)/lib_ext/darwin-x64",
              "-lili2c"
            ],
            "copies": [
              {
                "files": [
                  "lib_ext/darwin-x64/libili2c.dylib"
                ],
                "destination": "<(module_root_dir)/prebuilds/darwin-x64/"
              }
            ]
          }
        ],
        [
          "OS=='linux'",
          {
            "link_settings": {
              "ldflags": [
                "-Wl,-rpath,'$$ORIGIN/..'"
              ]
            },
            "libraries": [
              "-L<(module_root_dir)/lib_ext/linux-x64",
              "-lili2c"
            ],
            "copies": [
              {
                "files": [
                  "lib_ext/linux-x64/libili2c.so"
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
                  "lib_ext\\win32-x64\\libili2c.lib"
                ],
                "destination": "<(module_root_dir)"
              },
              {
                "files": [
                  "lib_ext\\win32-x64\\libili2c.dll"
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