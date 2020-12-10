Get Animated Android https://www.youtube.com/watch?v=N_x7SV3I3P0

► Sumário
	► General purpose property animation → ObjectAnimator
	► Complex coordinated layout | Gesture to animation? → MotionLayout
	► Interruptible | Gesture to animation? → Physics
	► Shared elements or window content? → Transitions
	► Vector graphic animation? → Animated Vector Drawable
	► WindowAnimations? → ViewAnimations
	► View properties? → ViewPropertyAnimator
	► Custom Animation? → ValueAnimator

► Animator (android.animation API 11) RECOMMENDED
	→ Animate more than view properties, can animate any arbitrary property
	
	→ Animate Vector Drawable, Animate State List Drawable 
	 ObjectAnimator is a → ValueAnimator is a → Animator ← AnimatorSet is a  

	 ViewPropertyAnimator is backed by → ValueAnimator	

	 PropertyValuesHolder is used by → ValueAnimator	

	→ ObjectAnimator
		→ General purpose
		 val view = findViewById<View>(R.id.animated)
		 ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f).apply{
		 	duration = 100
		 }.start

	→ PropertiesValuesHolder 
		→ Multiple properties on the same Object
		→ Like ViewPropertyAnimator, but can be used in coordination
	 	No exemplo ela mostra uma view que dá um pop na tela, que ela anima usando alpha, scaleX, scaleY.

	 	Animating multiple properties in the same view, with same interpolator and duration

		 val scaleX = PropertiesValuesHolder.ofFloat(View.SCALE_X, 0.5f, 1f)
		 val scaleX = PropertiesValuesHolder.ofFloat(View.SCALE_Y, 0.5f, 1f)
		 val alpha = PropertiesValuesHolder.ofFloat(View.ALPHA, 0f, 1f)

		 ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY, alpha).apply{
		 		interpolator = OvershootInterpolator()
			 }.start()


		→ Declaring PropertyValuesHolder in XML

		<objectAnimator
		android:interpolator = "@android:interpolator/overshoot">
			<propertyValuesHolder
				android:propertyName="scaleX"
				android:valueFrom="0.5"
				android:valueTo="1"
			/>
			<..../>
			<.../>
		</objectAnimator>

	→ AnimatorSet (Coordenar animações)
		→ Coreografar animações
		→ No exemplo dela, uma view dá um *pop* e depois que acaba um botão dá um fade in

		val animatorSet = AnimatorSet()
		animatorSet.play(fadeInText).after(fadeOutText).with(scaleText).before(fadeInButton)
		animatorSet.start()

	→ ViewPropertyAnimator
		→ Multiple properties, fire-n-forget
		view.animate()
			.scaleX(1f).scaleY(1f).alpha(1f)
			.setInterpolator(OvershootInterpolator)
			.start()

		→ Backed by ValueAnimator, slightly more efficient
		→ Fire-n-Forget
			→ No animation coordination, seeking/reversing/repeating

	→ ValueAnimator
		→ Custom animations (when you cant use property)
		→ Exemplo: textView tem três pontinhos que ficam aparecendo progressivamente. Os três pontinhos não são uma propriedade da textView, então esse código é pra animar o número de pontos

		ValueAnimator.ofInt(0, 4).apply{
			repeatCount = 10
			duration = 1000
			addUpdateListener{ valueAnimator -> 
				val dotsCount = valueAnimator.getAnimatedValue() as Int 
				if(dotsCount < 4 ){
					//spannable tem a ver com a string pra mostrar os dots,
					//ele deixa transparente os dots que não deveriam aparecer
					spannable.setSpan(
						transparentColorSpan, 23 + dotsCount, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
							textView.invalidate
						)

				}
			}
		}

	→ When to use

► Animated Vector Drawable (AVD)
	→ Usar em animações de ícones, fire-n-forget, performance critica
	→ Wrapper around a Vector Drawable + Animator(s)
	val avd = AppCompatResources.getDrawable(
		context, R.drawable.avd_foo) as AnimatedVectorDrawable
	imageView.drawable = avd
	avd.start() //play, pause and stop
	→ Use of avd on renderthread is better than in ui thread

	→ Para coisas mais complexas: Lottie ou Kyirie

