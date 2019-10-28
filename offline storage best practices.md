# References 
https://github.com/forbesmyester/SyncIt
https://softwareengineering.stackexchange.com/questions/310924/how-to-sync-the-data-from-the-same-user-across-multiple-devices

# Synchronization Best Practices 
	- Server is the one central version of truth in the system
	- Clients must be able to download information from the server. This server information can then have local patches applied to it.
	- Local changes must be kept separate from data that is on the Server, this enables easy identification of what needs to be uploaded to the server and will allow rollback and merges of those local changes when errors occur.
	- Users will always have the expectation that their changes will be successfully applied to the server, so to the user their locally modified data is the data. The API must support reading the result of taking the known-to-be-on-the-server data and applying local changes to it.
	- We must be able to get the local changes for the purpose of sending them to send to the server.
	- If patches are rejected by the server, the client should be able to download the new patches from the server and merge them with the local patches, at which point it can then upload those merged patches to the server.

## How do I detect data out of sync?
	- Objects should have versioning built-in. Each time an object is saved to permanent storage, you increment the version. If an app goes to sync its changes to the cloud and detects the version in the cloud does not match the version it is editing, it knows the data was edited in two locations without syncing up in between.
	- Example: 	
		-- A and B downloads v1 of object  
		-- A edits object offline keeping v1 status 
		-- B edits data --> sync with cloud -> current state is v2
		-- A syncs with cloud --> stored data is v1 but data downloaded is v2 --> problem detected!
		-- Timestamps can be used instead of versions 

## How to resolve conflicts
	- Solution might vary
		-- Leave solution of conflict to user --> ask wich version is the tr
		1. Force the local edits to the cloud, overriding the more recent version (this would now be version 3 in the example above).
		2. Discard local changes and accept the cloud version (2).
		3. Edit the local record then save and sync as version 3.
		4. Take external data from cloud (v2), use a kotlin function to detect diffs (v1), edit new register and by saving the server creates a v3 


## Application client can make a diff to notice for differences between data 	
	- TODO: See Joe Birch Clean Architecture Cache Module for tips on how this is handled there
	- Deixar decisão para o usuário 


# Identifying Connectivity changes on Android 

## Using Broadcast Receivers
- If connectivity is changed, use broadcast receiver and start an assynctask for data refreshing or saving 
  '''xml
	<receiver android:name=".ConnectionReceiver" >
        <intent-filter>
            <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
        </intent-filter>
    </receiver>
  '''
- Teste 

# Sync Adapters to Transfer Data
https://developer.android.com/training/sync-adapters