// Define interface State.
 interface State {
    void dispenseProduct(VendingContext ctx);
    void insertCoin(VendingContext ctx);
    void ejectCoin(VendingContext ctx);
    void selectProduct(VendingContext ctx);
    String getStateName();
}

 class NoCoinState implements State {
    @Override
    public void insertCoin(VendingContext context) {
        System.out.println("Coin inserted");
        context.setState(new hasCoinState());
    }
    @Override
    public void ejectCoin(VendingContext context){
        System.out.println("Insert Coin first");

    }
    @Override
    public void selectProduct(VendingContext context){
        System.out.println("Insert Coin first");

    }
    @Override
    public void dispenseProduct(VendingContext context){
        System.out.println("Insert Coin first");

    }
    @Override
    public String getStateName(){
        return "NoCoinState";
    }
}
 
class hasCoinState implements State {
    @Override
    public void insertCoin(VendingContext context) {
        System.out.println("Coin already inserted");
    }
    @Override
    public void ejectCoin(VendingContext context){
        context.setState(new NoCoinState());

    }
    @Override
    public void selectProduct(VendingContext context){
        System.out.println("Selecting product ");
        context.setState(new productSelectedState());

    }
    @Override
    public void dispenseProduct(VendingContext context){
        System.out.println("Select product first");

    }
    @Override
    public String getStateName(){
        return "hasCoinState";
    }

}
class productSelectedState implements State {
    @Override
    public void insertCoin(VendingContext context) {
        System.out.println("Coin already inserted");
    }
    @Override
    public void ejectCoin(VendingContext context){
        System.out.println("Ejecting coin  ");
        context.setState(new NoCoinState());
    }
    @Override
    public void selectProduct(VendingContext context){
        System.out.println("Already product selected ");
    }
    @Override
    public void dispenseProduct(VendingContext context){
        System.out.println("Dispensing product ");
        context.setState(new NoCoinState());

    }
    @Override
    public String getStateName(){
        return "productSelectedState";
    }

}

// Context class
class VendingContext {
    State state;
    public VendingContext(){
        this.state = new NoCoinState();
    }
    public void setState(State state){
        this.state = state;
        System.out.println("Current State is "+state.getStateName());
    }
    // Implement all behaviors to delegate to respective state.
    // All concerte state class must class setState() 
    public void dispenseProduct(){
        state.dispenseProduct(this); // send this context
    }
    public void insertCoin() {
        state.insertCoin(this);
    }
    public void ejectCoin() {
        state.ejectCoin(this);

    }
    public void selectProduct() {
        state.selectProduct(this);

    }
    public String getCurrentState() {
        return state.getStateName();
    }

}




public class StatePatternDemo {

    public static void main(String[] args) {
        VendingContext ctx = new VendingContext();
        System.out.println("Current State of the machine is " +ctx.getCurrentState());
        // initial no coin state
        // Invalid flow test - 1
        ctx.selectProduct();
        ctx.ejectCoin();
        // Valid flow test - 2
        ctx.insertCoin();
        ctx.selectProduct();
        ctx.dispenseProduct();
        System.out.println("Current State of the machine now  " +ctx.getCurrentState());

    }
}