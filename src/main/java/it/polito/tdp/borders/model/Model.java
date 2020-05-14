package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> idMap;
	private BordersDAO dao;
	private ConnectivityInspector<Country, DefaultEdge> inspector;

	public Model() {
		
		dao = new BordersDAO();
		
		idMap = new HashMap<>();
		
		this.dao.loadAllCountries(idMap);
		
	}
		
	public void creaGrafo(int anno) {
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		this.inspector = new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		
		//Aggiungo i vertici
		for(Country c: idMap.values()) {
			if(this.dao.countryExist(c, anno)) {
				this.grafo.addVertex(c);
			}
		}
		
		//Aggiungo gli archi
		List<Border> confini = dao.getCountryPairs(anno, idMap);
		for(Border b: confini) {
			this.grafo.addEdge(b.getStato1(), b.getStato2());
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public String elencoStati() {
		
		String str = "";
		
		for(Country c: this.grafo.vertexSet()) {
			str += c.getStateName() + " " + Graphs.neighborListOf(this.grafo, c).size() + "\n";
		}
		
		return str;
	}
	
	public int componentiConnesse() {
		return inspector.connectedSets().size();
	}
	
	public String statiConnessi(Country country){
		
		String str = "";
		
		for(Country c: this.inspector.connectedSetOf(country)) {
			str = c.getStateName() + "\n";
		}
		
		return str;
	}
	
	public Set<Country> menuTendina(){
		return this.grafo.vertexSet();
	}
}
