package Utilities_Package;

         public class Person { private String name,address,phone_Number,register_Number;
        private double sold,sold_Max;
        int sold_duration;

     // Setters
        public void setName(String name) {
            this.name = name;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public void setPhone_Number(String phone_Number) {
            this.phone_Number = phone_Number;
        }
        public void setRegister_Number(String register_Number) {
            this.register_Number = register_Number;
        }
        public void setSold(double sold) {
            this.sold = sold;
        }
        public void setSold_Max(double sold_Max) {
            this.sold_Max = sold_Max;
        }
        public void setSold_duration(int sold_duration) {
            this.sold_duration = sold_duration;
        }
     //  Getters
        public String getName() {
            return name;
        }
        public String getAddress() {
            return address;
        }
        public String getPhone_Number() {
            return phone_Number;
        }
        public String getRegister_Number() {
            return register_Number;
        }
        public double getSold() {
            return sold;
        }
        public double getSold_Max() {
            return sold_Max;
        }
        public int getSold_duration() {
            return sold_duration;
        }

        // Clients Constructor
        public Person(String Name,String Address,String Phone,String Register,double Sold,double Sold_Max,int Sold_duration){

        name   = Name   ;
        address = Address;
        phone_Number = Phone;
        register_Number = Register;
        sold = Sold;
        sold_duration = Sold_duration;
        sold_Max = Sold_Max;
    }
        // Fournisseur Constructor
        public Person(String Name,String Address,String Phone,String Register,double Sold){

        name    = Name   ;
        address = Address;
        phone_Number = Phone;
        register_Number = Register;
        sold = Sold;

    }









}
