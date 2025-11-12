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
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
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

			for (int i = 0, indexTableauFinal = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					tableauFinalEtals[indexTableauFinal] = etals[i];
					indexTableauFinal++;
				}
			}
			return tableauFinalEtals;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etalTrouve = null;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];

				}
			}
			return etalTrouve;
		}

		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int etalsVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					etalsVide++;
				}
			}
			if (etalsVide != 0) {
				chaine.append("Il reste " + etalsVide + " étals non utilisés dans le marché.");
			}
			return chaine.toString();
		}

	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + "\n");
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°");
		if (marche.trouverVendeur(vendeur) == null) {
			int etalLibre = marche.trouverEtalLibre();
			marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
			chaine.append(marche.trouverEtalLibre());
		} else {
			chaine.append(marche.trouverVendeur(vendeur));
		}
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsTrouve = this.marche.trouverEtals(produit);
		if (etalsTrouve.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.");
		} else if (etalsTrouve.length == 1) {
			chaine.append("Seul le vendeur " + etalsTrouve[0].getVendeur().getNom() + "  " + "propose des " + produit
					+ " au marché.");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etalsTrouve.length; i++) {
				chaine.append("- ");
				chaine.append(etalsTrouve[i].getVendeur().getNom());
				chaine.append("\n");
			}
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etal = this.marche.trouverVendeur(vendeur);
		if (etal != null) {
			return etal.libererEtal();
		} else {
			return "Il n'y a pas de vendeur dans l'étal";
		}
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + this.getNom() + "\" possède plusieurs étals : \n");
		chaine.append(this.marche.afficherMarche());
		return chaine.toString();
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

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if (chef == null) {
			throw new VillageSansChefException();
		}
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