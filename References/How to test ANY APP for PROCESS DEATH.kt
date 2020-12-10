How to test ANY APP for PROCESS DEATH

- cmd to AppData>Local>Android>Sdk>platform-tools
- adb shell
- ps -A | grep busao      (pesquisa pelo package, retorna app id)
- am kill busao.life.io (app não pode estar no foreground)



Se ao ligar o app de novo o aplicativo tiver resetado significa que NÃO ESTÁ SENDO FEITO O HANDLING CORRETO