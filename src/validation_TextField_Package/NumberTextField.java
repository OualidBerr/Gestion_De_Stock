package validation_TextField_Package;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

    public NumberTextField(){

    }

    @Override
    public void replaceText(int i, int i2 , String string){


      if (string.matches("[0-9]")|| string.isEmpty() || string.equals(".")  ){

            super.replaceText(i,i2,string);
      }

    }

    @Override
    public void replaceSelection(String string){

        super.replaceSelection(string);
    }



}
