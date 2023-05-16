package viewPackage;

import modelPackage.RentalDetailsInformation;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class RentalDetailsInformationModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<RentalDetailsInformation> contents;

    public RentalDetailsInformationModel(ArrayList<RentalDetailsInformation> rentals) {
        columnNames = new ArrayList<>();
        columnNames.add("Name");
        columnNames.add("Type");
        columnNames.add("Client number");
        columnNames.add("First name");
        columnNames.add("Last name");
        setContents(rentals);
    }

    private void setContents(ArrayList<RentalDetailsInformation> rentals) {
        contents = rentals;
    }

    public int getColumnCount( ) { return columnNames.size( ); }
    public int getRowCount( ) { return contents.size( ); }
    public String getColumnName(int column) { return columnNames.get(column); }

    public Object getValueAt (int row, int column) {
        RentalDetailsInformation rental = contents.get(row);
        switch(column) {
            case 0 : return rental.getName();
            case 1 : return rental.getType();
            case 2 : return rental.getClientNumber();
            case 3 : return rental.getFirstName();
            case 4 : return rental.getLastName();
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
