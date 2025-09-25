package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	private static class Marche {
		private Etal[] etals;

		public Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
			for (int i = 0; i < nombreEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			int resultat = -1;
			int i = 0;
			while (resultat == -1 && i < etals.length) {
				if (!etals[i].isEtalOccupe()) {
					resultat = i;
				}
			}
			return resultat;
		}

		private Etal[] trouverEtals(String produit) {
			int nombreProduitsTrouves = 0;

			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nombreProduitsTrouves++;
				}
			}

			Etal[] tableauFinalEtals = new Etal[nombreProduitsTrouves];

			int indexTableauFinal = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					tableauFinalEtals[indexTableauFinal] = etals[i];
					indexTableauFinal++;
				}
			}
			return tableauFinalEtals;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etalTrouve = null;
			int i = 0;
			while (etalTrouve == null && i < this.etals.length) {
				if (etals[i].getVendeur() == gaulois) {
					etalTrouve = etals[i];
				}
			}
			return etalTrouve;
		}

		private void afficherMarche() {
			int etalsVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				} else {
					etalsVide++;
				}
			}
			if (etalsVide != 0) {
				System.out.println("Il reste " + etalsVide + " étals non utilisés dans le marché.");
			}
		}

	}

	public StringBuilder installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + "\n");
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + "à l'étal ");
		if (this.marche.trouverVendeur(vendeur) != null) {

			chaine.append(this.marche.trouverEtalLibre());
		} else {
			chaine.append(this.marche.trouverVendeur(vendeur));
		}
		return chaine;
	}

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.marche = new Marche(nbEtals);
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}