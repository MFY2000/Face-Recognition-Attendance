package FRA;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdvanceSearch implements Initializable {
    ObservableList<Search> list = FXCollections.observableArrayList();

    public AnchorPane rootPane;
    public TextField searchEngine;
    public RadioButton sortSno;
    public RadioButton sortName;
    public RadioButton sortISBN;
    public RadioButton sortAuther;
    public RadioButton sortDate;
    public javafx.scene.control.TableView<Search>TableView;
    public TableColumn<Search,String>colSno;
    public TableColumn<Search,String> colName;
    public TableColumn<Search,String> colISBN;
    public TableColumn<Search,String>colAuther;
    public TableColumn<Search,String>colDate;
    public TableColumn<Search,Boolean> colAvailiblity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValueinSertion();
        loadData();
        Advancesearch();
    }

    private void ValueinSertion(){
        colSno.setCellValueFactory(new PropertyValueFactory<>("sno"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colAuther.setCellValueFactory(new PropertyValueFactory<>("author"));
        colDate.setCellValueFactory(new PropertyValueFactory<Search,String>("indate"));
        colAvailiblity.setCellValueFactory(new PropertyValueFactory<>("availiblity"));
    }


    public static class Search{
        private final SimpleStringProperty sno;
        private final SimpleStringProperty name;
        private final SimpleStringProperty isbn;
        private final SimpleStringProperty auther;
        private final SimpleStringProperty insertdate;
        private final SimpleBooleanProperty availiblity;

        public Search(String sno , String name , String isbn , String auther ,  String insertedDate , Boolean Availiblity ){
            this.sno = new SimpleStringProperty(sno);
            this.name = new SimpleStringProperty(name);
            this.isbn = new SimpleStringProperty(isbn);
            this.auther = new SimpleStringProperty(auther);
            this.insertdate = new SimpleStringProperty(insertedDate);
            this.availiblity = new SimpleBooleanProperty(Availiblity);
        }

        public String getSno(){
            return sno.get();
        }
        public String getName(){
            return name.get();
        }
        public String getIsbn(){
            return isbn.get();
        }
        public String getAuthor(){
            return auther.get();
        }
        public String getDate(){
            return insertdate.getValue();
        }
        public Boolean getAviliblity(){
            return availiblity.get();
        }

        public void setSno(String sno){
            this.sno.set(sno);
        }
        public void setName(String name){
            this.name.set(name);
        }
        public void seIsbn(String isbn){
            this.isbn.set(isbn);
        }
        public void setAuther(String auther){
            this.auther.set(auther);
        }
        public void setInsertionDate(String insertDate){
            this.insertdate.set(insertDate);
        }
        public void setAvailiblity(Boolean Availiblity){
            this.availiblity.set(Availiblity);
        }

        public SimpleStringProperty indateProperty() {
            return insertdate;
        }

        public SimpleBooleanProperty availiblityProperty() {
            return availiblity;
        }
    }


    private void loadData(){
        DbConn connect = new DbConn();
        String SELECT_BOOK_QUERY = "select * from book_collection";
        //  PreparedStatement preparedStatement = connect.connection.prepareStatement(SELECT_BOOK_QUERY);
        ResultSet resultSet = connect.execQuery(SELECT_BOOK_QUERY);
        try {
            while (resultSet.next()) {
                String Sno = resultSet.getString("sno");
                String Name = resultSet.getString("name");
                String Isbn = resultSet.getString("isbn");
                String Auther = resultSet.getString("auther");
                String Dates =resultSet.getString("insertion_date");
                Boolean Avail = resultSet.getBoolean("availiblity");
                list.add(new Search(Sno, Name, Isbn, Auther,Dates, Avail));
            }
        }catch (SQLException e){
            Logger.getLogger(AdvanceSearch.class.getName()).log(Level.SEVERE,null,e);
        }
        TableView.getItems().setAll(list);
    }


    void Advancesearch(){
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Search> filteredData = new FilteredList<>(list, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchEngine.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(search -> {

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if(sortSno.isSelected()){
                    if(search.getSno().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortISBN.isSelected()){
                    if(search.getIsbn().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortName.isSelected()){
                    if (search.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    }
                }else if(sortAuther.isSelected()){
                    if (search.getAuthor().contains(lowerCaseFilter)){
                        return true;
                    }
                }else if(sortDate.isSelected()){
                     if(search.getDate().contains(lowerCaseFilter)){
                        return true;
                    }
                }
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Search> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(TableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TableView.setItems(sortedData);
    }

}
