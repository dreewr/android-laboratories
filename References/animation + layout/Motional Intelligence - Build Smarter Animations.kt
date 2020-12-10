► slides nick butcher 
	http://j.mp/motional-intelligence
	https://www.youtube.com/watch?v=f3Lm8iOr4mE&t=1s

► Como lidar com o controle saindo da camada de View? (delegado à ViewModel)
	► Fluxo de dados unidirecional (MVI)
	► ViewLayer ← Model
	► Enable animations in a reactive model
	► Ex: ao pressionar botão para login mostrar loading de um jeito mais "macio"

► Princípios
	→ Reentrant: interrompível e chamável a qualquer momento
		→ Pode ser que durante a animação venha outra emissão de dados e devo ser capaz de responder à isso
		→ Continuous
			→ Sem "saltos" ou descontinuidades
	→ Smooth

► Jeitos de tratar
	→ Continuidade: Eliminando a presunção do valor inicial (usar o valor atual para iniciar)
		ObjectAnimator.ofFloat(view, View.ALPHA, /*1f,*/   0f)
	→ Reentrante: Limpar as animações que estão acontecendo ou não fazer a animação se ela não for necessária (se já está no estado, retorna e não fica reexecutando a animação)
		→ mas como saber o estado que a animação está sem uma referência?
		R: eu consigo acesso ao valor da propriedade pelo ObjectAnimator
		...
		val anim = view.animate().alpha(targetAlpha)
		if (!visible){
			anim.withEndAction{ //Only called if animation runs completely
				view.visible = View.GONE
			}
		}
	→ Suavidade: 
		► Springs como opção - https://developer.android.com/guide/topics/graphics/spring-animation)
		► Usa as Physics para manter coisas como velocidade e aceleração
		val springAnim = SpringAnimation(view, SpringAnimation.TRANSLATION_X)
		springAnim.animateToFinalPosition(0f) //Se ajusta
		► Springs não tem as paradas descoladas de pegar o valor atual :(
	→ Juntando as coisas: usando extentions
	
		//stash the current spring on the view as a tag
		fun View.spring(property: ViewProperty): SpringAnimation{
			val key = getKey(property)
			var springAnim = getTag(key) as? SpringAnimation?
			if(springAnim == null){
				springAnim = SpringAnimation(this, property)
				setTag(key, springAnim)
			}
			return springAnim
		}

		

		em uso:

		view.spring(SpringAnimation.TRANSLATION_X).animateToFinalPosition(0f)

	→ [16:22] 

► ItemAnimator Exemplo de RecyclerView 
	► Código - 
	https://photos.google.com/share/AF1QipN4pycm4lg9jEP-SMrikC1j53zBDttT_9bFVS_1nIob9zAQHtGkaYwhMoNuUbKwVg/photo/AF1QipM57WXH0GYKA7E4_DOk5D_KIdY426kt-k9nqTWx?key=VHowOEdZY1hGWXkyQ3MyeTljM1pfa1FOc284OUtB

► Animando VectorDrawables 
	► Slides: https://photos.google.com/share/AF1QipN4pycm4lg9jEP-SMrikC1j53zBDttT_9bFVS_1nIob9zAQHtGkaYwhMoNuUbKwVg/photo/AF1QipOlvRATFfALKpdMWXgi2dxRMojdO8mUPmwb1vxr?key=VHowOEdZY1hGWXkyQ3MyeTljM1pfa1FOc284OUtB
	► Aplicando estados a drawables
		► Selector normal
			<selector>
				<item
				android:state_checked="true"
				android:drawable = "@drawable/ic_star"	/>
				<item
				android:drawable = "@drawable/ic_star_border"	/>
			</selector>
		► Animated-selector [API 21 ou JETPACK 16]
		  	→ Posso prover transições quando mudando entre esses estados
				<animated-selector>
					<item
					android:id="@+id/starred"
					android:state_checked="true"
					android:drawable = "@drawable/ic_star"	/>
					<item
					android:id="@+id/unstarred"
					android:drawable = "@drawable/ic_star_border"	/>

					<transition
					android:fromId="unstarred"
					android:toId="starred"
					android:drawable="avd_star_event"/>

					<transition
					android:fromId="starred"
					android:toId="unstarred" 
					android:drawable="avd_unstar_event"/>
				</animated-selector>

			button.isChecked = isChecked   <-- usa o selector provido e anima
			→ Código: https://photos.google.com/share/AF1QipN4pycm4lg9jEP-SMrikC1j53zBDttT_9bFVS_1nIob9zAQHtGkaYwhMoNuUbKwVg/photo/AF1QipOOomNe7w_QTSCb1NAiPm5_Z1Tccqga02cHKKJ4?key=VHowOEdZY1hGWXkyQ3MyeTljM1pfa1FOc284OUtB

			→ [22:20] mais avançado, declarando estados customizados para uma view → em vez de só checked ou focused eu posso criar estados específicos para a view, como "pronto" ou "erro" e setar os drawables corretos
				Código → https://photos.google.com/share/AF1QipN4pycm4lg9jEP-SMrikC1j53zBDttT_9bFVS_1nIob9zAQHtGkaYwhMoNuUbKwVg/photo/AF1QipPKv9Mf8SmoTP6RVWv4vCAruu9hYSxrGAc_HbiY?key=VHowOEdZY1hGWXkyQ3MyeTljM1pfa1FOc284OUtB

► StateListAnimator [26:00]
	→ Criando animações para estados customizados, só que em vez de vector, views
	→ Dentro de um state-list tu passa animações
	→ Não é contínuo se animar propriedades que não são salváveis como background color 

► Custom View Animation [28:00]
	→ O approach ingênuo quando várias coisas acontecem ao mesmo tempo é animar cada animação individualmente e tentar orquestrar de algum jeito
	→ Outri approach é usar uma única propriedade de progresso e roda todas essas animações em torno dessa propriedade

		ValueAnimator.ofFloat(0f, 1f).apply{
			addUpdateListener{
				progress = it.animedValue as Float
			}
		}
		val circleRadius = lerp (strokeWidth + iconRadius, width.toFloat, progress) //lerp é uma util de animação lerp(from, to, progress)

		! Pode aliar isso ao Spring para rodar animações suaves e contínuas, ou gesture, ou motion lation