/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity=2;
    private int price = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(getApplicationContext(),"You cannot have more than 100 coffees!",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity==1){
            Toast.makeText(getApplicationContext(),"You cannot have less than 1 coffee!",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        if (hasWhippedCream){
            price+=1;
        }

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        if (hasChocolate){
            price +=2;
        }

        EditText userName = (EditText) findViewById(R.id.name_edit_box);
        String name = userName.getText().toString();

        //displayMessage(createOrderSummary(price, hasWhippedCream, hasChocolate, name));
        String order = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        price = 5;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, order);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for: "+name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     *
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */
    /**
     * This method creates order summary
     */
    private String createOrderSummary(int number, boolean addWhippedCream, boolean addChocolate, String newName){
        String summary = "Name: "+newName+"\nAdd Whipped Cream? "+addWhippedCream+"\nAdd Chocolate? "+addChocolate+"\nQuantity: "+quantity+"\nTotal: $"+(quantity*number)+"\n "+getString(R.string.thank_you);
        TextView orderOut = (TextView) findViewById(R.id.order_summary_text_view);
        orderOut.setText(summary);
        return summary;
    }
}