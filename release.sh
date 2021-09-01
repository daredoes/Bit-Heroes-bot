#!/bin/bash

VERSION=$1

if [[ -z $VERSION ]];
then
  echo 'Specific version'
  exit 1
fi

BINARY=./target/BitHeroes-$VERSION-jar-with-dependencies.jar
if [ ! -f $BINARY ];
then
  echo 'Binary version '$VERSION' does not exist'
  echo 'File not found: '$BINARY
  exit 1
fi

# Cleanup
rm -rf ./release/

# Remake release directory
mkdir -p ./release/

# Copy binary
cp $BINARY ./release/BitHeroes.jar

# Provide empty properties file, help players override configuration
touch ./release/user-config.properties

cat <<EOF > ./release/user-config.properties
# Screen offset
# from left
#offset.screen.x=0
# from top
#offset.screen.y=57

# Game tokens
#1.game.kong.user.id=
#1.game.kong.user.name=
#1.game.auth.token=

# Configure telegram
#telegram.token=
#telegram.channel-id=

# Tolerant
#tolerant.position=40
#tolerant.color=0

# Google Chrome path, for Windows only
#external.application.chrome.path=C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe
EOF

# Copy files for mini client
cp ./prepare-mini-chrome-client.txt ./release/

# Copy launch scripts for Linux/Mac
cp ./fishing.sh ./release/
cp ./rerun.sh ./release/
cp ./client.sh ./release/
cp ./world-boss.sh ./release/
cp ./pvp.sh ./release/
cp ./invasion.sh ./release/
cp ./trials.sh ./release/
cp ./gvg.sh ./release/
cp ./gauntlet.sh ./release/
cp ./afk.sh ./release/
cp ./setting.sh ./release/

# Copy launch scripts for Windows
cp ./fishing.bat ./release/
cp ./rerun.bat ./release/
cp ./client.bat ./release/
cp ./world-boss.bat ./release/
cp ./pvp.bat ./release/
cp ./invasion.bat ./release/
cp ./trials.bat ./release/
cp ./gvg.bat ./release/
cp ./gauntlet.bat ./release/
cp ./afk.bat ./release/
cp ./setting.bat ./release/

# Copy steam scripts
cp ./steam.*.bat ./release/

# Include README.md
echo '[![Github Release](https://img.shields.io/github/downloads/9-9-9-9/Bit-Heroes-bot/release-'$VERSION'/total?style=social)](https://github.com/9-9-9-9/Bit-Heroes-bot/releases/tag/release-'$VERSION')' > ./release/README.md 
echo >> ./release/README.md
cat ./README.release.md >> ./release/README.md

# Remove secret
rm -f ./release/bh-client/*.html

# Compress output
FILE=Bit-Heroes-bot-Release-v$VERSION.zip
rm -f $FILE

DIR=./BitHeroes-v$VERSION
rm -rf $DIR
mv ./release $DIR
zip -r $FILE $DIR

rm -f ./latest.README.md
cp $DIR/README.md ./latest.README.md

echo 'clean up'
rm -rf $DIR

echo 'Done '$FILE
