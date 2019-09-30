package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        public void increment(View view){
         if (quantity==100){
             Toast.makeText(this,  "You cannot have more than 100 coffess", Toast.LENGTH_SHORT).show();
               return;

         }
         quantity =quantity+1;
         display(quantity);

    }

    public void decrement(View view){
        if (quantity==1){
            Toast.makeText( this,  "You cannot have more than 100 coffess", Toast.LENGTH_SHORT).show();
            return;

        }
        quantity =quantity-1;
        display(quantity);
    }

    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name);
        String name = nameField.getText().toString();

       // String message= "Total $"+quantity*5+" \n \n Terima Kasih Orderannya";


        CheckBox whippedCreamCheckbox=(CheckBox) findViewById(R.id.whipped_cream_checBox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox ChocolateCheckbox=(CheckBox) findViewById(R.id.Chocolate_checBox);
        boolean hasChocolate = ChocolateCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String Message=creatOrderSummary(name, price,hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order by " +name);
        intent.putExtra(Intent.EXTRA_TEXT, Message);
        if (intent.resolveActivity(getPackageManager()) !=null ){
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean whippedCream, boolean Chocolate){
    int basePrice=5;
    if (whippedCream){
        basePrice +=1;
    }
    if (Chocolate){
        basePrice +=2;
    }
    return quantity * basePrice;
    }


   public String creatOrderSummary(String name, int price, boolean addwhippedCream, boolean addChocolate){
        String priceMessage=getString(R.string.order_summary_name, name);
        priceMessage += "\n"+ getString(R.string.order_summary_whipped_cream,addwhippedCream);
        priceMessage += "\n"+ getString(R.string.order_summary_Chocolate,addChocolate);
        priceMessage += "\n"+ getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n"+ getString(R.string.order_summary_price,
            NumberFormat.getCurrencyInstance().format(price));
        return priceMessage;
   }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }


}

