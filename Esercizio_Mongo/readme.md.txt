Lo scopo del èrogetto è quello di gestire un database di iscrizioni; il progetto è formato da due parti: 
	-la parte server (fatta in nodejs). 
	-e quella client fatto in android.
La parte server è composta da diversi file js, che forniscono i vari servizi:
	-routes: gestiste l'instradamento del client in base alle sue richieste http il server eseguirà operazioni
	 differenti.
	-app: gestisce il server creandolo e accogliendo le richieste dei client.
	-user: modello usato per la creazione degli utenti effettua il collegamento con il database.
	-login: controlla che l'utente esista e se esiste esguira un login.
	-profile: si occupa di prelevare i dati degli utenti.
	-register: se non esiste un utente lo crea seguendo il modello.
	-password: gestisce la criptazione della password e il recupero di essa in caso perdita di password.
	
La parte client è stata sviluppata sfruttando RxJavaAdapterFactory che funge da osservatore asincrono.
	
Problemi noti : imperfezioni nel software
