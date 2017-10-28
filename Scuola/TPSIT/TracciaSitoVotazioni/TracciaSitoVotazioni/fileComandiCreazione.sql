/* l'esecuzione di questo file assolve a diversi compiti:
 - crea lo schema relazionale del DB SQLITE usando quanto più possibile lo standard SQL
 - abilita i prgama SQLITE per l'abilitazione dei controlli di interità referneziale
 - popola un minimo il DB
 - effettua dei test sui vincoli espressi nello schema del DB
*/;

DROP TABLE COSAVOTARE; 
DROP TABLE VOTAZIONE; 
DROP TABLE ISCRIZIONE; 
DROP TABLE PERSONA;
DROP TABLE VOTO;

CREATE TABLE PERSONA (IdPersona INTEGER PRIMARY KEY, 
                      CognomeNome VARCHAR (40), 
                      Indirizzo VARCHAR (200), 
                      UserName VARCHAR (20), 
                      Password VARCHAR (20),
                      UNIQUE (CognomeNome COLLATE NOCASE ASC), 
                      UNIQUE (UserName)) ;

CREATE TABLE COSAVOTARE (IdCosaVotare INTEGER PRIMARY KEY AUTOINCREMENT, 
                       TestoCosaVotare VARCHAR (20), 
                       IdVotazione INTEGER REFERENCES VOTAZIONE(IdVotazione),
                       Votanti INTEGER,
                       Favorevoli INTEGER,
                       Contrari INTEGER,
                       Astenuti INTEGER,
                       CONSTRAINT VincoloSomma CHECK (Votanti = Favorevoli+Contrari+Astenuti));

                   

CREATE TABLE VOTAZIONE (IdVotazione INTEGER PRIMARY KEY AUTOINCREMENT, 
                       TestoVotazione VARCHAR(200),
                       NumeroIscritti INTEGER,
                       DataOraInizioVotazione VARCHAR(20),
                       DataOraFineVotazione VARCHAR(20)
                       );


CREATE TABLE VOTO (IdPersona INTEGER, 
                   IdCosaVotare INTEGER REFERENCES COSAVOTARE(IdCosaVotare),
                   primary key(IdPersona,IdCosaVotare)
                   );


CREATE TABLE ISCRIZIONE (
                         IdPersona INTEGER REFERENCES PERSONA(IdPersona),
                         IdVotazione INTEGER REFERENCES VOTAZIONE(IdVotazione),
                         primary key(IdPersona,IdVotazione)
                    );



PRAGMA foreign_keys = true; 
/* N.B. necesssario in sqlte per abilitare i controlli di Int.Ref. */;
INSERT INTO PERSONA (CognomeNome,Indirizzo,UserName,Password)
            VALUES ('Rossi Mario','Via Botteghe oscure 14, Roma','Rossi.Mario','red');

INSERT INTO PERSONA (CognomeNome,Indirizzo,UserName,Password)
            VALUES ('Verdi Gianni','Piazza Del Duomo 1, Milano','Verdi.Gianni','green');

INSERT INTO PERSONA (CognomeNome,Indirizzo,UserName,Password)
            VALUES ('Bianchi Nicola','Via del Campo 23, Genova','Bianchi.Nicola','white');
/* test su UNIQUE di CognomeNome : */
INSERT INTO PERSONA (CognomeNome,Indirizzo,UserName,Password)
            VALUES ('White Nicola','Via del Campo 23, Genova','Bianchi.Nicola','white');

/* test su UNIQUE su UserName : */
INSERT INTO PERSONA (CognomeNome,Indirizzo,UserName,Password)
            VALUES ('White Nicola','Via del Campo 23, Genova','Bianchi.Nicola','white');

INSERT INTO VOTAZIONE(TestoVotazione,NumeroIscritti,DataOraInizioVotazione,DataOraFineVotazione)
            VALUES ('Diritti civili',0,"2017-05-11 00:00:00","2017-06-11 00:00:00");

INSERT INTO VOTAZIONE(TestoVotazione,NumeroIscritti,DataOraInizioVotazione,DataOraFineVotazione)
            VALUES ('CdC 5IC',0,"2017-05-11 00:00:00","2017-05-13 00:00:00");



INSERT INTO COSAVOTARE(TestoCosaVotare,IdVotazione,Votanti,Favorevoli,Contrari,Astenuti)
            VALUES ('Tizio',(SELECT IdVotazione FROM VOTAZIONE where TestoVotazione ='CdC 5IC'),0,0,0,0);

INSERT INTO COSAVOTARE(TestoCosaVotare,IdVotazione)
            VALUES ('Caio',(SELECT IdVotazione FROM VOTAZIONE where TestoVotazione ='CdC 5IC'),0,0,0,0);

INSERT INTO COSAVOTARE(TestoCosaVotare,IdVotazione,Votanti,Favorevoli,Contrari,Astenuti)
            VALUES ('Sempronio',(SELECT IdVotazione FROM VOTAZIONE where TestoVotazione ='CdC 5IC'),0,0,0,0);

INSERT INTO COSAVOTARE(TestoCosaVotare,IdVotazione,Votanti,Favorevoli,Contrari,Astenuti)
            VALUES ('Vaccini', (SELECT IdVotazione FROM VOTAZIONE where TestoVotazione ='Diritti civili'),0,0,0,0);

INSERT INTO COSAVOTARE(TestoCosaVotare,IdVotazione,Votanti,Favorevoli,Contrari,Astenuti)
            VALUES ('Eutanasia',(SELECT IdVotazione FROM VOTAZIONE where TestoVotazione ='Diritti civili'),0,0,0,0);

INSERT INTO ISCRIZIONE ( IdPersona , IdVotazione) VALUES (1,1);
INSERT INTO ISCRIZIONE ( IdPersona , IdVotazione) VALUES (2,1); 
INSERT INTO ISCRIZIONE ( IdPersona , IdVotazione) VALUES (3,1);   
INSERT INTO ISCRIZIONE ( IdPersona , IdVotazione) VALUES (1,2); 
INSERT INTO ISCRIZIONE ( IdPersona , IdVotazione) VALUES (2,2);                 