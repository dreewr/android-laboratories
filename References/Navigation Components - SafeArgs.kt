https://codelabs.developers.google.com/codelabs/android-navigation/index.html?index=..%2F..%2Findex#7

Safe Args

1. Open the project build.gradle file and notice the safe args plugin:

build.gradle
dependencies {
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
    //...
    }
2. Open the app/build.gradle file and notice the applied plugin:

app/build.gradle
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'androidx.navigation.safeargs.kotlin'

android { 
   //...
}
3. Open mobile_navigation.xml, and notice how arguments are defined in the flow_step_one_dest destination.

mobile_navigation.xml
<fragment
    android:id="@+id/flow_step_one_dest"
    android:name="com.example.android.codelabs.navigation.FlowStepFragment"
    tools:layout="@layout/flow_step_one_fragment">
    <argument
        android:name="flowStepNumber"
        app:argType="integer"
        android:defaultValue="1"/>
    <action...>
    </action>
</fragment>
Using the <argument> tag, safeargs generates a class called FlowStepFragmentArgs.

Since the XML includes an argument called flowStepNumber, specified by android:name="flowStepNumber", the generated class FlowStepFragmentArgs will include a variable flowStepNumber with getters and setters.

4. Open FlowStepFragment.kt

5. Comment out the line of code shown below:

FlowStepFragment.kt
// Comment out this line
// val flowStepNumber = arguments?.getInt("flowStepNumber")
This old-style code is not type-safe. Its better to use safe args.

6. Update FlowStepFragment to use the code generated class FlowStepFragmentArgs. This will get the FlowStepFragment arguments in a type-safe manner


No Fragment Origem:

A class is created for each destination where an action originates. 
The name of this class is the name of the originating destination, appended with the word "Directions".
For example, if the originating destination is a fragment that is named SpecifyAmountFragment, 
the generated class would be called SpecifyAmountFragmentDirections.

This class has a method for each action defined in the originating destination.

For each action used to pass the argument, an inner class is created whose name is based on the action. For example, if the action is called confirmationAction, the class is named ConfirmationAction. If your action contains arguments without a defaultValue, then you use the associated action class to set the value of the arguments.

 override fun onClick(v: View) {
       val amountTv: EditText = view!!.findViewById(R.id.editTextAmount)
       val amount = amountTv.text.toString().toInt()
       val action = SpecifyAmountFragmentDirections.confirmationAction(amount)
       v.findNavController().navigate(action)
    }

FlowStepFragment

val safeArgs: FlowStepFragmentArgs by navArgs()
val flowStepNumber = safeArgs.flowStepNumber >>>> somente depois do on create!!!

Safe Args Direction classes
You can also use safe args to navigate in a type safe way, with or without adding arguments. You do this using the generated Directions classes.

Directions classes are generated for every distinct destination with actions. The Directions class includes methods for every action a destination has.

For example, the navigate_action_button click listener in HomeFragment.kt could be changed to:

HomeFragment.kt
// Note the usage of curly braces since we are defining the click listener lambda
view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener {
    val flowStepNumberArg = 1
    val action = HomeFragmentDirections.nextAction(flowStepNumberArg)
    findNavController().navigate(action)
}
Note that in your navigation graph XML you can provide a defaultValue for each argument. If you do not then you must pass the argument into the action, as shown:

HomeFragmentDirections.nextAction(flowStepNumberArg)