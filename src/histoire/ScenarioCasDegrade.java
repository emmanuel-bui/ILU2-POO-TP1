package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		System.out.println(etal.libererEtal());
		Gaulois oui = new Gaulois("Oui", 1);
		try {
			System.out.println(etal.acheterProduit(0, oui));
		} catch (IllegalArgumentException e) {
			System.out.println("La valeur doit être supérieure à 0.");
		} catch (IllegalStateException e) {
			System.out.println("Il n'y a pas de vendeur dans cet étal");
		} catch (NullPointerException e) {
			System.out.println("Le Gaulois n'existe pas.");
		}
		try {
			Village villageCasse = new Village("Bibimbap", 12, 1);
			villageCasse.afficherVillageois();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test");
	}

}
