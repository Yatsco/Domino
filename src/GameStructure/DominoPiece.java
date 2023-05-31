package GameStructure;

public class DominoPiece {
     int leftNum;
     int rightNum;

    public DominoPiece(int leftNum, int rightNum){

        this.leftNum=leftNum;
        this.rightNum=rightNum;


    }

    public int getLeftNum() {
        return leftNum;
    }

    public int getRightNum() {
        return rightNum;
    }

    @Override
    public String toString() {
        return "[ " + leftNum + " , "+ rightNum +" ]";
    }
    public boolean match(DominoPiece dominoOne,DominoPiece dominoTwo){
        if (dominoOne.leftNum==0){
            return true;
        }
        if (dominoOne.rightNum==0){
            return true;
        }
        if (dominoTwo.leftNum==0){
            return true;
        }
        if (dominoTwo.rightNum==0){
            return true;
        }
        if (dominoOne.leftNum==dominoTwo.leftNum){
            return true;
        }
        if (dominoOne.leftNum==dominoTwo.rightNum){
            return true;
        }
        if (dominoOne.rightNum==dominoTwo.leftNum){
            return true;
        }
        if (dominoOne.rightNum==dominoTwo.rightNum){
            return true;
        }

            return false;



    }
    public void flip(DominoPiece piece){

        int tempnum = piece.leftNum;
        piece.leftNum=rightNum;
        piece.rightNum=tempnum;

    }


}
