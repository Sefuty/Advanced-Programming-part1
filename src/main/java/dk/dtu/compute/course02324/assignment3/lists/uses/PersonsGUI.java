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
 * GUI til at vise og redigere en liste af personer (tilføj, slet, sorter, vægt, indeks m.m.).
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class PersonsGUI extends GridPane {

    /** Listen af personer som GUI'en viser og redigerer. */
    final private List<Person> persons;

    private GridPane personsPane;

    // labels der viser gennemsnitsvægt og mest forekommende navn (opdateres i update())
    private Label averageWeightLabel;
    private Label mostFrequentNameLabel;

    // label til fejlbeskeder fra knapper (valgfri, så brugeren ser undtagelser)
    private Label errorLabel;

    /**
     * Opretter GUI'en og kobler den til den givne personliste.
     *
     * @param persons listen der skal vises og redigeres (må ikke være null)
     */
    public PersonsGUI(@NotNull List<Person> persons) {
        this.persons = persons;

        this.setVgap(5.0);
        this.setHgap(5.0);

        // tekstfelt til navn
        TextField field = new TextField();
        field.setPrefColumnCount(5);
        field.setText("name");

        // tekstfelt til vægt (numerisk)
        TextField weightField = new TextField();
        weightField.setPrefColumnCount(5);
        weightField.setText("70");
        weightField.setPromptText("vægt (tal)");

        // label til fejlbeskeder
        errorLabel = new Label("");
        errorLabel.setWrapText(true);

        // knap til at tilføje person (navn + vægt). fanger undtagelser
        Button addButton = new Button("Add");
        addButton.setOnAction(
                e -> {
                    clearError();
                    try {
                        double weight = parseWeight(weightField.getText());
                        String name = field.getText();
                        if (name == null || name.isBlank()) {
                            showError("Angiv et navn.");
                            return;
                        }
                        Person person = new Person(name.trim(), weight);
                        persons.add(person);
                        update();
                    } catch (Exception ex) {
                        showError(ex.getMessage());
                    }
                });

        // tekstfelt til indeks og knap "Add at index:"
        TextField indexField = new TextField();
        indexField.setPrefColumnCount(4);
        indexField.setPromptText("indeks");
        Button addAtIndexButton = new Button("Add at index:");
        addAtIndexButton.setOnAction(
                e -> {
                    clearError();
                    try {
                        int index = Integer.parseInt(indexField.getText().trim());
                        double weight = parseWeight(weightField.getText());
                        String name = field.getText();
                        if (name == null || name.isBlank()) {
                            showError("Angiv et navn.");
                            return;
                        }
                        Person person = new Person(name.trim(), weight);
                        persons.add(index, person);
                        update();
                    } catch (NumberFormatException ex) {
                        showError("Ugyldigt indeks eller vægt. Brug hele tal for indeks.");
                    } catch (Exception ex) {
                        showError(ex.getMessage());
                    }
                });

        Comparator<Person> comparator = new GenericComparator<>();

        // knap til sortering. fanger UnsupportedOperationException for SortedList
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(
                e -> {
                    clearError();
                    try {
                        persons.sort(comparator);
                        update();
                    } catch (UnsupportedOperationException ex) {
                        showError("Sort er ikke tilladt for sorteret liste.");
                    } catch (Exception ex) {
                        showError(ex.getMessage());
                    }
                });

        // knap til at tømme listen
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(
                e -> {
                    clearError();
                    persons.clear();
                    update();
                });

        // labels til gennemsnitsvægt og mest forekommende navn
        averageWeightLabel = new Label("Gennemsnitlig vægt: -");
        mostFrequentNameLabel = new Label("Mest forekommende navn: -");

        // venstre kolonne: felter, knapper og labels
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

        // højre kolonne: scrollbar liste over personer
        Label labelPersonsList = new Label("Persons:");

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

        // tilføj listen til gridpane (kolonne 1)
        VBox personsList = new VBox(labelPersonsList, scrollPane);
        personsList.setSpacing(5.0);
        this.add(personsList, 1, 0);

        // første opdatering af gui ud fra listen
        update();
    }

    /**
     * Opdaterer GUI-elementerne med værdier fra listen, inkl. gennemsnitsvægt og mest forekommende navn.
     */
    private void update() {
        personsPane.getChildren().clear();
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            Label personLabel = new Label(i + ": " + person.toString());
            Button deleteButton = new Button("Delete");
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

        // opdater gennemsnitsvægt
        if (persons.isEmpty()) {
            averageWeightLabel.setText("Gennemsnitlig vægt: -");
        } else {
            double sum = 0;
            for (int i = 0; i < persons.size(); i++) {
                sum += persons.get(i).weight;
            }
            double avg = sum / persons.size();
            averageWeightLabel.setText("Gennemsnitlig vægt: " + String.format("%.2f", avg) + " kg");
        }

        // opdater mest forekommende navn (via Map som i forelæsning 06)
        if (persons.isEmpty()) {
            mostFrequentNameLabel.setText("Mest forekommende navn: -");
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
            mostFrequentNameLabel.setText("Mest forekommende navn: " + bestName + " (" + bestCount + " gange)");
        }
    }

    /**
     * Parser vægt fra tekst. Hvis tom eller ugyldig, kastes exception.
     * Ved tom streng bruges standardværdi 70.0.
     */
    private double parseWeight(String text) {
        if (text == null || text.isBlank()) {
            return 70.0;
        }
        String trimmed = text.trim();
        double w = Double.parseDouble(trimmed.replace(',', '.'));
        if (w <= 0) {
            throw new IllegalArgumentException("Vægt skal være større end 0.");
        }
        return w;
    }

    private void showError(String message) {
        errorLabel.setText("Fejl: " + message);
    }

    private void clearError() {
        errorLabel.setText("");
    }
}
