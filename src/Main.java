import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<List<String>> lists = initLists();
        startGame(lists);
    }

    public static List<List<String>> initLists(){
        List<List<String>> lists = new ArrayList<>();

        List<String> animals = new ArrayList<>();
        animals.add("aaaaaaaaaaaaaa");
        animals.add("bbbbbbbbbbbbbbb");
        animals.add("cccccccccccccccccc");
        animals.add("dddddddddddddddddd");
        lists.add(animals);

        List<String> countries = new ArrayList<>();
        countries.add("h");
        countries.add("zz");
        countries.add("hj");
        countries.add("lutf");
        lists.add(countries);

        return lists;
    }

    public static void play(String word, int lives) {
        List<Character> goodGuesses = new ArrayList<>();
        List<Character> badGuesses = new ArrayList<>();
        char[] wordArray = stringToCharArray(word);
        displayWord(wordArray, goodGuesses);

        while (true) {
            String guess = guessInput(goodGuesses, badGuesses);
            if(guess.equals("quit")) {
                System.out.println("Goodbye!");
                return;
            }
            if(isItGoodGuess(wordArray, guess)) {
                goodGuesses.add(guess.toUpperCase().charAt(0));
                goodGuesses.add(guess.toLowerCase().charAt(0));
                if(isItSolved(wordArray, goodGuesses)){
                    displayWord(wordArray, goodGuesses);
                    System.out.println("Congratulations, You've just solved!");
                    return;
                }
            }
            else {
                System.out.println("\n Wrong guess :(");
                badGuesses.add(guess.toLowerCase().charAt(0));
                badGuesses.add(guess.toUpperCase().charAt(0));
                displayWrongLetters(badGuesses);
                lives--;
                if(lives == 0) {
                    System.out.println("Game over!");
                    return;
                }
            }
            displayWord(wordArray, goodGuesses);
            System.out.println("Lives: " + lives);
        }
    }

    public static char[] stringToCharArray(String word){
        char[] wordCharsArray = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            wordCharsArray[i] = word.charAt(i);
        }
        return wordCharsArray;
    }

    public static void displayWord(char [] word, List<Character> goodGuesses){
        boolean guessed;
        for(int i = 0; i < word.length; i++){
            guessed = false;
            for(int j = 0; j < goodGuesses.size(); j++){
                if(word[i] == goodGuesses.get(j)){
                    System.out.print(word[i] + " ");
                    guessed = true;
                }
            }
            if(!guessed)
                System.out.print("_ ");
        }
        System.out.println("\n");
    }

    public static void displayWrongLetters(List<Character> badGuesses){
        System.out.println("Wrong guesses: " + badGuesses);
    }

    public static String guessInput(List<Character> goodGuesses, List<Character> badGuesses){
        Scanner input = new Scanner(System.in);
        String guess;
        do {
            System.out.println("Guess a letter!");
            guess = input.next();
            if(guess.equals("quit"))
                return "quit";
        }while(isItAlready_a_guess(guess, goodGuesses, badGuesses));
        return guess;
    }

    public static boolean isItAlready_a_guess(String guess, List<Character> goodGuesses, List<Character> badGuesses){
        char lower = guess.toLowerCase().charAt(0);
        char upper = guess.toUpperCase().charAt(0);

        if(goodGuesses.contains(lower) || badGuesses.contains(lower) || goodGuesses.contains(upper) || badGuesses.contains(upper)) {
            System.out.println("You have already guessed this letter!");
            if (badGuesses.contains(lower) || badGuesses.contains(upper))
                displayWrongLetters(badGuesses);
            return true;
        }
        return false;
    }

    public static boolean isItGoodGuess(char[] word, String guess){
        char lower = guess.toLowerCase().charAt(0);
        char upper = guess.toUpperCase().charAt(0);

        for(char c : word){
            if(c == lower || c == upper)
                return true;
        }
        return false;
    }

    public static boolean isItSolved(char[] word, List<Character> goodGuesses){
        LinkedHashSet<Character> wordSet = new LinkedHashSet<>();
        LinkedHashSet<Character> goodGuessesSet = new LinkedHashSet<>(goodGuesses);
        for (char c : word)
            wordSet.add(c);
        return goodGuessesSet.containsAll(wordSet);
    }

    public static void displayMenu() {
        System.out.println("Choose a level!\n" +
                "1. Level 1\n" +
                "2. Level 2\n" +
                "3. Exit");
    }

    public static void startGame(List<List<String>> lists){
        displayMenu();
        Random random = new Random();

        Scanner input = new Scanner(System.in);
        int menu = input.nextInt();

        switch (menu) {
            case 1:
                System.out.println("Animal");
                play(lists.get(0).get(random.nextInt(lists.get(0).size())), 5);
                break;
            case 2:
                System.out.println("Country");
                play(lists.get(1).get(random.nextInt(lists.get(1).size())), 3);
                break;
            case 3:
                System.out.println("Goodbye");
                System.exit(1);
            default:
                System.out.println("Invalid input");
        }
    }
}