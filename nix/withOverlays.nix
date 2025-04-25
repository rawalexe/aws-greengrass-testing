final: prev:
let
  inherit (final) lib src fetchgit pkg-config clang-tools ggl-clang cmake;
  inherit (lib) fileset mapAttrs mapAttrsToList removePrefix toUpper;
  inherit (builtins) fromJSON readFile;

  deps = mapAttrs (_: v: fetchgit (v // { fetchSubmodules = true; }))
    (fromJSON (readFile (src + "/fc_deps.json")));
in
{
  gglUtil = rec {
    fetchContentFlags = mapAttrsToList
      (n: v: "-DFETCHCONTENT_SOURCE_DIR_${toUpper n}=${v}")
      deps;

    buildFileset = lib.fileset.unions (map (p: src + p) [
      "/CMakeLists.txt"
      "/fc_deps.json"
      "/misc/systemd"
      "/misc/cmake_uninstall.cmake.in"
      "/version"
      "/modules"
    ]);

    checkFileset = lib.fileset.unions ([ buildFileset ] ++ (map (p: src + p) [
      "/.clang-tidy"
      "/test_modules"
    ]));

    llvmStdenv = final.overrideCC final.llvmPackages.stdenv
      (final.llvmPackages.stdenv.cc.override
        { inherit (final.llvmPackages) bintools; });

    clangChecks = {
      src = lib.fileset.toSource {
        root = src;
        fileset = checkFileset;
      };

      cmakeBuildDir = llvmStdenv.mkDerivation {
        name = "clang-cmake-build-dir";
        nativeBuildInputs = [ pkg-config clang-tools ];
        inherit (ggl-clang) buildInputs;
        buildPhase = ''
          ${cmake}/bin/cmake -B $out -S ${clangChecks.src} -D BUILD_EXAMPLES=1 \
            -D CMAKE_BUILD_TYPE=Debug ${toString fetchContentFlags}
          rm $out/CMakeFiles/CMakeConfigureLog.yaml
        '';
        dontUnpack = true;
        dontPatch = true;
        dontConfigure = true;
        dontInstall = true;
        dontFixup = true;
      };
    };
  };

  clangd-tidy = final.callPackage
    ({ python3Packages }:
      python3Packages.buildPythonPackage rec {
        pname = "clangd_tidy";
        version = "1.1.0.post1";
        format = "pyproject";
        src = final.fetchPypi {
          inherit pname version;
          hash = "sha256-wqwrdD+8kd2N0Ra82qHkA0T2LjlDdj4LbUuMkTfpBww=";
        };
        buildInputs = with python3Packages; [ setuptools-scm ];
        propagatedBuildInputs = with python3Packages; [
          attrs
          cattrs
          typing-extensions
        ];
      })
    { };
}
