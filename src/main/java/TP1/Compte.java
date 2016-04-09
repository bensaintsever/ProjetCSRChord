package TP1;

class Compte{
	
	private int idCompte;
	private boolean etatCompte;
	private int valeurCompte;


	public Compte(int id){
		this.idCompte = id;
		this.etatCompte = false;
		this.valeurCompte = 0;
	}

	public void activerCompte(){
		etatCompte = true;
	}

	public int getidCompte(){
		return idCompte;
	}

	public void ajouterSurCompte(int val){
		if(etatCompte)
			this.valeurCompte += val;
	}
	public boolean enleverSurCompte(int val){
		if(etatCompte && (this.valeurCompte >0)){
			this.valeurCompte -= val;
			return true;
		}
		return false;
	}

	public Integer getValeurCompte(){
		if(etatCompte)
			return this.valeurCompte;
		return null;
	}
	public void setValeurCompte(int val){
		if(etatCompte)
			this.valeurCompte = val;
	}

	public boolean getEtatCompte(){
		return etatCompte;
	}

}