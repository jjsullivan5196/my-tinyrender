with import <nixpkgs> {};

stdenv.mkDerivation {
  name = "stegocljs";
  src = ./.;
  buildInputs = [
    adoptopenjdk-openj9-bin-11
    boot
    nodejs
    yarn
  ];
}
