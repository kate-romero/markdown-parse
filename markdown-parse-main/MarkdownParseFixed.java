// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParseFixed {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        String[] linkArray = markdown.split("\n");
        for(String link: linkArray) {
            String[] inLineLinksArray = link.split(" ");
            for(String inLineLink: inLineLinksArray) {
                int currentIndex = 0;
                while(currentIndex < inLineLink.length()) {
                    int imageIndex = inLineLink.indexOf("!", currentIndex);
                    int nextOpenBracket = inLineLink.indexOf("[", currentIndex);
                    int nextCloseBracket = inLineLink.indexOf("]", nextOpenBracket);
                    int openParen = inLineLink.indexOf("(", nextCloseBracket);
                    int closeParen = inLineLink.lastIndexOf(")");
    
                    if(nextOpenBracket == -1 || nextCloseBracket == -1 || openParen == -1 || closeParen == -1) {
                        break;
                    }
    
                    if (imageIndex != -1 && imageIndex == nextOpenBracket - 1) {
                        currentIndex = closeParen + 1;
                    } else {
                        toReturn.add(inLineLink.substring(openParen + 1, closeParen));
                        currentIndex = closeParen + 1;
                    }
                }
            }
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("No file name given");
            return;
        }
        
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}