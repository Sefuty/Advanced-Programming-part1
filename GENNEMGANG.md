# Gennemgang: Opgave 3a, 3b og 3c – hvad opgaven var, og hvordan det er løst

Dette dokument forklarer **hvad hver opgave gik ud på**, **præcis hvilken kode der er skrevet**, og **hvorfor den er skrevet sådan**.

---

## Overblik over opgaverne

- **3a:** Implementer en generisk **List&lt;E&gt;** med array (det meste fandtes allerede i base code).
- **3b:** Implementer **sortering** (BubbleSort) og datatypen **SortedList&lt;E&gt;** (SortedArrayList).
- **3c:** Udvid **GUI’en** med vægt-felt, "Add at index", gennemsnitsvægt og mest forekommende navn (med Map).

Person-klassen skulle også have **compareTo**, **equals** og **hashCode** implementeret, så sortering og sammenligning virker korrekt (det hører under 3b/3c).

---

# Del 1: Person.java

## Hvad opgaven var

Person bruges overalt i GUI og tests. For at sortere og sammenligne personer skal:

- **compareTo** definere rækkefølge (fx til SortedList og Sort-knappen).
- **equals** og **hashCode** være implementeret korrekt og konsistent med compareTo (Java-kontrakt).

## Hvad der er skrevet

### Konstruktor – lille rettelse

```java
if (name == null || weight <= 0) {
    throw new IllegalArgumentException("A person must be initialized with a " +
            "(non null) name and a weight greater than 0");
}
```

- **Hvorfor:** Fejlbeskeden sagde tidligere "age greater than 0" (kopieret fra anden opgave). Her er det **vægt**, så teksten er rettet til "weight greater than 0", så den matcher koden.

### compareTo(Person o)

```java
if (o == null) {
    throw new IllegalArgumentException("Argument of compareTo() must not be null");
}
// først sammenlign på navn
int nameCompare = this.name.compareTo(o.name);
if (nameCompare != 0) {
    return nameCompare;
}
// hvis navne er ens, sammenlign på vægt
return Double.compare(this.weight, o.weight);
```

- **Hvad det gør:** Sammenligner to personer: først **navn** (String.compareTo), derefter **vægt** hvis navne er ens.
- **Hvorfor navn først:** Så sortering bliver forudsigelig: først alfabetisk på navn, derefter på vægt. Det matcher også typisk brug i GUI og tests.
- **Hvorfor Double.compare:** For at sammenligne to `double` uden at lave "a - b" (som kan give fejl med floating point). Double.compare returnerer negativt, 0 eller positivt – præcis som compareTo skal.

### equals(Object o)

```java
if (this == o) {
    return true;
}
if (o == null || getClass() != o.getClass()) {
    return false;
}
Person person = (Person) o;
return Double.compare(person.weight, weight) == 0 && name.equals(person.name);
```

- **Hvad det gør:** To personer er "lige" hvis og kun hvis **navn** og **vægt** er ens.
- **this == o:** Hurtig check – samme objekt er altid lige med sig selv.
- **null og getClass():** Hvis o ikke er en Person (eller er null), er de ikke lige. getClass() sikrer at vi ikke regner en subklasse som lige med en Person uden at have samme felter.
- **Double.compare igen:** Samme grund som i compareTo – korrekt sammenligning af double.
- **Kontrakt:** equals skal være konsistent med compareTo: hvis compareTo siger "lige" (0), skal equals sige true. Her er "lige" defineret som samme navn og vægt i begge metoder.

### hashCode()

```java
return java.util.Objects.hash(name, weight);
```

- **Hvad det gør:** Returnerer en hash baseret på **name** og **weight**.
- **Hvorfor:** Java kræver at **o1.equals(o2) ⇒ o1.hashCode() == o2.hashCode()**. Vi bruger de samme felter som i equals (name og weight), så kontrakten overholdes. Objects.hash er den simple og sikre måde at gøre det på.

---

# Del 2: BubbleSort.java (Opgave 3b)

## Hvad opgaven var

Implementer **Bubble Sort** som en generisk metode der kan sortere en **List&lt;T&gt;** med en **Comparator&lt;T&gt;** (som i forelæsningen, men for lister i stedet for arrays).

## Hvad der er skrevet

### Null-tjek

```java
if (comp == null || list == null) {
    throw new IllegalArgumentException("Arguments of the sort function must not be null.");
}
```

