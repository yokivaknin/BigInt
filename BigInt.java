import java.util.ArrayList;

//The class BigInt has a property of an ArrayList of integers that holds in each cell a digit of the big number,
// and a property isPositive that describes whether the number is positive or negative.
//the class implements basic arithmetical operations.
public class BigInt implements Comparable <BigInt> {

    private ArrayList <Integer> _digits;
    private boolean _isPositive =true;

    //Constructor.
    public BigInt(String numString){

        // initialize digits ArrayList
        _digits = new ArrayList<Integer>();

        // Empty string is invalid.
        if(numString.equals(""))
        {
            throw new IllegalArgumentException("Invalid number");
        }

        // set sign
        if (numString.charAt(0) == '-') {
            _isPositive = false;
            numString = numString.substring(1);
        } else if(numString.charAt(0) == '+'){
                _isPositive = true;
                numString = numString.substring(1);
        }

        if (numString.charAt(0) == '0' && numString.length() > 1){ // removing leading zeros.
            while (numString.charAt(0) == '0' && numString.length() > 1){
                numString = numString.substring(1);
            }
        }

        // if the number is zero mark it positive regardless from the sign.
        if(numString.length() == 1 && numString.charAt(0) == '0'){
            _isPositive = true;
            _digits.add(0);
        }
        else {
            // convert string to digits ArrayList
            for (int i = 0; i < numString.length(); i++) {
                char c = numString.charAt(i);
                if (!Character.isDigit(c)) {
                    throw new IllegalArgumentException("Invalid number");
                }
                _digits.add(Character.getNumericValue(c));
            }
        }
    }

    //Copy constructor for internal needs.
    protected BigInt(BigInt other) {
        this._digits = new ArrayList<Integer>(other._digits);
        this._isPositive = other._isPositive;
    }

