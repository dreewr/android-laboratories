► Como usa partes da library do Android tem que rodar com @RunWith(RobolectricTestRunner::class)


► Teste do ConfigurationDAO 

	@RunWith(RobolectricTestRunner::class)
	class CachedProjectsDaoTest {

	    @Rule
	    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

	    private val database = Room.inMemoryDatabaseBuilder(
	        RuntimeEnvironment.application.applicationContext,
	        ProjectsDatabase::class.java)
	        .allowMainThreadQueries()
	        .build()

	    @After  ► Depois dos testes, limpa o database. Precisa nesse caso pra não interferir
		► no próximo teste, mas se não não precisa colocar 
 	    fun closeDb() {
	        database.close()
	    }

	    @Test
	    fun getProjectsReturnsData() {
	        val project = ProjectDataFactory.makeCachedProject()
	        database.cachedProjectsDao().insertProjects(listOf(project))

	        val testObserver = database.cachedProjectsDao().getProjects().test()
	        testObserver.assertValue(listOf(project))
	    }

	    @Test
	    fun deleteProjectsClearsData() {
	        val project = ProjectDataFactory.makeCachedProject()
	        database.cachedProjectsDao().insertProjects(listOf(project))
	        database.cachedProjectsDao().deleteProjects()

	        val testObserver = database.cachedProjectsDao().getProjects().test()
	        testObserver.assertValue(emptyList())
	    }

	    @Test
	    fun getBookmarkedProjectsReturnsData() {
	        val project = ProjectDataFactory.makeCachedProject()
	        val bookmarkedProject = ProjectDataFactory.makeBookmarkedCachedProject()
	        database.cachedProjectsDao().insertProjects(listOf(project, bookmarkedProject))

	        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
	        testObserver.assertValue(listOf(bookmarkedProject))
	    }

	    @Test
	    fun setProjectAsBookmarkedSavesData() {
	        val project = ProjectDataFactory.makeCachedProject()
	        database.cachedProjectsDao().insertProjects(listOf(project))
	        database.cachedProjectsDao().setBookmarkStatus(true, project.id)
	        project.isBookmarked = true

	        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
	        testObserver.assertValue(listOf(project))
	    }

	    @Test
	    fun setProjectAsNotBookmarkedSavesData() {
	        val project = ProjectDataFactory.makeBookmarkedCachedProject()
	        database.cachedProjectsDao().insertProjects(listOf(project))
	        database.cachedProjectsDao().setBookmarkStatus(false, project.id)
	        project.isBookmarked = false

	        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
	        testObserver.assertValue(emptyList())
	    }
	}


► CacheImplementationTest
	
	→ Annotation do RobolectricTestRunner
	@RunWith(RobolectricTestRunner::class)
	class ProjectsCacheImplTest {
		► Assegura que vai rodar sincronamente os dados dos flowables| @get: apply @Rule annotation to property getter
	    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

	    ► Cria instância do Database
	    private val database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application.applicationContext,
	        ProjectsDatabase::class.java)
	        .allowMainThreadQueries()
	        .build()

	    ► Usa uma instância em vez de um mock
	    private val entityMapper = CachedProjectMapper()
	    ► Classe sendo testada
 	    private val cache = ProjectsCacheImpl(database, entityMapper)


	    @Test
	    fun clearTablesCompletes() {
	        val testObserver = cache.clearProjects().test()
	        testObserver.assertComplete()
	    }


	    @Test
	    fun saveProjectsCompletes() {
	        val projects = listOf(ProjectDataFactory.makeProjectEntity())

	        val testObserver = cache.saveProjects(projects).test()
	        testObserver.assertComplete()
	    }


	    ► Antes de testar retorno, tenho que salvar
	    @Test
	    fun getProjectsReturnsData() {
	        val projects = listOf(ProjectDataFactory.makeProjectEntity())
	        cache.saveProjects(projects).test()

	        val testObserver = cache.getProjects().test()
	        testObserver.assertValue(projects)
	    }

		...
	}