- **Hvorfor:** JavaDoc/opgaven forventer at argumenter ikke er null. Vi tjekker det med det samme, så resten af metoden kan regne med gyldige argumenter.

### Variabler og do-while

```java
boolean swapped;
int n = list.size();
do {
    swapped = false;
    // ...
} while (swapped);
```

- **swapped:** Fortæller om vi har byttet noget i dette gennemløb. Hvis vi gennemgår hele listen uden at bytte, er listen sorteret og vi stopper.
- **n:** Antal elementer. Bruges til at løkken kun kigger på indeks 0..n-2 (nabopar i og i+1).
- **do-while:** Vi kører mindst ét gennemløb; herefter kører vi videre indtil der ikke længere bliver byttet.

### Indre for-løkke (selve bubble sort)

```java
for (int i = 0; i < n - 1; i++) {
    T a = list.get(i);
    T b = list.get(i + 1);
    if (comp.compare(a, b) > 0) {
        list.set(i, b);
        list.set(i + 1, a);
        swapped = true;
    }
}
```

- **Hvad det gør:** Går fra start til slut og kigger på **nabopar** (i og i+1). Hvis de er i forkert rækkefølge (comp siger a > b), **bytter** vi dem med set().
- **comp.compare(a, b) > 0:** Comparator returnerer &gt; 0 når a skal komme efter b. Så vi bytter for at få den mindre først.
- **Hvorfor set() to gange:** Listen har ikke en "swap"-metode. Vi erstatter element på i med b og element på i+1 med a – det er præcis en bytning.
- **swapped = true:** Så do-while ved at vi skal køre endnu en runde.

Bubble sort virker fordi det største element "bobler" ned til slutningen i hvert gennemløb; efter n-1 gennemløb (i værste fald) er listen sorteret.

---

# Del 3: ArrayList.java (Opgave 3b)

## Hvad opgaven var

ArrayList skulle have **sort(Comparator)** implementeret, så den kan sortere listen (ved hjælp af BubbleSort). Resten af ArrayList (clear, get, set, add, remove, indexOf osv.) fandtes i forvejen fra 3a.

## Hvad der er skrevet

### sort(Comparator<? super E> c)

```java
if (c == null) {
    throw new IllegalArgumentException("Comparator must not be null");
}
BubbleSort.sort(c, this);
```

- **Hvad det gør:** Tjekker at comparator ikke er null, og kalder derefter **BubbleSort.sort** med den comparator og **denne** liste (this).
- **Hvorfor null-tjek her:** Interface/JavaDoc forventer at man ikke sender null. Vi giver en tydelig IllegalArgumentException. (BubbleSort tjekker også, men her sikrer vi at ArrayList selv overholder kontrakten.)
- **Hvorfor "this":** BubbleSort.sort(comp, list) tager en liste som andet argument. Vi er selve ArrayList-instansen, så vi sender this – så BubbleSort sorterer direkte i vores array via get/set.

---

# Del 4: SortedArrayList.java (Opgave 3b)

## Hvad opgaven var

- **SortedList&lt;E&gt;** er en liste der **altid** er sorteret. Man må **ikke** kalde set(pos,e), add(pos,e) eller **sort()** – de skal kaste UnsupportedOperationException.
- **SortedArrayList** skal arve fra ArrayList men:
  - ved **add(e)** indsætte elementet på det rigtige sted så listen forbliver sorteret;
  - ved **sort()** altid kaste UnsupportedOperationException (også for sort(null)).

## Hvad der er skrevet

### Override af sort(Comparator)

```java
@Override
public void sort(@NotNull Comparator<? super E> c) throws UnsupportedOperationException {
    // sortering er ikke tilladt på SortedList – listen er altid sorteret
    throw new UnsupportedOperationException("Operation sort(Comparator<? super E> c) not allowed on SortedLists");
}
```

- **Hvad det gør:** Uanset argument (inkl. null) kastes **UnsupportedOperationException**.
- **Hvorfor override:** Uden override ville SortedArrayList bruge ArrayList.sort(). Den tjekker for null og kaster **IllegalArgumentException** før nogen "sorted list"-logik. Testen forventer at **sort(null) på SortedList** giver **UnsupportedOperationException**. Derfor skal SortedArrayList **overskrive** sort() og altid kaste UnsupportedOperationException.

### add(E e)

```java
if (e == null) {
    throw new IllegalArgumentException("Element must not be null");
}
int pos = findIndexToInsert(e);
return super.add(pos, e);
```

