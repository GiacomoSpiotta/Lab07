package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();
			return nercList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<PowerOutages> getNercOutages(Nerc nerc) {
		
		String sql = "SELECT * "
				+ "FROM poweroutages "
				+ "WHERE nerc_id = ? "
				+ "ORDER BY date_event_began ASC";
		List<PowerOutages> nercOutages = new ArrayList<>();
		try{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nerc.getId());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				//int id, LocalDateTime dateBegan, LocalDateTime dateFinish, int hoursOfOutage, int peopleAffected
				PowerOutages po = new PowerOutages(res.getInt("id"), res.getTimestamp("date_event_began").toLocalDateTime(), res.getTimestamp("date_event_finished").toLocalDateTime(), res.getInt("customers_affected"));
				nercOutages.add(po);
			}
			res.close();
			st.close();
			conn.close();
			return nercOutages;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
