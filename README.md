# **Hawkeye Innovations – Open Assessment**

This repository contains my implementation for the **Hawkeye Innovations Open Assessment**, featuring both a **CLI version** and a **JavaFX GUI version**.
The project is built using **Java 17** and **Maven**.

---

## **Requirements**

### **CLI Version**

* Java **17+** must be installed and available on your system PATH.

### **GUI Version**

* Java **17+**
* Maven **3.8+** (required to run via Maven JavaFX plugin)

To verify installations:

```bash
java -version
mvn -version
```

---

## **Option 1: Run via CLI**

The CLI version runs directly using Java. Maven is **not** required for this option.

### **1. Clone the Repository**

```bash
git clone https://github.com/<your-username>/<repo-name>.git
cd <repo-name>
```

### **2. Compile the Project**

Use `javac` or Maven (optional).

**Using Maven (recommended):**

```bash
mvn clean compile
```

### **3. Run the CLI Application**

The main class for the CLI version is:

```
game.cards.cli.CLIMain
```

Run it using Maven:

```bash
mvn exec:java -Dexec.mainClass="game.cards.cli.CLIMain"
```

Or run directly with Java (if you compiled manually):

```bash
java -cp target/classes game.cards.cli.CLIMain
```

---

## **Option 2: Run the GUI Version (JavaFX)**

The GUI version requires Maven because the JavaFX plugin manages module dependencies.

### **1. Clone the Repository**

```bash
git clone https://github.com/<your-username>/<repo-name>.git
cd <repo-name>
```

### **2. Run the JavaFX GUI**

```bash
mvn javafx:exec
```

This will launch the full graphical version of the assessment.

---

## **Project Structure**

```
src/
  main/
    java/
      game/
        cards/
          cli/        ← CLI entry point (CLIMain)
          gui/        ← GUI controllers & views
    resources/
pom.xml
README.md
```



---

## **Notes**

* Both the CLI and GUI versions follow the rules and specifications provided in the Hawkeye Innovations assessment brief.
* Code is written following clean architectural practices to ensure readability, separation of concerns, and testability.
* GUI version uses JavaFX and is run directly via Maven.
