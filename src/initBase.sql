-- Les articles
INSERT INTO article VALUES ('re12', 'EN_COURS','patate', 1, 0);
INSERT INTO article VALUES ('re13', 'NON_COMMENCE','frite', 4, 0);
INSERT INTO article VALUES ('re14', 'NON_COMMENCE','tartiflette', 12, 0);
INSERT INTO article VALUES ('re15', 'NON_COMMENCE','pomme de terre', 32, 0);
INSERT INTO article VALUES ('re16', 'NON_COMMENCE','raclette', 12, 0);
INSERT INTO article VALUES ('re17', 'NON_COMMENCE','canard', 42, 0);

-- Les clients
INSERT INTO client (dtype, adressemail, pourcentage) VALUES ('Agressif', 'bateau@mer.fr',0);
INSERT INTO client (dtype, adressemail, pourcentage) VALUES ('Classique', 'moi@free.fr',0);
INSERT INTO client (dtype, adressemail, pourcentage) VALUES ('Aleatoire', 'avion@air.fr',50);
INSERT INTO client (dtype, adressemail, pourcentage) VALUES ('Systematique', 'velo@terre.fr',25);
INSERT INTO client (dtype, adressemail, pourcentage) VALUES ('Aleatoire', 'voiture@terre.fr',42);
INSERT INTO client (dtype, adressemail, pourcentage) VALUES ('Systematique', 'cheval@terre.fr',12);

-- Les acheteurs
INSERT INTO acheteur (article_code, client_adressemail, plafond) VALUES ('re12', 'bateau@mer.fr', 100);
INSERT INTO acheteur (article_code, client_adressemail, plafond) VALUES ('re12', 'avion@air.fr', 50);
INSERT INTO acheteur (article_code, client_adressemail, plafond) VALUES ('re12', 'voiture@terre.fr', 100);

INSERT INTO acheteur (article_code, client_adressemail, plafond) VALUES ('re13', 'voiture@terre.fr', 100);
INSERT INTO acheteur (article_code, client_adressemail, plafond) VALUES ('re13', 'cheval@terre.fr', 75);

