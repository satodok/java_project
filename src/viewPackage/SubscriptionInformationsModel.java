package viewPackage;

import modelPackage.Subscription;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class SubscriptionInformationsModel extends AbstractTableModel {

    private Subscription content;
    private ArrayList<String> columnNames;

    public SubscriptionInformationsModel(Subscription subscription) {
        columnNames = new ArrayList<>();
        columnNames.add("Susbcription ID");
        columnNames.add("Price");
        columnNames.add("Discount");
        columnNames.add("Automatic renewal");
        columnNames.add("Caution payed");
        columnNames.add("Price payed");
        columnNames.add("Start date");
        columnNames.add("End date");
        columnNames.add("Client number");
        columnNames.add("Type name");
        setContent(subscription);
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Subscription subscription = content;
        switch (columnIndex) {
            case 0:
                return subscription.getSubscriptionID();
            case 1:
                return subscription.getPrice();
            case 2:
                return subscription.getDiscount();
            case 3:
                return subscription.getAutomaticRenewal();
            case 4:
                return subscription.getCautionPayed();
            case 5:
                return subscription.getPricePayed();
            case 6:
                return subscription.getStartDate();
            case 7:
                return subscription.getEndDate();
            case 8:
                return subscription.getClientNumber();
            case 9:
                return subscription.getTypeName();
            default:
                return null;
        }
    }
    private void setContent(Subscription subscription) {
        this.content = subscription;
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
            case 8: c = String.class;
                break;
            case 9: c = String.class;
                break;
            default: c = String.class;
        }
        return c;
    }

    public void addRow(Object[] rowData) {
    }

    public void setRowCount(int i) {
    }
}
