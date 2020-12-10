Kotlin Sealed Classes and Custom State Management

► Definition
	Kotlin sealed class: superpowered enum.
	represent constrained hierarchies in which
	an object can only be of one of the given types

	A class with an specific number of subclasses
	In enum, you can only have one object per type, in sealed class
	you can have several objects for the same class 

	enum class: group of constants that are related
	sealed class: group of classes that are related with added features

► Example
	sealed class Result<out T: Any>{
		data class Success<out T: Any>(val data: T): Result <T>()
		data class Error(val exception: Exception): Result<Nothing>()
		data class Loading: Result<Nothing>()
	}





Example: when user long presses, list enters a "selection mode" like whatsapp and toolbar changes

SearchViewState -> toolbar with edittext and filter
MultiSelectionState -> toolbar with thrash icon and close icon

presentation>state>resourceState>mapState


sealed class NoteListToolbarState{
	//two different states that are related 
	class MultiSelectionState:  NoteListToolbarState(){
		override fun toString(): String{
			return "MultiSelectionState"
		}
	}

	class SearchViewState: NoteListToolbarState(){
		override fun toString(): String
		{
			return "SearchViewState"
		}

	}}





//Adicionar esse ca
//Class that manages the states 
class NoteListInteractionManager @Inject constructor() {

	private val _selectedNotes = MutableLiveData<ArrayList<Note>>
	= MutableLiveData()

	private val _toolbarState = MutableLiveData<NoteListToolbarState>
	= MutableLiveData(SearchViewState())


	val selectedNotes: LiveData<ArrayList<Note>>
	get() = _selectedNotes

	val _toolbarState: LiveData<NoteListToolbarState>
	get() = _toolbarState


	fun setToolbarState(state: NoteListToolbarState){
		_toolbarState.value = state
	}

	fun getSelectedNotes(): ArrayList<Note> = _selectedNotes.value?: ArrayList()


	fun is MultiSelectionStateActive()
	: Boolean = _toolbarState.value.toString() == MultiSelectionState.toString()

	fun addOrRemoveFromSelectedList(note: Note){
		var list = selectedNotes.value
		if(list == null){

		}
	}

}


class NoteListViewModel(){
	val NoteListInteractionManager = NoteListInteractionManager()

	//UI OBSERVER THAT
	val toolbarState: LiveData<NoteListToolbarState>
	get() = NoteListInteractionManager.toolbarState

}


UI

fun subscribeObservers(){
	viewModel.toolbarState.observe(...){

		when(toolbarState){

			is MultiSelectionState -> {
				...
			}
			is SearchViewState -> {
				...
			}


		}


	}

}

no adapter (onViewHolder)