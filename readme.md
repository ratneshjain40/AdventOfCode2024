# Advent of Code in Kotlin 🎄

Welcome to my **Advent of Code** solutions repository, written in the sleek and powerful Kotlin! 🛠️✨ This is where holiday cheer meets clean code and algorithmic challenges. Dive in and join the fun as we unwrap problems and solve them with style.

## What is Advent of Code? 🎁

Advent of Code is a series of daily programming puzzles released every December. Each puzzle adds a twist to make your coding skills shine. It's like a digital advent calendar for your brain, with challenges ranging from simple to head-scratching. You can find the puzzles at [Advent of Code](https://adventofcode.com).

## Project Structure 🏗️

Here's what you'll find in this repository:

- **src/main/kotlin/solutions**: The heart of the project. Each day’s solution lives here, organized and ready to tackle!
- **src/main/kotlin/solutions/framework**: A simple and reusable framework for adding new solutions (see below!).
- **build.gradle.kts**: Build file to ensure smooth sailing with Kotlin and dependencies.

## Framework for Adding Solutions 🛠️

Want to use this template for your own Advent of Code challenges? You’re in luck! This project is designed to be reusable and extensible.

### Steps to Reuse:

1. **Clone the Repository**:
   ```bash
   git clone <repo-url>
   ```
2. **Remove Existing Solutions**:
   Clear out the solutions in `src/main/kotlin/solutions`.
3. **Start Fresh**:
   Utilize the simple framework to add your own solutions.

### How to Add New Solutions 📚

#### Step 1: Extend the `Solution` Class

Located in `src/main/kotlin/solutions/framework`:

```kotlin
abstract class Solution {
    abstract fun part_1(input: String): Number
    abstract fun part_2(input: String): Number
}
```

#### Step 2: Implement Your Solution

Create a new file for each day's solution. For example, for Day 1:

```kotlin
class Day01 : Solution() {
    private fun parseInput(input: String): ArrayList<ArrayList<Int>> {
        // Parsing logic here
        return ArrayList()
    }

    override fun part_1(input: String): Int {
        // Part 1 solution logic here
        return 0
    }

    override fun part_2(input: String): Int {
        // Part 2 solution logic here
        return 0
    }
}
```

#### Step 3: Register Your Solution

Add your solution in `src/main/kotlin/solutions/SolutionProvider.kt`:

```kotlin
private fun registerSolution() {
    addSolution(1, Day01());
}
```

#### Step 4: Run Your Code

In `Main.kt`, update the `main` function as follows:

```kotlin
fun main() {
    println("Hello World, this is Kotlin Advent of Code 2024")
    SolutionRunner.runSolution(1, Part.PART_1, test = false);
}
```

### Quality of Life Features 🛠️

- **Automatic Puzzle Input Fetching**: Puzzle inputs are fetched and saved automatically in the `DATA_DIR` (configured in `.env`).
- **Session Token Setup**: Add your Advent of Code session token in `.env`. A sample `.env` file is provided in the project.
- **Test Data Management**: Manually add test data in the path `\data\test\` and follow the naming convention `day_{day}.txt` (e.g., `day_01.txt`).

To get your Advent of Code session token using Chrome:
1. Open the Advent of Code website and log in.
2. Right-click anywhere on the page and select **Inspect** or press `Ctrl+Shift+I`.
3. Go to the **Network** tab in the DevTools.
4. Refresh the page and look for a request to `adventofcode.com` (usually named `cookie` or similar).
5. Click on the request and scroll down to the **Cookies** section.
6. Copy the `session` token value.
7. Add this token to your `.env` file as `SESSION_TOKEN=<your-token>`.

This will allow the script to fetch puzzle inputs automatically.

## Contributions 🎨

Feel free to fork this repository, play around with it, and add your unique touch. PRs for improvements are welcome—let’s build something fun together!

## License 📜

This repository is under the [MIT License](LICENSE).

Happy coding and may your December be filled with algorithmic joy! 🎅✨

