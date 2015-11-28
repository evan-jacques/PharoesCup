package com.firstapp.evan.pharoescup;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by evan on 30/10/15.
 */
public class Deck {
    private Card[] cards;
    int i;

    Deck(ArrayList<String> rules, ArrayList<String> descriptions, int[] ppos,int[][] ids){
        i = 51;
        cards = new Card[52];
        int x = 0;
        for(int a = 0;a < 13;a++) {
            for (int b = 0; b < 4; b++) {

                cards[x] = new Card(b, a);
                cards[x].setImg(ids[b][a]);
                cards[x].setRule(rules.get(ppos[a]), descriptions.get(ppos[a]));
                x++;
            }
        }
    }
    public Card drawFromDeck(){
        Random generator = new Random();
        int index = 0;
        do{
            index = generator.nextInt(52);
        } while (cards[index] == null);

        i--;
        Card temp = cards[index];
        cards[index] = null;
        return temp;
    }
    public int getTotalCards(){
        return i;
    }
}
