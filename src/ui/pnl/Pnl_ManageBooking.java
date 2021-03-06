package ui.pnl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.BookingDAO;
import dao.BookingDetailDAO;
import dao.CategoryDAO;
import dao.CustomerDAO;
import entity.Account;
import entity.Booking;
import entity.BookingDetail;
import entity.Category;
import entity.Customer;
import ui.component.BoxComponent;
import ui.frm.Frm_Payment;

public class Pnl_ManageBooking extends JPanel implements ActionListener {

	private Font fontSan = new Font("Arial", Font.BOLD, 14);

	private JLabel lbl_booking_id, lbl_quantity_room, 
			lbl_quantity_category, lbl_subtotal, 
			lbl_person_code, lbl_customer;
	private JTextField txt_booking_id, txt_quantity_room,
			txt_quantity_category, txt_subtotal, 
			txt_person_code, txt_customer;
	private JButton btn_delete, btn_checkin, 
			btn_pay, btn_load, btn__person_code;
	private JPanel pnl_booking, pnl_detail;
	private JTable tbl_booking, tbl_detail;
	private DefaultTableModel tbl_model_booking, tbl_model_detail;
	private JScrollPane jsp_booking, jsp_detail;

	private JPanel pnl_header, pnl_body;

	// DAO
	private BookingDAO bdao;
	private BookingDetailDAO bddao;
	private CustomerDAO cdao;
	
	// List
	private List<Booking> bookings;
	private List<BookingDetail> bookingDetails;
	private List<Customer> customers;

	private DefaultTableCellRenderer dtbl_cell_render;

	private CategoryDAO cadao;
	
	private JFrame jFrame;
	
	private Account account;
	
	public Pnl_ManageBooking(JFrame jFrame, Account account) {
		setLayout(new BorderLayout());
		this.jFrame = jFrame;
		this.account = account;
		init();
		gui();
		getData();
		tableEvent();
	}
	
