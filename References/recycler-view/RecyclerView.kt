RECYCLERVIEW PERFORMANCE

►Referencias
	► https://blog.usejournal.com/improve-recyclerview-performance-ede5cec6c5bf
	► https://developer.android.com/topic/performance/vitals/render#recyclerview_notifydatasetchanged

► https://blog.usejournal.com/improve-recyclerview-performance-ede5cec6c5bf

	→ Evitar aninhamento de layout dentro do item do recycler
		Don’t use ConstraintLayout in RecyclerView
	→ Se o item tem tamanho fixo: 
		recyclerView.setHasFixedSize(true)
	→ recyclerView.setItemViewCacheSize(20)

► https://developer.android.com/topic/performance/vitals/render#recyclerview_notifydatasetchanged
	
	→ Usando DiffUtil em vez de notifyDatasetChanged
		fun onNewDataArrived(news: List<News>) {
		    val oldNews = myAdapter.items
		    val result = DiffUtil.calculateDiff(MyCallback(oldNews, news))
		    myAdapter.news = news
		    result.dispatchUpdatesTo(myAdapter)
		}

	→ Nested RecyclerView
	Compartilhar RecyclerView.RecyclerViewPool
	class OuterAda

► https://android.jlelse.eu/smart-way-to-update-recyclerview-using-diffutil-345941a160e0

	→ getOldListSize():
		Return the size of the old list.
	
	→ getNewListSize():
		 Return the size of the new list.
	
	→ areItemsTheSame(int oldItemPosition, int newItemPosition)
	 It decides whether two objects are representing same items or not.
	
	→ areContentsTheSame(int oldItemPosition, int newItemPosition):
	 It decides whether two items have same data or not.
	 This method is called by DiffUtil only if areItemsTheSame() returns true.
	
	→ getChangePayload(int oldItemPosition, int newItemPosition)
	  If areItemTheSame() returns true and areContentsTheSame() returns false than DiffUtil utility calls this method to get a payload about the change.

► https://mobikul.com/how-to-optimize-recyclerview-in-android/
	
	recyclerView.setItemViewCacheSize(20)
	recyclerView.setDrawingCacheEnabled(true)
	recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)

