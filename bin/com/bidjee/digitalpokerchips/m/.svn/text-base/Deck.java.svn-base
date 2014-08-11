package com.bidjee.digitalpokerchips.m;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
     ArrayList <Card> cards = new ArrayList <Card>();
      
     public Deck() {
         createCards();
     }
	        
     public void createCards() {
         for (int i = 0; i < 13; i++) {
             cards.add(new Card(i, Card.SPADES));
         }
         for (int i = 0; i < 13; i++) {
             cards.add(new Card(i, Card.HEARTS));
         }
         for (int i = 0; i < 13; i++) {
             cards.add(new Card(i, Card.CLUBS));
         }
         for (int i = 0; i < 13; i++) {
             cards.add(new Card(i, Card.DIAMONDS));
         }
     }
     
     public void reset() {
         cards.clear();
         createCards();
     }
     
     public void shuffle() {
         Collections.shuffle(cards);
     }
     
     public Card drawTopCard() {
         return cards.remove(0);
     }

}
