import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class ArithmeticEncoding {
    private class Range {
        public Double start, end;
        Range(Double start, Double end){
            this.start = start;
            this.end = end;
        }
    }

    private class KeyRange extends Range{
        public Character key;
        KeyRange(Character key, Double start, Double end){
            super(start, end);
            this.key = key;
        }
    }

    final Character END_OF_STREAM = '\0';

    HashMap<Character, Range> cumulativeRanges;

    ArithmeticEncoding(String probFile) {
        this.cumulativeRanges = new HashMap<>();
        try {
            // Read probability file
            // Values as Char - Probability
            Scanner reader = new Scanner(new FileInputStream(probFile));
            Double last = 0.0;
            while(reader.hasNextLine()){
                String tag = reader.next();
                Character ch = null;
                if(tag.equals("EOF")){
                    ch = END_OF_STREAM;
                }else{
                    ch = tag.charAt(0);
                }
                Double value = reader.nextDouble();
                this.cumulativeRanges.put(ch, new Range(last, last+value));
                last += value;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Double encode(String input){
        Double low = 0.0, high = 0.0, range = 1.0;
        input += END_OF_STREAM;
        for(int i = 0; i < input.length(); ++i){
            // Find range 
            final Character current = input.charAt(i);
            Range curRange = this.cumulativeRanges.get(current);
            high = low + range * curRange.end;
            low = low + range * curRange.start;
            range = high - low;
        }
        // Find best value representable in base 2
        Double value = 0.0;
        int k = 1;
        while(value < low){
            final Double bitVal = Math.pow(2, -k);
            value += bitVal;
            if(value > high){
                value -= bitVal;
            }
            k++;
        }
        return value;
    }

    String decode(Double input){
        String output = "";
        ArrayList<KeyRange> dynamicRanges = new ArrayList<>();
        // Initialize dynamicRanges with original ranges
        for(Character k : this.cumulativeRanges.keySet()){
            Range original = this.cumulativeRanges.get(k);
            dynamicRanges.add(new KeyRange(k, original.start, original.end));
        }
        Character current = null;
        while(current != END_OF_STREAM){
            // find value in current ranges
            KeyRange curRange = null;
            for(KeyRange range : dynamicRanges){
                if(input >= range.start && input < range.end){
                    curRange = range;
                    break;
                }
            }
            current = curRange.key;
            if(current == END_OF_STREAM) break;
            output += current;
            // Update dynamic ranges
            Double low = curRange.start;
            Double range = curRange.end - low;
            for(KeyRange dRange : dynamicRanges){
                Range original = this.cumulativeRanges.get(dRange.key);
                dRange.end = low + range * original.end;
                dRange.start = low + range * original.start;
            }
        }
        return output;
    }
}