:: Do chaining tasks PVP, WB, GVG/Invasion/Expedition, TG, Raid and then exit after completed them all
:: https://github.com/9-9-9-9/Bit-Heroes-bot/wiki/Function-%22afk%22
:: Consider adding `--profile=YourProfileName` so you no longer needed to select profile manually
:: You can remove flag `--ear` to keep game online all day (but still gone if you got Disconnected)
:: With steam version of BH, refer to AFK.steam.bat file
echo 'e' | call web.bot.bat character 3
echo 'e' | call web.bot.bat afk a --ear --profile=elon
echo 'e' | call web.bot.bat character 2
echo 'e' | call web.bot.bat afk a --ear --profile=devito
echo 'e' | call web.bot.bat character 1
echo 'e' | call web.bot.bat afk a --ear --profile=dare
call auto.bat
:: Move this file to bot's folder in order to use (this file was distributed within `sample-script` folder so it unable to run)
