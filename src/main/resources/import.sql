-- INSERIMENTO 6 POETI --
INSERT INTO autore(id, username, email) VALUES (next_val(autore_seq), 'Er Bestia', 'erbestia@poetadertrullo.it');
INSERT INTO autore(id, username, email) VALUES (next_val(autore_seq), 'Er Quercia', 'erquercia@poetadertrullo.it');
INSERT INTO autore(id, username, email) VALUES (next_val(autore_seq), 'Marta der Terzo Lotto', 'martaterzolotto@poetadertrullo.it');
INSERT INTO autore(id, username, email) VALUES (next_val(autore_seq), 'Sara G.', 'sarag@poetadertrullo.it');
INSERT INTO autore(id, username, email) VALUES (next_val(autore_seq), 'Er Farco', 'erfarco@poetadertrullo.it');
INSERT INTO autore(id, username, email) VALUES (next_val(autore_seq), 'Inumi Laconico', 'inumilaconico@poetadertrullo.it');

--INSERIMENTO POESIE--
INSERT INTO poesia(id, titolo, testo, data_pubblicazione, autore_id) VALUES (next_val(poesia_seq),
'Vojo la luna', E'Testa rasata, ascella ignorante
Sguardo bestiale, inchiostro su pelle
De donne sur petto ce n’ho avute tante
Cadenti sur letto come le stelle\n
Parlata romana, diploma mancante
Eloquio volgare, condotta ribelle
Se vojo so esse un ottimo amante
Ce perdo la testa pe’ certe pischelle\n
Ma in fondo davvero nun me rivelo
E resto da solo, nun vojo nessuna
A sciojeme er gelo che c’ho sotto ‘r pelo\n
Ai lupi quarcosa, lo so, m’accomuna:
Tutte le luci che brillano in cielo
Le ignoro una ad una, voglio la luna.', '2023-05-08', (SELECT id FROM autore WHERE username='Er Bestia'));

INSERT INTO poesia(id, titolo, testo, data_pubblicazione, autore_id) VALUES (next_val(poesia_seq),
'Black Rainbow',
E'Ce so'' delle vorte che so'' infelice
Dentro de me è buio de botto
Me sembra che ''r cielo me maledice
Allora lo sfido e piove a dirotto\n
Ce so'' certe vorte... tutto me tocca
Basta ''no sguardo, ''na sola parola
M''entra ''n capoccia ''na filastrocca
Pe'' falla smette ce vò ''na pistola\n
Ce so'' delle vorte... tutto m''ammazza
Casco dar cielo e sbatto pe'' terra
Quarcuno lassù a vedemme s''incazza
Vòle che smetto de famme ''sta guerra\n
''Na guerra che faccio io co'' me stesso
Dando la colpa sempre a ''sto cielo
Ma quanno lui piagne je vado appresso
E quanno c''ha freddo io me congelo\n
Cerco er calore e volo più in alto
Vojo anna'' incontro ai raggi der sole
Ma sono un nemico ed ecco l''assalto
La luce respinge ciò che non vòle\n
Volo più fiero, veloce e testardo...
Oltre la sera di questa atmosfera
Poi me distrugge er fòco bastardo
Facendo de me ''na macchia più nera', '2023-01-08', (SELECT id FROM autore WHERE username='Er Farco'));

INSERT INTO poesia(id, titolo, testo, data_pubblicazione, autore_id) VALUES (next_val(poesia_seq),
'Ecco perché',
E'La vita non è quell''incomprensione, quel litigio, quella distanza.
O quella serata a cui non volevi proprio mancare.
La vita non è una scopata, un complimento, un buco nell''acqua.
Né quella figura di merda che hai fatto davanti a tutti.
La vita non è qualcosa che si possiede.
Ecco perché se dopo molto tempo pensi a tutte queste cose, le vedi tanto piccole.
Quasi non te le ricordi.\n
La vita è il tuo migliore amico che capisce dai tuoi occhi che non te la stai passando alla grande e senza dire neanche una parola ti stringe forte.
La vita è tua madre che tutte le sere ti pensa e ti regala una preghiera. Spera che tu sia felice. Che nessuno ti faccia del male e che tu non faccia del male a nessuno. Spera che tu possa essere meno inquieto e più spensierato. Chiede a Dio di andarsene solo dopo averti visto sistemato. Tu non credi in Dio, ma in questo caso è come se ci credessi.
La vita è tuo padre vestito da lavoro, pieno di schizzi di calce, che ti aspetta fuori casa nel suo vecchio camion. È uscito prima di te per riscaldare il motore. La vita sono le scorze d''arancia che trovi sul sedile: se le dimentica sempre. La vita sono le sue rughe. È quell''espressione stanca ma felice, perché è lui ad accompagnare suo figlio alla stazione e dargli l''ultimo abbraccio. Tu gli dici di non lavorare troppo e chiudi lo sportello. Poi ti giri, lo vedi andare via e pensi che parli poco con lui. Pensi che ci sono ancora troppe cose che non conosce di te e che il tempo a tua disposizione per raccontargliele inizia a diminuire.\n
La vita è quel pianto disperato lungo un''eternità ed esploso senza motivo.
La vita sono i tuoi silenzi, i tuoi pensieri, le tue trasformazioni. Le mille parti di te che si alternano, sovrappongono, uccidono.
La vita sono le paure che non riesci a superare. Le verità che non riesci ad accettare.
La vita è quella volta che hai sentito di amare. Non ci credevi quando te lo raccontavano. Quella sensazione è nuova. Arriva. E tu capisci.
Ecco perché se dopo molto tempo pensi a tutte queste cose, le vedi tanto grandi.
Quasi ti viene da piangere.', '2022-07-28', (SELECT id FROM autore WHERE username='Er Bestia'));

INSERT INTO poesia(id, titolo, testo, data_pubblicazione, autore_id) VALUES (next_val(poesia_seq),
'Ritorna',
E'Oggi t''ho sentito
dopo tanto tempo...
Un attimo è servito
pe'' scioje ''sto cemento\n
che ''mprigionava ''r còre
da quanno ''n ce sei più.
Mio codardo amore,
ritorna pure tu...!\n
Ritorna come fanno
i pescatori ar porto
cor callo e co'' l''affanno
cor sole appena morto.\n
Ritorna come torna
er giorno dopo er buio.
Ritorna come l''onda
der mare a fine luglio.\n
Ma oggi t''ho sentito!
E questo può bastare
per accettar l''invito
che m''offre questo mare\n
chiamato “fantasia”...
Fa mòve le passioni
che vònno fuggi'' via
tra nuvole e aquiloni.', '2022-07-13', (SELECT id FROM autore WHERE username='Marta der Terzo Lotto'));

INSERT INTO poesia(id, titolo, testo, data_pubblicazione, autore_id) VALUES (next_val(poesia_seq),
'L''amore che sento',
E'L''amore che sento in questo momento
è de ''na bellezza feroce, inaudita.
Supera er mare. Gareggia cor vento.
Me toje ''l respiro. Me cionca le dita.\n
La rabbia che ''n tempo bruciava dentro
de botto me pare che s''è raddorcita.
D''avella sentita però nun me pento.
A esse più forte, lo so, m''è servita.\n
Vojo esse svejo. Devo sta'' attento
a vive ''r presente. Sporcamme de vita.
Vita ''mbottita de cielo e cemento.\n
Quello che sono è un tratto a matita.
Scrivo, cancello, riscrivo ed invento,
guidato da questa creazione infinita.', '2022-07-06', (SELECT id FROM autore WHERE username='Inumi Laconico'));

INSERT INTO poesia(id, titolo, testo, data_pubblicazione, autore_id) VALUES (next_val(poesia_seq),
'Ho raccolto',
E'Ho raccolto materiale
di ricordi, di momenti.
E mi farò del male
con rumori che non senti.\n
Ho raccolto le istantanee
attraverso la memoria.
E in grotte sotterranee
ho rinchiuso la mia storia.\n
Ho raccolto dei frammenti
di quello che c’è stato.
Son come dei lamenti
di un cane abbandonato.\n
Ho raccolto le parole
donate, sussurrate.
Ma ora sono sole:
schedate, congelate.\n
Ho raccolto fantasie,
menzogne che intuivo.
Son pezzi di poesie
che ingoio mentre scrivo.', '2022-05-13', (SELECT id FROM autore WHERE username='Er Farco'));

INSERT INTO poesia(id, titolo, testo, data_pubblicazione, autore_id) VALUES (next_val(poesia_seq),
'Racconto',
E'Odi et Amo alla maniera de Catullo
Radici latine attraversano er Trullo
M''annullo d''amore dall''alba ar tramonto
Controllo la rabbia e poi la racconto\n
Racconto che so'' un''anonima bestia
Che scrive cor còre, senza modestia
E resto in ascolto in mezzo alla gente
Rifletto, comprendo, accendo la mente\n
Nessuno conosce i miei lineamenti
So'' meno importanti rispetto ai strumenti
Che su giuramento ho stretto tra ''e mani
Pensieri, argomenti e versi romani\n
Racconto che Roma, dar precipizio,
Subisce er disastro sociale, edilizio
Politico, umano, e in più culturale...
Co'' l''animo a pezzi e un pianto che sale\n
Racconto che er tempo che passa feroce
Me dice e m''avverte, arzando la voce
Che chi me vò bene va messo ar sicuro
E chi me vò male... tre palmi dar culo\n
Racconto che Roma nun è tutta marcia
C''è pure chi cerca e schiaccia l''erbaccia
E chi nun s''arrende ar cappio che pende
E in notti tremende un fòco s''accende\n
Un fòco che serve pe'' fasse calore...
E copre il rumore der monno che mòre
In mezzo ar disprezzo de chi se ne frega
Racconto ''a bellezza che spesso m''annega\n
Perché so'' cosciente che quello che sento
Nun posso tenello sempre qui dentro...
Lo devo da offri'' - sia alba o tramonto -
A chi sta a senti''... Pe'' questo racconto', '2022-05-10', (SELECT id FROM autore WHERE username='Er Bestia'));


