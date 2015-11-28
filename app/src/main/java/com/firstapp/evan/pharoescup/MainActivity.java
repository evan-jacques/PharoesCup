package com.firstapp.evan.pharoescup;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
//import android.view.Menu;
//import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_RULES = "com.example.evan.pharoescup.RULES";
    public final static String EXTRA_DESC = "com.example.evan.pharoescup.DESCRIPTIONS";
    public final static String EXTRA_POS = "com.example.evan.pharoescup.POSITIONS";
    public final static String EXTRA_HEARTIDS = "com.example.evan.pharoescup.HEARTIDS";
    public final static String EXTRA_SPADEIDS = "com.example.evan.pharoescup.SPADEIDS";
    public final static String EXTRA_DIAMONDIDS = "com.example.evan.pharoescup.DIAMONDIDS";
    public final static String EXTRA_CLUBIDS = "com.example.evan.pharoescup.CLUBIDS";
    SQLiteDatabase rulesDB = null;
    private int[] ppos;
    private Spinner aceSpinner,twoSpinner,threeSpinner,fourSpinner,fiveSpinner,sixSpinner,sevenSpinner,eightSpinner,nineSpinner,tenSpinner,jackSpinner,queenSpinner,kingSpinner;
    private Spinner[] spinners;
    private EditText addRuleEditText,addDescriptionEditText;
    private Button addRuleButton,aceExpand,twoExpand,threeExpand,fourExpand,fiveExpand,sixExpand,sevenExpand,eightExpand,nineExpand,tenExpand,jackExpand,queenExpand,kingExpand;
    private ArrayList<String> rules, descriptions;
    private ArrayAdapter<String> adapter;
    private int currentAce, currentTwo,currentThree,currentFour,currentFive,currentSix,currentSeven,currentEight,currentNine,currentTen,currentJack,currentQueen,currentKing;
    private int[][] imgIds;
    private boolean[] toggle;
    private TextView aceDescription,twoDescription,threeDescription,fourDescription,fiveDescription,sixDescription,sevenDescription,eightDescription,nineDescription,tenDescription,jackDescription,queenDescription,kingDescription;
    private TextView[] textViews;
    private Button[] expand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        aceSpinner = (Spinner) findViewById(R.id.aceSpinner);
        twoSpinner = (Spinner) findViewById(R.id.twoSpinner);
        threeSpinner = (Spinner) findViewById(R.id.threeSpinner);
        fourSpinner = (Spinner) findViewById(R.id.fourSpinner);
        fiveSpinner = (Spinner) findViewById(R.id.fiveSpinner);
        sixSpinner = (Spinner) findViewById(R.id.sixSpinner);
        sevenSpinner = (Spinner) findViewById(R.id.sevenSpinner);
        eightSpinner = (Spinner) findViewById(R.id.eightSpinner);
        nineSpinner = (Spinner) findViewById(R.id.nineSpinner);
        tenSpinner = (Spinner) findViewById(R.id.tenSpinner);
        jackSpinner = (Spinner) findViewById(R.id.jackSpinner);
        queenSpinner = (Spinner) findViewById(R.id.queenSpinner);
        kingSpinner = (Spinner) findViewById(R.id.kingSpinner);
        spinners = new Spinner[] {aceSpinner,twoSpinner,threeSpinner,fourSpinner,fiveSpinner,sixSpinner,sevenSpinner,eightSpinner,nineSpinner,tenSpinner,jackSpinner,queenSpinner,kingSpinner};


        addRuleEditText = (EditText) findViewById(R.id.addRuleEditText);
        addDescriptionEditText = (EditText) findViewById(R.id.addDescriptionEditText);

        aceDescription = (TextView) findViewById(R.id.aceDescription);
        twoDescription = (TextView) findViewById(R.id.twoDescription);
        threeDescription = (TextView) findViewById(R.id.threeDescription);
        fourDescription = (TextView) findViewById(R.id.fourDescription);
        fiveDescription = (TextView) findViewById(R.id.fiveDescription);
        sixDescription = (TextView) findViewById(R.id.sixDescription);
        sevenDescription = (TextView) findViewById(R.id.sevenDescription);
        eightDescription = (TextView) findViewById(R.id.eightDescription);
        nineDescription = (TextView) findViewById(R.id.nineDescription);
        tenDescription = (TextView) findViewById(R.id.tenDescription);
        jackDescription = (TextView) findViewById(R.id.jackDescription);
        queenDescription = (TextView) findViewById(R.id.queenDescription);
        kingDescription = (TextView) findViewById(R.id.kingDescription);
        textViews = new TextView[] {aceDescription,twoDescription,threeDescription,fourDescription,fiveDescription,sixDescription,sevenDescription,eightDescription,nineDescription,tenDescription,jackDescription,queenDescription,kingDescription};

        addRuleButton = (Button) findViewById(R.id.addRuleButton);


        currentAce = 0;
        currentTwo = 1;
        currentThree = 2;
        currentFour = 3;
        currentFive = 4;
        currentSix = 5;
        currentSeven = 6;
        currentEight = 7;
        currentNine = 8;
        currentTen = 9;
        currentJack = 10;
        currentQueen = 11;
        currentKing = 12;

        ppos = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0};

        aceExpand = (Button) findViewById(R.id.aceExpand);
        twoExpand = (Button) findViewById(R.id.twoExpand);
        threeExpand = (Button) findViewById(R.id.threeExpand);
        fourExpand = (Button) findViewById(R.id.fourExpand);
        fiveExpand = (Button) findViewById(R.id.fiveExpand);
        sixExpand = (Button) findViewById(R.id.sixExpand);
        sevenExpand = (Button) findViewById(R.id.sevenExpand);
        eightExpand = (Button) findViewById(R.id.eightExpand);
        nineExpand = (Button) findViewById(R.id.nineExpand);
        tenExpand = (Button) findViewById(R.id.tenExpand);
        jackExpand = (Button) findViewById(R.id.jackExpand);
        queenExpand = (Button) findViewById(R.id.queenExpand);
        kingExpand = (Button) findViewById(R.id.kingExpand);
        expand = new Button[] {aceExpand,twoExpand,threeExpand,fourExpand,fiveExpand,sixExpand,sevenExpand,eightExpand,nineExpand,tenExpand,jackExpand,queenExpand,kingExpand};

        String[] dbAddRule = new String[] {"'Face','Ace is usually face. Last person to touch the person to their left s face drinks!'",
                "'A.B. Dare','The person who draws this card closes their eyes. The person to their left chooses two people and declares them A and B, respectively (without the use of names, so the person with their eyes closed cannot identify A and B). Once A and B are chosen, the person who drew the card opens their eyes and comes up with a dare involving A and B (e.g. A gives B a lap dance).'",
                "'Headphones','The person who draws this card puts on headphones (preferably noise cancelling headphones) and sings a song out loud chosen by the remaining players. Only the person with the headphones should be able to hear the song, and song lyrics can be used if needed.'",
                "'Shopping Spree','Everyone must exchange an article of clothing with someone else. You may not trade for an article of clothing you have already worn in the game (you can only get your clothes back at the end of the game). And yes, socks, belts, and jewelry can be used for trade.'",
                "'Gecko/Lava','If the card is black, you re playing gecko. Everyone has to touch his or her chest and inner left thigh on a flat surface. Last person to do so must draaank! Oh, and you can t go to the same surface next time. If the card is red, you re playing lava. This one s simple, the floor is lava until a black card is drawn (because black cards obviously cool down lava - Jamie). If you re ever in the lava, you drink!'",
                "'Fruit Pass','Pass a spherical fruit, such as an orange, around the circle using only your chin and chest to grip the fruit.'",
                "'Shot Roulette','The person who draws this card chooses 3 people to participate with them. Someone who isn t among the 4 participants fills 2 shot glasses with clear alcohol and 2 with water. The 4 players must choose a glass and take the shot! Don t smell it before you take it!'",
                "'Suck and Blow','Pass the drawn card around the circle by sucking on the card and blowing on it when the next person begins sucking on it.'",
                "'Straight Arm Chug','Finish your drink without bending your elbow to take a sip (a straight arm). Yes this does get messy, so you probably only want to do this if you have access to the outdoors.'",
                "'Never Had I Ever','Eve = (TextView) findViewById(R.id.ryone puts 3 fingers up and the first player (whoever draws the card) says a truthful statement starting with \"Never have I ever\" (e.g. Never have I ever gone skinny dipping). Anyone who HAS done what the player has not, puts a finger down and takes a drink. Play continues around the circle until someone has no fingers showing.'",
                "'Blackout Glasses','Whoever draws the card wears a pair of sunglasses with duct tape over the lenses (the blackout glasses). The person must wear the glasses until it becomes their turn again or someone else draws another blackout glasses card at which point, the new player must wear the glasses.'",
                "'Dino Hands','Whoever draws this card gets their index finger + middle finger taped together and their ring finger + pinky finger taped together (on both hands) for the remainder of the game. If someone already has \"Dino hands\" and draws this card, they can choose someone else to join their dino family. The tape can only be removed at the end of the game!'",
                "'Pharaohs Cup','Everyone pours a small amount of his or her drink into the Pharaoh s Cup (usually a large glass in the middle of the table). Once the 4th \"Pharaoh s Cup Card\" is drawn and further additions to the cup have been made, everyone takes a sip from the cup as it is passed to them around the circle. Make as many laps around the circle as necessary, and continue the game once the cup is empty. Feel free to wear hoodies and play any ritual music you own the rights to during the ceremony.'",
                "'Categories','Whoever draws this card chooses a category (e.g. types of instruments). Go around the circle naming instruments (in this case) without repeating any that have been said. You only have 3 seconds to give an answer and whoever screws up... Yup, drink. Oh, and if you choose a stupid category (you ll know it s stupid because everyone s yelling at you for choosing a stupid category - like the shades of gray), first DRINK, then strongly think about how your actions have affected the fun of others.'",
                "'Body Shot','Wooo Body shot! Whoever draws this card lies face up on the table (make sure it s sturdy, otherwise do it on the floor). The person to their left pours a shot in their belly button and takes the shot!'",
                "'Thumb Master','Whoever draws this card is the thumb master! At any point during the game, the thumb master can place their thumb on an object. The last person to place their thumb on that object drinks! \n" +
                "Note: There is only one thumb master at a time! If you don t use your power before someone else becomes thumb master (it s quite tragic when witnessed), I m sorry, but you re now powerless! Oh, and you can only use your power once... people get power hungry otherwise.'",
                "'Viking Master','Whoever draws this card is the Viking master! At any point during the game, the Viking master can place two fingers on their head (representative of Viking horns). Once this happens, everyone has to pretend they re paddling a boat as fast as they can (watch a video on the J-stroke if you re self-conscious about your stroke). Last person to paddle their boat, drink!! \n" +
                "Note: Similar to the thumb master, there is only one Viking master at a time. If you don t use your power before someone else becomes Viking master, you re now powerless! And again, you can only use your power once.'",
                "'Truth or Dare','Whoever draws this card is on the stand. The person to their left asks them, \"Truth or Dare?\" If the person chooses a truth, ask them to truthfully answer a question, if they choose dare, give them a dare to do!'",
                "'Cellphone Submission','Whoever draws this card must submit their cellphone to a person of their choosing (and yes, you have to unlock the phone for them). The person can now text anyone they want (with the exception of family, and one \"veto\" of the submitters choosing). This rule is fun, but don t overdo it... If done correctly, no one should be crying after a cellphone submission.'",
                "'Make a rule','Make a rule... Again, don t overdo it. But don t underdo it. Do it juuusssttt right.'",
                "'Waterfall','This starts with everyone chugging. Then the person who picked the card can stop whenever he/she wants. This allows the next person to stop when he/she wants and so on and so on.'",
                "'You','This means that whoever picks the 2 card gets to choose anybody they want in the game to drink'",
                "'Me','You have to drink'",
                "'Guys','All the guys have to drink'",
                "'Chicks','All the girls have to drink'",
                "'Jive','The person who picks the card has to do a dance move. Then the next person has to do that dance move and add to it. This continues until someone screws up and has to drink.'",
                "'Heaven',' All players reach for the sky. The last person has to drink.'",
                "'Mate','The player who picks the card chooses another player to be their mate. This means when one of them drinks they both drink.'",
                "'Bust a Rhyme','The simple version has the player who picked the card say a word and everybody has to say a word that rhymes with it.'",
                "'Question Master','The person who selected this card is now the Question Master. Whoever answers a question posed by the Question Master must drink.'"
        };

        createDatabase();
        try {
            for(int r = 0; r < dbAddRule.length;r++) {
                rulesDB.execSQL("INSERT INTO rules (ruleName,description) VALUES (" + dbAddRule[r] + ")");
            }
        } catch (SQLiteConstraintException e){

        }
        imgIds = new int[][] {{R.drawable.heart_ace,R.drawable.heart_2,R.drawable.heart_3,R.drawable.heart_4,R.drawable.heart_5,R.drawable.heart_6,R.drawable.heart_7,R.drawable.heart_8,R.drawable.heart_9,R.drawable.heart_10,R.drawable.heart_jack,R.drawable.heart_queen,R.drawable.heart_king},{R.drawable.spade_ace,R.drawable.spade_2,R.drawable.spade_3,R.drawable.spade_4,R.drawable.spade_5,R.drawable.spade_6,R.drawable.spade_7,R.drawable.spade_8,R.drawable.spade_9,R.drawable.spade_10,R.drawable.spade_jack,R.drawable.spade_queen,R.drawable.spade_king},{R.drawable.diamond_ace,R.drawable.diamond_2,R.drawable.diamond_3,R.drawable.diamond_4,R.drawable.diamond_5,R.drawable.diamond_6,R.drawable.diamond_7,R.drawable.diamond_8,R.drawable.diamond_9,R.drawable.diamond_10,R.drawable.diamond_jack,R.drawable.diamond_queen,R.drawable.diamond_king},{R.drawable.club_ace,R.drawable.club_2,R.drawable.club_3,R.drawable.club_4,R.drawable.club_5,R.drawable.club_6,R.drawable.club_7,R.drawable.club_8,R.drawable.club_9,R.drawable.club_10, R.drawable.club_jack,R.drawable.club_queen,R.drawable.club_king}};
        rules = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        updateRules();
        setAdapter();
        for(int i = 0; i < spinners.length;i++){
            final int finalI = i;
            spinners[i].setSelection(i);
            textViews[i].setText(descriptions.get(i));
            ppos[i] = i;
            spinners[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int pos = spinners[finalI].getSelectedItemPosition();
                    resetAdapter(spinners[finalI], pos);
                    return false;
                }
            });
            spinners[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ppos[finalI] = position;
                    textViews[finalI].setText(descriptions.get(position));

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        toggle = new boolean[] {true,true,true,true,true,true,true,true,true,true,true,true,true};

        for(int i = 0; i < expand.length; i++) {
            final int finalI = i;
            expand[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (toggle[finalI]) {
                        textViews[finalI].setVisibility(View.VISIBLE);

                    } else {
                        textViews[finalI].setVisibility(View.GONE);
                    }
                    toggle[finalI] = !toggle[finalI];
                }
            });
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
 TEST1       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
    public void setAdapter() {
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,rules);
        aceSpinner.setAdapter(adapter);
        twoSpinner.setAdapter(adapter);
        threeSpinner.setAdapter(adapter);
        fourSpinner.setAdapter(adapter);
        fiveSpinner.setAdapter(adapter);
        sixSpinner.setAdapter(adapter);
        sevenSpinner.setAdapter(adapter);
        eightSpinner.setAdapter(adapter);
        nineSpinner.setAdapter(adapter);
        tenSpinner.setAdapter(adapter);
        jackSpinner.setAdapter(adapter);
        queenSpinner.setAdapter(adapter);
        kingSpinner.setAdapter(adapter);


    }
    public void createDatabase() {

        try{
            rulesDB = this.openOrCreateDatabase("PharaohsRules",MODE_PRIVATE,null);
            rulesDB.execSQL("CREATE TABLE IF NOT EXISTS rules " + "(id integer primary key,ruleName VARCHAR UNIQUE, description VARCHAR);");
            File database = getApplicationContext().getDatabasePath("PharaohsRules.db");
            if(!database.exists()){
                //Toast.makeText(this, "Database Created", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Database Missing", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Log.e("Contacts Error", "Error creating contacts");
        }

    }
    public void addRule(View view) {

        String ruleName = addRuleEditText.getText().toString();
        String ruleDescription = addDescriptionEditText.getText().toString();
        if(ruleName.length() > 0) {
            try {
                rulesDB.execSQL("INSERT INTO rules (ruleName,description) VALUES ('" + ruleName + "', '" + ruleDescription + "');");
            } catch (SQLiteConstraintException e) {
                Toast.makeText(this, "Rule already exists", Toast.LENGTH_SHORT).show();
            }
            updateRules();
            addRuleEditText.setText("");
            addDescriptionEditText.setText("");
        }
        else {
            Toast.makeText(this, "Enter Rule", Toast.LENGTH_SHORT).show();
        }

    }
    public void updateRules(){

        Cursor cursor = rulesDB.rawQuery("SELECT * FROM rules", null);
        int idCulumn = cursor.getColumnIndex("id");
        int ruleNameColumn = cursor.getColumnIndex("ruleName");
        int descriptionColumn = cursor.getColumnIndex("description");
        cursor.moveToFirst();
        rules.clear();
        descriptions.clear();
        if(cursor != null && (cursor.getCount() > 0)){
            do {
                String id = cursor.getString(idCulumn);
                String name = cursor.getString(ruleNameColumn);
                String description = cursor.getString(descriptionColumn);
                rules.add(name);
                descriptions.add(description);
            } while(cursor.moveToNext());


        } else {
            Toast.makeText(this, "No Results to Show", Toast.LENGTH_SHORT).show();

        }
        cursor.close();

    }
    public void resetAdapter(Spinner spinner,int position){
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,rules);
        spinner.setAdapter(adapter);
        spinner.setSelection(position);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this,GameActivity.class);
        //Deck cards = new Deck(rules,descriptions,ppos);
        intent.putExtra(EXTRA_RULES, rules);
        intent.putExtra(EXTRA_DESC, descriptions);
        intent.putExtra(EXTRA_POS, (Serializable) ppos);
        intent.putExtra(EXTRA_HEARTIDS,(Serializable) imgIds[0]);
        intent.putExtra(EXTRA_SPADEIDS,(Serializable) imgIds[1]);
        intent.putExtra(EXTRA_DIAMONDIDS,(Serializable) imgIds[2]);
        intent.putExtra(EXTRA_CLUBIDS,(Serializable) imgIds[3]);
        startActivity(intent);

    }

}
