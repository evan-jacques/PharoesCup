package com.firstapp.evan.pharoescup;

/**
 * Created by evan on 28/10/15.
 */
public class Card {
    private int rank, suit;
    private String[] suits = { "heart", "spade", "diamond", "club" };
    private String[] ranks  = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king" };
    private int img;
    private CardRule rule = new CardRule("","");

    Card(int suit, int rank) {
        this.rank=rank;
        this.suit=suit;

    }
    public @Override String toString() {
        return  suits[suit] + "_" + ranks[rank];
    }
    public int getRank() {
        return rank;
    }
    public int getSuit() {
        return suit;
    }
    public int getImg() { return img;}
    public void setImg(int card) {img = card;}
    public CardRule getRule() { return rule;}
    public void setRule(String r, String d) {rule.setRule(r);rule.setDescription(d);}
}

