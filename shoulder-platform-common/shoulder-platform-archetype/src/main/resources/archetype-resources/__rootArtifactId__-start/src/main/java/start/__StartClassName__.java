#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ${appId} 启动类
 *
 * @author ${author}
 */
@SpringBootApplication(scanBasePackages = "${package}")
public class ${StartClassName} {

    public static void main(String[] args) {
        // printColorfulShoulderBanner();
        SpringApplication.run(${StartClassName}.class, args);
    }

    /**
     * shoulder 前缀的彩色启动 banner
     */
    private static void printColorfulShoulderBanner(){
        // todo origin 复制自 http://patorjk.com/software/taag/#p=display&f=Ivrit&t=${rootArtifactId}
        String origin =
        "";

        String prefix1 = "${AnsiColor.CYAN} ____  _                 _     _           ${AnsiColor.BRIGHT_YELLOW} ";
        String prefix2 = "${AnsiColor.CYAN}/ ___|| |__   ___  _   _| | __| | ___ _ __ ${AnsiColor.BRIGHT_YELLOW} ";
        String prefix3 = "${AnsiColor.CYAN}\\___ \\| '_ \\ / _ \\| | | | |/ _` |/ _ \\ '__|${AnsiColor.BRIGHT_YELLOW} ";
        String prefix4 = "${AnsiColor.CYAN} ___) | | | | (_) | |_| | | (_| |  __/ |   ${AnsiColor.BRIGHT_YELLOW} ";
        String prefix5 = "${AnsiColor.CYAN}|____/|_| |_|\\___/ \\__,_|_|\\__,_|\\___|_|   ${AnsiColor.BRIGHT_YELLOW} ";
        String suffix1 = "${AnsiColor.CYAN} __   __    __";
        String suffix2 = "${AnsiColor.CYAN} \\ \\  \\ \\   \\ \\";
        String suffix3 = "${AnsiColor.CYAN}  \\ \\  \\ \\   \\ \\";
        String suffix4 = "${AnsiColor.CYAN}  / /  / /   / /";
        String suffix5 = "${AnsiColor.CYAN} / /  / /   / /";


        List<String> banner = List.of(origin.split("\n"));
        // line1
        System.out.print(prefix1);
        System.out.print(banner.get(0));
        System.out.println(suffix1);
        // line2
        System.out.print(prefix2);
        System.out.print(banner.get(1));
        System.out.println(suffix2);
        // line3
        System.out.print(prefix3);
        System.out.print(banner.get(2));
        System.out.println(suffix3);
        // line4
        System.out.print(prefix4);
        System.out.print(banner.get(3));
        System.out.println(suffix4);
        // line5
        System.out.print(prefix5);
        System.out.print(banner.get(4));
        System.out.println(suffix5);
        // line6
        printUnderLine(banner.get(5));
        }

    private static void printUnderLine(String line6){
        String prefix6 = "${AnsiColor.CYAN}===========================================";
        String suffix6 = "=/_/==/_/===/_/";

        String pre = "${AnsiColor.BRIGHT_YELLOW}";
        String suf = "${AnsiColor.CYAN}";
        char[] chars = line6.toCharArray();
        System.out.print(prefix6);
        boolean noChar = true;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if(c == ' '){
                System.out.print(noChar ? "=" : "${AnsiColor.CYAN}=");
                noChar = true;
            }else {
                System.out.print(noChar ? (pre + c) : c);
                noChar = false;
            }
        }
        System.out.println(suffix6);
    }

}
