# UNO Game 🎮

A fully-featured UNO card game implementation in Java with a graphical user interface. Play against computer opponents with authentic UNO rules and mechanics.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue?style=for-the-badge)

## Features ✨

- **User Authentication System**: Register and login with personalized accounts
- **Single Player Mode**: Play against intelligent CPU opponents
- **Game Statistics**: Track your wins, losses, and total score
- **Leaderboard**: Compete with other players and view rankings
- **Save/Load Game**: Save your progress and continue later
- **Complete UNO Rules**:
  - Number cards (0-9 in four colors)
  - Action cards (Skip, Reverse, Draw Two)
  - Wild cards (Wild, Wild Draw Four)
  - UNO call system
- **Intuitive GUI**: Clean and user-friendly interface built with Java Swing

## Screenshots 📸

*Game features login page, main menu, game board with card display, and player statistics*

## Installation 🚀

### Prerequisites

- **Java JDK 17 or higher** is required
- Download from: [Adoptium (Eclipse Temurin)](https://adoptium.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)

### Option 1: Run Pre-built JAR (Easiest)

1. **Download the latest release** from the [Releases](https://github.com/mtunaswe/UNO-PROJECT/releases) page
2. **Double-click** `run.bat` (Windows) or run the JAR directly:
   ```bash
   java -jar UNO-Game.jar
   ```

### Option 2: Build from Source

1. **Clone the repository**:
   ```bash
   git clone https://github.com/mtunaswe/UNO-PROJECT.git
   cd UNO-PROJECT
   ```

2. **Build the project**:
   - **Windows**: Double-click `build.bat` or run:
     ```bash
     build.bat
     ```
   - **Linux/Mac**: Run:
     ```bash
     javac -d bin -sourcepath src src/Runnable/Main.java src/*/*.java
     cp -r resources bin/
     cd bin
     jar cfm ../UNO-Game.jar ../MANIFEST.MF . resources
     cd ..
     ```

3. **Run the game**:
   - **Windows**: Double-click `run.bat`
   - **Linux/Mac**:
     ```bash
     java -jar UNO-Game.jar
     ```

## How to Play 🎯

### Game Setup
1. **Register/Login**: Create an account or login with existing credentials
2. **Main Menu**: Choose "Quick Play" to start a new game or "Saved Games" to continue
3. **Game Start**: You'll be dealt 7 cards and play against CPU opponents

### Game Rules
- **Objective**: Be the first player to get rid of all your cards
- **Matching**: Play a card that matches the color, number, or symbol of the top card
- **Action Cards**:
  - **Skip**: Next player loses their turn
  - **Reverse**: Reverses the direction of play
  - **Draw Two**: Next player draws 2 cards and loses their turn
  - **Wild**: Change the color in play
  - **Wild Draw Four**: Change color and next player draws 4 cards

### Controls
- **Click** on a card to play it
- **Draw Card**: Click the deck to draw if you can't play
- **Save Game**: Save your progress from the game menu

## Project Structure 📁

```
UNO-PROJECT/
├── src/                      # Source code
│   ├── card_model/          # Card classes (Card, Deck, etc.)
│   ├── game_model/          # Game logic (Game, Player, CPUPlayer)
│   ├── gui/                 # User interface components
│   ├── Controller/          # Game controllers and listeners
│   ├── Interfaces/          # Constants and interfaces
│   └── Runnable/            # Main entry point
├── resources/               # Game assets (images, backgrounds)
├── build.bat               # Windows build script
├── run.bat                 # Windows run script
├── MANIFEST.MF             # JAR manifest file
└── README.md               # This file
```

## Development 💻

### Setting up in IDE

#### Visual Studio Code
1. Install the "Extension Pack for Java" by Microsoft
2. Open the project folder
3. Press F5 or click "Run" above the `main` method in `Main.java`

#### Eclipse
1. File → Import → Existing Projects into Workspace
2. Select the project directory
3. Right-click `Main.java` → Run As → Java Application

#### IntelliJ IDEA
1. File → Open → Select project directory
2. Right-click `Main.java` → Run 'Main.main()'

### Building

The game uses a simple build system:
- **Compilation**: `javac` compiles all `.java` files to `bin/`
- **Resource Copying**: Resources are copied to `bin/resources/`
- **JAR Creation**: Everything is packaged into `UNO-Game.jar`

## Technologies Used 🛠️

- **Java SE**: Core programming language
- **Java Swing**: GUI framework
- **Java AWT**: Graphics and event handling
- **File I/O**: User data persistence and game saving

## Features in Detail 📋

### User Management
- Secure user registration with password validation
- Username uniqueness checking
- Persistent user data storage
- Session management

### Game Statistics
- Win/Loss tracking
- Total score calculation
- Games played counter
- Leaderboard ranking

### AI Opponents
- Strategic card selection
- Valid move generation
- Automated gameplay

## Known Issues 🐛

- Resources must be in the correct path relative to the JAR file
- User data is stored in plain text (not encrypted)

## Future Enhancements 🚀

- [ ] Multiplayer support (network play)
- [ ] More CPU difficulty levels
- [ ] Sound effects and music
- [ ] Customizable themes
- [ ] Mobile version
- [ ] Tournament mode

## Contributing 🤝

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License 📄

This project was created as an educational project. Feel free to use and modify it for learning purposes.

## Author ✍️

**Mert Tuna** ([@mtunaswe](https://github.com/mtunaswe))
- Email: mtuna21@ku.edu.tr

## Acknowledgments 🙏

- UNO is a registered trademark of Mattel
- This is a fan-made implementation for educational purposes
- Card images and game assets created for this project

---

*Developed as a university project - COMP132*

**Made with ❤️ and Java**