    public BigInt plus(BigInt other) {

        BigInt result = new BigInt("0");
        ArrayList<Integer> resultAbs = new ArrayList<Integer>();

        int carry = 0;
        int i = this._digits.size() - 1;
        int j = other._digits.size() - 1;

        if(this._isPositive == true && other._isPositive == true){
            result._isPositive = true;
        }else if(this._isPositive == false && other._isPositive == false){
            result._isPositive = false;
        }else if(this._isPositive == true && other._isPositive == false){
            BigInt otherSwitchSign = new BigInt(other);
            otherSwitchSign ._isPositive = true;
            return new BigInt(this.minus(otherSwitchSign));
        }else if(this._isPositive == false && other._isPositive == true){
            BigInt thisSwitchSign = new BigInt(this);
            thisSwitchSign._isPositive = true;
            BigInt toReturn = new BigInt("0");
            toReturn._digits = thisSwitchSign.minus(other)._digits;
            toReturn._isPositive = false;
            return toReturn;
        }

        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) {
                sum += this._digits.get(i);
                i--;
            }
            if (j >= 0) {
                sum += other._digits.get(j);
                j--;
            }
            resultAbs.add(0, sum % 10);
            carry = sum / 10;
        }
        result._digits = resultAbs;

        return new BigInt(result);
    }

    public BigInt minus (BigInt other) {
        if (this._isPositive == true && other._isPositive == false) {
            BigInt otherSwitchSign = new BigInt(other);
            otherSwitchSign._isPositive = true;
            return new BigInt(this.plus(otherSwitchSign));
        } else if (this._isPositive == false && other._isPositive == true) {
            BigInt thisSwitchSign = new BigInt(this);
            thisSwitchSign._isPositive = true;
            BigInt toReturn = new BigInt(thisSwitchSign.plus(other));
            toReturn._isPositive = false;
            return toReturn;
        } else if (this._isPositive == true && other._isPositive == true) {
            if (this.compareTo(other) < 0) {
                BigInt toReturn = other.minus(this);
                toReturn._isPositive = false;
                return toReturn;
            } else {//Both numbers are positive and this number is bigger or equal to the other number.

                BigInt toReturn = new BigInt("0");
                ArrayList<Integer> result = new ArrayList<Integer>();

                int borrow = 0;
                int i = this._digits.size()-1;
                int j =other._digits.size() - 1;

                while (i >= 0 || j >= 0) {
                    int diff = borrow;
                    if (i >= 0) {
                        diff += this._digits.get(i);
                    }
                    if (j >= 0) {
                        diff -= other._digits.get(j);
                    }
                    if (diff < 0) {
                        diff += 10;
                        borrow = -1;
                    } else {
                        borrow = 0;
                    }
                    result.add(diff);//Adding the digits in the opposite order.
                    i--;
                    j--;
                }

                // Reversing the digits order to the correct order.
                ArrayList<Integer> correctResult = new ArrayList<Integer>();
                for(int k = result.size()-1; k > -1; k--){
                    correctResult.add(result.get(k));
                }

                // Remove leading zeros
                while (correctResult.size() > 1 && correctResult.get(0) == 0) {
                    correctResult.remove(0);
                }
                toReturn._digits = correctResult;
                return toReturn;
            }
        } else { // both numbers are negative
            BigInt otherAbs = new BigInt(other);
            otherAbs._isPositive = true;
            return new BigInt(this.plus(otherAbs));
        }
    }

    //Returns true when the number is zero and false otherwise.
    private boolean isZero(){
        if(_digits.size() == 1 && _digits.get(0) == 0)
            return true;
        return false;
    }

    public BigInt multiply(BigInt other) {

        //If at list one of the numbers is zero.
        if(this.isZero() == true || other.isZero() == true)
            return new BigInt("0");

        // determine sign of result
        boolean resultIsPositive ;
        if(this._isPositive == other._isPositive)
            resultIsPositive = true;
        else
            resultIsPositive = false;

        // initialize result array with zeros
        int[] resultDigits = new int[this._digits.size() + other._digits.size()];
        for (int i = 0; i < resultDigits.length; i++) {
            resultDigits[i] = 0;
        }

        // multiply digits of both numbers
        for (int i = this._digits.size() - 1; i >= 0; i--) {
            int carry = 0;
            for (int j = other._digits.size() - 1; j >= 0; j--) {
                int product = this._digits.get(i) * other._digits.get(j) + carry + resultDigits[i + j + 1];
                resultDigits[i + j + 1] = product % 10;
                carry = product / 10;
            }
            resultDigits[i] += carry;
        }

        // remove leading zeros
        int startIndex = 0;
        while (startIndex < resultDigits.length - 1 && resultDigits[startIndex] == 0) {
            startIndex++;
        }

        // construct result BigInt from result array
        BigInt result = new BigInt("0");
        ArrayList<Integer> resultDigitsList = new ArrayList<Integer>();

        for (int i = startIndex; i < resultDigits.length; i++) {
            resultDigitsList.add(resultDigits[i]);
        }
        result._digits = resultDigitsList;
        result._isPositive = resultIsPositive;

        return result;
    }

    public BigInt divide(BigInt other) {

        //Check deviation by zero.
        if (other.equals(new BigInt("0"))) {
            throw new ArithmeticException("Division by zero is not allowed!");
        }

        //Check result sign.
        boolean resultSignIsPositive;
        if (_isPositive != other._isPositive)
            resultSignIsPositive = false;
        else
            resultSignIsPositive = true;

        //Creating the absolute of the two given numbers.
        BigInt ABSThis = new BigInt(this);
        ABSThis._isPositive = true;
        BigInt ABSOther = new BigInt(other);
        ABSOther._isPositive = true;

        //If the dividend is less than the divisor, return zero.
        if (ABSThis.compareTo(ABSOther) < 0) {
            return new BigInt("0");
        }

        //If the numbers are equal the deviation returns 1 or -1.
        if (ABSThis.equals(ABSOther)) {
            if (resultSignIsPositive) {
                return new BigInt("1");
            } else {
                return new BigInt("-1");
            }
        }

        BigInt quotient = new BigInt("0");

        while (ABSThis.compareTo(ABSOther) >= 0) {
            BigInt tmp = new BigInt(ABSOther);
            BigInt multiple = new BigInt("1");
            while (ABSThis.compareTo(tmp.plus(tmp)) >= 0) {
                tmp = tmp.plus(tmp);
                multiple = multiple.plus(multiple);
            }
            ABSThis = ABSThis.minus(tmp);
            quotient = quotient.plus(multiple);
        }

        quotient._isPositive = (this._isPositive == other._isPositive);

        return quotient;
    }

    public boolean equals(Object other){

        boolean isTheSameDigits = true;

        if(other instanceof BigInt){
            if(_isPositive != ((BigInt) other)._isPositive || _digits.size() != ((BigInt)other)._digits.size())
                return false;
            for(int i = 0; i< _digits.size(); i++){
                if(_digits.get(i) != ((BigInt)other)._digits.get(i)) {
                    isTheSameDigits = false;
                    break;
                }
            }
        }
        else
            return false;

        return isTheSameDigits;
    }

    public int compareTo(BigInt other) {

        //Different sign
        if(this._isPositive != other._isPositive){
            if(this._isPositive == true)
                return 1;
            else
                return -1;
        }

        int len1 = this._digits.size();
        int len2 = other._digits.size();

        if (len1 != len2) {
            // if lengths are different, the number with more digits is greater
            return (len1 > len2) ? 1 : -1;
        }

        // compare digits starting from the most significant digit
        for (int i = 0; i < len1; i++) {
            int digit1 = this._digits.get(i);
            int digit2 = other._digits.get(i);
            if (digit1 != digit2) {
                // if digits are different, return the result of their comparison
                return (digit1 > digit2) ? 1 : -1;
            }
        }
        // if all digits are equal, the numbers are equal
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Add sign to string
        if (!_isPositive) {
            sb.append("-");
        }

        // Add digits to string
        for (int digit : _digits) {
            sb.append(digit);
        }

        // If BigInt is zero, append a single zero digit
        if (_digits.size() == 0) {
            sb.append("0");
        }

        return sb.toString();
    }
}
