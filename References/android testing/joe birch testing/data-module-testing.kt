Android Testing ► Data [Biblioteca Kotlin pura | Mockito] 

► no domain ele usa @Mock e o setup é diferente do que no
► mock<T>()  → Lembrar de colocar open em T pra não dar erro de classe final

► Componentes principais dos testes
	► Objetos Data Factory
	► RemoteDataStoreTest, CacheDataStoreTest, DataStoreFactoryTest
	► MapperTest

► DataStoreFactoryTest: testar se as classes factory retornam de acordo
	→ Bem diferente as classes do vídeo e do repositório mais atual dele, analisar
	→ Assinatura da classe original: 
		open class ProjectsDataStoreFactory @Inject constructor(
   			private val projectsCacheDataStore: ProjectsCacheDataStore,
    		private val projectsRemoteDataStore: ProjectsRemoteDataStore)

	class ProjectsDataStoreFactoryTest {

		→ mock<T>() em vez de @Mock como na Domain → lá são interfaces sendo mockadas, aqui são classes
	    private val cacheStore = mock<ProjectsCacheDataStore>()   ← As DataStore tem que ser OPEN pro mockito poder mocar
	    private val remoteStore = mock<ProjectsRemoteDataStore>()
	    private val factory = ProjectsDataStoreFactory(cacheStore, remoteStore)

	    → A lógica aqui é avaliar quando cada tipo de store é retornada, de acordo com os parâmetros
	   

	    @Test
	    fun getDataStoreReturnsRemoteStoreWhenCacheExpired() {
	        ► se o cache tiver expirado, retornar remote
	        assertEquals(remoteStore, factory.getDataStore(true, true))
	    }

	    @Test
	    fun getDataStoreReturnsRemoteStoreWhenProjectsNotCached() {
	    	► se não tiver cache, retornar remote
	        assertEquals(remoteStore, factory.getDataStore(false, false))
	    }

	    @Test
	    fun getDataStoreReturnsCacheStore() {
	    	► is cached e não expired: retorna cache
	        assertEquals(cacheStore, factory.getDataStore(true, false))
	    }

	    @Test
	    fun getCacheDataStoreReturnsCacheStore() {
	        assertEquals(cacheStore, factory.getCacheDataStore())
	    }
	}