- **Hvad det gør:** Finder det **indeks** hvor e skal stå for at listen forbliver sorteret, og kalder så **super.add(pos, e)** (ArrayList’s add med position).
- **Hvorfor null-tjek:** Samme regel som i List – null må ikke ind i listen.
- **Hvorfor super.add(pos, e):** Vi må ikke tillade add(pos, e) udefra (det er blokeret i SortedList), men **internt** kan vi kalde ArrayList’s add(int, E) med det indeks findIndexToInsert har fundet. Så vi genbruger den eksisterende logik (flyt elementer, indsæt).

### findIndexToInsert(E e)

```java
for (int i = 0; i < size(); i++) {
    if (get(i).compareTo(e) >= 0) {
        return i;
    }
}
return size();
```

- **Hvad det gør:** Går gennem listen fra start. Returnerer **første indeks** hvor elementet er **større eller lig med** e (i sorteringsrækkefølge). Hvis alle er mindre end e, returneres **size()** (indsæt bagest).
- **Hvorfor compareTo(e) >= 0:** E skal være Comparable (SortedList kræver E extends Comparable&lt;E&gt;). get(i).compareTo(e) >= 0 betyder "get(i) er lig med eller kommer efter e", så e skal ind **før** get(i), dvs. på plads i.
- **Hvorfor lineær søgning:** Opgaven bad om en simpel løsning. Lineær søgning er let at læse og forstå. En binær søgning ville være hurtigere men mere kode; det var ikke et krav.

---

# Del 5: PersonsGUI.java (Opgave 3c)

## Hvad opgaven var

1. **Vægt-felt:** Et tekstfelt så brugeren kan angive **vægt** når en person tilføjes (ikke bare et stigende tal).
2. **"Add at index":** Et numerisk felt + knap der **indsætter** en person på et bestemt **indeks** (som add(pos, e)).
3. **To labels** der opdateres når listen ændres:
   - **Gennemsnitlig vægt** (alle personers vægt / antal).
   - **Mest forekommende navn** (fx ved brug af Map som i L06).
4. **Undtagelser:** Knapperne skal **fange** undtagelser så der ikke crasher; det er godt hvis brugeren kan **se** fejl i GUI (fx i en label).

## Hvad der er skrevet

### Felter og labels (instansvariabler)

```java
private Label averageWeightLabel;
private Label mostFrequentNameLabel;
private Label errorLabel;
```

- **averageWeightLabel / mostFrequentNameLabel:** Viser gennemsnitsvægt og mest forekommende navn. Skal kunne opdateres i **update()**, derfor gemmes de som felter.
- **errorLabel:** Viser fejlbesked når en knap kaster (fx ugyldig vægt, ugyldigt indeks, "Sort ikke tilladt på sorteret liste"). Så brugeren ser fejlen i stedet for i konsollen.

### Vægt-felt

```java
TextField weightField = new TextField();
weightField.setPrefColumnCount(5);
weightField.setText("70");
weightField.setPromptText("vægt (tal)");
```

- **Hvad det gør:** Et tekstfelt til vægt med startværdi "70" og prompt "vægt (tal)".
- **Hvorfor 70:** Fornuftig standard så brugeren kan trykke Add uden at skrive vægt. Opgaven tillader enten at kræve vægt (exception) eller bruge standard – her er valgt standard ved tom værdi.

### Add-knap (med vægt og undtagelser)

```java
addButton.setOnAction(e -> {
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
```

- **clearError():** Nulstiller tidligere fejl så kun den seneste handling vises.
- **parseWeight(...):** Læser vægt fra tekst (tom = 70, ellers Double.parseDouble; ved vægt ≤ 0 kastes exception). Se parseWeight nedenfor.
- **name.isBlank():** Tomt eller kun mellemrum accepteres ikke – vi viser "Angiv et navn." og returnerer.
- **name.trim():** Fjerner mellemrum foran og bagved.
- **try/catch:** Hvis Person-konstruktøren eller add() kaster (fx ugyldig vægt), fanges det og **showError(ex.getMessage())** viser beskeden i errorLabel. Så ingen ubehandlede exceptions i konsollen.

### "Add at index:" (indeks-felt + knap)

