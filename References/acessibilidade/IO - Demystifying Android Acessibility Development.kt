https://www.youtube.com/watch?v=bTodlNvQGfY
g.co/androidaccessibility

APIs para tornar acessibilidade acessível para os devs

► Intro
	► 1. Deixar informações visíveis sem usuários terem que mexer em configurações 
	→ gente com pouca visão ou problemas de visão, daltonismo 
	
	► 2. Facilitar os controles para que sejam grandes e simples
	→ pessoas problemas de mobilidade manual, ou dedos grandes ou deformações 
	► Label images pra que os usuários que não conseguem ter uma boa visibilidade possam ver
	► Accessibility Services → como atingir determinados usuários
		► use the android framework to take actions on the ui
		► APIs que auxiliam na hora de apresentar a informação e permitir que os usuários tomem ação 
		► Testing pra verificar que o app tá atingindo uma grande gama de usuários

	TalkBack: Helps people who have low vision or are blind. Announces content through a synthesized voice, and performs actions on an app in response to user gestures.

	Switch Access: Helps people who have motor disabilities. Highlights interactive elements, and performs actions in response to the user pressing a button. Allows for controlling the device using only 1 or 2 buttons.

► APIs
	► APP → Comunica ao Acessibility Service o que esta visivel na tela usando APIs
		→ contentDescription in the XML
		→ Adicionando accessibility actions
			ViewCompat.addAcce...Action(view, R.string.remove /*Descrever a ação*/, (v,b) ->  //todo da função poss um lambda)
		→ This works back to API21
	► Non URL Spans [Spans are powerful markup objects that you can use to style text at a character or paragraph level.]
		► Como Talk-Back users saberiam que existe um link na tela?
			[Usable all the way to API 19]ViewCompat.enableAccessibleClickableSpanSuport(hamsterInfoView) <- pass view that contains that span
		► Tratando dialogs: para os TalkBack saberem que houve mudança no contexto
			ViewCompat.setAccessibilityPaneTitle(view, R.string.myAlertTitle)
		► Timeouts API (mostra/esconde botões eg videoplayer dependendo das necessidades do usuário)	

► Anti-Patterns
	► Uma anti-padrão que NÃO é recomendado é usar announcements, eg quando chega um email e você anuncia que um email novo chegou.
	► A forma "correta" é dar uma boa descrição da view e delegar isso pra API
		→ No AndroidX e no Material existem ferramentas acopladas que permitem
	► Você não se comunica diretamente com o usuário e sim com a API de acessibilidade 

	► Evitar inconsistências na acessibilidade. Não quebrar os paradgimas de acessibilidade em relação ao restante dos apps 

► Testing ► Botão visível? Está aparente para o usuário?
	► Automated Tests: Testing Goals
		► Tests for missing or incorrect labels
		► Small touch targets
		► Low-contrast text/images
		► Other implementation-specific issues
	Espresso:
		@Before public void setUp(){
			AccessibilityChecks.enable()
				.setRunChecksFromRootView(true) /*on the entire view hierarchy*/
				.setSuppressingResultMatcher(...)  /*supresss known accessibility issues*/
		} 

		@Test public void arbitraryTest(){
			onView(withId(R.id.my_view)).perform(click()) /*se eu interajo diretamente eu estou bypassando os checks de acessibilidade*/
		}

	Roboletric
	@Before public void setUp(){
			AccessibilityUtil.
				.setRunChecksFromRootView(true) /*on the entire view hierarchy*/
	} 

	@Test 
	@AccessibilityChecks
	public void arbitraryTest(){
			ShadowView.clickOn(myView)
		}


	► Accessibility testing tools [25:00]
		GooglePlay Pre-Launch Report ► procura por problemas de acessibilidade em vários devices antes de você lançar o seu app
		g.co/acessibilityscanner
	
	► Manual
		► Testar os apps usando TalkBack e SwitchAccess

	→ Usar uma mistura de testes automatizados e manuais

