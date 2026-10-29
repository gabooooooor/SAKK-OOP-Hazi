# Chess move validator (b-16)

A console chess referee: given a position and a move such as `e2 e4`, it decides whether the move is legal. It is not a chess engine, it does not play.

## How to run
1. Open the project in NetBeans (Java with Ant, source folder `src`, test folder `test`) and run `sakk.Main`, or from a terminal in the repo root:
   `javac -d out $(find src -name "*.java")` then `java -cp out sakk.Main`
2. Type moves as two squares, e.g. `e2 e4`. Type `exit` to quit.
3. Tests: run the test project in NetBeans (JUnit 4.13.2 and Hamcrest).

## Design
- `Figura` is abstract, every piece knows its own movement (`lephet`), so there is no switch on piece type. Sliding pieces (queen, rook, bishop) share one helper (`csuszik`), king and knight use a list of fixed offsets (`fixLepes`).
- `Mezo` is an immutable final class (not a record, because the project targets Java 11), `Mezo.of("e4")` throws on invalid input.
- `Tabla` holds the 8x8 grid and can copy itself (`masolat`).
- `Szabaly` checks legality; to forbid moving into check it plays the move on a copy of the board and tests whether the own king is attacked.
- `Jatek` holds the game state (side to move, en passant target, which squares have been touched) and handles the special moves.

## Supported rules
All normal piece movement, captures, blocked paths, not leaving or moving the own king into check, castling (both sides: king and rook unmoved, empty squares between, king not in check and not passing or landing on an attacked square), en passant, pawn promotion (queen, rook, bishop or knight).

## Known gaps
No checkmate, stalemate, draw rules (50 moves, repetition, insufficient material). The special rules use `instanceof` because they depend on the exact piece type.

## AI usage
I used Claude as a tutor. It did not write my code. It guided me step by step: it explained the concepts, gave me skeleton code with TODO comments and the key line as a comment, and reviewed the code I sent back with targeted corrections.