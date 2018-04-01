package com.example.isa2017.model;

/**
 * USER - Registrovani korisnici,  mogu da rezervišu mesta u pozorištu/bioskopu za određenu predstavu/projekciju,
 *  da rezervišu karte na popustu jednim klikom, pozovu prijatelje, ocene predstavu/film, ocene pozorište/bioskop,
 *   otkažu rezervaciju mesta ili karte najkasnije pola sata pre početka termina, imaju pristup fan zoni u kojoj 
 *   mogu da kupuju i prodaju tematske rekvizite;
 * ADMIN - Administratori pozorišta/bioskopa: mogu da definišu termine projekcija, cene karata, segmente sala
 *  koji su zatvoreni za sedenje (balkon, VIP sedišta, itd.), da objave koje karte su na popustu, dobijaju izveštaje
 *   o posetama, ocenama predstava/filmova, ambijenta i ostvarenom prihodu na nedeljnom/mesečnom nivou, kao i da 
 *   uređuju info stranicu pozorišta/bioskopa;
 * SYSADMIN - Administratori sistema: mogu da registruju pozorišta, bioskope i njihove administratore.
 * FANPITADMIN - administratori fan zone: mogu da postavljaju/uklanjaju ponudu tematskih rekvizita za predstave/filmove,
 *  da odobravaju licitacije za rekvizite koje registrovani korisnici postavljaju;
 * @author Gema
 *
 */
public enum Role {

	USER, ADMIN, SYSTEMADMIN, FANPITADMIN
	
}
