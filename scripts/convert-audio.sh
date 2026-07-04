#!/usr/bin/env sh
set -eu

cd "$(dirname "$0")/.."

if ! command -v ffmpeg >/dev/null 2>&1; then
  echo "ffmpeg is required. Install it, then run scripts/convert-audio.sh again." >&2
  exit 1
fi

sound_dir="common/src/main/resources/assets/bigdogbark/sounds"
mkdir -p "$sound_dir"

ffmpeg -y -i bigdog.mp3 -ac 1 -ar 44100 -c:a libvorbis -q:a 5 "$sound_dir/bigdog.ogg"
ffmpeg -y -i bark.mp3 -ac 1 -ar 44100 -c:a libvorbis -q:a 5 "$sound_dir/bark.ogg"

echo "Converted audio into $sound_dir"
