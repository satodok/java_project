package viewPackage;

import modelPackage.RentalDetailsInformation;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class RentalDetailsInformationModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<RentalDetailsInformation> contents;

    public RentalDetailsInformationModel(ArrayList<RentalDetailsInformation> members) {
        columnNames = new ArrayList<>();
        columnNames.add("Name");
        columnNames.add("Type");
        columnNames.add("Client number");
        columnNames.add("First name");
        columnNames.add("Last name");
        setContents(members);
    }

    private void setContents(ArrayList<RentalDetailsInformation> members) {
        contents = members;
    }

    public int getColumnCount( ) { return columnNames.size( ); }
    public int getRowCount( ) { return contents.size( ); }
    public String getColumnName(int column) { return columnNames.get(column); }

    public Object getValueAt (int row, int column) {
        RentalDetailsInformation member = contents.get(row);
        switch(column) {
            case 0 : return member.getName();
            case 1 : return member.getType();
            case 2 : return member.getClientNumber();
            case 3 : return member.getFirstName();
            case 4 : return member.getLastName();
            default : return null;
        }
    }

    public Class getColumnClass (int column) {
        Class c;

        switch (column) {
            case 0: c = String.class; break;
            case 1: c = String.class; break;
            case 2: c = Integer.class; break;
            case 3: c = String.class; break;
            case 4 : c = String.class; break;
            default: c = String.class;
        }
        return c;
    }
}