► CacheDataStoreTest: testar as funções da DataStore de Cache
	► Assinatura
	open class ProjectsCacheDataStore @Inject constructor(
    private val projectsCache: ProjectsCache): ProjectsDataStore
	
	► Classe de teste
		class ProjectsCacheDataStoreTest {

		    private val cache = mock<ProjectsCache>()
		    private val store = ProjectsCacheDataStore(cache)

		    @Test
		    fun getProjectsCompletes() {
		        stubProjectsCacheGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
		        val testObserver = store.getProjects().test()
		        testObserver.assertComplete()
		    }

		    @Test
		    fun getProjectsReturnsData() {
		        val data = listOf(ProjectFactory.makeProjectEntity())
		        stubProjectsCacheGetProjects(Observable.just(data))
		        val testObserver = store.getProjects().test()
		        testObserver.assertValue(data)
		    }

		    @Test
		    fun getProjectsCallsCacheSource() {
		        stubProjectsCacheGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
		        store.getProjects().test()
		        verify(cache).getProjects()
		    }

		    @Test
		    fun saveProjectsCompletes() {
		        stubProjectsCacheSaveProjects(Completable.complete())
		        val testObserver = store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
		        testObserver.assertComplete()
		    }

		    @Test
		    fun saveProjectsCallsCacheStore() {
		        val data = listOf(ProjectFactory.makeProjectEntity())
		        stubProjectsCacheSaveProjects(Completable.complete())
		        stubProjectsCacheSetLastCacheTime(Completable.complete())
		        store.saveProjects(data).test()
		        verify(cache).saveProjects(data)
		    }

		    @Test
		    fun clearProjectsCompletes() {
		        stubProjectsClearProjects(Completable.complete())
		        val testObserver = store.clearProjects().test()
		        testObserver.assertComplete()
		    }

		    @Test
		    fun clearProjectsCallCacheStore() {
		        stubProjectsClearProjects(Completable.complete())
		        store.clearProjects().test()
		        verify(cache).clearProjects()
		    }

		    @Test
		    fun getBookmarkedProjectsCompletes() {
		        stubProjectsCacheGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
		        val testObserver = store.getBookmarkedProjects().test()
		        testObserver.assertComplete()
		    }

		    @Test
		    fun getBookmarkedProjectsCallsCacheStore() {
		        stubProjectsCacheGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
		        store.getBookmarkedProjects().test()
		        verify(cache).getBookmarkedProjects()
		    }

		    @Test
		    fun getBookmarkedProjectsReturnsData() {
		        val data = listOf(ProjectFactory.makeProjectEntity())
		        stubProjectsCacheGetBookmarkedProjects(Observable.just(data))
		        val testObserver = store.getBookmarkedProjects().test()
		        testObserver.assertValue(data)
		    }

		    @Test
		    fun setProjectAsBookmarkedCompletes() {
		        stubProjectsCacheSetProjectAsBookmarked(Completable.complete())
		        val testObserver = store.setProjectAsBookmarked(DataFactory.randomString()).test()
		        testObserver.assertComplete()
		    }

		    @Test
		    fun setProjectAsBookmarkedCallsCacheStore() {
		        val projectId = DataFactory.randomString()
		        stubProjectsCacheSetProjectAsBookmarked(Completable.complete())
		        store.setProjectAsBookmarked(projectId).test()
		        verify(cache).setProjectAsBookmarked(projectId)
		    }

		    @Test
		    fun setProjectAsNotBookmarkedCompletes() {
		        stubProjectsCacheSetProjectAsNotBookmarked(Completable.complete())
		        val testObserver = store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
		        testObserver.assertComplete()
		    }

		    @Test
		    fun setProjectAsNotBookmarkedCallsCacheStore() {
		        val projectId = DataFactory.randomString()
		        stubProjectsCacheSetProjectAsNotBookmarked(Completable.complete())
		        store.setProjectAsNotBookmarked(projectId).test()
		        verify(cache).setProjectAsNotBookmarked(projectId)
		    }

		    private fun stubProjectsCacheGetProjects(observable: Observable<List<ProjectEntity>>) {
		        whenever(cache.getProjects())
		            .thenReturn(observable)
		    }

		    private fun stubProjectsCacheSaveProjects(completable: Completable) {
		        stubProjectsCacheSetLastCacheTime(completable)
		        whenever(cache.saveProjects(any()))
		            .thenReturn(completable)
		    }

		    private fun stubProjectsCacheSetLastCacheTime(completable: Completable) {
		        whenever(cache.setLastCacheTime(any()))
		            .thenReturn(completable)
		    }

		    private fun stubProjectsClearProjects(completable: Completable) {
		        whenever(cache.clearProjects())
		            .thenReturn(completable)
		    }

		    private fun stubProjectsCacheGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
		        whenever(cache.getBookmarkedProjects())
		            .thenReturn(observable)
		    }

		    private fun stubProjectsCacheSetProjectAsBookmarked(completable: Completable) {
		        whenever(cache.setProjectAsBookmarked(any()))
		            .thenReturn(completable)
		    }

		    private fun stubProjectsCacheSetProjectAsNotBookmarked(completable: Completable) {
		        whenever(cache.setProjectAsNotBookmarked(any()))
		            .thenReturn(completable)
		    }
		}

 
	► MapperTest class
	@RunWith(JUnit4::class)
	class ProjectMapperTest{

		private val mapper = ProjectMapper

		→ quero testar duas funções: mapToEntity e mapFromEntity

	@Test
    fun mapToEntityMapsData() {
        val model = ProjectFactory.makeProject()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model) ◄ função separada para fazer o assert dos dados 
    }

    @Test
    fun mapFromEntityMapsData() {
        val entity = ProjectFactory.makeProjectEntity()
        val model = mapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }

    private fun assertEqualData(entity: ProjectEntity,
                                model: Project) {
        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.fullName, model.fullName)
        assertEquals(entity.starCount, model.starCount)
        assertEquals(entity.dateCreated, model.dateCreated)
        assertEquals(entity.ownerName, model.ownerName)
        assertEquals(entity.ownerAvatar, model.ownerAvatar)
        assertEquals(entity.isBookmarked, model.isBookmarked)
    }
	}

