package de.fklappan.app.expandtestapp;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import de.fklappan.android.util.expandablecardview.ExpandableCardView;

public class MainActivity extends AppCompatActivity {

    ColorStateList cardDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ExpandableCardView cardView1 = findViewById(R.id.expandableCard1);
        cardDefaultColor = cardView1.getCardBackgroundColor();
        cardView1.setOnExpandListener(new ExpandableCardView.OnExpandListener() {
            @Override
            public void onExpand() {
                cardView1.setExpandIconColor(getColor(R.color.colorWhite));
                cardView1.setHeaderTextColor(getColor(R.color.colorWhite));
                cardView1.setCardBackgroundColor(getColor(R.color.colorPrimary));
            }
        });
        cardView1.setOnCollapseListener(new ExpandableCardView.OnCollapseListener() {
            @Override
            public void onCollapse() {
                cardView1.setExpandIconColor(getColor(R.color.colorBlack));
                cardView1.resetHeaderTextColor();
                cardView1.setCardBackgroundColor(cardDefaultColor);
            }
        });

        ExpandableCardView cardView2 = findViewById(R.id.expandableCard2);


    }
}
