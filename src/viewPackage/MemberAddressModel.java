package viewPackage;

import modelPackage.MemberAddress;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MemberAddressModel extends AbstractTableModel {

    private ArrayList<String> columnNames;
    private MemberAddress content;
    public MemberAddressModel(MemberAddress memberAddress) {
        columnNames = new ArrayList<>();
        columnNames.add("First name");
        columnNames.add("Last name");
        columnNames.add("Address");
        setContent(memberAddress);
    }

    public void setContent(MemberAddress memberAddress) {
        this.content = memberAddress;
    }

    public int getColumnCount( ) { return columnNames.size( );
    }

    public int getRowCount( ) { return 1;
    }

    public String getColumnName(int column) { return columnNames.get(column);
    }

    public Object getValueAt (int row, int column) {
        MemberAddress memberAddress = content;
        switch(column) {
            case 0 : return memberAddress.getFirstName();
            case 1 : return memberAddress.getLastName();
            case 2: return memberAddress.getAddress();
            default : return null;
        }
    }
    public Class getColumnClass (int column)
    { Class c;
        switch (column)
        { case 0: c = String.class;
            break;
            case 1: c = String.class;
                break;
            case 2: c = String.class;
                break;
            default: c = String.class;
        }
        return c;
    }


}
