package viewPackage;

import modelPackage.DiscountMember;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class DiscountMemberModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<DiscountMember> contents;

    public DiscountMemberModel(ArrayList<DiscountMember> members) {
        columnNames = new ArrayList<>();
        columnNames.add("First name");
        columnNames.add("Last name");
        columnNames.add("Discount");
        columnNames.add("Client number");
        setContents(members);
    }

    private void setContents(ArrayList<DiscountMember> members) {
        contents = members;
    }

    public int getColumnCount( ) { return columnNames.size( ); }
    public int getRowCount( ) { return contents.size( ); }
    public String getColumnName(int column) { return columnNames.get(column); }

    public Object getValueAt (int row, int column) {
        DiscountMember member = contents.get(row);
        switch(column) {
            case 0 : return member.getFirstName();
            case 1 : return member.getLastName();
            case 2 : return member.getDiscount();
            case 3 : return member.getClientNumber();
            default : return null;
        }
    }

    public Class getColumnClass (int column) {
        Class c;

        switch (column) {
            case 0: c = String.class; break;
            case 1: c = String.class; break;
            case 2: c = Double.class; break;
            case 3: c = Integer.class; break;
            default: c = String.class;
        }
        return c;
    }
}
