package dk.dtu.compute.course02324.assignment3.lists.uses;


import dk.dtu.compute.course02324.assignment3.lists.implementations.GenericComparator;
import dk.dtu.compute.course02324.assignment3.lists.types.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A GUI element that is allows the user to interact and
 * change a list of persons.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class PersonsGUI extends GridPane {

    final private List<Person> persons;

    private GridPane personsPane;

    // labels showing average weight and most frequent name
    private Label averageWeightLabel;
    private Label mostFrequentNameLabel;

    // label for showing error messages from button actions
    private Label errorLabel;

    /**
     * Constructor which sets up the GUI attached a list of persons.
     *
     * @param persons the list of persons which is to be maintained in
     *                this GUI component; it must not be <code>null</code>
     */
    public PersonsGUI(@NotNull List<Person> persons) {
        this.persons = persons;

        
        this.setVgap(5.0);
        
        this.setHgap(5.0);

        // text field for person name
        
        TextField field = new TextField();
        
        field.setPrefColumnCount(5);
        
        field.setText("name");

        // text field for numeric weight
        TextField weightField = new TextField();
        

        weightField.setPrefColumnCount(5);
        
        weightField.setText("70");
        
        weightField.setPromptText("weight (number)");

        // label that will show the last error message
        
        errorLabel = new Label("");
        
        
        errorLabel.setWrapText(true);


        // button to add a person (name + weight), catching exceptions
        Button addButton = new Button("add");

        addButton.setOnAction(
                e -> {
                    clearError();

                    try {
                        double weight = parseWeight(weightField.getText());



                        String name = field.getText();


                        if (name == null || name.isBlank()) {
                            showError("enter a name.");
                            return;

                        }
                        Person person = new Person(name.trim(), weight);
                        persons.add(person);
                        update();

                    } catch (Exception ex) {

                        showError(ex.getMessage());
                    }
                });


        // text field and button for adding a person at a given index
        TextField indexField = new TextField();
        indexField.setPrefColumnCount(4);

        indexField.setPromptText("index");

        Button addAtIndexButton = new Button("add at index:");

        addAtIndexButton.setOnAction(

                e -> {
                    clearError();

                    try {
                        int index = Integer.parseInt(indexField.getText().trim());


                        double weight = parseWeight(weightField.getText());

                        String name = field.getText();

                        if (name == null || name.isBlank()) {

                            showError("enter a name.");

                            return;

                        }

                        Person person = new Person(name.trim(), weight);

                        persons.add(index, person);

                        update();
                    } catch (NumberFormatException ex) {

                        showError("invalid index or weight. use whole numbers for index.");

                    } catch (Exception ex) {

                        showError(ex.getMessage());

                    }

                });

        Comparator<Person> comparator = new GenericComparator<>();


        // sort button, catches unsupported operation on sorted lists

        Button sortButton = new Button("sort");



        sortButton.setOnAction(
                e -> {

                    clearError();

                    try {
                        persons.sort(comparator);

                        update();

                    } catch (UnsupportedOperationException ex) {

                        showError("sort is not allowed for sorted list.");

                    } catch (Exception ex) {

                        showError(ex.getMessage());

                    }
                });


        // button to clear the whole list
        Button clearButton = new Button("clear");


        clearButton.setOnAction(

                e -> {
                    clearError();

                    persons.clear();

                    update();
                });

        // labels for average weight and most frequent name statistics
        averageWeightLabel = new Label("average weight: -");

        mostFrequentNameLabel = new Label("most frequent name: -");


        // left column with input fields, buttons and labels
        VBox actionBox = new VBox(

                field,
                weightField,

                addButton,

                indexField,
                addAtIndexButton,


                sortButton,

                clearButton,

                averageWeightLabel,

                mostFrequentNameLabel,

                errorLabel
        );
        actionBox.setSpacing(5.0);

        this.add(actionBox, 0, 0);


        // right column with a scrollable list of persons
        Label labelPersonsList = new Label("persons:");


        personsPane = new GridPane();


        personsPane.setPadding(new Insets(5));

        personsPane.setHgap(5);


        personsPane.setVgap(5);

        ScrollPane scrollPane = new ScrollPane(personsPane);


        scrollPane.setMinWidth(300);

        scrollPane.setMaxWidth(300);

        scrollPane.setMinHeight(300);

        scrollPane.setMaxHeight(300);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        // add the scrollable list to the grid pane (column 1)
        VBox personsList = new VBox(labelPersonsList, scrollPane);

        personsList.setSpacing(5.0);

        this.add(personsList, 1, 0);


        // initial update of the gui from the current list
        update();
    }

    /**
     * update all gui elements from the current list values,
     * including average weight and most frequent name.
     */
    private void update() {

        // clear all existing person entries and rebuild them from the list
        personsPane.getChildren().clear();
        for (int i = 0; i < persons.size(); i++) {

            Person person = persons.get(i);

            Label personLabel = new Label(i + ": " + person.toString());

            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(

                    e -> {

                        persons.remove(person);
                        update();

                    }
            );

            HBox entry = new HBox(deleteButton, personLabel);
            entry.setSpacing(5.0);

            entry.setAlignment(Pos.BASELINE_LEFT);
            personsPane.add(entry, 0, i);

        }



        // update average weight label
        if (persons.isEmpty()) {

            averageWeightLabel.setText("average weight: -");

        } else {

            double sum = 0;

            for (int i = 0; i < persons.size(); i++) {
                sum += persons.get(i).weight;

            }
            double avg = sum / persons.size();

            averageWeightLabel.setText("average weight: " + String.format("%.2f", avg) + " kg");
        }


        // update most frequent name label using a map of name -> count
        if (persons.isEmpty()) {

            mostFrequentNameLabel.setText("most frequent name: -");
        } else {

            Map<String, Integer> nameCount = new HashMap<>();

            for (int i = 0; i < persons.size(); i++) {

                String name = persons.get(i).name;
                nameCount.put(name, nameCount.getOrDefault(name, 0) + 1);

            }

            String bestName = null;

            int bestCount = 0;
            for (Map.Entry<String, Integer> entry : nameCount.entrySet()) {

                if (entry.getValue() > bestCount) {
                    bestCount = entry.getValue();

                    bestName = entry.getKey();

                }

            }
            mostFrequentNameLabel.setText("most frequent name: " + bestName + " (" + bestCount + " times)");
        }

    }


    /**
     * parse weight from text. if empty, return default 70.0.
     * throws an exception if the parsed weight is not a positive number.
     */
    private double parseWeight(String text) {

        if (text == null || text.isBlank()) {

            return 70.0;

        }

        String trimmed = text.trim();
        double w = Double.parseDouble(trimmed.replace(',', '.'));

        if (w <= 0) {
            throw new IllegalArgumentException("weight must be greater than 0.");
        }


        return w;
    }

    // show last error message in the error label
    private void showError(String message) {
        errorLabel.setText("error: " + message);
    }

    // clear any previous error message from the gui
    private void clearError() {
        errorLabel.setText("");
    }
}