```java
TextField indexField = new TextField();
// ...
addAtIndexButton.setOnAction(e -> {
    clearError();
    try {
        int index = Integer.parseInt(indexField.getText().trim());
        double weight = parseWeight(weightField.getText());
        // ... navn-tjek som ovenfor ...
        persons.add(index, person);
        update();
    } catch (NumberFormatException ex) {
        showError("Ugyldigt indeks eller vægt. Brug hele tal for indeks.");
    } catch (Exception ex) {
        showError(ex.getMessage());
    }
});
```

- **Hvad det gør:** Læser **indeks** som heltal, laver en Person (navn + vægt) og kalder **persons.add(index, person)** – præcis som List.add(int pos, E e).
- **Integer.parseInt:** Ved ugyldig tekst kastes NumberFormatException; vi fanger den og viser en dansk besked. Andre exceptions (fx IndexOutOfBoundsException ved forkert indeks) fanges af den anden catch og vises via showError.

### Sort-knap (særlig håndtering af SortedList)

```java
sortButton.setOnAction(e -> {
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
```

- **Hvorfor særlig catch for UnsupportedOperationException:** Når brugeren vælger "sorted List" og trykker Sort, kaster SortedArrayList.sort() UnsupportedOperationException. I stedet for den tekniske standardbesked vises en kort dansk forklaring: "Sort er ikke tilladt for sorteret liste."

### update() – gennemsnitsvægt

```java
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
```

- **Hvad det gør:** Hvis listen er tom vises "-". Ellers: **sum** af alle weight, **gennemsnit** = sum / antal, og label opdateres med to decimaler (fx "72.50 kg").
- **Hvorfor simpel løkke:** Gennemsnit behøver ikke Map; vi skal bare lægge vægte sammen og dividere. Løkken er nem at læse.

### update() – mest forekommende navn (Map)

```java
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
```

- **Hvad det gør:**  
  - **nameCount:** Map fra **navn** → **antal forekomster**.  
  - Første løkke: for hver person tæller vi dens navn op (getOrDefault(name, 0) + 1).  
  - Anden løkke: finder det navn med **størst** antal (bestCount/bestName).  
  - Label sættes til det navn og antallet (fx "Anna (3 gange)").
- **Hvorfor Map:** Opgaven bad om at bruge Map til at tælle forekomster (som i L06). HashMap giver nem opslag og opdatering af tællere per navn.

### parseWeight(String text)

```java
if (text == null || text.isBlank()) {
    return 70.0;
}
String trimmed = text.trim();
double w = Double.parseDouble(trimmed.replace(',', '.'));
if (w <= 0) {
    throw new IllegalArgumentException("Vægt skal være større end 0.");
}
return w;
```

- **Tom eller blank:** Returnerer 70.0 som standard (som opgaven tillader).
- **replace(',', '.'):** Så både "70,5" og "70.5" accepteres.
- **Double.parseDouble:** Kaster NumberFormatException ved ugyldig tekst – den fanges i knap-handlerne og vises i errorLabel.
- **w <= 0:** Person kræver vægt &gt; 0; vi tjekker det her og kaster en tydelig dansk besked i stedet for at lade Person gøre det.

### showError / clearError

- **showError(String message):** Sætter errorLabel til "Fejl: " + message så brugeren ser fejlen.
- **clearError():** Sætter errorLabel til "" ved ny handling, så gamle fejl ikke bliver hængende.

---

## Korte svar på "hvorfor"

| Valg | Hvorfor |
|------|--------|
| compareTo: navn først, derefter vægt | Forudsigelig sortering og konsistent med equals (samme felter). |
| Double.compare i stedet for a - b | Korrekt og sikkert for double. |
| equals/hashCode med name + weight | Java-kontrakt og konsistens med compareTo. |
| BubbleSort i egen klasse | Opgaven og JavaDoc bad om det; ArrayList kalder bare BubbleSort.sort(c, this). |
| SortedArrayList overrider sort() | Så sort(null) giver UnsupportedOperationException (testen) i stedet for IllegalArgumentException fra ArrayList. |
| findIndexToInsert lineært | Simpel og læsbar; opgaven krævede ikke binær søgning. |
| Vægt tom = 70 | Opgaven tillod standardværdi; 70 er et fornuftigt default. |
| Fejl i label | Undtagelser skal fanges og gerne vises til brugeren (ingen ubehandlede i konsollen). |
| Map til mest forekommende navn | Som anvist i opgaven (L06-eksempel med Map til frekvens). |

Hvis du vil have det samme som et kortere "elevator pitch" eller kun for én fil, kan det laves som en ekstra sektion.
