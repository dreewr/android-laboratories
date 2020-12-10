Services Android: Trabalho pesado

Intro
- Tarefas que não possuem interface de interação com o usuário
- Deve ser adicionado no manifest
- Tipos: <Foreground>, <Background>, <Bound>

<Foreground><FOCAR>
	- Ex: whatsapp, player de música enquanto está executando

<Background>
	- Sincronizações (WorkManager)
	- WorkManager (posso agendar tarefas, ex: pegar tweets novos a cada minuto)

<Bound>
	- Vinculada a uma interface

♠ Estrutura

	→ onStartCommand() -> sistema executa esse método após chamada de startService().
		→ Ao implementar esse método, é dever do seu app gerenciar quando o serviço deve ser executado 
	→ onBind() → Método chamado quando um app faz vinculaçõa à um Service. Se usado dessa maneira, IRA TER QUE FORNECER UMA INTERFACE para o cliente usar como comunicação com o Service através de um IBinder. SEMPRE TEM QUE SER IMPLEMENTADO, mas pode ser retornado null caso não se deseje usar dessa maneira
		→ RARO, usado para comunicação entre processos, canal de comunicação entre apps 
	→ onCreate() → invocado APENAS UMA VEZ NA CRIAÇÃO DE UM SERVICE, antes de onStartCommand e onBind. Se já estiver rodando, não chama novamente
	→ onDestroy() 


♠ Ciclo de vida → por default roda na MainThread [rec mudar]

♠ Limitações de uso em background:
	→ Background Service é terminado após o app entrar em idle
		→ Similar à chamada Service.stopSelf)()
	→ Lista de autorizados: 
		→ recebendo broadcast MMS/SMS
		→ executando pendingIntent de uma notificação
		→ iniciando um vpnService antes da VPN ir para Foreground

	→ CONSIDERE USAR UM FOREGROUND SERVICE SE PRECISA EXEC UMA TAREFA CONTINUAMENTE

♠ IntentService: IDEAL PARA TRATAR UMA REQUISIÇÃO POR VEZ
	→ Cria uma thread nova automaticamente
	→ Uma requisição por vez (fila onHandleIntent())
	→ Provê implementação onBind() que retorna null
	→ Provê implementação padrão de onStartCommand() que envia intent para fila de intents, que é consumida por onHandleIntent()
	