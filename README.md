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
- **Total Pairs**: 9 (18 tiles)
- **Time Limit**: ❌ No time limit
- **Description**: Ideal for new players to practice without pressure

### 2. Intermediate
- **Total Pairs**: 16 (32 tiles)
- **Time Limit**: ✅ 3 minutes
- **Description**: Moderate difficulty with added time pressure

### 3. Advanced
- **Total Pairs**: 25 (50 tiles)
- **Time Limit**: ✅ 5 minutes
- **Description**: Challenging level for experienced players with a strict time limit

## 🎨 Design & Aesthetics

- **Color Scheme**: Soft gradients with modern, pleasing color tones
- **UI Elements**: Rounded buttons, smooth flip animations, and disappearing effects for matched tiles
- **Font**: Clean and legible sans-serif font for clarity
- **Visual Feedback**: Color-coded tiles (blue for hidden, gold for flipped, green for matched)

## 🛠 Technologies Used

- **Java**: Core game logic and GUI (using Swing)
- **CMake**: Project configuration and build system
- **Swing**: User interface components
- **Timer**: Game timing and animations

## 📁 Project Structure

```
TwinMatch/
│
├── src/
│   ├── controller/
│   │   └── GameController.java
│   │
│   ├── model/
│   │   ├── GameLevel.java
│   │   ├── GameState.java
│   │   └── HighScoreManager.java
│   │
│   ├── util/
│   │   └── SoundManager.java
│   │
│   ├── view/
│   │   ├── StartScreen.java
│   │   ├── LevelSelectionScreen.java
│   │   └── GameWindow.java
│   │
│   └── Main.java
│
├── assets/
│   ├── twin_match_bg.jpg             ← Background image for start screen
│   ├── level_bg.jpg                  ← Optional background for level screen
│   └── sounds/
│       └── click.wav                 ← Sound effect for button click
│
├── lib/                              ← External libraries (if any)
│   └── *.jar
│
├── README.md
└── build.gradle / pom.xml           ← If using Gradle or Maven

```

## 🚀 Building and Running

### Prerequisites

- **Java 11 or higher**
- **CMake 3.16 or higher**
- **Make** (or your system's build tool)

### Build Instructions

1. **Create build directory**:
   ```bash
   mkdir build
   cd build
   ```

2. **Configure with CMake**:
   ```bash
   cmake ..
   ```

3. **Build the project**:
   ```bash
   cmake --build .
   ```

### Running the Game

#### Option 1: Using CMake
```bash
# From the build directory
cmake --build . --target run
```

#### Option 2: Direct Java execution
```bash
# From the build directory
java -jar TwinMatchQuest.jar
```

#### Option 3: From source
```bash
# Compile and run directly
javac -d . src/com/twinmatchquest/*.java
java com.twinmatchquest.Main
```

## 🎯 Game Features

- **Three difficulty levels** with increasing complexity
- **Timer system** for Intermediate and Advanced levels
- **Score tracking** based on successful matches
- **Attempt counter** to track performance
- **Pause/Resume functionality**
- **Smooth animations** and visual feedback
- **Modern UI** with intuitive controls
- **Game over dialog** with statistics

## 🎮 Controls

- **Mouse Click**: Select tiles
- **Pause Button**: Pause/Resume game
- **Main Menu Button**: Return to level selection
- **Level Buttons**: Start new game at selected difficulty

## 🏆 Scoring System

- **+10 points** for each successful match
- **Attempt counter** tracks total moves
- **Time tracking** for timed levels
- **Final score** displayed on game completion

## 🔧 Development

### Adding New Features

1. **New Game Levels**: Add to `GameLevel` enum and update `GameController`
2. **UI Improvements**: Modify `GameWindow` class
3. **Game Logic**: Extend `GameController` and `GameState` classes
4. **Animations**: Add to `GameWindow` update methods

### Code Organization

- **MVC Pattern**: Model (GameState), View (GameWindow), Controller (GameController)
- **Separation of Concerns**: Each class has a specific responsibility
- **Event-Driven**: UI events trigger game logic updates
- **State Management**: Centralized game state in GameState class

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

**Enjoy playing Twin Match Quest!** 🎮✨ 
