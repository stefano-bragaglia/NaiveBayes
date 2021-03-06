package preparing.languages;

import java.util.*;

/**
 * TODO Add some meaningful class description...
 */
public class Italiano {
	public static final Set<String> LINKS;

	static {
		List<String> links = Arrays.asList(
				"https://it.wikipedia.org/wiki/2_Pallas",
				"https://it.wikipedia.org/wiki/21_(Adele)",
				"https://it.wikipedia.org/wiki/2e_R%C3%A9giment_%C3%A9tranger_de_parachutistes",
				"https://it.wikipedia.org/wiki/3_Juno",
				"https://it.wikipedia.org/wiki/30_Seconds_to_Mars_(album)",
				"https://it.wikipedia.org/wiki/61_Cygni",
				"https://it.wikipedia.org/wiki/Aaliyah",
				"https://it.wikipedia.org/wiki/Albus_Silente",
				"https://it.wikipedia.org/wiki/Alcolismo",
				"https://it.wikipedia.org/wiki/Aldwych_(metropolitana_di_Londra)",
				"https://it.wikipedia.org/wiki/Alex_l%27ariete",
				"https://it.wikipedia.org/wiki/Alien_2_sulla_Terra",
				"https://it.wikipedia.org/wiki/Alimentazione_medievale",
				"https://it.wikipedia.org/wiki/Amazing_Stories",
				"https://it.wikipedia.org/wiki/Anafilassi",
				"https://it.wikipedia.org/wiki/Anastacia",
				"https://it.wikipedia.org/wiki/Anelli_di_Giove",
				"https://it.wikipedia.org/wiki/Anello_principale",
				"https://it.wikipedia.org/wiki/Anselmo_d%27Aosta",
				"https://it.wikipedia.org/wiki/Apoplessia_pituitaria",
				"https://it.wikipedia.org/wiki/Apparato_circolatorio",
				"https://it.wikipedia.org/wiki/Archimede",
				"https://it.wikipedia.org/wiki/Architettura_longobarda",
				"https://it.wikipedia.org/wiki/Neil_Armstrong",
				"https://it.wikipedia.org/wiki/Assedio_di_Costantinopoli_(717)",
				"https://it.wikipedia.org/wiki/Associazione_Calcio_Milan_2011-2012",
				"https://it.wikipedia.org/wiki/Associazione_Scorpius-Centaurus",
				"https://it.wikipedia.org/wiki/Associazione_Sportiva_Roma",
				"https://it.wikipedia.org/wiki/Asteroidi_troiani_di_Giove",
				"https://it.wikipedia.org/wiki/Atlantide",
				"https://it.wikipedia.org/wiki/Ator_l%27invincibile",
				"https://it.wikipedia.org/wiki/Attentato_a_Hitler_del_20_luglio_1944",
				"https://it.wikipedia.org/wiki/Automobile_a_vapore",
				"https://it.wikipedia.org/wiki/Johann_Sebastian_Bach",
				"https://it.wikipedia.org/wiki/Flavio_Torello_Baracchini",
				"https://it.wikipedia.org/wiki/Domenico_Barduzzi",
				"https://it.wikipedia.org/wiki/Battaglia_del_monte_Ortigara",
				"https://it.wikipedia.org/wiki/Battaglia_del_R%C3%ADo_de_la_Plata",
				"https://it.wikipedia.org/wiki/Battaglia_dell%27Isola_di_Wake",
				"https://it.wikipedia.org/wiki/Battaglia_della_Somme",
				"https://it.wikipedia.org/wiki/Battaglia_di_Canne",
				"https://it.wikipedia.org/wiki/Battaglia_di_Capo_Matapan",
				"https://it.wikipedia.org/wiki/Battaglia_di_Capo_Speranza",
				"https://it.wikipedia.org/wiki/Battaglia_di_Cheronea_(86_a.C.)",
				"https://it.wikipedia.org/wiki/Battaglia_di_Creta",
				"https://it.wikipedia.org/wiki/Battaglia_di_Dunkerque",
				"https://it.wikipedia.org/wiki/Battaglia_di_Isandlwana",
				"https://it.wikipedia.org/wiki/Batterioterapia_fecale",
				"https://it.wikipedia.org/wiki/Lucio_Battisti",
				"https://it.wikipedia.org/wiki/Jim_Baxter",
				"https://it.wikipedia.org/wiki/Blackburn_Olympic_Football_Club",
				"https://it.wikipedia.org/wiki/Giovanni_Boccaccio",
				"https://it.wikipedia.org/wiki/Usain_Bolt",
				"https://it.wikipedia.org/wiki/Bombardamento_navale_di_Genova_(1941)",
				"https://it.wikipedia.org/wiki/Bomber_Command",
				"https://it.wikipedia.org/wiki/Napoleone_Bonaparte",
				"https://it.wikipedia.org/wiki/Bressanone",
				"https://it.wikipedia.org/wiki/Bristol_stool_scale",
				"https://it.wikipedia.org/wiki/Brunate",
				"https://it.wikipedia.org/wiki/Pasquale_Bruno",
				"https://it.wikipedia.org/wiki/Michelangelo_Buonarroti",
				"https://it.wikipedia.org/wiki/Aaron_Burr",
				"https://it.wikipedia.org/wiki/Pedro_Calomino",
				"https://it.wikipedia.org/wiki/Caloris_Planitia",
				"https://it.wikipedia.org/wiki/Campagne_germanico-sarmatiche_di_Costantino",
				"https://it.wikipedia.org/wiki/Campagne_siriano-mesopotamiche_di_Sapore_I",
				"https://it.wikipedia.org/wiki/Campidoglio_(Lansing)",
				"https://it.wikipedia.org/wiki/Campionato_mondiale_di_calcio_1930",
				"https://it.wikipedia.org/wiki/Cappella_Sansevero",
				"https://it.wikipedia.org/wiki/Capricorn_(A_Brand_New_Name)",
				"https://it.wikipedia.org/wiki/Michelangelo_Merisi_da_Caravaggio",
				"https://it.wikipedia.org/wiki/Carcinoma_del_polmone",
				"https://it.wikipedia.org/wiki/Carcinoma_della_prostata",
				"https://it.wikipedia.org/wiki/Carrozze_FS_tipo_TEE",
				"https://it.wikipedia.org/wiki/Dan_Carter",
				"https://it.wikipedia.org/wiki/Castello_Barbarossa",
				"https://it.wikipedia.org/wiki/Castello_di_Amantea",
				"https://it.wikipedia.org/wiki/Castello_di_Angers",
				"https://it.wikipedia.org/wiki/Castello_di_Bodiam",
				"https://it.wikipedia.org/wiki/Castello_di_Malbork",
				"https://it.wikipedia.org/wiki/Castello_Reale_di_Racconigi",
				"https://it.wikipedia.org/wiki/Cattedrale_di_San_Basilio",
				"https://it.wikipedia.org/wiki/Marco_Celio_Rufo",
				"https://it.wikipedia.org/wiki/Cerere_(astronomia)",
				"https://it.wikipedia.org/wiki/Chiesa_Moschea_di_Vefa",
				"https://it.wikipedia.org/wiki/Ciclo_di_Krebs",
				"https://it.wikipedia.org/wiki/Cittadella_di_Parma",
				"https://it.wikipedia.org/wiki/Flavio_Claudio_Giuliano",
				"https://it.wikipedia.org/wiki/Claudio_il_Gotico",
				"https://it.wikipedia.org/wiki/Cleomene_III",
				"https://it.wikipedia.org/wiki/Colangiocarcinoma",
				"https://it.wikipedia.org/wiki/Collegiata_di_San_Martino_(Cerreto_Sannita)",
				"https://it.wikipedia.org/wiki/Cristoforo_Colombo",
				"https://it.wikipedia.org/wiki/Complesso_nebuloso_molecolare_di_Gemini_OB1",
				"https://it.wikipedia.org/wiki/Complesso_nebuloso_molecolare_di_Monoceros_R2",
				"https://it.wikipedia.org/wiki/Concattedrale_di_Maria_Santissima_Assunta",
				"https://it.wikipedia.org/wiki/Consolidated_PBY_Catalina",
				"https://it.wikipedia.org/wiki/Paolo_Conte",
				"https://it.wikipedia.org/wiki/Cropani",
				"https://it.wikipedia.org/wiki/Croup",
				"https://it.wikipedia.org/wiki/Cultura_di_Ubaid",
				"https://it.wikipedia.org/wiki/Cuore_umano",
				"https://it.wikipedia.org/wiki/Cygnus_X-1",
				"https://it.wikipedia.org/wiki/Elio_De_Angelis",
				"https://it.wikipedia.org/wiki/Deimos_(astronomia)",
				"https://it.wikipedia.org/wiki/Dengue",
				"https://it.wikipedia.org/wiki/Derby_di_Roma",
				"https://it.wikipedia.org/wiki/Angelo_Destasio",
				"https://it.wikipedia.org/wiki/Deva_Victrix",
				"https://it.wikipedia.org/wiki/Carmelo_Di_Bella",
				"https://it.wikipedia.org/wiki/Diga_di_Hoover",
				"https://it.wikipedia.org/wiki/Disturbed",
				"https://it.wikipedia.org/wiki/Novak_%C4%90okovi%C4%87",
				"https://it.wikipedia.org/wiki/Dolcenera",
				"https://it.wikipedia.org/wiki/Domiziano",
				"https://it.wikipedia.org/wiki/Ines_Donati",
				"https://it.wikipedia.org/wiki/Donkey_Kong_Country_Returns",
				"https://it.wikipedia.org/wiki/Albrecht_D%C3%BCrer",
				"https://it.wikipedia.org/wiki/Eccidio_dell%27ospedale_psichiatrico_di_Vercelli",
				"https://it.wikipedia.org/wiki/El_Greco",
				"https://it.wikipedia.org/wiki/Elfi_della_notte",
				"https://it.wikipedia.org/wiki/Emicrania",
				"https://it.wikipedia.org/wiki/Emorragia_subaracnoidea",
				"https://it.wikipedia.org/wiki/Emorroidi",
				"https://it.wikipedia.org/wiki/Empire_State_Building",
				"https://it.wikipedia.org/wiki/Enrico_V_d%27Inghilterra",
				"https://it.wikipedia.org/wiki/Enrico_VI_d%27Inghilterra",
				"https://it.wikipedia.org/wiki/Enrico_VII_d%27Inghilterra",
				"https://it.wikipedia.org/wiki/Epatite_B",
				"https://it.wikipedia.org/wiki/Epatite_C",
				"https://it.wikipedia.org/wiki/Epsilon_Eridani",
				"https://it.wikipedia.org/wiki/Esame_della_saliva",
				"https://it.wikipedia.org/wiki/Esplorazione_di_Giove",
				"https://it.wikipedia.org/wiki/Estinzione_di_massa_del_Permiano-Triassico",
				"https://it.wikipedia.org/wiki/L%27evocazione_-_The_Conjuring",
				"https://it.wikipedia.org/wiki/Faringite_streptococcica",
				"https://it.wikipedia.org/wiki/Vanessa_Ferrari",
				"https://it.wikipedia.org/wiki/Ferrovia_Verona-Caprino-Garda",
				"https://it.wikipedia.org/wiki/Flauto_dolce",
				"https://it.wikipedia.org/wiki/Tore_Andr%C3%A9_Flo",
				"https://it.wikipedia.org/wiki/Foggia_Calcio",
				"https://it.wikipedia.org/wiki/Formazione_di_Giove",
				"https://it.wikipedia.org/wiki/Foro_di_Traiano",
				"https://it.wikipedia.org/wiki/Fortini_di_Capri",
				"https://it.wikipedia.org/wiki/Fotografia_aerea_con_i_piccioni",
				"https://it.wikipedia.org/wiki/Frattura_del_pene",
				"https://it.wikipedia.org/wiki/Annibale_Frossi",
				"https://it.wikipedia.org/wiki/Gallieno",
				"https://it.wikipedia.org/wiki/Gandalf",
				"https://it.wikipedia.org/wiki/Gastroenterite",
				"https://it.wikipedia.org/wiki/Steven_Gerrard",
				"https://it.wikipedia.org/wiki/Giovanna_d%27Arco",
				"https://it.wikipedia.org/wiki/Giustiniano_I",
				"https://it.wikipedia.org/wiki/Gliese_581",
				"https://it.wikipedia.org/wiki/Gliese_581_c",
				"https://it.wikipedia.org/wiki/Vincent_van_Gogh",
				"https://it.wikipedia.org/wiki/Aloisio_Gonzaga",
				"https://it.wikipedia.org/wiki/Gotta",
				"https://it.wikipedia.org/wiki/Gran_Premio_del_Belgio_2001",
				"https://it.wikipedia.org/wiki/Grande_Moschea_di_Gaza",
				"https://it.wikipedia.org/wiki/Green_Day",
				"https://it.wikipedia.org/wiki/Grotta_delle_Felci",
				"https://it.wikipedia.org/wiki/Grotta_di_Matermania",
				"https://it.wikipedia.org/wiki/Guardie_e_ladri",
				"https://it.wikipedia.org/wiki/Guerra_civile_romana_(306-324)",
				"https://it.wikipedia.org/wiki/Guerra_del_Vietnam",
				"https://it.wikipedia.org/wiki/Guerra_gotica_(535-553)",
				"https://it.wikipedia.org/wiki/Guerra_piratica_di_Pompeo",
				"https://it.wikipedia.org/wiki/Guerra_romano-persiana_del_602-628",
				"https://it.wikipedia.org/wiki/Guerra_sovietico-polacca",
				"https://it.wikipedia.org/wiki/Guerre_romano-persiane",
				"https://it.wikipedia.org/wiki/Eddie_Guerrero",
				"https://it.wikipedia.org/wiki/Hadar_(astronomia)",
				"https://it.wikipedia.org/wiki/HD_209458_b",
				"https://it.wikipedia.org/wiki/HR_8799",
				"https://it.wikipedia.org/wiki/IC_2944",
				"https://it.wikipedia.org/wiki/Il_giardiniere_(Van_Gogh)",
				"https://it.wikipedia.org/wiki/Impatto_su_Giove_del_luglio_2009",
				"https://it.wikipedia.org/wiki/In_the_Court_of_the_Crimson_King",
				"https://it.wikipedia.org/wiki/Inceneritore",
				"https://it.wikipedia.org/wiki/Indestructible_(album_Disturbed)",
				"https://it.wikipedia.org/wiki/Invasione_tedesca_dei_Paesi_Bassi",
				"https://it.wikipedia.org/wiki/Louis_Kahn",
				"https://it.wikipedia.org/wiki/G%C3%A9za_Kert%C3%A9sz",
				"https://it.wikipedia.org/wiki/Kesha",
				"https://it.wikipedia.org/wiki/La_M%C3%A1quina",
				"https://it.wikipedia.org/wiki/Laugh-O-Gram_Studio",
				"https://it.wikipedia.org/wiki/Lazise",
				"https://it.wikipedia.org/wiki/Legionario_romano",
				"https://it.wikipedia.org/wiki/Lena_(fiume)",
				"https://it.wikipedia.org/wiki/Stadio_Velodromo_Libertas",
				"https://it.wikipedia.org/wiki/Linea_Gotica",
				"https://it.wikipedia.org/wiki/Locomotiva_FS_737",
				"https://it.wikipedia.org/wiki/Locomotiva_FS_E.333",
				"https://it.wikipedia.org/wiki/Locomotiva_SAR_26",
				"https://it.wikipedia.org/wiki/Lohengrin_(opera)",
				"https://it.wikipedia.org/wiki/Lombardia",
				"https://it.wikipedia.org/wiki/Jonah_Lomu",
				"https://it.wikipedia.org/wiki/Luftwaffe_(Wehrmacht)",
				"https://it.wikipedia.org/wiki/Magiarizzazione",
				"https://it.wikipedia.org/wiki/Makemake_(astronomia)",
				"https://it.wikipedia.org/wiki/Malattia_di_Crohn",
				"https://it.wikipedia.org/wiki/Malattia_di_Wilson",
				"https://it.wikipedia.org/wiki/Mappa_della_metropolitana_di_Londra",
				"https://it.wikipedia.org/wiki/Marina_militare_romana",
				"https://it.wikipedia.org/wiki/Mariner_4",
				"https://it.wikipedia.org/wiki/Massacro_di_Sand_Creek",
				"https://it.wikipedia.org/wiki/Andrea_Massena",
				"https://it.wikipedia.org/wiki/Massimiano",
				"https://it.wikipedia.org/wiki/Massimino_Trace",
				"https://it.wikipedia.org/wiki/Richie_McCaw",
				"https://it.wikipedia.org/wiki/Medaglie,_decorazioni_e_ordini_cavallereschi_italiani",
				"https://it.wikipedia.org/wiki/Cosimo_III_de%27_Medici",
				"https://it.wikipedia.org/wiki/Meningite",
				"https://it.wikipedia.org/wiki/Metadone",
				"https://it.wikipedia.org/wiki/Modern_Family",
				"https://it.wikipedia.org/wiki/Mondo_perduto_(genere)",
				"https://it.wikipedia.org/wiki/Marilyn_Monroe",
				"https://it.wikipedia.org/wiki/Monte_Piana",
				"https://it.wikipedia.org/wiki/Moschea_di_Koca_Mustafa_Pasci%C3%A0",
				"https://it.wikipedia.org/wiki/Moschea_G%C3%BCl",
				"https://it.wikipedia.org/wiki/Mr._Nobody",
				"https://it.wikipedia.org/wiki/James_Naismith",
				"https://it.wikipedia.org/wiki/Narsete_(generale_bizantino)",
				"https://it.wikipedia.org/wiki/Naruto",
				"https://it.wikipedia.org/wiki/Nebulosa_di_Gum",
				"https://it.wikipedia.org/wiki/Nebulosa_Granchio",
				"https://it.wikipedia.org/wiki/John_Henry_Newman",
				"https://it.wikipedia.org/wiki/Nicol%C3%B2_Nicolosi_(calciatore)",
				"https://it.wikipedia.org/wiki/Nube_del_Camaleonte",
				"https://it.wikipedia.org/wiki/Nube_del_Lupo",
				"https://it.wikipedia.org/wiki/Nube_di_Rho_Ophiuchi",
				"https://it.wikipedia.org/wiki/Nube_molecolare",
				"https://it.wikipedia.org/wiki/Oasis",
				"https://it.wikipedia.org/wiki/Obesit%C3%A0",
				"https://it.wikipedia.org/wiki/Joachim_Olsen",
				"https://it.wikipedia.org/wiki/Operazione_Bagration",
				"https://it.wikipedia.org/wiki/Olivier_Panis",
				"https://it.wikipedia.org/wiki/Panthera_tigris_altaica",
				"https://it.wikipedia.org/wiki/Paramore_(album)",
				"https://it.wikipedia.org/wiki/Parco_nazionale_del_Grand_Teton",
				"https://it.wikipedia.org/wiki/Giovanni_Passannante",
				"https://it.wikipedia.org/wiki/Riccardo_Patrese",
				"https://it.wikipedia.org/wiki/Pianeta",
				"https://it.wikipedia.org/wiki/Pianura_alluvionale",
				"https://it.wikipedia.org/wiki/Scipione_Piattoli",
				"https://it.wikipedia.org/wiki/Picnodisostosi",
				"https://it.wikipedia.org/wiki/Pietra_di_Bismantova",
				"https://it.wikipedia.org/wiki/Pikachu",
				"https://it.wikipedia.org/wiki/Pinzano_al_Tagliamento",
				"https://it.wikipedia.org/wiki/Vettio_Agorio_Pretestato",
				"https://it.wikipedia.org/wiki/Prima_guerra_mitridatica",
				"https://it.wikipedia.org/wiki/Primidone",
				"https://it.wikipedia.org/wiki/Protostella",
				"https://it.wikipedia.org/wiki/Quadriglia_(scacchi)",
				"https://it.wikipedia.org/wiki/Questione_della_lingua_a_Ragusa",
				"https://it.wikipedia.org/wiki/Quinta_coalizione",
				"https://it.wikipedia.org/wiki/Raffreddore_comune",
				"https://it.wikipedia.org/wiki/Rangifer_tarandus",
				"https://it.wikipedia.org/wiki/Renzo_Ravenna",
				"https://it.wikipedia.org/wiki/Reggia_di_Capodimonte",
				"https://it.wikipedia.org/wiki/Reggiane_Re.2000",
				"https://it.wikipedia.org/wiki/Reggiane_Re.2001",
				"https://it.wikipedia.org/wiki/Regione_di_Lacerta_OB1",
				"https://it.wikipedia.org/wiki/Regione_di_Lambda_Orionis",
				"https://it.wikipedia.org/wiki/Regione_di_Scorpius_OB1",
				"https://it.wikipedia.org/wiki/Regione_di_Vulpecula_OB1",
				"https://it.wikipedia.org/wiki/Regione_oscura_della_Giraffa",
				"https://it.wikipedia.org/wiki/Regioni_periferiche_del_Complesso_di_Orione",
				"https://it.wikipedia.org/wiki/Riconoscimenti_ottenuti_da_Toy_Story_3_-_La_grande_fuga",
				"https://it.wikipedia.org/wiki/Riflessioni_sulla_Rivoluzione_in_Francia",
				"https://it.wikipedia.org/wiki/Roma",
				"https://it.wikipedia.org/wiki/Roma_(nave_da_battaglia_1940)",
				"https://it.wikipedia.org/wiki/Romanzo_gotico",
				"https://it.wikipedia.org/wiki/Romolo",
				"https://it.wikipedia.org/wiki/Attila_Sallustro",
				"https://it.wikipedia.org/wiki/San_Menaio",
				"https://it.wikipedia.org/wiki/George_Sand",
				"https://it.wikipedia.org/wiki/Michele_Sanmicheli",
				"https://it.wikipedia.org/wiki/Santuario_di_San_Martino",
				"https://it.wikipedia.org/wiki/Marija_%C5%A0arapova",
				"https://it.wikipedia.org/wiki/Satelliti_naturali_di_Giove",
				"https://it.wikipedia.org/wiki/Schiavit%C3%B9_negli_Stati_Uniti_d%27America",
				"https://it.wikipedia.org/wiki/Scream_4",
				"https://it.wikipedia.org/wiki/Secondo_caso_di_Sciacca",
				"https://it.wikipedia.org/wiki/Manuel_Seoane",
				"https://it.wikipedia.org/wiki/Sh2-54",
				"https://it.wikipedia.org/wiki/William_Shakespeare",
				"https://it.wikipedia.org/wiki/Sifilide",
				"https://it.wikipedia.org/wiki/Sindrome_da_prolasso_valvolare_mitralico",
				"https://it.wikipedia.org/wiki/Sindrome_di_Beh%C3%A7et",
				"https://it.wikipedia.org/wiki/Sindrome_di_Down",
				"https://it.wikipedia.org/wiki/Sindrome_di_Klinefelter",
				"https://it.wikipedia.org/wiki/Sindrome_emolitico-uremica",
				"https://it.wikipedia.org/wiki/Sindrome_epatorenale",
				"https://it.wikipedia.org/wiki/Slackware",
				"https://it.wikipedia.org/wiki/SMS_Emden_(1908)",
				"https://it.wikipedia.org/wiki/SMS_Goeben",
				"https://it.wikipedia.org/wiki/Son_Goku",
				"https://it.wikipedia.org/wiki/Space_Shuttle",
				"https://it.wikipedia.org/wiki/Spedizione_al_K2_del_1954",
				"https://it.wikipedia.org/wiki/Charles_Villiers_Stanford",
				"https://it.wikipedia.org/wiki/Star_Wars:_Episodio_I_-_La_minaccia_fantasma",
				"https://it.wikipedia.org/wiki/Stati_con_armi_nucleari",
				"https://it.wikipedia.org/wiki/Stazione_Spaziale_Internazionale",
				"https://it.wikipedia.org/wiki/Stazioni_dell%27arte",
				"https://it.wikipedia.org/wiki/Sternotherus_carinatus",
				"https://it.wikipedia.org/wiki/Mary_Cassatt",
				"https://it.wikipedia.org/wiki/Storia_del_Palazzo_Ducale_di_Venezia",
				"https://it.wikipedia.org/wiki/Storia_della_fantascienza",
				"https://it.wikipedia.org/wiki/Suite_per_violoncello_solo_di_Johann_Sebastian_Bach",
				"https://it.wikipedia.org/wiki/Super_Mario_World",
				"https://it.wikipedia.org/wiki/Super_Metroid",
				"https://it.wikipedia.org/wiki/Super_Terra",
				"https://it.wikipedia.org/wiki/Supernova_di_tipo_Ia",
				"https://it.wikipedia.org/wiki/Supernova_di_tipo_II",
				"https://it.wikipedia.org/wiki/Domingo_Tarasconi",
				"https://it.wikipedia.org/wiki/Tau_Ceti",
				"https://it.wikipedia.org/wiki/Teatro_del_Mediterraneo_della_seconda_guerra_mondiale",
				"https://it.wikipedia.org/wiki/Terza_guerra_mitridatica",
				"https://it.wikipedia.org/wiki/Tin_Toy",
				"https://it.wikipedia.org/wiki/Tragedia_greca",
				"https://it.wikipedia.org/wiki/Trasporti_a_Londra",
				"https://it.wikipedia.org/wiki/Mario_Trevi_(cantante)",
				"https://it.wikipedia.org/wiki/Trilobita",
				"https://it.wikipedia.org/wiki/Trombofilia",
				"https://it.wikipedia.org/wiki/Trombosi_del_seno_venoso_cerebrale",
				"https://it.wikipedia.org/wiki/Troppo_belli",
				"https://it.wikipedia.org/wiki/Under_the_Bridge",
				"https://it.wikipedia.org/wiki/Unico_Anello",
				"https://it.wikipedia.org/wiki/Unione_Sportiva_Salernitana_1919",
				"https://it.wikipedia.org/wiki/United_States_Air_Force",
				"https://it.wikipedia.org/wiki/United_States_Army_Air_Forces",
				"https://it.wikipedia.org/wiki/Upminster_Bridge_(metropolitana_di_Londra)",
				"https://it.wikipedia.org/wiki/USS_Arizona_(BB-39)",
				"https://it.wikipedia.org/wiki/Ustione",
				"https://it.wikipedia.org/wiki/V1647_Orionis",
				"https://it.wikipedia.org/wiki/Vaiolo",
				"https://it.wikipedia.org/wiki/Jaco_Van_Dormael",
				"https://it.wikipedia.org/wiki/Francisco_Varallo",
				"https://it.wikipedia.org/wiki/Verona",
				"https://it.wikipedia.org/wiki/Via_Camerelle",
				"https://it.wikipedia.org/wiki/Via_ferrata",
				"https://it.wikipedia.org/wiki/Via_Krupp",
				"https://it.wikipedia.org/wiki/Jacques_Villeneuve",
				"https://it.wikipedia.org/wiki/Volo_Air_France_447",
				"https://it.wikipedia.org/wiki/Volpe_a_Nove_Code_(Naruto)",
				"https://it.wikipedia.org/wiki/WrestleMania_XIX",
				"https://it.wikipedia.org/wiki/Wright_Flyer",
				"https://it.wikipedia.org/wiki/Fratelli_Wright",
				"https://it.wikipedia.org/wiki/Zombi_3",
				"https://it.wikipedia.org/wiki/Zona_Safari",
				"https://it.wikipedia.org/wiki/%E1%B8%AAattu%C5%A1a");
		Collections.shuffle(links);
		LINKS = new HashSet<>(links);
	}
}
