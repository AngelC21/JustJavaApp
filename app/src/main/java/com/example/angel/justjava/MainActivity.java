package com.example.angel.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity == 25) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        Editable nameEditable = nameField.getText();
        String name = nameField.getText().toString();
        Log.v("MainActivity", "Name: " + name);

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();


        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox caramelCheckBox = (CheckBox) findViewById(R.id.caramel_checkbox);
        boolean hasCaramel = caramelCheckBox.isChecked();

        CheckBox cinnamonCheckBox = (CheckBox) findViewById(R.id.cinnamon_checkbox);
        boolean hasCinnamon  = cinnamonCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate, hasCaramel, hasCinnamon);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate, hasCaramel, hasCinnamon);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addCaramel, boolean addCinnamon) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        if (addCaramel) {
            basePrice = basePrice + 2;
        }
        if (addCinnamon) {
            basePrice = basePrice + 1;
        }
        return quantity * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate, boolean addCaramel, boolean addCinnamon) {
        String priceMessage = "Name: " + name;
        priceMessage +=  "\nAdd whipped cream? " + addWhippedCream;
        priceMessage +=  "\nAdd chocolate? " + addChocolate;
        priceMessage +=  "\nAdd caramel? " + addCaramel;
        priceMessage +=  "\nAdd cinnamon? " + addCinnamon;
        priceMessage +=  "\nQuantity: " + quantity;
        priceMessage +=  "\nTotal: $" + price;
        priceMessage +=  "\nThank You " + name+"!";
        return priceMessage;
    }

    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}