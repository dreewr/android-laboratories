
► verify(objeto, times(numero de vezes).métodoSendoTestado(parâmetros)
► Um pouco diferente de quando a gente usa o .test() em outras camadas


► Captor
	captor.firstValue.onNext(t) ► chama o onNext
	        assertEquals(ResourceState.SUCCESS, projectViewModel.getProjects().value?.status)

	captor.firstValue.onError(e) ► chama o onNext
	        assertEquals(ResourceState.SUCCESS, projectViewModel.getProjects().value?.status)

	captor.firstValue.onComplete(projects) ► chama o onNext
	        assertEquals(ResourceState.SUCCESS, projectViewModel.getProjects().value?.status)

► Exemplo de teste das funções do ViewModel
	@RunWith(JUnit4::class)
	class BrowseBookmarkedProjectsViewModelTest {

	    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule() ► dá erro sem isso

	    var getBookmarkedProjects = mock<GetBookmarkedProjects>()
	    var mapper = mock<ProjectViewMapper>()
	    var projectViewModel = BrowseBookmarkedProjectsViewModel(getBookmarkedProjects, mapper)

	    ► classe usada para capturar eventos que são retornados pelas subscriptions
	    @Captor val captor = argumentCaptor<DisposableObserver<List<Project>>>()


	    @Test
	    fun fetchProjectsExecutesUseCase() {
	        projectViewModel.fetchProjects()

	        verify(getBookmarkedProjects, times(1)).execute(any(), eq(null))
	    }

	    @Test
	    fun fetchProjectsReturnsSuccess() {
	        val projects = ProjectFactory.makeProjectList(2)
	        val projectViews = ProjectFactory.makeProjectViewList(2)
	        stubProjectMapperMapToView(projectViews[0], projects[0])
	        stubProjectMapperMapToView(projectViews[1], projects[1])

	        projectViewModel.fetchProjects()

	        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
	        captor.firstValue.onNext(projects)
	        assertEquals(ResourceState.SUCCESS, projectViewModel.getProjects().value?.status)
	    }

	    @Test
	    fun fetchProjectsReturnsData() {
	        val projects = ProjectFactory.makeProjectList(2)
	        val projectViews = ProjectFactory.makeProjectViewList(2)
	        stubProjectMapperMapToView(projectViews[0], projects[0])
	        stubProjectMapperMapToView(projectViews[1], projects[1])

	        projectViewModel.fetchProjects()

	        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
	        captor.firstValue.onNext(projects)

			► valor recebido | valor esperado	        
	        assertEquals(projectViews, projectViewModel.getProjects().value?.data)
	    }

	    @Test
	    fun fetchProjectsReturnsError() {
	        projectViewModel.fetchProjects()

	        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
	        captor.firstValue.onError(RuntimeException())
	        assertEquals(ResourceState.ERROR, projectViewModel.getProjects().value?.status)
	    }

	    @Test
	    fun fetchProjectsReturnsMessageForError() {
	        val errorMessage = DataFactory.randomString()
	        projectViewModel.fetchProjects()

	        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
	        captor.firstValue.onError(RuntimeException(errorMessage))
	        assertEquals(errorMessage, projectViewModel.getProjects().value?.message)
	    }

	    private fun stubProjectMapperMapToView(projectView: ProjectView, project: Project) {
	        whenever(mapper.mapToView(project))
	            .thenReturn(projectView)
	    }
	}