package viewPackage;

import javax.swing.table.AbstractTableModel;
import modelPackage.MemberInformations;

import java.util.ArrayList;

public class MemberInformationsModel extends AbstractTableModel {

    private MemberInformations content;
    private ArrayList<String> columnNames;

    public MemberInformationsModel(MemberInformations memberInformations){
        columnNames = new ArrayList<>();
        columnNames.add("Last name");
        columnNames.add("First name");
        columnNames.add("Birth date");
        columnNames.add("Phone number");
        columnNames.add("Gender");
        columnNames.add("Email");
        columnNames.add("Newsletter");
        columnNames.add("Address");
        setContent(memberInformations);
    }

    private void setContent(MemberInformations memberInformations) {
        this.content = memberInformations;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MemberInformations memberInformations = content;
        switch (columnIndex){
            case 0 : return memberInformations.getLastName();
            case 1 : return memberInformations.getFirstName();
            case 2 : return memberInformations.getBirthDate();
            case 3 : return memberInformations.getPhoneNumber();
            case 4 : return memberInformations.getGender();
            case 5 : return memberInformations.getEmailAddress();
            case 6 : return memberInformations.getNewsLetter();
            case 7 : return memberInformations.getAddress();
            default:return null;
        }
    }

    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    public Class getColumnClass(int column){
        Class c;
        switch (column){
            case 0: c = String.class;
                break;
            case 1: c = String.class;
                break;
            case 2: c = String.class;
                break;
            case 3: c = String.class;
                break;
            case 4: c = String.class;
                break;
            case 5: c = String.class;
                break;
            case 6: c = String.class;
                break;
            case 7: c = String.class;
                break;
            default: c = String.class;
        }
        return c;
    }
}