package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Booking;

public class BookingDAO extends DAOAbstract<Booking> {

	@Override
	public List<Booking> getAll() throws Exception {
		List<Booking> list = new ArrayList<>();
		String sql = "select * from Bookings";
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			while(rs.next()) {
				list.add(0, new Booking(rs.getInt(1), rs.getInt(2), 
						rs.getInt(3), rs.getDouble(4), 
						rs.getString(5), rs.getInt(6), 
						rs.getString(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean insert(Booking r) throws Exception {
		String sql = "insert into Bookings("
				+ "QuantityRoom, quantityCategory, "
				+ "SubTotal, PersonCode, CustomerID, Status) values (?,?,?,?,?,'Booking')";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, r.getQuantityRoom());
		ps.setInt(2, r.getQuantityCategory());
		ps.setDouble(3, r.getSubTotal());
		ps.setString(4, r.getPersonCode());
		ps.setInt(5, r.getCustomerID());
		return ps.executeUpdate() > 0;
	}
	
	public boolean updateStatus(int bookingID, String status) throws Exception {
		String sql = "update Bookings set Status = ? where BookingID = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, status);
		ps.setInt(2, bookingID);
		return ps.executeUpdate() > 0;
	}
	
}