► Testando a DataRepository

	→ Assinatura
	class ProjectsDataRepository @Inject constructor(
        private val mapper: ProjectMapper,
        private val cache: ProjectsCache,
        private val factory: ProjectsDataStoreFactory)
    : ProjectsRepository

	→ Classe de teste
	► testar as funções de ProjectsDataRepository, fazendo o stub das classes do construtor
	@RunWith(JUnit4::class)
	class ProjectsDataRepositoryTest {

	    private val mapper = mock<ProjectMapper>()
	    private val factory = mock<ProjectsDataStoreFactory>()
	    private val store = mock<ProjectsDataStore>()
	    private val cache = mock<ProjectsCache>()

	    /*Classe sendo testada*/
	    private val repository = ProjectsDataRepository(mapper, cache, factory)

	    @Before > todos os stubs vão rodar antes dos testes pra não repetir código
	    fun setup() {
	        stubFactoryGetDataStore() ►stub da DataStore instance
	        stubFactoryGetCacheDataStore()  ►stub da DataStore instance
	     
	        ► como a implementação de getProjects na classe depende dessas funções, eu preciso fazer esses stubs se não dá NullPointer na hora do teste
	        stubIsCacheExpired(Flowable.just(false)) 
	        stubAreProjectsCached(Single.just(false))

	        stubSaveProjects(Completable.complete())
	    }

	    ► any no stub pq tanto faz, só quer ver se retorna 
	    @Test
	    fun getProjectsCompletes() {
	    	► stubs chamados "whenever" getProjects é chamado
 	        stubGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
	        stubMapper(ProjectFactory.makeProject(), any())

	        val testObserver = repository.getProjects().test()
	        testObserver.assertComplete() ► testObserver permite testar diferentes situações
	    }

	    @Test
	    fun getProjectsReturnsData() {
	        val projectEntity = ProjectFactory.makeProjectEntity()
	        val project = ProjectFactory.makeProject()
	        stubGetProjects(Flowable.just(listOf(projectEntity)))
	        stubMapper(project, projectEntity)

	        val testObserver = repository.getProjects().test()
	        testObserver.assertValue(listOf(project))
	    }

	    @Test
	    fun getBookmarkedProjectsCompletes() {
	        stubGetBookmarkedProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
	        stubMapper(ProjectFactory.makeProject(), any())

	        val testObserver = repository.getBookmarkedProjects().test()
	        testObserver.assertComplete()
	    }

	    @Test
	    fun getBookmarkedProjectsReturnsData() {
	        val projectEntity = ProjectFactory.makeProjectEntity()
	        val project = ProjectFactory.makeProject()
	        stubGetBookmarkedProjects(Flowable.just(listOf(projectEntity)))
	        stubMapper(project, projectEntity)

	        val testObserver = repository.getBookmarkedProjects().test()
	        testObserver.assertValue(listOf(project))
	    }

	    @Test
	    fun bookmarkProjectCompletes() {
	        stubBookmarkProject(Completable.complete())

	        val testObserver = repository.bookmarkProject(DataFactory.randomString()).test()
	        testObserver.assertComplete()
	    }

	    @Test
	    fun unbookmarkProjectCompletes() {
	        stubUnBookmarkProject(Completable.complete())

	        val testObserver = repository.unbookmarkProject(DataFactory.randomString()).test()
	        testObserver.assertComplete()
	    }

	    private fun stubBookmarkProject(completable: Completable) {
	        whenever(cache.setProjectAsBookmarked(any()))
	                .thenReturn(completable)
	    }

	    private fun stubUnBookmarkProject(completable: Completable) {
	        whenever(cache.setProjectAsNotBookmarked(any()))
	                .thenReturn(completable)
	    }

	    private fun stubIsCacheExpired(single: Flowable<Boolean>) {
	        whenever(cache.isProjectsCacheExpired())
	                .thenReturn(single)
	    }

	    private fun stubAreProjectsCached(single: Single<Boolean>) {
	        whenever(cache.areProjectsCached())
	                .thenReturn(single)
	    }

	    ► mock a resposta do mapper
	    private fun stubMapper(model: Project, entity: ProjectEntity) {
	        whenever(mapper.mapFromEntity(entity))
	                .thenReturn(model)
	    }

	    private fun stubGetProjects(observable: Flowable<List<ProjectEntity>>) {
	        whenever(store.getProjects())
	                .thenReturn(observable)
	    }

	    private fun stubGetBookmarkedProjects(observable: Flowable<List<ProjectEntity>>) {
	        whenever(store.getBookmarkedProjects())
	                .thenReturn(observable)
	    }

	    private fun stubFactoryGetDataStore() {
	        whenever(factory.getDataStore(any(), any()))
	                .thenReturn(store)
	    }

	    private fun stubFactoryGetCacheDataStore() {
	        whenever(factory.getCacheDataStore())
	                .thenReturn(store)
	    }

	    private fun stubSaveProjects(completable: Completable) {
	        whenever(store.saveProjects(any()))
	                .thenReturn(completable)
	    }

	}