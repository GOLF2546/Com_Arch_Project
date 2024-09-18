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
        
        memory = new int[NUMMEMORY];
        registers = new HashMap<>();
        // memory[0]=8454151;
        // memory[1]=9043971;
        // memory[2]=655361;
        // memory[3]=16842754;
        // memory[4]=16842749;
        // memory[5]=29360128;
        // memory[6]=25165824;
        // memory[7]=5;
        // memory[8]=-1;
        // memory[9]=2;
           
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
    //machinecode เก็บใน memory[]
    public void loadProgram(List<String> machineCode) {
        for (int i = 0; i < machineCode.size(); i++) {
            
            //memory[i] = this.convertNum(Integer.parseInt(machineCode.get(i), 2));
            memory[i] = Integer.parseInt(machineCode.get(i));
            //System.out.println(memory[i]);
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
        String rs;
        String rd;
        String rt;
        int regBValue;
        switch (opcode) {
            case "000": // ADD
                rd = "R" + Integer.parseInt(instruction.substring(22, 25), 2);
                rs = "R" + Integer.parseInt(instruction.substring(3, 6), 2);
                rt = "R" + Integer.parseInt(instruction.substring(6,9), 2);
                int rsValue = registers.getOrDefault(rs, 0);
                int rtValue = registers.getOrDefault(rt, 0);
                registers.put(rd, rsValue + rtValue);
                
            break;
            case "001": // nand (Nand ค่าใน regA ด้วยค่าใน regB และเอาค่าไปเก็บใน destReg)
                rs = "R" + Integer.parseInt(instruction.substring(3, 6), 2); // Bits 21-19 reg A (rs)
                rt = "R" + Integer.parseInt(instruction.substring(6,9), 2); //  Bits 18-16 res B (rt)
                rd = "R" + Integer.parseInt(instruction.substring(22, 25), 2); //Bits 2-0  destReg (rd)
              
                int regAValue = registers.getOrDefault(rs , 0);
                regBValue = registers.getOrDefault(rt, 0);
                registers.put(rd,nand(regAValue, regBValue) & 0xFFFFFFFF);

                break;
            case "010": // LOAD
                String rload = "R" + Integer.parseInt(instruction.substring(6, 9), 2);//r1
                String raddress = "R" + Integer.parseInt(instruction.substring(3, 6), 2);//r0
                int raddressValue = Integer.valueOf(registers.get(raddress));
                int address =raddressValue + Integer.parseInt(instruction.substring(9,25), 2);
                registers.put(rload, memory[address]);
                break;
            case "011": // STORE (Store regB ใน memory และ memory address หาได้จากการเอา offsetField บวกกับค่าใน regA)
                String rstore = "R" + Integer.parseInt(instruction.substring(6, 9), 2);//r1  Bits 18-16 reg B (rt) 
                String rstoreaddress = "R" + Integer.parseInt(instruction.substring(3, 6), 2);//r0  Bits 21-19 reg A (rs)
                int rstoreaddressValue = Integer.valueOf(registers.get(rstoreaddress));
                regBValue = Integer.valueOf(registers.get(rstore));
                int storeaddress =rstoreaddressValue + Integer.parseInt(instruction.substring(9,25), 2);
                //registers.put(rstore, memory[storeaddress]);
                memory[storeaddress]=regBValue;
                break;
            case "100"://beq (ถ้า ค่าใน regA เท่ากับค่าใน regB ให้กระโดดไปที่ address PC+1+offsetField ซึ่ง PC คือ address ของ beq instruction)
                int offset;
                String r2 = "R" + Integer.parseInt(instruction.substring(6, 9), 2);//r1
                String r1 = "R" + Integer.parseInt(instruction.substring(3, 6), 2);//r0
                int r2value = Integer.valueOf(registers.get(r2));
                int r1value = Integer.valueOf(registers.get(r1));
                offset=convertNum(Integer.parseInt(instruction.substring(9,25), 2));
                System.out.println(r1value+" "+r2value+" "+offset);
                if(r1value==r2value) pc=pc+offset;
                break;
            case "101": //jalr (เก็บค่า PC+1 ไว้ใน regB ซึ่ง PC คือ address ของ jalr instruction และกระโดดไปที่ address ที่ถูกเก็บไว้ใน regA แต่ถ้า regA และ regB คือ register ตัวเดียวกัน ให้เก็บ PC+1 ก่อน และค่อยกระโดดไปที่ PC+1)
            rs = "R" + Integer.parseInt(instruction.substring(3, 6), 2); //Bits 21-19 reg A (rs)
            rd = "R" + Integer.parseInt(instruction.substring(6, 9), 2); //Bits 18-16 reg B (rd)
            if(!rd.equals(rd)){
                int regAvalue = Integer.valueOf(registers.get(rs));
            registers.put(rd, pc); //เก็บค่า PC+1 ไว้ใน regB +1/+0
            pc=regAvalue; 
            }else{
                registers.put(rd, pc); //เก็บค่า PC+1 ไว้ใน regB
                //pc=pc+1; // +1 / +0
            }
            
            
                break;
            case "110"://halt
                
                System.out.println("machine halted");
                return false;
            case "111"://noop
                break;
           
        }
        
        return true;
    }

    public void run() {
        
         while (true) {
            // printState(this); 

            String instruction = fetch();


            if (!decodeExecute(instruction)){
                //System.out.println("Finsihed");
                break;
            }
            
            //System.out.println(pc);
         }
        
        //  System.out.println(decodeExecute("1100000000000000000000000"));
        //  printState(this);
        //System.out.println("Final register states: " + registers);
        System.out.println("total of "+ total_instruction +" instructions executed");
        //System.out.println("Memory locations 0-10: " + Arrays.toString(Arrays.copyOfRange(memory, 0, 11)));
        System.out.println("final state of machine:");
        System.out.println(registers.get("R3"));

    }


   
    public static void runSimulate(List<String> input) {  //use this function to use everything
        BinaryStringToDecimal(input);
        MachineCodeSimulator sim = new MachineCodeSimulator();
        sim.loadProgram(input);
        sim.run();
        printState(sim); 
    }

    public static void main(String[] args) {
        
        

        List<String> machineCode = Arrays.asList(
            "8454151","9043971","655361","16842754","16842749"
            ,"29360128","25165824","5","-1","2");

        MachineCodeSimulator sim = new MachineCodeSimulator();
       // printState(sim);
        sim.loadProgram(machineCode);
       sim.run();
       printState(sim);
        
    }
    private static void printState(MachineCodeSimulator state) {
        System.out.println("\n@@@\nstate:");
        System.out.printf("\tpc %d\n", state.pc);
        System.out.println("\tmemory:");
        for (int i = 0; i < NUMMEMORY; i++) {
            if(state.memory[i]==0 ){ //or tate.memory[i]==25165824 || 
                break;
            }
            System.out.printf("\t\tmem[ %d ] %d\n", i, state.memory[i]);
            
        }
        System.out.println("\tregisters:");
        for (int i = 0; i < NUMREGS; i++) {
            System.out.printf("\t\treg[ %d ] %d\n", i, state.registers.get("R"+i));
        }
        
        System.out.println("end state"); //+state.pc
    }
    public int convertNum(int num) {//num decimal
        /* แปลงจำนวน 16 บิตเป็น 32 บิตแบบ signed */
        if ((num & (1 << 15)) != 0) {
            // ถ้าบิตที่ 16 (bit ที่ตำแหน่ง 15) เป็น 1 (เลขลบใน signed 16-bit)
            num -= (1 << 16); // หักค่า 2^16 เพื่อแปลงเป็นค่าลบที่ถูกต้อง
        }
        return num;
    }

    public static int nand(int input1, int input2) {
        // Perform bitwise AND, then NOT (~ inverts all bits)
        return ~(input1 & input2);
    }

   
    
    public static void BinaryStringToDecimal(List<String> inputArray) { 
            for(int i = 0; i < inputArray.size(); i++){
                
                inputArray.set(i, binaryToDecimal(inputArray.get(i)));
            }    
    }

    public static String binaryToDecimal(String binaryStr)
    {
            if (binaryStr.length() != 32) {
                throw new IllegalArgumentException("Binary string must be exactly 32 bits long");
            }
            if (binaryStr.charAt(0) == '1') {
                long unsignedValue = Long.parseUnsignedLong(binaryStr, 2);
                int signedValue = (int)(unsignedValue & 0xFFFFFFFF);
                return Integer.toString(signedValue);
            } else {
                // For positive numbers, directly convert to hexadecimal
                long decimalValue = Long.parseLong(binaryStr, 2);
                return Long.toString(decimalValue);
            }
       
    }
    

}