# Twin_Match-Game
This is a simple 2D game created using Java

# 🎮 Twin Match

A memory-based picture matching game developed in Java using CMake for project configuration.

## 📝 Game Description

Twin Match Quest is a classic memory matching game where players flip over tiles to find matching pairs. When a match is found, the tiles disappear. The goal is to clear all the tiles by correctly matching all pairs with as few attempts as possible.

## 🧠 How to Play

1. **Click on a tile** to reveal an image
2. **Click on a second tile** to reveal another image
3. **If the images match**, the tiles disappear and you earn points
4. **If they don't match**, the tiles flip back and you must remember their positions
5. **Complete the board** by matching all pairs with as few attempts as possible

## 🔢 Game Levels

### 1. Beginner
- **Total Pairs**: 8 (16 tiles)
- **Time Limit**: ❌ No time limit
- **Description**: Ideal for new players to practice without pressure

### 2. Intermediate
- **Total Pairs**: 8 (16 tiles)
- **Time Limit**: ✅ 1 minute
- **Description**: Moderate difficulty with added time pressure

### 3. Advanced
- **Total Pairs**: 11 pairs and 3 extra tiles (25 tiles)
- **Time Limit**: ✅ 1 minute (Extra 15 mins will be added when you click a bonus tile)
- **Description**: Challenging level for experienced players with a strict time limit

## 🎨 Design & Aesthetics
- **Color Scheme**: Soft gradients with modern, pleasing color tones
- **UI Elements**: Rounded buttons, smooth flip animations, and disappearing effects for matched tiles
- **Font**: Clean and legible sans-serif font for clarity
- **Visual Feedback**: 
  - Color-coded tiles (blue for hidden)
  - Bonus tile indicators for Advanced level (special visual effects)
  - Level-specific background themes for enhanced immersion
  - Smooth transitions between game states
  - Visual confirmation for successful matches and bonus activations

## 🛠 Technologies Used

- **Java**: Core game logic and GUI (using Swing)
- **Swing**: User interface components
- **Timer**: Game timing and animations
- **File I/O**: For managing game assets and settings

## 📁 Project Structure

```
Twin_Match-Game/
│
├── controller/
│   └── GameController.java           ← Main game controller
│
├── model/
│   ├── GameLevel.java                ← Game level definitions
│   ├── GameState.java                ← Game state management
│   └── Tile.java                     ← Tile model class
│
├── util/
│   ├── AnimationManager.java         ← Handles tile animations
│   └── SoundManager.java             ← Manages game sounds
│
├── view/
│   ├── StartScreen.java              ← Main menu screen
│   ├── LevelSelectionScreen.java     ← Level selection interface
│   ├── GameWindow.java               ← Main game window
│   ├── BeginnerLevel.java            ← Beginner level implementation
│   ├── IntermediateLevel.java        ← Intermediate level implementation
│   ├── AdvancedLevel.java            ← Advanced level implementation
│   └── SettingsDialog.java           ← Game settings dialog
│
├── main/
│   └── Main.java                     ← Application entry point
│
├── assets/
│   ├── twin_match_bg.jpg             ← Background image for start screen
│   ├── level_bg.jpg                  ← Background for level selection
│   ├── beginner_bg.png               ← Beginner level background
│   ├── intermediate_bg.png           ← Intermediate level background
│   ├── advanced_bg.png               ← Advanced level background
│   └── tiles/                        ← Tile images
│       ├── img1.jpg to img12.jpg/png ← Game tile images
│
├── bin/                              ← Compiled .class files
├── LICENSE
└── README.md

```

## 🚀 Building and Running

### Prerequisites

- **Java 8 or higher**
- **Java Development Kit (JDK)**

### Build Instructions

1. **Compile the project**:
   ```bash
   # From the project root directory
   javac -d bin -cp . controller/*.java model/*.java util/*.java view/*.java main/*.java
   ```

### Running the Game

#### Option 1: Run from compiled classes
```bash
# From the project root directory
java -cp bin main.Main
```

#### Option 2: Compile and run in one command
```bash
# From the project root directory
javac -d bin -cp . controller/*.java model/*.java util/*.java view/*.java main/*.java && java -cp bin main.Main
```

#### Option 3: Using an IDE
- Import the project into your preferred Java IDE (Eclipse, IntelliJ IDEA, VS Code)
- Run the `Main.java` file from the `main` package

## 🎯 Game Features

- **Three difficulty levels** with increasing complexity
- **Timer system** for Intermediate and Advanced levels
- **Attempt counter** to track performance
- **Pause/Resume functionality**
- **Smooth animations** and visual feedback
- **Modern UI** with intuitive controls
- **Settings dialog** for game customization
- **Background music** and sound effects
- **Multiple tile images** for visual variety
- **Level-specific backgrounds** for enhanced immersion

## 🎮 Controls

- **Mouse Click**: Select tiles
- **Pause Button**: Pause/Resume game
- **Main Menu Button**: Return to level selection
- **Level Buttons**: Start new game at selected difficulty
- **Settings Button**: Access game settings and preferences

## 🔧 Development

### Adding New Features

1. **New Game Levels**: Create new level classes (e.g., `ExpertLevel.java`) and update `GameLevel` enum
2. **Bonus Tile Mechanics**: Extend tile system to support special bonus tiles with time extensions
3. **UI Improvements**: Modify view classes (`StartScreen`, `GameWindow`, `LevelSelectionScreen`)
4. **Game Logic**: Extend `GameController` and `GameState` classes for bonus tile handling
5. **Animations**: Enhance `AnimationManager` class for bonus tile effects and visual feedback
6. **Timer Enhancements**: Add dynamic timer updates and bonus time mechanics
7. **Settings Integration**: Extend `SettingsDialog` for new game options

### Code Organization

- **MVC Pattern**: 
  - Model: `GameState`, `GameLevel`, `Tile` (with bonus tile support)
  - View: `StartScreen`, `GameWindow`, `LevelSelectionScreen`, level-specific classes
  - Controller: `GameController` (handles bonus tile logic)
- **Utility Classes**: `AnimationManager`, `SoundManager`
- **Separation of Concerns**: Each class has a specific responsibility
- **Event-Driven**: UI events trigger game logic updates
- **State Management**: Centralized game state in `GameState` class
- **Level-Specific Implementation**: Separate classes for each difficulty level
- **Bonus System**: Special tile handling for Advanced level time extensions
- **Dynamic Timer**: Real-time timer updates with bonus time integration

## 📝 License

This project is open source and available under the MIT License.

## 🤝 Contributing

Feel free to contribute by:
- Adding new features
- Improving the UI
- Optimizing performance
- Adding sound effects
- Creating new difficulty levels

---

**Enjoy playing Twin Match!** 🎮✨ 

---

**Author:** A.K.N. Tharushika  
**Implementation:** Java Swing-based memory matching game with multiple difficulty levels and bonus mechanics
