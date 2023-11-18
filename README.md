# Wordle solver
Le contenu rédigé en français se trouve [ici](./doc/content.md)

migrated from Python 3.10.4 to Java 17

The algorithm in `./src/wordle/players/Bot.java` try to solve the Wordle game by eliminating each rounds every words that do not match the secret word's constraints. This is done by tracking and updating 5 distinct set containing all the possible letters at that position based on the previous word tested.

Last test output from `./src/BotStats.java` using FREQUENCY and UNIQUE options:
```
* TESTED: 2500  
* SOLVED: 2473  
* FAILED: 27    
* FAULTS: 0     
* RATE %: 98,92%
* ~TRIES: ~3,90
```