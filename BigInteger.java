import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class BigInteger {
    boolean positive;
    final int[] data;

//constructor
    public BigInteger(boolean positive, int[] data) {
        this.positive = positive;
        this.data = data;
    }

    public BigInteger(boolean sign, String s) {
        this.positive=sign;
        data = new int[200];
        int idx=0;
        for (int i = s.length() - 1; i >-1; i--) {
            data[idx] = Character.getNumericValue(s.charAt(i));
            idx++;
        }
    }


    public BigInteger(String s) {
        this.positive= true;
        data = new int[200];
        int idx=0;
        for (int i = s.length() - 1; i >-1; i--) {
            data[idx] = Character.getNumericValue(s.charAt(i));
            idx++;
        }
    }

    public BigInteger turn_opposite_operation (){
        return new BigInteger(!this.positive,this.data);
    }

    public BigInteger add(BigInteger right) {
        //System.out.println("=========");
        BigInteger answer;
        int[] result=new int[200];
        int temp;
        int carry=0;
        for(int i=data.length-1;i>-1;i--){
            temp=this.data[i]+right.data[i];
            if(temp>=10){
                result[i]=temp-10+carry;
                carry=1;
            }
            else{
                result[i]=temp+carry;
                carry=0;
            }
        }
        answer=new BigInteger(true,result);
       // System.out.println(answer.positive);
        //System.out.println(answer.data[110]);
        if(this.positive && right.positive){

            return answer;
        }
        else if(!this.positive && !right.positive) {
            return answer.turn_opposite_operation();
        }
        else{throw new IllegalArgumentException();}
    }

    public static int abc;
    public int compare(BigInteger right) {
        int idx = data.length - 1;
        for (; idx >= 0; --idx) {
            int lhs = this.data[idx],
                    rhs = right.data[idx];
            if (lhs == rhs) { continue; }

            if (lhs < rhs) { abc=1;return abc; }
            if (lhs > rhs) { abc=2;return abc; }
        }
        abc=0;
        return abc;
    }

    public BigInteger sub(BigInteger right){
        //System.out.println("==SUB===");
        if (this.compare(right)==0) { return new BigInteger("0"); }
        if (this.compare(right)==1) { return (right.sub(this)).turn_opposite_operation(); }
        BigInteger answer;
        int temp;
        int carry=0;
        int[] result=new int[200];
        for(int j=0;j<result.length;j++){
            temp=this.data[j]-right.data[j]-carry;
            if(temp<0){
                temp+=10;
                carry=1;}
            else{carry=0;}
            result[j]=temp;}
        answer=new BigInteger(true,result);
        return answer;
    }

    public BigInteger mul(BigInteger right) {
        int ab=0;int bc=0;
        for(int i=0;i<this.data.length;i++){
            if(this.data[i]==0){ab++;}
        }
        for(int i=0;i<right.data.length;i++){
            if(right.data[i]==0){bc++;}
        }
        if(ab==this.data.length || bc==right.data.length){
            return new BigInteger("0");
        }

        int[] result = new int[200];
        int carry = 0;
        BigInteger answer;
        boolean g;
        for (int j = 0; j < result.length; j++) {
            int sum = carry;
            for (int i = 0; i <= j; i++) {
                sum += this.data[i]*right.data[j - i];
            }
            carry = sum / 10;
            result[j] = (sum % 10);
        }
        if(this.positive&& right.positive||!this.positive&& !right.positive){g=true;}
        else{g=false;}
        answer=new BigInteger(g,result);
        //System.out.println(this.positive);
        //System.out.println(right.positive);
        //System.out.println(answer.positive);
        return answer;
    }

    @Override
    public String toString() {
        int idx = data.length - 1;
       for (idx = data.length - 1; idx >= 0 && data[idx] == 0; idx--) {}
        StringBuffer buffer_ = new StringBuffer();
        for (idx = data.length - 1; idx > -1; idx--) { buffer_.append(data[idx]); }
        int i=0;
        while (buffer_.charAt(i)==0 ||buffer_.charAt(i)=='0'){
            i++;
            if(i==200){buffer_.append("0");break;}
        }
        //System.out.println("버퍼"+buffer_);
        //System.out.println("자리"+i);
        buffer_.delete(0,i);
        if (positive==false) { buffer_.insert(0,'-'); }
        return buffer_.toString();
    }

    private static final Pattern regex = Pattern.compile("([+-]?)(\\d+)([+\\-*])([+-]?)(\\d+)");

    static BigInteger evaluate(String input) throws IllegalArgumentException {
        input = input.trim();
        boolean aa,bb;
        input = input.replace(" ", "");
        Matcher match = regex.matcher(input);
        if (!match.matches()) {
            throw new IllegalArgumentException();
        }
        String left_sign = match.group(1).trim();
        //System.out.println("left_sign"+left_sign);
        String operator = match.group(3).trim();
        //System.out.println("operator"+operator);
        String right_sign = match.group(4).trim();
        //System.out.println("right_sign"+right_sign);
        final char operation = operator.charAt(0);
        if (left_sign.length() == 0) {
            left_sign="+";}
        if (left_sign=="+"||left_sign.charAt(0)=='+'){
            aa=true;
        }
        else if(left_sign=="-"||left_sign.charAt(0)=='-'){
            aa=false;
        }
        else{throw new IllegalArgumentException();}
        if (right_sign.length() == 0) {
            right_sign="+";}
        if (right_sign=="+"||right_sign.charAt(0)=='+'){
            bb=true;
        }
        else if(right_sign=="-"||right_sign.charAt(0)=='-'){
            bb=false;
        }
        else{throw new IllegalArgumentException();}
        BigInteger lhs = new BigInteger(aa, match.group(2));;
        BigInteger rhs = new BigInteger(bb, match.group(5));;
        //System.out.println("finalleft_sign"+lhs.positive);
        //System.out.println("finalrright_sign"+rhs.positive);
        //System.out.println(operation);
        switch (operation) {
            case '+':
                if (aa && bb) {
                    //System.out.println("========");
                    return lhs.add(rhs);
                } else if (!aa && bb) {
                    //System.out.println("========");
                    lhs.turn_opposite_operation();
                    return rhs.sub(lhs);
                } else if (aa&& !bb) {
                    //System.out.println("========");
                    rhs.turn_opposite_operation();
                    return lhs.sub(rhs);
                } else if (!aa &&!bb) {
                    //System.out.println("========");
                    return lhs.add(rhs);
                }
                //be sure that it needs turn in to opposite sign
                else {throw new IllegalArgumentException();}
            case '-':
                if (aa && bb) {
                    return lhs.sub(rhs);
                } else if (aa && !bb) {
                    rhs.turn_opposite_operation();
                    return lhs.add(rhs);
                } else if (!aa && bb) {
                    rhs.turn_opposite_operation();
                    //System.out.println("========");
                    return lhs.add(rhs);}
                //be sure that it needs turn in to opposite sign
                else if (!aa && !bb) {
                    lhs.turn_opposite_operation();
                    rhs.turn_opposite_operation();
                    //System.out.println("========");
                    return rhs.sub(lhs);
                } else {throw new IllegalArgumentException();}
            case '*':
                    return lhs.mul(rhs);
            default:
                throw new IllegalArgumentException();
        }
    }



    public static void main(String[] args) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(System.in)) {
            try (BufferedReader reader = new BufferedReader(isr)) {
                try { while (processInput(reader.readLine())) { } }
                catch (IllegalArgumentException e) {
                    System.err.println("입력이 잘못되었습니다.");
                    System.exit(1);
                }
            }
        }
    }

    static boolean processInput(String input) throws IllegalArgumentException {
        if (isQuitCmd(input)) { return false; }
        BigInteger result = evaluate(input);
        System.out.println(result.toString());
        return true;
    }

    static boolean isQuitCmd(String input) {
        if (input == null) { return true; }
        return input.equalsIgnoreCase("quit");
    }
}