	private void tableEvent() {
		tbl_booking.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl_booking.getSelectedRow();
				String id = tbl_model_booking.getValueAt(row, 0).toString();
				String qRoom = tbl_model_booking.getValueAt(row, 1).toString();
				String qCategory = tbl_model_booking.getValueAt(row, 2).toString();
				String subtotal = tbl_model_booking.getValueAt(row, 3).toString();
				String personCode = tbl_model_booking.getValueAt(row, 4).toString();
				String customer = tbl_model_booking.getValueAt(row, 5).toString();
				
				txt_booking_id.setText(id);
				txt_quantity_room.setText(qRoom);
				txt_quantity_category.setText(qCategory);
				txt_subtotal.setText(subtotal);
				txt_person_code.setText(personCode);
				txt_customer.setText(customer);
				
				tbl_model_detail.setRowCount(0);
				try {
					List<BookingDetail> list = bddao.getByBookingID(Integer.parseInt(id));
					for(BookingDetail i : list) {
						Object[] obj = {
							i.getBookingDetailID(), i.getCheckinDate(),
							i.getCheckoutDate(), i.getPrice(),
							i.getDiscount(), i.getRoomID(), getCategoryName(i.getCategoryID())
						};
						tbl_model_detail.addRow(obj);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void init() {
		// DAO
		bdao = new BookingDAO();
		bddao = new BookingDetailDAO();
		cdao = new CustomerDAO();
		cadao = new CategoryDAO();
		
		// List
		try {
			bookings = bdao.getAll();
			bookingDetails = bddao.getAll();
			customers = cdao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Jpanel
		pnl_header = new JPanel(new BorderLayout());
		JLabel lbl = new JLabel();
		lbl.setIcon(new ImageIcon("imgs/rose.jpeg"));
		pnl_header.add(lbl);

		pnl_body = new JPanel(new BorderLayout());
		pnl_body.setBorder(BorderFactory.createTitledBorder(null, "Quản lý đơn đặt phòng:", TitledBorder.LEFT, TitledBorder.TOP,
				fontSan, Color.MAGENTA));

		// JLabel
		lbl_booking_id = new JLabel("Mã đơn:");
		lbl_quantity_room = new JLabel("Số lượng phòng:");
		lbl_quantity_category = new JLabel("Số lượng loại phòng:");
		lbl_subtotal = new JLabel("Tổng tiền:");
		lbl_person_code = new JLabel("CMND:");
		lbl_customer = new JLabel("Khách hàng:");

		lbl_booking_id.setPreferredSize(lbl_subtotal.getPreferredSize());
		lbl_person_code.setPreferredSize(lbl_quantity_category.getPreferredSize());
		lbl_customer.setPreferredSize(lbl_quantity_room.getPreferredSize());

		// JTextField
		txt_customer = new JTextField();
		txt_customer.setEditable(false);
		txt_quantity_room = new JTextField();
		txt_quantity_room.setEditable(false);
		txt_booking_id = new JTextField();
		txt_booking_id.setEditable(false);
		txt_person_code = new JTextField();
		txt_subtotal = new JTextField();
		txt_subtotal.setEditable(false);
		txt_quantity_category = new JTextField();
		txt_quantity_category.setEditable(false);

		// JButton
		btn_delete = new JButton(new ImageIcon("imgs/ic_delete.png"));
		btn_delete.setMargin(new Insets(0, 0, 0, 0));
		btn_delete.setBorder(null);
		btn_delete.addActionListener(this);

		btn_checkin = new JButton(new ImageIcon("imgs/ic_checkin.png"));
		btn_checkin.setMargin(new Insets(0, 0, 0, 0));
		btn_checkin.setBorder(null);
		btn_checkin.addActionListener(this);

		btn_pay = new JButton(new ImageIcon("imgs/ic_pay.png"));
		btn_pay.setMargin(new Insets(0, 0, 0, 0));
		btn_pay.setBorder(null);
		btn_pay.addActionListener(this);
		
		btn_load = new JButton(new ImageIcon("imgs/ic_load.png"));
		btn_load.setMargin(new Insets(0, 0, 0, 0));
		btn_load.setBorder(null);
		btn_load.addActionListener(this);
		
		btn__person_code = new JButton(new ImageIcon("imgs/ic_check.png"));
		btn__person_code.setMargin(new Insets(0, 0, 0, 0));
		btn__person_code.setBorder(null);
		btn__person_code.addActionListener(this);

		// JTable
		String[] header_booking = { 
				"Mã đơn", "Số lượng loại phòng",
				"Số lượng phòng", "Tổng tiền",
				"CMND", "Khách hàng", "Tình trạng"
		};
		tbl_booking = new JTable(tbl_model_booking = new DefaultTableModel(header_booking, 0)){

			private static final long serialVersionUID = 1L;

			/*
			 * @Override public Class getColumnClass(int column) { return getValueAt(0,
			 * column).getClass(); }
			 */
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return Double.class;
				case 4:
					return String.class;
				case 5:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		tbl_booking.setRowHeight(25);
		dtbl_cell_render = new DefaultTableCellRenderer();
		dtbl_cell_render.setHorizontalAlignment(JLabel.CENTER);
		tbl_booking.setDefaultRenderer(String.class, dtbl_cell_render);

		String[] header_detail = { 
				"#", "Ngày nhận phòng", 
				"Ngày trả phòng","Đơn giá", 
				"Giảm giá", "Phòng", "Loại phòng"
		};
		tbl_detail = new JTable(tbl_model_detail = new DefaultTableModel(header_detail, 0)){

			private static final long serialVersionUID = 1L;

			/*
			 * @Override public Class getColumnClass(int column) { return getValueAt(0,
			 * column).getClass(); }
			 */
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return Double.class;
				case 4:
					return Float.class;
				case 5:
					return Integer.class;
				default:
					return String.class;
				}
			}
		};
		tbl_detail.setRowHeight(25);
		dtbl_cell_render = new DefaultTableCellRenderer();
		dtbl_cell_render.setHorizontalAlignment(JLabel.CENTER);
		tbl_detail.setDefaultRenderer(String.class, dtbl_cell_render);

		// JScrollPane
		jsp_booking = new JScrollPane(tbl_booking);
		jsp_booking.setPreferredSize(new Dimension(tbl_booking.getPreferredSize().width, 400));

		jsp_detail = new JScrollPane(tbl_detail);
		jsp_detail.setPreferredSize(new Dimension(tbl_detail.getPreferredSize().width, 200));

		// JPanel
		pnl_booking = new JPanel(new BorderLayout());
		pnl_booking.setBorder(BorderFactory.createTitledBorder(null, "Danh sách đơn đặt:", TitledBorder.LEFT,
				TitledBorder.TOP, fontSan, Color.MAGENTA));

		pnl_detail = new JPanel(new BorderLayout());
		pnl_detail.setBorder(BorderFactory.createTitledBorder(null, "Chi tiết đơn đặt:", TitledBorder.LEFT,
				TitledBorder.TOP, fontSan, Color.MAGENTA));

	}
	
	private void getData() {
		tbl_model_booking.setRowCount(0);
		try {
			bookings = bdao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(Booking b : bookings) 
		{
			Object[] row = {
				b.getBookingID(), b.getQuantityCategory(),
				b.getQuantityRoom(), b.getSubTotal(),
				b.getPersonCode(), getCustomerName(b.getCustomerID()),
				b.getStatus()
			};
			tbl_model_booking.addRow(row);
			
		}
	}
	
	private String getCustomerName(int customerID) {
		String name = "";
		for(Customer c : customers) {
			if(c.getCustomerID() == customerID)
				name = c.getFirstName() + " " + c.getMiddleName() + " " + c.getLastName();
		}
		return name;
	}
	
	private String getCategoryName(int categoryID) {
		String name = "";
		for(Category c : cadao.getAll()) {
			if(c.getCategoryID() == categoryID)
				name = c.getCategoryName();
		}
		return name;
	}

	private void gui() {
		// RESERVATION INFO
		Box b_id = BoxComponent.getHorizontalBox(lbl_booking_id, txt_booking_id, 10);
		Box b_quantity_room = BoxComponent.getHorizontalBox(lbl_quantity_room, txt_quantity_room, 10);
		Box b_quantity_category = BoxComponent.getHorizontalBox(lbl_quantity_category, txt_quantity_category, 10);
		Box b_customer = BoxComponent.getHorizontalBox(lbl_customer, txt_customer, 10);
		Box b_subtotal = BoxComponent.getHorizontalBox(lbl_subtotal, txt_subtotal, 10);
		Box b_person_code = BoxComponent.getHorizontalBox(lbl_person_code, txt_person_code, btn__person_code, 10);
		b_person_code.setPreferredSize(new Dimension(30, b_person_code.getPreferredSize().height));

		Box b1 = BoxComponent.getVerticalBox(b_id, b_subtotal, 10);
		Box b2 = BoxComponent.getVerticalBox(b_quantity_category, b_person_code, 10);
		Box b3 = BoxComponent.getVerticalBox(b_quantity_room, b_customer, 10);
		
		Box b_info = BoxComponent.getHorizontalBox_NoPadding(b1, b2, b3, 30);
		b_info.setPreferredSize(new Dimension(b_info.getPreferredSize().width, 80));

		// Button
		Box b_button = Box.createHorizontalBox();
		b_button.add(Box.createHorizontalStrut(940));
		b_button.add(btn_load);
		b_button.add(Box.createHorizontalStrut(15));
		b_button.add(btn_delete);
		b_button.add(Box.createHorizontalStrut(15));
		b_button.add(btn_checkin);
		b_button.add(Box.createHorizontalStrut(15));
		b_button.add(btn_pay);
		b_button.add(Box.createHorizontalStrut(10));

		// RESERVATION TABLE
		Box bh_reservation = BoxComponent.getHorizontalBox(jsp_booking, 10);
		Box bv_reservation = BoxComponent.getVerticalBox(bh_reservation, 10);
		pnl_booking.add(bv_reservation);

		// RESERVATION DETAIL TABLE
		Box bh_detail = BoxComponent.getHorizontalBox(jsp_detail, 10);
		Box bv_detail = BoxComponent.getVerticalBox(bh_detail, 10);
		pnl_detail.add(bv_detail);

		// FULL
		Box bv_full = Box.createVerticalBox();
		bv_full.add(pnl_header);
		bv_full.add(Box.createVerticalStrut(10));
		bv_full.add(BoxComponent.getHorizontalBox(b_info, 10));
		bv_full.add(Box.createVerticalStrut(10));
		bv_full.add(BoxComponent.getHorizontalBox(b_button, 10));
		bv_full.add(Box.createVerticalStrut(10));
		bv_full.add(BoxComponent.getHorizontalBox(pnl_booking, 10));
		bv_full.add(Box.createVerticalStrut(10));
		bv_full.add(BoxComponent.getHorizontalBox(pnl_detail, 10));
		bv_full.add(Box.createVerticalStrut(10));

		pnl_body.add(bv_full);
		this.add(BoxComponent.getVerticalBox(BoxComponent.getHorizontalBox(pnl_body, 10), 10));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		String personCode = txt_person_code.getText().trim();
		
		if(o.equals(btn_delete)) {
			
		}
		else if(o.equals(btn_checkin)) {
			int index = tbl_booking.getSelectedRow();
			if(index < 0)
			{
				JOptionPane.showMessageDialog(null, "Bạn chưa chọn đơn đặt để xác nhận việc nhận phòng của khách hàng!");
			}
			else {
				String status = tbl_model_booking.getValueAt(index, 6).toString();
				if(!txt_booking_id.getText().equals("") && status.equals("Booking")) {
					int bookingID = Integer.parseInt(txt_booking_id.getText().trim());
					checkin(bookingID);
				}
				else if(status.equals("Rented")) {
					JOptionPane.showMessageDialog(null, "Đã nhận phòng!");
				}
				else if(status.equals("Paid")) {
					JOptionPane.showMessageDialog(null, "Đã thanh toán!");
				}
			}
		}
		else if(o.equals(btn__person_code)) {
			if(!personCode.equals("")) 
			{
				FindBookingByPersonCode(personCode);
			}
		}
		else if(o.equals(btn_load)) {
			getData();
		}
		else if(o.equals(btn_pay)) {
			if(!txt_booking_id.getText().equals("")) {
				pay();
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Bạn chưa chọn đơn đặt để xác nhận việc nhận phòng của khách hàng!");
			}
		}
		
	}
	
	private void FindBookingByPersonCode(String personCode) {
		boolean status = checkPersonCode(personCode);
		if(!status)
			JOptionPane.showMessageDialog(null, "CMND không đúng hoặc không có trong danh sách!");
		else {
			tbl_model_booking.setRowCount(0);
			for(Booking b : bookings) {
				if(b.getPersonCode().equals(personCode)) {
					Object[] row = {
							b.getBookingID(), b.getQuantityCategory(),
							b.getQuantityRoom(), b.getSubTotal(),
							b.getPersonCode(), getCustomerName(b.getCustomerID()), b.getStatus()
					};
					tbl_model_booking.addRow(row);
					status = true;
				}
			}
		}
	}
	
	private void checkin(int bookingID) {
		int answer = JOptionPane.showConfirmDialog(null,
				"Khách hàng thực sự đến nhận phòng?", "Nhận phòng",
				JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			try {
				bdao.updateStatus(bookingID, "Rented");
				JOptionPane.showMessageDialog(null, "Ghi nhận việc nhận phòng của khách hàng!");
				getData();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void pay() {
		int answer = JOptionPane.showConfirmDialog(null,
				"Khách hàng đã muốn thanh toán?", "Thanh toán đơn đặt",
				JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			int bookingID = Integer.parseInt(txt_booking_id.getText().trim());
			double rentalPrice = Double.parseDouble(txt_subtotal.getText().trim());
			Frm_Payment frm = new Frm_Payment(bookingID, rentalPrice, account);
			frm.setVisible(true);
			jFrame.dispose();
		}
	}
	
	private boolean checkPersonCode(String personCode) {
		for(Booking b : bookings) {
			if(b.getPersonCode().equals(personCode)) {
				return true;
			}
		}
		return false;
	}

}
