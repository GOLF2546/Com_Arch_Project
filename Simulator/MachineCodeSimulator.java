import java.util.*;

public class MachineCodeSimulator {
    private int[] memory;
    private Map<String, Integer> registers;
    private int pc;
    private static final int NUMMEMORY = 65536;
    private static final int NUMREGS = 8;
    private int total_instruction=0;
    
    //initialize registers ทุกตัวและ set program counter เป็น 0 
    public MachineCodeSimulator() {
        
        memory = new int[10];
        registers = new HashMap<>();
        memory[0]=8454151;
        memory[1]=9043971;
        memory[2]=655361;
        memory[3]=16842754;
        memory[4]=16842749;
        memory[5]=29360128;
        memory[6]=25165824;
        memory[7]=5;
        memory[8]=-1;
        memory[9]=2;
           
        //$0     value 0
        // $1      n input to function
        // $2     r input to function
        // $3     return value of function
        // $4     local variable for function
        // $5     stack pointer
        // $6     temporary value (can hold different values at different times, e.g.
        //         +1, -1, function address)
        // $7 return address
        registers.put("R0", 0); 
        registers.put("R1", 0);
        registers.put("R2", 0);
        registers.put("R3", 0);
        registers.put("R4", 0);
        registers.put("R5", 0);
        registers.put("R6", 0);
        registers.put("R7", 0);
        pc = 0;
    }
    //เปลี่ยน machinecode จากd ecimal เป็น binary แล้วเก็บใน memory[]
    public void loadProgram(List<String> machineCode) {
        for (int i = 0; i < machineCode.size(); i++) {
            
            //memory[i] = this.convertNum(Integer.parseInt(machineCode.get(i), 2));
            memory[i] = Integer.parseInt(machineCode.get(i));
            System.out.println(memory[i]);
        }
    }
    
    private String fetch() {
        int instruction = memory[pc];
        total_instruction++;
        pc++;
        return String.format("%25s", Integer.toBinaryString(instruction)).replace(' ', '0');
    }

    private boolean decodeExecute(String instruction) {
        String opcode = instruction.substring(0, 3);
        switch (opcode) {
            case "000": // ADD
                
                String rd = "R" + Integer.parseInt(instruction.substring(22, 25), 2);
                String rs = "R" + Integer.parseInt(instruction.substring(3, 6), 2);
                String rt = "R" + Integer.parseInt(instruction.substring(6,9), 2);
                int rsValue = registers.getOrDefault(rs, 0);
                int rtValue = registers.getOrDefault(rt, 0);
                registers.put(rd, rsValue + rtValue);
                
            break;
            case "001": // nand

                break;
            case "010": // LOAD
                String rload = "R" + Integer.parseInt(instruction.substring(6, 9), 2);//r1
                String raddress = "R" + Integer.parseInt(instruction.substring(3, 6), 2);//r0
                int raddressValue = Integer.valueOf(registers.get(raddress));
                int address =raddressValue + Integer.parseInt(instruction.substring(9,25), 2);
                registers.put(rload, memory[address]);
                break;
            case "011": // STORE
                String rstore = "R" + Integer.parseInt(instruction.substring(6, 9), 2);//r1
                String rstoreaddress = "R" + Integer.parseInt(instruction.substring(3, 6), 2);//r0
                int rstoreaddressValue = Integer.valueOf(registers.get(rstoreaddress));
                int storeaddress =rstoreaddressValue + Integer.parseInt(instruction.substring(9,25), 2);
                registers.put(rstore, memory[storeaddress]);
                break;
            case "110"://halt
                
                System.out.println("machine halted");
                return false;
                
            case "111"://noop
                break;
            case "100"://beq
                int offset;
                String r2 = "R" + Integer.parseInt(instruction.substring(6, 9), 2);//r1
                String r1 = "R" + Integer.parseInt(instruction.substring(3, 6), 2);//r0
                int r2value = Integer.valueOf(registers.get(r2));
                int r1value = Integer.valueOf(registers.get(r1));
                offset=convertNum(Integer.parseInt(instruction.substring(9,25), 2));
                if(r1value==r2value) pc=pc+offset;
                break;
            
           
        }
        
        return true;
    }

    public void run() {
        
         while (true) {
            String instruction = fetch();
            if (!decodeExecute(instruction)){
                System.out.println("hello");
                break;
            }
            printState(this); 
            System.out.println(pc);
         }
        
        //  System.out.println(decodeExecute("1100000000000000000000000"));
        //  printState(this);
        //System.out.println("Final register states: " + registers);
        System.out.println("total of "+ total_instruction +" instructions executed");
        //System.out.println("Memory locations 0-10: " + Arrays.toString(Arrays.copyOfRange(memory, 0, 11)));
        System.out.println("final state of machine:");
    }

    public static void main(String[] args) {
        List<String> machineCode = Arrays.asList(
            "8454151","9043971","655361","16842754","16842749"
            ,"29360128","25165824","5","-1","2");

        MachineCodeSimulator sim = new MachineCodeSimulator();
        printState(sim);
        sim.loadProgram(machineCode);
       sim.run();
       printState(sim);
        
    }
    private static void printState(MachineCodeSimulator state) {
        System.out.println("\n@@@\nstate:");
        System.out.printf("\tpc %d\n", state.pc);
        System.out.println("\tmemory:");
        for (int i = 0; i < 10; i++) {
            System.out.printf("\t\tmem[ %d ] %d\n", i, state.memory[i]);
        }
        System.out.println("\tregisters:");
        for (int i = 0; i < NUMREGS; i++) {
            System.out.printf("\t\treg[ %d ] %d\n", i, state.registers.get("R"+i));
        }
        
        System.out.println("end state"+state.pc);
    }
    public int convertNum(int num) {//num decimal
        /* แปลงจำนวน 16 บิตเป็น 32 บิตแบบ signed */
        if ((num & (1 << 15)) != 0) {
            // ถ้าบิตที่ 16 (bit ที่ตำแหน่ง 15) เป็น 1 (เลขลบใน signed 16-bit)
            num -= (1 << 16); // หักค่า 2^16 เพื่อแปลงเป็นค่าลบที่ถูกต้อง
        }
        return num;
    }
}