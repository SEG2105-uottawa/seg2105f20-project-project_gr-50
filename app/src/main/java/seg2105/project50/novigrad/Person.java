package seg2105.project50.novigrad;

class Person {


        private String name;
        private String role;
        private String email;
        private String password;
        private String branchName;

        Person(){

        }

        Person(String name, String role, String email, String password, String branchName){
            this.name = name;
            this.role = role;
            this.email = email;
            this.password = password;
            this.branchName= branchName;
        }
        public String getName(){
            return name;
        }
        public String getRole(){
            return role;
        }
        public String getEmail(){
            return email;
        }
        public String getPassword(){
            return password;
        }
        public String getBranchName(){return branchName;}


}
