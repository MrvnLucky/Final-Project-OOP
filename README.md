   # Snake Game

   This repository is a final project (Java GUI) from Object-Oriented Programming Class, Teknik Informatika Universitas Padjadjaran. 

   [Challenge Guidelines](challenge-guideline.md)

   **Snake game is the common game for a videogame concept where the player manage a line to grows in length as long as possible with the line as a primary object called "Snake". the game first concepted in the 1976 and implemented over than hundreds of version.**

   ## Credits

   | NPM           | Name                    |
   | ------------- |-------------------------|
   | 140810190043  | Marvin Luckianto        |
   | 140810190047  | Matthew Felix Ristanto  |
   | 140810190063  | Ananda Sapta Awedhana   |

   ## Change log

   - **[Sprint Planning](changelog/sprint-planning.md) - (19/11/2020)** 
      - Create the board
      - Create snake object
      - Snake movement function
      - Create food abject
      - Create food spawner
      - Score counter function
      - Create start game and end game screen

   - **[Sprint 1](changelog/sprint-1.md) - (20/11/2020 - 24/11/2020)**  
      - Created the board for the game
      - Created the snake object/class
      - Created the snake movement functions in Snake class

   - **[Sprint 2](changelog/sprint-2.md) - (27/11/2020 - 01/12/2020)** 
      - Created the object Food
      - Created Food spawn Randomize
      - Created Keyboard input function
      
   - **[Sprint 3](changelog/sprint-3.md) - (02/12/2020 - 08/12/2020)** 
      - Created a function for score counting
      - Created instruction screen
      - Created Scoreboard/Leaderboard screen

   ## Running The App

   File utama yang digunakan dalam program ini adalah SnakeGame.java

   1. **Open folder Final-Project-OOP**
   2. **Open terminal**
   3. **Compile class SnakeGame**
   ```
   javac src/SnakeGame.java
   ```
   4. **Run SnakeGame**
   ```
   java src/SnakeGame
   ```
   5. **Selamat bermain**

   ## Classes Used

   1. **SnakeGame (Main program)** - `SnakeGame.java` 
   - Main class program untuk menjalankan app

   2. **Snake** - `Snake.java` 
   - Class object Snake
   - 7 Variable Class
      - x: int[]
      - y: int[] 
      - movingLeft: bool
      - movingRight: bool
      - movingUp: bool
      - movingDown: bool 
      - joints: int
   - 15 Methods 
      - getSnakeX() - Untuk mengambil koordinat x Snake
      - getSnakeY() - Untuk mengambil koordinat y Snake
      - setSnakeX() - Untuk set koordinat x Snake
      - setSnakeY() - Untuk set koordinat y Snake
      - getJoints() - Untuk mengambil ukuran joint Snake
      - setJoints() - Untuk set ukura joint Snake
      - isMovingLeft() - Untuk mendapat nilai boolean jika Snake bergerak ke kiri
      - setMovingLeft() - Untuk set boolean Snake bergerak ke kiri
      - isMovingRight() - Untuk mendapat nilai boolean jika Snake bergerak ke kanan
      - setMovingRight() - Untuk set boolean Snake bergerak ke kanan
      - isMovingUp() - Untuk mendapat nilai boolean jika Snake bergerak ke atas
      - setMovingUp() - Untuk set boolean Snake bergerak ke atas
      - isMovingDown() - Untuk mendapat nilai boolean jika Snake bergerak ke bawah
      - setMovingDown() - Untuk set boolean Snake bergerak ke bawah
      - move() - Untuk menggerakan snake dari head ke tail 

   3. **Food** - `Food.java` 
   - Class object Food
   - 4 Variable Class
      - snake: Snake
      - foodX: int
      - foodY: int
      - RANDOMPOSITION = int 
   - 3 Methods 
      - getFoodX() - Untuk mengambil koordinat x Food
      - getFoodY() - Untuk mengambil koordinat y Food
      - createFood() - Untuk membuat Food baru dengan koordinat random

   4. **Board** - `Board.java` 
   - Class object Board. Merupakan area bermain, logic game, start screen, dan end screen yang mengekstensi `JPanel` dan mengimplementasi `ActionListener` untuk input keyboard. 
   - Class object Board
   - 8 Variable Class
      - BOARDWIDHT: int
      - BOARDHEIGHT: int
      - PIXELSIZE: int
      - TOTALPIXELS: int
      - inGame: bool
      - isFirstLaunch: bool
      - timer: Timer
      - speed: int
      - snake: Snake
      - food: Food
      - score: Score
   - 11 Methods
      - draw()- Untuk memunculkan objek yang ada di dalam game - Untuk memunculkan graphic yang telah ditentukan 
      - initializeGame() - Untuk memulai game
      - getAllDots() - Untuk mendapatkan TOTALPIXELS
      - checkFoodCollisions() - Untuk memeriksa apakah Food telah dimakan oleh Snake
      - checkCollisions - Untuk memeriksa apakah Snake menabrak dinding ataupun badannya sendiri
      - getDotSize() - Untuk mengambil ukuran pixel
      - endGame() - Untuk memberikan keadaan akhir game, jumlah score, leaderboard, dan end screen
      - instructionScreen() - Untuk membuat instruksi pada start screen
      - proximity() - Untuk menentukan luas area sentuh Snake dan Food
      - paintComponent() - Untuk memasukkan componen ke screen
      - actionPerformed() - Untuk memberikan record di console

   5. **Keys** - `Keys.java` 
   - Class Keyboard Listener
   - 1 method
      - keyPressed() - Untuk menginisiasi gerak yang akan diberikan kepada snake berdasarkan input keyboard

   ![UML](/images/UML.png)

   ## Notable Assumption and Design App Details

   - Ukuran board game 100x100 sel
   - Untuk memulai game tekan `spacebar`
   - Gunakan keyboard arrow atas, kiri, kanan, dan bawah untuk menggerakkan snake
   - Awal-awal snake memiliki 5 sel badan
   - Warna food merah dan snake hijau
   - Snake akan bertambah 1 sel saat memakan food
   - Game berhenti saat snake menabrak border board dan menabrak tubuhnya sendiri
   - Ada tampilan jumlah score food yang didapatkan diakhir game
   - Ada tampilan leaderboard diakhir game