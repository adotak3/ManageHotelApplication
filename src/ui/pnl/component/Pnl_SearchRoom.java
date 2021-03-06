package ui.pnl.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import entity.Category;
import entity.Room;
import ui.component.BoxComponent;

public class Pnl_SearchRoom extends JPanel {

	private Font fontSan = new Font("Arial", Font.BOLD, 18);
	
	private JLabel lbl_category, lbl_checkin_date, lbl_checkout_date;
	
	private JDateChooser dcs_checkin, dcs_checkout;
	
	private JComboBox<Category> cbx_categories;
	
	private JButton btn_search, btn_delete, btn_add;
	private JButton btn1, btn2, btn3, btn4,
				btn5, btn6, btn7, btn8, btn9, btn10,
				btn11, btn12, btn13, btn14, btn15,
				btn16, btn17, btn18, btn19,
				btn20, btn21, btn22, btn23, btn29,
				btn26, btn27, btn28, btn24, btn25,
				btn30, btn31, btn32, btn33, btn34;
	
	private JPanel pnl_empty_rooms, pnl_selected_rooms;

	private JTable tbl_selected_rooms;
	private DefaultTableModel tbl_model_selected_rooms;
	private JScrollPane jsp_rooms;

	private JPanel pnl_rooms;
	
	public Pnl_SearchRoom() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(null, "Tìm kiếm phòng trống:", TitledBorder.LEFT, TitledBorder.TOP, fontSan, Color.MAGENTA));
		init();
		gui();
	}
	
	private void init() {
		// Jpanel
		pnl_empty_rooms = new JPanel(new BorderLayout());
		pnl_empty_rooms.setBorder(BorderFactory.createTitledBorder(null, "Danh sách phòng trống:", TitledBorder.LEFT, TitledBorder.TOP, fontSan, Color.MAGENTA));
		pnl_empty_rooms.setPreferredSize(new Dimension(500, 300));
		
		pnl_rooms = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnl_rooms.setPreferredSize(new Dimension(450, 300));
		
		pnl_selected_rooms = new JPanel(new BorderLayout());
		pnl_selected_rooms.setBorder(BorderFactory.createTitledBorder(null, "Danh sách phòng được chọn:", TitledBorder.LEFT, TitledBorder.TOP, fontSan, Color.MAGENTA));
		pnl_selected_rooms.setPreferredSize(new Dimension(700, 300));
		
		// JLabel
		lbl_category = new JLabel("Loại phòng:");
		lbl_checkout_date = new JLabel("Ngày trả phòng:");
		lbl_checkin_date = new JLabel("Ngày nhận phòng:");

		// JDateChooser
		dcs_checkin = new JDateChooser();
		dcs_checkin.setDateFormatString("dd/MM/yyyy");

		dcs_checkout = new JDateChooser();
		dcs_checkout.setDateFormatString("dd/MM/yyyy");
		
		// JComboBox 
		cbx_categories = new JComboBox<>();
		cbx_categories.setPreferredSize(new Dimension(270, cbx_categories.getPreferredSize().height));
		
		// JButton
		btn_search = new JButton("Tìm kiếm");
		
		btn_delete = new JButton(new ImageIcon("imgs/ic_delete.png"));
		btn_delete.setMargin(new Insets(0, 0, 0, 0));
		btn_delete.setBorder(null);

		btn_add = new JButton(new ImageIcon("imgs/ic_addcart.png"));
		btn_add.setMargin(new Insets(0, 0, 0, 0));
		btn_add.setBorder(null);
		
		btn1 = new JButton("");
		btn2 = new JButton("");
		btn3 = new JButton("");
		btn4 = new JButton("");
		btn5 = new JButton("");
		btn6 = new JButton("");
		btn7 = new JButton("");
		btn8 = new JButton("");
		btn9 = new JButton("");
		btn10 = new JButton("");
		btn11 = new JButton("");
		btn12 = new JButton("");
		btn13 = new JButton("");
		btn14 = new JButton("");
		btn15 = new JButton("");
		btn16 = new JButton("");
		btn17 = new JButton("");
		btn18 = new JButton("");
		btn19 = new JButton("");
		btn20 = new JButton("");
		btn21 = new JButton("");
		btn22 = new JButton("");
		btn23 = new JButton("");
		btn24 = new JButton("");
		btn25 = new JButton("");
		btn26 = new JButton("");
		btn27 = new JButton("");
		btn28 = new JButton("");
		btn29 = new JButton("");
		btn30 = new JButton("");
		btn31 = new JButton("");
		btn32 = new JButton("");
		btn33 = new JButton("");
		btn34 = new JButton("");
		
		// JList
		String[] header = {
				"Mã phòng", "Loại phòng",
				"Ngày nhận", "Ngày trả",
				"Đơn giá", "Giá giảm"
		};
		tbl_model_selected_rooms = new DefaultTableModel(header, 0);
		tbl_selected_rooms = new JTable(tbl_model_selected_rooms) {

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
					return String.class;
				case 4:
					return Double.class;
				default:
					return Float.class;
				}
			}
		};
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbl_selected_rooms.setDefaultRenderer(String.class, centerRenderer);
		tbl_selected_rooms.setRowHeight(30);
		jsp_rooms = new JScrollPane(tbl_selected_rooms);
	}

	private void gui() {
		// Input
		Box b_category = BoxComponent.getHorizontalBox(lbl_category, cbx_categories, 10);
		Box b_checkin = BoxComponent.getHorizontalBox(lbl_checkin_date, dcs_checkin, 10);
		Box b_checkout = BoxComponent.getHorizontalBox(lbl_checkout_date, dcs_checkout, 10);
		
		Box b_input = BoxComponent.getHorizontalBox(b_category, b_checkin, b_checkout, btn_search, 10);
		
		// List Room
			// LEFT
		pnl_empty_rooms.add(new JScrollPane(pnl_rooms));
		Box b_empty_rooms = BoxComponent.getHorizontalBox(pnl_empty_rooms, 10);
		
			// RIGHT
		Box b_button = Box.createHorizontalBox();
		b_button.add(Box.createHorizontalStrut(590));
		b_button.add(btn_delete);
		b_button.add(Box.createHorizontalStrut(10));
		b_button.add(btn_add);
		b_button.add(Box.createHorizontalStrut(10));
		Box bh_selected_rooms = BoxComponent.getHorizontalBox(jsp_rooms, 10);
		Box bv_selected_rooms = BoxComponent.getVerticalBox(bh_selected_rooms, b_button, 10);
		
		pnl_selected_rooms.add(bv_selected_rooms);
		
		Box b_rooms = BoxComponent.getHorizontalBox_NoPadding(BoxComponent.getHorizontalBox(pnl_selected_rooms, 10), b_empty_rooms, 5);
		
		// Full
		Box bv_full = BoxComponent.getVerticalBox(b_input, b_rooms, 10);
		
		add(bv_full);
	}
	
	// Getter
	public JButton getButton(int index, String name) {
		JButton btn = new JButton();
		switch (index) {
			case 1:
				btn = btn1;
				break;
			case 2:
				btn = btn2;
				break;
			case 3:
				btn = btn3;
				break;
			case 4:
				btn = btn4;
				break;
			case 5:
				btn = btn5;
				break;
			case 6:
				btn = btn6;
				break;
			case 7:
				btn = btn7;
				break;
			case 8:
				btn = btn8;
				break;
			case 9:
				btn = btn9;
				break;
			case 10:
				btn = btn10;
				break;
			case 11:
				btn = btn11;
				break;
			case 12:
				btn = btn12;
				break;
			case 13:
				btn = btn13;
				break;
			case 14:
				btn = btn14;
				break;
			case 15:
				btn = btn15;
				break;
			case 16:
				btn = btn16;
				break;
			case 17:
				btn = btn17;
				break;
			case 18:
				btn = btn18;
				break;
			case 19:
				btn = btn19;
				break;
			case 20:
				btn = btn20;
				break;
			case 21:
				btn = btn21;
				break;
			case 22:
				btn = btn22;
				break;
			case 23:
				btn = btn23;
				break;
			case 24:
				btn = btn24;
				break;
			case 25:
				btn = btn25;
				break;
			case 26:
				btn = btn26;
				break;
			case 27:
				btn = btn27;
				break;
			case 28:
				btn = btn28;
				break;
			case 29:
				btn = btn29;
				break;
			case 30:
				btn = btn30;
				break;
			case 31:
				btn = btn31;
				break;
			case 32:
				btn = btn32;
				break;
			case 33:
				btn = btn33;
				break;
			case 34:
				btn = btn34;
				break;
		}
		btn.setText(name);
		btn.setPreferredSize(new Dimension(90, 45));
		return btn;
	}

	public JDateChooser getDcs_checkin() {
		return dcs_checkin;
	}

	public JDateChooser getDcs_checkout() {
		return dcs_checkout;
	}

	public JComboBox<Category> getCbx_categories() {
		return cbx_categories;
	}

	public JButton getBtn_search() {
		return btn_search;
	}

	public JButton getBtn_delete() {
		return btn_delete;
	}

	public JButton getBtn_add() {
		return btn_add;
	}

	public JTable getTbl_selected_rooms() {
		return tbl_selected_rooms;
	}

	public DefaultTableModel getTbl_model_selected_rooms() {
		return tbl_model_selected_rooms;
	}

	public JPanel getPnl_empty_rooms() {
		return pnl_rooms;
	}

	public JPanel getPnl_selected_rooms() {
		return pnl_selected_rooms;
	}
	
}
