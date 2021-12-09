// A basic test class. Feel free to extend it, as needed.
public class Test {

  public static void main(String args[]) {
    // registerTest();
    // memoryTest();
    // computerStage1Test();
    // computerStage2Test();
    // computerStage3Test();
    // computerStage4Test();
    // computerStage5Test();
    computerStage6Test();
  }

  public static void registerTest() {
    Register r1 = new Register();
    r1.setValue(10);
    System.out.println(r1);
    Register r2 = new Register(20);
    System.out.println(r2);
    r1.addOne();
    r2.setValue(r1.getValue());
    System.out.println(r2);
  }

  public static void memoryTest() {
    Memory m = new Memory(100);
    m.setValue(0, 100);
    m.setValue(1, 200);
    m.setValue(2, -17);
    System.out.println(m.getValue(0));
    m.setValue(1, 300);
    System.out.println(m);
  }

  public static void computerStage1Test() {
    Computer vic = new Computer();
    System.out.println(vic);
  }

  public static void computerStage2Test() {
    Computer vic = new Computer();
    vic.loadProgram("program1.vic");
    System.out.println(vic);
  }

  public static void computerStage3Test() {
    Computer vic = new Computer();
    //// Test Load and Write
    // vic.loadProgram("program1.vic"); // or some other program file
    //// Test Add
    // vic.loadProgram("program2.vic"); // or some other program file
    //// Test Sub
    // vic.loadProgram("program3.vic"); // or some other program file
    //// Test Store
    vic.loadProgram("program4.vic"); // or some other program file
    vic.run();
    System.out.println(vic);
  }

  public static void computerStage4Test() {
    Computer vic = new Computer();
    vic.loadProgram("program5.vic");
    vic.loadInput("input1.txt");
    vic.run();
    System.out.println(vic);
  }

  public static void computerStage5Test() {
    Computer vic = new Computer();
    vic.loadProgram("program6.vic");
    vic.loadInput("input2.txt");
    vic.run();
    System.out.println(vic);
  }

  public static void computerStage6Test() {
    Computer vic = new Computer();
    vic.loadProgram("max2.vic");
    vic.loadInput("input3.txt");
    vic.run();
    System.out.println(vic);
  }
}
