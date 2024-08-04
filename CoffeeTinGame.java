package a1_2201040070;
import java.util.Arrays;

/**
 * @overview A program that performs the coffee tin game on a
 *    tin of beans and display result on the standard output.
 *
 */

public class CoffeeTinGame {
    /** constant value for the green bean*/
    private static final char GREEN = 'G';
    /** constant value for the blue bean*/
    private static final char BLUE = 'B';
    /** constant for removed beans */
    private static final char REMOVED = '-';
    /** the null character*/
    private static final char NULL = '\u0000';


    public static final char[] BeansBag;


    static {
        BeansBag = new char[30];
        int beans;
        beans = BeansBag.length / 3;
        int blue = beans;
        int green = BeansBag.length - blue;
        for (int i = 0; i < BeansBag.length; i++) {
            if (i < blue) BeansBag[i] = BLUE;
            else if (i < green) BeansBag[i] = GREEN;
            else BeansBag[i] = REMOVED;
        }
    }

    /**
     * the main procedure
     * @effects
     *    initialise a coffee tin
     *    {@link TextIO#putf(String, Object...)}: print the tin content
     *    {@link @tinGame(char[])}: perform the coffee tin game on tin
     *    {@link TextIO#putf(String, Object...)}: print the tin content again
     *    if last bean is correct
     *      {@link TextIO#putf(String, Object...)}: print its colour
     *    else
     *      {@link TextIO#putf(String, Object...)}: print an error message
     */




    public static void main(String[] args) {
        char[][] tins = {
                {BLUE, BLUE, BLUE, GREEN, GREEN},
                {BLUE, BLUE, BLUE, GREEN, GREEN, GREEN},
                {GREEN},
                {BLUE},
                {BLUE, GREEN}
        };


        for (int i = 0; i < tins.length; i++) {
            char[] tin = tins[i];

            // expected last bean
            // p0 = green parity /\
            // (p0=1 -> last=GREEN) /\ (p0=0 -> last=BLUE)
            // count number of greens
            int greens = 0;
            for (char bean : tin) {
                if (bean == GREEN)
                    greens++;
            }
            // expected last bean
            final char last = (greens % 2 == 1) ? GREEN : BLUE;


            // print the content of tin before the game


            System.out.printf("%nTIN (%d Gs): %s %n", greens, Arrays.toString(tin));


            // perform the game
            // get actual last bean


            char lastBean = tinGame(tin);


            // lastBean = last \/ lastBean != last


            // print the content of tin and last bean


            System.out.printf("tin after: %s %n", Arrays.toString(tin));


            // check if last bean as expected and print


            if (lastBean == last) {
                System.out.printf("last bean: %c%n", lastBean);
            } else {
                System.out.printf("Oops, wrong last bean: %c (expected: %c)%n", lastBean, last);
            }
        }
    }


    /**
     * Performs the coffee tin game to determine the colour of the last bean
     *
     * @requires tin is not null /\ tin.length > 0
     * @modifies tin
     * @effects <pre>
     *   take out two beans from tin
     *   if same colour
     *     throw both away, put one blue bean back
     *   else
     *     put green bean back
     *   let p0 = initial number of green beans
     *   if p0 = 1
     *     result = `G'
     *   else
     *     result = `B'
     *   </pre>
     */




    public static char tinGame(char[] tin) {
        while (hasAtLeastTwoBeans(tin)) {
            // take two beans from tin
            char[] twoBeans = takeTwo(tin);
            // process beans to update tin
            updateTin(tin, twoBeans[0], twoBeans[1]);
        }
        return anyBean(tin);
    }


    public static int randInt(int n){
        return (int) (Math.random()*n);
    }

    public static char getBean(char[] beansBag, char beanType){
        char bean = NULL;
        for (int i = 0; i < beansBag.length; i++) {
            i = randInt(beansBag.length);
            bean = beansBag[i];
            if (bean == beanType) {
                beansBag[i] = REMOVED;
                break;
            }
        }
        return bean;
    }


    public static void updateTin(char[] tin, char beanOne, char beanTwo){
        if(beanOne == beanTwo) {
            putIn(tin, BLUE);
        } else{
            putIn(tin, GREEN);
        }
    }

    /**
     * @effects
     *  if tin has at least two beans
     *    return true
     *  else
     *    return false
     */



    private static boolean hasAtLeastTwoBeans(char[] tin) {
        int count = 0;
        for (char bean : tin) {
            if (bean != REMOVED) {
                count++;
            }


            if (count >= 2) // enough beans
                return true;
        }


        // not enough beans
        return false;
    }

    /**
     * @requires tin has at least 2 beans left
     * @modifies tin
     * @effects
     *  remove any two beans from tin and return them
     */

    public static char[] takeTwo(char[] tin) {
        char first = takeOne(tin);
        char second = takeOne(tin);


        return new char[]{first, second};
    }


    /**
     * tin has at least one bean
     * tin
     *   remove any bean from tin and return it
     */
    private static char takeOne(char[] tin) {
        for (int i = 0; i < tin.length; i++) {
            char bean = tin[i];
            if (bean != REMOVED) {  // found one
                tin[i] = REMOVED;
                return bean;
            }
        }


        // no beans left
        return NULL;
    }


    /**
     * @requires tin has vacant positions for new beans
     * @modifies tin
     * @effects
     *   place bean into any vacant position in tin
     */
    private static void putIn(char[] tin, char bean) {
        for (int i = 0; i < tin.length; i++) {
            if (tin[i] == REMOVED) { // vacant position
                tin[i] = bean;
                break;
            }
        }
    }


    /**
     * @effects
     *  if there are beans in tin
     *    return any such bean
     *  else
     *    return '\u0000' (null character)
     */
    private static char anyBean(char[] tin) {
        for (char bean : tin) {
            if (bean != REMOVED) {
                return bean;
            }
        }


        // no beans left
        return NULL;
    }
}

