import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HackerRank {

    @Test
    void JavaDataTypes() {

        String in = new Scanner(System.in).nextLine();
        String out;
        final int count = Integer.parseInt(in);
        for (int i = 0; i < count; i++)
            try {
                in = new Scanner(System.in).nextLine();
                long l = Long.parseLong(in);
                out = "\n* long";
                if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
                    out = "\n* int" + out;
                    if (l >= Short.MIN_VALUE && l <= Short.MAX_VALUE) {
                        out = "\n* short" + out;
                        if (l >= Byte.MIN_VALUE && l <= Byte.MAX_VALUE) {
                            out = "\n* byte" + out;
                        }
                    }
                }
                out = in + " can be fitted in:" + out;
                System.out.println(out);
            } catch (NumberFormatException exception) {
                System.out.println(in + " can't be fitted anywhere.");
            }

    }


    @Test
    void JavaEndOfFile() {

        Scanner sc = new Scanner(System.in);

        for (int i = 0; sc.hasNext(); i++) {
            String line = sc.nextLine();
            System.out.println(i + ". " + line);
        }

    }

    @Test
    void JavaStaticInitializerBlock() {

        Scanner sc = new Scanner(System.in);

        int b = sc.nextInt();
        sc.nextLine();
        int h = sc.nextInt();

        if (b <= 0 || h <= 0) {
            System.out.println("java.lang.Exception: Breadth and height must be positive");
        } else {
            System.out.println(b * h);
        }

    }

    @Test
    void intToString() {

        int i = new Scanner(System.in).nextInt();

        if (i >= -100 || i <= 100) {
            System.out.println("Good job");
        } else {
            System.out.println("Wrong answer");
        }

    }

    @Test
    void JavaDateandTime() {

        int year = 2021;
        int month = 11;
        int day = 14;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String dayOfWeekString;
        switch (dayOfWeek) {
            case 1 -> dayOfWeekString = "Sunday";
            case 2 -> dayOfWeekString = "Monday";
            case 3 -> dayOfWeekString = "Tuesday";
            case 4 -> dayOfWeekString = "Wednesday";
            case 5 -> dayOfWeekString = "Thursday";
            case 6 -> dayOfWeekString = "Friday";
            case 7 -> dayOfWeekString = "Saturday";
            default -> dayOfWeekString = null;
        }

        System.out.println(dayOfWeekString.toUpperCase());

    }


    @Test
    void JavaCurrencyFortmatter() {

        double payment = 12324.134;

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.println("US: " + nf.format(payment));

        Locale india = new Locale("en", "in");
        nf = NumberFormat.getCurrencyInstance(india);
        System.out.println("India: " + nf.format(payment));

        nf = NumberFormat.getCurrencyInstance(Locale.CHINA);
        System.out.println("China: " + nf.format(payment));

        nf = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        System.out.println("France: " + nf.format(payment));

        String s = "093";
//        String pattern = "[2]*[5][0-5]|[2]*[0-4]\\d|[0-1]\\d\\d|\\d\\d|\\d";
        String one = "[2]*[5][0-5]|[2]*[0-4]\\d|[0-1]\\d{2}|\\d{1,2}";

        Pattern p = Pattern.compile(one,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(one);


        String pattern = one + "[.]" + one + "[.]" +one + "[.]" +one;
        assertTrue("093".matches(one));
        assertTrue("93".matches(one));
        assertTrue("3".matches(one));

        assertFalse("0001".matches(one));
        assertFalse("256".matches(one));
        assertFalse("-123".matches(one));


    }


    @Test
    void ingatlanCom() throws URISyntaxException, IOException, InterruptedException {

        String url = "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3";
//        String url = "https://datahub.io/core/country-list";
//        String url = "https://www.nationsonline.org/oneworld/country_code_list.htm";

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

    }

}
