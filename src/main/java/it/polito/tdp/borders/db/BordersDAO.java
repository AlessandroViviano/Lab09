package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				if(!idMap.containsKey(rs.getInt("ccode"))) {
					Country c = new Country(rs.getInt("ccode"), rs.getString("StateAbb"),
							rs.getString("StateNme"));
					idMap.put(c.getCCode(), c);
				}
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, Map<Integer, Country> idMap) {

		String sql = "SELECT DISTINCT state1no, state2no " + 
				"FROM contiguity " + 
				"WHERE YEAR <= ? AND conttype = 1";
		
		List<Border> result = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Border b = new Border(idMap.get(rs.getInt("state1no")), idMap.get(rs.getInt("state2no")));
				result.add(b);
			}
			conn.close();
			return result;
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore di connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public boolean countryExist(Country country, int anno) {
		
		String sql = "SELECT COUNT(*) AS contatore " + 
				"FROM contiguity " + 
				"WHERE YEAR <= ? AND " + 
				"state1no = ? || state2no = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, country.getCCode());
			st.setInt(3, country.getCCode());
			
			ResultSet rs = st.executeQuery();
			rs.next();
			if(rs.getInt("contatore") > 0) {
				conn.close();
				return true;
			}else {
				conn.close();
				return false;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore di connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
}
