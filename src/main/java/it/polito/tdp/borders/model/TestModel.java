package it.polito.tdp.borders.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		model.creaGrafo(2000);
		
		System.out.format("Grafo creato! %d vertici e %d archi", model.nVertici(), model.nArchi());
		
		//for(Country c: model.elencoStatiLista()) {
			//System.out.println(c.getStateName());
		//}

		//model.creaGrafo(2000);
		//System.out.format("Grafo creato! %d vertici e %d archi", )
		
//		System.out.println("Creo il grafo relativo al 2000");
//		model.createGraph(2000);
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