► Physics
	→ Casos de uso em que há bastante interação, gestures
	→ Senso de continuidade na UI

	Exemplo: botão numa janela que segue o dedo, e quando ela solta o botão vai suave até um dos lados da tela 
	
	override fun onTouch(v:View, event: MotionEvent):Boolean {
		event.setLocation(event.rawX, event.rawY)
		tracker.addMovement(event)

		when(event.actionMasked){

			MotionEvent.ACTION_DOWN -> {
				firstDown.set(event.x, event.y)
				loc.set(layoutParams.x.toFloat(), layoutParams.y.toFloat())
			} 
			MotionEvent.ACTION_MOVE -> {
				val movementX = event.x - firstDown.x
				val movementY = event.y - firstDown.y
				layoutParams.x = (loc.x + movementX).toInt()
				layoutParams.y = (loc.y + movementY).toInt()

			windowManager.updateViewLayout(bubble, layoutParams)

			}

			MotionEvent.ACTION_UP -> {
				tracker.computeCurrentVelocity(1000)
				val velocityX = tracker.xVelocity
				val finalPosition = 
					if (velocityX > 0){
						(screenSize.x - v.width).toFloat
						} else 0f

				val animX = SpringAnimation(v, ParamXProp(), finalPosition)	
				animX.start()	
			}

		}
	}


	ParamXProp é uma propriedade Custom, que serve pra associar o valor da animation com a posição da bubble na tela

	inner class ParamXProp (name:String = "ParamX")
		: FloatPropertyCompat<View>(name){
			//seta o valor da animação nos layout params
			override fun setValue(view: View, animValue: Float){
				var params = (view.layoutParams as windowManager.LayoutParams)
				params.x = animValue.toInt()
				this@MainActivity.windowManager.updateViewLayout(view, params)
			}

			override fun getValue(view:View): Float = 
			(view.layoutParams as windowManager.LayoutParams).x.toFloat

		}

► Transitions
   	→ UseCases
   	 	→ Shared Element transitions (Fragments ou Activities)
   	 	→ Window content enter/exit
   	 	→ Modularize animations
   	 	→ Simple Changes using the beginDelayedTransition method

	→ Dois estados de um layout, e animar as mudanças de estado dentro desse layout
	→ é uma factory pra criar animaçlões usando esses dois inputs 

	TransitionManager.beginDelayedTransition(viewGroup)
	Você chama essa API, faz alguma alteração na hierarquia das views e o TransitionManager vê o que foi mudado, cria um animator e exibe essas animações

	→ Move código para um estilo mais declarativo

► Motion Animation
	→ Android Studio Design Tools 
	→ ConstraintLayout2: Helpers object	 CODE|API ► HELPERS
		► Encapsular um dado comportamento
		► Aplicar a um set de widgets
		► Suportado no Android Studio
		► Usar Animation API nos Helpers para promover reuso !!
		► Ex: Circular Reveal [23:57] - 
			Animator anim = ViewAnimationUtils.createCircularReveal...
			xml ~ <com.example.CircularRevealHelper>
	→ Declarative | XML 
		► Motion Editor in Android Studio
	→ Motion layout
		► Subclass of ConstraintLayout 
		► Declarative
		► LayoutTransitions
		► Properties interpolations
		► Keyframes & Modifiers
		► Touch-driven & Seekability (draws the views depending on touch)
		► Interpolate between two states
		► Keyframes: Servem pra especificar ações entre os pontos de interpolação
			Ex: para evitar que ícones se choquem durante animação, coloca um Keyframe [29:58]
			<KeyPosition
				motion:framePosition='50'
				motion:percent='1'
				motion:target="@id/imageButton"
				/>
		► KeyCycle : criar oscilações sem precisar de paths
		► KeyTimeCycle
		► In alpha: multi-State transitions [37:22]
  		► References:
			Medium: Introduction to MotionLayout part i-iv
			Github: /googlesamples/android-ConstraintLayoutExamples

► ViewAnimations (android.view.animation)
	→ Consider it deprecated 
	→ Usable only with Window Animations

	<style name="AppTheme.Screen">
		<item name = "android:windowAnimationStyle">@style/WindowAnimations</item>
	</style>

	<style name="WindowAnimations">
		<item name = "android:windowEnterAnimation">@anim/foo</item>
		<item name = "android:windowEnterAnimation">@anim/bar</item>
	</style>
	... 

	<set ...>
		<translate
			android:fromYDelta="10%"
			android:toYDelta="0"
			android:interpolator="@interpolator/decelerate_quint"
			android:duration="@android:integer/config_shortAnimTime"/>
		<alpha
			android:fromAlpha = "0.5"
			android:toAlpha = "1.0"
			android:interpolator="@interpolator/decelerate_cubic"
			android:duration="@android:integer/config_shortAnimTime"/>
	</set>