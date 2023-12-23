
import java.util.HashSet;
import java.util.Random;

public class MontyHall {
    Integer[] boxes = new Integer[3];

    public static void main(String[] args){
        MontyHall mh = new MontyHall();
        mh.play(10000000);
    }

    public void play(int playNumberOfTimes){
        double won = 0;
        double lost = 0;
        int index = 0;

        while (index < playNumberOfTimes + 1){
            int valg = chooseRandom();
            putElementInList();
            int ikkePremie = chooseAwayNull(valg);
            int switchedChoice = switchChoice(valg, ikkePremie);
            if(checkBox(switchedChoice)){
                won++;
            }
            else{
                lost++;
            }

            index++;

            emptyList();

        }
        
        System.out.println((won / playNumberOfTimes) * 100 + "%");
    }

    public int chooseAwayNull(int notThisOne){
        for(int i = 0; i < boxes.length; i++){
            if(boxes[i] == null && i != notThisOne){
                return i;
            }
        }
        return 0;
    }

    public boolean checkBox(int chosenBox){
        if(boxes[chosenBox] != null){
            return true;
        }
        return false;
    }
    public int switchChoice(int alreadyChosen, int boxTakenAway){
        HashSet<Integer> tempSet = new HashSet<>();
        tempSet.add(0);
        tempSet.add(1);
        tempSet.add(2);
        tempSet.remove(alreadyChosen);
        tempSet.remove(boxTakenAway);

        return tempSet.iterator().next();
    }
    public int chooseRandom(){
        Random rand = new Random();
        return rand.nextInt(3);
    }

    public int putElementInList(){
        Random rand = new Random();
        int random = rand.nextInt(3); //Får et tilfeldig nummer(index)
        boxes[random] = 1;
        return random;
    }

    public void emptyList(){
        for(int i = 0; i < boxes.length; i++){
            boxes[i] = null;
        }
    }

    
}
