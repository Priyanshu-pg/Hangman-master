# Hangman Android App
This repository in an Android app for single players. The game is played among two players, the app will automate one player. The app generates a word to be guessed by the player and will win/lose based on whether he was able to guess the word.

## Hangman game
Hangman is a paper and pencil guessing game for two or more players. One player thinks of a word and the other tries to guess it by suggesting the letters.The word to guess is represented by a row of dashes, giving the number of letters. If the guessing player suggests a letter which occurs in the word, the program writes it in all its correct positions. If the suggested letter does not occur in the word, the other player draws one element of the hangman diagram as a tally mark. The game is over when: The guessing player completes the word, or guesses the whole word correctly.

## Features:
* Help page: Introduces new-bies to the rules of game.
* Category based words: The user can choose a category of words in coming games. To maintain category based words, words list has been stored in SQLite atabase for future scaling of other categories and difficulty level.
* Hint: The user can ask for hint of the word being guessed. Each word stored in database has been mapped with a hint. 
* Scoreboard: Each game plyed by the user is scored. For every correct guess +5 is given and for using hint, -5 is deducted. If  player chooses to play again, previous score is incremented to curret score. Using Shared prefrences, highest score of player is also maintained.

## Future Work:
* Add suppoort for multiplayer gaming in real time.
* Improve UI with animations.
* Add difficulty level for words based on number of people who succeed in guessing the answer.





