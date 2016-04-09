package TP1;

import java.util.ArrayList;
import java.util.logging.Logger;

class Bank implements iBank {

	private ArrayList<Compte> listeCompte;
	private int id_reference_compte;

	private final static Logger log = Logger.getLogger(Bank.class.getName());

	public Bank(){
		id_reference_compte = 0;
		listeCompte = new ArrayList<Compte>();
	}

	public void ajouterSurCompte(int id_compte, int val){
		Compte c = listeCompte.get(id_compte);
		c.ajouterSurCompte(val);
		log.info("Ajout de "+val+" sur le compte n°"+ id_compte);
	}
	public void enleverSurCompte(int id_compte, int val){
		Compte c = listeCompte.get(id_compte);
		if(c.enleverSurCompte(val))
			log.info("Suppression de "+val+" sur le compte n°"+ id_compte);
		else
			log.info("Pas assez de fond pour supprimer "+val+ " du comte n°"+id_compte);
	}
	/**
	*	Transfert d'argent du compte de bank1 vers bank2
	*/
	public void transfertEntreCompte(int compte1, int compte2, int val){
		Compte c1 = listeCompte.get(compte1);
		Compte c2 = listeCompte.get(compte2);
		if(c1.getEtatCompte() && c2.getEtatCompte()){
			if(c1.enleverSurCompte(val))
				c2.ajouterSurCompte(val);
		}
		log.info("Transfert de "+val+" de compte n°"+compte1+" à compte n°"+compte2);
	}
	public Integer getValeurCompte(int id_compte){
		Compte c = listeCompte.get(id_compte);
		int valeur_compte = c.getValeurCompte();
		log.info("Valeur du compte n°"+id_compte+" : "+valeur_compte);
		return valeur_compte;
	}
	public void setValeurCompte(int id_compte, int val){
		Compte c = listeCompte.get(id_compte);
		int valeur_compte = c.getValeurCompte();
		if (val < valeur_compte)
			c.ajouterSurCompte(valeur_compte-val);
		if(valeur_compte < val)
			c.ajouterSurCompte(val-valeur_compte);

		log.info("Modification de la valeur du compte : " + val + " présent sur le compte");
	}
	public int creerCompte(){
		int numero_compte_courant = id_reference_compte;
		Compte c = new Compte(numero_compte_courant);
		c.activerCompte();
		listeCompte.add(c);
		this.id_reference_compte++;

		log.info("Creation du compte n°"+numero_compte_courant);
		return numero_compte_courant;
	}

	public Logger getLog(){
		return log;
	}
}