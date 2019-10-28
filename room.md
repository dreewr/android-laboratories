# References 

https://medium.com/androiddevelopers/7-steps-to-room-27a5fe5f99b2
https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1
https://www.youtube.com/watch?v=sU-ot_Oz3AE
https://www.youtube.com/watch?v=_aJsh6P00c0	

# FTS: Full Text Search
	Search by text ROOM functionality
	- Codigo
		'''
			@Entity 
			data class Song(
				@PrimaryKey
				val id: Long, 
				val url: String,
				@Embedded
				val labels: SongLabels)

			data class SongLabels(
				val songName: String,
				val albumName: String,
				val artistName: String)
			
		'''



#  DatabaseViews
	Useful to simplify joins when you have N to N relationships 