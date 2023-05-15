package viewPackage;

import modelPackage.Member;
import modelPackage.MemberAddress;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MemberSubscriptionModel extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<Member> contents;
    public MemberSubscriptionModel(ArrayList<Member>members) {
        columnNames = new ArrayList<>();
        columnNames.add("First name");
        columnNames.add("Last name");
        columnNames.add("Client Number");
        setContents(members);
    }

    public void setContents(ArrayList<Member>members) {
        contents = members;

    }

    public int getColumnCount( ) { return columnNames.size( );
    }

    public int getRowCount( ) { return contents.size();
    }

    public String getColumnName(int column) { return columnNames.get(column);
    }

    public Object getValueAt (int row, int column) {
        Member member = contents.get(row);
        switch(column) {
            case 0 : return member.getFirstName();
            case 1 : return member.getLastName();
            case 2: return member.getClientNumber();
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
            case 2: c = Integer.class;
                break;
            default: c = String.class;
        }
        return c;
    }
}
