# Assignment 3c – Java Collections and GUI Extensions

## What the assignment was about

In Assignment 3c you had to extend the **PersonsGUI** so that:

1. **Weight input:** Add a **TextField** where the user can enter the **weight** of the person being added (not just an auto-incrementing value). You could either restrict input to valid numbers or allow any text and validate when “Add” is pressed; and either require a weight (throw if missing) or use a default.

2. **Add at index:** Add a **numeric TextField** and a **Button** (e.g. “Add at index:”) that **inserts** a person at a **given index**, i.e. the same behaviour as **List.add(int pos, E e)**.

3. **Two labels** that update whenever the list changes:
   - **Average weight** of all persons in the list (mean of their weights).
   - **Most frequently occurring name** in the list, using a **Map&lt;K,V&gt;** (e.g. as in the L06 lecture example for counting occurrences).

4. **Exceptions:** Button actions should **catch** exceptions (e.g. invalid weight, invalid index, or sorting a SortedList) so that nothing crashes and preferably the user sees the error (e.g. in a label or text area) instead of only in the console.

---

## How it was solved

### 1. Weight field and parsing

- A **TextField** `weightField` was added with default text `"70"` and prompt `"vægt (tal)"` so the user can type a weight.  
- A helper **parseWeight(String text)** was added:
  - If the text is null or blank, return **70.0** (default).
  - Otherwise trim, replace comma with dot for decimal, and parse with **Double.parseDouble**.
  - If the parsed value is **&lt;= 0**, throw **IllegalArgumentException** (e.g. “Vægt skal være større end 0.”).
  - **NumberFormatException** from parsing is left to propagate so the button’s catch block can show a clear error in the GUI.

So: default weight when empty, validation when not empty, and all errors shown in the GUI via the error label.

### 2. Add button (with weight and exception handling)

- The **Add** button no longer uses an auto-incrementing weight; it reads **name** from the existing name field and **weight** from **weightField.getText()** passed to **parseWeight(...)**.
- Before adding: **clearError()**, then in a **try** block: validate name (e.g. not null/blank), create **Person(name.trim(), weight)**, call **persons.add(person)**, then **update()**.
- **catch (Exception ex):** call **showError(ex.getMessage())** so any exception (invalid weight, null name, etc.) is shown in the GUI and no unhandled exception reaches the console.

### 3. Add at index

- A **TextField** `indexField` (e.g. prompt “indeks”) and a **Button** “Add at index:” were added.
- On action: **clearError()**, then in **try**: parse index with **Integer.parseInt(indexField.getText().trim())**, get weight via **parseWeight(weightField.getText())** and name as above, create **Person**, then **persons.add(index, person)** and **update()**.
- **catch (NumberFormatException):** show a clear message (e.g. “Ugyldigt indeks eller vægt. Brug hele tal for indeks.”).
- **catch (Exception):** e.g. **IndexOutOfBoundsException** from **add(index, person)** is caught and shown via **showError(ex.getMessage())**.

So the behaviour matches **List.add(int pos, E e)** and invalid index or parsing errors are shown in the GUI.

### 4. Labels: average weight and most frequent name

- Two instance **Label**s were added: **averageWeightLabel** and **mostFrequentNameLabel**, and an **errorLabel** for exception messages.
- In **update()** (called after every list change):

  - **Average weight:**  
    If the list is empty, set the label to something like “Gennemsnitlig vægt: -”.  
    Otherwise: sum all **person.weight** in a simple loop, compute **avg = sum / persons.size()**, and set the label text to a formatted string (e.g. **String.format("%.2f", avg)** and “ kg”). No Map is needed here; just a sum and a count.

  - **Most frequent name:**  
    If the list is empty, set the label to “Mest forekommende navn: -”.  
    Otherwise: use a **Map&lt;String, Integer&gt;** (e.g. **HashMap**). Loop over all persons and for each name do **nameCount.put(name, nameCount.getOrDefault(name, 0) + 1)**. Then loop over the map entries and find the name with the **largest** count. Set the label to that name and its count (e.g. “Mest forekommende navn: Anna (3 gange)”). This follows the L06 idea of using a Map to count frequencies.

So both labels are updated whenever **update()** runs (after add, add at index, remove, clear, sort).

### 5. Exception handling on all buttons

- **Add:** try/catch around parseWeight, Person creation, and add; show message with **showError(ex.getMessage())**.
- **Add at index:** try/catch with special handling for **NumberFormatException** and generic **Exception** for index bounds and others.
- **Sort:** try/catch around **persons.sort(comparator)** and **update()**. **UnsupportedOperationException** is caught separately and shown with a clear message (e.g. “Sort er ikke tilladt for sorteret liste.”) so the user understands that sorting is not allowed on a SortedList. Other exceptions are shown via **showError(ex.getMessage())**.
- **Clear:** **clearError()** and then clear; no exceptions expected, but error label is cleared so old errors do not stay visible.

So no unhandled exceptions in the console, and the user sees errors in the **errorLabel**.

---

## Summary

| Requirement            | How it was met                                                                 |
|------------------------|---------------------------------------------------------------------------------|
| Weight TextField       | Added weightField; default “70”; parseWeight() used in Add and Add at index.  |
| Add uses name + weight | Add button reads name and weightField, creates Person(name, weight), add(person). |
| Add at index           | indexField + button; parse index, create Person, persons.add(index, person).   |
| Average weight label   | In update(): sum weights, divide by size, format and set averageWeightLabel.   |
| Most frequent name    | In update(): Map&lt;String,Integer&gt; to count names, find max count, set mostFrequentNameLabel. |
| Exceptions caught     | All button handlers in try/catch; errors shown in errorLabel.                   |
| Sort on SortedList     | UnsupportedOperationException caught and shown with a clear message.            |

Comments in the code are in Danish; identifiers and UI strings can be in Danish or English as in the rest of the project.
